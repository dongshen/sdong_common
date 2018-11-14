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
		Process pro = null;
		String inputStr;
		String errorStr;

		try {
			//p.redirectErrorStream(true);
			pro = Runtime.getRuntime().exec(cmd);
			// 取得命令结果的输出流
			final InputStream fis = pro.getInputStream();

			final InputStream fies = pro.getErrorStream();

			Thread inputStream = new Thread() {
				public String result = null;

				public void run() {
					BufferedReader br = null;
					try {
						br = new BufferedReader(new InputStreamReader(fis));
						String line = null;
						StringBuffer sb = new StringBuffer();

						while ((line = br.readLine()) != null) {
							sb.append(line).append("\r\n");
							logger.info(line);
						}
						result = sb.toString();
					} catch (Exception e) {
						logger.error(e.getMessage());
					} finally {
						if (br != null) {
							try {
								br.close();
							} catch (IOException e) {
								logger.error(e.getMessage());
							}
						}

						try {
							fis.close();
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					}
				}
			};

			inputStream.start();

			Thread errorStream = new Thread() {
				String result = null;

				public void run() {
					BufferedReader br = null;
					try {
						br = new BufferedReader(new InputStreamReader(fies));
						String line = null;
						StringBuffer sb = new StringBuffer();

						while ((line = br.readLine()) != null) {
							sb.append(line).append("\r\n");
							logger.info(line);
						}
						result = sb.toString();
					} catch (Exception e) {
						logger.error(e.getMessage());
					} finally {
						if (br != null) {
							try {
								br.close();
							} catch (IOException e) {
								logger.error(e.getMessage());
							}
						}

						try {
							fis.close();
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					}
				}
			};

			errorStream.start();

			inputStream.join();
			errorStream.join();

			pro.waitFor();
			pro.destroy();


		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			try {
				if (pro != null) {
					pro.getInputStream().close();
					pro.getOutputStream().close();
					pro.getErrorStream().close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	public class RunCommandThread extends Thread {

	}
}
