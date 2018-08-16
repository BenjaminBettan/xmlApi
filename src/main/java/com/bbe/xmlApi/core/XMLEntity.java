package com.bbe.xmlApi.core;

import java.io.Serializable;
import java.util.Map;

/**
 * @author benjamin
 * Entity to persist
 */
public class XMLEntity extends Entity implements Serializable{

	private static final long serialVersionUID = Long.MAX_VALUE - 9999;

	public XMLEntity(String currentTag, Map<String, String> currentAttributes, int level2, String currentData) {
		this.id = EntityControler.getInstance().getNewValue();
		this.tag = currentTag;
		this.attributes = currentAttributes;
		this.level = level2;
		this.data = currentData;
		EntityControler.putEntity(this);
	}

	public XMLEntity(String currentTag) {
		this(currentTag, null,0,null);
	}

	public XMLEntity(String currentTag, Map<String, String> currentAttributes, int level2) {
		this(currentTag, currentAttributes,level2,null);
	}

	public XMLEntity(String currentTag, Map<String, String> att) {
		this(currentTag, att,0,null);
	}

	public XMLEntity(String currentTag, int i) {
		this(currentTag, null,i,null);
	}

	@Override
	public String toString() {
		return "XMLEntity [id=" + id + ", level=" + level + ", tag=" + tag + ", data=" + data +  ", leaf="
				+ isLeaf() + ", isChildOf=" + isChildOf + ", attributes=" + attributes 
				+ ", isFatherOf=" + isFatherOf +"]";
	}

}

