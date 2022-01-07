package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import sdong.common.bean.rules.conditional.Conditional;
import sdong.common.bean.rules.conditional.ConditionalNode;
import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class JsonUtilTest {
    private static final Logger LOG = LogManager.getLogger(JsonUtilTest.class);

    @Test
    public void testJsonStringToObject() {
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
    public void testObjectToJsonString() {
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
}
