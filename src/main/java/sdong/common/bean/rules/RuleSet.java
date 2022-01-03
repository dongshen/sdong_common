package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RuleSet {
    @SerializedName(value = RuleJsonConstants.RULE_PACKAGE)
    private RulePackage rulePackage = new RulePackage();

    @SerializedName(value = RuleJsonConstants.RULES)
    private Rules rules = new Rules();

    @SerializedName(value = RuleJsonConstants.RULE_MESSAGES)
    private List<ReportMessage> messages = new ArrayList<ReportMessage>();

    public RulePackage getRulePackage() {
        return rulePackage;
    }

    public void setRulePackage(RulePackage rulePackage) {
        this.rulePackage = rulePackage;
    }

    public Rules getRules() {
        return rules;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }

    public List<ReportMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ReportMessage> messages) {
        this.messages = messages;
    }
}
