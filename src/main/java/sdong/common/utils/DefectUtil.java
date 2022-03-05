package sdong.common.utils;

import java.util.List;

import sdong.common.bean.defect.Defect;
import sdong.common.bean.defect.DefectEvent;

public class DefectUtil {
    /**
     * Clone event
     * 
     * @param from from
     * @param to   to
     */
    public static void cloneEvent(DefectEvent from, DefectEvent to) {
        to.setDefectId(from.getDefectId());
        to.setEventid(from.getEventid());
        to.setFilePath(from.getFilePath());
        to.setNameSpace(from.getNameSpace());
        to.setEnclosingClass(from.getEnclosingClass());
        to.setFunction(from.getFunction());
        to.setReportLine(from.getReportLine());
        to.setRefNodeId(from.getRefNodeId());
        to.setEventTag(from.getEventTag());
        to.setDescription(from.getDescription());
        to.setMain(from.isMain());
        to.setContentId(from.getContentId());
        to.setSnippetId(from.getSnippetId());
        to.setSubEvent(from.getSubEvent());
    }

    /**
     * Clone defect
     * 
     * @param from from
     * @param to   to
     */
    public static void cloneDefect(Defect from, Defect to) {
        to.setDefectId(from.getDefectId());
        to.setCheckerId(from.getCheckerId());
        to.setFilePath(from.getFilePath());
        to.setNameSpace(from.getNameSpace());
        to.setEnclosingClass(from.getEnclosingClass());
        to.setFunction(from.getFunction());
        to.setReportLine(from.getReportLine());
        to.setExtra(from.getExtra());
        to.setMergekey(from.getMergekey());
    }

    public static int getDefectEventListSize(List<DefectEvent> eventList) {
        int size = 0;
        for (DefectEvent event : eventList) {
            size = size + 1;
            size = size + getDefectSubEventSize(event.getSubEvent());
        }

        return size;
    }

    private static int getDefectSubEventSize(List<DefectEvent> eventList) {
        int size = 0;
        if(eventList == null){
            return size;
        }
        for (DefectEvent event : eventList) {
            size = size + 1;
            size = size + getDefectSubEventSize(event.getSubEvent());
        }

        return size;
    }
}