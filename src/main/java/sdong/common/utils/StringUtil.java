package sdong.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	public static String LINE_BREAK = "\r\n";

	public static final int checkIndentNum(String line, String checkChar) {
		int indented = 0;
		while (line.startsWith(checkChar)) {
			indented = indented + 1;
			line = line.substring(1);
		}

		return indented;
	}

	public static final List<String> splitStringToListByLineBreak(String str) {
		BufferedReader reader = null;
		List<String> list = new ArrayList<String>();
		try {
			reader = new BufferedReader(new StringReader(str));
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				list.add(line);
			}
			reader.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return list;
	}

	public static final String joinStringListToStringByLineBreak(List<String> list) {
		StringBuffer bf = new StringBuffer();
		for (String line : list) {
			bf.append(line).append(LINE_BREAK);
		}

		return bf.toString();
	}

	public static int convertStringToInt(String input) {
		int result = 0;
		if (input == null) {
			return result;
		}
		try {
			result = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
		}

		return result;
	}
	
	public static boolean convertStringToBoolean(String in) {
		boolean result = false;

		if (in == null) {
			return result;
		}

		if (in.trim().equalsIgnoreCase("yes")) {
			result = true;
		}

		return result;
	}
}
