package sdong.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import sdong.common.utils.StringCommonFactor.ChildString;
import sdong.common.utils.StringCommonFactor.DiffSequence;

import org.junit.jupiter.api.Test;

import java.util.List;

public class StringCommonFactorTest {
	@Test
	public void testGetStringMaxCommmon_case1() {
		String str1 = "String a, b, c;";
		String str2 = "String a=null, b, c;";

		// str1, str2
		StringCommonFactor factor = new StringCommonFactor(str1, str2);
		factor.printStringGraph();
		factor.printStringDistance();

		List<ChildString> childStringList = factor.getChildStringList();
		factor.printChildStringList(childStringList);

		assertEquals(6, childStringList.size());

		List<ChildString> matchList = factor.getMatchList();
		assertEquals(2, matchList.size());
		assertEquals(8, matchList.get(0).length);
		assertEquals(7, matchList.get(1).length);

		List<DiffSequence> changeList = factor.getChangeList(matchList);
		assertEquals(1, changeList.size());
		DiffSequence change = changeList.get(0);
		assertEquals(StringCommonFactor.ACTION_INSERT, change.action);
		assertEquals(8, change.start);
		assertEquals("=null", change.content);

		// str2, str1
		factor = new StringCommonFactor(str2, str1);
		factor.printStringGraph();
		factor.printStringDistance();

		childStringList = factor.getChildStringList();
		factor.printChildStringList(childStringList);

		assertEquals(7, childStringList.size());

		matchList = factor.getMatchList();
		assertEquals(2, matchList.size());
		assertEquals(8, matchList.get(0).length);
		assertEquals(7, matchList.get(1).length);

		changeList = factor.getChangeList(matchList);
		assertEquals(1, changeList.size());
		change = changeList.get(0);
		assertEquals(StringCommonFactor.ACTION_REMOVE, change.action);
		assertEquals(8, change.start);
		assertEquals(12, change.end);
		assertEquals("", change.content);
	}

	@Test
	public void testGetStringMaxCommmon_case2() {
		String str1 = "String a, b, c;";
		String str2 = "String a, b, c = null;";

		// str1, str2
		StringCommonFactor factor = new StringCommonFactor(str1, str2);
		factor.printStringGraph();
		factor.printStringDistance();

		List<ChildString> childStringList = factor.getChildStringList();
		factor.printChildStringList(childStringList);
		assertEquals(12, childStringList.size());

		List<ChildString> matchList = factor.getMatchList();
		assertEquals(2, matchList.size());
		assertEquals(14, matchList.get(0).length);
		assertEquals(1, matchList.get(1).length);

		List<DiffSequence> changeList = factor.getChangeList(matchList);
		assertEquals(1, changeList.size());
		DiffSequence change = changeList.get(0);
		assertEquals(StringCommonFactor.ACTION_INSERT, change.action);
		assertEquals(14, change.start);
		assertEquals(" = null", change.content);

		// str2, str1
		factor = new StringCommonFactor(str2, str1);
		factor.printStringGraph();
		factor.printStringDistance();

		childStringList = factor.getChildStringList();
		factor.printChildStringList(childStringList);
		assertEquals(12, childStringList.size());

		matchList = factor.getMatchList();
		assertEquals(2, matchList.size());
		assertEquals(14, matchList.get(0).length);
		assertEquals(1, matchList.get(1).length);

		changeList = factor.getChangeList(matchList);
		assertEquals(1, changeList.size());
		change = changeList.get(0);
		assertEquals(StringCommonFactor.ACTION_REMOVE, change.action);
		assertEquals(14, change.start);
		assertEquals(20, change.end);
		assertEquals("", change.content);

	}

	@Test
	public void testGetStringMaxCommmon_case3() {
		String str1 = "(void)VOS_memcpy_s(v1,v2,v3,v4);";
		String str2 = "ret = VOS_memcpy_s(v1,v2,v3,v4);";
		StringCommonFactor factor = new StringCommonFactor(str1, str2);
		factor.printStringGraph();
		factor.printStringDistance();

		List<ChildString> childStringList = factor.getChildStringList();
		factor.printChildStringList(childStringList);
		
		assertEquals(10, childStringList.size());
		List<ChildString> matchList = factor.getMatchList();
		assertEquals(1, matchList.size());
		assertEquals(26, matchList.get(0).length);

		List<DiffSequence> changeList = factor.getChangeList(matchList);
		assertEquals(2, changeList.size());
		DiffSequence change = changeList.get(0);
		assertEquals(StringCommonFactor.ACTION_REMOVE, change.action);
		assertEquals(0, change.start);
		assertEquals(5, change.end);
		assertEquals("", change.content);

		change = changeList.get(1);
		assertEquals(StringCommonFactor.ACTION_INSERT, change.action);
		assertEquals(0, change.start);
		assertEquals(0, change.end);
		assertEquals("ret = ", change.content);

		// str2, str1
		factor = new StringCommonFactor(str2, str1);
		factor.printStringGraph();
		factor.printStringDistance();

		childStringList = factor.getChildStringList();
		factor.printChildStringList(childStringList);
		
		assertEquals(14, childStringList.size());

		matchList = factor.getMatchList();
		assertEquals(1, matchList.size());
		assertEquals(26, matchList.get(0).length);

		changeList = factor.getChangeList(matchList);

		assertEquals(2, changeList.size());
		change = changeList.get(0);
		assertEquals(StringCommonFactor.ACTION_REMOVE, change.action);
		assertEquals(0, change.start);
		assertEquals(5, change.end);
		assertEquals("", change.content);

		change = changeList.get(1);
		assertEquals(StringCommonFactor.ACTION_INSERT, change.action);
		assertEquals(0, change.start);
		assertEquals(0, change.end);
		assertEquals("(void)", change.content);
	}
}
