package sdong.common.utils;

import com.google.common.io.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sdong.common.exception.SdongException;

public class FileUtil {

	public static final String DEFAULT_FILE_ENCODING = "UTF-8";

	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static List<String> readFileToStringList(String fileName) throws SdongException {
		return readFileToStringList(fileName, DEFAULT_FILE_ENCODING);
	}

	public static List<String> readFileToStringList(String fileName, String encoding) throws SdongException {
		List<String> contentList = null;
		try {
			String fileEncoding = encoding;
			if (fileEncoding == null) {
				fileEncoding = EncodingUtil.defectFileEncoding(fileName);
			}

			contentList = Files.readLines(new File(fileName), Charset.forName(fileEncoding));
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}
		return contentList;
	}

	// read file content into a string
	public static String readFileToString(String fileName) throws SdongException {
		return readFileToString(fileName, DEFAULT_FILE_ENCODING);
	}

	public static String readFileToString(String fileName, String encoding) throws SdongException {
		String fileEncoding = encoding;
		if (fileEncoding == null) {
			fileEncoding = EncodingUtil.defectFileEncoding(fileName);
		}
		return new String(readFileToByteArray(fileName), Charset.forName(fileEncoding));
	}

	public static String getCurrentFilePath() throws IOException {
		File dirs = new File(".");
		String dirPath = dirs.getCanonicalPath();
		return dirPath;
	}

	public static String getFileExtension(String fileName) {
		return Files.getFileExtension(fileName);
	}

	public static int getFileLineNum(File file) throws SdongException {
		try(LineNumberReader reader = new LineNumberReader(new FileReader(file))){
			reader.skip(Integer.MAX_VALUE);
			return reader.getLineNumber() + 1;
		}catch(IOException e){
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}
	}

	public static List<String> getFilesInFolder2(String dirPath) throws SdongException {

		List<String> fileList = new ArrayList<String>();

		try {
			File root = new File(dirPath);
			if (!root.isDirectory()) {
				fileList.add(root.getCanonicalPath());
				return fileList;
			}

			File[] files = root.listFiles();
			String filePath = null;

			for (File file : files) {
				filePath = file.getCanonicalPath();
				if (file.isFile()) {
					fileList.add(filePath);
				} else if (file.isDirectory()) {
					fileList.addAll(getFilesInFolder(filePath));
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
		}
		return fileList;

	}

	public static List<String> getFilesInFolder(String folder) throws SdongException {
		List<String> fileList = new ArrayList<String>();
		try {
			for (File file : Files.fileTraverser().depthFirstPreOrder(new File(folder))) {
				if (file.isFile()) {
					fileList.add(file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e.getMessage());
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

	public static byte[] readFileToByteArray(String fileName) throws SdongException {

		File f = new File(fileName);
		try (FileInputStream fs = new FileInputStream(f); FileChannel channel = fs.getChannel();) {
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			channel.read(byteBuffer);
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
			result = Files.toByteArray(file);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		}

		return result;
	}

	public static void writeBytesToFile(byte[] outputBytes, String fileName) throws SdongException {
		try {
			Files.write(outputBytes, createFile(fileName));
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		}
	}

	public static File createFile(String fileName) {
		File file = new File(fileName);
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		return file;
	}

	public static void copyFile(String fromFile, String toFile) throws IOException {
		Files.copy(new File(fromFile), createFile(toFile));
	}
}
