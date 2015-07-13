/**
 * Copyright 2015 George Belden
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

package com.ciphertool.zodiacengine.fitness.cipherkey;

import org.apache.log4j.Logger;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.cipherkey.CipherKeyChromosome;

public class CipherKeyKnownSolutionFitnessEvaluator extends CipherKeyFitnessEvaluatorBase implements FitnessEvaluator {

	private Logger log = Logger.getLogger(getClass());

	private static final String KNOWN_SOLUTION_STRING = "ilikekillingpeoplebecauseitissomuchfunitismorefunthankillingwildgameintheforrest"
			+ "becausemanisthemoatdangeroueanamalofalltokillsomethinggivesmethemoatthrillingexperenceitisevenbetterthangettingyourrocksoff"
			+ "withagirlthebestpartofitiathaewhenidieiwillbereborninparadicesndalltheihavekilledwillbecomemyslavesiwillnotgiveyoumynamebecause"
			+ "youwilltrytosloidownorstopmycollectingofslavesformyafterlifeebeorietemethhpiti";

	/**
	 * Default no-args constructor
	 */
	public CipherKeyKnownSolutionFitnessEvaluator() {
	}

	@Override
	public Double evaluate(Chromosome chromosome) {
		double proximityToKnownSolution = 0.0;

		if (log.isDebugEnabled()) {
			log.debug("Solution " + ((CipherKeyChromosome) chromosome).getId() + " has a confidence level of: "
					+ proximityToKnownSolution);
		}

		String currentSolutionString = getSolutionAsString((CipherKeyChromosome) chromosome);

		if (currentSolutionString.length() != KNOWN_SOLUTION_STRING.length()) {
			log.error("Current solution length of " + currentSolutionString.length()
					+ " does not match the known solution String length of " + KNOWN_SOLUTION_STRING.length()
					+ ".  This will cause innacurate fitness calculations.  Solution: " + currentSolutionString);
		}

		for (int i = 0; i < currentSolutionString.length(); i++) {
			if (currentSolutionString.charAt(i) == KNOWN_SOLUTION_STRING.charAt(i)) {
				proximityToKnownSolution++;
			}
		}

		return proximityToKnownSolution;
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;
	}
}
