package sdong.common.bean.rules.conditional;

import com.google.common.base.Optional;

import sdong.common.bean.rules.RuleJsonConstants;
import sdong.common.utils.CommonUtil;

/**
 * Conditional type enum
 */
public enum ConditionalType {
    AND(RuleJsonConstants.CONDITIONAL_AND),
    OR(RuleJsonConstants.CONDITIONAL_OR),
    NOT(RuleJsonConstants.CONDITIONAL_NOT),
    TAINT_FLAG(RuleJsonConstants.TAINT_FLAG),
    IS_TYPE(RuleJsonConstants.CONDITIONAL_CON_IS_TYPE),
    IS_CONSTANT(RuleJsonConstants.CONDITIONAL_CON_IS_CONSTANT),
    CONSTANT_EQ(RuleJsonConstants.CONDITIONAL_CONSTANT_EQ),
    CONSTANT_GT(RuleJsonConstants.CONDITIONAL_CONSTANT_GT),
    CONSTANT_LT(RuleJsonConstants.CONDITIONAL_CONSTANT_LT),
    CONSTANT_MATCHES(RuleJsonConstants.CONDITIONAL_CONSTANT_MATCHES),
    OTHTERS("Others");

    private final String type;

    private ConditionalType(String ruleType) {
        this.type = ruleType;
    }

    public String getType() {
        return type;
    }

    /**
     * Get Conditional type
     *
     * @param conditionalType type name
     * @return type
     */
    public static ConditionalType getConditionalType(String conditionalType) {
        Optional<ConditionalType> option = CommonUtil.getEnum(ConditionalType.class, conditionalType);
        if (option.isPresent()) {
            return option.get();
        } else {
            return ConditionalType.OTHTERS;
        }
    }
}
