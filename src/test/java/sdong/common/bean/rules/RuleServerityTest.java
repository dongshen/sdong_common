package sdong.common.bean.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RuleServerityTest { 
    @Test
    void testGetSeverityByName(){
        assertEquals(RuleSeverity.FATAL, RuleSeverity.getSeverityByName("Fatal"));   
        assertEquals(RuleSeverity.FATAL, RuleSeverity.getSeverityByName("fatal"));   
        assertEquals(RuleSeverity.NORMAL, RuleSeverity.getSeverityByLevel(4));   
        assertEquals(RuleSeverity.OTHTERS, RuleSeverity.getSeverityByName("fatalx")); 
        assertEquals(RuleSeverity.OTHTERS, RuleSeverity.getSeverityByLevel(8));
    }
}
