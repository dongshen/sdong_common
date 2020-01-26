package sdong.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.fit.pdfdom.PDFDomTree;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;
import sdong.thesis.bean.Paper;

public class ParsePDFTest {
    private static final Logger logger = LoggerFactory.getLogger(ParsePDFTest.class);

    @Test
    public void testGetFileContent_Chinese() {
        String file = "input/pdf/Chinese_pdf.pdf";
        ParsePDF parse = new ParsePDF();
        String content;
        try {
            content = parse.getFileContent(file);

            logger.info("content=" + content);

        } catch (SdongException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testGetFileContent_English() {
        String file = "input/pdf/arxiv_1812.01158.pdf";
        // String file = "input/pdf/arxiv_1807.00515.pdf";
        ParsePDF parse = new ParsePDF();
        String content;
        try {
            content = parse.getFileContent(file);
            Paper paper = parse.extractPDF(content);
            // logger.info("content=" + content);
        } catch (SdongException e) {
            logger.error(e.getMessage(), e);
        }

    }

    @Test
    public void testGetFileContent_pdfbox() {
        String file = "input/pdf/arxiv_1812.01158.pdf";
        try {
            PDDocument document = PDDocument.load(new File(file));
            document.getClass();

            if (!document.isEncrypted()) {

                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText;

                pdfFileInText = tStripper.getText(document);

                // System.out.println("Text:" + st);

                // split by whitespace
                String lines[] = pdfFileInText.split("\\r?\\n");
                System.out.println(lines.length);
                for (String line : lines) {
                    System.out.println(line);
                }

                Writer output = new PrintWriter("./output/pdf.html", "utf-8");
                new PDFDomTree().writeText(document, output);

                output.close();

            }
            document.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}