package sdong.common.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class RunCommandUtilTest {
	private static final Logger logger = LoggerFactory.getLogger(RunCommandUtilTest.class);

	@Test
	public void testRunCommand_cov_manage_emit_listjson() {
		String[] cmd = { "cmd", "/C", "cov-manage-emit --dir D:/git/sdong/AST/input/coverityEmit list-json" };

		try {
			String result = RunCommandUtil.runCommand(cmd);
			logger.info("result=" + result);
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not have exception!");
		}
	}

	@Test
	public void testRunCommand_cov_manage_emit_print_definitions() {
		String[] cmd = { "cmd", "/C", "cov-manage-emit --dir D:/git/sdong/AST/input/coverityEmit --tu 10 find . --print-definitions" };

		try {
			String result = RunCommandUtil.runCommand(cmd);
			logger.info("result=" + result);
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not have exception!");
		}
	}

	@Test
	public void testRunCommand_cov_manage_emit_print_debug() {
		String[] cmd = { "cmd", "/C", "cov-manage-emit --dir D:/git/sdong/AST/input/coverityEmit --tu 10 find . --print-debug" };

		try {
			String result = RunCommandUtil.runCommand(cmd);
			logger.info("result=" + result);
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not have exception!");
		}
	}
	
	@Test
	public void testRunCommand_cov_manage_emit_print_source() {
		String[] cmd = { "cmd", "/C", "cov-manage-emit --dir D:/git/sdong/AST/input/coverityEmit --tu 10  print-source" };

		try {
			String result = RunCommandUtil.runCommand(cmd);
			logger.info("result=" + result);
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not have exception!");
		}
	}
}
