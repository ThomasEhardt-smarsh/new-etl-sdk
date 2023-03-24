package com.smarsh.dataengineering.conversionsdk.util;

import com.smarsh.dataengineering.conversionsdk.util.EmailParser.EmailUser;
import com.smarsh.dataengineering.conversionsdk.util.EmailParser.StandardEmailHeader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SimpleEmailParserTest {
    private static final Logger log = LoggerFactory.getLogger(SimpleEmailParserTest.class);

    @SuppressWarnings("SpellCheckingInspection")
    static Stream<Arguments> getMessageId() {
        return Stream.of(arguments("EmailWithAttachments.eml", "<ABCD123456789001@digitalreasoning.com>"), arguments("EmailWithBccCc.eml", "<da8e409a-d7aa-11ec-9d64-0242ac120002>"), arguments("EmailWithInlineAttachments.eml", "<ABCD123456789001@digitalreasoning.com>"), arguments("large.eml", "<CAMULqeJ_Tv-wEdVNwwFzWD60ZFKuJ9m=Udj6Cv3xF6Z8fOTYAA@mail.gmail.com>"), arguments("sanitized_sdk_attachments.eml", "<2116578487.691447.1642696462617@gbl12430.systems.uk.BANK>"), arguments("sanitized_sdk_attachments-2.eml", "<2116578487.691447.1642696462617@gbl12430.systems.uk.BANK>"), arguments("two-attach-same-name.eml", "<CAEi4OHUN0yWJwnd2YJf008GnwyD4AqnrvdnY8qjrbxky0uXUeQ@mail.example.com>"));
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static Stream<Arguments> getAttachmentCids() {
        return Stream.of(arguments("EmailWithAttachments.eml", List.of("823504223@17052000-0f8d", "823504223@17052000-0f94")), arguments("EmailWithBccCc.eml", Collections.emptyList()), arguments("EmailWithInlineAttachments.eml", List.of("823504223@17052000-0f8d")), arguments("large.eml", Collections.emptyList()),
                /* note: we leave off sanitized_sdk_attachments.eml because it is not properly encoded - it is included
                         here for completeness
                Arguments.arguments("sanitized_sdk_attachments.eml", List.of("internal_693379520518512Fh4efwGXeXBwzzAAck7Uxgg3D3D_202201191506_1U22019919470001%20-%20%E3%83%A1%E3%83%A2%E5%B8%B3.pdf")),
                */
                arguments("sanitized_sdk_attachments-2.eml", List.of("internal_693379520518512Fh4efwGXeXBwzzAAck7Uxgg3D3D_202201191506_1U22019919470001%20-%20%E3%83%A1%E3%83%A2%E5%B8%B3.pdf")), arguments("two-attach-same-name.eml", List.of("f_ld44kyhg1", "f_ld44kygz0")));
    }

    public static Stream<Arguments> getAttachmentNames() {
        return Stream.of(arguments("EmailWithAttachments.eml", List.of("abc.txt", "def.txt")), arguments("EmailWithBccCc.eml", Collections.emptyList()),
                // this message has an attachment, but it is inline and is not named
                arguments("EmailWithInlineAttachments.eml", Collections.emptyList()), arguments("large.eml", Collections.emptyList()), arguments("sanitized_sdk_attachments.eml", List.of("202201191506_1U22019919470001 - メモ帳.pdf")), arguments("sanitized_sdk_attachments-2.eml", List.of("202201191506_1U22019919470001 - メモ帳.pdf")), arguments("two-attach-same-name.eml", List.of("unknown%20poem.txt", "unknown poem.txt")));
    }

    public static Stream<Arguments> getAttachments() {
        return Stream.of(arguments("EmailWithAttachments.eml", 2), arguments("EmailWithBccCc.eml", 0), arguments("EmailWithInlineAttachments.eml", 1), arguments("large.eml", 0), arguments("no-subject.eml", 0), arguments("sanitized_sdk_attachments-2.eml", 1), arguments("sanitized_sdk_attachments.eml", 1), arguments("two-attach-same-name.eml", 2));
    }

    public static Stream<Arguments> getEmailUsersFromHeader() {
        // note: if we want to test any standard ppt header, we need to parse it, as we will get a single string
        //       instead of a collection

        return Stream.of(arguments("EmailWithAttachments.eml", "From", List.of(new EmailUser("a.b@digitalreasoning.com", "A B"))), arguments("EmailWithAttachments.eml", "invalid-header", Collections.emptyList()), arguments("EmailWithBccCc.eml", "Xxx", List.of(new EmailUser("fake.recipient@example.com", null))), arguments("no-subject.eml", "From", List.of(new EmailUser("3128675309", null))));
    }

    public static Stream<Arguments> getEmailUsersFromStandardHeaders() {
        return Stream.of(arguments("EmailWithAttachments.eml", List.of(StandardEmailHeader.TO), List.of(new EmailUser("c.d@digitalreasoning.com", "C D"))), arguments("EmailWithAttachments.eml", List.of(StandardEmailHeader.FROM), List.of(new EmailUser("a.b@digitalreasoning.com", "A B"))), arguments("EmailWithAttachments.eml", List.of(StandardEmailHeader.CC), Collections.emptyList()), arguments("EmailWithAttachments.eml", List.of(StandardEmailHeader.BCC), Collections.emptyList()), arguments("EmailWithAttachments.eml", Arrays.asList(StandardEmailHeader.values()), List.of(new EmailUser("a.b@digitalreasoning.com", "A B"), new EmailUser("c.d@digitalreasoning.com", "C D"))),

                // by default, we aren't deduping, so we will have duplicates
                arguments("EmailWithBccCc.eml", List.of(StandardEmailHeader.TO), List.of(new EmailUser("to.recipient.one@example.com", "To Recipient One"), new EmailUser("to.recipient.two@example.com", "To Recipient Two"), new EmailUser("to.recipient.two@example.com", null))),

                // we can handle non-ascii
                arguments("EmailWithBccCc.eml", List.of(StandardEmailHeader.BCC), List.of(new EmailUser("bcc.recipient.one@example.com", "Bcc Recipient One"), new EmailUser("bcc.recipient.two@example.com", "Bcc Recipient Two"), new EmailUser("john.doe@example.co.kr", "존 도우"))));
    }

    public static Stream<Arguments> getDedupedEmailUsersFromStandardHeaders() {
        return Stream.of(arguments("EmailWithBccCc.eml", List.of(StandardEmailHeader.TO), List.of(new EmailUser("to.recipient.one@example.com", "To Recipient One"), new EmailUser("to.recipient.two@example.com", "To Recipient Two"))));
    }

    @ParameterizedTest
    @ValueSource(strings = {"EmailWithAttachments.eml", "EmailWithBccCc.eml", "EmailWithInlineAttachments.eml", "large.eml", "sanitized_sdk_attachments.eml", "sanitized_sdk_attachments-2.eml", "two-attach-same-name.eml"})
    void parseEmailFromPath(String filename) {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted(filename));
        assertDoesNotThrow(() -> SimpleEmailParser.parseEmailFromPath(emailPath));
    }

    @Test
    void parseEmailFromPathNegativeTest(@TempDir Path tempDir) {
        var emailPath = tempDir.resolve("file-does-not-exist.txt");
        assertThrows(EmailParser.EmailParseException.class, () -> SimpleEmailParser.parseEmailFromPath(emailPath));
    }

    @ParameterizedTest
    @ValueSource(strings = {"EmailWithAttachments.eml", "EmailWithBccCc.eml", "EmailWithInlineAttachments.eml", "large.eml", "sanitized_sdk_attachments.eml", "sanitized_sdk_attachments-2.eml", "two-attach-same-name.eml"})
    void parseEmailFromStream(String filename) {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted(filename));
        assertDoesNotThrow(() -> SimpleEmailParser.parseEmailFromStream(Files.newInputStream(emailPath)));
    }

    @Disabled(value = "can't currently come up with a scenario under which this would throw")
    @Test
    void parseEmailFromStreamNegativeTest() throws Exception {
        var inputStream = InputStream.nullInputStream();
        inputStream.close();
        assertThrows(EmailParser.EmailParseException.class, () -> SimpleEmailParser.parseEmailFromStream(inputStream));
    }

    @ParameterizedTest
    @MethodSource
    void getMessageId(String filename, String expectedId) throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted(filename));
        assertEquals(expectedId, SimpleEmailParser.parseEmailFromPath(emailPath).getMessageId().orElse(null));
    }

    @Test
    void getHeaderValues() throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted("EmailWithBccCc.eml"));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        assertTrue(parser.getHeaderValues("this-header-does-not-exist").isEmpty());
        var headerValues = parser.getHeaderValues("X-Test-Header");
        assertEquals(2, headerValues.size());
        assertTrue(headerValues.containsAll(List.of("hello", "world")));
    }

    @Test
    void getSubject() throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted("large.eml"));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);
        var subject = parser.getSubject();

        assertEquals("Text prospectus", subject.orElse(null));

        // an email without a subject
        emailPath = Paths.get("src/test/resources/data/email/%s".formatted("no-subject.eml"));
        parser = SimpleEmailParser.parseEmailFromPath(emailPath);
        subject = parser.getSubject();
        assert (subject.isEmpty());
    }

    // note: we will get a value for plain text body even if we parse an empty file
    @Test
    void getPlainTextBody() throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted("EmailWithBccCc.eml"));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        var expectedBody = """
                this is a test message to demonstrate gathering ppts from headers - note
                the following:
                - sender address is not an email, just a phone number - this is allowed
                - one BCC recipient has extra text in parentheses after the address - this
                  text is non-standard and is expected to be discarded
                - one BCC recipient has a name with non-ascii characters - this is expected
                  to be decoded
                  """.replaceAll("\n", "\r\n");
        assertEquals(expectedBody, parser.getPlainTextBody().orElse(null));
    }

    @Test
    void getHtmlBody(@TempDir Path tempDir) throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted("two-attach-same-name.eml"));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        var expectedBody = "<div dir=\"ltr\">here&#39;s a message with two attachments<div><br></div><div><br></div></div>\r\n";
        assertEquals(expectedBody, parser.getHtmlBody().orElse(null));

        var emptyPath = tempDir.resolve("empty-file.eml");
        Files.writeString(emptyPath, "hello, world", StandardCharsets.UTF_8);
        parser = SimpleEmailParser.parseEmailFromPath(emptyPath);

        assertTrue(parser.getHtmlBody().isEmpty());
    }

    @Test
    @SuppressWarnings("SpellCheckingInspection")
    void getAttachmentBytesByCid() throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted("two-attach-same-name.eml"));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        var expectedBytes = Base64.getDecoder().decode("SXQgd2FzIG1hbmF5IGFuZCBtYW55IGEgeWVhciBhZ28sDQogICBJbiBhIGtpbmdkb20gYnkgdGhlIHNlYQ0K");
        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(parser.getAttachmentBytesByCid("f_ld44kyhg1"), StandardCharsets.UTF_8));

        expectedBytes = Base64.getDecoder().decode("VGhhdCBhIG1haWRlbiB0aGVyZSBsaXZlZCB3aG9tIHlvdSBtYXkga25vdw0KICAgQnkgdGhlIG5hbWUgb2YgQW5uYWJlbCBMZWU7DQo=");
        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(parser.getAttachmentBytesByCid("f_ld44kygz0"), StandardCharsets.UTF_8));

        // negative test
        assertEquals(0, parser.getAttachmentBytesByCid("this-does-not-exist").length);

        // test non-ascii characters
        emailPath = Paths.get("src/test/resources/data/email/%s".formatted("sanitized_sdk_attachments-2.eml"));
        parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        expectedBytes = Base64.getDecoder().decode("JVBERi0xLjMNCiXi48/TDQoNCjEgMCBvYmoNCjw8DQovVHlwZSAvQ2F0YWxvZw0KL091dGxpbmVzIDIgMCBSDQovUGFnZXMgMyAwIFINCj4+DQplbmRvYmoNCg0KMiAwIG9iag0KPDwNCi9UeXBlIC9PdXRsaW5lcw0KL0NvdW50IDANCj4+DQplbmRvYmoNCg0KMyAwIG9iag0KPDwNCi9UeXBlIC9QYWdlcw0KL0NvdW50IDINCi9LaWRzIFsgNCAwIFIgNiAwIFIgXSANCj4+DQplbmRvYmoNCg0KNCAwIG9iag0KPDwNCi9UeXBlIC9QYWdlDQovUGFyZW50IDMgMCBSDQovUmVzb3VyY2VzIDw8DQovRm9udCA8PA0KL0YxIDkgMCBSIA0KPj4NCi9Qcm9jU2V0IDggMCBSDQo+Pg0KL01lZGlhQm94IFswIDAgNjEyLjAwMDAgNzkyLjAwMDBdDQovQ29udGVudHMgNSAwIFINCj4+DQplbmRvYmoNCg0KNSAwIG9iag0KPDwgL0xlbmd0aCAxMDc0ID4+DQpzdHJlYW0NCjIgSg0KQlQNCjAgMCAwIHJnDQovRjEgMDAyNyBUZg0KNTcuMzc1MCA3MjIuMjgwMCBUZA0KKCBBIFNpbXBsZSBQREYgRmlsZSApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY4OC42MDgwIFRkDQooIFRoaXMgaXMgYSBzbWFsbCBkZW1vbnN0cmF0aW9uIC5wZGYgZmlsZSAtICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjY0LjcwNDAgVGQNCigganVzdCBmb3IgdXNlIGluIHRoZSBWaXJ0dWFsIE1lY2hhbmljcyB0dXRvcmlhbHMuIE1vcmUgdGV4dC4gQW5kIG1vcmUgKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2NTIuNzUyMCBUZA0KKCB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDYyOC44NDgwIFRkDQooIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjE2Ljg5NjAgVGQNCiggdGV4dC4gQW5kIG1vcmUgdGV4dC4gQm9yaW5nLCB6enp6ei4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjA0Ljk0NDAgVGQNCiggbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDU5Mi45OTIwIFRkDQooIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNTY5LjA4ODAgVGQNCiggQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA1NTcuMTM2MCBUZA0KKCB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBFdmVuIG1vcmUuIENvbnRpbnVlZCBvbiBwYWdlIDIgLi4uKSBUag0KRVQNCmVuZHN0cmVhbQ0KZW5kb2JqDQoNCjYgMCBvYmoNCjw8DQovVHlwZSAvUGFnZQ0KL1BhcmVudCAzIDAgUg0KL1Jlc291cmNlcyA8PA0KL0ZvbnQgPDwNCi9GMSA5IDAgUiANCj4+DQovUHJvY1NldCA4IDAgUg0KPj4NCi9NZWRpYUJveCBbMCAwIDYxMi4wMDAwIDc5Mi4wMDAwXQ0KL0NvbnRlbnRzIDcgMCBSDQo+Pg0KZW5kb2JqDQoNCjcgMCBvYmoNCjw8IC9MZW5ndGggNjc2ID4+DQpzdHJlYW0NCjIgSg0KQlQNCjAgMCAwIHJnDQovRjEgMDAyNyBUZg0KNTcuMzc1MCA3MjIuMjgwMCBUZA0KKCBTaW1wbGUgUERGIEZpbGUgMiApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY4OC42MDgwIFRkDQooIC4uLmNvbnRpbnVlZCBmcm9tIHBhZ2UgMS4gWWV0IG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2NzYuNjU2MCBUZA0KKCBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY2NC43MDQwIFRkDQooIHRleHQuIE9oLCBob3cgYm9yaW5nIHR5cGluZyB0aGlzIHN0dWZmLiBCdXQgbm90IGFzIGJvcmluZyBhcyB3YXRjaGluZyApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY1Mi43NTIwIFRkDQooIHBhaW50IGRyeS4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2NDAuODAwMCBUZA0KKCBCb3JpbmcuICBNb3JlLCBhIGxpdHRsZSBtb3JlIHRleHQuIFRoZSBlbmQsIGFuZCBqdXN0IGFzIHdlbGwuICkgVGoNCkVUDQplbmRzdHJlYW0NCmVuZG9iag0KDQo4IDAgb2JqDQpbL1BERiAvVGV4dF0NCmVuZG9iag0KDQo5IDAgb2JqDQo8PA0KL1R5cGUgL0ZvbnQNCi9TdWJ0eXBlIC9UeXBlMQ0KL05hbWUgL0YxDQovQmFzZUZvbnQgL0hlbHZldGljYQ0KL0VuY29kaW5nIC9XaW5BbnNpRW5jb2RpbmcNCj4+DQplbmRvYmoNCg0KMTAgMCBvYmoNCjw8DQovQ3JlYXRvciAoUmF2ZSBcKGh0dHA6Ly93d3cubmV2cm9uYS5jb20vcmF2ZVwpKQ0KL1Byb2R1Y2VyIChOZXZyb25hIERlc2lnbnMpDQovQ3JlYXRpb25EYXRlIChEOjIwMDYwMzAxMDcyODI2KQ0KPj4NCmVuZG9iag0KDQp4cmVmDQowIDExDQowMDAwMDAwMDAwIDY1NTM1IGYNCjAwMDAwMDAwMTkgMDAwMDAgbg0KMDAwMDAwMDA5MyAwMDAwMCBuDQowMDAwMDAwMTQ3IDAwMDAwIG4NCjAwMDAwMDAyMjIgMDAwMDAgbg0KMDAwMDAwMDM5MCAwMDAwMCBuDQowMDAwMDAxNTIyIDAwMDAwIG4NCjAwMDAwMDE2OTAgMDAwMDAgbg0KMDAwMDAwMjQyMyAwMDAwMCBuDQowMDAwMDAyNDU2IDAwMDAwIG4NCjAwMDAwMDI1NzQgMDAwMDAgbg0KDQp0cmFpbGVyDQo8PA0KL1NpemUgMTENCi9Sb290IDEgMCBSDQovSW5mbyAxMCAwIFINCj4+DQoNCnN0YXJ0eHJlZg0KMjcxNA0KJSVFT0YNCg==");
        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(parser.getAttachmentBytesByCid("internal_693379520518512Fh4efwGXeXBwzzAAck7Uxgg3D3D_202201191506_1U22019919470001%20-%20%E3%83%A1%E3%83%A2%E5%B8%B3.pdf"), StandardCharsets.UTF_8));
    }

    @Test
    @SuppressWarnings("SpellCheckingInspection")
    void getAttachmentBytesByName() throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted("two-attach-same-name.eml"));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        var expectedBytes = Base64.getDecoder().decode("SXQgd2FzIG1hbmF5IGFuZCBtYW55IGEgeWVhciBhZ28sDQogICBJbiBhIGtpbmdkb20gYnkgdGhlIHNlYQ0K");
        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(parser.getAttachmentBytesByName("unknown%20poem.txt"), StandardCharsets.UTF_8));

        // note that these effectively have the same name
        expectedBytes = Base64.getDecoder().decode("VGhhdCBhIG1haWRlbiB0aGVyZSBsaXZlZCB3aG9tIHlvdSBtYXkga25vdw0KICAgQnkgdGhlIG5hbWUgb2YgQW5uYWJlbCBMZWU7DQo=");
        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(parser.getAttachmentBytesByName("unknown poem.txt"), StandardCharsets.UTF_8));

        // negative test
        assertEquals(0, parser.getAttachmentBytesByName("this-does-not-exist").length);

        // test non-ascii characters
        emailPath = Paths.get("src/test/resources/data/email/%s".formatted("sanitized_sdk_attachments-2.eml"));
        parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        expectedBytes = Base64.getDecoder().decode("JVBERi0xLjMNCiXi48/TDQoNCjEgMCBvYmoNCjw8DQovVHlwZSAvQ2F0YWxvZw0KL091dGxpbmVzIDIgMCBSDQovUGFnZXMgMyAwIFINCj4+DQplbmRvYmoNCg0KMiAwIG9iag0KPDwNCi9UeXBlIC9PdXRsaW5lcw0KL0NvdW50IDANCj4+DQplbmRvYmoNCg0KMyAwIG9iag0KPDwNCi9UeXBlIC9QYWdlcw0KL0NvdW50IDINCi9LaWRzIFsgNCAwIFIgNiAwIFIgXSANCj4+DQplbmRvYmoNCg0KNCAwIG9iag0KPDwNCi9UeXBlIC9QYWdlDQovUGFyZW50IDMgMCBSDQovUmVzb3VyY2VzIDw8DQovRm9udCA8PA0KL0YxIDkgMCBSIA0KPj4NCi9Qcm9jU2V0IDggMCBSDQo+Pg0KL01lZGlhQm94IFswIDAgNjEyLjAwMDAgNzkyLjAwMDBdDQovQ29udGVudHMgNSAwIFINCj4+DQplbmRvYmoNCg0KNSAwIG9iag0KPDwgL0xlbmd0aCAxMDc0ID4+DQpzdHJlYW0NCjIgSg0KQlQNCjAgMCAwIHJnDQovRjEgMDAyNyBUZg0KNTcuMzc1MCA3MjIuMjgwMCBUZA0KKCBBIFNpbXBsZSBQREYgRmlsZSApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY4OC42MDgwIFRkDQooIFRoaXMgaXMgYSBzbWFsbCBkZW1vbnN0cmF0aW9uIC5wZGYgZmlsZSAtICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjY0LjcwNDAgVGQNCigganVzdCBmb3IgdXNlIGluIHRoZSBWaXJ0dWFsIE1lY2hhbmljcyB0dXRvcmlhbHMuIE1vcmUgdGV4dC4gQW5kIG1vcmUgKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2NTIuNzUyMCBUZA0KKCB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDYyOC44NDgwIFRkDQooIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjE2Ljg5NjAgVGQNCiggdGV4dC4gQW5kIG1vcmUgdGV4dC4gQm9yaW5nLCB6enp6ei4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjA0Ljk0NDAgVGQNCiggbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDU5Mi45OTIwIFRkDQooIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNTY5LjA4ODAgVGQNCiggQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA1NTcuMTM2MCBUZA0KKCB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBFdmVuIG1vcmUuIENvbnRpbnVlZCBvbiBwYWdlIDIgLi4uKSBUag0KRVQNCmVuZHN0cmVhbQ0KZW5kb2JqDQoNCjYgMCBvYmoNCjw8DQovVHlwZSAvUGFnZQ0KL1BhcmVudCAzIDAgUg0KL1Jlc291cmNlcyA8PA0KL0ZvbnQgPDwNCi9GMSA5IDAgUiANCj4+DQovUHJvY1NldCA4IDAgUg0KPj4NCi9NZWRpYUJveCBbMCAwIDYxMi4wMDAwIDc5Mi4wMDAwXQ0KL0NvbnRlbnRzIDcgMCBSDQo+Pg0KZW5kb2JqDQoNCjcgMCBvYmoNCjw8IC9MZW5ndGggNjc2ID4+DQpzdHJlYW0NCjIgSg0KQlQNCjAgMCAwIHJnDQovRjEgMDAyNyBUZg0KNTcuMzc1MCA3MjIuMjgwMCBUZA0KKCBTaW1wbGUgUERGIEZpbGUgMiApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY4OC42MDgwIFRkDQooIC4uLmNvbnRpbnVlZCBmcm9tIHBhZ2UgMS4gWWV0IG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2NzYuNjU2MCBUZA0KKCBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY2NC43MDQwIFRkDQooIHRleHQuIE9oLCBob3cgYm9yaW5nIHR5cGluZyB0aGlzIHN0dWZmLiBCdXQgbm90IGFzIGJvcmluZyBhcyB3YXRjaGluZyApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY1Mi43NTIwIFRkDQooIHBhaW50IGRyeS4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2NDAuODAwMCBUZA0KKCBCb3JpbmcuICBNb3JlLCBhIGxpdHRsZSBtb3JlIHRleHQuIFRoZSBlbmQsIGFuZCBqdXN0IGFzIHdlbGwuICkgVGoNCkVUDQplbmRzdHJlYW0NCmVuZG9iag0KDQo4IDAgb2JqDQpbL1BERiAvVGV4dF0NCmVuZG9iag0KDQo5IDAgb2JqDQo8PA0KL1R5cGUgL0ZvbnQNCi9TdWJ0eXBlIC9UeXBlMQ0KL05hbWUgL0YxDQovQmFzZUZvbnQgL0hlbHZldGljYQ0KL0VuY29kaW5nIC9XaW5BbnNpRW5jb2RpbmcNCj4+DQplbmRvYmoNCg0KMTAgMCBvYmoNCjw8DQovQ3JlYXRvciAoUmF2ZSBcKGh0dHA6Ly93d3cubmV2cm9uYS5jb20vcmF2ZVwpKQ0KL1Byb2R1Y2VyIChOZXZyb25hIERlc2lnbnMpDQovQ3JlYXRpb25EYXRlIChEOjIwMDYwMzAxMDcyODI2KQ0KPj4NCmVuZG9iag0KDQp4cmVmDQowIDExDQowMDAwMDAwMDAwIDY1NTM1IGYNCjAwMDAwMDAwMTkgMDAwMDAgbg0KMDAwMDAwMDA5MyAwMDAwMCBuDQowMDAwMDAwMTQ3IDAwMDAwIG4NCjAwMDAwMDAyMjIgMDAwMDAgbg0KMDAwMDAwMDM5MCAwMDAwMCBuDQowMDAwMDAxNTIyIDAwMDAwIG4NCjAwMDAwMDE2OTAgMDAwMDAgbg0KMDAwMDAwMjQyMyAwMDAwMCBuDQowMDAwMDAyNDU2IDAwMDAwIG4NCjAwMDAwMDI1NzQgMDAwMDAgbg0KDQp0cmFpbGVyDQo8PA0KL1NpemUgMTENCi9Sb290IDEgMCBSDQovSW5mbyAxMCAwIFINCj4+DQoNCnN0YXJ0eHJlZg0KMjcxNA0KJSVFT0YNCg==");
        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(parser.getAttachmentBytesByName("202201191506_1U22019919470001 - メモ帳.pdf"), StandardCharsets.UTF_8));
    }

    @Test
    @SuppressWarnings("SpellCheckingInspection")
    void getAttachmentStreamByCid() throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted("two-attach-same-name.eml"));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        var expectedBytes = Base64.getDecoder().decode("SXQgd2FzIG1hbmF5IGFuZCBtYW55IGEgeWVhciBhZ28sDQogICBJbiBhIGtpbmdkb20gYnkgdGhlIHNlYQ0K");
        var actualBytes = parser.getAttachmentStreamByCid("f_ld44kyhg1").readAllBytes();
        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(actualBytes, StandardCharsets.UTF_8));

        expectedBytes = Base64.getDecoder().decode("VGhhdCBhIG1haWRlbiB0aGVyZSBsaXZlZCB3aG9tIHlvdSBtYXkga25vdw0KICAgQnkgdGhlIG5hbWUgb2YgQW5uYWJlbCBMZWU7DQo=");
        actualBytes = parser.getAttachmentStreamByCid("f_ld44kygz0").readAllBytes();
        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(actualBytes, StandardCharsets.UTF_8));
    }

    @Test
    @SuppressWarnings("SpellCheckingInspection")
    void getAttachmentStreamByName() throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted("two-attach-same-name.eml"));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        var expectedBytes = Base64.getDecoder().decode("SXQgd2FzIG1hbmF5IGFuZCBtYW55IGEgeWVhciBhZ28sDQogICBJbiBhIGtpbmdkb20gYnkgdGhlIHNlYQ0K");
        var actualBytes = parser.getAttachmentStreamByName("unknown%20poem.txt").readAllBytes();
        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(actualBytes, StandardCharsets.UTF_8));

        // note that these effectively have the same name
        expectedBytes = Base64.getDecoder().decode("VGhhdCBhIG1haWRlbiB0aGVyZSBsaXZlZCB3aG9tIHlvdSBtYXkga25vdw0KICAgQnkgdGhlIG5hbWUgb2YgQW5uYWJlbCBMZWU7DQo=");
        actualBytes = parser.getAttachmentStreamByName("unknown poem.txt").readAllBytes();
        assertEquals(new String(expectedBytes, StandardCharsets.UTF_8), new String(actualBytes, StandardCharsets.UTF_8));
    }

    @ParameterizedTest
    @MethodSource
    void getAttachmentCids(String filename, List<String> cids) throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted(filename));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);
        var actualCids = parser.getAttachmentCids();

        assertEquals(cids.size(), actualCids.size());
        assertTrue(cids.containsAll(actualCids));
    }

    @ParameterizedTest
    @MethodSource
    void getAttachmentNames(String filename, List<String> names) throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted(filename));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);
        var actualNames = parser.getAttachmentNames();

        assertEquals(names.size(), actualNames.size());
        assertTrue(names.containsAll(actualNames));

    }

    @ParameterizedTest
    @MethodSource
    void getAttachments(String filename, int count) throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted(filename));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        assertEquals(count, parser.getAttachments().size());
    }

    @ParameterizedTest
    @MethodSource
    void getEmailUsersFromHeader(String filename, String header, List<EmailUser> emailUsers) throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted(filename));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        var actualEmailUsers = parser.getEmailUsersFromHeader(header);
        assertEquals(emailUsers.size(), actualEmailUsers.size());
        assertTrue(emailUsers.containsAll(actualEmailUsers));
    }

    @ParameterizedTest
    @MethodSource
    void getEmailUsersFromStandardHeaders(String filename, List<StandardEmailHeader> headers, List<EmailUser> emailUsers) throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted(filename));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        var actualEmailUsers = parser.getEmailUsersFromStandardHeaders(headers);
        assertEquals(emailUsers.size(), actualEmailUsers.size());
        assertTrue(emailUsers.containsAll(actualEmailUsers));
    }

    @ParameterizedTest
    @MethodSource
    void getDedupedEmailUsersFromStandardHeaders(String filename, List<StandardEmailHeader> headers, List<EmailUser> emailUsers) throws Exception {
        var emailPath = Paths.get("src/test/resources/data/email/%s".formatted(filename));
        var parser = SimpleEmailParser.parseEmailFromPath(emailPath);

        var actualEmailUsers = parser.getDedupedEmailUsersFromStandardHeaders(headers, true);
        assertEquals(emailUsers.size(), actualEmailUsers.size());
        assertTrue(emailUsers.containsAll(actualEmailUsers));
    }
}