package com.bbe.xmlApi.util.xml.persist;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import com.bbe.xmlApi.core.Entity;

public class XmlLoad
{
	public static Entity serializeObjectToEntity(long l)
	{
		Entity e;
		String filePath = Useful.getPath(l);
		
		try
		{
			FileInputStream fis = new FileInputStream(filePath+Useful.getPrefix()+l);
			ObjectInputStream ois = new ObjectInputStream(fis);
			e = (Entity) ois.readObject();
			ois.close();
			fis.close();
			return e;
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}catch(ClassNotFoundException c)
		{
			c.printStackTrace();
		}
		return null;
	}
}