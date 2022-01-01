package sdong.common.bean.loc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileTypeTest {
    private static final Logger LOG = LogManager.getLogger(FileTypeTest.class);

    @Test
    public void testGetFileType(){
        assertEquals(FileType.Python,FileType.getFileTypeByExt("py"));
        assertEquals(FileType.C,FileType.getFileTypeByExt("c"));
        assertEquals(FileType.C,FileType.getFileTypeByExt("h"));
        assertEquals(FileType.Java,FileType.getFileTypeByExt("java"));
        assertEquals(FileType.JavaScript,FileType.getFileTypeByExt("js"));
    }
}