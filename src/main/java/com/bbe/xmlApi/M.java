package com.bbe.xmlApi;

import java.util.HashMap;
import java.util.Map;
import com.bbe.xmlApi.core.*;

public class M {
	
	public static void main(String[] args) {
		
		Entity v = new VirtualXMLEntity("a");
		
		v.addChild("b");
		v.addChild("b");
		v.addChild("b");
		
		System.out.println(v.showXml());
		
		Map<String, String> attributes = new HashMap<>();
		attributes.put("aKey", "aValue");
		
		Entity x1 = new XMLEntity("x1");
		
		Entity x2 = x1.addChild("x2");
		x1.addChild("x2");
		x2.addChild("x3").setAttributes(attributes);
		
		
		for (Map.Entry<Long, Entity> xmlEntity : EntityControler.getMapEntities().entrySet()) {
			System.out.println(xmlEntity.getValue());
		}
		System.out.println(x1.showXml());
		
	}

}
