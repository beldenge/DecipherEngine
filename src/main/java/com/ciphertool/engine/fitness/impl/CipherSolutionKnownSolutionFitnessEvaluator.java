/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.fitness.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ciphertool.engine.entities.SolutionChromosome;
import com.ciphertool.engine.fitness.KnownSolutionEvaluatorBase;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;

public class CipherSolutionKnownSolutionFitnessEvaluator extends KnownSolutionEvaluatorBase implements
		FitnessEvaluator {
	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Default no-args constructor
	 */
	public CipherSolutionKnownSolutionFitnessEvaluator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.engine.util.SolutionEvaluator#determineConfidenceLevel
	 * (com.ciphertool.engine.entities.Solution)
	 * 
	 * This is essentially a test class. It evaluates fitness by comparing against a known solution so that we can rule
	 * out issues with the evaluator when debugging issues.
	 */
	@Override
	public Double evaluate(Chromosome chromosome) {
		clearHasMatchValues((SolutionChromosome) chromosome);

		SolutionChromosome solution = (SolutionChromosome) chromosome;
		int total = 0;

		int lastRowBegin = (cipher.getColumns() * (cipher.getRows() - 1));

		for (int i = 0; i < lastRowBegin; i++) {
			if (knownSolution.getPlaintextCharacters().get(i).getValue().equals(solution.getPlaintextCharacters().get(i).getValue().toLowerCase())) {
				solution.getPlaintextCharacters().get(i).setHasMatch(true);
				total++;
			}
		}

		solution.setTotalMatches(total);

		double proximityToKnownSolution = (((double) total) / lastRowBegin) * 100;

		if (log.isDebugEnabled()) {
			log.debug("Solution " + solution.getId() + " has a confidence level of: " + proximityToKnownSolution);
		}

		return proximityToKnownSolution;
	}

	@Override
	public String getDisplayName() {
		return "Cipher Solution Known Solution";
	}
}