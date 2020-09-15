package sdong.common.bean.thesis;

/**
 * Paper type
 *
 */
public enum PaperType {
    /**
     * JOURNAL
     */
    JOURNAL("JOURNAL", 1), 
    /**
     * meeting
     */
    MEETING("MEETING", 2), 
    /**
     * BOOK
     */
    BOOK("BOOK", 3),
    /**
     * WEB
     */
    WEB("WEB", 4),
    /**
     * OTHER
     */
    OTHER("OTHER", 0);

    private String paperType;
    private int index;

    private PaperType(String paperType, int index) {
        this.paperType = paperType;
        this.index = index;
    }

    public static String getName(int index) {
        for (PaperType type : PaperType.values()) {
            if (type.getIndex() == index) {
                return type.paperType;
            }
        }
        return null;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
