package sdong.common.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class ZlibUtilTest {

	private static final Logger log = LoggerFactory.getLogger(ZlibUtilTest.class);

	@Test
	public void testCompressByteArray() {
		String inputFile = "input/zlib/sdong.log";
		int originalSize = 23252;
		int zipSize = 3142;
		try {
			// read to byte
			byte[] data = FileUtil.readFileToByteArray(inputFile);
			log.info("read file size=" + data.length);
			assertEquals(originalSize, data.length);

			// compress
			byte[] zlib = ZlibUtil.compress(data);
			log.info("zlib file size=" + zlib.length);
			assertEquals(zipSize, zlib.length);

			// verify uncompress
			byte[] uncompress = ZlibUtil.decompress(zlib);
			log.info("un compress zlib file size=" + uncompress.length);
			assertEquals(originalSize, uncompress.length);
			assertArrayEquals(data, uncompress);

		} catch (SdongException e) {
			log.error(e.getMessage());
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
			log.info("read file size=" + data.length);
			assertEquals(originalSize, data.length);

			// compress
			byte[] zlib = ZlibUtil.gzip(data);
			log.info("zlib file size=" + zlib.length);
			assertEquals(zipSize, zlib.length);
			FileUtil.writeBytesToFile(zlib, outputFile);

			// verify uncompress
			byte[] uncompress = ZlibUtil.gunzip(zlib);
			log.info("un compress zlib file size=" + uncompress.length);
			assertEquals(originalSize, uncompress.length);
			assertArrayEquals(data, uncompress);

		} catch (SdongException e) {
			log.error(e.getMessage());
			fail("should not get exception!");
		}

	}

	@Test
	public void testZip() {
		String inputFile = "input/zlib/sdong.log";
		String outputFile = "output/zlib/sdong.zip";
		int originalSize = 23252;
		int zipSize = 3264;
		try {
			// read to byte
			byte[] data = FileUtil.readFileToByteArray(inputFile);
			log.info("read file size=" + data.length);
			assertEquals(originalSize, data.length);

			// compress
			byte[] zlib = ZlibUtil.zip(data);
			log.info("zlib file size=" + zlib.length);
			assertEquals(zipSize, zlib.length);
			FileUtil.writeBytesToFile(zlib, outputFile);

			// verify uncompress
			// byte[] unbcompress = ZlibUtil.unzip(zlib);
			ConcurrentHashMap<String, byte[]> ziplist = ZlibUtil.unzip(new FileInputStream(outputFile));
			assertEquals(1, ziplist.size());
			for (Map.Entry<String, byte[]> entry : ziplist.entrySet()) {
				log.info("un compress zlib file size=" + entry.getValue().length);
				assertEquals(originalSize, entry.getValue().length);
				assertArrayEquals(data, entry.getValue());
			}

		} catch (SdongException | FileNotFoundException e) {
			log.error(e.getMessage());
			fail("should not get exception!");
		} 

	}

}
