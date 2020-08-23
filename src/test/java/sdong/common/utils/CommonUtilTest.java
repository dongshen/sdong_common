package sdong.common.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtilTest {
    private static final Logger LOG = LoggerFactory.getLogger(CommonUtilTest.class);
    @Test
    public void testGetUuid(){
        LOG.info("uuid: {}",CommonUtil.getUuid());
    }
}