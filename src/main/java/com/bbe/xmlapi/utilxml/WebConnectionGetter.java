package com.bbe.xmlapi.utilxml;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.apache.log4j.Logger;

import com.bbe.xmlapi.core.Entity;
import com.bbe.xmlapi.core.EntityControler;

public class WebConnectionGetter {
	private static final Logger logger = Logger.getLogger(WebConnectionGetter.class);

	private WebConnectionGetter() {}
	
	public static Entity get(String url) {

		String content = null;
		URLConnection connection = null;
		
		try {
			connection = new URL(url).openConnection();
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		
		if (connection!=null) {
			try (Scanner scanner = new Scanner(connection.getInputStream())){

				scanner.useDelimiter("\\Z");
				content = scanner.next();
			}catch ( Exception e ) {
				logger.warn(e.getMessage());
			}	
		}

		return (content==null) ? null : EntityControler.parseWithSax(content);
	}
}
