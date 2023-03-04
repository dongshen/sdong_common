package sdong.common.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ZlibUtilTest {
	private static final Logger LOG = LogManager.getLogger(ZlibUtilTest.class);

	@Test
	public void testCompressByteArray() {
		String inputFile = "input/zlib/sdong.log";
		int originalSize = 23252;
		int zipSize = 3142;
		try {
			// read to byte
			byte[] data = FileUtil.readFileToByteArray(inputFile);
			LOG.info("read file size=" + data.length);
			assertEquals(originalSize, data.length);

			// compress
			byte[] zlib = ZlibUtil.compress(data);
			LOG.info("zlib file size=" + zlib.length);
			assertEquals(zipSize, zlib.length);

			// verify uncompress
			byte[] uncompress = ZlibUtil.decompress(zlib);
			LOG.info("un compress zlib file size=" + uncompress.length);
			assertEquals(originalSize, uncompress.length);
			assertArrayEquals(data, uncompress);

		} catch (SdongException e) {
			LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
			fail("should not get exception!");
		}
	}

	@Test
	public void testGzip() {
		String inputFile = "input/zlib/sdong.log";
		String outputFile = "output/zlib/sdong.gz";
		int originalSize = 23252;
		int zipSize = 3154;
		try {
			// read to byte
			byte[] data = FileUtil.readFileToByteArray(inputFile);
			LOG.info("read file size=" + data.length);
			assertEquals(originalSize, data.length);

			// compress
			byte[] zlib = ZlibUtil.gzip(data);
			LOG.info("zlib file size=" + zlib.length);
			assertEquals(zipSize, zlib.length);
			FileUtil.writeBytesToFile(zlib, outputFile);

			// verify uncompress
			byte[] uncompress = ZlibUtil.gunzip(zlib);
			LOG.info("un compress zlib file size=" + uncompress.length);
			assertEquals(originalSize, uncompress.length);
			assertArrayEquals(data, uncompress);

		} catch (SdongException e) {
			LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
			fail("should not get exception!");
		}

	}

	@Test
	public void testZip() {
		String inputFile = "input/zlib/sdong.log";
		String outputFile = "output/zlib/sdong.zip";
		int zipSize = 3264;
		try {
			// clear history
			File outFile = new File(outputFile);
			if (outFile.exists()) {
				outFile.delete();
			}

			long originalSize = new File(inputFile).length();
			// read to byte
			LOG.info("File size:{}", originalSize);
			byte[] data = FileUtil.readFileToByteArray(inputFile);
			LOG.info("read file size=" + data.length);
			assertEquals(originalSize, data.length);

			// compress
			byte[] zlib = ZlibUtil.zip(data);
			LOG.info("zlib file size=" + zlib.length);
			assertEquals(zipSize, zlib.length);
			FileUtil.writeBytesToFile(zlib, outputFile);

			// verify uncompress
			// byte[] unbcompress = ZlibUtil.unzip(zlib);
			ConcurrentHashMap<String, byte[]> ziplist = ZlibUtil.unzipStream(new FileInputStream(outputFile));
			assertEquals(1, ziplist.size());
			for (Map.Entry<String, byte[]> entry : ziplist.entrySet()) {
				LOG.info("un compress zlib file size=" + entry.getValue().length);
				assertEquals(originalSize, entry.getValue().length);
				assertArrayEquals(data, entry.getValue());
			}

		} catch (SdongException | FileNotFoundException e) {
			LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
			fail("should not get exception!");
		}
	}

	@Test
	public void testUnzipStream() {
		String input = "input/zlib/zlib.zip";
		try {
			ConcurrentHashMap<String, byte[]> ziplist = ZlibUtil.unzipStream(new FileInputStream(input));
			assertEquals(2, ziplist.size());
		} catch (FileNotFoundException | SdongException e) {
			LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
			fail("should not get exception!");
		}
	}

	@Test
	public void testUnZip_all() {
		String input = "input/zlib/zlib.zip";
		String outputfolder = "output/zlib/unzip";
		try {
			// clear history
			FileUtil.deleteFolder(outputfolder);

			ZlibUtil.unzip(input, outputfolder, null);
			assertEquals(2, FileUtil.getFilesInFolder(outputfolder).size());
		} catch (SdongException e) {
			LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
			fail("should not get exception!");
		}
	}

	@Test
	public void testUnZip_selectFile() {
		String input = "input/zlib/zlib.zip";
		String outputfolder = "output/zlib/unzip_selected";
		Set<String> list = new HashSet<String>(Arrays.asList("zip/sdong.log"));
		try {
			// clear history
			FileUtil.deleteFolder(outputfolder);

			ZlibUtil.unzip(input, outputfolder, list);
			assertEquals(1, FileUtil.getFilesInFolder(outputfolder).size());
		} catch (SdongException e) {
			LOG.error("{}:{}", e.getErrorPosition(), e.getMessage());
			fail("should not get exception!");
		}
	}
}
