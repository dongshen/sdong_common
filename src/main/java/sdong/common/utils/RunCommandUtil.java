package sdong.common.utils;

import sdong.common.CommonConstants;
import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RunCommandUtil {
    private static final Logger logger = LogManager.getLogger(RunCommandUtil.class);

    private static final String[] WINDOWS_CMD = { "cmd", "/c" };
    private static final String[] LINUX_CMD = { "/bin/sh", "-c" };

    /**
     * run command
     * 
     * @param cmd input command string
     * @return
     * @throws SdongException
     */
    public static String runCommand(String cmd) throws SdongException {
        Process pro = null;
        String resultStr = null;
        String errorStr = null;
        InputStream inputStream = null;
        InputStream errorStream = null;

        try {
            // p.redirectErrorStream(true);
            String[] runCmd = CommonUtil.isLinux() ? Arrays.copyOf(LINUX_CMD, 3) : Arrays.copyOf(WINDOWS_CMD, 3);
            runCmd[2] = cmd;
            pro = Runtime.getRuntime().exec(runCmd);

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
            if (pro != null) {
                pro.destroy();
            }
        }
        return resultStr;
    }

    public static String readFromInputStream(InputStream input) throws SdongException {
        String result = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));) {

            String line = null;
            StringBuffer sb = new StringBuffer();

            while ((line = br.readLine()) != null) {
                sb.append(line).append(CommonConstants.LINE_BREAK_CRLF);
            }
            result = sb.toString();
            br.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new SdongException(e);
        }
        return result;
    }

}