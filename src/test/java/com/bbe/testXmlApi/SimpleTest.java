package com.bbe.testXmlApi;

import java.util.Iterator;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.xmlunit.diff.Difference;
import com.bbe.xmlapi.core.Entity;
import com.bbe.xmlapi.core.EntityControler;
import com.bbe.xmlapi.core.XMLEntity;
import com.bbe.xmlapi.util.other.WebConnectionGetter;
import com.bbe.xmlapi.util.persist.PersistConfigurator;
import com.bbe.xmlapi.util.persist.SerializeToEntity;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleTest extends AbstractTests{
	private static final Logger logger = Logger.getLogger(SimpleTest.class);
	static {PropertyConfigurator.configure("log4j.properties");}
	
	@Test
	public void testUnitaires() {
		testUnitaire1_();
		testUnitaire2_();
		testUnitaire3_();
		testUnitaire4_();
		testUnitaire5_();
	}
	
	//tests start here
	/**
	 * load pom.xml then transform and print json equivalent
	 * @throws Exception 
	 */
	@Test
	public void testSaxToJson() throws Exception {
		EntityControler.parseFileWithSax("pom.xml");
		logger.info(EntityControler.getRoot().showJson());
		
	}
	
	@Test
	public void testPersistLoad() throws Exception {
		EntityControler.setToHardDriveAndClean(true);
		
		Entity root = EntityControler.parseFileWithSax("pom.xml");
		EntityControler.clean();
		root = SerializeToEntity.get(root.getId());
		logger.info(root.showJson());
	}
	
	///*	
	@Test
	public void testUrl() throws Exception {
		
		Entity root = WebConnectionGetter.get("https://www.w3schools.com/xml/note.xml");
		
		logger.info(root);
	}
	//*/
	
	@Test
	public void testValidate() throws Exception {
		
		Entity root = EntityControler.parseFileWithSax("xmlSamples"+PersistConfigurator.getPrefix()+"xsd.xml");
		String[] xsd = {"test"};
		boolean b = root.validateWithXsd(xsd);
		Assert.assertTrue("Incorrect basic validation",b);
		logger.info(b);
//		logger.info(root);
	}
	
	@Test
	public void testValidatewrong() throws Exception {
		
		Entity root = EntityControler.parseFileWithSax("xmlSamples"+PersistConfigurator.getPrefix()+"xsd2.xml");
		String[] xsd = {"shiporder"};
		boolean b = root.validateWithXsd(xsd);
		Assert.assertTrue("Incorrect basic validation",b==false);
		logger.info(b);
//		logger.info(root);
	}
	
	@Test
	public void testJsonToXml() throws Exception {
		
        String str = "{\"name\":\"JSON\",\"integer\":1,\"double\":2.0,\"boolean\":true,\"nested\":{\"id\":42},\"array\":[1,2,3]}";  

		
		Entity root = EntityControler.parseJson(str);
		
		logger.info(root);
		
		root = EntityControler.parseJsonFile("xmlSamples"+PersistConfigurator.getPrefix()+"jgiven.LoginTest.json");
		logger.info(root);
	}
	
	
	@Test
	public void testXmlUnitApi(){
		int size;
		
		Entity a = testUnitaire5_();
		Entity b = testUnitaire1_();
	    Assert.assertTrue("devraient etre different..." , a.isDiff(b));

		Iterator<Difference> iter = a.getDiff(b);
		
	    for (size = 0; iter.hasNext(); size++) {
	    	logger.info(iter.next().toString());
		}
	    
	    Assert.assertTrue("size = 7 ? -> "+size,size==7);
	}
	
	@Test
	public void testPersistPerf() {
		long deltaT = 10;//in ms
		int poolSize = 2;//nb thread
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
        //print result
        long l = 0;
        for (TestPerfThread testPerfThread : arrayRefVar) {
			l += testPerfThread.getNbInstance();
		}
		logger.info("Size (hard drive) : " + l);

		show = false;
		
	}
	
	private void executeTestPerf(long deltaT) {
		executor = Executors.newFixedThreadPool(arrayRefVar.length);
		XMLEntity root = new XMLEntity("root");

		long start = System.currentTimeMillis();

        for (int i = 0; i < arrayRefVar.length; i++) {
            executor.execute(arrayRefVar[i].setDeltaT(deltaT).setStart(start).setRoot(root).setNbInstance(0));
          }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
		
	}
}