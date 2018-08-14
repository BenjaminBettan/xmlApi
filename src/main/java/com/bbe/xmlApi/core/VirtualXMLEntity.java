package com.bbe.xmlApi.core;

import java.util.Map;

/**
 * @author benjamin
 * Entity to persist
 */
public class VirtualXMLEntity extends Entity{

	private VirtualXMLEntity(String currentTag, Map<String, String> currentAttributes, int level2) {
		this.id = EntityControler.getInstance().getNewValue();
		this.tag = currentTag;
		this.attributes = currentAttributes;
		this.level = level2;
	}

	public VirtualXMLEntity(String currentTag, Map<String, String> att) {
		this(currentTag, att,0);
		EntityControler.getMapEntities().put(this.getId(), this);
	}
	
	public VirtualXMLEntity(String currentTag) {
		this(currentTag, null,0);
		EntityControler.getMapEntities().put(this.getId(), this);
	}

	
	public VirtualXMLEntity(String currentTag, int i) {
		this(currentTag, null,i);
		EntityControler.getMapEntities().put(this.getId(), this);
	}

	@Override
	public String toString() {
		return "VirtualXMLEntity [id=" + id + ", level=" + level + ", tag=" + tag + ", data=" + data +  ", leaf="
				+ isLeaf() + ", isChildOf=" + isChildOf + ", attributes=" + attributes 
				+ ", isFatherOf=" + isFatherOf +"]";
	}
	
	@Override
	public String showXml() {

		return showXml("1.0","UTF-8",null);
	}
	
	@Override
	public String showXml(String version, String encoding, String grammaire) {

		return "<?xml version=\""+version+"\" encoding=\""+encoding+"\""+ ((grammaire==null) ? "" : " "+grammaire)  +" ?>"+showXmlValue();
	}
	
	@Override
	public String showXmlValue() {
		String s = new String("");
		for (Long l : isFatherOf) 
		{
			s+=getEntityById(l).showXmlValue_();
		}
		
		return s;
	}
	
}

