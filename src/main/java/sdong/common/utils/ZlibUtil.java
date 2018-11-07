package sdong.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class ZlibUtil {
	private static final Logger logger = LoggerFactory.getLogger(ZlibUtil.class);

	private static final int BUFFER_SIZE = 2048;

	public static byte[] compress(byte[] data) throws SdongException {
		byte[] output = null;

		Deflater compresser = new Deflater();

		compresser.reset();
		compresser.setInput(data);
		compresser.finish();
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream(data.length);
			byte[] buf = new byte[BUFFER_SIZE];
			int i = 0;
			while (!compresser.finished()) {
				i = compresser.deflate(buf);
				bos.write(buf, 0, i);
			}
			output = bos.toByteArray();

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			if (bos != null) {
				try {

					bos.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
			compresser.end();
		}

		return output;
	}

	// 压缩 字节数组到输出流
	public static void compress(byte[] data, OutputStream os) throws SdongException {
		DeflaterOutputStream dos = new DeflaterOutputStream(os);

		try {
			dos.write(data, 0, data.length);

			dos.finish();

			dos.flush();
			dos.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		}
	}

	// 解压缩 字节数组
	public static byte[] decompress(byte[] data) throws SdongException {
		byte[] output = new byte[0];

		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(data);

		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[BUFFER_SIZE];
			int i = 0;
			while (!decompresser.finished()) {
				i = decompresser.inflate(buf);
				o.write(buf, 0, i);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			try {
				o.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

		decompresser.end();
		return output;
	}

	// 解压缩 输入流 到字节数组
	public static byte[] decompress(InputStream is) throws SdongException {

		byte[] result = null;
		InflaterInputStream iis = null;
		ByteArrayOutputStream os = null;
		try {
			iis = new InflaterInputStream(is);
			os = new ByteArrayOutputStream(BUFFER_SIZE);
			int i = 0;
			byte[] buf = new byte[BUFFER_SIZE];

			while ((i = iis.read(buf, 0, i)) > 0) {
				os.write(buf, 0, i);
			}
			result = os.toByteArray();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SdongException(e);
		} finally {
			if (os != null) {
				try {
					os.close();
					os = null;
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}

			if (iis != null) {
				try {
					iis.close();
					iis = null;
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return result;
	}
}
