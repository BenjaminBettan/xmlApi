package com.bbe.xmlApi.core;

import java.util.Map;

import com.bbe.xmlApi.util.XmlFormatter;

/**
 * @author benjamin
 * Entity to persist
 */
public class XMLEntity extends Entity{

	private XMLEntity(String currentTag, Map<String, String> currentAttributes, int level2) {
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

	@Override
	public String showXml() {

		return XmlFormatter.format((showXml("1.0","UTF-8",null)));
	}
	@Override
	public String showXml(String version, String encoding, String grammaire) {

		return "<?xml version=\""+version+"\" encoding=\""+encoding+"\""+ ((grammaire==null) ? "" : " "+grammaire)  +" ?>"+showXmlValue();
	}
	@Override	
	public String showXmlValue() {
		
		String header,footer;
		
		if ("".equals(data) && this.isLeaf()) {
			
			footer=new String("");
			if (attributes==null || attributes.size()==0) 
			{
				header=new String("<"+tag+"/>");
			}
			else 
			{
				header=new String("<"+tag+" "+attributes.toString().substring(1, attributes.toString().length()-1).replace("=", "=\"").replace(",", "\"") + "\"/>");
			}
			
		}
		else {
			
			footer=new String("</"+tag+">");
			if (attributes==null || attributes.size()==0) 
				{
					header=new String("<"+tag+">");
				}
				else 
				{
					header=new String("<"+tag+" "+attributes.toString().substring(1, attributes.toString().length()-1).replace("=", "=\"").replace(",", "\"") + "\">");
				}
		}
		
		return header + data + getSonTags() +footer;

	}

}

