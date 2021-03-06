package sdong.common.utils;

import static org.junit.Assert.fail;

import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class RunCommandUtilTest {
	private static final Logger logger = LoggerFactory.getLogger(RunCommandUtilTest.class);

	@Test
	public void testRunCommand_cov_manage_emit_listjson() {
		String[] cmd = { "cmd", "/C", "cov-manage-emit1 --dir D:/git/sdong/AST/input/coverityEmit list-json" };

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
		String[] cmd = { "cmd", "/C",
				"cov-manage-emit --dir D:/git/sdong/AST/input/coverityEmit --tu 10 find . --print-definitions" };

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
		String[] cmd = { "cmd", "/C",
				"cov-manage-emit --dir D:/git/sdong/AST/input/coverityEmit --tu 10,32,33,34,35,36,37,38,39,40 find . --print-debug" };

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
		String[] cmd = { "cmd", "/C",
				"cov-manage-emit --dir D:/git/sdong/AST/input/coverityEmit --tu 10  print-source" };

		try {
			String result = RunCommandUtil.runCommand(cmd);
			logger.info("result=" + result);
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not have exception!");
		}
	}

	@Test
	public void testT() {
		String defaultEncodingName = System.getProperty("file.encoding");
		System.out.println("defaultEncodingName : " + defaultEncodingName);
		// in JDK 1.5+, will typically be "windows-1252"
		// First, get the Charset/encoding then convert to String.
		String defaultEncodingName2 = Charset.defaultCharset().name();
		System.out.println("defaultEncodingName2 : " + defaultEncodingName2);
		System.out.println("Charset.defaultCharset().displayName() : " + Charset.defaultCharset().displayName());
		// I'm told this circumlocution has the nice property you can even use
		// it in an unsigned Applet.
		String defaultEncodingName3 = new OutputStreamWriter(System.out).getEncoding();
		System.out.println("defaultEncodingName3 : " + defaultEncodingName3);

		System.out.println(Locale.getDefault());
	}
}
