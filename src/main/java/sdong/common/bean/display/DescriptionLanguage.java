package sdong.common.bean.display;

public enum DescriptionLanguage {
    ALL("All"),
    CN("Chinese"),
    EN("English"),
    OTHERS("Others");

    private String language;

    DescriptionLanguage(String language) {
        this.language = language;
    }

    /**
     * get CheckerItem by name
     * 
     * @param language item name
     * @return DescriptionLanguage
     */
    public static DescriptionLanguage getLanguage(String language) {
        if (language == null || language.isEmpty()) {
            return OTHERS;
        }
        for (DescriptionLanguage lang : DescriptionLanguage.values()) {
            if (lang.getLanguage().equals(language)) {
                return lang;
            }
        }
        return OTHERS;
    }

    public String getLanguage() {
        return language;
    }
}