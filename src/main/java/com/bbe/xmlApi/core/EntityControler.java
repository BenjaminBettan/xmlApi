package com.bbe.xmlApi.core;
import java.util.HashMap;
import java.util.Map;
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
	
	private long compt = 0L;
	
	private static Map<Long, Entity> mapEntities = new HashMap<Long, Entity>();

	protected synchronized long getNewValue() {
		return compt++;
	}
	
	public Entity getEntity(long l) {
		return mapEntities.get(l);
	}

	public static Map<Long, Entity> getMapEntities() {
		return mapEntities;
	}

}
