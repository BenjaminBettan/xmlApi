package com.bbe.testXmlApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.bbe.xmlapi.core.Entity;
import com.bbe.xmlapi.core.EntityControler;
import com.bbe.xmlapi.core.XMLEntity;

public class AbstractTests {
	private static final Logger logger = Logger.getLogger(AbstractTests.class);
	protected boolean show = true;
	protected ExecutorService executor;
	protected TestPerfThread[] arrayRefVar;

	public Entity testUnitaire1_() {
		Entity root = new XMLEntity("root");
		root.setIsVirtualXMLEntity(true);
		root.addChild("b");
		root.addChild("b");
		root.addChild("b");
		return root;
	}

	public Entity testUnitaire2_() {
		Entity root = new XMLEntity("root");
		Map<String, String> attributes = new HashMap<>();
		attributes.put("aKey", "aValue");

		Entity root_a = root.addChild("a");
		root.addChild("b");
		root_a.addChild("c").setAttributes(attributes);
		return root;
	}
	
	public Entity testUnitaire3_() {
		Entity root_a_b, root_a,root;
		
		root = new XMLEntity("root");
		root_a = root.addChild("a");
		
		root_a_b = new XMLEntity("b");
		root_a_b.addChild("c");
		
		root_a.addChild(root_a_b);
		
		//root/a/b/c
		return root;
	}
	
	public Entity testUnitaire4_() {
		Entity root = new XMLEntity("x1");
		root.addChild("x2");
		root.addChild("x2");
		return root;
	}

	public Entity testUnitaire5_() {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("aKey", "aValue");
		
		Entity root= new XMLEntity("x1");
		
		root.addChild("x2");
		root.addChild("x2").addChild("x3").setAttributes(attributes);
		return root;
	}
	
	@Rule
	public TestWatcher watchman= new TestWatcher() {
		@Override
		protected void failed(Throwable e, Description description) {

			try {
				printAndPrepareNextTest("Fin methode" + description.getMethodName());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		private void printAndPrepareNextTest(String message) throws IOException {
			if (show) {
				for (Map.Entry<Long, Entity> xmlEntity : EntityControler.getMapEntities().entrySet()) {
					logger.info(xmlEntity.getValue());
				}
				if (EntityControler.getRoot() !=null) {
					logger.info("\n\n"+EntityControler.getRoot().showXml()+"\n\n");
				}
			}
			show = true;
			
			EntityControler.setToHardDriveAndClean(false);
			logger.info(message+"\n\n------------------------------\n");

		}

		@Override
		protected void succeeded(Description description) {
			try {
				printAndPrepareNextTest("Fin methode "+description.getMethodName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

}
