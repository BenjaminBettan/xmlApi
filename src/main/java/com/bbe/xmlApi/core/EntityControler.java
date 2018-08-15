package com.bbe.xmlApi.core;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
/***
 * This is a singleton.
 * 
 * 
 * @author benjamin
 */
public class EntityControler{

	/***next is for singleton*/
	private EntityControler() {}
	private static class SingletonHolder { private final static EntityControler instance = new EntityControler(); }
	public static EntityControler getInstance() {  EntityControler x = SingletonHolder.instance; return x; }
	
	private static long compt = 0L;
	private MySaxHandler mySaxHandler = new MySaxHandler();
	
	private static Map<Long, Entity> mapEntities = new HashMap<Long, Entity>();

	protected synchronized long getNewValue() {
		return ++compt;
	}
	
	public Entity getEntity(long l) {
		return mapEntities.get(l);
	}
	
	public static void clean() {
		mapEntities.clear();
		compt = 0L;
	}

	public static Map<Long, Entity> getMapEntities() {
		return mapEntities;
	}
	
	public Entity parseWithSax(String filePath) {
		EntityControler.clean();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		
		try {
			saxParser = factory.newSAXParser();
			saxParser.parse(filePath, mySaxHandler);
			return mySaxHandler.getRoot();
			
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

		
	}
}
