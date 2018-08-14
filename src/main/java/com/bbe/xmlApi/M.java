package com.bbe.xmlApi;

import java.util.HashMap;
import java.util.Map;

import com.bbe.xmlApi.core.Entity;
import com.bbe.xmlApi.core.VirtualXMLEntity;
import com.bbe.xmlApi.core.XMLEntity;
import com.bbe.xmlApi.core.XMLEntityControler;

public class M {
	
	public static void main(String[] args) {
		
		Entity v = new VirtualXMLEntity("a");
		
		v.addChild("b");
		
		System.out.println(v.showXml());
		
		System.out.println(v.isVirtualEntity());
		
		Map<String, String> attributes = new HashMap<>();
		attributes.put("aKey", "aValue");
		
		Entity x1 = new XMLEntity("x1");
		
		Entity x2 = x1.addChild("x2");
		x1.addChild("x2");
		x2.addChild("x3").setAttributes(attributes);
		
		
		XMLEntityControler.getInstance();
		for (Map.Entry<Long, Entity> xmlEntity : XMLEntityControler.getMapEntities().entrySet()) {
			System.out.println(xmlEntity.getValue());
		}
		System.out.println(x1.showXml());
		
	}

}
