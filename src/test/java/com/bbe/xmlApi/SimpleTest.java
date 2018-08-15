package com.bbe.xmlApi;

import java.util.HashMap;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import com.bbe.xmlApi.core.Entity;
import com.bbe.xmlApi.core.EntityControler;
import com.bbe.xmlApi.core.VirtualXMLEntity;
import com.bbe.xmlApi.core.XMLEntity;

public class SimpleTest {

	@Rule
	public TestWatcher watchman= new TestWatcher() {
		@Override
		protected void failed(Throwable e, Description description) {

			printAndPrepareNextTest("Fin methode" + description.getMethodName());
		}

		private void printAndPrepareNextTest(String message) {

			for (Map.Entry<Long, Entity> xmlEntity : EntityControler.getMapEntities().entrySet()) {
				System.out.println(xmlEntity.getValue());
			}
			if (root !=null) {
				System.out.println("\n\n"+root.showXml()+"\n\n");
				EntityControler.clean();	
			}
			
			System.out.println(message+"\n\n------------------------------\n");

		}

		@Override
		protected void succeeded(Description description) {
			printAndPrepareNextTest("Fin methode "+description.getMethodName());
		}
	};

	private Entity root;

	@Test
	public void a() {
		root = a_();
	}

	public Entity a_() {
		Entity root = new VirtualXMLEntity("a");
		root.addChild("b");
		root.addChild("b");
		root.addChild("b");
		return root;
	}

	@Test
	public void b() {
		root = b_();
	}

	public Entity b_() {
		Entity root = new XMLEntity("x1");
		Map<String, String> attributes = new HashMap<>();
		attributes.put("aKey", "aValue");

		Entity x2 = root.addChild("x2");
		root.addChild("x2");
		x2.addChild("x3").setAttributes(attributes);
		return root;
	}
	
	@Test
	public void sc_1() {
		root = sc_1_();
	}
	
	public Entity sc_1_() {
		Entity y1, x2;
		Entity root = new XMLEntity("x1");
		x2 = root.addChild("x2");
		y1 = new XMLEntity("y1");
		y1.addChild("y2");
		
		x2.addChild(y1);
		return root;
	}

	@Test
	public void sc_2() {
		root = sc_2_();
	}
	
	public Entity sc_2_() {
		root = new XMLEntity("x1");
		root.addChild("x2");
		root.addChild("x2");
		return root;
	}
	
	@Test
	public void sc_3() {
		root = sc_3_();
	}

	public Entity sc_3_() {
		Entity x2;
		Map<String, String> attributes = new HashMap<>();
		attributes.put("aKey", "aValue");
		root= new XMLEntity("x1");
		
		x2 = root.addChild("x2");
		root.addChild("x2");
		x2.addChild("x3").setAttributes(attributes);
		return root;
	}

	
	@Test
	public void testSax() {
		root = EntityControler.getInstance().parseWithSax("target/surefire-reports/TEST-com.bbe.xmlApi.SimpleTest.xml");
	}
	
	@Test
	public void testSax2() {
		root = EntityControler.getInstance().parseWithSax("pom.xml");
	}

}