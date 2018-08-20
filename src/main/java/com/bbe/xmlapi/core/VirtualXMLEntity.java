package com.bbe.xmlapi.core;

import java.io.Serializable;

public class VirtualXMLEntity extends Entity implements Serializable{

	private static final long serialVersionUID = Long.MAX_VALUE - 99999;
/**
 * If you print values of this nodes and sons, this node won't be printed
 * This is the only way to get an xml with several root tags
 * Note : this kind of xml can't be indented yet
 */
	public VirtualXMLEntity() {
		this.isChildOf = -1;
		EntityControler.putEntity(this);
	}

	@Override
	public String toString() {
		return "VirtualXMLEntity [id=" + id + ", level=" + level + ", tag=" + tag + ", data=" + data +  ", leaf="
				+ isLeaf() + ", isChildOf=" + isChildOf + ", attributes=" + attributes 
				+ ", isFatherOf=" + getIsFatherOf() +"]";
	}

	@Override
	public String showXml() {

		return showXml("1.0","UTF-8",null);
	}

	@Override
	public String showXml(String version, String encoding, String grammaire) {

		return "<?xml version=\""+version+"\" encoding=\""+encoding+"\""+ ((grammaire==null) ? "" : " "+grammaire)  +" ?>"+show();
	}

	@Override
	public String show() {
		String s = "";
		for (Long l : getIsFatherOf()) 
		{
			s+=getEntityById(l).show();
		}

		return s;
	}

}

