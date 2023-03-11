package sdong.common.utils;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class CommonUtilTest {
    private static final Logger LOG = LogManager.getLogger(CommonUtilTest.class);

    @Test
    void testGetUuid() {
        LOG.info("uuid: {}", CommonUtil.getUuid());
    }

    @Test
    void testGenerateType1UUID() {
        for (int i = 0; i < 10; i++) {
            LOG.info("Seq uuid: {}", CommonUtil.generateUuidSeq());
        }
    }

    @Test
    void testGetUlid() {
        for (int i = 0; i < 10; i++) {
            LOG.info("Seq uuid: {}", CommonUtil.getUlid());
        }
    }

    @Test
    void testGenerateUuidSeq() {
        LOG.info("Seq uuid: {}", CommonUtil.generateUuidSeq());
    }

    @Test
    void testShannonEntropy() {
        String inStr = "abcdefghijklmnopqrstuvwxyz";
        LOG.info("entropy:{}", CommonUtil.shannonEntropy(inStr));

        inStr = "c2L7yXeGvUyrPgYsDnWRRC1AYEXAMPLE";
        LOG.info("entropy:{}", CommonUtil.shannonEntropy(inStr));

        inStr = "abcdefghijklmnopqrst";
        LOG.info("entropy:{}", CommonUtil.shannonEntropy(inStr));
    }

    @Test
    void testCheckNumber() {
        String inStr = "0123456789";
        assertEquals(true, CommonUtil.checkNumber(inStr));

        inStr = "c2L7";
        assertEquals(false, CommonUtil.checkNumber(inStr));

        inStr = "12 3";
        assertEquals(false, CommonUtil.checkNumber(inStr));

        inStr = "";
        assertEquals(false, CommonUtil.checkNumber(inStr));

        inStr = null;
        assertEquals(false, CommonUtil.checkNumber(inStr));
    }

    @Test
    void testIsLinux() {
        assertEquals(false, CommonUtil.isLinux());
    }
}