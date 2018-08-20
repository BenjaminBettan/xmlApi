package com.bbe.xmlapi.util.restitution;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class JsonTransformer {

    private static final int PRETTY_PRINT_INDENT_FACTOR = 4;
    private static final Logger logger = Logger.getLogger(JsonTransformer.class);
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