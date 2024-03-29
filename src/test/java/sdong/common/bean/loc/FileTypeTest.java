package sdong.common.bean.loc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FileTypeTest {
    @Test
    void testGetFileType(){
        assertEquals(FileType.Python,FileType.getFileTypeByExt("py"));
        assertEquals(FileType.C,FileType.getFileTypeByExt("c"));
        assertEquals(FileType.C,FileType.getFileTypeByExt("h"));
        assertEquals(FileType.Java,FileType.getFileTypeByExt("java"));
        assertEquals(FileType.JavaScript,FileType.getFileTypeByExt("js"));
    }
}