package com.bbe.xmlApi.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class JsonTransformer {

    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    public static String xmlToJson(String xmlStr) {
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xmlStr);
            return xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
		return null;
    }
}