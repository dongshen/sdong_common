package sdong.common.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

public class FileUtilTest {

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

		result = FileUtil.getFileSuffix(file);
		assertEquals("java", result);

		file = "a.cpp";

		result = FileUtil.getFileSuffix(file);
		assertEquals("cpp", result);

	}

	@Test
	public void testGetFilesInFolder() {
		fail("Not yet implemented");
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
}
