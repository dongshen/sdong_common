package sdong.common.bean.rules;

import java.util.concurrent.ConcurrentHashMap;

/**
 * rule base
 */
public class RuleBase {
    private String RuleId = "";

    private String ruleName = "";

    private String note = "";

    private RuleType ruleType = RuleType.BASE;

    private ConcurrentHashMap<String, String> refInfos = new ConcurrentHashMap<String, String>();

    public String getRuleId() {
        return RuleId;
    }

    public void setRuleId(String ruleId) {
        RuleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public ConcurrentHashMap<String, String> getRefInfos() {
        return refInfos;
    }

    public void setRefInfos(ConcurrentHashMap<String, String> refInfos) {
        this.refInfos = refInfos;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
