package sdong.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import sdong.common.exception.SdongException;
import sdong.common.parse.pdf.PdfParser;

public class PaperUtil {
  private static final Logger LOG = LoggerFactory.getLogger(PaperUtil.class);

  /**
   * get contents from pdf file
   *
   * @param file
   * @return
   * @throws SdongException
   */
  public static String getFileContent(String file) throws SdongException {
    String content;
    try (FileInputStream inputstream = new FileInputStream(new File(file));) {
      // -1 mean unlimited, else the limited is 100,000
      BodyContentHandler handler = new BodyContentHandler(-1);
      Metadata metadata = new Metadata();
      ParseContext pcontext = new ParseContext();

      // parsing the document using PDF parser
      PDFParser pdfparser = new PDFParser();
      pdfparser.parse(inputstream, handler, metadata, pcontext);

      content = handler.toString();
      LOG.debug("Contents of the PDF :{}", handler.toString());

      // getting metadata of the document
      LOG.debug("Metadata of the PDF:");
      String[] metadataNames = metadata.names();
      for (String name : metadataNames) {
        LOG.debug("{}:{}", name, metadata.get(name));
      }
    } catch (IOException | SAXException | TikaException e) {
      LOG.error(e.getMessage());
      throw new SdongException(e);
    }

    return content;
  }

  /**
   * get pdf footer
   *
   * @param references
   * @return
   * @throws SdongException
   */
  public static List<String> getFooter(List<String> contents) throws SdongException {
    List<String> footers = new ArrayList<String>();
    Map<String, Integer> count = new HashMap<String, Integer>(contents.size());
    String line;
    for (String content : contents) {
      line = content.trim();
      if (line.isEmpty()) {
        continue;
      }
      if (count.containsKey(line)) {
        count.put(line, count.get(line) + 1);
      } else {
        count.put(line, 1);
      }
    }

    int max = 0;
    String footer = "";
    for (Map.Entry<String, Integer> en : count.entrySet()) {
      if (en.getValue() > max && en.getKey().length() > 10) {
        footer = en.getKey();
        max = en.getValue();
      }
    }
    if (max > 1) {
      LOG.info("Footer max:{},{}", max, footer);
      footers.add(footer);
    }

    return footers;
  }

  public static String getMoreDetail(String ref, List<String> footerList) throws SdongException {
    List<String> details = StringUtil.splitStringToListByLineBreak(ref);
    List<String> blocks = PaperUtil.getReferenceblocks(details);

    StringBuilder sb = new StringBuilder();
    int start = 0;
    int end = 0;
    String cur;
    String linkChar = "";
    for (String lines : blocks) {
      String[] block = lines.split(",");
      start = CommonUtil.parseInteger(block[0]);
      end = CommonUtil.parseInteger(block[1]);
      if (checkSkipBlcok(details, start, end, footerList)) {
        continue;
      }

      for (int ind = start; ind <= end; ind++) {
        cur = details.get(ind);
        // remove word continue mark
        if (cur.endsWith("-")) {
          cur = cur.substring(0, cur.length() - 1);
          linkChar = "";
        } else if (cur.endsWith("–") || cur.endsWith("/")) {
          linkChar = "";
        } else {
          linkChar = " ";
        }

        sb.append(cur).append(linkChar);
      }
    }
    return sb.toString().trim();
  }

  private static boolean checkSkipBlcok(List<String> details, int start, int end, List<String> footerList) {
    boolean isSkip = false;
    // page no && footer
    String line;
    String preLine = details.get(start);
    for (int ind = start; ind <= end; ind++) {
      line = details.get(ind);
      if (checkPageNumber(line) || checkFooter(line, footerList)) {
        return true;
      }

      // duplicate http
      if (preLine.startsWith("http") && preLine.equals(line)) {
        return true;
      } else {
        preLine = line;
      }
    }
    return isSkip;
  }

  public static List<String> getReferenceblocks(List<String> details) {
    List<String> blocks = new ArrayList<String>();
    if (details == null || details.isEmpty()) {
      return blocks;
    }

    String line;
    int mark = 0;
    // 0:isblank, 1: not blank
    int preMark = details.get(0).isEmpty() ? 0 : 1;
    ;
    int start = 0;
    int end = 0;
    for (int ind = 0; ind < details.size(); ind++) {
      line = details.get(ind);
      mark = line.isEmpty() ? 0 : 1;
      if (preMark != mark) {
        if (preMark == 1) {
          end = ind - 1;
          blocks.add(start + "," + end);
        } else {
          start = ind;
        }
        preMark = mark;
      }
    }
    if (mark == 1) {
      blocks.add(start + "," + (details.size() - 1));
    }

    return blocks;
  }

  private static boolean checkPageNumber(String line) {
    boolean isPageNumber = CommonUtil.checkNumber(line);
    if (!isPageNumber) {
      String clear = line.replace(":", "");
      for (String rep : PdfParser.REFERENCE_LIST) {
        clear = clear.replace(rep, "");
      }
      isPageNumber = CommonUtil.checkNumber(clear.trim());
    }
    return isPageNumber;
  }

  private static boolean checkFooter(String line, List<String> footerList) {
    boolean isFooter = false;
    for (String footer : footerList) {
      if (line.equals(footer)) {
        isFooter = true;
        break;
      }
    }
    return isFooter;
  }
}