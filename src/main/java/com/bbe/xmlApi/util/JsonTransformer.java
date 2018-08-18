package com.bbe.xmlApi.util;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class JsonTransformer {

    private final static int PRETTY_PRINT_INDENT_FACTOR = 4;
    private final static Logger logger = Logger.getLogger(JsonTransformer.class);
    private JsonTransformer() {}
    
    public static String xmlToJson(String xmlStr) {
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xmlStr);
            return xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        } catch (JSONException je) {
        	logger.warn(je.toString());
        }
		return null;
    }
}