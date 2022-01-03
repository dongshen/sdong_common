package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Rules {    
    @SerializedName(value = RuleJsonConstants.RULES_TAINT_SOURCE)
    private List<TaintSourceRule> taintSourceRules = new ArrayList<TaintSourceRule>();

    @SerializedName(value = RuleJsonConstants.RULES_TAINT_PASSTHROUGH)
    private List<TaintPassThroughRule> taintPassThroughRules = new ArrayList<TaintPassThroughRule>();

    @SerializedName(value = RuleJsonConstants.RULES_TAINT_SINK)
    private List<TaintSinkRule> taintSinkRules = new ArrayList<TaintSinkRule>();

    @SerializedName(value = RuleJsonConstants.RULES_TAINT_CLEANSE)
    private List<TaintCleanseRule> taintCleanesRules = new ArrayList<TaintCleanseRule>();

    public List<TaintSourceRule> getTaintSourceRules() {
        return taintSourceRules;
    }

    public void setTaintSourceRules(List<TaintSourceRule> taintSourceRules) {
        this.taintSourceRules = taintSourceRules;
    }

    public List<TaintPassThroughRule> getTaintPassThroughRules() {
        return taintPassThroughRules;
    }

    public void setTaintPassThroughRules(List<TaintPassThroughRule> taintPassThroughRules) {
        this.taintPassThroughRules = taintPassThroughRules;
    }

    public List<TaintSinkRule> getTaintSinkRules() {
        return taintSinkRules;
    }

    public void setTaintSinkRules(List<TaintSinkRule> taintSinkRules) {
        this.taintSinkRules = taintSinkRules;
    }

    public List<TaintCleanseRule> getTaintCleanesRules() {
        return taintCleanesRules;
    }

    public void setTaintCleanesRules(List<TaintCleanseRule> taintCleanesRules) {
        this.taintCleanesRules = taintCleanesRules;
    }

    public boolean isEmpty() {
        return taintSourceRules.isEmpty() && taintPassThroughRules.isEmpty() && taintSinkRules.isEmpty()
                && taintCleanesRules.isEmpty();
    }

    public int size() {
        return taintSourceRules.size() + taintPassThroughRules.size() + taintSinkRules.size()
                + taintCleanesRules.size();
    }
}
