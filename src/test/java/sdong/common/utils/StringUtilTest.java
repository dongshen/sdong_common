package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sdong.common.CommonConstants;
import sdong.common.exception.SdongException;

public class StringUtilTest {
	private static final Logger LOG = LogManager.getLogger(StringUtilTest.class);

	@Test
	public void testCheckIndentNum() {
		String line = "tree = TF_func:";
		String checkChar = " ";
		int intended = StringUtil.checkIndentNum(line, checkChar);
		assertEquals(0, intended);

		line = "  f = Function:";
		intended = StringUtil.checkIndentNum(line, checkChar);
		assertEquals(2, intended);

		line = "        return = scalar_type_t:";
		intended = StringUtil.checkIndentNum(line, checkChar);
		assertEquals(8, intended);

		line = "          kind = void";
		intended = StringUtil.checkIndentNum(line, checkChar);
		assertEquals(10, intended);
	}

	@Test
	public void testSplitStringToListByLineBreak() {
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("");
		list.add("line1");
		list.add("");
		list.add("line2");
		list.add("");
		list.add("");
		list.add("line3");
		list.add("");
		list.add("");
		try {
			String str = StringUtil.joinStringListToStringByLineBreak(list);
			List<String> result = StringUtil.splitStringToListByLineBreak(str);
			assertEquals(list.size(), result.size());
		} catch (SdongException e) {
			LOG.error(e.getMessage());
			fail("should not get exception!");
		}
	}

	@Test
	public void testGetCurrentLineInString() {
		String lines = "line 1\r\nline 2\r\nline 3\r\nline 4\r\nline 5\r\n";
		int start = lines.indexOf("3");
		String cur = StringUtil.getCurrentLineInString(lines, start);
		assertEquals("line 3", cur.trim());
	}

	@Test
	public void testGetNextLineInString() {
		String lines = "line 1\r\nline 2\r\nline 3\r\nline 4\r\nline 5\r\n";
		int start = lines.indexOf("3");
		String cur = StringUtil.getNextLineInString(lines, start);
		assertEquals("line 4", cur.trim());
	}

	@Test
	public void testRemoveStarAndEndBlankLine() {
		String lines = "\r\n   \r\n\r\n  line 1\r\n\r\n  line 2\r\nline 3\r\n\r\n\r\nline 4\r\n\r\nline 5\r\n   \r\n";
        LOG.info("org:{}",lines);
		String cur = StringUtil.removeStarAndEndBlankLine(lines);
        LOG.info("cur:{}",cur);
		assertEquals("  line 1\r\n\r\n  line 2\r\nline 3\r\n\r\nline 4\r\n\r\nline 5\r\n",cur);
	}

    @Test
    public void testRemoveHtmlMark(){
        StringBuilder sb = new StringBuilder();
        sb.append("<p>").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("<b>Scenario</b><br/>").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("1. A user is tricked into visiting the malicious URL: <code>http://website.com/login?redirect=http://evil.vvebsite.com/fake/login</code><br/>").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("2. The user is redirected to a fake login page that looks like a site they trust. (<code>http://evil.vvebsite.com/fake/login</code>)<br/>").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("3. The user enters his credentials.<br/>").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("4. The evil site steals the user's credentials and redirects him to the original website.<br/>").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("<br/>").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("This attack is plausible because most users don't double check the URL after the redirection. Also, redirection to").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("an authentication page is very common.").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("</p>").append(CommonConstants.LINE_BREAK_CRLF);
    
        String org = sb.toString();
        LOG.info("Org:{}",org);
        LOG.info("cur:{}",StringUtil.removeHtmlMark(org));
    }
}
