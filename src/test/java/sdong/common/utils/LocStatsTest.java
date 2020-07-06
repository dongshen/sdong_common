package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import sdong.common.bean.loc.FileInfo;
import sdong.common.bean.loc.FileInfoSum;
import sdong.common.bean.loc.FileType;
import sdong.common.exception.SdongException;

public class LocStatsTest {

    private static final Logger LOG = LoggerFactory.getLogger(LocStatsTest.class);

    private final String teseCaseFiles = "input/loc/example";

    @Test
    public void testMain() {
        String[] args = { teseCaseFiles };
        LocStats.main(args);
    }

    @Test
    public void testMainList() {
        String[] args = { teseCaseFiles, "-list" };
        LocStats.main(args);
    }

    @Test
    public void testMainJ() {
        String[] args = { teseCaseFiles, "-j9" };
        LocStats.main(args);
    }

    @Test
    public void testMainVerify() {
        List<String> fileList;
        try {
            fileList = FileUtil.getFilesInFolderList(Arrays.asList(teseCaseFiles.split(",")));
            assertEquals(5, fileList.size());

            List<FileInfo> fileInfoList = LocStats.getFieInfoThread(fileList, 1);
            boolean isPrintList = false;
            ConcurrentHashMap<FileType, FileInfoSum> sumMap = LocStats.printFileInfoSum(fileInfoList, isPrintList);
            assertEquals(4, sumMap.size());

            // get C
            FileInfoSum sumC = sumMap.get(FileType.C);
            assertEquals(2, sumC.getFilesCounts());
            assertEquals(50 + 57, sumC.getBlankLineCounts());
            assertEquals(47 + 62, sumC.getCommentCounts());
            assertEquals(37 + 14, sumC.getCommentInLineCounts());
            assertEquals(8503, sumC.getFileSize());
            assertEquals(158 + 46, sumC.getLineCounts());
            assertEquals(143 + 277, sumC.getRowLineCounts());

            // get Java
            FileInfoSum sumJ = sumMap.get(FileType.Java);
            assertEquals(1, sumJ.getFilesCounts());
            assertEquals(5, sumJ.getBlankLineCounts());
            assertEquals(9, sumJ.getCommentCounts());
            assertEquals(0, sumJ.getCommentInLineCounts());
            assertEquals(392, sumJ.getFileSize());
            assertEquals(7, sumJ.getLineCounts());
            assertEquals(21, sumJ.getRowLineCounts());

            // get Python
            FileInfoSum sumP = sumMap.get(FileType.Python);
            assertEquals(1, sumP.getFilesCounts());
            assertEquals(2, sumP.getBlankLineCounts());
            assertEquals(7, sumP.getCommentCounts());
            assertEquals(0, sumP.getCommentInLineCounts());
            assertEquals(339, sumP.getFileSize());
            assertEquals(5, sumP.getLineCounts());
            assertEquals(14, sumP.getRowLineCounts());

            // get Python
            FileInfoSum sumX = sumMap.get(FileType.Xml);
            assertEquals(1, sumX.getFilesCounts());
            assertEquals(0, sumX.getBlankLineCounts());
            assertEquals(2, sumX.getCommentCounts());
            assertEquals(1, sumX.getCommentInLineCounts());
            assertEquals(480, sumX.getFileSize());
            assertEquals(12, sumX.getLineCounts());
            assertEquals(14, sumX.getRowLineCounts());
        } catch (SdongException e) {
            LOG.error(e.getMessage());
            fail("should not get exception!");
        }

    }
}