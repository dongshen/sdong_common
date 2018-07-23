package sdong.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static List<String> readFileToStringList(String fileName) throws SdongException {
		List<String> astContent = null;
		try {
			Path path = Paths.get(fileName);
			astContent = Files.readAllLines(path);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}
		return astContent;
	}

	/**
	 * Get Unique file name
	 * @param directory
	 * @param extension
	 * @return
	 */
	public static String getUniqueFileName(String directory, String extension) {
		String fileName = MessageFormat.format("{0}.{1}", UUID.randomUUID(), extension.trim());
		return Paths.get(directory, fileName).toString();
	}
}
