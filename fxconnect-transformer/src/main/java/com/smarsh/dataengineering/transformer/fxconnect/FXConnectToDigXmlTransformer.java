package com.smarsh.dataengineering.transformer.fxconnect;

import com.smarsh.dataengineering.conversionsdk.transform.Transformer;
import com.smarsh.dataengineering.conversionsdk.transform.TransformerException;
import com.smarsh.dataengineering.conversionsdk.util.DateTimeUtils;
import com.smarsh.dataengineering.conversionsdk.util.EmailParser;
import com.smarsh.dataengineering.conversionsdk.util.EmailParser.EmailUser;
import com.smarsh.dataengineering.conversionsdk.util.SimpleEmailParser;
import com.smarsh.dataengineering.creator.digxml.builder.*;
import com.smarsh.dataengineering.creator.digxml.writer.DigXmlWriter;
import com.smarsh.dataengineering.model.digxml.MimeType;
import com.smarsh.dataengineering.model.digxml.TextEventType;
import com.smarsh.dataengineering.model.digxml.TimeFrame;
import com.smarsh.dataengineering.model.digxml.UserVisibility;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLOutputFactory;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.smarsh.dataengineering.conversionsdk.util.EmailParser.EmailParseException;
import static com.smarsh.dataengineering.conversionsdk.util.EmailParser.StandardEmailHeader;

// NOTE: at this time, we do not have the means to dejournal an email, so this must first be done outside of here
public class FXConnectToDigXmlTransformer implements Transformer {
    private static final Logger log = LoggerFactory.getLogger(FXConnectToDigXmlTransformer.class);

