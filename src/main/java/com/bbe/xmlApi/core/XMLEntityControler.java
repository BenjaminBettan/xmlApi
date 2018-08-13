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
	
	protected static Map<Long, XMLEntity> mapEntities = new HashMap<Long, XMLEntity>();

	protected long getNewValue() {
		return compt++;
	}
	
	public XMLEntity getRoot() 
	{
		return mapEntities.get(0L);
	}

	public XMLEntity getEntity(long l) {
		return mapEntities.get(l);
	}
	
}
