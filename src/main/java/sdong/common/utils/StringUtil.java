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
    private static final Logger log = LogManager.getLogger(StringUtil.class);

    public static final String PATTERN_LINEBREAK = "\\r?\\n|\\r";

    public static final String MARK_HTML_LINEBREAK = "<br/>";
    public static final String MARK_HTML_LINEBREAK2 = "<br>";

    public static final String MARK_HTML_UL_START = "<ul>";
    public static final String MARK_HTML_UL_END = "</ul>";
    public static final String MARK_HTML_OL_START = "<ol>";
    public static final String MARK_HTML_OL_END = "</ol>";
    public static final String MARK_HTML_LI_START = "<li>";
    public static final String MARK_HTML_LI_END = "</li>";

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

    public static String removeHtmlMark(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String pStart = "<p>";
        String pEnd = "</p>";
        String lineEnd = ".";
        String linkEnd = ">";
        String mark = "";
        boolean isCodeBlock = false;
        boolean isLi = false;
        String strBlank = "";

        String[] lines = value.split(StringUtil.PATTERN_LINEBREAK);
        String lineValue;
        for (String line : lines) {
            lineValue = line.trim();
            if (lineValue.startsWith(pStart) && lineValue.endsWith(pEnd)) {
                sb.append(lineValue.replace(pStart, CommonConstants.LINE_BREAK_CRLF).replace(pEnd, ""))
                        .append(CommonConstants.LINE_BREAK_CRLF);
                strBlank = "";
                continue;
            }
            if (lineValue.startsWith(pStart) && (lineValue.endsWith(lineEnd) || lineValue.endsWith(linkEnd))) {
                sb.append(lineValue.replace(pStart, CommonConstants.LINE_BREAK_CRLF))
                        .append(CommonConstants.LINE_BREAK_CRLF);
                strBlank = "";
                mark = pStart;
                continue;
            }
            if (lineValue.startsWith(pStart)) {
                line = lineValue.replace(pStart, CommonConstants.LINE_BREAK_CRLF);
                if (!line.isEmpty()) {
                    sb.append(line);
                    strBlank = " ";
                } else {
                    strBlank = "";
                }

                mark = pStart;
                continue;
            }

            if (lineValue.endsWith(pEnd)) {
                line = lineValue.replace(pEnd, "");
                sb.append(strBlank).append(line).append(CommonConstants.LINE_BREAK_CRLF);

                mark = "";
                strBlank = "";
                continue;
            }

            // code block
            if (lineValue.startsWith(MdUtil.MARK_MD_CODE_BLOCK)) {
                isCodeBlock = !isCodeBlock;
                if (!isCodeBlock) {
                    sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
                } else {
                    sb.append(CommonConstants.LINE_BREAK_CRLF).append(CommonConstants.LINE_BREAK_CRLF).append(line)
                            .append(CommonConstants.LINE_BREAK_CRLF);
                }
                strBlank = "";
                continue;

            }
            if (isCodeBlock) {
                sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
                strBlank = "";
                continue;
            }

            // UI
            if ((line.contains(MARK_HTML_UL_START) || line.contains(MARK_HTML_UL_END))
                    || (line.contains(MARK_HTML_OL_START) || line.contains(MARK_HTML_OL_END))) {
                sb.append(CommonConstants.LINE_BREAK_CRLF);
                continue;
            }

            // LI
            if (line.contains(MARK_HTML_LI_START) && line.contains(MARK_HTML_LI_END)) {
                line = line.trim().replace(MARK_HTML_LI_START, "  * ").replace(MARK_HTML_LI_END, "");
                sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
                continue;
            }

            if (line.contains(MARK_HTML_LI_START)) {
                line = line.trim().replace(MARK_HTML_LI_START, "  * ");
                isLi = true;
                sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
                continue;
            }

            if (isLi || line.contains(MARK_HTML_LI_END)) {
                if (line.contains(MARK_HTML_LI_END)) {
                    isLi = false;
                }
                line = line.trim().replace(MARK_HTML_LI_END, "");
                sb.append(" ").append(line).append(CommonConstants.LINE_BREAK_CRLF);
                continue;
            }

            if (mark.equals(pStart)) {
                if (lineValue.isEmpty()) {
                    strBlank = "";
                    sb.append(CommonConstants.LINE_BREAK_CRLF);
                } else {
                    sb.append(strBlank).append(lineValue);
                    strBlank = " ";
                }
                continue;
            }
            sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
        }

        return sb.toString().replace(MARK_HTML_LINEBREAK, CommonConstants.LINE_BREAK_CRLF).replace(MARK_HTML_LINEBREAK2,
                CommonConstants.LINE_BREAK_CRLF);
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
}
