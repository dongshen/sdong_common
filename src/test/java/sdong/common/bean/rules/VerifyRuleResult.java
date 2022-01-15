package sdong.common.bean.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

public class VerifyRuleResult {
    public static void verifyRuleSet(RuleSet ruleSet) {
        RulePackage rulePackage = ruleSet.getRulePackage();
        assertEquals("Fortify Secure Coding Rules, C/C++", rulePackage.getName());
        assertEquals("2021.12.22", rulePackage.getVersion());
        assertEquals("CPP", rulePackage.getLanguage());
        assertEquals("fortify C/C++ rules", rulePackage.getDescription());
        assertEquals("EN", rulePackage.getLocale());
        assertEquals(11, ruleSet.getRules().size());

        testTaintSourceRules(ruleSet.getRules().getTaintSourceRules());
        testTaintPassThroughRules(ruleSet.getRules().getTaintPassThroughRules());
        testTaintSinkRules(ruleSet.getRules().getTaintSinkRules());
        testTaintCleanseRules(ruleSet.getRules().getTaintCleanesRules());
    }

    public static void testTaintSourceRules(List<TaintSourceRule> rulelist) {
        assertEquals(3, rulelist.size());
        for (TaintSourceRule rule : rulelist) {
            if (rule.getRuleId().equals("DataflowSourceRuleId_1")) {
                // assertEquals(RuleType.TAINT_SOURCE, rule.getRuleType());
                List<RuleReferenceInfo> metaInfos = rule.getRefInfos();
                assertEquals(2, metaInfos.size());
                for (RuleReferenceInfo metaInfo : metaInfos) {
                    if (metaInfo.getKey().equals("inputsource")) {
                        assertEquals("Stream", metaInfo.getValue());
                    } else if (metaInfo.getKey().equals("package")) {
                        assertEquals("C Core", metaInfo.getValue());
                    } else {
                        fail("wrong value for：" + metaInfo.getKey());
                    }
                }

                TaintSource source = rule.getSource();
                assertEquals("+STREAM,+NOT_NULL_TERMINATED", source.getTaintFlags());
                assertEquals("1", source.getOutArgs());

                List<FunctionIdentifier> functionList = rule.getFunctions();
                assertEquals(1, functionList.size());
                assertEquals("read|pread|pread64|readv", functionList.get(0).getFunctionName().getPattern());
                assertEquals(false, functionList.get(0).getParameters().isVarArgs());
            } else if (rule.getRuleId().equals("DataflowSourceRuleId_2")) {
                // assertEquals(RuleType.TAINT_SOURCE, rule.getRuleType());
                List<RuleReferenceInfo> metaInfos = rule.getRefInfos();
                assertEquals(2, metaInfos.size());
                for (RuleReferenceInfo metaInfo : metaInfos) {
                    if (metaInfo.getKey().equals("inputsource")) {
                        assertEquals("Standard Input Stream", metaInfo.getValue());
                    } else if (metaInfo.getKey().equals("package")) {
                        assertEquals("C Core", metaInfo.getValue());
                    } else {
                        fail("wrong value for：" + metaInfo.getKey());
                    }
                }

                TaintSource source = rule.getSource();
                assertEquals("+STDIN,+NO_NEW_LINE", source.getTaintFlags());
                assertEquals("return", source.getOutArgs());

                List<FunctionIdentifier> functionList = rule.getFunctions();
                assertEquals(1, functionList.size());
                assertEquals("readline", functionList.get(0).getFunctionName().getValue());
                assertEquals(false, functionList.get(0).getParameters().isVarArgs());
            } else if (rule.getRuleId().equals("DataflowSourceRuleId_3")) {
                // assertEquals(RuleType.TAINT_SOURCE, rule.getRuleType());
                List<RuleReferenceInfo> metaInfos = rule.getRefInfos();
                assertEquals(1, metaInfos.size());
                assertEquals("package", metaInfos.get(0).getKey());
                assertEquals("CPP Core", metaInfos.get(0).getValue());

                TaintSource source = rule.getSource();
                assertEquals("+STREAM,+NOT_NULL_TERMINATED", source.getTaintFlags());
                assertEquals("1", source.getOutArgs());

                List<FunctionIdentifier> functionList = rule.getFunctions();
                assertEquals(1, functionList.size());
                assertEquals("operator>>", functionList.get(0).getFunctionName().getValue());

                List<FunctionParameterType> parameters = functionList.get(0).getParameters().getParameterTypes();
                assertEquals(1, parameters.size());
                assertEquals("std::basic_istream*", parameters.get(0).getParamType().getValue());
                assertEquals(0, parameters.get(0).getParamInd());
                assertEquals(false, functionList.get(0).getParameters().isVarArgs());
            } else {
                fail("wrong rule!");
            }
        }
    }

