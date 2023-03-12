package sdong.common.utils;

import static org.junit.jupiter.api.Assertions.fail;

import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Locale;

public class RunCommandUtilTest {
	private static final Logger LOG = LogManager.getLogger(RunCommandUtilTest.class);

    @Test
    void testRunCommand(){
        String cmd = "dir";
        try {
			String result = RunCommandUtil.runCommand(cmd);
			LOG.info("result=" + result);
		} catch (SdongException e) {
			LOG.error("Get exception:{}",e.getMessage());
			fail("should not have exception!");
		}
    }

	@Test
	public void testRunCommand_cov_manage_emit_listjson() {
		String cmd = "cov-manage-emit1 --dir D:/git/sdong/AST/input/coverityEmit list-json" ;

		try {
			String result = RunCommandUtil.runCommand(cmd);
			LOG.info("result=" + result);
		} catch (SdongException e) {
			LOG.error("Get exception:{}",e.getMessage());
			fail("should not have exception!");
		}
	}

	@Test
	public void testRunCommand_cov_manage_emit_print_definitions() {
		String cmd =  "cov-manage-emit --dir D:/git/sdong/AST/input/coverityEmit --tu 10 find . --print-definitions" ;

		try {
			String result = RunCommandUtil.runCommand(cmd);
			LOG.info("result=" + result);
		} catch (SdongException e) {
			LOG.error("Get exception:{}",e.getMessage());
			fail("should not have exception!");
		}
	}

	@Test
	public void testRunCommand_cov_manage_emit_print_debug() {
		String cmd =  "cov-manage-emit --dir D:/git/sdong/AST/input/coverityEmit --tu 10,32,33,34,35,36,37,38,39,40 find . --print-debug" ;

		try {
			String result = RunCommandUtil.runCommand(cmd);
			LOG.info("result=" + result);
		} catch (SdongException e) {
			LOG.error("Get exception:{}",e.getMessage());
			fail("should not have exception!");
		}
	}

	@Test
	public void testRunCommand_cov_manage_emit_print_source() {
		String cmd =  "cov-manage-emit --dir D:/git/sdong/AST/input/coverityEmit --tu 10  print-source" ;

		try {
			String result = RunCommandUtil.runCommand(cmd);
			LOG.info("result=" + result);
		} catch (SdongException e) {
			LOG.error("Get exception:{}",e.getMessage());
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
