package com.bbe.xmlApi.util.xml;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import com.bbe.xmlApi.core.Entity;
import com.bbe.xmlApi.core.EntityControler;

public class WebConnectionGetter {
	
	public static Entity get(String url) {
		
		String content = null;
		URLConnection connection = null;
		Scanner scanner = null;
		
		try {
		  connection =  new URL(url).openConnection();
		  scanner = new Scanner(connection.getInputStream());
		  scanner.useDelimiter("\\Z");
		  content = scanner.next();
		}catch ( Exception ex ) {
		    ex.printStackTrace();
		}
		finally {
			if (scanner!=null) {
				scanner.close();	
			}
		}
		
		return (content==null) ? null : EntityControler.getInstance().parseWithSax(content);
	}
}
