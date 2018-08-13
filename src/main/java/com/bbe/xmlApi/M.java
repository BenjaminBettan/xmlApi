package com.bbe.xmlApi;

import com.bbe.xmlApi.core.XMLEntity;

public class M {
	
	public static void main(String[] args) {
		
		XMLEntity x1 = new XMLEntity("x1");
		XMLEntity y1 = new XMLEntity("y1");
		
		XMLEntity x2 = x1.addChild("x2");
		XMLEntity y2 = y1.addChild("y2");
		
		x2.addChild(y1);
		System.out.println(x1);
		System.out.println(x2);
		System.out.println(y1);
		System.out.println(y2);
		System.out.println(x1.showXml());
	}

}
