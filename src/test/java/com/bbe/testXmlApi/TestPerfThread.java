package com.bbe.testXmlApi;

import org.apache.log4j.Logger;

import com.bbe.xmlapi.core.XMLEntity;

public class TestPerfThread implements Runnable {
	private static final Logger logger = Logger.getLogger(TestPerfThread.class);
	private long start;
	private long deltaT;
	private long nbInstance = 0;
	private XMLEntity root;


	public TestPerfThread setNbInstance(long nbInstance) {
		this.nbInstance = nbInstance;
		return this;
	}


	public TestPerfThread setStart(long start) {
		this.start = start;
		return this;
	}


	public TestPerfThread setDeltaT(long deltaT) {
		this.deltaT = deltaT;
		return this;
	}


	public TestPerfThread setRoot(XMLEntity root) {
		this.root = root;
		return this;
	}


	@Override
	public void run() {
		while (true) {
			if (System.currentTimeMillis() - start > deltaT) {
				logger.info(nbInstance + " entities persisted for this thread");
				break;
			} else {
				root.addChild("a");
				nbInstance++;
			}
		}
	}


	public long getNbInstance() {
		return nbInstance;
	}

}
