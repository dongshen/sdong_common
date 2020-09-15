package sdong.common.parse.pdf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.CommonConstants;
import sdong.common.bean.thesis.Author;
import sdong.common.bean.thesis.Paper;
import sdong.common.exception.SdongException;
import sdong.common.utils.PaperUtil;
import sdong.common.utils.StringUtil;

public class PdfParser {
    private static final Logger LOG = LoggerFactory.getLogger(PdfParser.class);

    private static final List<String> ABSTRACT_LIST = Arrays.asList("Abstract");
    private static final List<String> KEYWOARD_LIST = Arrays.asList("Index Terms");
    public static final List<String> REFERENCE_LIST = Arrays.asList("REFERENCES", "References", "References:");
    private static final List<String> INDEX_LIST = Arrays.asList("I Introduction", "1 Introduction");

    private final List<String> indexList = new ArrayList<String>();
    private int indexPos = -1;

    private String fileName;
    private String contents;
    private List<String> contenList;

    public PdfParser(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getPdfContents() throws SdongException {
        this.contents = PaperUtil.getFileContent(this.fileName);
        contenList = StringUtil.splitStringToListByLineBreak(contents);
        return this.contenList;
    }

    public Paper parsePdfContents() throws SdongException {
        Paper paper = new Paper();

        indexPos = checkIndex();

        paper.setTitle(getTitle());

        paper.setAuthors(getAuthors());

        paper.setKeywords(getKeyWords());

        paper.setSummary(getSummary());

        paper.setReferenceList(getReferenceList());
        updateReferenceDetailList(paper.getReferenceList());

        return paper;
    }

    /**
     * check whether have index in pdf. it will put index in goble list: indexList.
     *
     * @return index start position
     * @throws SdongException module exception
     */
    public int checkIndex() throws SdongException {
        String curIndex = "";
        int position = 0;
        for (String index : INDEX_LIST) {
            position = contents.indexOf(index);
            if (position >= 0) {
                curIndex = index;
                break;
            }
        }
        // cant find marks for index
        if (position < 0) {
            return position;
        }

        // verify next line
        String nextLine = StringUtil.getNextLineInString(contents, position).trim();
        if (nextLine.isEmpty() || !(curIndex.startsWith("1") && nextLine.startsWith("2"))
                && !(curIndex.startsWith("I") && nextLine.startsWith("II"))) {
            return -1;
        }

        String indexContents = contents.substring(position);
        List<String> indexs = StringUtil.splitStringToListByLineBreak(indexContents);
        String ind;
        for (String line : indexs) {
            ind = line.trim();
            if (!ind.isEmpty()) {
                indexList.add(ind);
            }
        }

        return position;
    }

    public String getTitle() {
        String title = "";

        return title;
    }

    public String getSummary() {
        String summary = "";

        return summary;
    }

    public List<String> getKeyWords() {
        List<String> keywords = new ArrayList<String>();

        return keywords;
    }

    public List<Author> getAuthors() {
        List<Author> authors = new ArrayList<Author>();

        return authors;
    }

    public List<Paper> getReferenceList() throws SdongException {
        int position = 0;
        String curIndex = "";
        for (String index : REFERENCE_LIST) {
            position = contents.indexOf(index);
            if (position >= 0) {
                if (index.equals(StringUtil.getCurrentLineInString(contents, position).trim())) {
                    curIndex = index;
                    break;
                }
            }
        }
        // cant find marks for reference
        if (curIndex.isEmpty()) {
            throw new SdongException("Cant find reference!");
        }

        // verify
        int curLineBreak = contents.indexOf("\n", position);
        if (curLineBreak < 0
                || (position + curIndex.length() != curLineBreak && position + curIndex.length() + 1 != curLineBreak)) {
            throw new SdongException("Cant find reference!");
        }
        // compare with index position
        int startPos = curLineBreak + 1;
        int endPos = contents.length();
        int indexPos = checkIndex();
        if (indexPos > 0) {
            if (startPos > indexPos) {
                throw new SdongException("Cant find reference!");
            } else {
                endPos = indexPos;
            }
        }

        // get Reference
        String refContents = contents.substring(startPos, endPos);
        List<String> indexs = StringUtil.splitStringToListByLineBreak(refContents);
        String line;
        int curRef = 1;
        List<Paper> references = new ArrayList<Paper>();
        Iterator<String> itr = indexs.iterator();
        StringBuilder sb = new StringBuilder();
        boolean isInRef = false;
        while (itr.hasNext()) {
            line = itr.next().trim();
            if (!isInRef && line.isEmpty()) {
                continue;
            }

            if (line.startsWith("[" + curRef + "]")) {
                sb.append(line).append(CommonConstants.LINE_BREAK);
                isInRef = true;
            } else if (line.startsWith("[" + (curRef + 1) + "]")) {
                Paper paper = new Paper();
                paper.setTitle(sb.toString());
                references.add(paper);
                sb.setLength(0);
                sb.append(line).append(CommonConstants.LINE_BREAK);
                curRef = curRef + 1;
            } else {
                sb.append(line).append(CommonConstants.LINE_BREAK);
            }
        }
        // add last one
        Paper paper = new Paper();
        paper.setTitle(sb.toString());
        references.add(paper);
        sb.setLength(0);

        return references;
    }

    public void updateReferenceDetailList(List<Paper> referenceList) throws SdongException {
        for(Paper paper: referenceList ){
            paper.setTitle(PaperUtil.getMoreDetail(paper.getTitle(),PaperUtil.getFooter(contenList)));
        }
    }

    public List<String> getIndexList() {
        return indexList;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public List<String> getContenList() {
        return contenList;
    }

    public void setContenList(List<String> contenList) {
        this.contenList = contenList;
    }
}