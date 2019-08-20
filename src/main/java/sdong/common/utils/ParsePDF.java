package sdong.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class ParsePDF {

  private static final Logger logger = LoggerFactory.getLogger(ParsePDF.class);

  public Metadata getFileMetadata(String file) {

    BodyContentHandler handler = new BodyContentHandler();
    Metadata metadata = new Metadata();

    FileInputStream inputstream = null;
    try {
      inputstream = new FileInputStream(new File(file));

      ParseContext pcontext = new ParseContext();

      // parsing the document using PDF parser
      PDFParser pdfparser = new PDFParser();
      pdfparser.parse(inputstream, handler, metadata, pcontext);

      // getting the content of the document
       System.out.println("Contents of the PDF :" + handler.toString());
       
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      logger.error(e.getMessage());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      logger.error(e.getMessage());
    } catch (SAXException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      logger.error(e.getMessage());
    } catch (TikaException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      logger.error(e.getMessage());
    } finally {

      try {
        if (inputstream != null) {
          inputstream.close();
        }
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }

    return metadata;
  }
}