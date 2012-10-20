package com.ciphertool.zodiacengine;

import org.apache.log4j.Logger;

public abstract class AbstractCipherSolutionService {
	private Logger log = Logger.getLogger(getClass());

	protected boolean isRunning = false;

	public void begin() {
		if (isRunning) {
			log.info("Cipher solution service is already running.  Cannot start until current process completes.");
		} else {
			isRunning = true;
			setUp();

			try {
				start();
			} catch (InterruptedException e) {
				log.warn("Caught InterruptedException while running cipher solution service.  "
						+ "Cannot continue.  Performing tear-down tasks.", e);
				end();
			}
		}
	}

	public void end() {
		if (isRunning) {
			stop();
			tearDown();
			isRunning = false;
		} else {
			log.info("Cipher solution service is already stopped.  Nothing to do.");
		}
	}

	protected abstract void start() throws InterruptedException;

	protected abstract void stop();

	protected abstract void setUp();

	protected abstract void tearDown();
}
