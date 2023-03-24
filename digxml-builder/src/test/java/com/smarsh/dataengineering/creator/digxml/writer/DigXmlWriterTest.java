package com.smarsh.dataengineering.creator.digxml.writer;

import com.smarsh.dataengineering.conversionsdk.util.DateTimeUtils;
import com.smarsh.dataengineering.creator.digxml.builder.*;
import com.smarsh.dataengineering.model.digxml.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.xml.stream.XMLOutputFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

class DigXmlWriterTest {

    @Test
    void write(@TempDir Path target) throws Exception {
        var outputPath = target.resolve("foo.xml");
        var outputWriter = new FileWriter(outputPath.toFile());
        var xmlStreamWriter = XMLOutputFactory.newFactory().createXMLStreamWriter(outputWriter);

        var now = Instant.now().toEpochMilli();
        var yesterday = now - 86_400_000;

        // need to create a non-empty interaction
        var interaction = new InteractionBuilder(
                new TimeFrameBuilder(yesterday, now).build(),
                "13_3001",
                new ModalityBuilder("IM", "InstantBloomberg")
                        .with(modalityBuilder -> {
                            modalityBuilder.setVendor("Bloomberg");
                            modalityBuilder.setClassification(ModalityClass.UC);
                            modalityBuilder.setType(ModalityType.PRIVATE);
                }).build(),
                "inter-event-id-0001"
        ).with(interactionBuilder -> {
            interactionBuilder.setSubject("PCHAT-0x000000000002d", MimeType.TEXT_PLAIN);
            interactionBuilder.addAttribute(
                    new IndexableAttributeBuilder("Conversation contains Forms")
                            .with(builder -> {
                                builder.setValue("false");
                                builder.setContentType(MimeType.TEXT_PLAIN);
                                builder.setUserVisibility(UserVisibility.ALWAYS_VISIBLE);
                                builder.setIndexable(true);
                            }).build()
            );
            interactionBuilder.setThreadObjectId("13_7540007");
            interactionBuilder.addParticipant(
                    new ParticipantBuilder(
                            new NetworkEndpointBuilder("InstantBloomberg", "jonusko")
                                    .with(networkEndpointBuilder -> {
                                        networkEndpointBuilder.setDisplayName("jonusko");
                                        networkEndpointBuilder.setEndpointIdType("login");
                                    }).build(),
                            new UserInfoBuilder("internal").build(),
                            "OWNER_null"
                    ).with(participantBuilder -> {
                        participantBuilder.setParticipantRole("owner");
                    }).build()
            );
            interactionBuilder.addParticipant(
                    new ParticipantBuilder(
                            new NetworkEndpointBuilder("InstantBloomberg", "jonusko")
                                    .with(networkEndpointBuilder -> {
                                        networkEndpointBuilder.setDisplayName("jonusko");
                                        networkEndpointBuilder.setEndpointIdType("login");
                                    }).build(),
                            new UserInfoBuilder("internal").build(),
                            "InstantBloomberg:jonusko"
                            ).with(participantBuilder -> {
                                participantBuilder.setParticipantRole("participant");
                            }).build()
            );
            interactionBuilder.addTextEvent(
                    new TextEventBuilder(
                            new TextObjectBuilder(
                                    "text-id-01",
                                    TextEventType.NORMAL_MESSAGE,
                                    new TimeStampBuilder(Instant.now().toEpochMilli()).build(),
                                    "participant-01"
                            ).with(textObjectBuilder -> {
                                textObjectBuilder.setTextContent(
                                        new TextContentBuilder()
                                                .with(tcb -> {
                                                    tcb.setContentType(MimeType.TEXT_PLAIN);
                                                    tcb.setIndexable(true);
                                                    tcb.setUserVisibile(UserVisibility.ALWAYS_VISIBLE);
                                                    tcb.setValue("hello, world");
                                                }).build()
                                );
                            }).build(),
                            "text-event-01"
                    ).build()
            );
            interactionBuilder.addFileEvent(
                    new FileEventBuilder(
                            new FileObjectBuilder("file-01")
                                    .with(fileObjectBuilder ->
                                        fileObjectBuilder.setFileContent(
                                                new FileContentBuilder()
                                                        .with(fileContentBuilder -> {
                                                                fileContentBuilder.setFilePath("C:\\tmp");
                                                                fileContentBuilder.setEncoding(Encoding.BASE_64);
                                                                fileContentBuilder.setName("hello.txt");
                                                                fileContentBuilder.setValue("Hello, world".getBytes(StandardCharsets.UTF_8));
                                                        }).build()
                                        )
                                    ).build(),
                            "file-event-01",
                            FileEventType.ATTACHMENT
                    ).build()
            );
            interactionBuilder.addFileEvent(
                    new FileEventBuilder(
                            new FileObjectBuilder("file-02")
                                    .with(fileObjectBuilder ->
                                            fileObjectBuilder.setFileContent(
                                                    new FileBackedFileContentBuilder()
                                                            .with(fileContentBuilder -> {
                                                                fileContentBuilder.setFileWithContents(
                                                                        new File(
                                                                                "src/test/resources/data/Email_InvitationforLunch3.xml"
                                                                        )
                                                                );
                                                            }).build()
                                            )
                                    ).build(),
                            "file-event-02",
                            FileEventType.ATTACHMENT
                    ).build()
            );
        }).build();

        var transcript = new TranscriptBuilder(interaction, "id_0001", "test endpoint", DateTimeUtils.toXmlGregorian(Instant.now().toEpochMilli())).build();

        new DigXmlWriter(xmlStreamWriter).writeDigXml(transcript);

        xmlStreamWriter.flush();
        xmlStreamWriter.close();

        outputWriter.flush();
        outputWriter.close();

        // TODO: dump the output
        Path tempFileOutput = Paths.get("/tmp/" + System.currentTimeMillis() + ".xml");
        Files.copy(outputPath, tempFileOutput);

        System.out.printf("file: %s%n", tempFileOutput.toAbsolutePath());

        System.out.println("********");
        System.out.println(Files.readAllLines(outputPath, StandardCharsets.UTF_8));
        System.out.println("********");
    }

