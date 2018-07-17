package sdong.common.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testCheckIndentNum() {
		String line = "tree = TF_func:";
		String checkChar = " ";
		int intended = StringUtil.checkIndentNum(line, checkChar);
		assertEquals(0,intended);
		
		line= "  f = Function:";
		intended = StringUtil.checkIndentNum(line, checkChar);
		assertEquals(2,intended);
		
		line= "        return = scalar_type_t:";
		intended = StringUtil.checkIndentNum(line, checkChar);
		assertEquals(8,intended);
		
		line= "          kind = void";
		intended = StringUtil.checkIndentNum(line, checkChar);
		assertEquals(10,intended);
				
	}

}
