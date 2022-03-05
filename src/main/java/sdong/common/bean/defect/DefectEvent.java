package sdong.common.bean.defect;

import java.util.ArrayList;
import java.util.List;

public class DefectEvent {
    String defectId = "";

    String eventid = "";
    String filePath = "";
    String nameSpace = "";
    String enclosingClass = "";
    String function = "";
    int reportLine = 0;

    String eventTag = "";
    String description = "";
    boolean isMain = false;

    String contentId = "";
    String snippetId = "";

    List<DefectEvent> subEvent;

    String refNodeId = "";

    public String getDefectId() {
        return defectId;
    }

    public void setDefectId(String defectId) {
        this.defectId = defectId;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
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

    public String getEventTag() {
        return eventTag;
    }

    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean isMain) {
        this.isMain = isMain;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getSnippetId() {
        return snippetId;
    }

    public void setSnippetId(String snippetId) {
        this.snippetId = snippetId;
    }

    public List<DefectEvent> getSubEvent() {
        return subEvent;
    }

    public void setSubEvent(List<DefectEvent> subEvent) {
        this.subEvent = subEvent;
    }

    public void addSubEvent(DefectEvent event){
        if(subEvent == null ){
            subEvent = new ArrayList<DefectEvent>();
        }
        subEvent.add(event);
    }

    public String getRefNodeId() {
        return refNodeId;
    }

    public void setRefNodeId(String refNodeId) {
        this.refNodeId = refNodeId;
    }
}