    public static void testTaintPassThroughRules(List<TaintPassThroughRule> rulelist) {
        assertEquals(3, rulelist.size());
        for (TaintPassThroughRule rule : rulelist) {
            if (rule.getRuleId().equals("DataflowPassthroughRuleId_1")) {
                // assertEquals(RuleType.TAINT_SOURCE, rule.getRuleType());
                List<RuleReferenceInfo> metaInfos = rule.getRefInfos();
                assertEquals(1, metaInfos.size());
                for (RuleReferenceInfo metaInfo : metaInfos) {
                    if (metaInfo.getKey().equals("package")) {
                        assertEquals("C Core", metaInfo.getValue());
                    } else {
                        fail("wrong value for：" + metaInfo.getKey());
                    }
                }

                TaintPassThrough passthrough = rule.getPassthrough();
                assertEquals("0,2", passthrough.getInArgs());
                assertEquals("return", passthrough.getOutArgs());

                List<FunctionIdentifier> functionList = rule.getFunctions();
                assertEquals(1, functionList.size());
                assertEquals("strtrns", functionList.get(0).getFunctionName().getValue());
                assertEquals(false, functionList.get(0).getParameters().isVarArgs());
            } else if (rule.getRuleId().equals("DataflowPassthroughRuleId_2")) {
                // assertEquals(RuleType.TAINT_SOURCE, rule.getRuleType());
                List<RuleReferenceInfo> metaInfos = rule.getRefInfos();
                assertEquals(1, metaInfos.size());
                for (RuleReferenceInfo metaInfo : metaInfos) {
                    if (metaInfo.getKey().equals("package")) {
                        assertEquals("C Core", metaInfo.getValue());
                    } else {
                        fail("wrong value for：" + metaInfo.getKey());
                    }
                }

                TaintPassThrough passthrough = rule.getPassthrough();
                assertEquals("0", passthrough.getInArgs());
                assertEquals("2...", passthrough.getOutArgs());

                List<FunctionIdentifier> functionList = rule.getFunctions();
                assertEquals(1, functionList.size());
                assertEquals("((vs)|(vf)|(f)|(s))scanf", functionList.get(0).getFunctionName().getPattern());
                assertEquals(false, functionList.get(0).getParameters().isVarArgs());
            } else if (rule.getRuleId().equals("DataflowPassthroughRuleId_3")) {
                // assertEquals(RuleType.TAINT_SOURCE, rule.getRuleType());
                List<RuleReferenceInfo> metaInfos = rule.getRefInfos();
                assertEquals(1, metaInfos.size());
                assertEquals("package", metaInfos.get(0).getKey());
                assertEquals("C Core", metaInfos.get(0).getValue());

                TaintPassThrough passthrough = rule.getPassthrough();
                assertEquals("3...", passthrough.getInArgs());
                assertEquals("0", passthrough.getOutArgs());

                List<FunctionIdentifier> functionList = rule.getFunctions();
                assertEquals(1, functionList.size());
                assertEquals("swprintf", functionList.get(0).getFunctionName().getValue());
                assertEquals(true, functionList.get(0).getParameters().isVarArgs());

                List<FunctionParameterType> parameters = functionList.get(0).getParameters().getParameterTypes();
                assertEquals(3, parameters.size());
                for (FunctionParameterType parameter : parameters) {
                    if (parameter.getParamInd() == 0) {
                        assertEquals("long*", parameter.getParamType().getValue());
                    } else if (parameter.getParamInd() == 1) {
                        assertEquals("unsigned int", parameter.getParamType().getValue());
                    } else if (parameter.getParamInd() == 2) {
                        assertEquals("long*", parameter.getParamType().getValue());
                    } else {
                        fail("wrong parameter");
                    }
                }
            } else {
                fail("wrong rule!");
            }
        }
    }