    public static final Collection<DateTimeFormatter> DATE_TIME_FORMATTER_COLLECTION = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'")
    );

    // this will come from config
    static final String internalDomain = "credit-suisse.com";
    static final String endpointId = "test";

    @Override
    public void transform(InputStream input, OutputStream output) throws TransformerException {
        try {
            convert(input, output);
        } catch (Exception e) {
            throw new TransformerException(e);
        }
    }

    @Override
    public void transform(InputStream input, OutputStream output, Properties streamMetadata) throws TransformerException {
        log.info("transform called with properties, but this channel does not use them");
        transform(input, output);
    }

    /**
     * converts from fxconnect to digxml
     * @param fxConnectFile
     * @param digXmlFile
     */
    public void convert(File fxConnectFile, File digXmlFile) throws Exception {
        try (
                var is = new BufferedInputStream(new FileInputStream(fxConnectFile));
                var os = new BufferedOutputStream(new FileOutputStream(digXmlFile))
        ) {
            convert(is, os);
        }
    }

    private void convert(InputStream fxConnectInputStream, OutputStream digXmlOutputStream) throws Exception {
        EmailParser emailParser;

        try {
            emailParser = SimpleEmailParser.parseEmailFromStream(fxConnectInputStream);
        } catch (EmailParseException e) {
            throw new IllegalArgumentException("cannot parse email", e);
        }

        // we assume for now that the entire email body can fit into memory
        // TODO: for some reason, the html body isn't being picked up as such
        var htmlBody = emailParser.getHtmlBody().orElse(emailParser.getPlainTextBody().orElse(null));
        if (Objects.isNull(htmlBody)) {
            throw new IllegalArgumentException("source email does not have an HTML body - this is invalid");
        }

        var doc = Jsoup.parse(htmlBody);

        // we assume that we have one table
        var tableElements = doc.selectXpath("/html/body/table");
        Objects.requireNonNull(tableElements, "input message does not have a table - this is an error");
        if (tableElements.size() > 1) {
            log.warn("more than one table found - this is likely an error but we will work with the first table");
        }
        var table = tableElements.first();
        Objects.requireNonNull(table, "input message does not have a table - this is an error");
        // and that it has a body
        var tableBody = table.select("tbody").first();
        Objects.requireNonNull(tableBody, "input message has empty table - this is an error");

        // let's first go through and collect our timestamps - we need this for our InteractionBuilder
        var timeFrame = getTimeFrame(tableBody);
        var interactionBuilder = new InteractionBuilder(
                timeFrame,
                emailParser.getMessageId().orElse(UUID.randomUUID().toString()),    // TODO: figure out what this should be
                new ModalityBuilder("Chat", "FXConnect").build(),
                emailParser.getMessageId().orElse(UUID.randomUUID().toString())    // TODO: figure out what this should be
        );


        // let's get all our users from the email - we will add as we go along
        var emailUsers = new ArrayList<>(emailParser.getDedupedEmailUsersFromStandardHeaders(
                List.of(StandardEmailHeader.values()),
                true
        ));

        // now get the users from the conversation and dedup
        var conversationUsers = getUsersFromConversation(tableBody);

        var dedupedUsers = EmailUser.dedupUsersByEmailAddress(Stream.concat(emailUsers.stream(), conversationUsers.stream()).collect(Collectors.toUnmodifiableSet()), true);

        dedupedUsers.stream()
                .map(emailUser -> new ParticipantBuilder(
                        new NetworkEndpointBuilder("FXConnect", emailUser.address())
                                .with(b -> b.setDisplayName(emailUser.name()))
                                .build(),
                        new UserInfoBuilder(
                                emailUser.address().toLowerCase().endsWith("@%s".formatted(internalDomain)) ? "internal" : "external"
                        ).build(), // this should be internal/external based on email
                        emailUser.address()).build()
                ).forEach(ppt -> interactionBuilder.with(b -> b.addParticipant(ppt)));

        // process the text events
        tableBody.select("tr").stream()
                .filter(row -> row.childNodeSize() == 3)
                .filter(row -> row.child(0).tag() == Tag.valueOf("td"))
                .map(row -> {
                    var timestampText = row.child(0).text();
                    var senderText = row.child(1).text();
                    var text = row.child(2).text();

                    long timeStamp = DateTimeUtils.toEpochMillis(timestampText, DATE_TIME_FORMATTER_COLLECTION, 0);

                    return new TextEventBuilder(
                            new TextObjectBuilder(
                                    String.valueOf(timeStamp),
                                    TextEventType.NORMAL_MESSAGE,
                                    new TimeStampBuilder(timeStamp).build(),
                                    senderText
                            ).with(b -> b.withTextContent(MimeType.TEXT_PLAIN, true, UserVisibility.ALWAYS_VISIBLE, text)).build(),
                            String.valueOf(timeStamp)
                    ).build();
                })
                .forEach(textEvent -> interactionBuilder.with(builder -> builder.addTextEvent(textEvent)));

        var transcriptBuilder = new TranscriptBuilder(
                interactionBuilder.build(),
                emailParser.getMessageId().orElse(UUID.randomUUID().toString()),
                endpointId,
                timeFrame.getStartTime().getTimestamp()
        );

        var outputWriter = new OutputStreamWriter(digXmlOutputStream);
        var xmlStreamWriter = XMLOutputFactory.newFactory().createXMLStreamWriter(outputWriter);

        new DigXmlWriter(xmlStreamWriter).writeDigXml(transcriptBuilder.build());
//        new TranscriptWriter(xmlStreamWriter).write(transcriptBuilder.build());

        xmlStreamWriter.flush();
        xmlStreamWriter.close();
        outputWriter.flush();
        outputWriter.close();
    }

    protected TimeFrame getTimeFrame(Element tableBody) {
        AtomicLong firstTimestamp = new AtomicLong(0L);
        AtomicLong lastTimestamp = new AtomicLong(0L);

        tableBody.select("tr").stream()
                .map(Element::firstElementChild) // get the cell
                .filter(Objects::nonNull)
                .filter(td -> td.tag() == Tag.valueOf("td") && td.hasText())
                .map(Element::text)
                .map(s -> DateTimeUtils.toEpochMillis(s, DATE_TIME_FORMATTER_COLLECTION, 0L))
                .forEach(aLong -> {
                    if (firstTimestamp.get() == 0 || aLong < firstTimestamp.get()) {
                        firstTimestamp.set(aLong);
                    }
                    if (aLong > lastTimestamp.get()) {
                        lastTimestamp.set(aLong);
                    }
                });

        return new TimeFrameBuilder(firstTimestamp.get(), lastTimestamp.get()).build();
    }

    protected Set<EmailUser> getUsersFromConversation(Element tableBody) {
        return tableBody.select("tr").stream()
                .map(element -> element.child(1))
                .filter(td -> td.tag() == Tag.valueOf("td") && td.hasText())
                .map(element -> new EmailUser(element.text(), null))
                .collect(Collectors.toUnmodifiableSet());
    }
}
