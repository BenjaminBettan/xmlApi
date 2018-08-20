package com.bbe.xmlapi.utilxml.persist;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InvalidClassException;
import java.nio.file.Files;
import org.apache.log4j.Logger;
import com.bbe.xmlapi.core.Entity;
import com.bbe.xmlapi.core.XMLEntity;

public class XmlLoad
{
	private static final Logger logger = Logger.getLogger(XmlLoad.class);
	private XmlLoad() {}

	public static Entity serializeObjectToEntity(long l) throws ClassNotFoundException, IOException	{
		Entity e = null;
		String filePath = PersistConfigurator.convertToFilePath(l);
		File f = new File((filePath+PersistConfigurator.getPrefix()+l));
		byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(f.toPath());
		} catch (IOException e1) {
			logger.warn(e1.getMessage());
		}
		if (fileContent !=null) {
			e = (XMLEntity) deserialize(fileContent);
		}
		return e;
	}

	private static Object deserialize(byte[] buffer){
		Object obj =null;
		try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer)){
			//LookAheadObjectInputStream instead of InputStream
			try(LookAheadObjectInputStream ois = new LookAheadObjectInputStream(bais)) {
				try {
					obj = ois.readObject();
				} 
				catch (InvalidClassException e1) {
					logger.warn(e1.getMessage());
				}
				catch (ClassNotFoundException | IOException e2) {
					logger.warn(e2.getMessage());
				}
			} catch (IOException e3) {
				logger.warn(e3.getMessage());
			}
		} catch (IOException e5) {
			logger.warn(e5.getMessage());
		}
		return obj;
	}
	
}