package sdong.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import sdong.common.exception.SdongException;

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
}