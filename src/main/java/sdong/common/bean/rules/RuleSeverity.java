package sdong.common.bean.rules;

/**
 * Rule Severity enum
 */
public enum RuleSeverity {
    FATAL("Fatal", 1),
    CRITICAL("Critical", 2),
    MEDIUM("Medium", 3),
    NORMAL("Normal", 4),
    SUGGEST("Suggest", 5),
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
