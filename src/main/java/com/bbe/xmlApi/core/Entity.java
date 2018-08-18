package com.bbe.xmlApi.core;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.xmlunit.diff.Difference;

public interface Entity {
	public Iterator<Difference> getDiff(Entity e);
	public boolean isDiff(Entity e);
	public String showJson() throws IOException;
	public String showXml() throws IOException;
	public String showXml(String version, String encoding, String grammaire);
	public String show();
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
