package com.smarsh.dataengineering.conversionsdk.util;

import java.io.*;
import java.util.Base64;

public class AttachmentUtils {
    private AttachmentUtils() {}
    public static void base64EncodeFile(final File inputFile, final File outputFile) throws IOException {
        try (
                var inputFileStream = new BufferedInputStream(new FileInputStream(inputFile));
                var outputFileStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                var outputStream = Base64.getEncoder().wrap(outputFileStream);
        ) {

            var buffer = new byte[2048];
            int bytes = -1;
            while ((bytes = inputFileStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytes);
            }
        }
    }
}
