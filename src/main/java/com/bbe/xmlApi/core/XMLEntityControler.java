package com.bbe.xmlApi.core;
import java.util.HashMap;
import java.util.Map;
/***
 * This is a singleton.
 * 
 * 
 * @author benjamin
 */
public class XMLEntityControler{

	/***next is for singleton*/
	private XMLEntityControler() {}
	private static class SingletonHolder { private final static XMLEntityControler instance = new XMLEntityControler(); }
	public static XMLEntityControler getInstance() {  XMLEntityControler x = SingletonHolder.instance; return x; }
	
	private long compt = 0L;
	
	private static Map<Long, AbstractXMLEntity> mapEntities = new HashMap<Long, AbstractXMLEntity>();

	protected synchronized long getNewValue() {
		return compt++;
	}
	
	public AbstractXMLEntity getEntity(long l) {
		return mapEntities.get(l);
	}

	public static Map<Long, AbstractXMLEntity> getMapEntities() {
		return mapEntities;
	}

}