    @Test
    void makeEmailInvitationForLunch3() throws Exception {
        var interaction = new InteractionBuilder(
            new TimeFrameBuilder(1439217650000L, 1439217650000L).build(),
            "5F3352DA-7BDF-4851-8E29-00F3C763A2JK2133",
            new ModalityBuilder("Email", "Journal")
                    .with(m -> m.setVendor("Microsoft"))
                    .with(m -> m.setClassification(ModalityClass.EMAIL))
                    .with(m -> m.setType(ModalityType.PUBLIC))
                    .build(),
            "<375E4EB08CA7134EBCC4D8692E46EF5C064224JK25@QAMARSMAIL01>")
            .with(i -> {
                i.setThreadObjectId("Ac/11x8c3o7Q0Zov8YRee8OysEuyE4nAB==");
                i.setSubject("Re:Compelling investment opportunity", MimeType.TEXT_PLAIN);
                i.addAttribute(new IndexableAttributeBuilder("AgentVersion")
                        .with(a -> {
                            a.setContentType(MimeType.TEXT_PLAIN);
                            a.setIndexable(false);
                            a.setUserVisibility(UserVisibility.HIDDEN);
                            a.setValue("FileVersion = 6, 2, 0, 10, ProductVersion = 6, 2, 0, 10, Create Time = Wed, Oct 15 2014,  23:38:18, Modified Time = Tue, Oct 14 2014,  11:12:36");
                        }).build());
            })
            .build();

        var transcript = new TranscriptBuilder(
                interaction,
                "<375E4EB08CA7134EBCC4D8692E46EF5C064224JK25@QAMARSMAIL01>",
                "Nemx Power Tools for MS Exchange Server",
                DateTimeUtils.toXmlGregorian(1566916136281L)
        ).build();

        var outputWriter = new PrintWriter(System.out);
        var xmlWriter = XMLOutputFactory.newFactory().createXMLStreamWriter(outputWriter);

        new DigXmlWriter(xmlWriter).writeDigXml(transcript);
        xmlWriter.flush();
        outputWriter.flush();

        assert true;
    }
}