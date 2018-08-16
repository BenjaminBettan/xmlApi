package com.bbe.xmlApi.core;

import java.util.Map;

public interface Entity {
	
	public String showJson();
	public String showXml();
	public String showXml(String version, String encoding, String grammaire);
	public String showXmlValue();
	public Entity addChild(String currentTag);
	public Entity addChild(Entity x_);
	public boolean thisNodeHasNoAttribute();
	public long getId();
	public String getTag();
	public void setTag(String tag);
	public String getData();
	public void setData(String data);
	public boolean isLeaf();
	public Entity getParent();
	public Map<String, String> getAttributes();
	public void setAttributes(Map<String, String> attributes);
	public Map<Long, Entity> getChilds();
	public Map<Long, Entity> getEntitiesByXpath(String xpath_);
	public Entity getEntityById(long l);
	public boolean isVirtualEntity();
	public boolean isRootNode();
	public void setLevel(int level);
	
}
