package sdong.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class RunCommandUtil {

	private static final Logger logger = LoggerFactory.getLogger(RunCommandUtil.class);

	public static String runCommand(String[] cmd) throws SdongException {
		StringBuffer result = new StringBuffer();
		Process p;

		try {
			p = Runtime.getRuntime().exec(cmd);
			// 取得命令结果的输出流
			InputStream fis = p.getInputStream();
			// 用一个读输出流类去读
			InputStreamReader isr = new InputStreamReader(fis);
			// 用缓冲器读行
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			// 直到读完为止
			while ((line = br.readLine()) != null) {
				result.append(line).append("\r\n");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		}
		return result.toString();
	}
}
