package sdong.common.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
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

	// read file content into a string
	public static String readFileToString(String filePath) throws SdongException {
		String content = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new SdongException("file:" + filePath + " not exists!");
			}
			content = new String(Files.readAllBytes(file.toPath()), Charset.forName("UTF-8"));
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}
		return content;
	}

	public static String getCurrentFilePath() throws IOException {
		File dirs = new File(".");
		String dirPath = dirs.getCanonicalPath();
		return dirPath;
	}

	public static String getFileSuffix(String fileName) {
		return FilenameUtils.getExtension(fileName);
	}

	public static List<String> getFilesInFolder(String dirPath) {

		List<String> fileList = new ArrayList<String>();

		File root = new File(dirPath);
		if (!root.isDirectory()) {
			fileList.add(root.getAbsolutePath());
			return fileList;
		}

		File[] files = root.listFiles();
		String filePath = null;

		for (File f : files) {
			filePath = f.getAbsolutePath();
			if (f.isFile()) {
				fileList.add(filePath);
			} else if (f.isDirectory()) {
				fileList.addAll(getFilesInFolder(filePath));
			}
		}

		return fileList;

	}

	public static String getFolderName(String fileName) {

		File file = new File(fileName);

		String parentFile = file.getParent();

		if (parentFile == null) {
			return "";
		} else {
			return parentFile;
		}
	}

	public static String getFileName(String fileName) {

		File file = new File(fileName);
		return file.getName();
	}

	/**
	 * Get Unique file name
	 * 
	 * @param directory
	 * @param extension
	 * @return
	 */
	public static String getUniqueFileName(String directory, String extension) {
		String fileName = MessageFormat.format("{0}.{1}", UUID.randomUUID(), extension.trim());
		return Paths.get(directory, fileName).toString();
	}

	public static byte[] readFileToByteArray(String filename) throws SdongException {

		File f = new File(filename);

		try (FileInputStream fs = new FileInputStream(f); FileChannel channel = fs.getChannel();) {
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
			}
			return byteBuffer.array();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		}
	}

	/**
	 * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
	 *
	 * @param filename
	 * @return
	 * @throws SdongException
	 */
	public static byte[] readFileToByteArray2(String filename) throws SdongException {
		
		try (RandomAccessFile rf = new RandomAccessFile(filename, "r"); FileChannel fc = rf.getChannel();) {
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size()).load();

			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} 
	}

	@Deprecated
	public static byte[] readFileToByteArrayGuava(String filename) throws SdongException {
		byte[] result = null;
		File file = new File(filename);
		try {
			result = com.google.common.io.Files.toByteArray(file);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		}

		return result;
	}

	public static Path writeBytesToFile(byte[] outputBytes, String fileName) throws SdongException {
		File outputFile = new  File(fileName);
		File parent = outputFile.getParentFile();
		if(!parent.exists()){
			parent.mkdirs();
		}

		try {
			return Files.write(outputFile.toPath(), outputBytes);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		}
	}
}
