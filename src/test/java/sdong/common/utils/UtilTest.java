package sdong.common.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilTest {

	private static final Logger logger = LoggerFactory.getLogger(UtilTest.class);

	@Test
	public void testGetUUID() {
		for (int i = 0; i < 10; i++) {
			logger.info("UUID= " + Util.getUUID());
		}
	}

	@Test
	public void testGetUUID_v1() {
		for (int i = 0; i < 10; i++) {
			logger.info("UUID= " + Util.getUUID_v1());
		}
	}

}
