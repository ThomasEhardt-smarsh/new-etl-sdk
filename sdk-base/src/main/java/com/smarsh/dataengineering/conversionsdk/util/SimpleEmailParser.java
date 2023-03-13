package com.smarsh.dataengineering.conversionsdk.util;

import org.apache.commons.mail.util.MimeMessageParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SimpleEmailParser extends AbstractEmailParser {
    private static final Logger log = LoggerFactory.getLogger(SimpleEmailParser.class);
    private static final String MAIL_MIME_ADDRESS_STRICT = "mail.mime.address.strict";
    private static final String MAIL_MIME_MULTIPART_ALLOW_EMPTY = "mail.mime.multipart.allowempty";

    private final MimeMessageParser mimeMessageParser;

    private SimpleEmailParser(@NotNull MimeMessageParser mimeMessageParser) {
        this.mimeMessageParser = mimeMessageParser;
    }


    @NotNull
    public static SimpleEmailParser parseEmailFromPath(@NotNull Path emailPath) throws EmailParseException {
        try {
            return parseEmailFromStream(new BufferedInputStream(Files.newInputStream(emailPath)));
        } catch (IOException e) {
            throw new EmailParseException("unable to parse email from file %s".formatted(emailPath.toAbsolutePath()), e);
        }
    }

    @NotNull
    public static SimpleEmailParser parseEmailFromStream(@NotNull InputStream emailInputStream) throws EmailParseException {
        var sessionProperties = new Properties();
        sessionProperties.setProperty(MAIL_MIME_ADDRESS_STRICT, String.valueOf(false));
        sessionProperties.setProperty(MAIL_MIME_MULTIPART_ALLOW_EMPTY, String.valueOf(true));

        var session = Session.getInstance(sessionProperties);

        try {
            var mimeMessage = new MimeMessage(session, emailInputStream);
            return new SimpleEmailParser(new MimeMessageParser(mimeMessage).parse());
        } catch (Exception e) {
            throw new EmailParseException("unable to parse input stream", e);
        }
    }

    @Override
    public @NotNull Optional<String> getMessageId() {
        String messageId = null;
        try {
            messageId = mimeMessageParser.getMimeMessage().getMessageID();
        } catch (MessagingException e) {
            log.warn("could not obtain message ID from message - this field is optional", e);
        }

        return Optional.ofNullable(messageId);
    }

    @Override
    public @NotNull Collection<String> getHeaderValues(@NotNull String headerName) {
        Collection<String> values = Collections.emptyList();
        try {
            String[] stringValues = mimeMessageParser.getMimeMessage().getHeader(headerName);
            if (Objects.nonNull(stringValues) && stringValues.length > 0) {
                values = List.of(stringValues);
            }
        } catch (MessagingException e) {
            log.warn("could not obtain header values from {}", headerName, e);
        }

        return values;
    }

    @Override
    public @NotNull Optional<String> getSubject() {
        String subject = null;

        try {
            subject = mimeMessageParser.getSubject();
        } catch (Exception e) {
            log.warn("could not obtain subject from message - this field is optional", e);
        }

        return Optional.ofNullable(subject);
    }

    @Override
    public @NotNull Optional<String> getPlainTextBody() {
        return Optional.ofNullable(mimeMessageParser.hasPlainContent() ? mimeMessageParser.getPlainContent() : null);
    }

    @Override
    public @NotNull Optional<String> getHtmlBody() {
        return Optional.ofNullable(mimeMessageParser.hasHtmlContent() ? mimeMessageParser.getHtmlContent() : null);
    }

    @Override
    public byte[] getAttachmentBytesByCid(@NotNull String cid) {
        return getBytesFromDataSource(mimeMessageParser.hasAttachments() ? mimeMessageParser.findAttachmentByCid(cid) : null);
    }

    @Override
    public @NotNull InputStream getAttachmentStreamByCid(@NotNull String cid) {
        return getInputStreamFromDataSource(mimeMessageParser.hasAttachments() ? mimeMessageParser.findAttachmentByCid(cid) : null);
    }

    @Override
    public byte[] getAttachmentBytesByName(@NotNull String name) {
        return getBytesFromDataSource(mimeMessageParser.hasAttachments() ? mimeMessageParser.findAttachmentByName(name) : null);
    }

    @Override
    public @NotNull InputStream getAttachmentStreamByName(@NotNull String name) {
        return getInputStreamFromDataSource(mimeMessageParser.hasAttachments() ? mimeMessageParser.findAttachmentByName(name) : null);
    }

    /**
     * Gets all known CIDs for attachments. By default, MimeMessageParser.getContentIds() will return CIDs for all parts
     * regardless of Content-Disposition. Going through the attachment list and comparing to the CID list will give us
     * only CIDs for attachments.
     * @return a Collection of String containing the CIDs, or an empty List if there are no attachments and/or CIDs
     */
    @Override
    public @NotNull Collection<String> getAttachmentCids() {
        if (!mimeMessageParser.hasAttachments()) {
            return Collections.emptyList();
        }

        return mimeMessageParser.getContentIds().stream()
                .filter(cid -> mimeMessageParser.findAttachmentByCid(cid) != null)
                .toList();
    }

    /**
     * return a list of attachment names for named attachments (this sounds redundant, but it's not necessary for an
     * attachment to have a name), so we will return only non-null names
     * @return Collection of attachment names, or an empty List if there are no named attachments
     */
    @Override
    public @NotNull Collection<String> getAttachmentNames() {
        if (!mimeMessageParser.hasAttachments()) {
            return Collections.emptyList();
        }

        return mimeMessageParser.getAttachmentList().stream()
                .map(DataSource::getName)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<DataSource> getAttachments() {
        if (mimeMessageParser.hasAttachments()) {
            return mimeMessageParser.getAttachmentList();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public @NotNull Collection<EmailUser> getEmailUsersFromHeader(@NotNull String header) {
        var headerValues = getHeaderValues(header);
        List<EmailUser> emailUsers = Collections.emptyList();

        if (!headerValues.isEmpty()) {
            emailUsers = headerValues.stream()
                    .map(EmailUser::fromString)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        }

        return emailUsers;
    }

    @Override
    public @NotNull Collection<EmailUser> getEmailUsersFromStandardHeaders(@NotNull Collection<StandardEmailHeader> headers) {
        var emailUsers = new ArrayList<Address>();

        headers.forEach(standardEmailHeader -> {
            try {
                switch (standardEmailHeader) {
                    case TO -> emailUsers.addAll(mimeMessageParser.getTo());
                    case CC -> emailUsers.addAll(mimeMessageParser.getCc());
                    case BCC -> emailUsers.addAll(mimeMessageParser.getBcc());
                    case FROM -> {
                        // NOTE: commons-email will only return the address portion of the user with getFrom()
                        var fromUsers = getHeaderValues("From");
                        String fromUser;
                        if (!fromUsers.isEmpty()) {
                            fromUser = fromUsers.stream().toList().get(0);
                        } else {
                            // we couldn't get from the "From" header for whatever reason, so use getFrom()
                            // this means we lose the name and just get the address
                            fromUser = mimeMessageParser.getFrom();
                        }
                        emailUsers.add(new InternetAddress(fromUser));
                    }
                    default -> log.error("unknown header type {} requested", standardEmailHeader);
                }
            } catch (Exception e) {
                log.warn("unable to get users from header {}", standardEmailHeader, e);
            }
        });

        return emailUsers.stream()
                .map(EmailUser::fromAddress)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public @NotNull Collection<EmailUser> getDedupedEmailUsersFromStandardHeaders(@NotNull Collection<StandardEmailHeader> headers, boolean ignoreAddressCase) {
        return super.getDedupedEmailUsersFromStandardHeaders(headers, ignoreAddressCase);
    }

    private byte[] getBytesFromDataSource(@Nullable DataSource dataSource)  {
        if (Objects.isNull(dataSource)) {
            return new byte[0];
        }

        try (InputStream dsStream = dataSource.getInputStream()) {
            return dsStream.readAllBytes();
        } catch (IOException e) {
            log.error("unable to read bytes from datasource - returning empty byte array", e);
        }

        return new byte[0];
    }

    private InputStream getInputStreamFromDataSource(@Nullable DataSource dataSource) {
        if (Objects.isNull(dataSource)) {
            return InputStream.nullInputStream();
        }

        try {
            return dataSource.getInputStream();
        } catch (IOException e) {
            log.error("unable to get input stream from datasource - returning nullInputStream", e);
        }

        return InputStream.nullInputStream();
    }
}
