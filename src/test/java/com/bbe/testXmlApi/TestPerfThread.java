package com.bbe.testXmlApi;

import org.apache.log4j.Logger;

import com.bbe.xmlapi.core.XMLEntity;

public class TestPerfThread implements Runnable {
	private static final Logger logger = Logger.getLogger(TestPerfThread.class);
	private long start;
	private long deltaT;
	private long l = 0;


	public TestPerfThread setStart(long start) {
		this.start = start;
		return this;
	}


	public TestPerfThread setDeltaT(long deltaT) {
		this.deltaT = deltaT;
		return this;
	}


	@Override
	public void run() {
		l = 0;
		XMLEntity root = new XMLEntity("root");
		while (true) {
			if (System.currentTimeMillis() - start > deltaT) {
				logger.info(getL() + " entities persisted for this thread");
				break;
			} else {
				root.addChild("a");
				l++;
			}
		}
	}


	public long getL() {
		return l;
	}

}
