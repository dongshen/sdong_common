package sdong.common.utils;

public class StringUtil {

	public static final int checkIndentNum(String line, String checkChar) {
		int indented = 0;
		while (line.startsWith(checkChar)) {
			indented = indented + 1;
			line = line.substring(1);
		}

		return indented;
	}
}
