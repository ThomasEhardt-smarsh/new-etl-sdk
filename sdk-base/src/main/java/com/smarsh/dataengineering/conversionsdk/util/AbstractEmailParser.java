package com.smarsh.dataengineering.conversionsdk.util;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public abstract class AbstractEmailParser {
    private static final Logger log = LoggerFactory.getLogger(AbstractEmailParser.class);

    @NotNull
    public abstract Optional<String> getMessageId();

    @NotNull
    public abstract Collection<String> getHeaderValues(@NotNull final String headerName);

    @NotNull
    public abstract Optional<String> getSubject();

    @NotNull
    public abstract Optional<String> getPlainTextBody();

    @NotNull
    public abstract Optional<String> getHtmlBody();

    public abstract byte[] getAttachmentBytesByCid(@NotNull final String cid);

    @NotNull
    public abstract InputStream getAttachmentStreamByCid(@NotNull final String cid);

    public abstract byte[] getAttachmentBytesByName(@NotNull final String name);

    @NotNull
    public abstract InputStream getAttachmentStreamByName(@NotNull final String name);

    @NotNull
    public abstract Collection<String> getAttachmentCids();

    @NotNull
    public abstract Collection<String> getAttachmentNames();

    /**
     * Used to get EmailUser objects from a given header. This method should be used when EmailUsers come from a header
     * other than to, cc, bcc, or from
     * @param header the header to pull from
     * @return a Collection of EmailUser objects from the header
     */
    @NotNull
    public abstract Collection<EmailUser> getEmailUsersFromHeader(@NotNull final String header);

    @NotNull
    public abstract Collection<EmailUser> getEmailUsersFromStandardHeaders(@NotNull final Collection<StandardEmailHeader> headers);

    @NotNull
    public Collection<EmailUser> getDedupedEmailUsersFromStandardHeaders(@NotNull final Collection<StandardEmailHeader> headers, boolean ignoreAddressCase) {
        return EmailUser.dedupUsersByEmailAddress(getEmailUsersFromStandardHeaders(headers), ignoreAddressCase);
    }

    public static class EmailParseException extends Exception {
        @SuppressWarnings("unused")
        public EmailParseException(String message) {
            super(message);
        }

        public EmailParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public enum StandardEmailHeader {
        TO, FROM, CC, BCC
    }

    public record EmailUser(String address, String name) {

        public static Optional<EmailUser> fromAddress(@NotNull final Address address) {
            EmailUser emailUser = null;

            if (address instanceof InternetAddress internetAddress) {
                emailUser = new EmailUser(internetAddress.getAddress(), internetAddress.getPersonal());
            } else {
                log.warn("could not create EmailUser from address {}", address);
            }

            return Optional.ofNullable(emailUser);
        }

        public static Optional<EmailUser> fromString(@NotNull String addressString) {
            AtomicReference<EmailUser> emailUser = new AtomicReference<>();

            try {
                var internetAddress = new InternetAddress(addressString, false);
                Optional<EmailUser> optionalEmailUser = fromAddress(internetAddress);
                optionalEmailUser.ifPresent(emailUser::set);
            } catch (AddressException e) {
                log.warn("could not create EmailUser from address string {} - {}", addressString, e.getMessage());
            }

            return Optional.ofNullable(emailUser.get());
        }

        public static Set<EmailUser> dedupUsersByEmailAddress(@NotNull final Collection<EmailUser> emailUsers, boolean ignoreAddressCase) {
            var dedupSet = new HashSet<EmailUser>();

            emailUsers.stream()
                .collect(Collectors.groupingBy(e -> {
                    if (ignoreAddressCase) {
                        return e.address().toLowerCase();
                    } else {
                        return e.address();
                    }
                }))
                .forEach((address, userList) -> {
                    var names = userList.stream()
                            .map(EmailUser::name)
                            .filter(name -> Objects.nonNull(name) && !("".equals(name.trim())))
                            .distinct()
                            .toList();

                    var userAddress = userList.get(0).address();
                    if (names.isEmpty()) {
                        dedupSet.add(new EmailUser(userAddress, null));
                    } else if (names.size() == 1) {
                        dedupSet.add(new EmailUser(userAddress, names.get(0)));
                    } else {
                        dedupSet.add(new EmailUser(userAddress, names.get(0)));
                        log.info("email address {} has {} distinct name values - using first non-empty ({})", userAddress, names.size(), names.get(0));
                    }
                });


            return dedupSet;
        }

        @Override
        public String toString() {
            return "[address=%s, name=%s]".formatted(address, name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (Objects.isNull(o) || this.getClass() != o.getClass()) {
                return false;
            }

            var oo = (EmailUser) o;
            return Objects.equals(this.address, oo.address) && Objects.equals(this.name, oo.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(address, name);
        }
    }
}
