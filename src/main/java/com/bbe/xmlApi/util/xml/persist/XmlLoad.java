package com.bbe.xmlApi.util.xml.persist;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import org.apache.commons.io.serialization.ValidatingObjectInputStream;
import org.apache.log4j.Logger;

import com.bbe.xmlApi.core.Entity;

public class XmlLoad
{
	private final static Logger logger = Logger.getLogger(XmlLoad.class);

	public static Entity serializeObjectToEntity(long l)
	{
		Entity e = null;
		String filePath = Useful.convertToFilePath(l);
		File f = new File((filePath+Useful.getPrefix()+l));
		byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(f.toPath());
		} catch (IOException e1) {
			logger.warn(e1.getMessage());
		}
		if (fileContent !=null) {
			try (
					ByteArrayInputStream bais = new ByteArrayInputStream(fileContent);
					ValidatingObjectInputStream ois = new ValidatingObjectInputStream(bais);
				) 
			{
				ois.accept(Entity.class);
				e = (Entity) ois.readObject();
			} catch (IOException e1) {
				logger.warn(e1.getMessage());
			} catch (ClassNotFoundException e1) {
				logger.warn(e1.getMessage());
			}
		}

		return e;

	}
}