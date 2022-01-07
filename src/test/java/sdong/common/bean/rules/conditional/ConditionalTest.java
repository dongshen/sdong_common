package sdong.common.bean.rules.conditional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.google.gson.Gson;

import sdong.common.exception.SdongException;
import sdong.common.utils.FileUtil;
import sdong.common.utils.JsonUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.List;

public class ConditionalTest {
    private static final Logger LOG = LogManager.getLogger(ConditionalTest.class);
    @Test
    public void testParseRuleConditioanalJson_Not() {
        String jsonFile = "input/rules/conditional/conditional_not.json";
        try {
            Conditional conditional = JsonUtil.jsonFileToObject(jsonFile, Conditional.class);

            LOG.info("Conditional:{}", conditional.toString());
            assertEquals("STRING_LENGTH", conditional.getCond().getCondNot().getTaintFlag());

            verify(conditional, jsonFile);
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("Should not get exception!");
        }
    }

    @Test
    public void testParseRuleConditioanalJson_And() {
        String jsonFile = "input/rules/conditional/conditional_and.json";
        try {
            Conditional conditional = JsonUtil.jsonFileToObject(jsonFile, Conditional.class);

            LOG.info("Conditional:{}", conditional.toString());
            assertEquals(2, conditional.getCond().getCondAnd().size());
            for (ConditionalNode node : conditional.getCond().getCondAnd()) {
                assertEquals("STRING_LENGTH", node.getTaintFlag());
            }

            verify(conditional, jsonFile);
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("Should not get exception!");
        }
    }

    @Test
    public void testParseRuleConditioanalJson_AndNot() {
        String jsonFile = "input/rules/conditional/conditional_andnot.json";
        try {
            Conditional conditional = JsonUtil.jsonFileToObject(jsonFile, Conditional.class);

            assertEquals(2, conditional.getCond().getCondAnd().size());
            int ind = 0;
            for (ConditionalNode node : conditional.getCond().getCondAnd()) {
                if (ind == 0) {
                    assertEquals(2, node.getCondAnd().size());
                    assertEquals("PRIVATE", node.getCondAnd().get(0).getTaintFlag());
                    assertEquals("STRING_LENGTH", node.getCondAnd().get(1).getCondNot().getTaintFlag());
                } else {
                    assertEquals("VALIDATED_PRIVACY_VIOLATION", node.getCondNot().getTaintFlag());
                }
                ind = ind + 1;
            }

            verify(conditional, jsonFile);
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("Should not get exception!");
        }
    }

    @Test
    public void testParseRuleConditioanalJson_complex_not() {
        String jsonFile = "input/rules/conditional/conditional_complex_not.json";
        try {
            Conditional conditional = JsonUtil.jsonFileToObject(jsonFile, Conditional.class);

            assertEquals(3, conditional.getCond().getCondAnd().size());
            int ind = 0;
            for (ConditionalNode node : conditional.getCond().getCondAnd()) {
                switch (ind) {
                    case 0:
                        assertEquals("PRIVATE", node.getTaintFlag());
                        break;
                    case 1:
                        assertEquals("VALIDATED_PRIVACY_VIOLATION", node.getCondNot().getTaintFlag());
                        break;
                    case 2:
                        List<ConditionalNode> nodes = node.getCondNot().getCondOr();
                        assertEquals(3, nodes.size());
                        CondIsType isType = nodes.get(0).getIsType();
                        assertEquals("0", isType.getArg());
                        assertEquals("javax.servlet.jsp", isType.getNamespaceName().getValue());
                        assertEquals("JspWriter", isType.getClassName().getValue());
                        isType = nodes.get(2).getIsType();
                        assertEquals("0", isType.getArg());
                        assertEquals("javax.faces.context", isType.getNamespaceName().getValue());
                        assertEquals("ResponseWriterWrapper", isType.getClassName().getValue());
                        break;
                    default:
                        break;
                }
                ind = ind + 1;
            }
            verify(conditional, jsonFile);
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("Should not get exception!");
        }
    }

    @Test
    public void testParseRuleConditioanalJson_ConstantMatches() {
        String jsonFile = "input/rules/conditional/conditional_ConstantMatches.json";
        try {
            Conditional conditional = JsonUtil.jsonFileToObject(jsonFile, Conditional.class);

            assertEquals(5, conditional.getCond().getCondAnd().size());
            int ind = 0;
            for (ConditionalNode node : conditional.getCond().getCondAnd()) {
                switch (ind) {
                    case 0:
                        assertEquals("PROCESS", node.getTaintFlag());
                        break;
                    case 1:
                        assertEquals("SHELL_PROCESS", node.getCondNot().getTaintFlag());
                        break;
                    case 2:
                        assertEquals("VALIDATED_COMMAND_INJECTION", node.getCondNot().getTaintFlag());
                        break;
                    case 3:
                        assertEquals("0", node.getIsConstant().getArg());
                        break;
                    case 4:
                        List<ConditionalNode> nodes = node.getCondOr();
                        assertEquals(2, nodes.size());
                        CondConstantMatches matches = nodes.get(0).getConstantMatches();
                        assertEquals("0", matches.getArg());
                        assertEquals("value", matches.getMode());
                        assertEquals("\\$", matches.getPattern());
                        matches = nodes.get(1).getConstantMatches();
                        assertEquals("0", matches.getArg());
                        assertEquals("value", matches.getMode());
                        assertEquals("%.*%", matches.getPattern());
                        break;
                    default:
                        break;
                }
                ind = ind + 1;
            }
            verify(conditional, jsonFile);
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("Should not get exception!");
        }
    }

    private void verify(Conditional conditional, String jsonFile) throws SdongException {
        Gson gs = new Gson();
        LOG.info(gs.toJson(conditional));
        assertEquals(FileUtil.readFileToString(jsonFile), gs.toJson(conditional));
    }
}
