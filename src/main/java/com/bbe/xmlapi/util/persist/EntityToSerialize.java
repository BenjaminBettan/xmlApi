package com.bbe.xmlapi.util.persist;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

import com.bbe.xmlapi.core.Entity;
public class EntityToSerialize
{
	private static final Logger logger = Logger.getLogger(EntityToSerialize.class);

	private EntityToSerialize() {}
/**
 * 
 * @param e
 * @return true if the file/entity has been persisted on hard drive (tmp directory cf com.bbe.xmlapi.utilxml.persist.PersistConfigurator.setTmp / setTmpSubDir)
 */
	public static synchronized boolean persistOnHardDrive(Entity e)
	{

		long l = e.getId();

		String filePath = PersistConfigurator.convertToFilePath(l);
		new File(filePath).mkdirs();
		
		if (Files.isDirectory(Paths.get(filePath))) {//if dir exist
			try(FileOutputStream fos = new FileOutputStream(filePath+l))
			{
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(e);
				oos.close();
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