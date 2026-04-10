package org.openmrs.module.registration.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class BioPluginResponseParser {
	
	public static String registerResponse(String xml) {
		return getAttribute(xml, "result", "value");
	}
	
	public static String isMatch(String xml) {
		return getAttribute(xml, "result", "value");
	}
	
	private static String getAttribute(String xml, String tag, String attribute) {
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			
			NodeList nodes = doc.getElementsByTagName(tag);
			if (nodes.getLength() > 0) {
				Element element = (Element) nodes.item(0);
				return element.getAttribute(attribute);
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Invalid biometric XML", e);
		}
		return "";
	}
	
}
