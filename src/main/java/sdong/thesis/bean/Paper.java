package sdong.thesis.bean;

import java.util.ArrayList;
import java.util.List;

public class Paper {
    private String paperId = "";
    private String title = "";
    private String summary = "";
    private List<String> keywords = new ArrayList<String>();
    private List<Author> authors = new ArrayList<Author>();

    private List<Paper> referenceList = new ArrayList<Paper>();

    private String publishYear = "";
    private String publishIn = "";
    private String publishVolumn = "";
    private String publishIssue = "";
    private String publishPages = "";

    private String URL = "";

    private String categoryId = "";

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

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public String getPublishIn() {
        return publishIn;
    }

    public void setPublishIn(String publishIn) {
        this.publishIn = publishIn;
    }

    public String getPublishVolumn() {
        return publishVolumn;
    }

    public void setPublishVolumn(String publishVolumn) {
        this.publishVolumn = publishVolumn;
    }

    public String getPublishIssue() {
        return publishIssue;
    }

    public void setPublishIssue(String publishIssue) {
        this.publishIssue = publishIssue;
    }

    public String getPublishPages() {
        return publishPages;
    }

    public void setPublishPages(String publishPages) {
        this.publishPages = publishPages;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

}
