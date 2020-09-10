package sdong.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.uuid.Generators;
import com.google.common.base.Enums;
import com.google.common.base.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.CommonConstants;
import sdong.common.exception.SdongException;

public class CommonUtil {
	private static final Logger LOG = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * type = 4
	 * 
	 * @return
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().toUpperCase(Locale.ENGLISH);
	}

	/**
	 * 
	 * type = 1 https://www.percona.com/blog/2014/12/19/store-uuid-optimized-way/
	 * SUBSTR(uuid, 15, 4),SUBSTR(uuid, 10, 4),SUBSTR(uuid, 1, 8),SUBSTR(uuid, 20,
	 * 4),SUBSTR(uuid, 25)));
	 * 
	 * @return
	 */
	public static String generateUuidSeq() {
		UUID uuid = Generators.timeBasedGenerator().generate();
		String id = uuid.toString();
		LOG.debug("original id={}", id);
		// UNHEX(CONCAT(SUBSTR(uuid, 15, 4),SUBSTR(uuid, 10, 4),SUBSTR(uuid, 1,
		// 8),SUBSTR(uuid, 20, 4),SUBSTR(uuid, 25)));
		id = id.substring(14, 18) + id.substring(9, 13) + id.substring(0, 8) + id.substring(19, 23) + id.substring(24);
		return id;
	}

	public static int parseInteger(String input) {
		int value = 0;
		try {
			if (input == null || input.isEmpty()) {
				return value;
			}
			value = Integer.parseInt(input);
		} catch (NumberFormatException exp) {
			LOG.error("Parse {} to integer error!", input);
		}
		return value;
	}

	public static float parseFloat(String input) {
		float value = (float) 0.0;
		try {
			if (input == null || input.isEmpty()) {
				return value;
			}
			value = Float.parseFloat(input);
		} catch (NumberFormatException exp) {
			LOG.error("Parse {} to integer error!", input);
		}
		return value;
	}

	public static long parseLong(String input) {
		long value = 0l;
		try {
			if (input == null || input.isEmpty()) {
				return value;
			}
			value = Long.parseLong(input);
		} catch (NumberFormatException exp) {
			LOG.error("Parse {} to integer error!", input);
		}
		return value;
	}

	public static boolean parseBoolean(String input) {
		boolean value = false;
		try {
			if (input == null || input.isEmpty()) {
				return value;
			}
			value = Boolean.parseBoolean(input);
		} catch (NumberFormatException exp) {
			LOG.error("Parse {} to integer error!", input);
		}
		return value;
	}

	public static String generateMD5(String plainText) throws SdongException {
		return generateMD5(plainText.getBytes());
	}

	public static String generateMD5(byte[] bytes) throws SdongException {
		String md5code = "";
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 对字符串进行加密
			md.update(bytes);
			// 获得加密后的数据
			byte[] secretBytes = md.digest();
			// 将加密后的数据转换为16进制数字
			md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
			// 如果生成数字未满32位，需要前面补0
			for (int i = 0; i < 32 - md5code.length(); i++) {
				md5code = "0" + md5code;
			}
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getMessage(), e);
			throw new SdongException(e);
		}

		return md5code;
	}

	public static String generateFileMd5(String fileName) throws SdongException {
		return generateFileMd5(new File(fileName));
	}

	public static String generateFileMd5(File file) throws SdongException {
		return generateMD5(FileUtil.readFileToByteArray(file));
	}

	/**
	 * Get enum value
	 * 
	 * @param <T>       enum type
	 * @param enumClass enum class
	 * @param strValue  convert string
	 * @return enum
	 */
	public static <T extends Enum<T>> Optional<T> getEnum(Class<T> enumClass, String strValue) {
		return Enums.getIfPresent(enumClass, strValue);
	}

	/**
	 * Convert boolean string to int
	 *
	 * @param value input string
	 * @return result
	 */
	public static int convertBooleanToInt(String value) {
		int result = 0;
		if (CommonConstants.BOOLEAN_TRUE.equalsIgnoreCase(value)) {
			result = 1;
		}
		return result;
	}

	/**
	 * get resource input stream
	 * 
	 * @param resource resource name
	 * @return inputStream
	 * @throws SdongException module exception
	 */
	public static InputStream getResourceStream(String resource) throws SdongException {
		InputStream inputStream = null;
		try {
			inputStream = CommonUtil.class.getClassLoader().getResourceAsStream(resource);
		} catch (SecurityException e) {
			LOG.error(e.getMessage());
			inputStream = null;
		}
		if (inputStream == null) {
			try {
				URL url = CommonUtil.class.getClassLoader().getResource(resource);
				inputStream = new FileInputStream(new File(url.getFile()));
			} catch (SecurityException | FileNotFoundException e) {
				LOG.error(e.getMessage());
				throw new SdongException(e);
			}
		}
		return inputStream;
	}

	/**
	 * base on shannon entropy return bits of entropy represented in string.
	 * 
	 * @param inStr input string
	 * @return bits of entropy represented in string
	 */
	public static double shannonEntropy(String inStr) {
		double result = 0.0;
		char[] chars = inStr.toCharArray();
		Map<String, Integer> dict = new HashMap<String, Integer>();
		String str;
		for (char ch : chars) {
			str = Integer.toHexString((int) ch);
			if (dict.containsKey(str)) {
				dict.put(str, dict.get(str) + 1);
			} else {
				dict.put(str, 1);
			}
		}

		int len = dict.size();
		double frequency;
		for (Map.Entry<String, Integer> en : dict.entrySet()) {
			frequency = (double) en.getValue() / len;
			result -= frequency * (Math.log(frequency) / Math.log(2));
		}

		return result;
	}

	/**
	 * check string is number
	 * 
	 * @param input input string
	 * @return true/false
	 */
	public static boolean checkNumber(String input) {
		if (input == null || input.isEmpty()) {
			return false;
		}

		for (int ind = 0; ind < input.length(); ind++) {
			int ch = input.charAt(ind);
			if (ch > 57 || ch < 48) {
				return false;
			}
		}
		return true;
	}
}
