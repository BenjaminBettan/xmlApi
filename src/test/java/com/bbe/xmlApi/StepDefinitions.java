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
	
	@Given("^je charge scenario (\\d+)$")
	public void je_charge_scenario(int scenario) throws Throwable {
		
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
	}
	@When("^je cherche \"(.*?)\"$")
	public void je_cherche(String xpath_) throws Throwable {
		xpath = xpath_;
		mapEntities = x.getEntitiesByXpath(xpath);
		
		xpath_ = new String(xpath_.substring(1, xpath_.length() - 1));
		
		String[] tags = xpath_.split("/");
		tagToFind = tags[tags.length - 1];
		
	}
	
	@Then("^je dois trouver : (\\d+) entite$")
	public void je_dois_trouver_entite(int nbEntities) throws Throwable {

		assertTrue(mapEntities.size() + " entitie(s) found"
				+ "/ Expected : " + nbEntities ,
				
				mapEntities.size() == nbEntities);
		
		for (Map.Entry<Long, AbstractXMLEntity> xmlEntity : mapEntities.entrySet()) {
			assertTrue("At least one tag is wrong : "+xmlEntity.getValue().getTag()
					+"/ Expected : "+tagToFind,
					
					xmlEntity.getValue().getTag().equals(tagToFind));
		}
		
	}
}
