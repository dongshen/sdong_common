package sdong.common.bean.defect;

import java.util.ArrayList;
import java.util.List;

public class Defect {
    String defectId = "";

    // checker info
    String checkerId = "";
    String checkerName = "";
    String category = "";
    String subCategory = "";
    String issuetype = "";

    // defect info
    String filePath = "";
    String nameSpace = "";
    String enclosingClass = "";
    String function = "";
    int reportLine = 0;
    String extra = "";
    String mergekey = "";

    List<DefectEvent> eventList = new ArrayList<DefectEvent>();

    public String getDefectId() {
        return defectId;
    }

    public void setDefectId(String defectId) {
        this.defectId = defectId;
    }

    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(String issuetype) {
        this.issuetype = issuetype;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getEnclosingClass() {
        return enclosingClass;
    }

    public void setEnclosingClass(String enclosingClass) {
        this.enclosingClass = enclosingClass;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public int getReportLine() {
        return reportLine;
    }

    public void setReportLine(int reportLine) {
        this.reportLine = reportLine;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getMergekey() {
        return mergekey;
    }

    public void setMergekey(String mergekey) {
        this.mergekey = mergekey;
    }

    public List<DefectEvent> getEventList() {
        return eventList;
    }

    public void setEventList(List<DefectEvent> eventList) {
        this.eventList = eventList;
    }
}