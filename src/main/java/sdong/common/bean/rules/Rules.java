package sdong.common.bean.rules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Rules {
    @SerializedName(value = RuleJsonConstants.RULES_ENTRY_POINT)
    private List<EntryPointRule> entryPointRules;

    @SerializedName(value = RuleJsonConstants.RULES_TAINT_SOURCE)
    private List<TaintSourceRule> taintSourceRules;

    @SerializedName(value = RuleJsonConstants.RULES_TAINT_PASSTHROUGH)
    private List<TaintPassThroughRule> taintPassThroughRules;

    @SerializedName(value = RuleJsonConstants.RULES_TAINT_SINK)
    private List<TaintSinkRule> taintSinkRules;

    @SerializedName(value = RuleJsonConstants.RULES_TAINT_CLEANSE)
    private List<TaintCleanseRule> taintCleanesRules;

    public List<TaintSourceRule> getTaintSourceRules() {
        return taintSourceRules;
    }

    public void setTaintSourceRules(List<TaintSourceRule> taintSourceRules) {
        this.taintSourceRules = taintSourceRules;
    }

    public void addTaintSourceRule(TaintSourceRule sourceRule) {
        if (getTaintSourceRules() == null) {
            setTaintSourceRules(new ArrayList<TaintSourceRule>());
        }
        getTaintSourceRules().add(sourceRule);
    }

    public List<TaintPassThroughRule> getTaintPassThroughRules() {
        return taintPassThroughRules;
    }

    public void setTaintPassThroughRules(List<TaintPassThroughRule> taintPassThroughRules) {
        this.taintPassThroughRules = taintPassThroughRules;
    }

    public void addTaintPassThroughRule(TaintPassThroughRule passThroughRule) {
        if (getTaintPassThroughRules() == null) {
            setTaintPassThroughRules(new ArrayList<TaintPassThroughRule>());
        }
        getTaintPassThroughRules().add(passThroughRule);
    }

    public List<TaintSinkRule> getTaintSinkRules() {
        return taintSinkRules;
    }

    public void setTaintSinkRules(List<TaintSinkRule> taintSinkRules) {
        this.taintSinkRules = taintSinkRules;
    }

    public void addTaintSinkRule(TaintSinkRule sinkRule) {
        if (getTaintSinkRules() == null) {
            setTaintSinkRules(new ArrayList<TaintSinkRule>());
        }
        getTaintSinkRules().add(sinkRule);
    }

    public List<TaintCleanseRule> getTaintCleanesRules() {
        return taintCleanesRules;
    }

    public void setTaintCleanesRules(List<TaintCleanseRule> taintCleanesRules) {
        this.taintCleanesRules = taintCleanesRules;
    }

    public void addTaintCleanesRule(TaintCleanseRule cleanesRule) {
        if (getTaintCleanesRules() == null) {
            setTaintCleanesRules(new ArrayList<TaintCleanseRule>());
        }
        getTaintCleanesRules().add(cleanesRule);
    }

    public boolean isEmpty() {
        return entryPointRules.isEmpty() && taintSourceRules.isEmpty() && taintPassThroughRules.isEmpty()
                && taintSinkRules.isEmpty()
                && taintCleanesRules.isEmpty();
    }

    public List<EntryPointRule> getEntryPointRules() {
        return entryPointRules;
    }

    public void setEntryPointRules(List<EntryPointRule> entryPointRules) {
        this.entryPointRules = entryPointRules;
    }

    public void addEntryPointRule(EntryPointRule entryPointRule) {
        if (getEntryPointRules() == null) {
            setEntryPointRules(new ArrayList<EntryPointRule>());
        }
        getEntryPointRules().add(entryPointRule);
    }

    public int getEntryPointRulesSize() {
        return entryPointRules == null ? 0 : entryPointRules.size();
    }

    public int getTaintSourceRulesSize() {
        return taintSourceRules == null ? 0 : taintSourceRules.size();
    }

    public int getTaintSinkRulesSize() {
        return taintSinkRules == null ? 0 : taintSinkRules.size();
    }

    public int getTaintThroughRulesSize() {
        return taintPassThroughRules == null ? 0 : taintPassThroughRules.size();
    }

    public int getTaintCleanesRulesSize() {
        return taintCleanesRules == null ? 0 : taintCleanesRules.size();
    }

    public int size() {
        return getEntryPointRulesSize() + getTaintSourceRulesSize() + getTaintSinkRulesSize()
                + getTaintThroughRulesSize() + getTaintCleanesRulesSize();
    }
}
