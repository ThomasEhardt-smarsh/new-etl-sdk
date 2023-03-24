package com.smarsh.dataengineering.transformer.fxconnect;

import com.smarsh.dataengineering.conversionsdk.util.DateTimeUtils;
import com.smarsh.dataengineering.conversionsdk.util.EmailParser;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FXConnectToDigXmlTransformerTest {
    private static final Logger log = LoggerFactory.getLogger(FXConnectToDigXmlTransformerTest.class);

    @TempDir
    Path tempDir;

    @Test
    void getTimeFrame() {
        var doc = Jsoup.parseBodyFragment(tableSnippet);
        var tableBodyElements = doc.body().selectXpath("/html/body/table/tbody");

        var timeFrame = new FXConnectToDigXmlTransformer().getTimeFrame(Objects.requireNonNull(tableBodyElements.first()));

        assertEquals(1636569578299L, DateTimeUtils.toEpochMillis(timeFrame.getStartTime().getTimestamp()));
        assertEquals(1636569781116L, DateTimeUtils.toEpochMillis(timeFrame.getEndTime().getTimestamp()));
    }

    @Test
    void usersFromConversation() {
        var doc = Jsoup.parseBodyFragment(tableSnippet);
        var tableBodyElements = doc.body().selectXpath("/html/body/table/tbody");

        var users = new FXConnectToDigXmlTransformer().getUsersFromConversation(Objects.requireNonNull(tableBodyElements.first()));

        assertEquals(1, users.size());
        assertTrue(Set.of(new EmailParser.EmailUser("shakir.rizvi@credit-suisse.com", null)).containsAll(users));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "02ee0b8d-e721-415d-83d0-7e8122b0869d"
    })
    void convert(String filenameBase) throws Exception {
        Path inputPath = Paths.get("src/test/resources/data/fxconnect/dejournaled/%s.eml".formatted(filenameBase));
        Path outputPath = tempDir.resolve("%s.xml".formatted(filenameBase));

        new FXConnectToDigXmlTransformer().convert(inputPath.toFile(), outputPath.toFile());

        // for convenience, log the content
        String content = Files.readString(outputPath, StandardCharsets.UTF_8);
        log.info(content);

        // this is a small sample - use JAXB
        // NOTE: for some reason, JAXB doesn't like the generated classes - possibly because the namespaces are in
        //       different files, so right now the best we can do is look at the output and ensure it is correct
        /*
        var context = JAXBContext.newInstance(Transcript.class);
        var unmarshaller = context.createUnmarshaller();

        var transcript = ((Transcript) unmarshaller.unmarshal(outputPath.toFile()));

        assertEquals(1636569578299L, DateTimeUtils.toEpochMillis(transcript.getTimeStamp()));
        */
        assertTrue(content.contains("source-endpoint-id=\"%s\"".formatted(FXConnectToDigXmlTransformer.endpointId)));
        assertTrue(content.contains("communication-object-id>&lt;cc2c40d2-ed66-4b74-af05-75f7864d7937@VerQuMSGForge1.0&gt;<"));
        // two participants
        assertEquals(2, StringUtils.countMatches(content, "<itm:participant "));
        // two text events
        assertEquals(2, StringUtils.countMatches(content, "<itm:text-event "));
    }


    String tableSnippet = """
                <html>
                <body>
                <table>
                <tbody>
                    <tr>
                        <th width="180">Date Sent (UTC)</th>
                        <th>Sender</th>
                        <th>Message</th>
                    </tr>
                    <tr class="even">
                        <td>2021-11-10T18:39:38.2990000</td>
                        <td>shakir.rizvi@credit-suisse.com</td>
                        <td>Trader XYZ icked up new trade session from $$$$; Trade Session Id: CALEDONIA-11111</td>
                    </tr>
                    <tr class="odd">
                        <td>2021-11-10T18:43:01.1160000</td>
                        <td>shakir.rizvi@credit-suisse.com</td>
                        <td>ZYXl</td>
                    </tr>
                </tbody>
                </table>
                </body>
                </html>""";
}