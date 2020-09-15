package sdong.common.bean.thesis;

import java.util.ArrayList;
import java.util.List;

public class Paper {
    private String paperId = "";
    private PaperType paperType;

    private String title = "";
    private String summary = "";
    private List<String> keywords = new ArrayList<String>();
    private List<Author> authors = new ArrayList<Author>();
    private List<String> urlList = new ArrayList<String>();

    private List<Paper> referenceList = new ArrayList<Paper>();

    // journal =   {{期刊名称}},  booktitle   =   {{会议论文集名称或会议名称}}, publisher = {{出版社名称}},
    private String journal = "";
    // volume  =   {卷号},
    private String volumn = "";
    // number  =   {期号},
    private String number = "";
    // pages   =   {起始页码--终止页码},
    private String pages = "";
    //year    =   {年份}
    private String year = "";
    private String  url = "";
    
    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Paper> getReferenceList() {
        return referenceList;
    }

    public void setReferenceList(List<Paper> referenceList) {
        this.referenceList = referenceList;
    }


    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getVolumn() {
        return volumn;
    }

    public void setVolumn(String volumn) {
        this.volumn = volumn;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PaperType getPaperType() {
        return paperType;
    }

    public void setPaperType(PaperType paperType) {
        this.paperType = paperType;
    }
}
