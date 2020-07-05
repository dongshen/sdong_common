package sdong.common.utils;

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
import java.util.Set;
import java.util.UUID;

import com.google.common.io.Files;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
			reader.skip(Integer.MAX_VALUE);
			return reader.getLineNumber();
		} catch (IOException e) {
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

	public static List<String> getFilesInFolderSum(String folderList) throws SdongException {
		List<String> fileList = new ArrayList<String>();
		String[] folders = folderList.split(",");
		for (String file : folders) {
			fileList.addAll(getFilesInFolder(file));
		}
		return fileList;
	}

	/**
	 * get all files under folder
	 *
	 * @param folder search folder
	 * @return file list
	 * @throws SdongException IOException
	 */
	public static List<String> getFilesInFolder(String folder) throws SdongException {
		List<String> fileList = new ArrayList<String>();
		try {
			for (File file : Files.fileTraverser().depthFirstPreOrder(new File(folder))) {
				if (file.isFile()) {
					fileList.add(file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			throw new SdongException(e.getMessage());
		}
		return fileList;

	}

	/**
	 * Get folder name base on file name
	 *
	 * @param fileName file name
	 * @return folder name
	 */
	public static String getFolderName(String fileName) {

		File file = new File(fileName);

		String parentFile = file.getParent();

		if (parentFile == null) {
			return "";
		} else {
			return parentFile;
		}
	}

	/**
	 * get file name
	 *
	 * @param fileName file path
	 * @return file name
	 */
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
		return readFileToByteArray(new File(fileName));
	}

	public static byte[] readFileToByteArray(File file) throws SdongException {

		try (FileInputStream fs = new FileInputStream(file); FileChannel channel = fs.getChannel();) {
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

	/**
	 * create file, if parent folder is not exist, then create parent folder.
	 * 
	 * @param fileName file name
	 * @return File
	 * @throws SdongException create fail
	 */
	public static File createFile(String fileName) throws SdongException {
		File file = new File(fileName);
		File parent = file.getParentFile();
		if (!parent.exists()) {
			if (!parent.mkdirs()) {
				if (!parent.exists()) {
					throw new SdongException("Create folder fail!");
				}
			}
		}
		return file;
	}

	/**
	 * google copy file
	 *
	 * @param fromFile source file
	 * @param toFile   to file
	 * @throws SdongException
	 */
	public static void copyFile(String fromFile, String toFile) throws SdongException {
		try {
			Files.copy(new File(fromFile), createFile(toFile));
		} catch (IOException e) {
			throw new SdongException(e);
		}
	}

	/**
	 * use apache delete folder
	 * 
	 * @param folder delete folder
	 * @throws SdongException
	 */
	public static void deleteFolder(String folder) throws SdongException {
		try {
			FileUtils.deleteDirectory(new File(folder));
		} catch (IOException e) {
			throw new SdongException(e);
		}

	}

	/**
	 * check file/folder exist
	 *
	 * @param file check file or folder
	 * @return exist or not
	 */
	public static boolean fileExist(String file) {
		return new File(file).exists();
	}

	/**
	 * get file list by extension list
	 *
	 * @param folder     search folder
	 * @param extensions extension list
	 * @return filter result
	 * @throws SdongException
	 */
	public static List<String> filterFiles(String folder, Set<String> extensions) throws SdongException {
		List<String> fileList = new ArrayList<String>();
		try {
			for (File file : Files.fileTraverser().depthFirstPreOrder(new File(folder))) {
				if (file.isFile()) {
					if (extensions == null || extensions.contains(file.getName())) {
						fileList.add(file.getCanonicalPath());
					}
				}
			}
		} catch (IOException e) {
			throw new SdongException(e.getMessage());
		}
		return fileList;

	}
}
