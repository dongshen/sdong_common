package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.fit.pdfdom.PDFDomTree;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.CommonConstants;
import sdong.common.bean.thesis.Paper;
import sdong.common.exception.SdongException;
import sdong.common.parse.pdf.PdfParser;

public class PdfUtilTest {
    private static final Logger LOG = LoggerFactory.getLogger(PdfUtilTest.class);

    @Test
    public void testGetFileContent_English() {
        // String file = "input/pdf/arxiv_1812.01158.pdf";
        String file = "input/pdf/arxiv_1807.00515.pdf";
        PdfUtil parse = new PdfUtil();
        String content;
        try {
            content = parse.getFileContent(file);
            // logger.info("content=" + content);
        } catch (SdongException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Test
    public void testGetFileContent_Chinese() {
        String file = "input/pdf/Chinese_pdf.pdf";
        PdfUtil parse = new PdfUtil();
        String content;
        try {
            content = parse.getFileContent(file);

            // LOG.info("content={}" , content);

        } catch (SdongException e) {
            LOG.error(e.getMessage(), e);
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
                LOG.info("Totallines:{}", lines.length);
                for (int ind = 0; ind < lines.length; ind++) {
                    LOG.info("line:{}:{}", ind, lines[ind]);
                }

                Writer output = new PrintWriter("./output/pdf.html", "utf-8");
                new PDFDomTree().writeText(document, output);

                output.close();

            }
            document.close();
        } catch (IOException | ParserConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Test
    public void testCheckFooter() {
        String file = "input/pdf/arxiv_1807.00515.pdf";
        PdfParser parser = new PdfParser(file);
        try {
            List<String> contents = parser.getPdfContents();

            List<String> footers = PdfUtil.checkFooter(contents);
            for (String footer : footers) {
                LOG.info("footer:{}", footer);
            }
            assertEquals(1, footers.size());
            assertEquals("ACM Computing Surveys, Vol. online, No. , Article , Publication date: June 2017.", footers.get(0));
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("Should Not get exception!");
        }
    }

    @Test
    public void testGetMoreDetail() {
        String input = "[1] R. Abraham and M. Erwig. “Goal-Directed Debugging of Spreadsheets”. In: Pro-"
                + CommonConstants.LINE_BREAK + CommonConstants.LINE_BREAK
                + "ceedings of the 2005 IEEE Symposium on Visual Languages and Human-Centric"
                + CommonConstants.LINE_BREAK + "Computing. 2005, pp. 37–44.";
        String expected = "[1] R. Abraham and M. Erwig. “Goal-Directed Debugging of Spreadsheets”. In: Proceedings of the 2005 IEEE Symposium on Visual Languages and Human-Centric Computing. 2005, pp. 37–44.";
        try {
            assertEquals(expected, PdfUtil.getMoreDetail(input));
        } catch (SdongException e) {
            LOG.error(e.getMessage(), e);
            fail("Should not get exception!");
        }
    }

    @Test
    public void testGetMoreDetail2() {
        String input = "[14] R. Bodik and B. Jobstmann. “Algorithmic Program Synthesis: Introduction”. In:"
                + CommonConstants.LINE_BREAK
                + "International journal on software tools for technology transfer 15.5 (2013), pp. 397–"
                + CommonConstants.LINE_BREAK + "411.";
        String expected = "[14] R. Bodik and B. Jobstmann. “Algorithmic Program Synthesis: Introduction”. In: International journal on software tools for technology transfer 15.5 (2013), pp. 397–411.";
        try {
            assertEquals(expected, PdfUtil.getMoreDetail(input));
        } catch (SdongException e) {
            LOG.error(e.getMessage(), e);
            fail("Should not get exception!");
        }
    }

    @Test
    public void testGetMoreDetail3() {
        String input = "[15] M. Brodie, S. Ma, G. Lohman, L. Mignet, M. Wilding, J. Champlin, and P. Sohn."
                + CommonConstants.LINE_BREAK
                + "“Quickly Finding Known Software Problems via Automated Symptom Matching”."
                + CommonConstants.LINE_BREAK + CommonConstants.LINE_BREAK
                + "ACM Computing Surveys, Vol. online, No. , Article , Publication date: June 2017."
                + CommonConstants.LINE_BREAK + CommonConstants.LINE_BREAK + CommonConstants.LINE_BREAK
                + CommonConstants.LINE_BREAK + "REFERENCES :17" + CommonConstants.LINE_BREAK
                + CommonConstants.LINE_BREAK
                + "In: Proceedings of the International Conference on Autonomic Computing. 2005,"
                + CommonConstants.LINE_BREAK + "pp. 101–110.";
        String expected = "[15] M. Brodie, S. Ma, G. Lohman, L. Mignet, M. Wilding, J. Champlin, and P. Sohn. “Quickly Finding Known Software Problems via Automated Symptom Matching”. In: Proceedings of the International Conference on Autonomic Computing. 2005, pp. 101–110.";
        try {
            assertEquals(expected, PdfUtil.getMoreDetail(input));
        } catch (SdongException e) {
            LOG.error(e.getMessage(), e);
            fail("Should not get exception!");
        }
    }
}