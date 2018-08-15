package com.bbe.xmlApi;

import java.util.HashMap;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import com.bbe.xmlApi.core.EntityControler;
import com.bbe.xmlApi.core.Entity_I;
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

			for (Map.Entry<Long, Entity_I> xmlEntity : EntityControler.getMapEntities().entrySet()) {
				System.out.println(xmlEntity.getValue());
			}
			if (EntityControler.getRoot() !=null) {
				System.out.println("\n\n"+EntityControler.getRoot().showXml()+"\n\n");
				EntityControler.clean();	
			}
			
			System.out.println(message+"\n\n------------------------------\n");

		}

		@Override
		protected void succeeded(Description description) {
			printAndPrepareNextTest("Fin methode "+description.getMethodName());
		}
	};

	@Test
	public void a() {
		a_();
	}

	@Test
	public void b() {
		b_();
	}
	
	@Test
	public void sc_1() {
		sc_1_();
	}

	@Test
	public void sc_2() {
		sc_2_();
	}
	
	@Test
	public void sc_3() {
		sc_3_();
	}
	
//debut tests
	
	@Test
	public void testSax() {
		EntityControler.getInstance().parseWithSax("pom.xml");
		XmlPersist.persist("hashmap.ser");
	}
	
	public Entity_I a_() {
		Entity_I root = new VirtualXMLEntity("a");
		root.addChild("b");
		root.addChild("b");
		root.addChild("b");
		return root;
	}

	public Entity_I b_() {
		Entity_I root = new XMLEntity("x1");
		Map<String, String> attributes = new HashMap<>();
		attributes.put("aKey", "aValue");

		Entity_I x2 = root.addChild("x2");
		root.addChild("x2");
		x2.addChild("x3").setAttributes(attributes);
		return root;
	}
	
	public Entity_I sc_1_() {
		Entity_I y1, x2,root;
		
		root = new XMLEntity("x1");
		x2 = root.addChild("x2");
		y1 = new XMLEntity("y1");
		y1.addChild("y2");
		
		x2.addChild(y1);
		return root;
	}
	
	public Entity_I sc_2_() {
		Entity_I root = new XMLEntity("x1");
		root.addChild("x2");
		root.addChild("x2");
		return root;
	}

	public Entity_I sc_3_() {
		Entity_I root,x2;
		Map<String, String> attributes = new HashMap<>();
		attributes.put("aKey", "aValue");
		root= new XMLEntity("x1");
		
		x2 = root.addChild("x2");
		root.addChild("x2");
		x2.addChild("x3").setAttributes(attributes);
		return root;
	}

}