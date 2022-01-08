package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RuleInfo {
    @SerializedName(value = RuleJsonConstants.RULE_ID)
    private String ruleId;

    @SerializedName(value = RuleJsonConstants.RULE_NAME)
    private String ruleName;

    @SerializedName(value = RuleJsonConstants.RULE_NOTE)
    private String note;
    
    private transient RuleType ruleType = RuleType.BASE;

    @SerializedName(value = RuleJsonConstants.REF_INFOS)
    private List<RuleReferenceInfo> refInfos;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
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

    public List<RuleReferenceInfo> getRefInfos() {
        return refInfos;
    }

    public void setRefInfos(List<RuleReferenceInfo> refInfos) {
        this.refInfos = refInfos;
    }

    public void addRefInfo(RuleReferenceInfo refInfo) {
        if (getRefInfos() == null) {
            setRefInfos(new ArrayList<RuleReferenceInfo>());
        }
        getRefInfos().add(refInfo);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
