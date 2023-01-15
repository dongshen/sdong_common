package sdong.common.utils;

import sdong.common.CommonConstants;
import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MdUtil {
    private static final Logger LOG = LogManager.getLogger(MdUtil.class);

    private static final Pattern TITLE_PATTER = Pattern.compile("^#{1,5}");
    private static final Pattern TITLE_SEQ_PATTER = Pattern.compile("[0-9.]{1,}");

    public static void addUpdateSectionNumber(String mdFile) throws SdongException {
        StringBuffer sb = new StringBuffer();
        try (Scanner sc = new Scanner(new File(mdFile))) {
            String line;
            int[] seqs = new int[] { 0, 0, 0, 0, 0 };
            Matcher matcher;
            String title;
            String seqStr;
            String[] values;
            String value;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                matcher = TITLE_PATTER.matcher(line);
                if (matcher.find()) {
                    values = line.split(" ");
                    title = values[0];
                    setSeq(seqs, title.length() - 1);
                    value = extractValue(line, title, values);
                    seqStr = getSeq(seqs, title.length());
                    sb.append(title).append(" ").append(seqStr).append(" ").append(value)
                            .append(CommonConstants.LINE_BREAK_CRLF);
                    continue;
                }
                sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
            }
            sc.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new SdongException(e.getMessage(), e);
        }

        try (FileWriter writer = new FileWriter(mdFile)) {
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new SdongException(e.getMessage(), e);
        }
    }

    private static String extractValue(String line, String title, String[] values) {
        Matcher matcher;
        String seqStr;
        String value;
        if (values.length > 1) {
            seqStr = values[1];
            matcher = TITLE_SEQ_PATTER.matcher(line);
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
}
