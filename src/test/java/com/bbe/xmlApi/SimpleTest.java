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
import com.bbe.xmlApi.util.JsonTransformer;

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
	
//tests start here
	/**
	 * load pom.xml then transform and print json equivalent
	 */
	@Test
	public void testSax() {
		EntityControler.getInstance().parseWithSax("pom.xml");
		System.out.println(JsonTransformer.xmlToJson(EntityControler.getRoot().showXmlValue()));
		
	}
	
	@Test
	public void testPersistLoad() {
		// persist HashMap to file then load and print
		String filePath = "hashmap.ser";
		sc_1_();
		XmlPersist.persist(filePath);
		HashMap<Long, Entity_I> e = XmlLoad.serializeObjectToEntity(filePath);
		System.out.println("e.values()");
		System.out.println(e.values());
		
	}
	
//	@Test
//	public void testPersistPerf() {
//		
//		long start = System.currentTimeMillis();
//		Entity_I root = new XMLEntity("root");
//		while (true) {
//			if (System.currentTimeMillis() - start > 100) {
//				break;
//			} else {
//				root.addChild("a");
//			}
//		}
//		
//		System.out.println("Size : "+EntityControler.getMapEntities().size());
//		
//		// persist HashMap to file then load and print
//		String filePath = "hashmap.ser";
//		XmlPersist.persist(filePath);
//		HashMap<Long, Entity_I> e = XmlLoad.serializeObjectToEntity(filePath);
//		System.out.println("e.values()");
//		System.out.println(e.values());
//		
//	}
	
	
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