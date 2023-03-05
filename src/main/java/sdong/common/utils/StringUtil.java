package sdong.common.utils;

import sdong.common.CommonConstants;
import sdong.common.exception.SdongException;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    private static final Logger LOG = LogManager.getLogger(StringUtil.class);

    public static final String PATTERN_LINEBREAK = "\\r?\\n|\\r";

    public static final String MARK_HTML_LINEBREAK = "<br/>";
    public static final String MARK_HTML_LINEBREAK2 = "<br>";
    public static final String MARK_HTML_LINEBREAK3 = "<br />";

    public static final String MARK_HTML_UL_START = "<ul>";
    public static final String MARK_HTML_UL_END = "</ul>";
    public static final String MARK_HTML_OL_START = "<ol>";
    public static final String MARK_HTML_OL_END = "</ol>";
    public static final String MARK_HTML_LI_START = "<li>";
    public static final String MARK_HTML_LI_END = "</li>";
    public static final String MARK_HTML_P_START = "<p>";
    public static final String MARK_HTML_P_END = "</p>";

    public static final String MARK_LINE_END = ".";
    public static final String MARK_LINK_END = ">";

    private static final int MARK_IND_P = 0;
    private static final int MARK_IND_CODE_BLCOK = 1;
    private static final int MARK_IND_LI = 2;
    private static final int MARK_IND_ADD_BLANK = 3;

    private static final String[] LINE_END_MARK = new String[] { "<table>", "</table>", "</th>", "</tr>" };

    public static final List<String> splitStringToListByLineBreak(String str) throws SdongException {
        List<String> list = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new StringReader(str));) {

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                list.add(line);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new SdongException(e.getMessage(), e);
        }
        return list;
    }

    public static final String joinStringListToStringByLineBreak(List<String> list) {
        return joinStringListToString(list, CommonConstants.LINE_BREAK_CRLF);
    }

    public static final String joinStringListToString(List<String> list, String split) {
        StringBuilder bf = new StringBuilder();
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
     * @param value input string
     * @return result
     */
    public static String removeStarAndEndBlankLine(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        String[] lines = value.split(StringUtil.PATTERN_LINEBREAK);
        boolean isStart = true;
        boolean isEmpty = false;
        boolean isEmptyPre = false;

        StringBuilder sb = new StringBuilder();
        String lineValue;
        for (String line : lines) {
            lineValue = line.trim();
            isEmpty = lineValue.isEmpty() ? true : false;

            if (isStart && !isEmpty) {
                sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
                isStart = false;
                continue;
            }

            if (isStart && isEmpty) {
                continue;
            }

            if (isEmpty) {
                if (!isEmptyPre) {
                    isEmptyPre = true;
                }
                continue;
            }

            if (isEmptyPre) {
                sb.append(CommonConstants.LINE_BREAK_CRLF);
                isEmptyPre = false;
            }
            sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
        }

        return sb.toString();
    }

    public static String removeStarAndEndBlankLine2(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        // String result = value.replaceAll("(^[\\s]+|[\\s]+$)", "");
        String result = value.replaceAll("(?m)(^[\\s]{2,}$)", "");
        // result = result.replaceAll("(\r\n)", CommonConstants.LINE_BREAK_CRLF);

        return result;
    }

    /**
     * secape string for java
     * 
     * @param inputStr input string
     * @return output string
     */
    public static String escapeJava(String inputStr) {
        return StringEscapeUtils.escapeJava(inputStr);
    }

    /**
     * remove html mark:
     * <p>
     * <ul>
     * <li>
     * <ol>
     * 
     * @param value input string
     * @return result
     */
    public static String removeHtmlMark(String value) {
        return removeHtmlMark(value, true);
    }

    public static String removeHtmlMark(String value, boolean isIncludeP) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        // 0 - <p> , 1 - <pre> , 2 - li, 3 - add blank;
        boolean[] mark = new boolean[] { false, false, false, false };
        value = value.replace(MARK_HTML_LINEBREAK, CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF);
        value = value.replace(MARK_HTML_LINEBREAK2, CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF);
        value = value.replace(MARK_HTML_LINEBREAK3, CommonConstants.LINE_BREAK_CRLF + CommonConstants.LINE_BREAK_CRLF);
        value = value.replace(MARK_HTML_P_START,
                CommonConstants.LINE_BREAK_CRLF + MARK_HTML_P_START + CommonConstants.LINE_BREAK_CRLF);
        value = value.replace(MARK_HTML_P_END,
                CommonConstants.LINE_BREAK_CRLF + MARK_HTML_P_END + CommonConstants.LINE_BREAK_CRLF);
        value = value.replace(MARK_HTML_LI_START,
                CommonConstants.LINE_BREAK_CRLF + MARK_HTML_LI_START + CommonConstants.LINE_BREAK_CRLF);
        value = value.replace(MARK_HTML_LI_END,
                CommonConstants.LINE_BREAK_CRLF + MARK_HTML_LI_END + CommonConstants.LINE_BREAK_CRLF);

        String[] lines = value.split(StringUtil.PATTERN_LINEBREAK);
        for (String line : lines) {
            processLine(line, sb, mark, isIncludeP);
        }

        return sb.toString();
    }

    private static void processLine(String line, StringBuilder sb, boolean[] mark, boolean isIncludeP) {
        String lineValue = line.trim();
        if (isIncludeP) {
            if (extractHtmlTagP(line, sb, mark)) {
                return;
            }
        } else {
            mark[MARK_IND_P] = true;
        }

        // code block
        if (extractPre(line, sb, mark)) {
            return;
        }

        // UI
        if (extractUlAndLi(line, sb, mark)) {
            return;
        }

        if (mark[MARK_IND_P]) {
            if (lineValue.isEmpty()) {
                mark[MARK_IND_ADD_BLANK] = false;
                sb.append(CommonConstants.LINE_BREAK_CRLF).append(CommonConstants.LINE_BREAK_CRLF);
                return;
            }

            lineValue = replaceScript(lineValue);
            if (lineValue.startsWith(">")) {
                sb.append(lineValue).append(CommonConstants.LINE_BREAK_CRLF);
                mark[MARK_IND_ADD_BLANK] = false;
                return;
            }

            if (mark[MARK_IND_ADD_BLANK]) {
                sb.append(" ");
            }
            if (checkLineEnd(lineValue)) {
                sb.append(lineValue).append(CommonConstants.LINE_BREAK_CRLF);
                mark[MARK_IND_ADD_BLANK] = false;
                return;
            }

            sb.append(lineValue);
            mark[MARK_IND_ADD_BLANK] = true;

            return;
        }
        sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
    }

    private static boolean extractHtmlTagP(String line, StringBuilder sb, boolean[] mark) {
        String lineValue = line.trim();
        if (lineValue.startsWith(MARK_HTML_P_START) && lineValue.endsWith(MARK_HTML_P_END)) {
            line = lineValue.replace(MARK_HTML_P_START, "").replace(MARK_HTML_P_END, "").trim();
            line = replaceScript(line);
            if (mark[MARK_IND_LI]) {
                sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
            } else {
                sb.append(CommonConstants.LINE_BREAK_CRLF).append(line).append(CommonConstants.LINE_BREAK_CRLF);
            }
            mark[MARK_IND_ADD_BLANK] = false;
            return true;
        }
        if (lineValue.startsWith(MARK_HTML_P_START) && checkLineEnd(lineValue)) {
            lineValue = lineValue.replace(MARK_HTML_P_START, "").trim();
            lineValue = replaceScript(lineValue);
            if (mark[MARK_IND_LI] && !lineValue.isEmpty()) {
                sb.append(" ").append(lineValue);
            } else if (!lineValue.isEmpty()) {
                sb.append(CommonConstants.LINE_BREAK_CRLF).append(lineValue).append(CommonConstants.LINE_BREAK_CRLF);
            } else {
                sb.append(CommonConstants.LINE_BREAK_CRLF);
            }
            mark[MARK_IND_ADD_BLANK] = false;
            mark[MARK_IND_P] = true;
            return true;
        }
        if (lineValue.startsWith(MARK_HTML_P_START)) {
            line = lineValue.replace(MARK_HTML_P_START, "").trim();
            line = replaceScript(line);
            if (!line.isEmpty()) {
                if (mark[MARK_IND_LI]) {
                    sb.append(line);
                } else {
                    sb.append(CommonConstants.LINE_BREAK_CRLF).append(line);
                }
                mark[MARK_IND_ADD_BLANK] = true;
            } else {
                mark[MARK_IND_ADD_BLANK] = false;
            }

            mark[MARK_IND_P] = true;
            return true;
        }

        if (lineValue.endsWith(MARK_HTML_P_END)) {
            line = lineValue.replace(MARK_HTML_P_END, "");
            line = replaceScript(line);
            if (mark[MARK_IND_ADD_BLANK]) {
                sb.append(" ");
            }
            sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);

            mark[MARK_IND_P] = false;
            mark[MARK_IND_ADD_BLANK] = false;
            return true;
        }

        return false;
    }

    private static String replaceScript(String value) {
        return value.replace("<script>", "\\<script>").replace("</script>", "\\</script>");
    }

    private static boolean extractPre(String line, StringBuilder sb, boolean[] mark) {
        String lineValue = line.trim();
        if (lineValue.startsWith(MdUtil.MARK_MD_CODE_BLOCK)) {
            mark[MARK_IND_CODE_BLCOK] = !mark[MARK_IND_CODE_BLCOK];
            if (!mark[MARK_IND_CODE_BLCOK]) {
                sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
            } else {
                sb.append(CommonConstants.LINE_BREAK_CRLF).append(CommonConstants.LINE_BREAK_CRLF).append(line)
                        .append(CommonConstants.LINE_BREAK_CRLF);
            }
            mark[MARK_IND_ADD_BLANK] = false;
            return true;
        }

        if (mark[MARK_IND_CODE_BLCOK]) {
            sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
            mark[MARK_IND_ADD_BLANK] = false;
            return true;
        }
        return false;
    }

    private static boolean extractUlAndLi(String line, StringBuilder sb, boolean[] mark) {
        // UI
        if ((line.contains(MARK_HTML_UL_START) || line.contains(MARK_HTML_UL_END))
                || (line.contains(MARK_HTML_OL_START) || line.contains(MARK_HTML_OL_END))) {
            sb.append(CommonConstants.LINE_BREAK_CRLF);
            return true;
        }

        // LI
        if (line.contains(MARK_HTML_LI_START) && line.contains(MARK_HTML_LI_END)) {
            line = line.trim().replace(MARK_HTML_LI_START, "  * ").replace(MARK_HTML_LI_END, "");
            sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
            mark[MARK_IND_ADD_BLANK] = false;
            return true;
        }

        if (line.contains(MARK_HTML_LI_START)) {
            line = line.trim().replace(MARK_HTML_LI_START, "  * ");
            mark[MARK_IND_LI] = true;
            sb.append(CommonConstants.LINE_BREAK_CRLF).append(line);
            if (line.equals("  * ")) {
                mark[MARK_IND_ADD_BLANK] = false;
            } else {
                mark[MARK_IND_ADD_BLANK] = true;
            }

            return true;
        }

        if (mark[MARK_IND_LI] || line.contains(MARK_HTML_LI_END)) {
            if (line.contains(MARK_HTML_LI_END)) {
                mark[MARK_IND_LI] = false;
            }
            line = line.trim().replace(MARK_HTML_LI_END, "");

            if (!line.isEmpty()) {
                if (mark[MARK_IND_ADD_BLANK]) {
                    sb.append(" ");
                }
                sb.append(line);
            }
            if (!mark[MARK_IND_LI]) {
                sb.append(CommonConstants.LINE_BREAK_CRLF);
                mark[MARK_IND_ADD_BLANK] = false;
            }
            return true;
        }
        return false;
    }

    public static String removeHtmlUl(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String mark = "";

        String[] lines = value.split(StringUtil.PATTERN_LINEBREAK);
        for (String line : lines) {
            if (line.contains(MARK_HTML_UL_START) || line.contains(MARK_HTML_OL_START)) {
                mark = MARK_HTML_UL_START;
                line = line.replace(MARK_HTML_UL_START, CommonConstants.LINE_BREAK_CRLF)
                        .replace(MARK_HTML_OL_START, CommonConstants.LINE_BREAK_CRLF).trim();
                if (!line.isEmpty()) {
                    sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
                }
                continue;
            } else if (line.contains(MARK_HTML_UL_END) || line.contains(MARK_HTML_OL_END)) {
                mark = "";
                line = line.replace(MARK_HTML_UL_END, "").replace(MARK_HTML_OL_END, "").trim();
                if (!line.isEmpty()) {
                    sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
                }
                continue;
            }

            if (MARK_HTML_UL_START.equals(mark)) {
                line = line.trim();
                if (line.startsWith(MARK_HTML_LI_START)) {
                    line = line.replace(MARK_HTML_LI_START, "  * ").replace(MARK_HTML_LI_END, "");
                }
                sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
                continue;
            }

            sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
        }

        return sb.toString();
    }

    /**
     * generate required number of string;
     * 
     * @param str required string
     * @param num required number
     * @return result
     */
    public static String getNumbersOfString(String str, int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(str);
        }

        return sb.toString();
    }

    /**
     * replace last
     * 
     * @param text            input string
     * @param strToReplace    to replace
     * @param replaceWithThis replace with
     * @return result
     */
    public static String replaceLast(String text, String strToReplace, String replaceWithThis) {
        return text.replaceFirst(strToReplace + "(?!.*?" + strToReplace + ")", replaceWithThis);
    }

    private static boolean checkLineEnd(String line) {
        if (line == null || line.isEmpty()) {
            return false;
        }
        for (String mark : LINE_END_MARK) {
            if (line.endsWith(mark)) {
                return true;
            }
        }
        return false;
    }
}
