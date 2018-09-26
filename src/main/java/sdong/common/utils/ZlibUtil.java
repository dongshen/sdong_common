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

	public static byte[] compress(byte[] data) throws SdongException {
		byte[] output = new byte[0];

		Deflater compresser = new Deflater();

		compresser.reset();
		compresser.setInput(data);
		compresser.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!compresser.finished()) {
				int i = compresser.deflate(buf);
				bos.write(buf, 0, i);
			}
			output = bos.toByteArray();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SdongException(e);
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		compresser.end();
		return output;
	}

	// 压缩 字节数组到输出流
	public static void compress(byte[] data, OutputStream os) throws SdongException {
		DeflaterOutputStream dos = new DeflaterOutputStream(os);

		try {
			dos.write(data, 0, data.length);

			dos.finish();

			dos.flush();
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
			byte[] buf = new byte[1024];
			while (!decompresser.finished()) {
				int i = decompresser.inflate(buf);
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
	public static byte[] decompress(InputStream is) {
		InflaterInputStream iis = new InflaterInputStream(is);
		ByteArrayOutputStream o = new ByteArrayOutputStream(1024);
		try {
			int i = 1024;
			byte[] buf = new byte[i];

			while ((i = iis.read(buf, 0, i)) > 0) {
				o.write(buf, 0, i);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return o.toByteArray();
	}
}