package sdong.common.bean.defect;

import java.util.ArrayList;
import java.util.List;

public class Defect {
    String defectId = "";

    // checker info
    String checkerId = "";

    // defect info
    String filePath = "";
    String nameSpace = "";
    String enclosingClass = "";
    String function = "";
    int reportLine = 0;
    String extra = "";
    String mergekey = "";

    List<List<DefectEvent>> eventList = new ArrayList<List<DefectEvent>>();

    public String getDefectId() {
        return defectId;
    }

    public void setDefectId(String defectId) {
        this.defectId = defectId;
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

    public List<List<DefectEvent>> getEventList() {
        return eventList;
    }

    public void addEventList(List<DefectEvent> eventList) {
        this.eventList.add(eventList);
    }

    /**
     * add event to the latest event list
     *
     * @param event event
     */
    public void addEvent(DefectEvent event) {
        int curlist = this.eventList.size();
        if (curlist == 0) {
            List<DefectEvent> eventList = new ArrayList<DefectEvent>();
            eventList.add(event);
            this.eventList.add(eventList);
        } else {
            List<DefectEvent> eventList = this.eventList.get(curlist - 1);
            eventList.add(event);
        }
    }

    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }
}