    public static void testTaintSinkRules(List<TaintSinkRule> rulelist) {
        assertEquals(3, rulelist.size());
        for (TaintSinkRule rule : rulelist) {
            if (rule.getRuleId().equals("DataflowSinkRuleId_1")) {
                // assertEquals(RuleType.TAINT_SOURCE, rule.getRuleType());
                List<RuleReferenceInfo> metaInfos = rule.getRefInfos();
                assertEquals(1, metaInfos.size());
                for (RuleReferenceInfo metaInfo : metaInfos) {
                    if (metaInfo.getKey().equals("package")) {
                        assertEquals("C WinAPI", metaInfo.getValue());
                    } else {
                        fail("wrong value for：" + metaInfo.getKey());
                    }
                }

                List<TaintSink> sinks = rule.getTaintSinks();
                assertEquals(1, sinks.size());

                TaintSink sink = sinks.get(0);
                assertEquals("2", sink.getInArgs());
                assertEquals("VALIDATED_BUFFER_OVERFLOW",sink.getConditional().getCondNot().getTaintFlag());

                List<FunctionIdentifier> functionList = rule.getFunctions();
                assertEquals(1, functionList.size());
                assertEquals("RaedFiledEx", functionList.get(0).getFunctionName().getValue());
                assertEquals(false, functionList.get(0).getParameters().isVarArgs());

                Vulnerability vuln = rule.getVulnerability();
                assertEquals("Input Validation and Representation", vuln.getCategory());
                assertEquals("Buffer Overflow", vuln.getSubCategory());
                assertEquals(RuleSeverity.CRITICAL, vuln.getSeverity());
            } else if (rule.getRuleId().equals("DataflowSinkRuleId_2")) {
                // assertEquals(RuleType.TAINT_SOURCE, rule.getRuleType());
                assertEquals("\n            fwscanf, swscanf, vfwscanf, vswscanf\n                        ",
                        rule.getRuleInfo().getNote());
                List<RuleReferenceInfo> metaInfos = rule.getRefInfos();
                assertEquals(1, metaInfos.size());
                for (RuleReferenceInfo metaInfo : metaInfos) {
                    if (metaInfo.getKey().equals("package")) {
                        assertEquals("C WinAPI", metaInfo.getValue());
                    } else {
                        fail("wrong value for：" + metaInfo.getKey());
                    }
                }

                List<TaintSink> sinks = rule.getTaintSinks();
                assertEquals(1, sinks.size());
                TaintSink sink = sinks.get(0);
                assertEquals("1", sink.getInArgs());
                assertEquals("VALIDATED_FORMAT_STRING",sink.getConditional().getCondNot().getTaintFlag());

                List<FunctionIdentifier> functionList = rule.getFunctions();
                assertEquals(1, functionList.size());
                assertEquals("((fw)|(sw)|(vfw)|(vsw))scanf", functionList.get(0).getFunctionName().getPattern());
                assertEquals(false, functionList.get(0).getParameters().isVarArgs());

                Vulnerability vuln = rule.getVulnerability();
                assertEquals("Input Validation and Representation", vuln.getCategory());
                assertEquals("Format String", vuln.getSubCategory());
                assertEquals(RuleSeverity.CRITICAL, vuln.getSeverity());
            } else if (rule.getRuleId().equals("DataflowSinkRuleId_3")) {
                // assertEquals(RuleType.TAINT_SOURCE, rule.getRuleType());
                assertEquals("windows api specific prototype", rule.getRuleInfo().getNote());
                List<RuleReferenceInfo> metaInfos = rule.getRefInfos();
                assertEquals(1, metaInfos.size());
                assertEquals("package", metaInfos.get(0).getKey());
                assertEquals("C Core", metaInfos.get(0).getValue());

                List<TaintSink> sinks = rule.getTaintSinks();
                assertEquals(1, sinks.size());
                TaintSink sink = sinks.get(0);
                assertEquals("1", sink.getInArgs());
                assertEquals("VALIDATED_FORMAT_STRING",sink.getConditional().getCondNot().getTaintFlag());

                List<FunctionIdentifier> functionList = rule.getFunctions();
                assertEquals(1, functionList.size());
                assertEquals("swprintf", functionList.get(0).getFunctionName().getValue());
                assertEquals(false, functionList.get(0).getParameters().isVarArgs());

                List<FunctionParameterType> parameters = functionList.get(0).getParameters().getParameterTypes();
                assertEquals(2, parameters.size());
                for (FunctionParameterType parameter : parameters) {
                    if (parameter.getParamInd() == 0) {
                        assertEquals("unsigned short*", parameter.getParamType().getValue());
                    } else if (parameter.getParamInd() == 1) {
                        assertEquals("unsigned short*", parameter.getParamType().getValue());
                    } else {
                        fail("wrong parameter");
                    }
                }

                Vulnerability vuln = rule.getVulnerability();
                assertEquals("Input Validation and Representation", vuln.getCategory());
                assertEquals("Format String", vuln.getSubCategory());
                assertEquals(RuleSeverity.CRITICAL, vuln.getSeverity());
            } else {
                fail("wrong rule!");
            }
        }
    }

