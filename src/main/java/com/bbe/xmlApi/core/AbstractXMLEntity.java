package com.bbe.xmlApi.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractXMLEntity {
	
	protected long id = 0L;
	protected String tag;
	protected String data = new String("");
	protected List<Long> isFatherOf;
	protected long isChildOf = -1;
	protected Map<String, String> attributes = null;
	protected int level = 0;//TODO bug. le level ne se repand pas quand on utilise la methode addchild
	protected boolean isVirtualXMLEntity;
	
	public abstract Object getThis();

	public abstract String showXml();
	public abstract String showXml(String version, String encoding, String grammaire);
	public abstract String showXmlValue();
	
	public XMLEntity addChild(String currentTag) {

		XMLEntity x_ = new XMLEntity(currentTag, level+1);
		
		XMLEntity this_ = (XMLEntity) getThis();
		
		this_.setIsFatherOf(x_.getId());
		x_.setIsChildOf(this.getId());
		x_.level = this.level+1;
		XMLEntityControler.getMapEntities().put(x_.getId(), x_);

		return x_;
	}
	
	public XMLEntity addChild(AbstractXMLEntity x_) {
		return addChild((XMLEntity) x_);
	}

	public XMLEntity addChild(XMLEntity x_) {
		this.setIsFatherOf(x_.getId());
		x_.setIsChildOf(this.getId());
		x_.level = this.level+1;
		XMLEntityControler.getMapEntities().put(x_.getId(), x_);
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

	public AbstractXMLEntity getParent() {
		return XMLEntityControler.getInstance().getEntity(isChildOf);
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

	public Map<Long, AbstractXMLEntity> getChilds() {
		Map<Long, AbstractXMLEntity> mapEntities = new HashMap<Long, AbstractXMLEntity>();

		if ( isFatherOf!=null) {
			for (Long l : isFatherOf) {
				mapEntities.put(l, XMLEntityControler.getInstance().getEntity(l));
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
	
	public Map<Long, AbstractXMLEntity> getEntitiesByXpath(String xpath_) {
		
		Map<Long, AbstractXMLEntity> mapEntities = new HashMap<Long, AbstractXMLEntity>();
		
		xpath_ = xpath_.substring(1, xpath_.length() - 1);
		String[] x = xpath_.split("/");
		String[] xp = new String[x.length - 1];
		
		for (int i = 1; i < x.length; i++) {
			xp[i-1] = x[i];
		}
		
		XMLEntity x_ = (XMLEntity) getThis();

		if (x_.getTag().equals(x[0])) {
			findNextEntity(mapEntities,xp,x_,0);

		}
		else {
			return mapEntities;	
		}
		
		return mapEntities;
	}
	
	private void findNextEntity(Map<Long, AbstractXMLEntity> mapEntities, String[] xp, AbstractXMLEntity x_, int i) {
		
		String tagToFind = xp[i];
		
		for (Map.Entry<Long, AbstractXMLEntity> xmlEntity : x_.getChilds().entrySet()) {
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
	
	protected String getSonTags() {

		String s = new String("");

		if (isFatherOf!=null) 
		{
			for (Long l : isFatherOf) 
			{
				s += XMLEntityControler.getInstance().getEntity(l).showXmlValue();
			}	
		}

		return s;
	}

	public AbstractXMLEntity getEntityById(long l) {
		return XMLEntityControler.getInstance().getEntity(l);
	}
	
}
