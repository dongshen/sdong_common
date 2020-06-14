package sdong.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class logbackTest {

    private static final Logger LOG = LoggerFactory.getLogger(logbackTest.class);

    @Test
    public void LogbackTest() {
        LOG.trace("This will be printed on trace");
        LOG.debug("This will be printed on debug");
        LOG.info("This will be printed on info");
        LOG.warn("This will be printed on warn");
        LOG.error("This will be printed on error");
    }
}