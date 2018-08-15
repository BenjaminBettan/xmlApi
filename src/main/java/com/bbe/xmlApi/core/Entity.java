package com.bbe.xmlApi.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bbe.xmlApi.util.XmlFormatter;

public class Entity implements Entity_I{

	protected long id = 0L;
	protected String tag;
	protected String data = new String("");
	protected List<Long> isFatherOf;
	protected long isChildOf = -1;
	protected Map<String, String> attributes = null;
	protected int level = 0;//TODO bug. le level ne se repand pas quand on utilise la methode addchild

	protected Entity() {}

	public Entity addChild(String currentTag) {

		Entity x_ = new XMLEntity(currentTag, level+1);

		Entity this_ = (Entity) getThis();
		this_.setIsFatherOf(x_.getId());
		x_.setIsChildOf(this.getId());
		EntityControler.getMapEntities().put(x_.getId(), x_);

		return x_;
	}

	public Entity addChild(Entity x_) {
		this.setIsFatherOf(x_.getId());
		x_.setIsChildOf(this.getId());
		x_.level = this.level+1;
		EntityControler.getMapEntities().put(x_.getId(), x_);
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

	public Entity getParent() {
		if (isChildOf>0) {
			return EntityControler.getMapEntities().get(isChildOf);	
		}
		else {
			return null;
		}
	}

	protected void setLevel(int level2) {
		level = level2;
	}
	protected void setIsChildOf(long isChildOf) {
		this.isChildOf = isChildOf;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public Map<Long, Entity> getChilds() {
		Map<Long, Entity> mapEntities = new HashMap<Long, Entity>();

		if ( isFatherOf!=null) {
			for (Long l : isFatherOf) {
				mapEntities.put(l, EntityControler.getInstance().getEntity(l));
			}	
		}

		return mapEntities;
	}

	protected void setIsFatherOf(long id2) {
		if (isFatherOf==null) 
		{
			isFatherOf = new ArrayList<>();
		}
		this.isFatherOf.add(id2);
	}

	public Map<Long, Entity> getEntitiesByXpath(String xpath_) {

		Map<Long, Entity> mapEntities = new HashMap<Long, Entity>();

		xpath_ = xpath_.substring(1, xpath_.length() - 1);
		String[] x = xpath_.split("/");
		String[] xp = new String[x.length - 1];

		for (int i = 1; i < x.length; i++) {
			xp[i-1] = x[i];
		}

		Entity x_ = (Entity) getThis();

		if ( ! x[0].contains("[")) {//no attribute has to be found
			if (x_.getTag().equals(x[0])) {
				findNextEntity(mapEntities,xp,x_,0);

			}
			else {
				return mapEntities;	
			}			
		}
		else {// an attribute has to be found

			String strToParse = x[0].split("[\\[]")[1];
			strToParse = strToParse.replace("]", "").replace("\"", "");
			String tagToFind = x[0].split("[\\[]")[0];

			String[] attToFind = strToParse.split(",");
			for (int i = 0; i < 2; i++) {
				attToFind[i] = attToFind[i].substring(1);
			}
			boolean exit = false;
			int nbAttToFind = attToFind.length;
			for (String att : attToFind) {
				String[] attToFind_ = att.split("=");
				String key,value;
				key = attToFind_[0];
				value = attToFind_[1];
				if ( x_.getAttributes() !=null && x_.getAttributes().get(key).equals(value)) {
					nbAttToFind--;//if nbAttToFind = 0 -> attributes have been found
				}
				else if ( x_.getAttributes() !=null && ! x_.getAttributes().get(key).equals(value)) {
					exit = true;
					break;
				}
			}
			if ( ! exit) {
				if (x_.getTag().equals(tagToFind) && nbAttToFind==0) {
					findNextEntity(mapEntities,xp,x_,0);

				}
				else {
					return mapEntities;	
				}		
			}


		}

		return mapEntities;
	}

	private void findNextEntity(Map<Long, Entity> mapEntities, String[] xp, Entity x_, int i) {

		String tagToFind = xp[i];

		if ( tagToFind.contains("[")) {//attributes have to be found
			String strToParse = xp[i].split("[\\[]")[1];
			strToParse = strToParse.replace("]", "").replace("\"", "");
			tagToFind = xp[i].split("[\\[]")[0];

			for (Map.Entry<Long, Entity> xmlEntity : x_.getChilds().entrySet()) {
				String[] attToFind = strToParse.split(",");
				for (int j = 0; j < attToFind.length; j++) {
					attToFind[j] = attToFind[j].substring(1);
				}
				int nbAttToFind = attToFind.length;
				for (String att : attToFind) {
					String[] attToFind_ = att.split("=");
					String key,value;
					key = attToFind_[0];
					value = attToFind_[1];
					if ( xmlEntity.getValue().getAttributes() !=null && xmlEntity.getValue().getAttributes().get(key).equals(value)) {
						nbAttToFind--;
					}
				}

				if (tagToFind.equals(xmlEntity.getValue().getTag()) && nbAttToFind == 0) {
					if (i+1 == xp.length) {
						mapEntities.put(xmlEntity.getValue().id, xmlEntity.getValue());
					}
					else {
						findNextEntity(mapEntities,xp,xmlEntity.getValue(),i+1);					
					}
				}
			}

		}
		else {
			for (Map.Entry<Long, Entity> xmlEntity : x_.getChilds().entrySet()) {
				if (tagToFind.equals(xmlEntity.getValue().getTag())) {
					if (i+1 == xp.length) {
						mapEntities.put(xmlEntity.getValue().id, xmlEntity.getValue());
					}
					else {
						findNextEntity(mapEntities,xp,xmlEntity.getValue(),i+1);					
					}
				}
			}
		}


	}

	protected String getSonTags() {

		String s = new String("");

		if (isFatherOf!=null) 
		{
			for (Long l : isFatherOf) 
			{
				s += EntityControler.getInstance().getEntity(l).showXmlValue();
			}	
		}

		return s;
	}



	public Entity getEntityById(long l) {
		return EntityControler.getInstance().getEntity(l);
	}

	public boolean isVirtualEntity() {
		return getThis().getClass().getSimpleName().equals(VirtualXMLEntity.class.getSimpleName());
	}

	public Entity getThis() {
		return this;
	}

	@Override
	public String showXml() {
		if (isVirtualEntity()) {
			return showXml("1.0","UTF-8",null);
		}
		else {
			return XmlFormatter.format(showXml("1.0","UTF-8",null));
		}

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

	@Override
	public String toString() {
		return "Entity [id=" + id + ", level=" + level + ", tag=" + tag + ", data=" + data +  ", leaf="
				+ isLeaf() + ", isChildOf=" + isChildOf + ", attributes=" + attributes 
				+ ", isFatherOf=" + isFatherOf +"]";
	}


}
