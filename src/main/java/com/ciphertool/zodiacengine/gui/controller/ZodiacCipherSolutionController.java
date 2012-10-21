package com.ciphertool.zodiacengine.gui.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.zodiacengine.gui.service.CipherSolutionService;

public class ZodiacCipherSolutionController implements CipherSolutionController {
	private Logger log = Logger.getLogger(getClass());
	private CipherSolutionService cipherSolutionService;

	@Override
	public void startServiceThread() {
		if (cipherSolutionService.isRunning()) {
			log.info("Cipher solution service is already running.  Cannot start until current process completes.");
		} else {
			Thread serviceThread = new Thread(new Runnable() {
				public void run() {
					cipherSolutionService.begin();
				}
			});

			serviceThread.start();
		}
	}

	@Override
	public void stopServiceThread() {
		if (cipherSolutionService.isRunning()) {
			Thread serviceThread = new Thread(new Runnable() {
				public void run() {
					cipherSolutionService.endImmediately();
				}
			});

			serviceThread.start();
		} else {
			log.info("Cipher solution service is already stopped.  Nothing to do.");
		}
	}

	/**
	 * @param cipherSolutionService
	 *            the cipherSolutionService to set
	 */
	@Required
	public void setCipherSolutionService(CipherSolutionService cipherSolutionService) {
		this.cipherSolutionService = cipherSolutionService;
	}
}
