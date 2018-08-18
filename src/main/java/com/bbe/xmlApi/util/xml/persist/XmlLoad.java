package com.bbe.xmlApi.util.xml.persist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import com.bbe.xmlApi.core.Entity;

public class XmlLoad
{
	public static Entity serializeObjectToEntity(long l)
	{
		Entity e = null;
		String filePath = Useful.getPath(l);
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(filePath+Useful.getPrefix()+l);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if (fis!=null) {
			try {
				ois = new ObjectInputStream(fis);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (ois!=null) {
				try {
					e = (Entity) ois.readObject();
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
				try {
					ois.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			try {
				fis.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return e;
		
	}
}