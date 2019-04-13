package sdong.common.utils;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.utils.StringCommonFactor.ChildString;
import sdong.common.utils.StringCommonFactor.DiffSequence;

public class StringCommonFactorTest {
	private static final Logger log = LoggerFactory.getLogger(StringCommonFactorTest.class);

	@Test
	public void testGetStringMaxCommmon_case1() {
		String str1 = "String a, b, c;";
		String str2 = "String a=null, b, c;";

		// str1, str2
		StringCommonFactor factor = new StringCommonFactor(str1, str2);

		List<ChildString> childStringList = factor.getChildStringList();
		log.info("size=" + childStringList.size());
		for (ChildString s : childStringList) {
			log.info(s.toString() + "'" + str2.substring(s.y, s.y + s.length) + "'");
		}
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
		childStringList = factor.getChildStringList();
		log.info("size=" + childStringList.size());
		for (ChildString s : childStringList) {
			log.info(s.toString() + "'" + str1.substring(s.y, s.y + s.length) + "'");
		}
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
		String str1 = "String a = null, b, d;";
		String str2 = "String a, b = null, c;";

		StringCommonFactor factor = new StringCommonFactor(str1, str2);

		// 返回最大公因子字符串
		List<ChildString> childStringList = factor.getChildStringList();
		log.info("size=" + childStringList.size());
		for (ChildString s : childStringList) {
			log.info(s.toString() + "'" + str2.substring(s.y, s.y + s.length) + "'");
		}
		assertEquals(15, childStringList.size());

		List<ChildString> matchList = factor.getMatchList();
		assertEquals(4, matchList.size());
		assertEquals(8, matchList.get(0).length);
		assertEquals(3, matchList.get(8).length);
		assertEquals(2, matchList.get(18).length);
		assertEquals(1, matchList.get(21).length);

	}

	@Test
	public void testGetStringMaxCommmon_case3() {
		String str1 = "(void)VOS_memcpy_s(v1,v2,v3,v4);";
		String str2 = "ret = VOS_memcpy_s(v1,v2,v3,v4);";
		StringCommonFactor factor = new StringCommonFactor(str1, str2);

		List<ChildString> childStringList = factor.getChildStringList();
		log.info("size=" + childStringList.size());
		for (ChildString s : childStringList) {
			log.info(s.toString() + "'" + str2.substring(s.y, s.y + s.length) + "'");
		}
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
		childStringList = factor.getChildStringList();
		log.info("size=" + childStringList.size());
		for (ChildString s : childStringList) {
			log.info(s.toString() + "'" + str1.substring(s.y, s.y + s.length) + "'");
		}
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
