package sdong.common.utils;

import sdong.common.CommonConstants;
import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MdUtil {
    private static final Logger LOG = LogManager.getLogger(MdUtil.class);

    private static final Pattern TITLE_PATTER = Pattern.compile("^#{1,5}\\s+");
    private static final Pattern TITLE_SEQ_PATTER = Pattern.compile("^([\\d]+\\.)+");

    public static final String MARK_MD_CODE_BLOCK = "```";

    public static final String MARK_MD_REFERENCE = ">";

    private static final Pattern LINK_EXCLUDE_PATTERN = Pattern
            .compile("[\"`~!@#$%^&*()_\\+=|{}':;',\\[\\].<>/?~！@#￥%……*（）+|{}【】‘；：”“’。，、？]");

    public static final String MARK_MD_TOC = "[TOC]";

    public static final String MARK_MD_LEVEL = "#";

    /**
     * add updated section number for MD file
     * 
     * @param mdFile input md file
     * @throws SdongException module exception
     */
    public static void addUpdateSectionNumber(String mdFile) throws SdongException {
        StringBuilder sb = new StringBuilder();
        List<String> tocList = new ArrayList<String>();
        try (Scanner sc = new Scanner(new File(mdFile))) {
            int[] seqs = new int[] { 0, 0, 0, 0, 0 };
            boolean isBlock = false;
            while (sc.hasNextLine()) {
                isBlock = processLine(sb, tocList, sc, seqs, isBlock);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new SdongException(e);
        }

        try (FileWriter writer = new FileWriter(mdFile)) {
            String content = sb.toString();
            writer.write(content.replace(MARK_MD_TOC, generateToc(tocList)));
            writer.flush();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new SdongException(e);
        }
    }

    private static boolean processLine(StringBuilder sb, List<String> tocList, Scanner sc, int[] seqs, boolean isBlock) {
        String line;
        Matcher matcher;
        String title;
        String seqStr;
        String[] values;
        String value;
        String lineVal;
        line = sc.nextLine();
        lineVal = line.trim();
        if (!lineVal.isEmpty()) {
            if (lineVal.startsWith(MARK_MD_CODE_BLOCK)) {
                isBlock = !isBlock;
            }
            if (!isBlock) {
                matcher = TITLE_PATTER.matcher(lineVal);
                if (matcher.find()) {
                    LOG.info("{}", line);
                    values = line.split(" ");
                    title = values[0];
                    setSeq(seqs, title.length() - 1);
                    value = extractValue(line, title, values);
                    seqStr = getSeq(seqs, title.length());
                    sb.append(title).append(" ").append(seqStr).append(" ").append(value)
                            .append(CommonConstants.LINE_BREAK_CRLF);
                    tocList.add(title + " " + seqStr + " " + value);
                    return isBlock;
                }
            }
        }
        sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
        return isBlock;
    }

    private static String extractValue(String line, String title, String[] values) {
        Matcher matcher;
        String seqStr;
        String value;
        if (values.length > 1) {
            seqStr = values[1];
            matcher = TITLE_SEQ_PATTER.matcher(seqStr);
            if (matcher.find()) {
                value = line.substring(line.indexOf(seqStr) + seqStr.length() + 1).trim();
            } else {
                value = line.substring(title.length() + 1).trim();
            }
        } else {
            value = "";
        }
        return value;
    }

    /**
     * get md level current level seq number string
     * 
     * @param seqs   md seqs arrays
     * @param length current level
     * @return result
     */
    public static String getSeqStr(int[] seqs, int length) {
        setSeq(seqs, length - 1);
        return getSeq(seqs, length);
    }

    private static void setSeq(int[] seqs, int length) {
        seqs[length] = seqs[length] + 1;
        for (int ind = length + 1; ind < seqs.length; ind = ind + 1) {
            seqs[ind] = 0;
        }
    }

    private static String getSeq(int[] seqs, int length) {
        StringBuilder sb = new StringBuilder();
        for (int ind = 0; ind < length; ind = ind + 1) {
            sb.append(seqs[ind]).append(".");
        }
        return sb.toString();
    }

    private static String generateToc(List<String> tocList) {
        StringBuilder sb = new StringBuilder();
        int level = 0;
        String[] titles;
        String value;
        for (String line : tocList) {
            titles = line.split(" ");
            level = (titles[0].length() - 1) * 2;
            sb.append(StringUtil.getNumbersOfString(" ", level)).append("- [");
            value = line.substring(titles[0].length() + 1);
            sb.append(value).append("](#");
            sb.append(generateMdLink(value)).append(")").append(CommonConstants.LINE_BREAK_CRLF);
        }

        return sb.toString();
    }

    /**
     * base on level to generate number of md level #
     * 
     * @param mdLevel md level
     * @return result
     */
    public static String getMdLevel(int mdLevel) {
        return StringUtil.getNumbersOfString(MARK_MD_LEVEL, mdLevel);
    }

    /**
     * generate md link
     * 
     * @param value input md string
     * @return md link
     */
    public static String generateMdLink(String value) {
        String result = value.replace(" ", "-");
        result = result.toLowerCase();
        Matcher m = LINK_EXCLUDE_PATTERN.matcher(result);
        return m.replaceAll("");
    }

    /**
     * remove html mark and blank line for description
     * 
     * @param value      description value
     * @param removeStrs require remove or replaced mark
     * @return result
     */
    public static String extractDescription(String value, String[][] removeStrs) {
        return extractDescription(value, removeStrs, true);
    }

    public static String extractDescription(String value, String[][] removeStrs, Boolean isIncludeP) {
        value = XmlUtils.decode(value);
        if (removeStrs != null) {
            for (String[] str : removeStrs) {
                value = value.replace(str[0], str[1]);
            }
        }

        value = StringUtil.removeHtmlMark(value, isIncludeP);

        value = StringUtil.removeStarAndEndBlankLine(value);
        return value;
    }
}
