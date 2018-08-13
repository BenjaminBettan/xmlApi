package com.bbe.xmlApi.core;



import java.util.Map;

/**
 * 
 * @author benjamin
 * Entity to persist
 */
public class VirtualXMLEntity extends AbstractXMLEntity{

	public VirtualXMLEntity(String currentTag, Map<String, String> currentAttributes, int level2) {
		this.id = XMLEntityControler.getInstance().getNewValue();
		this.tag = currentTag;
		this.attributes = currentAttributes;
		this.level = level2;
		this.isVirtualXMLEntity = true;
	}

	public VirtualXMLEntity(String currentTag, Map<String, String> att) {
		this(currentTag,att,0);
		XMLEntityControler.mapEntities.put(this.getId(), this);
	}
	
	public VirtualXMLEntity(String currentTag) {
		this(currentTag,null,0);
	}
	
	@Override
	public XMLEntity addChild(AbstractXMLEntity x_) {
		return addChild((VirtualXMLEntity) x_);
	}

	public XMLEntity addChild(XMLEntity x_) {
		this.setIsFatherOf(x_.getId());
		x_.setIsChildOf(this.getId());
		x_.level = this.level+1;
		return x_;
	}
	
	@Override
	public String toString() {
		return "XMLEntity [id=" + id + ", level=" + level + ", tag=" + tag + ", data=" + data +  ", leaf="
				+ isLeaf() + ", isChildOf=" + isChildOf + ", attributes=" + attributes 
				+ ", isFatherOf=" + isFatherOf +"]";
	}

	public String showXml() {

		return showXml("1.0","UTF-8",null);
	}

	public String showXml(String version, String encoding, String grammaire) {

		return "<?xml version=\""+version+"\" encoding=\""+encoding+"\""+ ((grammaire == null) ? "" : " "+grammaire)  +" ?>"+showXmlValue();
	}

	public String showXmlValue() {

		String header;

		if (attributes==null || attributes.size()==0) 
		{
			header=new String("<"+tag+">");
		}
		else 
		{
			header=new String("<"+tag+" "+attributes.toString().substring(1, attributes.toString().length()-1).replace("=", "=\"").replace(",", "\"") + "\">");
		}

		String footer=new String("</"+tag+">");
		return header + data + getSonTags() +footer;

	}

	@Override
	public Object getThis() {
		return this;
	}

}

