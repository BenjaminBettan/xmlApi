package com.bbe.xmlApi;


import static org.junit.Assert.assertTrue;

import java.util.Map;

import com.bbe.xmlApi.core.XMLEntity;
import com.bbe.xmlApi.core.AbstractXMLEntity;

import cucumber.api.java.en.*;

public class StepDefinitions {
	
	private AbstractXMLEntity x;
	private String xpath,tagToFind;
	private Map<Long, XMLEntity> mapEntities;
	
	@Given("^je charge scenario (\\d+) et je cherche \"(.*?)\"$")
	public void je_charge_scenario_et_je_cherche(int scenario, String xpath_) throws Throwable {
		
		xpath = xpath_;
		
		xpath_ = xpath_.substring(1, xpath_.length() - 1);
		String[] tags = xpath_.split("/");
		tagToFind = tags[tags.length - 1];
		
		switch (scenario) {
		case 1:
			x = new XMLEntity("x");
			AbstractXMLEntity y = new XMLEntity("y");
			
			AbstractXMLEntity x2 = x.addChild("x2");
			AbstractXMLEntity y2 = y.addChild("y2");
			
			x2.addChild(y2);
			break;

		default:
			throw new RuntimeException();
		}
		
		mapEntities = x.getEntitiesByXpath(xpath);
		
	}
	
	@Then("^je dois trouver : (\\d+)$")
	public void je_dois_trouver(int nbEntities) throws Throwable {

		assertTrue("Number of entities found should be equal to : " + nbEntities,mapEntities.size() == nbEntities);
		
		for (Map.Entry<Long, XMLEntity> xmlEntity : mapEntities.entrySet()) {
			assertTrue(xmlEntity.getValue().getTag().equals(tagToFind));
		}
		
	}
}
