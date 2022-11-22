package sdong.common.utils;

import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WebUtil {
    private static final Logger LOG = LogManager.getLogger(WebUtil.class);

    public static void saveWebPage(String webUrl, String outputFile) throws SdongException {
        LOG.info("Get website:{}",webUrl);
        String line;
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(webUrl).openStream()))) {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            File file = FileUtil.createFileWithFolder(outputFile);
            try (FileWriter fw = new FileWriter(file);) {
                fw.write(sb.toString());
                fw.close();
            }
        } catch (IOException e) {
            throw new SdongException(e.getMessage(),e);
        } 
    }
}
