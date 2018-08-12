package com.bbe.xmlApi;

import com.bbe.xmlApi.core.XMLEntity;

public class M {
	
	public static void main(String[] args) {
		
		XMLEntity x = new XMLEntity("x");
		XMLEntity y = new XMLEntity("y");
		
		XMLEntity x2 = x.addChild("x2");
		XMLEntity y2 = y.addChild("y2");
		
		x2.addChild(y2);
		
		System.out.println(x.showXml());
		
		System.out.println(x);
	}

}
