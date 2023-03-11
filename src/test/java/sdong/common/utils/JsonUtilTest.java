package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import sdong.common.bean.rules.conditional.Conditional;
import sdong.common.bean.rules.conditional.ConditionalNode;
import sdong.common.bean.rules.json.validator.JsonValidatErrors;
import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.everit.json.schema.Schema;
import org.junit.Test;

import java.util.Optional;

public class JsonUtilTest {
    private static final Logger LOG = LogManager.getLogger(JsonUtilTest.class);

    @Test
    void testJsonStringToObject() {
        String condFile = "input/rules/conditional/conditional_complex_not.json";
        try {
            String condStr = FileUtil.readFileToString(condFile);
            Conditional node = JsonUtil.jsonStringToObject(condStr, Conditional.class);
            String convertStr = JsonUtil.objectToJsonString(node, Conditional.class);
            LOG.info("Conditional:{}", convertStr);
            assertEquals(condStr, convertStr);
        } catch (SdongException e) {
            LOG.error("Get exception:{}", e.getMessage());
            fail("should not have exception!");
        }
    }

    @Test
    void testObjectToJsonString() {
        String condFile = "input/rules/conditional/conditional_complex_not.json";
        try {
            String condStr = FileUtil.readFileToString(condFile);
            Conditional conditional = JsonUtil.jsonStringToObject(condStr, Conditional.class);
            String nodeStr = JsonUtil.objectToJsonString(conditional.getCond(), ConditionalNode.class);
            LOG.info("ConditionalNode:{}", nodeStr);
            ConditionalNode condNode = JsonUtil.jsonStringToObject(nodeStr, ConditionalNode.class);

            assertEquals(nodeStr, JsonUtil.objectToJsonString(condNode, ConditionalNode.class));
        } catch (SdongException e) {
            LOG.error("Get exception:{}", e.getMessage());
            fail("should not have exception!");
        }
    }

    @Test
    void testGetTaintRuleSchema() {
        try {
            Schema schema = JsonUtil.getDataFlowRulesSchema();
            assertEquals("The common dataflow rules schema for static analysis 1.0", schema.getTitle());
        } catch (SdongException e) {
            LOG.error("Get exception:{}", e.getMessage());
            fail("should not have exception!");
        }
    }

    @Test
    void testValidatTaintRule(){
        try {
            String rule = "input/rules/dataflow_rules_c.json";
            Optional<JsonValidatErrors> validat = JsonUtil.validatTaintRule(rule); 
            LOG.info(validat.isPresent());
            if(validat.isPresent()){
                LOG.error(validat.get());
            }
            assertEquals(false, validat.isPresent());            
        } catch (SdongException e) {
            LOG.error("Get exception:{}", e.getMessage());
            fail("should not have exception!");
        }
    }
}
