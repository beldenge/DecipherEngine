/**
 * Copyright 2012 George Belden
 * 
 * This file is part of ZodiacEngine.
 * 
 * ZodiacEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ZodiacEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ZodiacEngine. If not, see <http://www.gnu.org/licenses/>.
 */

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
