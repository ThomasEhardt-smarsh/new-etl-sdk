package com.smarsh.dataengineering.conversionsdk.transform;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * the basic interface for performing transformations
 */
public interface Transformer {
    void transform(final InputStream input, final OutputStream output) throws TransformerException;
    void transform(final InputStream input, final OutputStream output, final Properties streamMetadata) throws TransformerException;
}
