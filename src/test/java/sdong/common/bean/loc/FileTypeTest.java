package sdong.common.bean.loc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTypeTest {

    private static final Logger LOG = LoggerFactory.getLogger(FileTypeTest.class);

    @Test
    public void testGetFileType(){
        assertEquals(FileType.Python,FileType.getFileTypeByExt("py"));
        assertEquals(FileType.C,FileType.getFileTypeByExt("c"));
        assertEquals(FileType.C,FileType.getFileTypeByExt("h"));
        assertEquals(FileType.Java,FileType.getFileTypeByExt("java"));
        assertEquals(FileType.JavaScript,FileType.getFileTypeByExt("js"));
    }
}