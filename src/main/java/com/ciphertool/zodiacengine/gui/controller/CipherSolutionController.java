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

import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.zodiacengine.gui.view.GenericCallback;

public interface CipherSolutionController {
	public void startServiceThread(GeneticAlgorithmStrategy geneticAlgorithmStrategy,
			GenericCallback uiCallback, boolean debugMode);

	/**
	 * @param inDebugMode
	 *            whether we are currently in debug mode
	 */
	public void stopServiceThread(boolean inDebugMode);

	public void continueServiceThread();

	public boolean isServiceThreadActive();
}
