package com.bbe.xmlApi.util.xml.persist;

import java.io.*;

import org.apache.log4j.Logger;

import com.bbe.xmlApi.core.Entity;
public class XmlPersist
{
	private final static Logger logger = Logger.getLogger(XmlPersist.class);

	public static boolean persist(Entity e)
	{
		
		long l = e.getId();
		
		String filePath = Useful.convertToFilePath(l);
		new File(filePath).mkdirs();
		
		try(FileOutputStream fos = new FileOutputStream(filePath+l))
		{
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(e);
			oos.close();
			fos.close();
			return true;
		}catch(IOException ioe)
		{
			logger.warn(ioe.getMessage());
			return false;
		}
		
	}
}