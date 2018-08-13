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
		
		xpath_ = new String(xpath_.substring(1, xpath_.length() - 1));
		
		String[] tags = xpath_.split("/");
		tagToFind = tags[tags.length - 1];
		XMLEntity y1,x2;
		switch (scenario) {
		case 1:
			x = new XMLEntity("x1");
			y1 = new XMLEntity("y1");
			
			x2 = x.addChild("x2");
			y1.addChild("y2");
			
			x2.addChild(y1);
			break;
		case 2:
			x = new XMLEntity("x1");
			x.addChild("x2");
			x.addChild("x2");
			break;

		default:
			throw new RuntimeException();
		}
		
		mapEntities = x.getEntitiesByXpath(xpath);
		
	}
	
	@Then("^je dois trouver : (\\d+)$")
	public void je_dois_trouver(int nbEntities) throws Throwable {

		assertTrue("Expected number of entitie(s) : " + nbEntities + "/Found : "+mapEntities.size() + " entitie(s)",mapEntities.size() == nbEntities);
		
		for (Map.Entry<Long, AbstractXMLEntity> xmlEntity : mapEntities.entrySet()) {
			assertTrue(xmlEntity.getValue().getTag().equals(tagToFind));
		}
		
	}
}
