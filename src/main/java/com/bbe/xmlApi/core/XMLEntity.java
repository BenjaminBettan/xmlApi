package com.bbe.xmlApi.core;

import java.util.Map;

/**
 * @author benjamin
 * Entity to persist
 */
public class XMLEntity extends Entity{

	protected XMLEntity(String currentTag, Map<String, String> currentAttributes, int level2) {
		this.id = EntityControler.getInstance().getNewValue();
		this.tag = currentTag;
		this.attributes = currentAttributes;
		this.level = level2;
	}

	public XMLEntity(String currentTag, Map<String, String> att) {
		this(currentTag, att,0);
		EntityControler.getMapEntities().put(this.getId(), this);
	}
	
	public XMLEntity(String currentTag) {
		this(currentTag, null,0);
		EntityControler.getMapEntities().put(this.getId(), this);
	}

	
	public XMLEntity(String currentTag, int i) {
		this(currentTag, null,i);
		EntityControler.getMapEntities().put(this.getId(), this);
	}

	@Override
	public String toString() {
		return "XMLEntity [id=" + id + ", level=" + level + ", tag=" + tag + ", data=" + data +  ", leaf="
				+ isLeaf() + ", isChildOf=" + isChildOf + ", attributes=" + attributes 
				+ ", isFatherOf=" + isFatherOf +"]";
	}

}

