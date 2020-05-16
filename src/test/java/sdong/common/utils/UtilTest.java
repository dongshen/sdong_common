package sdong.common.utils;

import static org.junit.Assert.assertEquals;

import com.google.common.base.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.bean.loc.FileType;
import sdong.common.exception.SdongException;

public class UtilTest {

	private static final Logger LOG = LoggerFactory.getLogger(UtilTest.class);

	@Test
	public void testGetUUID() {
		for (int i = 0; i < 10; i++) {
			LOG.info("UUID= " + Util.getUUID());
		}
	}

	@Test
	public void testGenerateUUIDSeq() {
		for (int i = 0; i < 10; i++) {
			LOG.info("UUID= " + Util.generateUUIDSeq());
		}
	}

	@Test
	public void testGenerateMD5() {
		String content = "xxxx";
		String md5;
		try {
			md5 = Util.generateMD5(content);
			LOG.info("md5=" + md5);
			assertEquals("ea416ed0759d46a8de58f63a59077499", md5);
		} catch (SdongException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Test
	public void testGetEnum() {
		assertEquals(FileType.C, FileType.getFileTypeByTypeName("C"));
		assertEquals(FileType.Others, FileType.getFileTypeByTypeName("XXX"));
	}

	@Test
	public void testOptional() throws Exception {
		Optional<Integer> possible = Optional.of(6);
		Optional<Integer> absentOpt = Optional.absent();
		Optional<Integer> NullableOpt = Optional.fromNullable(null);
		Optional<Integer> NoNullableOpt = Optional.fromNullable(10);
		if (possible.isPresent()) {
			System.out.println("possible isPresent:" + possible.isPresent());
			System.out.println("possible value:" + possible.get());
		}
		if (absentOpt.equals(Optional.absent())) {
			System.out.println("absentOpt isPresent:" + absentOpt.isPresent());
			;
		}
		if (NullableOpt.isPresent()) {
			System.out.println("fromNullableOpt isPresent:" + NullableOpt.isPresent());
			;
		}
		if (NoNullableOpt.isPresent()) {
			System.out.println("NoNullableOpt isPresent:" + NoNullableOpt.isPresent());
			;
		}
	}

}
