package com.bbe.xmlApi.core;

public abstract class AbstractEntity {
	public abstract Entity getThis();

	public abstract String showXml();
	public abstract String showXml(String version, String encoding, String grammaire);
	public abstract String showXmlValue();
}
