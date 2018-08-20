package com.bbe.xmlapi.utilxml.persist;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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
		
		if (Files.isDirectory(Paths.get(filePath))) {
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
		else {
			logger.warn("Fail create dir : "+ filePath);
			return false;
		}
		
	}
}