package com.bbe.xmlApi.core;


import java.util.HashMap;
import java.util.Map;

public class XML_Controler implements XML_Controler_I
{
	/***
	 * next is for singleton
	 */
	private XML_Controler() {}
	private static class SingletonHolder { private final static XML_Controler instance = new XML_Controler(); }
	public static XML_Controler getInstance() {  XML_Controler x = SingletonHolder.instance; return x; }
	
	private long compt = 0L;
	
	protected static Map<Long, XMLEntity> mapEntities = new HashMap<Long, XMLEntity>();
	

	protected long getNewValue() {
		return compt++;
	}
	
	public XMLEntity getRoot() 
	{
		return mapEntities.get(0L);
	}

	@Override
	public XMLEntity getEntity(long l) {
		return mapEntities.get(l);
	}
	
}
