package sdong.common.bean;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTypeTest {

    private static final Logger LOG = LoggerFactory.getLogger(FileTypeTest.class);

    @Test
    public void testGetFileType(){
        assertEquals(FileType.Python,FileType.getFileType("py"));
        assertEquals(FileType.C,FileType.getFileType("c"));
        assertEquals(FileType.C,FileType.getFileType("h"));
        assertEquals(FileType.Java,FileType.getFileType("java"));
        assertEquals(FileType.JavaScript,FileType.getFileType("js"));
    }
}