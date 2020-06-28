package sdong.common.bean.loc;

/**
 * Source file line type
 *
 */
public enum LineType {
    /**
     * code line
     */
    CODE_LINE("CODE_LINE", 0), 
    /**
     * blan line
     */
    BLANK_LINE("BLANK_LINE", 1), 
    /**
     * comment line
     */
    COMMENT_LINE("COMMENT_LINE", 2),
    /**
     * multiple comment start line
     */
    COMMENT_START_LINE("COMMENT_START_LINE", 3),
    /**
     * code with multiple comment start line
     */
    CODE_COMMENT_START_LINE("CODE_COMMENT_START_LINE", 4),
    /**
     * multiple comment end line
     */
    COMMENT_END_LINE("COMMENT_END_LINE", 5),
    /**
     * multiple comment end line with code
     */
    COMMENT_END_CODE_LINE("COMMENT_END_CODE_LINE", 6),
    /**
     * multiple comment end line with multiple comment start line
     */
    COMMENT_END_START_LINE("COMMENT_END_START_LINE", 7),
    /**
     * multiple comment end line with code and multiple comment start line
     */
    COMMENT_END_CODE_START_LINE("COMMENT_END_CODE_START_LINE", 8),
    /**
     * comment with code line
     */
    COMMENT_CODE_LINE("COMMENT_CODE_LINE", 9);

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
