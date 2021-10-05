package sdong.common.parse.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sdong.common.bean.thesis.Paper;
import sdong.common.exception.SdongException;
import sdong.common.utils.PaperUtil;

public class PdfParserTest {
	private static final Logger LOG = LogManager.getLogger(PdfParserTest.class);

	@Test
	public void testGetPdfContents() {
		fail("Not yet implemented");
	}

	@Test
	public void testParsePdfContents() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckIndex() {
		// String file = "input/pdf/arxiv_1812.01158.pdf";
		String file = "input/pdf/arxiv_1807.00515.pdf";
		PdfParser parser = new PdfParser(file);
		try {
			parser.getPdfContents();
			assertEquals(true, parser.checkIndex());
			for (String line : parser.getIndexList()) {
				LOG.info("{}", line);
			}
			assertEquals(30, parser.getIndexList().size());
		} catch (SdongException e) {
			LOG.error(e.getMessage());
			fail("Should Not get exception!");
		}
	}

	@Test
	public void testGetTitle() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSummary() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetKeyWords() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAuthors() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetReferenceList1() {
		String file = "input/pdf/arxiv_1807.00515.pdf";
		PdfParser parser = new PdfParser(file);
		try {
			parser.getPdfContents();
			List<Paper> refs = parser.getReferenceList();
			LOG.info("Get refs:{}", refs.size());
			assertEquals(197, refs.size());

			List<String> footerList = PaperUtil.getFooter(parser.getContenList());
			for (Paper ref : refs) {
				LOG.info("{}", ref.getTitle());
				LOG.info("Update:{}", PaperUtil.getMoreDetail(ref.getTitle(),footerList));				
			}
		} catch (SdongException e) {
			LOG.error(e.getMessage());
			fail("Should Not get exception!");
		}
	}

	@Test
	public void testGetReferenceList2() {
		String file = "input/pdf/arxiv_1812.01158.pdf";
		PdfParser parser = new PdfParser(file);
		try {
			parser.getPdfContents();
			List<Paper> refs = parser.getReferenceList();
			LOG.info("Get refs:{}", refs.size());
			assertEquals(61, refs.size());
			List<String> footerList = PaperUtil.getFooter(parser.getContenList());

			for (Paper ref : refs) {
				LOG.info("{}", ref.getTitle());
				LOG.info("Update:{}", PaperUtil.getMoreDetail(ref.getTitle(),footerList));
			}
		} catch (SdongException e) {
			LOG.error(e.getMessage());
			fail("Should Not get exception!");
		}
	}

	@Test
	public void testGetReferenceList3() {
		String file = "input/pdf/Chinese_pdf.pdf";
		PdfParser parser = new PdfParser(file);
		try {
			parser.getPdfContents();
			List<Paper> refs = parser.getReferenceList();
			LOG.info("Get refs:{}", refs.size());
			assertEquals(77, refs.size());
			List<String> footerList = PaperUtil.getFooter(parser.getContenList());

			for (Paper ref : refs) {
				LOG.info("{}", ref.getTitle());
				LOG.info("Update:{}", PaperUtil.getMoreDetail(ref.getTitle(),footerList));
			}
		} catch (SdongException e) {
			LOG.error(e.getMessage());
			fail("Should Not get exception!");
		}
	}
}
