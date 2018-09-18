package sdong.common.utils;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class ZlibUtilTest {

	private static final Logger logger = LoggerFactory.getLogger(ZlibUtilTest.class);

	@Test
	public void testCompressByteArray() {
		String inputFile = "input/zlib/sdong.log";
		try {
			// read to byte
			byte[] data = FileUtil.readFileToByteArray(inputFile);
			logger.info("read file size=" + data.length);

			// compress
			byte[] zlib = ZlibUtil.compress(data);
			logger.info("zlib file size=" + zlib.length);

			// verify uncompress
			byte[] uncompress = ZlibUtil.decompress(zlib);
			logger.info("un compress zlib file size=" + uncompress.length);
			
			String strData = new String(data);			
			String strUncompress = new String(uncompress); 
			assertEquals(strData,strUncompress);
		} catch (SdongException e) {
			e.printStackTrace();
			fail("should not get exception!");
		}

	}

	@Test
	public void testCompressByteArrayOutputStream() {
		fail("Not yet implemented");
	}

	@Test
	public void testDecompressByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testDecompressInputStream() {
		fail("Not yet implemented");
	}

}
