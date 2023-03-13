package com.smarsh.dig.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    private static final File xsdFile = new File("src/main/resources/xsd/actiance.sfab/cis/dig/itm-transcript-1.0.xsd");
    private static String dataDir = "src/test/resources/data/";
    private static final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private static Schema schema;

    @BeforeAll
    static void init() throws Exception {
        schema = sf.newSchema(xsdFile);
    }

    @ParameterizedTest(name = "validate[{index}] {0} is valid: {1}")
    @MethodSource("validFileLister")
    void weCanValidateWithSchema(final String filename, final boolean isValid) {
        File xmlFile = new File("%s/%s".formatted(dataDir, filename));

        Validator validator;

        validator = schema.newValidator();

        assertNotNull(validator);

        if (isValid) {
            assertDoesNotThrow(() -> validator.validate(new StreamSource(xmlFile)));
        } else {
            assertThrows(SAXException.class, () -> validator.validate(new StreamSource(xmlFile)));
        }
    }

    static Stream<Arguments> validFileLister() {
        return Stream.of(
                Arguments.of("bloomberg.xml", true),
                Arguments.of("invalid.xml", false),
                Arguments.of("email.xml", true)
        );
    }
}
