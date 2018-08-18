package com.bbe.xmlapi.utilxml.persist;

import java.io.*;

import org.apache.log4j.Logger;

import com.bbe.xmlapi.core.Entity;
public class XmlPersist
{
	private static final Logger logger = Logger.getLogger(XmlPersist.class);

	private XmlPersist() {}
	
	public static boolean persist(Entity e)
	{
		
		long l = e.getId();
		
		String filePath = PersistConfigurator.convertToFilePath(l);
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