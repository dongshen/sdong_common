package sdong.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sdong.common.CommonConstants;
import sdong.common.exception.SdongException;

public class StringUtil {
	private static final Logger log = LogManager.getLogger(StringUtil.class);

	public static final List<String> splitStringToListByLineBreak(String str) throws SdongException {
		List<String> list = new ArrayList<String>();
		try (BufferedReader reader = new BufferedReader(new StringReader(str));) {

			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				list.add(line);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}
		return list;
	}

	public static final String joinStringListToStringByLineBreak(List<String> list) {
		return joinStringListToString(list, CommonConstants.LINE_BREAK_CRLF);
	}

	public static final String joinStringListToString(List<String> list, String split) {
		StringBuffer bf = new StringBuffer();
		for (String line : list) {
			bf.append(line).append(split);
		}

		return bf.toString();
	}

	public static final int checkIndentNum(String line, String checkChar) {
		int indented = 0;
		while (line.startsWith(checkChar)) {
			indented = indented + 1;
			line = line.substring(1);
		}

		return indented;
	}

	/**
	 * Get next line in string
	 *
	 * @param contents input contents
	 * @param start    start position
	 * @return result
	 */
	public static String getNextLineInString(String contents, int start) {
		String result = "";
		int curLineBreak = contents.indexOf("\n", start);
		if (curLineBreak < 0) {
			return result;
		}
		int nextLineBreak = contents.indexOf("\n", curLineBreak + 1);
		if (nextLineBreak < 0) {
			return result;
		}
		return contents.substring(curLineBreak + 1, nextLineBreak);
	}

	/**
	 * Get current line in string
	 *
	 * @param contents input contents
	 * @param start    start position
	 * @return result
	 */
	public static String getCurrentLineInString(String contents, int start) {
		String result = "";
		int curLineBreak = contents.indexOf("\n", start);
		if (curLineBreak < 0) {
			return result;
		}
		int pretLineBreak = contents.lastIndexOf("\n", start);
		if (pretLineBreak < 0) {
			return result;
		}
		return contents.substring(pretLineBreak + 1, curLineBreak);
	}

	/**
	 * remove the line break in begining and end of string.
	 *
	 * @param line input string
	 * @return result
	 */
	public static String removeStarAndEndBlankLine(String line) {
		if (line == null || line.isEmpty()) {
			return "";
		}

		String result = line.trim();
		// remove start line break;
		boolean change = false;
		do {
			change = false;
			if (result.indexOf(CommonConstants.LINE_BREAK_CRLF) == 0) {
				result = result.substring(CommonConstants.LINE_BREAK_CRLF.length());
				change = true;
			} else if (result.indexOf(CommonConstants.LINE_BREAK_LF) == 0) {
				result = result.substring(CommonConstants.LINE_BREAK_LF.length());
				change = true;
			} else if (result.indexOf(CommonConstants.TAB) == 0) {
				result = result.substring(CommonConstants.TAB.length());
				change = true;
			}
			result = result.trim();
		} while (change);

		if (result.isEmpty()) {
			return result;
		}

		// remove end line break;
		do {
			change = false;
			if (result.lastIndexOf(CommonConstants.LINE_BREAK_CRLF) == result.length()
					- CommonConstants.LINE_BREAK_CRLF.length()) {
				result = result.substring(0, result.length() - CommonConstants.LINE_BREAK_CRLF.length());
				change = true;
			} else if (result.lastIndexOf(CommonConstants.LINE_BREAK_LF) == result.length()
					- CommonConstants.LINE_BREAK_LF.length()) {
				result = result.substring(0, result.length() - CommonConstants.LINE_BREAK_LF.length());
				change = true;
			} else if (result.lastIndexOf(CommonConstants.TAB) == result.length() - CommonConstants.TAB.length()) {
				result = result.substring(0, result.length() - CommonConstants.TAB.length());
				change = true;
			}
			result = result.trim();
		} while (change);
		return result;
	}
}
