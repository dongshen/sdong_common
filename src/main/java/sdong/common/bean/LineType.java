package sdong.common.bean;

/**
 * Source file line type
 *
 */
public enum LineType {
    CODE_LINE("CODE_LINE", 0), BLANK_LINE("BLANK_LINE", 1), COMMNET_LINE("COMMNET_LINE", 2),
    COMMNET_START_LINE("COMMNET_START_LINE", 3), CODE_COMMNET_START_LINE("CODE_COMMNET_START_LINE", 4),
    COMMNET_END_LINE("COMMNET_END_LINE", 5),COMMNET_END_CODE_LINE("COMMNET_END_CODE_LINE", 6),
    COMMNET_END_START_LINE("COMMNET_END_START_LINE", 7),COMMNET_END_CODE_START_LINE("COMMNET_END_CODE_START_LINE", 8),
    COMMNET_CODE_LINE("COMMNET_CODE_LINE", 9);

    private String lineType;
    private int index;

    private LineType(String lineType, int index) {
        this.lineType = lineType;
        this.index = index;
    }

    public static String getName(int index) {
        for (LineType type : LineType.values()) {
            if (type.getIndex() == index) {
                return type.lineType;
            }
        }
        return null;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
