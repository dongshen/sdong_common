package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RuleSet {
    @SerializedName(value = RuleJsonConstants.RULE_PACKAGE)
    private RulePackage rulePackage;

    @SerializedName(value = RuleJsonConstants.RULES)
    private Rules rules;

    @SerializedName(value = RuleJsonConstants.RULE_MESSAGES)
    private List<ReportMessage> messages;

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

    public void addMessage(ReportMessage message) {
        if (getMessages() == null) {
            setMessages(new ArrayList<ReportMessage>());
        }
        getMessages().add(message);
    }
}
