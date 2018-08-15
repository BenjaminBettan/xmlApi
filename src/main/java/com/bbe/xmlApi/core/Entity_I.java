package com.bbe.xmlApi.core;

import java.util.Map;

public interface Entity_I {
	
	public String showXml();
	public String showXml(String version, String encoding, String grammaire);
	public String showXmlValue();
	public Entity_I addChild(String currentTag);
	public Entity_I addChild(Entity_I x_);
	public boolean thisNodeHasNoAttribute();
	public long getId();
	public String getTag();
	public void setTag(String tag);
	public String getData();
	public void setData(String data);
	public boolean isLeaf();
	public Entity_I getParent();
	public Map<String, String> getAttributes();
	public void setAttributes(Map<String, String> attributes);
	public Map<Long, Entity_I> getChilds();
	public Map<Long, Entity_I> getEntitiesByXpath(String xpath_);
	public Entity_I getEntityById(long l);
	public boolean isVirtualEntity();
	public void setLevel(int level);
	
}
