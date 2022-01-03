package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * rule base
 */
public class RuleBase implements Irule{
    @SerializedName(value = RuleJsonConstants.RULE)
    RuleInfo ruleInfo = new RuleInfo();

    public RuleInfo getRuleInfo() {
        return ruleInfo;
    }

    public void setRuleInfo(RuleInfo ruleInfo) {
        this.ruleInfo = ruleInfo;
    }

    public String getRuleId() {
        return ruleInfo.getRuleId();
    }

    public void setRuleId(String ruleId) {
        ruleInfo.setRuleId(ruleId);
    }

    public String getRuleName() {
        return ruleInfo.getRuleName();
    }

    public void setRuleName(String ruleName) {
        ruleInfo.setRuleName(ruleName);
    }

    public RuleType getRuleType() {
        return ruleInfo.getRuleType();
    }

    public void setRuleType(RuleType ruleType) {
        ruleInfo.setRuleType(ruleType);
    }

    public List<RuleReferenceInfo> getRefInfos() {
        return ruleInfo.getRefInfos();
    }

    public void setRefInfos(List<RuleReferenceInfo> refInfos) {
        ruleInfo.setRefInfos(refInfos);
    }

    public String getNote() {
        return ruleInfo.getNote();
    }

    public void setNote(String note) {
        ruleInfo.setNote(note);
    }
}   
