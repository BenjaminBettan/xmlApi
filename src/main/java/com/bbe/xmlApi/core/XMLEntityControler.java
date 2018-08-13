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
	
	protected static Map<Long, AbstractXMLEntity> mapEntities = new HashMap<Long, AbstractXMLEntity>();

	protected synchronized long getNewValue() {
		return compt++;
	}
	
	public AbstractXMLEntity getRoot() 
	{
		return mapEntities.get(0L);
	}

	public AbstractXMLEntity getEntity(long l) {
		return mapEntities.get(l);
	}
	
}
