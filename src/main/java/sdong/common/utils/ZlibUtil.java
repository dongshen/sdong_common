package sdong.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sdong.common.exception.SdongException;

public class ZlibUtil {
	private static final Logger logger = LoggerFactory.getLogger(ZlibUtil.class);

	private static final int BUFFER_SIZE = 1024 * 1024;

	public static byte[] compress(List<String> tu) throws SdongException {
		return compress(StringUtil.joinStringListToStringByLineBreak(tu).getBytes());
	}

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

	// 解压缩 字节数组
	public static byte[] decompress(byte[] data, int offSet) throws SdongException {
		byte[] output = new byte[0];

		Inflater decompresser = new Inflater();
		decompresser.reset();
		decompresser.setInput(subBytes(data, offSet));

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
	 * 
	 * 使用gzip进行压缩
	 */
	public static String gzip(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return primStr;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
	}

	/**
	 *
	 * <p>
	 * Description:使用gzip进行解压缩
	 * </p>
	 * 
	 * @param compressedStr
	 * @return
	 */
	public static String gunzip(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}

	public static String gunzip(byte[] compressed) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;

		String decompressed = null;
		try {
			// compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}

	/**
	 * 使用zip进行压缩
	 * 
	 * @param str 压缩前的文本
	 * @return 返回压缩后的文本
	 */
	public static final String zip(String str) {
		if (str == null)
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;
		String compressedStr = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
			compressedStr = new sun.misc.BASE64Encoder().encodeBuffer(compressed);
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressedStr;
	}

	/**
	 * 使用zip进行解压缩
	 * 
	 * @param compressed 压缩后的文本
	 * @return 解压后的字符串
	 */
	public static final String unzip(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed = null;
		try {
			byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}
	
	/**
	 * 使用zip进行解压缩
	 * 
	 * @param compressed 压缩后的文本
	 * @return 解压后的字符串
	 */
	public static final String unzip(byte[] compressed) {
		
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed = null;
		try {
			//byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}
}
