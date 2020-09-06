package sdong.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.CommonConstants;
import sdong.common.exception.SdongException;

public class StringUtil {
	private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

	public static String LINE_BREAK = "\r\n";

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
		return joinStringListToString(list, LINE_BREAK);
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
	 * @param start start position
	 * @return result
	 */
	public static String getNextLineInString(String contents, int start) {
		String result="";
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
	 * @param start start position
	 * @return result
	 */
	public static String getCurrentLineInString(String contents, int start) {
		String result="";
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
        String result = line;
        // remove start line break;
        while (result.indexOf(CommonConstants.LINE_BREAK) == 0) {
            result = result.substring(2);
        }
        // remove end line break;
        while (result.lastIndexOf(CommonConstants.LINE_BREAK) == result.length() - 2) {
            result = result.substring(0, result.length()-2);
        }
        return result;
    }
}
