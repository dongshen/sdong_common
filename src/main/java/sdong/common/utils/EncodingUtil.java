package sdong.common.utils;

import com.google.common.io.ByteStreams;
import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import sdong.common.exception.SdongException;

public class EncodingUtil {

    private static final Logger LOG = LogManager.getLogger(EncodingUtil.class);

    /**
     * detect bytes encoding
     * 
     * @param inputBytes input bytes
     * @return charset
     * @throws SdongException
     */
    public static String detectEncoding(byte[] inputBytes) throws SdongException {
        String charset = null;

        CharsetMatch match;

        CharsetDetector detector = new CharsetDetector();

        detector.setText(inputBytes);

        match = detector.detect();
        if (match != null) {
            charset = match.getName();
        } else {
            throw new SdongException("Un support charset!");
        }

        return charset;
    }

    /**
     * detect file encoding
     * 
     * @param fileName input file name
     * @return charset
     * @throws SdongException
     */
    public static String defectFileEncoding(String fileName) throws SdongException {
        String charset = null;

        try (BufferedInputStream fileStream = new BufferedInputStream(new FileInputStream(fileName))) {
            charset = detectEncoding(ByteStreams.toByteArray(fileStream));
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new SdongException(e);
        }

        return charset;
    }
}