package sdong.common.utils;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.utils.StringCommonFactor.ChildString;

public class StringCommonFactorTest {
	private static final Logger log = LoggerFactory.getLogger(StringCommonFactorTest.class);

	@Test
	public void testGetStringMaxCommmon_case1() {
		String str1 = "String a, b, c;";
		String str2 = "String a=null, b, c;";
		StringCommonFactor factor = new StringCommonFactor(str1, str2);
		List<ChildString> childStrings = factor.getChildString();

		// 返回最大公因子字符串
		log.info("size=" + childStrings.size());
		assertEquals(9,childStrings.size());
		for (ChildString s : childStrings) {
			// log.info("'" + str1.substring(s.start, s.start + s.maxLength) + "'");
			log.info(s.toString() + "'" + str2.substring(s.x, s.x + s.length) + "'");
		}
		
		Map<Integer, ChildString> map = factor.getMap();
		assertEquals(2, map.size());
		assertEquals(8, map.get(0).length);
		assertEquals(7, map.get(13).length);
	}
	

	

	@Test
	public void testGetStringMaxCommmon_case2() {
		String str1 = "String a = null, b, d;";
		String str2 = "String a, b = null, c;";
		
		StringCommonFactor factor = new StringCommonFactor(str1, str2);
		List<ChildString> childStrings = factor.getChildString();

		// 返回最大公因子字符串
		log.info("size=" + childStrings.size());
		for (ChildString s : childStrings) {
			// log.info("'" + str1.substring(s.start, s.start + s.maxLength) + "'");
			log.info(s.toString() + "'" + str2.substring(s.x, s.x + s.length) + "'");
		}
		assertEquals(28,childStrings.size());
		
		Map<Integer, ChildString> map = factor.getMap();
		assertEquals(4, map.size());
		assertEquals(8, map.get(0).length);
		assertEquals(3, map.get(8).length);
		assertEquals(2, map.get(18).length);
		assertEquals(1, map.get(21).length);

	}

	@Test
	public void testGetStringMaxCommmon_case3() {
		String str1 = "(void)VOS_memcpy_s(v1,v2,v3,v4);";
		String str2 = "ret = memcpy_s(v1,v2,v3,v4);";
		StringCommonFactor factor = new StringCommonFactor(str1, str2);
		List<ChildString> childStrings = factor.getChildString();

		// 返回最大公因子字符串
		log.info("size=" + childStrings.size());
		for (ChildString s : childStrings) {
			log.info("'" + str1.substring(s.x, s.x + s.length) + "'");

		}

	}
}
