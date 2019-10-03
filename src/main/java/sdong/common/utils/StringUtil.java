package sdong.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		StringBuffer bf = new StringBuffer();
		for (String line : list) {
			bf.append(line).append(LINE_BREAK);
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

	public static int convertStringToInt(String input) throws SdongException {
		int result = 0;
		if (input == null) {
			return result;
		}
		try {
			result = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}

		return result;
	}

	public static boolean convertStringToBoolean(String in) {
		boolean result = false;

		if (in == null) {
			return result;
		}

		if (in.trim().equalsIgnoreCase("yes") || in.trim().equalsIgnoreCase("true")) {
			result = true;
		}
		return result;
	}
}
