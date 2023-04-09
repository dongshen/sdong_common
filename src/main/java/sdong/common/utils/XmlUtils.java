package sdong.common.utils;

import sdong.common.exception.SdongException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class XmlUtils {
    private static final Logger LOG = LogManager.getLogger(XmlUtils.class);

    /**
     * convert string to unicode with &#xXXXX
     */
    public static String string2Unicode(String str) {
        StringBuilder unicode = new StringBuilder();
        if (str != null) {
            int charlength = 0;

            for (int i = 0; i < str.length(); i++) {

                charlength = str.substring(i, i + 1).getBytes().length;
                char c = str.charAt(i);

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
    public static String stringEscape(String str) {
        String cov = "";
        if (str != null) {
            cov = str.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
            cov = cov.replace("\"", "&quot").replace("\'", "&apos");
        }
        return cov;
    }

    /*
     * decode for xml: <,>,&, ,",',),(
     */
    public static String decode(String str) {
        String cov = "";
        if (str != null) {
            cov = str.replace("&amp;", "&").replace("&nbsp;", " ");
            cov = cov.replace("&lt;", "<").replace("&gt;", ">");
            cov = cov.replace("&quot", "\"").replace("&apos", "\'");
            cov = cov.replace("&langle;", "(").replace("&rangle;", ")");
        }
        return cov;
    }

    /**
     * unicode convert string
     */
    public static String unicode2String(String unicode) {
        StringBuilder string = new StringBuilder();

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
            LOG.error(e.getMessage());
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
    public static String getXmlSingleNodeText(Element ele, String tag) {
        String ret = null;
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
    public static String getXmlSingleNodeTextNoEnter(Element ele, String tag) {
        String ret = null;
        ret = getXmlSingleNodeText(ele, tag);

        return mergeLines(ret);
    }

    public static String mergeLines(String linesText) {
        if (linesText == null) {
            return null;
        }
        StringBuilder retValue = new StringBuilder();
        String[] lines = linesText.split("\n");

        if (lines != null) {
            for (String line : lines) {
                retValue.append(" ").append(line.trim());
            }
        } else {
            retValue.append(linesText.replace("\t", "").replace("\n", " "));
        }
        return retValue.toString().trim();
    }

    /**
     * There are multiple the same tag,change to one line output.
     * 
     * @param ele
     * @param tag
     * @return
     */
    public static String getXmlAllNodesText(Element ele, String tag, String split) {
        StringBuilder ret = new StringBuilder();
        String text = "";
        List<Node> list = ele.selectNodes(tag);
        if (list == null) {
            return null;
        }
        String sp = "";
        for (Node o : list) {
            Element element = (Element) o;
            text = mergeLines(element.getText());
            if (text == null) {
                text = "";
            }
            ret.append(sp).append(text);
            sp = split;
        }

        return ret.toString();
    }

    /**
     * There are multiple node under the tag, change to one line output.
     * 
     * @param ele element
     * @param tag tag
     * @return result
     */
    public static String getXmlAllRelatedNodesText(Element ele, String tag, String subTag, String split) {
        StringBuilder ret = new StringBuilder();
        String text = "";
        Node node = ele.selectSingleNode(tag);
        if (node == null) {
            return null;
        }
        Element e = (Element) node;

        List<Node> list = e.selectNodes(subTag);
        if (list.isEmpty()) {
            return StringUtil.removeStarAndEndBlankLine(node.getText());
        }
        String sp = "";
        for (Node o : list) {
            Element element = (Element) o;
            text = StringUtil.removeStarAndEndBlankLine(element.getText());

            ret.append(sp).append(text);
            sp = split;
        }

        return ret.toString();
    }

    public static String getXmlElementAttribute(Element ele, String tag, String attr) {
        Element eTag = (Element) ele.selectSingleNode(tag);

        if (eTag == null) {
            return null;
        }
        return getXmlAttributeValue(eTag, attr);
    }

    public static String getXmlAttributeValue(Element ele, String attr) {
        return ele.attributeValue(attr);
    }

    public static List<Namespace> getDeclareNameSpaces(Document doc) {
        List<Namespace> declareNamespaces = doc.getRootElement().declaredNamespaces();
        for (Namespace ns : declareNamespaces) {
            LOG.debug("namespace prefix:{}, namespace URI:{}", ns.getPrefix(), ns.getURI());
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
        File file = FileUtil.createFileWithFolder(output);

        try (FileWriter fileWriter = new FileWriter(file)) {
            XMLWriter writer = new XMLWriter(fileWriter, OutputFormat.createPrettyPrint());
            writer.write(document);
        } catch (IOException e) {
            throw new SdongException(e);
        }
    }

    /**
     * get SAXReader
     * 
     * @return SAXReader
     * @throws SdongException module exception
     */
    public static SAXReader createSaxReader() throws SdongException {
        SAXReader xmlReader = new SAXReader();
        try {
            xmlReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        } catch (SAXException e) {
            throw new SdongException(e);
        }
        return xmlReader;
    }
}
