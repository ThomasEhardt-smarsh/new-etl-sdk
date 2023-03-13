package com.smarsh.sdk.util;

import com.smarsh.dataengineering.conversionsdk.util.AttachmentUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

class AttachmentUtilsTest {

    @Test
    void base64EncodeFile(@TempDir Path target) throws Exception {
        var inputPath = target.resolve("input.txt");
        try (
                FileWriter fileWriter = new FileWriter(inputPath.toFile());
                PrintWriter printWriter = new PrintWriter(fileWriter);
        ) {
            printWriter.print("hello, world!");
        }


        var outputFile = target.resolve("output.xxx").toFile();

        AttachmentUtils.base64EncodeFile(inputPath.toFile(), outputFile);

        assert Files.exists(outputFile.toPath());

        var checksum = MessageDigest.getInstance("MD5").digest(Files.readAllBytes(outputFile.toPath()));
        var checksumString = String.format("%032x", new BigInteger(1, checksum));

        Assertions.assertEquals("0a148460d46cedcd8a5db30419c8f942", checksumString, "checksums do not match");
    }
}