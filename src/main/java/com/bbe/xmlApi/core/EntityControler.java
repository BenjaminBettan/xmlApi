package com.bbe.xmlApi.core;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.bbe.xmlApi.util.xml.SaxHandler;
import com.bbe.xmlApi.util.xml.persist.XmlLoad;
import com.bbe.xmlApi.util.xml.persist.XmlPersist;
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
	private long id = 0;
	
	private final static Logger logger = Logger.getLogger(EntityControler.class);
	
	private static boolean toHardDrive = false;

	public static void setToHardDrive(boolean toHardDrive) {
		EntityControler.toHardDrive = toHardDrive;
	}

	protected synchronized long getNewValue() {
		return id++;
	}
	
	public Entity getEntity(long l) {
		if (toHardDrive) {
			return XmlLoad.serializeObjectToEntity(l);	
		}
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
	 * @return empty if toHardDrive==true
	 */
	public synchronized static Map<Long, Entity> getMapEntities() {
		return mapEntities;
	}
	
	public synchronized static void putEntity(Entity e) {
		if (toHardDrive) {
			XmlPersist.persist(e);
		}
		else {
			mapEntities.put(e.getId(), e);	
		}
		
		if (root == null) {
			root = e;
		}
	}
	
	public Entity parseFileWithSax(String filePath) {
		
		SaxHandler mySaxHandler = new SaxHandler();
		
		try {
			SAXParserFactory.newInstance().newSAXParser().parse(filePath, mySaxHandler);
			return mySaxHandler.getRoot();
			
		} catch (ParserConfigurationException | SAXException e) {
			logger.warn(e.getMessage());
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return null;
	}
	
	public static Entity parseWithSax(String xmlContent) {
		
		SaxHandler mySaxHandler = new SaxHandler();
		
		try {
			SAXParserFactory.newInstance().newSAXParser().parse(new InputSource(new StringReader(xmlContent)), mySaxHandler);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			logger.error(e.getMessage());
		}
		root = mySaxHandler.getRoot();
		
		return root;
	}

	public static Entity getRoot() {
		return root;
	}
	
}
