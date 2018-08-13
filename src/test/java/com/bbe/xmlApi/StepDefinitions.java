package com.bbe.xmlApi;


import static org.junit.Assert.assertTrue;

import java.util.Map;

import com.bbe.xmlApi.core.XMLEntity;
import com.bbe.xmlApi.core.AbstractXMLEntity;

import cucumber.api.java.en.*;

public class StepDefinitions {
	
	private AbstractXMLEntity x;
	private String xpath,tagToFind;
	private Map<Long, AbstractXMLEntity> mapEntities;
	
	@Given("^je charge scenario (\\d+) et je cherche \"(.*?)\"$")
	public void je_charge_scenario_et_je_cherche(int scenario, String xpath_) throws Throwable {
		
		xpath = xpath_;
		
		String str = new String(xpath_);
		
		str = str.substring(1, str.length() - 1);
		String[] tags = str.split("/");
		tagToFind = tags[tags.length - 1];
		
		switch (scenario) {
		case 1:
			x = new XMLEntity("x1");
			XMLEntity y1 = new XMLEntity("y1");
			
			XMLEntity x2 = x.addChild("x2");
			y1.addChild("y2");
			
			x2.addChild(y1);
			break;

		default:
			throw new RuntimeException();
		}
		
		mapEntities = x.getEntitiesByXpath(xpath);
		
	}
	
	@Then("^je dois trouver : (\\d+)$")
	public void je_dois_trouver(int nbEntities) throws Throwable {

		assertTrue("Number of entities found should be equal to : " + nbEntities,mapEntities.size() == nbEntities);
		
		for (Map.Entry<Long, AbstractXMLEntity> xmlEntity : mapEntities.entrySet()) {
			assertTrue(xmlEntity.getValue().getTag().equals(tagToFind));
		}
		
	}
}
