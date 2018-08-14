package com.bbe.xmlApi;

import static org.junit.Assert.assertTrue;
import java.util.Map;
import com.bbe.xmlApi.core.Entity;

import cucumber.api.java.en.*;

public class StepDefinitions {
	
	private Entity root;
	private String xp;
	private Map<Long, Entity> mapEntities;
	private SimpleTest st = new SimpleTest();
	
	@Given("^je charge scenario (\\d+)$")
	public void je_charge_scenario(int scenario) throws Throwable {
		switch (scenario) {
		case 1:
			root = st.sc_1_();
			break;
		case 2:
			root = st.sc_2_();
			break;
		case 3:
			root = st.sc_3_();
			break;
		default:
			throw new RuntimeException();
		}
	}
	
	@When("^je cherche \"(.*?)\"$")
	public void je_cherche(String xpath_) throws Throwable {
		xp = xpath_;
		mapEntities = root.getEntitiesByXpath(xpath_);
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
