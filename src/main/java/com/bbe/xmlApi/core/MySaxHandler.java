package com.bbe.xmlApi.core;

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MySaxHandler extends DefaultHandler {
	
	private Entity_I root,pointer;
	
	private int level = 0;
	private Map<String, String> currentAttributes = null;
	private boolean isFirstOccuranceInProcess = true; 

	@Override
	public void startElement(String uri, String localName, String tagName, Attributes attributes) throws SAXException {
		
		if (attributes.getLength()>0) {
			currentAttributes = new HashMap<>();
			for (int i=0; i < attributes.getLength(); i++) 
			{
				currentAttributes.put(attributes.getQName(i), attributes.getValue(i));
			}
		}
		else {
			currentAttributes = null;
		}
		
		if (isFirstOccuranceInProcess) {
			
			root = new XMLEntity(tagName, currentAttributes, level);
			pointer = root;
			isFirstOccuranceInProcess = false;
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
		if (pointer.getParent()==null) {//sax process is over. -> clear if you want to use this instance again
			isFirstOccuranceInProcess = true;
		}
		pointer = pointer.getParent();
		
	}
	
	public Entity_I getRoot() {
		return root;
	}
}