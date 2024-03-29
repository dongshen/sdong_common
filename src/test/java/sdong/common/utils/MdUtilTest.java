package sdong.common.utils;

import static org.junit.jupiter.api.Assertions.fail;

import sdong.common.CommonConstants;
import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class MdUtilTest {
    private static final Logger LOG = LogManager.getLogger(MdUtilTest.class);

    @Test
    void testAddUpdateSectionNumber(){
        try {
            String mdFile ="input/utils/md/title.md";
            String outFile = "output/utils/md/title.md";
            FileUtil.deleteFile(outFile);

            FileUtil.copyFile(mdFile, outFile);
            MdUtil.addUpdateSectionNumber(outFile);            
        } catch (SdongException e) {
            LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
            fail(CommonConstants.SHOULD_NOT_GET_EXCEPTION);
        }    
    }

    @Test
    void testTITLE_SEQ_PATTER(){
        
    }
}
