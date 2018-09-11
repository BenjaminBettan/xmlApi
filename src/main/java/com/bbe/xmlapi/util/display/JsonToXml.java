package com.bbe.xmlapi.util.display;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;

import com.bbe.xmlapi.core.Entity;
import com.bbe.xmlapi.core.EntityControler;

public class JsonToXml {

    private JsonToXml() {}
    private static final Logger logger = Logger.getLogger(JsonToXml.class);
    
    public static String get(String jsonContent) {
		return XML.toString(new JSONObject(jsonContent));
    }
    
    public static Entity getEntity(String jsonContent) {
    	
		try {
			return EntityControler.parseWithSax(XML.toString(new JSONObject(jsonContent)));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return null;
    }
    
}