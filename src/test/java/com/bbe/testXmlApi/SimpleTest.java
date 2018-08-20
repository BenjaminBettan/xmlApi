package com.bbe.testXmlApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.xmlunit.diff.Difference;

import com.bbe.xmlapi.core.Entity;
import com.bbe.xmlapi.core.EntityControler;
import com.bbe.xmlapi.core.VirtualXMLEntity;
import com.bbe.xmlapi.core.XMLEntity;
import com.bbe.xmlapi.util.other.WebConnectionGetter;
import com.bbe.xmlapi.util.persist.SerializeToEntity;

public class SimpleTest {
	private static final Logger logger = Logger.getLogger(SimpleTest.class);
	private boolean show = true;
	private ExecutorService executor;
	private TestPerfThread[] arrayRefVar;
	{
		PropertyConfigurator.configure("log4j.properties");
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
	 * @throws IOException 
	 */
	@Test
	public void testSaxToJson() throws IOException {
		EntityControler.getInstance().parseFileWithSax("pom.xml");
		logger.info(EntityControler.getRoot().showJson());
		
	}
	
	@Test
	public void testPersistLoad() throws ClassNotFoundException, IOException {
		EntityControler.setToHardDriveAndClean(true);
		
		Entity root = EntityControler.getInstance().parseFileWithSax("pom.xml");
		EntityControler.clean();
		root = SerializeToEntity.get(root.getId());
		logger.info(root.showJson());
	}
	
	///*	
	@Test
	public void testUrl() {
		
		Entity root = WebConnectionGetter.get("https://www.w3schools.com/xml/note.xml");
		
		logger.info(root);
	}
	//*/
	
	@Test
	public void testXmlUnitApi(){
		int size;
		
		Entity a = b_();
		Entity b = sc_1_();
	    Assert.assertTrue("devraient etre different..." , a.isDiff(b));

		Iterator<Difference> iter = a.getDiff(b);
		
	    for (size = 0; iter.hasNext(); size++) {
	        System.out.println(iter.next().toString());

		}
	    
	    Assert.assertTrue("size = 7 ? -> "+size,size==7);
	}
	
	@Test
	public void testPersistPerf() {
		long deltaT = 10;//in ms
		int poolSize = 8;//nb thread
		logger.info("deltaT is : "+deltaT+"ms / nb thread is " + poolSize);
		arrayRefVar = new TestPerfThread[poolSize];
        for (int i = 0; i < arrayRefVar.length; i++) {
        	arrayRefVar[i] = new TestPerfThread();
		}
		
        executeTestPerf(deltaT);
        
		logger.info("Size (memory) : "+EntityControler.getMapEntities().size());
		
		//test #2 to hard drive
		
		EntityControler.setToHardDriveAndClean(true);
		
        executeTestPerf(deltaT);

        long l = 0;
        for (TestPerfThread testPerfThread : arrayRefVar) {
			l += testPerfThread.getNbInstance();
		}
		logger.info("Size (hard drive) : " + l);

		show = false;
		
	}
	
	private void executeTestPerf(long deltaT) {
		executor = Executors.newFixedThreadPool(arrayRefVar.length);

		long start = System.currentTimeMillis();

        for (int i = 0; i < arrayRefVar.length; i++) {
            executor.execute(arrayRefVar[i].setDeltaT(deltaT).setStart(start));
          }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
		
	}

	public Entity a_() {
		Entity root = new VirtualXMLEntity();
		root.addChild("b");
		root.addChild("b");
		root.addChild("b");
		return root;
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
	
	public Entity sc_1_() {
		Entity y1, x2,root;
		
		root = new XMLEntity("x1");
		x2 = root.addChild("x2");
		y1 = new XMLEntity("y1");
		y1.addChild("y2");
		
		x2.addChild(y1);
		return root;
	}
	
	public Entity sc_2_() {
		Entity root = new XMLEntity("x1");
		root.addChild("x2");
		root.addChild("x2");
		return root;
	}

	public Entity sc_3_() {
		Entity root,x2;
		Map<String, String> attributes = new HashMap<>();
		attributes.put("aKey", "aValue");
		root= new XMLEntity("x1");
		
		x2 = root.addChild("x2");
		root.addChild("x2");
		x2.addChild("x3").setAttributes(attributes);
		return root;
	}
}