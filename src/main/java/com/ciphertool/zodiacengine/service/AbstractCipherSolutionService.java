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

package com.ciphertool.zodiacengine.service;

import org.apache.log4j.Logger;

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.zodiacengine.view.GenericCallback;

public abstract class AbstractCipherSolutionService implements CipherSolutionService {
	private Logger log = Logger.getLogger(getClass());
	private boolean running = false;

	@Override
	public void begin(GeneticAlgorithmStrategy geneticAlgorithmStrategy,
			GenericCallback uiCallback, boolean debugMode) {
		toggleRunning();
		setUp(geneticAlgorithmStrategy);

		start(uiCallback, debugMode);
	}

	protected void end() {
		try {
			stop();
		} catch (Throwable t) {
			log.error("Caught Throwable while attempting to stop service.  "
					+ "Performing tear-down tasks.", t);
		} finally {
			tearDown();
		}

		toggleRunning();
	}

	@Override
	public void resume() {
		proceed();
	}

	@Override
	public abstract void endImmediately(boolean inDebugMode);

	@Override
	public synchronized boolean isRunning() {
		return running;
	}

	private synchronized void toggleRunning() {
		running = !running;
	}

	protected abstract void proceed();

	protected abstract void start(GenericCallback uiCallback, boolean debugMode);

	protected abstract void stop();

	protected abstract void setUp(GeneticAlgorithmStrategy geneticAlgorithmStrategy);

	protected abstract void tearDown();
}
