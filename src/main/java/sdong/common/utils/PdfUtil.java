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

public class PdfUtil {
  private static final Logger LOG = LoggerFactory.getLogger(PdfUtil.class);

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
  public static List<String> checkFooter(List<String> contents) throws SdongException {
    List<String> footers = new ArrayList<String>();
    Map<String, Integer> count = new HashMap<String, Integer>(contents.size());
    for (String line : contents) {
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
      if (en.getValue() > max) {
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
    String detail = StringUtil.removeStarAndEndBlankLine(ref);

    List<String> details = StringUtil.splitStringToListByLineBreak(detail);
    StringBuilder sb = new StringBuilder();
    String cur;
    String next;
    String linkChar = "";
    int blankline = 0;
    boolean isEndMark = false;
    int refNumber = details.size();
    for (int ind = 0; ind < refNumber; ind++) {
      cur = details.get(ind);
      if (cur.isEmpty()) {
        blankline++;
        continue;
      }

      // check page footer
      if (blankline >= 1) {
        if (isFooter(cur, footerList)) {
          continue;
        }
        if (checkPageNumber(cur)) {
          if (isEndMark && (ind + 1 == refNumber || (ind + 1) < refNumber && details.get(ind + 1).isEmpty())) {
            break;
          } else {
            continue;
          }
        }
      }

      // remove word continue mark
      if (cur.endsWith("-")) {
        cur = cur.substring(0, cur.length() - 1);
        linkChar = "";
      } else if (cur.endsWith("–") || cur.endsWith("/")) {
        linkChar = "";
      } else {
        linkChar = " ";
      }

      // check end mark
      if (cur.endsWith(".")) {
        isEndMark = true;
      } else {
        isEndMark = false;
      }

      sb.append(cur).append(linkChar);
    }
    detail = sb.toString().trim();

    return detail;
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

  private static boolean isFooter(String line, List<String> footerList) {
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