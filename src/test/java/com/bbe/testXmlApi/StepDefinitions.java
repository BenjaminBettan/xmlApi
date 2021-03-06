package com.bbe.testXmlApi;

import org.junit.Assert;

import com.bbe.xmlapi.core.Entity;
import com.bbe.xmlapi.core.EntityControler;

import cucumber.api.java.en.*;
import java.util.Map;

public class StepDefinitions {
	
	private String xpathToFind;
	private Map<Long, Entity> mapEntitiesFound;
	private SimpleTest simpleTest = new SimpleTest();
	
	@Given("^je charge \"(.*?)\"$")
	public void je_charge(String filePath) throws Throwable {
		
		EntityControler.clean();//
		
		EntityControler.parseFileWithSax(filePath);
	}
	
	@Given("^je charge scenario (\\d+)$")
	public void je_charge_scenario(int scenario) throws Throwable {
		
		EntityControler.clean();//
		
		switch (scenario) {
			case 1:
				simpleTest.testUnitaire1_();
				break;
			case 2:
				simpleTest.testUnitaire2_();
				break;
			case 3:
				simpleTest.testUnitaire3_();
				break;
			default:
				throw new RuntimeException();
		}
		
	}
	
	@When("^je cherche \"(.*?)\"$")
	public void je_cherche(String xpath_) throws Throwable {
		xpathToFind = xpath_;
		mapEntitiesFound = EntityControler.getRoot().getEntitiesByXpath(xpath_);
		
	}
	
	@Then("^je dois trouver : (\\d+) entite$")
	public void je_dois_trouver_entite(int nbEntities) throws Throwable {

		Assert.assertTrue(mapEntitiesFound.size() + " entitie(s) found"
				+ "/ Expected : " + nbEntities ,
				
				mapEntitiesFound.size() == nbEntities);
		
		//seconde partie du test on verifie que tous les noeuds ont le meme nom
		xpathToFind = xpathToFind.substring(1, xpathToFind.length() - 1);
		
		String[] tags = xpathToFind.split("/");
		String tagToFind = tags[tags.length - 1];
		
		if ( tagToFind.contains("[")) {//des attributs doivent etre trouves
			String strToParse = tagToFind.split("[\\[]")[1];
			strToParse = strToParse.replace("]", "").replace("\"", "");
			tagToFind = tagToFind.split("[\\[]")[0];
		}
		
		for (Map.Entry<Long, Entity> xmlEntity : mapEntitiesFound.entrySet()) {
			Assert.assertTrue("At least one tag is wrong : "+xmlEntity.getValue().getTag()
					+"/ Expected : "+tagToFind,
					
					xmlEntity.getValue().getTag().equals(tagToFind));
		}
		
	}
}
