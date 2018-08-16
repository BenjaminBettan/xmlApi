package com.bbe.xmlApi.util;

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.bbe.xmlApi.core.Entity;
import com.bbe.xmlApi.core.XMLEntity;

public class SaxHandler extends DefaultHandler {
	
	private Entity root,pointer;
	private int level = 0;
	private boolean isFirstOccuranceInProcess = true; 

	public Entity getRoot() {
		return root;
	}
	
	@Override
	public void startElement(String uri, String localName, String tagName, Attributes attributes) throws SAXException {
		
		Map<String, String> currentAttributes = null;
		
		if (attributes.getLength()>0) {
			
			currentAttributes = new HashMap<>();
			
			for (int i=0; i < attributes.getLength(); i++) 
			{
				currentAttributes.put(attributes.getQName(i), attributes.getValue(i));
			}
			
		}
		
		if (isFirstOccuranceInProcess) {
			
			isFirstOccuranceInProcess = false;
			
			root = new XMLEntity(tagName, currentAttributes, level);
			pointer = root;
			
		}
		else {
			
			pointer = pointer.addChild(tagName);//now pointer is focus on the child
			
			pointer.setAttributes(currentAttributes);
			pointer.setLevel(level);
			
		}
		
		level++;
	}

	@Override
	public void characters(char[] caracteres, int debut, int longueur) {
		
		String data = new String(caracteres, debut, longueur).trim();
		
		if ( data != null && ! data.equals("") ) 
		{	
			pointer.setData(data);
		}
		
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		level--;
		pointer = pointer.getParent();
		
	}
	
	@Override
	public void endDocument() {
		isFirstOccuranceInProcess = true;
	}
	
}