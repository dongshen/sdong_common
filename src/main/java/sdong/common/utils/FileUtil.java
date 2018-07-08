package sdong.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
}
