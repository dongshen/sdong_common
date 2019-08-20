package sdong.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.tika.metadata.Metadata;
import org.junit.Test;

public class ParsePDFTest {
    private static final Logger logger = LoggerFactory.getLogger(ParsePDFTest.class);

    @Test
    public void testGetFileMetadata_English() {
        String file = "input/pdf/arxiv_1812.01158.pdf";
        ParsePDF parse = new ParsePDF();
        Metadata metadata = parse.getFileMetadata(file);
        String[] metadataNames = metadata.names();

        logger.info("matadata size="+metadataNames.length);
        
        for (String name : metadataNames) {
            System.out.println(name + " : " + metadata.get(name));
        }
    }

    @Test
    public void testGetFileMetadata_Chinese() {
        String file = "input/pdf/Chinese_pdf.pdf";
        ParsePDF parse = new ParsePDF();
        Metadata metadata = parse.getFileMetadata(file);
        String[] metadataNames = metadata.names();

        logger.info("matadata size="+metadataNames.length);
        
        for (String name : metadataNames) {
            System.out.println(name + " : " + metadata.get(name));
        }
    }
}