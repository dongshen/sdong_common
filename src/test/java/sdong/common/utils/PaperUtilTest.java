package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.fit.pdfdom.PDFDomTree;
import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sdong.common.CommonConstants;
import sdong.common.exception.SdongException;
import sdong.common.parse.pdf.PdfParser;

public class PaperUtilTest {
    private static final Logger LOG = LogManager.getLogger(PaperUtilTest.class);

    @Test
    public void testGetFileContent_English() {
        // String file = "input/pdf/arxiv_1812.01158.pdf";
        String file = "input/pdf/arxiv_1807.00515.pdf";
        PaperUtil parse = new PaperUtil();
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
        PaperUtil parse = new PaperUtil();
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
    public void testgetFooter() {
        String file = "input/pdf/arxiv_1807.00515.pdf";
        PdfParser parser = new PdfParser(file);
        try {
            List<String> contents = parser.getPdfContents();

            List<String> footers = PaperUtil.getFooter(contents);
            for (String footer : footers) {
                LOG.info("footer:{}", footer);
            }
            assertEquals(1, footers.size());
            assertEquals("ACM Computing Surveys, Vol. online, No. , Article , Publication date: June 2017.",
                    footers.get(0));
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("Should Not get exception!");
        }
    }

    @Test
    public void testGetMoreDetail() {
        String input = "[1] R. Abraham and M. Erwig. “Goal-Directed Debugging of Spreadsheets”. In: Pro-"
                + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF
                + "ceedings of the 2005 IEEE Symposium on Visual Languages and Human-Centric"
                + CommonConstants.LINE_BREAK_CRLF + "Computing. 2005, pp. 37–44.";
        String expected = "[1] R. Abraham and M. Erwig. “Goal-Directed Debugging of Spreadsheets”. In: Proceedings of the 2005 IEEE Symposium on Visual Languages and Human-Centric Computing. 2005, pp. 37–44.";
        try {
            assertEquals(expected, PaperUtil.getMoreDetail(input, new ArrayList<String>()));
        } catch (SdongException e) {
            LOG.error(e.getMessage(), e);
            fail("Should not get exception!");
        }
    }

    @Test
    public void testGetMoreDetail2() {
        String input = "[14] R. Bodik and B. Jobstmann. “Algorithmic Program Synthesis: Introduction”. In:"
                + CommonConstants.LINE_BREAK_CRLF
                + "International journal on software tools for technology transfer 15.5 (2013), pp. 397–"
                + CommonConstants.LINE_BREAK_CRLF + "411.";
        String expected = "[14] R. Bodik and B. Jobstmann. “Algorithmic Program Synthesis: Introduction”. In: International journal on software tools for technology transfer 15.5 (2013), pp. 397–411.";
        try {
            assertEquals(expected, PaperUtil.getMoreDetail(input, new ArrayList<String>()));
        } catch (SdongException e) {
            LOG.error(e.getMessage(), e);
            fail("Should not get exception!");
        }
    }

    @Test
    public void testGetMoreDetail3() {
        String input = "[15] M. Brodie, S. Ma, G. Lohman, L. Mignet, M. Wilding, J. Champlin, and P. Sohn."
                + CommonConstants.LINE_BREAK_CRLF
                + "“Quickly Finding Known Software Problems via Automated Symptom Matching”."
                + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF
                + "ACM Computing Surveys, Vol. online, No. , Article , Publication date: June 2017."
                + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF
                + CommonConstants.LINE_BREAK_CRLF + "REFERENCES :17" + CommonConstants.LINE_BREAK_CRLF
                + CommonConstants.LINE_BREAK_CRLF
                + "In: Proceedings of the International Conference on Autonomic Computing. 2005,"
                + CommonConstants.LINE_BREAK_CRLF + "pp. 101–110.";
        String expected = "[15] M. Brodie, S. Ma, G. Lohman, L. Mignet, M. Wilding, J. Champlin, and P. Sohn. “Quickly Finding Known Software Problems via Automated Symptom Matching”. In: Proceedings of the International Conference on Autonomic Computing. 2005, pp. 101–110.";
        try {
            assertEquals(expected, PaperUtil.getMoreDetail(input,
                    Arrays.asList("ACM Computing Surveys, Vol. online, No. , Article , Publication date: June 2017.")));
        } catch (SdongException e) {
            LOG.error(e.getMessage(), e);
            fail("Should not get exception!");
        }
    }

    @Test
    public void testGetMoreDetail4() {
        String input = "[4] https://github.com/zom/Zom-Android/blob/master/app/src/main/java/"
                + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF
                + "org/awesomeapp/messenger/util/SecureMediaStore.java#L125, accessed" + CommonConstants.LINE_BREAK_CRLF
                + "in August 2018." + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF + "10"
                + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF
                + "https://github.com/cheng2016/LoginSdk/blob/master/src/com/example/loginsdk/util/Util.java#L323"
                + CommonConstants.LINE_BREAK_CRLF
                + "https://github.com/cheng2016/LoginSdk/blob/master/src/com/example/loginsdk/util/Util.java#L323"
                + CommonConstants.LINE_BREAK_CRLF
                + "https://github.com/zom/Zom-Android/blob/master/app/src/main/java/org/awesomeapp/messenger/util/SecureMediaStore.java#L125"
                + CommonConstants.LINE_BREAK_CRLF
                + "https://github.com/zom/Zom-Android/blob/master/app/src/main/java/org/awesomeapp/messenger/util/SecureMediaStore.java#L125";
        String expected = "[4] https://github.com/zom/Zom-Android/blob/master/app/src/main/java/org/awesomeapp/messenger/util/SecureMediaStore.java#L125, accessed in August 2018.";
        try {
            assertEquals(expected, PaperUtil.getMoreDetail(input, new ArrayList<String>()));
        } catch (SdongException e) {
            LOG.error(e.getMessage(), e);
            fail("Should not get exception!");
        }
    }

    @Test
    public void testGetMoreDetail5() {
        String input = "[91] C. Le Goues, S. Forrest, and W. Weimer. “Current Challenges in Automatic Software"
                + CommonConstants.LINE_BREAK_CRLF + "Repair”. In: Software quality journal 21.3 (2013), pp. 421–443."
                + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF
                + "ACM Computing Surveys, Vol. online, No. , Article , Publication date: June 2017."
                + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF
                + "http://www.computerworld.com/article/2515483/enterprise-applications/epic-failures--11-infamous-software-bugs.html"
                + CommonConstants.LINE_BREAK_CRLF
                + "http://www.computerworld.com/article/2515483/enterprise-applications/epic-failures--11-infamous-software-bugs.html"
                + CommonConstants.LINE_BREAK_CRLF
                + "http://www.computerworld.com/article/2515483/enterprise-applications/epic-failures--11-infamous-software-bugs.html"
                + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF
                + "REFERENCES :21" + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF;
        String expected = "[91] C. Le Goues, S. Forrest, and W. Weimer. “Current Challenges in Automatic Software Repair”. In: Software quality journal 21.3 (2013), pp. 421–443.";
        try {
            assertEquals(expected, PaperUtil.getMoreDetail(input,
                    Arrays.asList("ACM Computing Surveys, Vol. online, No. , Article , Publication date: June 2017.")));
        } catch (SdongException e) {
            LOG.error(e.getMessage(), e);
            fail("Should not get exception!");
        }
    }

    @Test
    public void testGetReferenceblocks() {
        String input = CommonConstants.LINE_BREAK_CRLF
                + "[91] C. Le Goues, S. Forrest, and W. Weimer. “Current Challenges in Automatic Software"
                + CommonConstants.LINE_BREAK_CRLF + "Repair”. In: Software quality journal 21.3 (2013), pp. 421–443."
                + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF
                + "ACM Computing Surveys, Vol. online, No. , Article , Publication date: June 2017."
                + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF
                + "http://www.computerworld.com/article/2515483/enterprise-applications/epic-failures--11-infamous-software-bugs.html"
                + CommonConstants.LINE_BREAK_CRLF
                + "http://www.computerworld.com/article/2515483/enterprise-applications/epic-failures--11-infamous-software-bugs.html"
                + CommonConstants.LINE_BREAK_CRLF
                + "http://www.computerworld.com/article/2515483/enterprise-applications/epic-failures--11-infamous-software-bugs.html"
                + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF
                + "REFERENCES :21"+ CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF ;
        try {
            List<String> details = StringUtil.splitStringToListByLineBreak(input);

            List<String> list = PaperUtil.getReferenceblocks(details);
            for (String line : list) {
                LOG.info("block:{}", line);
            }
            assertEquals(4, list.size());
        } catch (SdongException e) {
            LOG.error(e.getMessage(), e);
            fail("Should not get exception!");
        }
    }
}