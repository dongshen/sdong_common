package sdong.common.bean.rules;


import com.google.common.base.Optional;

import sdong.common.utils.CommonUtil;

/**
 * Rule type enum
 */
public enum RuleType {
    BASE("Base"),
    ENTRY_POINT("EntryPointRule"),
    TAINT_SOURCE("TaintSourceRule"),
    TAINT_PASSTHROUGH("TaintPassthroughRule"),
    TAINT_SINK("TaintSinkRule"),
    TAINT_CLEANSE("TaintCleanseRule"),
    OTHTERS("Others");

    private final String ruleType;

    private RuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleType() {
        return ruleType;
    }
    
        /**
     * Get File type
     *
     * @param ruleType file type name
     * @return file type
     */
    public static RuleType getRuleTypeByTypeName(String ruleType) {
        Optional<RuleType> option = CommonUtil.getEnum(RuleType.class, ruleType);
        if (option.isPresent()) {
            return option.get();
        } else {
            return RuleType.OTHTERS;
        }
    }
}