    public static void testTaintCleanseRules(List<TaintCleanseRule> rulelist) {
        assertEquals(2, rulelist.size());
        for (TaintCleanseRule rule : rulelist) {
            if (rule.getRuleId().equals("DataflowCleanseRuleId_1")) {
                // assertEquals(RuleType.TAINT_SOURCE, rule.getRuleType());
                assertEquals(
                        "\n            Removing all the tainted objects from the data structure untaints the data structure\n                        ",
                        rule.getRuleInfo().getNote());
                List<RuleReferenceInfo> metaInfos = rule.getRefInfos();
                assertEquals(1, metaInfos.size());
                for (RuleReferenceInfo metaInfo : metaInfos) {
                    if (metaInfo.getKey().equals("package")) {
                        assertEquals("CPP STL DataStructures", metaInfo.getValue());
                    } else {
                        fail("CPP STL DataStructures" + metaInfo.getKey());
                    }
                }

                TaintCleanse cleanse = rule.getCleanse();
                assertEquals("this", cleanse.getOutArgs());

                List<FunctionIdentifier> functionList = rule.getFunctions();
                assertEquals(1, functionList.size());
                assertEquals("(std)?", functionList.get(0).getNamespaceName().getPattern());
                assertEquals("list", functionList.get(0).getClassName().getValue());
                assertEquals("clear", functionList.get(0).getFunctionName().getValue());
                assertEquals(false, functionList.get(0).getParameters().isVarArgs());
            } else if (rule.getRuleId().equals("DataflowCleanseRuleId_2")) {
                // assertEquals(RuleType.TAINT_SOURCE, rule.getRuleType());
                assertEquals(
                        "\n            Removing all the tainted objects from the data structure untaints the data structure\n                        ",
                        rule.getRuleInfo().getNote());
                List<RuleReferenceInfo> metaInfos = rule.getRefInfos();
                assertEquals(1, metaInfos.size());
                for (RuleReferenceInfo metaInfo : metaInfos) {
                    if (metaInfo.getKey().equals("package")) {
                        assertEquals("CPP STL DataStructures", metaInfo.getValue());
                    } else {
                        fail("wrong value for：" + metaInfo.getKey());
                    }
                }

                TaintCleanse cleanse = rule.getCleanse();
                assertEquals("this", cleanse.getOutArgs());

                List<FunctionIdentifier> functionList = rule.getFunctions();
                assertEquals(1, functionList.size());
                assertEquals("(std)?", functionList.get(0).getNamespaceName().getPattern());
                assertEquals("(vector)|(set)|(multiset)|(map)|(multimap)",
                        functionList.get(0).getClassName().getPattern());
                assertEquals("clear", functionList.get(0).getFunctionName().getValue());
                assertEquals(false, functionList.get(0).getParameters().isVarArgs());
            } else {
                fail("wrong rule!");
            }
        }
    }
}
