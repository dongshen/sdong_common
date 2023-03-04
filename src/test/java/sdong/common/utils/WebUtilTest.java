package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.File;


public class WebUtilTest {
    private static final Logger LOG = LogManager.getLogger(WebUtilTest.class);

    @Test
    public void testSaveWebPage() {
        try {
            //String web = "https://vulncat.fortify.com/zh-cn/weakness?codelang=Java%2FJSP&po=";
            String web = "https://vulncat.fortify.com/zh-cn/weakness?po=";
            String outputFile = "output/util/webutil/fortify";
            FileUtil.deleteFolder(outputFile);
            for (int i = 1; i <= 147; i++) {
                WebUtil.saveWebPage(web + i, outputFile + File.separator + "fortify" + i + ".html");
            }
            assertEquals(true, FileUtil.fileExist(outputFile));
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail("should not get exception!");
        }
    }
}
