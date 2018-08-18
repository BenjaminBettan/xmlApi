package com.bbe.xmlapi.utilxml;

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.bbe.xmlapi.core.Entity;
import com.bbe.xmlapi.core.XMLEntity;

public class SaxHandler extends DefaultHandler {
	
	private Entity root,pointer;
	private int level = 0;

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
		
		if (pointer == null) {
			
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
			pointer.setData(pointer.getData()+data);
		}
		
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		level--;
		pointer = pointer.getParent();
		
	}
}