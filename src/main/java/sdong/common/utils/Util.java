package sdong.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.uuid.Generators;

import sdong.common.exception.SdongException;

public class Util {
	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	/**
	 * type = 4
	 * @return
	 */
	public static String getUUID() {
	    return UUID.randomUUID().toString();
	}

	/**
	 * 
	 * type = 1
	 * https://www.percona.com/blog/2014/12/19/store-uuid-optimized-way/
	 * SUBSTR(uuid, 15, 4),SUBSTR(uuid, 10, 4),SUBSTR(uuid, 1, 8),SUBSTR(uuid, 20,
	 * 4),SUBSTR(uuid, 25)));
	 * 
	 * @return
	 */
	public static String generateUUIDSeq() {
		UUID uuid = Generators.timeBasedGenerator().generate();
		String id = uuid.toString();
		logger.debug("original id={}", id);
		// UNHEX(CONCAT(SUBSTR(uuid, 15, 4),SUBSTR(uuid, 10, 4),SUBSTR(uuid, 1,
		// 8),SUBSTR(uuid, 20, 4),SUBSTR(uuid, 25)));
		id = id.substring(14, 18) + id.substring(9, 13) + id.substring(0, 8) + id.substring(19, 23) + id.substring(24);
		return id;
	}

	public static int parseInteger(String input) {
		int value = 0;
		try {
			value = Integer.parseInt(input);
		} catch (NumberFormatException exp) {
			logger.error("Parse " + input + "to integer error!");
		}
		return value;
	}

	public static String generateMD5(String plainText) throws SdongException {
		// 定义一个字节数组
		byte[] secretBytes = null;
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 对字符串进行加密
			md.update(plainText.getBytes());
			// 获得加密后的数据
			secretBytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			// throw new RuntimeException("没有md5这个算法！");
			logger.error(e.getMessage(), e);
			throw new SdongException(e);
		}
		// 将加密后的数据转换为16进制数字
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

}
