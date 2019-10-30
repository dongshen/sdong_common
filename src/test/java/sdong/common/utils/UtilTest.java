package sdong.common.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class UtilTest {

	private static final Logger logger = LoggerFactory.getLogger(UtilTest.class);

	@Test
	public void testGetUUID() {
		for (int i = 0; i < 10; i++) {
			logger.info("UUID= " + Util.getUUID());
		}
	}

	@Test
	public void testGenerateUUIDSeq() {
		for (int i = 0; i < 10; i++) {
			logger.info("UUID= " + Util.generateUUIDSeq());
		}
	}

	@Test
	public void testGenerateMD5() {
		String content = "xxxx";
		String md5;
		try {
			md5 = Util.generateMD5(content);
			logger.info("md5=" + md5);
			assertEquals("ea416ed0759d46a8de58f63a59077499", md5);
		} catch (SdongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
