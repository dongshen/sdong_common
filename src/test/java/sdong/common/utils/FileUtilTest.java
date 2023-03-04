package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sdong.common.exception.SdongException;

public class FileUtilTest {
	private static final Logger log = LogManager.getLogger(FileUtilTest.class);

	@Test
	public void testReadFileToStringList() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadFileToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCurrentFilePath() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFileSuffix() {
		String result = "";
		String file = "abc" + File.separator + "a.java";

		result = FileUtil.getFileExtension(file);
		assertEquals("java", result);

		file = "a.cpp";

		result = FileUtil.getFileExtension(file);
		assertEquals("cpp", result);

	}

	@Test
	public void testGetFilesInFolder() {
		try {
			List<String> files = FileUtil.getFilesInFolder("input/loc");
			assertEquals(7, files.size());

			List<String> file = FileUtil.getFilesInFolder("input/loc/example/loc_cases.c");
			assertEquals(1, file.size());
		} catch (SdongException e) {
			LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
			fail("should not get exception!");
		}
	}

	@Test
	public void testGetUniqueFileName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFolderName() {
		String result = "";

		String file = "abc" + File.separator + "abc" + File.separator + "a.java";
		result = FileUtil.getFolderName(file);
		assertEquals("abc" + File.separator + "abc", result);

		file = "abc" + File.separator + "a.java";
		result = FileUtil.getFolderName(file);
		assertEquals("abc", result);

		file = "a.java";
		result = FileUtil.getFolderName(file);
		assertEquals("", result);

	}

	@Test
	public void testGetFileName() {
		String result = "";
		String file = "abc" + File.separator + "a.java";

		result = FileUtil.getFileName(file);
		assertEquals("a.java", result);

		file = "a.java";
		result = FileUtil.getFileName(file);
		assertEquals("a.java", result);
	}

	@Test
	public void testReadFileToByteArray() {
		String filename = "C:\\Users\\shendong\\Downloads\\neo4j-desktop-offline-1.2.1-setup.exe";
		byte[] result;
		try {
			long avg = 0L;
			for (int i = 1; i <= 10; i++) {
				Instant first = Instant.now();
				result = FileUtil.readFileToByteArray(filename);
				log.info("size:" + result.length);
				Instant second = Instant.now();
				avg = avg + Duration.between(first, second).toMillis();
				log.info("duration = {}", avg / i);
			}
		} catch (SdongException e) {
			LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
			fail("should not get exception!");
		}
	}

}
