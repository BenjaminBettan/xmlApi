package com.bbe.xmlapi.utilxml;

import org.apache.log4j.Logger;

//https://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Pretty-prints xml, supplied as a string.
 * <p/>
 * eg.
 * <code>
 * String formattedXml = new XmlFormatter().format("<tag><nested>hello</nested></tag>");
 * </code>
 */
public class XmlFormatterIndent {
	private static final Logger logger = Logger.getLogger(XmlFormatterIndent.class);
	private XmlFormatterIndent() {}

	public static String format(String unformattedXml) throws IOException {

		final Document document = parseXmlFile(unformattedXml);
		Writer out = new StringWriter();
		
		if (document!=null) {
			
			OutputFormat format = new OutputFormat(document);
			format.setLineWidth(65);
			format.setIndenting(true);
			format.setIndent(2);
			
			XMLSerializer serializer = new XMLSerializer(out, format);
			serializer.serialize(document);

			return out.toString();
		}
		return "";
	}

	private static Document parseXmlFile(String in) {//get a Document instance by SAX
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(in));
			return db.parse(is);
		} catch (ParserConfigurationException e) {
			logger.warn(e.getMessage());
		} catch (SAXException e) {
			logger.warn(e.getMessage());
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}

		return null;

	}
}