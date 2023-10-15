package sdong.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import sdong.common.CommonConstants;
import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StringUtilTest {
    private static final Logger LOG = LogManager.getLogger(StringUtilTest.class);

    @Test
    void testCheckIndentNum() {
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
    void testSplitStringToListByLineBreak() {
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
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }

    @Test
    void testGetCurrentLineInString() {
        String lines = "line 1\r\nline 2\r\nline 3\r\nline 4\r\nline 5\r\n";
        int start = lines.indexOf("3");
        String cur = StringUtil.getCurrentLineInString(lines, start);
        assertEquals("line 3", cur.trim());
    }

    @Test
    void testGetNextLineInString() {
        String lines = "line 1\r\nline 2\r\nline 3\r\nline 4\r\nline 5\r\n";
        int start = lines.indexOf("3");
        String cur = StringUtil.getNextLineInString(lines, start);
        assertEquals("line 4", cur.trim());
    }

    @Test
    void testRemoveStarAndEndBlankLine() {
        String lines = "\r\n   \r\n\r\n  line 1\r\r\n  line 2\r\nline 3\r\n\n\r\nline 4\r\n\r\nline 5\r\n   \r\n";
        LOG.info("org:{}", lines);
        String cur = StringUtil.removeStarAndEndBlankLine(lines);
        LOG.info("cur:{}", cur);
        assertEquals("  line 1\r\n\r\n  line 2\r\nline 3\r\n\r\nline 4\r\n\r\nline 5\r\n", cur);
        String[] ls = lines.split(StringUtil.PATTERN_LINEBREAK);
        for (String str : ls) {
            LOG.info("{}", str);
        }
    }

    @Test
    void testRemoveStarAndEndBlankLine2() {
        String lines = "  line 1\r\n";
        LOG.info("org:{}", lines);
        String cur = StringUtil.removeStarAndEndBlankLine(lines);
        LOG.info("cur:{}", cur);
        assertEquals("  line 1", cur);
        String[] ls = lines.split(StringUtil.PATTERN_LINEBREAK);
        for (String str : ls) {
            LOG.info("{}", str);
        }
    }

    @Test
    void testRemoveHtmlMark() {
        StringBuilder sb = new StringBuilder();
        sb.append("<p>").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("<b>Scenario</b><br/>").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append(
                "1. A user is tricked into visiting the malicious URL: <code>http://website.com/login?redirect=http://evil.vvebsite.com/fake/login</code><br/>")
                .append(CommonConstants.LINE_BREAK_CRLF);
        sb.append(
                "2. The user is redirected to a fake login page that looks like a site they trust. (<code>http://evil.vvebsite.com/fake/login</code>)<br/>")
                .append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("3. The user enters his credentials.<br/>").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("4. The evil site steals the user's credentials and redirects him to the original website.<br/>")
                .append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("<br/>").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append(
                "This attack is plausible because most users don't double check the URL after the redirection. Also, redirection to")
                .append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("an authentication page is very common.").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("</p>").append(CommonConstants.LINE_BREAK_CRLF);

        String org = sb.toString();
        LOG.info("Org:{}", org);
        LOG.info("cur:{}", StringUtil.removeHtmlMark(org));
    }

    @Test
    void testRelaceLast() {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "\\012\\012Sample program that can crash:\\012\\012#include <stdio.h>\\012int main()\\012{\\012    char c[5];\\012    scanf(\"%s\", c);\\012    return 0;\\012}\\012\\012Typing in 5 or more characters may make the program crash.");
        String org = sb.toString();
        LOG.info("Org:{}", org);
        LOG.info("cur:{}", StringUtil.replaceLast(org, "\\\\012",
                // ""));
                CommonConstants.LINE_BREAK_CRLF + MdUtil.MARK_MD_CODE_BLOCK + CommonConstants.LINE_BREAK_CRLF));
    }

    @Test
    void testRemoveHtmlMark2() {
        StringBuilder sb = new StringBuilder();
        sb.append("<li class=\"li\">").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append(
                "<p class=\"p\">赋值（例如 <code class=\"ph codeph\">=</code>、<code class=\"ph codeph\">+=</code>、<code class=\"ph codeph\">-=</code> 或 <code class=\"ph codeph\">&lt;&lt;=</code>；）。</p> </li>")
                .append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("<li class=\"li\">").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append(
                "<p class=\"p\">递增和递减（例如 <code class=\"ph codeph\">++</code> 或 <code class=\"ph codeph\">--</code>）。</p> </li>");
        String org = sb.toString();
        org = org.replace("<li class=\"li\">", "<li>");
        org = org.replace("<p class=\"p\">", "<p>");
        org = org.replace("<code class=\"ph codeph\">", "<code>");
        LOG.info("Org:{}", org);
        LOG.info("cur:{}", StringUtil.removeHtmlMark(org));
    }

    @Test
    void testRemoveHtmlMark3() {
        StringBuilder sb = new StringBuilder();
        sb.append("case 1").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("case 1 conitnue.").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("case 2.").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("case 3").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("case 3 end。").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("case 4:").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("- case4 1").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("> case4 2").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("case 5").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("- case5 1").append(CommonConstants.LINE_BREAK_CRLF);
        sb.append("> case5 2").append(CommonConstants.LINE_BREAK_CRLF);
        String org = sb.toString();

        StringBuilder ep = new StringBuilder();
        ep.append("case 1 case 1 conitnue.").append(CommonConstants.LINE_BREAK_CRLF);
        ep.append("case 2.").append(CommonConstants.LINE_BREAK_CRLF);
        ep.append("case 3 case 3 end。").append(CommonConstants.LINE_BREAK_CRLF);
        ep.append("case 4:").append(CommonConstants.LINE_BREAK_CRLF);
        ep.append("- case4 1").append(CommonConstants.LINE_BREAK_CRLF);
        ep.append("> case4 2").append(CommonConstants.LINE_BREAK_CRLF);
        ep.append("case 5").append(CommonConstants.LINE_BREAK_CRLF);
        ep.append("- case5 1").append(CommonConstants.LINE_BREAK_CRLF);
        ep.append("> case5 2").append(CommonConstants.LINE_BREAK_CRLF);

        LOG.info("Org:");
        LOG.info("{}", org);
        LOG.info("result:");
        String result = StringUtil.removeHtmlMark(org, false);
        LOG.info("{}", result);
        assertEquals(true, ep.toString().equals(result));
    }
}
