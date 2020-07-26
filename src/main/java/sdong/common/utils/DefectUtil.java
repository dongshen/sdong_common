package sdong.common.utils;

import sdong.common.bean.defect.DefectEvent;

public class DefectUtil {
    public static void clone(DefectEvent from, DefectEvent to) {
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
}