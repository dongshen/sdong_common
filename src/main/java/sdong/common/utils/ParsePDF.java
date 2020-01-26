package sdong.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import sdong.common.exception.SdongException;
import sdong.thesis.bean.Author;
import sdong.thesis.bean.Paper;

public class ParsePDF {

  private static final Logger logger = LoggerFactory.getLogger(ParsePDF.class);

  public String getFileContent(String file) throws SdongException {

    FileInputStream inputstream = null;
    String content;
    try {
      inputstream = new FileInputStream(new File(file));

      // -1 mean unlimited, else the limited is 100,000
      BodyContentHandler handler = new BodyContentHandler(-1);
      Metadata metadata = new Metadata();
      ParseContext pcontext = new ParseContext();

      // parsing the document using PDF parser
      PDFParser pdfparser = new PDFParser();
      pdfparser.parse(inputstream, handler, metadata, pcontext);

      content = handler.toString();

    } catch (FileNotFoundException e) {
      logger.error(e.getMessage());
      throw new SdongException(e);
    } catch (IOException e) {
      logger.error(e.getMessage());
      throw new SdongException(e);
    } catch (SAXException e) {
      logger.error(e.getMessage());
      throw new SdongException(e);
    } catch (TikaException e) {
      logger.error(e.getMessage());
      throw new SdongException(e);
    } finally {

      try {
        if (inputstream != null) {
          inputstream.close();
        }
      } catch (IOException e) {
        logger.error(e.getMessage());
        throw new SdongException(e);
      }

    }

    return content;
  }

  public Paper extractPDF(String content) throws SdongException {
    Paper paper = new Paper();
    if (content == null || content.isEmpty()) {
      return paper;
    }

    List<String> contentList = StringUtil.splitStringToListByLineBreak(content);
    List<String> data = new ArrayList<String>();
    logger.info("Get lines: {}",contentList.size());
    boolean getData = false;
    for (String line : contentList) {
      if (line.isEmpty()) {
        if (getData == false) {
          continue;
        } else {
          fillPaperInfo(paper, data);
          data.clear();
          getData = false;
        }
      } else {
        data.add(line);
        getData = true;
      }
    }

    return paper;
  }

  public void fillPaperInfo(Paper paper, List<String> data){
    System.out.println("block:");
    for(String line: data){
      System.out.println(line);
    }
    /*
    boolean getTitle = true;
    boolean getAuthors = true;
    String title = "";
  // get title
  if (getTitle == true) {
    if (line.isEmpty()) {
      if (title.isEmpty()) {
        continue;
      } else {
        paper.setTitle(title);
        getTitle = false;
      }
    } else {
      title = title + line + " ";
    }
    // get authors
  } else if (getAuthors == true) {
    if (line.isEmpty()) {
      if (paper.getAuthors().size() == 0) {
        continue;
      } else {
        getAuthors = false;
      }
    } else {
      if (line.indexOf(",") >= 0) {
        String[] authors = line.split(",");
        for(String author:authors){
          Author one = new Author();  
          one.setFullName(author);
          paper.getAuthors().add(one);
        }

      } else {
        Author author = new Author();
        author.setFullName(line);
        paper.getAuthors().add(author);
      }
    }
    */
  }

}