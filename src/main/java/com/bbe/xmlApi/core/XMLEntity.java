package com.bbe.xmlApi.core;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author benjamin
 * Entity to persist
 */
public class XMLEntity {

	private long id = 0L;
	private String tag;
	private String data = new String("");
	private List<Long> isFatherOf;
	private long isChildOf = -1;
	private Map<String, String> attributes = null;
	private int level = 0;

	private XMLEntity(long id_,String currentTag, Map<String, String> currentAttributes, int level2) {
		this.id = id_;
		this.tag = currentTag;
		this.attributes = currentAttributes;
		this.level = level2;
	}

	public XMLEntity(String currentTag, Map<String, String> att) {
		this(XML_Controler.getInstance().getNewValue(),currentTag, att,0);
	}
	
	public XMLEntity(String currentTag) {
		this(XML_Controler.getInstance().getNewValue(),currentTag, null,0);
	}

	public XMLEntity addChild(String currentTag) {

		XMLEntity x_ = new XMLEntity(XML_Controler.getInstance().getNewValue(),currentTag, null, level+1);
		this.setIsFatherOf(x_.getId());
		x_.setIsChildOf(this.getId());
		x_.level = this.level+1;
		XML_Controler.mapEntities.put(x_.getId(), x_);

		return x_;
	}

	public XMLEntity addChild(XMLEntity x_) {
		this.setIsFatherOf(x_.getId());
		x_.setIsChildOf(this.getId());
		x_.level = this.level+1;
		return x_;
	}
	
	public boolean thisNodeHasNoAttribute() {
		if (attributes==null) {
			return true;
		}
		return attributes.isEmpty();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isLeaf() {
		return isFatherOf == null || isFatherOf.size() == 0;
	}

	public long getIsChildOf() {
		return isChildOf;
	}

	private void setIsChildOf(long isChildOf) {
		this.isChildOf = isChildOf;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public List<Long> getIsFatherOf() {
		return isFatherOf;
	}

	private void setIsFatherOf(long id2) {
		if (isFatherOf==null) 
		{
			isFatherOf = new ArrayList<>();
		}
		this.isFatherOf.add(id2);
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

	private String getSonTags() {

		String s = new String("");

		if (isFatherOf!=null) 
		{
			for (Long l : isFatherOf) 
			{
				s += XML_Controler.getInstance().getEntity(l).showXmlValue();
			}	
		}

		return s;
	}

}

