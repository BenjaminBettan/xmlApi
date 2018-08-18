package com.bbe.xmlApi.util.xml.persist;

import java.io.*;

import com.bbe.xmlApi.core.Entity;
public class XmlPersist
{
	public static void persist(Entity e)
	{
		
		long l = e.getId();
		
		String filePath = Useful.getPath(l);
		new File(filePath).mkdirs();
		
		try
		{
			FileOutputStream fos = new FileOutputStream(filePath+l);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(e);
			oos.close();
			fos.close();
		}catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		
	}
}