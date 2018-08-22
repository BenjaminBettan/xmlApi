package com.bbe.xmlapi.core;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.bbe.xmlapi.util.other.SaxHandler;
import com.bbe.xmlapi.util.persist.EntityToSerialize;
import com.bbe.xmlapi.util.persist.PersistConfigurator;
import com.bbe.xmlapi.util.persist.SerializeToEntity;

/**Singleton */
public class EntityControler{

	/***Next is for singleton*/
	private EntityControler() {}
	private static class SingletonHolder { private static final EntityControler instance = new EntityControler(); }
	public static EntityControler getInstance() {return SingletonHolder.instance;}
	/***End singleton part*/

	private static final Logger logger = Logger.getLogger(EntityControler.class);

	private static Map<Long, Entity> mapEntities = new HashMap<>();
	private static Entity root;
	private static long id = 0;
	
	private static boolean toHardDrive = false;
	
	public static boolean isPersistMode() {
		return toHardDrive;
	}
	
	public static void setToHardDriveAndClean(boolean toHardDrive_) {
		toHardDrive = toHardDrive_;
		clean();
	}
	
	protected static synchronized long getNewValue() {
		return id++;
	}
	
	public static Entity getEntity(long l) {
		if (toHardDrive) {
			try {
				return SerializeToEntity.get(l);
			} catch (ClassNotFoundException | IOException e) {
				logger.warn(e);
				return null;
			}	
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
		id = 0;
		mapEntities.clear();
		root = null;
		if (PersistConfigurator.getTmpSubDir()==null && toHardDrive) {//Set tmp dir
			PersistConfigurator.setTmpSubDir(""+System.currentTimeMillis());	
		}
	}

	/**
	 * Please use EntityControler.putEntity(Entity e) to update your field EntityControler.mapEntities
	 * @return empty if toHardDrive==true
	 */
	public static synchronized Map<Long, Entity> getMapEntities() {
		return mapEntities;
	}
	
	protected static synchronized void putEntity(Entity e) {
		if (toHardDrive) {
			EntityToSerialize.persistOnHardDrive(e);
		}
		else {
			mapEntities.put(e.getId(), e);	
		}
		
		if (root == null) {
			root = e;
		}
	}
	
	public Entity parseFileWithSax(String filePath) throws Exception {
		
		SaxHandler mySaxHandler = new SaxHandler();
		
		try {
			SAXParserFactory.newInstance().newSAXParser().parse(filePath, mySaxHandler);
			return mySaxHandler.getRoot();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.warn(e.getMessage());
			throw e;
		}
	}
	
	public static Entity parseWithSax(String xmlContent) throws Exception {
		
		SaxHandler mySaxHandler = new SaxHandler();
		
		try {
			SAXParserFactory.newInstance().newSAXParser().parse(new InputSource(new StringReader(xmlContent)), mySaxHandler);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			logger.error(e.getMessage());
			throw e;
		}
		root = mySaxHandler.getRoot();
		
		return root;
	}

	public static Entity getRoot() {
		return root;
	}
	
}
