/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with DecipherEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.fitness.cipherkey;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.MathConstants;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;

public class ProbabilisticFitnessEvaluator implements FitnessEvaluator {
	private MarkovModel	letterMarkovModel;
	protected Cipher	cipher;
	private int			lastRowBegin;
	private BigDecimal	unknownLetterNGramProbability;

	@Override
	public BigDecimal evaluate(Chromosome chromosome) {
		if (unknownLetterNGramProbability == null) {
			unknownLetterNGramProbability = BigDecimal.ONE.divide(BigDecimal.valueOf(letterMarkovModel.getRootNode().getTerminalInfo().getCount()), MathConstants.PREC_10_HALF_UP);
		}

		CipherKeyChromosome solution = (CipherKeyChromosome) chromosome;

		int order = letterMarkovModel.getOrder();

		BigDecimal letterNGramResults = evaluateLetterNGrams(letterMarkovModel, solution, order);

		return letterNGramResults;
	}

	public BigDecimal evaluateLetterNGrams(MarkovModel letterMarkovModel, CipherKeyChromosome solution, int order) {
		BigDecimal probability = null;
		BigDecimal nGramProbability = BigDecimal.ONE;
		NGramIndexNode match = null;

		String solutionString = WordGraphUtils.getSolutionAsString(solution);

		for (int i = 0; i < lastRowBegin - order; i++) {
			match = letterMarkovModel.findLongest(solutionString.substring(i, i + order));

			if (match != null && match.getTerminalInfo().getLevel() == order) {
				probability = match.getTerminalInfo().getProbability();
			} else {
				probability = unknownLetterNGramProbability;
			}

			nGramProbability = nGramProbability.multiply(probability, MathConstants.PREC_10_HALF_UP);
		}

		return nGramProbability;
	}

	@Override
	public void setGeneticStructure(Object obj) {
		this.cipher = (Cipher) cipher;

		lastRowBegin = (this.cipher.getColumns() * (this.cipher.getRows() - 1));
	}

	/**
	 * @param letterMarkovModel
	 *            the letterMarkovModel to set
	 */
	@Required
	public void setLetterMarkovModel(MarkovModel letterMarkovModel) {
		this.letterMarkovModel = letterMarkovModel;
	}

	@Override
	public String getDisplayName() {
		return "Probabilistic";
	}
}
