package sdong.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.Constants;
import sdong.common.exception.SdongException;

public class RunCommandUtil {

	private static final Logger logger = LoggerFactory.getLogger(RunCommandUtil.class);

	public static String runCommand(String[] cmd) throws SdongException {
		Process pro = null;
		String resultStr = null;
		String errorStr = null;
		InputStream inputStream = null;
		InputStream errorStream = null;

		try {
			// p.redirectErrorStream(true);
			pro = Runtime.getRuntime().exec(cmd);

			// 取得命令结果的输出流
			inputStream = pro.getInputStream();
			errorStream = pro.getErrorStream();

			resultStr = readFromInputStream(inputStream);

			errorStr = readFromInputStream(errorStream);

			pro.waitFor();
			pro.destroy();

			if (errorStr != null && !errorStr.isEmpty()) {
				throw new SdongException(errorStr);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			try {
				if (errorStream != null) {
					errorStream.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			pro.destroy();
		}
		return resultStr;
	}

	public static String readFromInputStream(InputStream input) throws SdongException {
		String result = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));

			String line = null;
			StringBuffer sb = new StringBuffer();

			while ((line = br.readLine()) != null) {
				sb.append(line).append(Constants.LINE_BREAK);
			}
			result = sb.toString();

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return result;
	}

}