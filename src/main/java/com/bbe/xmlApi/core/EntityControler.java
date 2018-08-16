package com.bbe.xmlApi.core;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
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
	public static EntityControler getInstance() {return SingletonHolder.instance;}
	
	private static Map<Long, Entity> mapEntities = new HashMap<Long, Entity>();
	private static Entity root;

	protected synchronized long getNewValue() {
		return mapEntities.size() + 1;
	}
	
	public Entity getEntity(long l) {
		return mapEntities.get(l);
	}
	
	/**
	 * clear mapEntities HashMap and put null to root Entity.
	 * Useful in an other context to 
	 *  1 - let EntityControler handle root entity 
	 *  2 - filter old values to get efficient logs
	 */
	public static void clean() {
		mapEntities.clear();
		root = null;
	}

	/**
	 * Please use EntityControler.putEntity(Entity e) to update your field EntityControler.mapEntities
	 * @return
	 */
	public synchronized static Map<Long, Entity> getMapEntities() {
		return mapEntities;
	}
	
	public synchronized static void putEntity(EntityImplementation e) {
		mapEntities.put(e.getId(), e);
		if (root == null) {
			root = e;
		}
	}
	
	public Entity parseWithSax(String filePath) {
		
		MySaxHandler mySaxHandler = new MySaxHandler();
		
		try {
			SAXParserFactory.newInstance().newSAXParser().parse(filePath, mySaxHandler);
			return mySaxHandler.getRoot();
			
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Entity getRoot() {
		return root;
	}
	
}
