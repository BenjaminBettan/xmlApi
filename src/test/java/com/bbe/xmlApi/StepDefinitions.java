package com.bbe.xmlApi;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import com.bbe.xmlApi.core.Entity;
import com.bbe.xmlApi.core.XMLEntity;

import cucumber.api.java.en.*;

public class StepDefinitions {
	
	private Entity x;
	private String xp;
	private Map<Long, Entity> mapEntities;
	
	@Given("^je charge scenario (\\d+)$")
	public void je_charge_scenario(int scenario) throws Throwable {
		
		Entity y1,x2;
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
		case 3:
			Map<String, String> attributes = new HashMap<>();
			attributes.put("aKey", "aValue");
			x= new XMLEntity("x1");
			
			x2 = x.addChild("x2");
			x.addChild("x2");
			x2.addChild("x3").setAttributes(attributes);
			break;

		default:
			throw new RuntimeException();
		}
	}
	
	@When("^je cherche \"(.*?)\"$")
	public void je_cherche(String xpath_) throws Throwable {
		xp = xpath_;
		mapEntities = x.getEntitiesByXpath(xpath_);
	}
	
	@Then("^je dois trouver : (\\d+) entite$")
	public void je_dois_trouver_entite(int nbEntities) throws Throwable {

		assertTrue(mapEntities.size() + " entitie(s) found"
				+ "/ Expected : " + nbEntities ,
				
				mapEntities.size() == nbEntities);
		
		//seconde partie du test on verifie que tous les noeuds ont le meme nom
		xp = xp.substring(1, xp.length() - 1);
		
		String[] tags = xp.split("/");
		String tagToFind = tags[tags.length - 1];
		
		if ( tagToFind.contains("[")) {//des attributs doivent etre trouves
			String strToParse = tagToFind.split("[\\[]")[1];
			strToParse = strToParse.replace("]", "").replace("\"", "");
			tagToFind = tagToFind.split("[\\[]")[0];
		}
		
		for (Map.Entry<Long, Entity> xmlEntity : mapEntities.entrySet()) {
			assertTrue("At least one tag is wrong : "+xmlEntity.getValue().getTag()
					+"/ Expected : "+tagToFind,
					
					xmlEntity.getValue().getTag().equals(tagToFind));
		}
	}
}
