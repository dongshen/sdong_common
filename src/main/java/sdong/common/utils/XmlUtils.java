package sdong.common.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sdong.common.exception.SdongException;

public class XmlUtils {
	private static final Logger LOG = LogManager.getLogger(XmlUtils.class);

	/**
	 * convert string to unicode with &#xXXXX
	 */
	public static String string2Unicode(String str) {

		StringBuffer unicode = new StringBuffer();
		if (str != null) {
			int charlength = 0;

			for (int i = 0; i < str.length(); i++) {

				charlength = str.substring(i, i + 1).getBytes().length;
				char c = str.charAt(i);

				// LOG.debug("c=" + c + ",length=" + charlength);

				if (charlength > 1) {
					// 取出每一个字符

					// 转换为unicode
					unicode.append("&#x" + Integer.toHexString(c) + ";");
				} else {
					unicode.append(c);
				}
			}
		}
		return unicode.toString();
	}

	/*
	 * Escape for xml (<,>,&)
	 */

	public static String StringEscape(String str) {

		String cov = "";
		if (str != null) {
			cov = str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		}
		return cov;
	}

	/**
	 * unicode convert string
	 */
	public static String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split(";");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

	/**
	 * format XML
	 *
	 * @param document xml doc
	 * @param charset  character set
	 * @param istrans  whether escape
	 * @return convert to string
	 */
	public static String formatXml(Document document, String charset, boolean istrans) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw, format);
		xw.setEscapeText(istrans);
		try {
			xw.write(document);
			xw.flush();
			xw.close();
		} catch (IOException e) {
			System.out.println("Format XML doc get exception，please check it!");
			e.printStackTrace();
		}
		return sw.toString();
	}

	/**
	 * The node and only one tag line.
	 * 
	 * @param ele
	 * @param tag
	 * @return
	 */
	public static String getXMLSingleNodeText(Element ele, String tag) {
		String ret = "";
		Node node = ele.selectSingleNode(tag);
		if (node != null) {
			ret = node.getText();
		}

		return ret;
	}

	/**
	 * The node with multiple lines, this function will remove the \t and \n to one
	 * line output.
	 * 
	 * @param ele
	 * @param tag
	 * @return
	 */
	public static String getXMLSingleNodeTextNoEnter(Element ele, String tag) {
		String ret = "";
		ret = getXMLSingleNodeText(ele, tag);

		return mergeLines(ret);
	}

	public static String mergeLines(String linesText) {
		String ret_value = "";
		String[] lines = linesText.split("\n");

		if (lines != null) {
			for (String line : lines) {
				ret_value = ret_value + " " + line.trim();
			}
		} else {
			ret_value = linesText.replaceAll("\t", "").replaceAll("\n", " ");
		}
		return ret_value.trim();
	}

	/**
	 * There are multiple the same tag,change to one line output.
	 * 
	 * @param ele
	 * @param tag
	 * @return
	 */
	public static String getXMLAllNodesText(Element ele, String tag, String split) {
		String ret = "";
		String text = "";
		List<Node> list = ele.selectNodes(tag);
		for (Object o : list) {
			Element e_l = (Element) o;
			text = mergeLines(e_l.getText());
			if (ret.equals("")) {
				ret = text;
			} else {
				ret = ret + split + text;
			}
		}

		return ret;
	}

	/**
	 * There are multiple node under the tag, change to one line output.
	 * 
	 * @param ele element
	 * @param tag tag
	 * @return result
	 */
	public static String getXMLAllRelatedNodesText(Element ele, String tag, String subTag, String split) {
		String ret = "";
		String text = "";
		Node node = ele.selectSingleNode(tag);
		if (node == null) {
			return ret;
		}
		Element e = (Element) node;

		List<Node> list = e.selectNodes(subTag);
		if (list.isEmpty()) {
			return StringUtil.removeStarAndEndBlankLine(node.getText());
		}
		for (Object o : list) {
			Element e_l = (Element) o;
			text = StringUtil.removeStarAndEndBlankLine(e_l.getText());
			if (ret.equals("")) {
				ret = text;
			} else {
				ret = ret + split + text;
			}
		}

		return ret;
	}

	public static String getXMLElementAttribute(Element ele, String tag, String attr) {
		String ret = "";

		Element e_tag = (Element) ele.selectSingleNode(tag);
		if (e_tag != null) {
			ret = getXMLAttributeValue(e_tag, attr);
		}

		return ret;
	}

	public static String getXMLAttributeValue(Element ele, String attr) {
		String ret = "";
		ret = ele.attributeValue(attr);
		if (ret == null) {
			ret = "";
		}
		return ret;
	}

	public static List<Namespace> getDeclareNameSpaces(Document doc) {

		List<Namespace> declareNamespaces = doc.getRootElement().declaredNamespaces();
		for (Namespace ns : declareNamespaces) {
			LOG.debug("namespace prefix:" + ns.getPrefix() + ", namespace URI:" + ns.getURI());
		}
		return declareNamespaces;
	}

	public static Namespace getDefaultNameSpace(Document doc) {
		Namespace defaultNameSpace = null;

		List<Namespace> declareNamespaces = getDeclareNameSpaces(doc);
		if (declareNamespaces != null) {
			for (Namespace ns : declareNamespaces) {
				if (ns.getPrefix() == null || ns.getPrefix().isEmpty()) {
					defaultNameSpace = ns;
					break;
				}
			}
		}

		return defaultNameSpace;
	}

	/**
	 * add tag and text
	 * 
	 * @param element element where to add a new tag
	 * @param tag     tag
	 * @param text    text
	 * @return tag element
	 */
	public static Element addElementText(Element element, String tag, String text) {
		if (text == null || text.isEmpty()) {
			return null;
		}
		Element tagElement = element.addElement(tag);
		tagElement.setText(text);
		return tagElement;
	}

	/**
	 * add element attribute and text
	 * 
	 * @param element element where to add attribute
	 * @param tag     tag
	 * @param text    text
	 */
	public static void addElementAttribute(Element element, String attTag, String attribute) {
		if (attribute == null || attribute.isEmpty()) {
			return;
		}
		
		element.addAttribute(attTag, attribute);
		return ;
	}

	/**
	 * add tag and text
	 * 
	 * @param element element where to add a new tag
	 * @param tag     tag
	 * @param text    text
	 * @return tag element
	 */
	public static Element addElementCdata(Element element, String tag, String text) {
		if (text == null || text.isEmpty()) {
			return null;
		}
		Element tagElement = element.addElement(tag);
		tagElement.addCDATA(text);
		return tagElement;
	}

	/**
	 * write xml document to file
	 *
	 * @param document document
	 * @param output   output file
	 * @throws SdongException module exception
	 */
	public static void writeDocToFile(Document document, String output) throws SdongException {
		XMLWriter writer = null;
		try {
			File file = FileUtil.createFile(output);

			writer = new XMLWriter(new FileWriter(file), OutputFormat.createPrettyPrint());

			writer.write(document);
		} catch (IOException e) {
			throw new SdongException(e.getMessage());
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
				}
			}
		}
	}
}
