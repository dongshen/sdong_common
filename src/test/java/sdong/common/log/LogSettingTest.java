package sdong.log;

import static org.junit.Assert.fail;

import ch.qos.logback.classic.Level;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogSettingTest {
	//private static final Logger LOG = LoggerFactory.getLogger(LogSettingTest.class);

	@Test
	public void testLog() {
		LogSetting.getLogFile();

		Logger log =  LogSetting.getLogger("test","logs/test.log",Level.INFO);
		LogSetting.getLogFile();

		log.info("Test log info");
		log.error("Test log error");
		log.warn("Test log warn");
		log.debug("Test log debug");
		Logger LOG = LoggerFactory.getLogger(LogSettingTest.class);
		LOG.info("log_LOG_test");

		LOG.info("stop:{}",LogSetting.detachAppender("test"));
		LogSetting.getLogFile();
		
		Logger logEngine =  LogSetting.getLogger("engine","logs/engine.log",Level.INFO);
		LogSetting.getLogFile();
		logEngine.info("Test logEngine info");
		logEngine.error("Test logEngine error");
		logEngine.warn("Test logEngine warn");
		logEngine.debug("Test logEngine debug");

		LOG.info("log_LOG_engine");

		LogSetting.getLogFile();
	}
	
	@Test
	public void testGetReferenceList1() {
		String file = "input/pdf/arxiv_1807.00515.pdf";
		//LOG.info("Get refs:{}", file);
	}
}
