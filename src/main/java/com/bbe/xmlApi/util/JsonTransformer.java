package com.bbe.xmlApi.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class JsonTransformer {

    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    public static void xmlToJson(String xmlStr) {
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xmlStr);
            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            System.out.println(jsonPrettyPrintString);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
    }
}