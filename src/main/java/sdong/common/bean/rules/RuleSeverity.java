package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

/**
 * Rule Severity enum
 */
public enum RuleSeverity {
    @SerializedName(value = "Fatal") 
    FATAL("Fatal", 1),
    @SerializedName(value = "Critical")    
    CRITICAL("Critical", 2),
    @SerializedName(value = "Medium")
    MEDIUM("Medium", 3),
    @SerializedName(value = "Normal")
    NORMAL("Normal", 4),
    @SerializedName(value = "Suggest")
    SUGGEST("Suggest", 5),
    @SerializedName(value = "Others")
    OTHTERS("Others", 6);

    private final String name;
    private final int level;

    private RuleSeverity(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Get rule serverity by name
     *
     * @param severity serverity name
     * @return rule serverity
     */
    public static RuleSeverity getSeverityByName(String severity) {
        for (RuleSeverity ser : RuleSeverity.values()) {
            if (ser.getName().equalsIgnoreCase(severity)) {
                return ser;
            }
        }
        return RuleSeverity.OTHTERS;
    }

    /**
     * Get rule serverity by level
     *
     * @param severity serverity level
     * @return rule serverity
     */
    public static RuleSeverity getSeverityByLevel(int level) {
        for (RuleSeverity ser : RuleSeverity.values()) {
            if (ser.getLevel() == level) {
                return ser;
            }
        }
        return RuleSeverity.OTHTERS;
    }
}
