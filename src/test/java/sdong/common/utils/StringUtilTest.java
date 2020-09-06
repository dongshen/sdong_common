package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class StringUtilTest {
	private static final Logger log = LoggerFactory.getLogger(StringUtilTest.class);

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
			log.error(e.getMessage());
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
		String lines = "\r\n\r\nline 1\r\n\r\nline 2\r\nline 3\r\n\r\nline 4\r\nline 5\r\n\r\n";
		String cur = StringUtil.removeStarAndEndBlankLine(lines);
		assertEquals("line 1\r\n\r\nline 2\r\nline 3\r\n\r\nline 4\r\nline 5", cur.trim());
	}
}
