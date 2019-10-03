package sdong.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.google.common.io.ByteStreams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class ZlibUtil {
	private static final Logger log = LoggerFactory.getLogger(ZlibUtil.class);

	private static final int BUFFER_SIZE = 1024 * 1024;

	/**
	 * compress byte[] with default deflater
	 * 
	 * @param data
	 * @return
	 * @throws SdongException
	 */
	public static byte[] compress(byte[] data) throws SdongException {
		byte[] output = null;
		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
				DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);) {

			deflaterOutputStream.write(data);
			deflaterOutputStream.finish();

			output = byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new SdongException(e);
		}
		return output;
	}

	/**
	 * compress List<String> to byte[]
	 * 
	 * @param tu
	 * @return
	 * @throws SdongException
	 */
	public static byte[] compress(List<String> tu) throws SdongException {
		return compress(StringUtil.joinStringListToStringByLineBreak(tu).getBytes());
	}

	/**
	 * decompress inputstream to byte[]
	 * 
	 * @param inputStream
	 * @return
	 * @throws SdongException
	 */
	public static byte[] decompress(InputStream inputStream) throws SdongException {

		byte[] result = null;

		try (InflaterInputStream inlfaterInputStream = new InflaterInputStream(inputStream);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {

			ByteStreams.copy(inlfaterInputStream, outputStream);

			result = outputStream.toByteArray();

		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new SdongException(e);
		}
		return result;
	}

	/**
	 * decompress byte[] to byte[]
	 * 
	 * @param data
	 * @return
	 * @throws SdongException
	 */
	public static byte[] decompress(byte[] data) throws SdongException {
		byte[] output = null;

		try (InputStream inputStream = new ByteArrayInputStream(data);) {
			output = decompress(inputStream);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new SdongException(e);
		}

		return output;
	}

	// 解压缩 字节数组有偏移
	public static byte[] decompress(byte[] data, int offSet) throws SdongException {
		return decompress(subBytes(data, offSet));
	}

	public static byte[] subBytes(byte[] src, int begin) {
		if (src == null || begin > src.length) {
			return null;
		}

		int count = src.length - begin;
		byte[] bs = new byte[count];
		for (int i = begin; i < begin + count; i++)
			bs[i - begin] = src[i];
		return bs;
	}

	/**
	 * compress byte[] with gzip to byte[]
	 * @param data
	 * @return
	 * @throws SdongException
	 */
	public static byte[] gzip(byte[] data) throws SdongException {
		byte[] output = null;
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
				GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);) {
			gzipOutputStream.write(data);
			gzipOutputStream.finish();
			output = outputStream.toByteArray();
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new SdongException(e);
		}

		return output;
	}

	/**
	 * uncompress byte[] with gzip to byte[] 
	 * @param compressed
	 * @return
	 * @throws SdongException
	 */
	public static byte[] gunzip(byte[] compressed) throws SdongException {

		byte[] ungzipBytes = null;
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(compressed.length);
				ByteArrayInputStream inputStream = new ByteArrayInputStream(compressed);
				GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);) {

			ByteStreams.copy(gzipInputStream, outputStream);

			ungzipBytes = outputStream.toByteArray();
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new SdongException(e);
		}

		return ungzipBytes;
	}
	
	/**
	 * compress byte[] with zip to byte[]
	 * @param inputBytes
	 * @return
	 * @throws SdongException
	 */
	public static final byte[] zip(byte[] inputBytes) throws SdongException {

		byte[] compressedBytes;

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);) {
			zipOutputStream.putNextEntry(new ZipEntry("0"));
			zipOutputStream.write(inputBytes);
			zipOutputStream.closeEntry();

			outputStream.flush();
			compressedBytes = outputStream.toByteArray();

		} catch (IOException e) {
			log.error(e.getMessage());
			throw new SdongException(e);
		}
		return compressedBytes;
	}

	/**
	 * uncompress byte[] with zip to byte[]
	 * @param data
	 * @return
	 * @throws SdongException
	 */
	public static final byte[] unzip(byte[] data) throws SdongException {

		byte[] unCompressedBytes;

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(data));) {
			ZipEntry entry;
			while ((entry = zipInputStream.getNextEntry()) != null) {
				log.info("entry name:{}", entry.getName());
				log.info("size:{}", entry.getSize());
			}
			ByteStreams.copy(zipInputStream, outputStream);
			/*
			 * byte[] buffer = new byte[BUFFER_SIZE]; int offset = -1; while ((offset =
			 * zipInputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
			 * outputStream.write(buffer, 0, offset); } outputStream.flush();
			 * zipInputStream.closeEntry();
			 */
			unCompressedBytes = outputStream.toByteArray();
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new SdongException(e);
		}
		return unCompressedBytes;
	}

}
