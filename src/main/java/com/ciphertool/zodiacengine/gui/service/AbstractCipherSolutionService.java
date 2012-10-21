package com.ciphertool.zodiacengine.gui.service;

import org.apache.log4j.Logger;

public abstract class AbstractCipherSolutionService implements CipherSolutionService {
	private Logger log = Logger.getLogger(getClass());
	private boolean running = false;

	@Override
	public void begin() {
		toggleRunning();
		setUp();

		try {
			start();
		} catch (InterruptedException e) {
			log.warn("Caught InterruptedException while running cipher solution service.  "
					+ "Cannot continue.  Performing tear-down tasks.", e);
			end();
		}
	}

	@Override
	public void end() {
		stop();
		tearDown();
		toggleRunning();
	}

	@Override
	public synchronized boolean isRunning() {
		return running;
	}

	private synchronized void toggleRunning() {
		running = !running;
	}

	protected abstract void start() throws InterruptedException;

	protected abstract void stop();

	protected abstract void setUp();

	protected abstract void tearDown();
}
