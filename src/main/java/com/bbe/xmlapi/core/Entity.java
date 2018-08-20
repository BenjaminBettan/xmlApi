package com.bbe.xmlapi.core;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.ComparisonControllers;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;

import com.bbe.xmlapi.utilxml.persist.XmlPersist;
import com.bbe.xmlapi.utilxml.restitution.JsonTransformer;
import com.bbe.xmlapi.utilxml.restitution.XmlFormatterIndent;

/**
 * Entity to persist
 * @author benjamin
 */

public class Entity implements Serializable{

	private static final long serialVersionUID = Long.MAX_VALUE - 999;
	private static final String STR_ATTRIBUTES_TO_FIND = "[\\[]";
	
	protected long id;
	protected String tag;
	protected String data;
	private List<Long> childsId;
	protected long isChildOf;//-1 -> root node
	transient Map<String, String> attributes;
	protected int level;

	protected Entity() {}

	public Entity addChild(String currentTag) {

		Entity e = new XMLEntity(currentTag, level+1);

		this.setIsFatherOf(e.getId());
		e.setIsChildOf(this.getId());
		if (EntityControler.isPersistMode()) {
			XmlPersist.persist(this);
			XmlPersist.persist(e);
		}
		return e;
	}
	
	public Entity addChild(Entity e) {
		this.setIsFatherOf(e.getId());
		e.setIsChildOf(this.getId());
		e.level = this.level+1;
		if (EntityControler.isPersistMode()) {
			XmlPersist.persist(this);
			XmlPersist.persist(e);
		}
		return e;
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
		if (EntityControler.isPersistMode()) {
			XmlPersist.persist(this);
		}
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
		if (EntityControler.isPersistMode()) {
			XmlPersist.persist(this);
		}
	}

	public boolean isLeaf() {
		return getIsFatherOf() == null || getIsFatherOf().isEmpty();
	}

	public Entity getParent() {
		if (isChildOf>=0) {
			return this.getEntityById((isChildOf));	
		}
		else {
			return null;
		}
	}

	public void setLevel(int level2) {
		level = level2;
		if (EntityControler.isPersistMode()) {
			XmlPersist.persist(this);
		}
	}
	
	protected void setIsChildOf(long isChildOf) {
		this.isChildOf = isChildOf;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
		if (EntityControler.isPersistMode()) {
			XmlPersist.persist(this);
		}
	}

	public Map<Long, Entity> getChilds() {
		Map<Long, Entity> mapEntities = new HashMap<>();

		if ( getIsFatherOf()!=null) {
			for (Long l : getIsFatherOf()) {
				mapEntities.put(l, EntityControler.getEntity(l));
			}	
		}

		return mapEntities;
	}
	
	public List<Long> getIsFatherOf() {
		return childsId;
	}
	
	protected void setIsFatherOf(long id2) {
		if (getIsFatherOf()==null) 
		{
			childsId = new ArrayList<>();
		}
		this.getIsFatherOf().add(id2);
		if (EntityControler.isPersistMode()) {
			XmlPersist.persist(this);
		}
	}

	/**
	 * Note that when you use addChild method, level field is not updated to sons
	 * @return 0 for root, 1 for his son...
	 */
	public int getLevel() {
		return level;
	}

	public boolean isRootNode() {
		return isChildOf==-1;
	}

	public boolean isVirtualEntity() {
	    return this instanceof VirtualXMLEntity;
	}

	public Iterator<Difference> getDiff(Entity e) {
	    Diff myDiff = DiffBuilder
	  	      .compare(this.show())
	  	      .withTest(e.show())
	  	       .build();
		return myDiff.getDifferences().iterator();
	}

	public boolean isDiff(Entity e) {
	    Diff myDiff = DiffBuilder
		  	      .compare(this.show())
		  	      .withTest(e.show())
		  	      .withComparisonController(ComparisonControllers.StopWhenDifferent)
		  	       .build();
		return myDiff.getDifferences().iterator().hasNext();
	}

	public Entity getEntityById(long l) {
		return EntityControler.getEntity(l);
	}

	public Map<Long, Entity> getEntitiesByXpath(String xpath_) {

		Map<Long, Entity> mapEntities = new HashMap<>();

		xpath_ = xpath_.substring(1, xpath_.length() - 1);
		String[] x = xpath_.split("/");
		String[] xp = new String[x.length - 1];

		for (int i = 1; i < x.length; i++) {
			xp[i-1] = x[i];
		}

		Entity x_ = this;

		if ( ! x[0].contains("[")) {//no attribute has to be found
			if (x_.getTag().equals(x[0])) {
				findNextEntity(mapEntities,xp,x_,0);

			}
			else {
				return mapEntities;	
			}			
		}
		else {// an attribute has to be found

			String strToParse = x[0].split(STR_ATTRIBUTES_TO_FIND)[1];
			strToParse = strToParse.replace("]", "").replace("\"", "");
			String tagToFind = x[0].split(STR_ATTRIBUTES_TO_FIND)[0];

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
		if (tagToFind.equals("..")) {
			findNextEntity(mapEntities,xp,x_.getParent(),i+1);	
		}else if ( tagToFind.contains("[")) {//attributes have to be found
			String strToParse = xp[i].split(STR_ATTRIBUTES_TO_FIND)[1];
			strToParse = strToParse.replace("]", "").replace("\"", "");
			tagToFind = xp[i].split(STR_ATTRIBUTES_TO_FIND)[0];

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
						mapEntities.put(xmlEntity.getValue().getId(), xmlEntity.getValue());
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
						mapEntities.put(xmlEntity.getValue().getId(), xmlEntity.getValue());
					}
					else {
						findNextEntity(mapEntities,xp,xmlEntity.getValue(),i+1);					
					}
				}
			}
		}
	}

	public String showJson() throws IOException {
		return JsonTransformer.xmlToJson(this.showXml());
	}

	public String showXml() throws IOException {
		if (isVirtualEntity()) {
			return showXml("1.0","UTF-8",null);
		}
		else {
			return XmlFormatterIndent.format(showXml("1.0","UTF-8",null));
		}

	}

	public String showXml(String version, String encoding, String grammaire) {

		return "<?xml version=\""+version+"\" encoding=\""+encoding+"\""+ ((grammaire==null) ? "" : " "+grammaire)  +" ?>"+show();
	}

	public String show() {
		String header,footer;

		if ("".equals(data) && this.isLeaf()) {

			footer="";
			if (attributes==null || attributes.size()==0) 
			{
				header="<"+tag+"/>";
			}
			else 
			{
				header="<"+tag+" "+attributes.toString().substring(1, attributes.toString().length()-1).replace("=", "=\"").replace(",", "\"") + "\"/>";
			}

		}
		else {

			footer="</"+tag+">";
			if (attributes==null || attributes.size()==0) 
			{
				header="<"+tag+">";
			}
			else 
			{
				header="<"+tag+" "+attributes.toString().substring(1, attributes.toString().length()-1).replace("=", "=\"").replace(",", "\"") + "\">";
			}
		}

		return header + data + getSonTags() +footer;
	}

	protected String getSonTags() {
		String s = "";

		if (getIsFatherOf()!=null) 
		{
			for (Long l : getIsFatherOf()) 
			{
				s+=EntityControler.getEntity(l).show();
			}	
		}

		return s;
	}
	
	@Override
	public String toString() {
		return "Entity [id=" + id + ", level=" + level + ", tag=" + tag + ", data=" + data +  ", leaf="
				+ isLeaf() + ", isChildOf=" + isChildOf + ", attributes=" + attributes 
				+ ", isFatherOf=" + getIsFatherOf() +"]";
	}

}
