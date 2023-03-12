package sdong.common.bean.rules;

import static org.junit.jupiter.api.Assertions.fail;

import sdong.common.exception.SdongException;
import sdong.common.utils.JsonUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class RuleSetTest {
    private static final Logger LOG = LogManager.getLogger(RuleSetTest.class);

    @Test
    void testParseRuleJson() {
        String jsonFile = "input/rules/dataflow_rules_c.json";
        try {
            RuleSet ruleSet = JsonUtil.jsonFileToObject(jsonFile, RuleSet.class);

            VerifyRuleResult.verifyRuleSet(ruleSet);
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("Should not get exception!");
        }
    }
}
