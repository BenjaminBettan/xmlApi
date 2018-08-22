package com.bbe.xmlapi.util.display;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class XmlToJson {

    private static final int PRETTY_PRINT_INDENT_FACTOR = 4;
    private static final Logger logger = Logger.getLogger(XmlToJson.class);
    private XmlToJson() {}
    
    public static String get(String xmlStr) {
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xmlStr);
            return xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        } catch (JSONException je) {
        	logger.warn(je.toString());
        }
		return null;
    }
}