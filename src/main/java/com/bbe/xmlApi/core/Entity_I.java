package com.bbe.xmlApi.core;

public interface Entity_I {
	public Entity getThis();
	public String showXml();
	public String showXml(String version, String encoding, String grammaire);
	public String showXmlValue();
}
