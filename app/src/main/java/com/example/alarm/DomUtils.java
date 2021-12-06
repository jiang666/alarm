package com.example.alarm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class DomUtils {
    public DomUtils() {
    }

    public static Document newDocument() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            return null;
        }
    }

    public static Document load(String xmlFile) {
        try {
            File e = new File(xmlFile);
            return !e.exists()?null:DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(e);
        } catch (ParserConfigurationException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        } catch (SAXException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public static Document create(String rootTag) {
        return create(rootTag, (String)null);
    }

    public static Document createUTF_8(String rootTag) {
        return create(rootTag, "UTF-8");
    }

    public static Document create(String rootTag, String encoding) {
        if(isBlank(rootTag)) {
            return null;
        } else {
            String xml = "<?xml version=\"1.0\"" + (isBlank(encoding)?"":" encoding=\"" + encoding + "\"") + "?><" + rootTag + "></" + rootTag + ">";
            return parseXmlString(xml);
        }
    }

    public static Document loadOrCreate(String xmlFile) {
        return loadOrCreate(xmlFile, "root");
    }

    public static Document loadOrCreate(String xmlFile, String rootTag) {
        return loadOrCreate(xmlFile, rootTag, "UTF-8");
    }

    public static Document loadOrCreate(String xmlFile, String rootTag, String encoding) {
        try {
            File e = new File(xmlFile);
            if(!e.exists()) {
                String xml = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?><" + rootTag + "></" + rootTag + ">";
                save(xmlFile, parseXmlString(xml), encoding);
            }

            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(e);
        } catch (ParserConfigurationException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        } catch (SAXException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public static Document loadFromUri(String uri) {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
        } catch (ParserConfigurationException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        } catch (SAXException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public static Document load(InputStream stream) {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
        } catch (ParserConfigurationException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        } catch (SAXException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public static Document parseXmlString(String xml) {
        return parseXmlString(xml, null);
    }

    public static Document parseXmlString(String xml, String encoding) {
        ByteArrayInputStream input = null;

        try {
            input = new ByteArrayInputStream(isBlank(encoding)?xml.getBytes("UTF-8"):xml.getBytes(encoding.trim()));
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            return doc;
        } catch (UnsupportedEncodingException ex) {
            ;
        } catch (IOException ex) {
            return null;
        } catch (ParserConfigurationException ex) {
            return null;
        } catch (SAXException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    ;
                }
            }

        }

        return null;
    }

    public static Document parseXmlBuffer(byte[] buffer) {
        return parseXmlBuffer(buffer);
    }

    public static Document parseXmlBuffer(byte[] buffer, int offset, int length) {

        if(buffer != null && offset >= 0 && length > 0 && buffer.length >= offset + length) {
            ByteArrayInputStream input = null;

            try {
                input = new ByteArrayInputStream(buffer, offset, length);
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
                return doc;
            } catch (IOException ex) {
                return null;
            } catch (ParserConfigurationException ex) {
                return null;
            } catch (SAXException ex) {
                return null;
            } catch (Exception ex) {
            } finally {
                if(input != null) {
                    try {
                        input.close();
                    } catch (IOException ex) {
                    }
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static String toXmlString(Node doc) {
        return toXmlString(doc, null);
    }

    public static String toXmlString(Node doc, String encoding) {
        if(doc == null) {
            throw new IllegalArgumentException("invalid document!");
        } else {
            ByteArrayOutputStream byteStream = null;

            try {
                Transformer e = TransformerFactory.newInstance().newTransformer();
                if(!isBlank(encoding)) {
                    e.setOutputProperty("encoding", encoding);
                }

                byteStream = new ByteArrayOutputStream();
                e.transform(new DOMSource(doc), new StreamResult(byteStream));
                String xml = null;
                if(!isBlank(encoding)) {
                    xml = byteStream.toString(encoding);
                } else {
                    xml = byteStream.toString();
                }

                return xml;
            } catch (TransformerConfigurationException ex) {
                return null;
            } catch (TransformerException ex) {
                return null;
            } catch (UnsupportedEncodingException ex) {
            } catch (Exception ex) {
                return null;
            } finally {
                if(byteStream != null) {
                    try {
                        byteStream.close();
                    } catch (IOException ex) {
                    }
                }

            }

            return null;
        }
    }

    public static boolean save(String filePath, Document doc) {
        return save(filePath, doc, (String)null);
    }

    public static boolean saveUTF_8(String filePath, Document doc) {
        return save(filePath, doc, "UTF-8");
    }

    public static boolean save(String filePath, Document doc, String encoding) {
        if(doc == null) {
            throw new IllegalArgumentException("invalid document!");
        } else {
            FileOutputStream fileStream = null;

            try {
                Transformer e = TransformerFactory.newInstance().newTransformer();
                e.setOutputProperty("indent", "yes");
                if(!isBlank(encoding)) {
                    e.setOutputProperty("encoding", encoding);
                }

                File file = new File(filePath);
                file.getParentFile().mkdirs();
                if(!file.exists()) {
                    file.createNewFile();
                }

                fileStream = new FileOutputStream(file);
                e.transform(new DOMSource(doc), new StreamResult(fileStream));
                return true;
            } catch (TransformerConfigurationException ex) {
                return false;
            } catch (TransformerException ex) {
                return false;
            } catch (IOException ex) {
                ;
            } catch (Exception ex) {
                return false;
            } finally {
                if(fileStream != null) {
                    try {
                        fileStream.flush();
                    } catch (IOException ex) {
                        ;
                    }

                    try {
                        if(fileStream.getFD() != null) {
                            fileStream.getFD().sync();
                        }
                    } catch (IOException ex) {
                        ;
                    }

                    try {
                        fileStream.close();
                    } catch (IOException ex) {
                        ;
                    }
                }

            }

            return false;
        }
    }

    public static NodeList selectNodes(Node node, String xpath) {
        try {
            return (NodeList)XPathFactory.newInstance().newXPath().evaluate(xpath, node, XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            return null;
        }
    }

    public static Node selectSingleNode(Node node, String xpath) {
        try {
            return (Node)XPathFactory.newInstance().newXPath().evaluate(xpath, node, XPathConstants.NODE);
        } catch (XPathExpressionException ex) {
            return null;
        }
    }

    public static int getAttributeInt(Element elem, String name) {
        return getAttributeInt(elem, name, 0);
    }

    public static int getAttributeInt(Element elem, String name, int defaultVal) {
        return getAttributeInt(elem, name, defaultVal, 10);
    }

    public static int getAttributeInt(Element elem, int radix, String name) {
        return getAttributeInt(elem, name, 0, radix);
    }

    public static int getAttributeInt(Element elem, String name, int defaultVal, int radix) {
        try {
            if(elem.hasAttribute(name)) {
                String e = elem.getAttribute(name);
                if(e != null && (e = trimToEmpty(e)).length() != 0) {
                    if(e.charAt(0) == 43) {
                        e = e.substring(1);
                    }

                    return Integer.parseInt(e, radix);
                } else {
                    return defaultVal;
                }
            } else {
                return defaultVal;
            }
        } catch (Exception ex) {
            return defaultVal;
        }
    }

    public static long getAttributeLong(Element elem, String name) {
        return getAttributeLong(elem, name, 0L);
    }

    public static long getAttributeLong(Element elem, String name, long defaultVal) {
        return getAttributeLong(elem, name, defaultVal, 10);
    }

    public static long getAttributeLong(Element elem, int radix, String name) {
        return getAttributeLong(elem, name, 0L, radix);
    }

    public static long getAttributeLong(Element elem, String name, long defaultVal, int radix) {
        try {
            if(elem.hasAttribute(name)) {
                String e = elem.getAttribute(name);
                if(e != null && (e = trimToEmpty(e)).length() != 0) {
                    if(e.charAt(0) == 43) {
                        e = e.substring(1);
                    }

                    return Long.parseLong(e, radix);
                } else {
                    return defaultVal;
                }
            } else {
                return defaultVal;
            }
        } catch (Exception ex) {
            return defaultVal;
        }
    }

    public static String getAttributeString(Element elem, String name) {
        return getAttributeString(elem, name, "");
    }

    public static String getAttributeString(Element elem, String name, String defaultVal) {
        try {
            return elem.hasAttribute(name)?elem.getAttribute(name):defaultVal;
        } catch (Exception ex) {
            return defaultVal;
        }
    }

    public static boolean getAttributeBoolean(Element elem, String name) {
        return getAttributeBoolean(elem, name, false).booleanValue();
    }

    public static Boolean getAttributeBoolean(Element elem, String name, boolean defaultVal) {
        try {
            return Boolean.valueOf(elem.hasAttribute(name)?Boolean.parseBoolean(trimToEmpty(elem.getAttribute(name))):defaultVal);
        } catch (Exception ex) {
            return Boolean.valueOf(defaultVal);
        }
    }

    public static String getStringContent(Element elem, String xpath) {
        return getStringContent(elem, xpath, (String)null);
    }

    public static String getStringContent(Element elem, String xpath, String defaultVal) {
        try {
            Node e = selectSingleNode(elem, xpath);
            if(e != null) {
                String textContent = e.getTextContent();
                return textContent != null?textContent:defaultVal;
            } else {
                return defaultVal;
            }
        } catch (Exception ex) {
            return defaultVal;
        }
    }

    public static int getIntContent(Element elem, String xpath) {
        return getIntContent(elem, xpath, 0);
    }

    public static int getIntContent(Element elem, String xpath, int defaultVal) {
        return getIntContent(elem, xpath, defaultVal, 10);
    }

    public static int getIntContent(Element elem, int radix, String xpath) {
        return getIntContent(elem, xpath, 0, radix);
    }

    public static int getIntContent(Element elem, String xpath, int defaultVal, int radix) {
        try {
            Node e = selectSingleNode(elem, xpath);
            if(e != null) {
                String textContent = e.getTextContent();
                if(textContent != null && (textContent = trimToEmpty(textContent)).length() != 0) {
                    if(textContent.charAt(0) == 43) {
                        textContent = textContent.substring(1);
                    }

                    return Integer.parseInt(textContent, radix);
                } else {
                    return defaultVal;
                }
            } else {
                return defaultVal;
            }
        } catch (Exception ex) {
            return defaultVal;
        }
    }

    public static long getLongContent(Element elem, String xpath) {
        return getLongContent(elem, xpath, 0L);
    }

    public static long getLongContent(Element elem, String xpath, long defaultVal) {
        return getLongContent(elem, xpath, defaultVal, 10);
    }

    public static long getLongContent(Element elem, int radix, String xpath) {
        return getLongContent(elem, xpath, 0L, radix);
    }

    public static long getLongContent(Element elem, String xpath, long defaultVal, int radix) {
        try {
            Node e = selectSingleNode(elem, xpath);
            if(e != null) {
                String textContent = e.getTextContent();
                if(textContent != null && (textContent = trimToEmpty(textContent)).length() != 0) {
                    if(textContent.charAt(0) == 43) {
                        textContent = textContent.substring(1);
                    }

                    return Long.parseLong(textContent);
                } else {
                    return defaultVal;
                }
            } else {
                return defaultVal;
            }
        } catch (Exception ex) {
            return defaultVal;
        }
    }

    public static boolean getBooleanContent(Element elem, String xpath) {
        return getBooleanContent(elem, xpath, false);
    }

    public static boolean getBooleanContent(Element elem, String xpath, boolean defaultVal) {
        try {
            Node e = selectSingleNode(elem, xpath);
            if(e != null) {
                String textContent = e.getTextContent();
                return textContent != null?Boolean.parseBoolean(trimToEmpty(textContent)):defaultVal;
            } else {
                return defaultVal;
            }
        } catch (Exception ex) {
            return defaultVal;
        }
    }

    private static Element findOrAppendChild(Element elem, String xpath) {
        Element newElem = (Element)selectSingleNode(elem, xpath);
        if(newElem != null) {
            return newElem;
        } else {
            int pos = xpath.lastIndexOf(47);
            if(pos > 0) {
                String parentPath = xpath.substring(0, pos);
                String tagName = xpath.substring(pos + 1);
                newElem = (Element)selectSingleNode(elem, parentPath);
                if(newElem == null) {
                    newElem = findOrAppendChild(elem, parentPath);
                }

                return (Element)newElem.appendChild(elem.getOwnerDocument().createElement(tagName));
            } else {
                return pos < 0?(Element)elem.appendChild(elem.getOwnerDocument().createElement(xpath)):null;
            }
        }
    }

    public static Node putStringContent(Element elem, String xpath, String textContent) {
        Element node = isBlank(xpath)?elem:findOrAppendChild(elem, xpath);
        node.setTextContent(textContent);
        return node;
    }

    public static Node putIntContent(Element elem, String xpath, int intContent) {
        return putStringContent(elem, xpath, Integer.toString(intContent));
    }

    public static Node putIntContent(Element elem, String xpath, int intContent, int radix) {
        return putStringContent(elem, xpath, Integer.toString(intContent, radix));
    }

    public static Node putLongContent(Element elem, String xpath, long longContent) {
        return putStringContent(elem, xpath, Long.toString(longContent));
    }

    public static Node putLongContent(Element elem, String xpath, long longContent, int radix) {
        return putStringContent(elem, xpath, Long.toString(longContent, radix));
    }

    public static Node putBooleanContent(Element elem, String xpath, boolean boolContent) {
        return putStringContent(elem, xpath, Boolean.toString(boolContent));
    }

    private static boolean isBlank(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    private static String trimToEmpty(String str) {
        return str == null?"":str.trim();
    }
}
