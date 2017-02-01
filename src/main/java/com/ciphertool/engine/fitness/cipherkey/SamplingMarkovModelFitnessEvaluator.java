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

package com.ciphertool.engine.fitness.cipherkey;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;

public class SamplingMarkovModelFitnessEvaluator implements FitnessEvaluator {
	protected Cipher	cipher;

	private MarkovModel	letterMarkovModel;

	private int			lastRowBegin;
	private int			sampleStepSize;

	@Override
	public BigDecimal evaluate(Chromosome chromosome) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		int order = letterMarkovModel.getOrder();

		double matches = 0.0;
		NGramIndexNode match = null;
		int offset = ThreadLocalRandom.current().nextInt(sampleStepSize);

		for (int i = offset; i < currentSolutionString.length() - order; i += sampleStepSize) {
			match = letterMarkovModel.findLongest(currentSolutionString.substring(i, i + order));

			if (match == null) {
				continue;
			}

			matches += 1.0;
		}

		return BigDecimal.valueOf(matches / ((lastRowBegin - order) / sampleStepSize));
	}

	@Override
	public void setGeneticStructure(Object cipher) {
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

	/**
	 * @param sampleStepSize
	 *            the sampleStepSize to set
	 */
	@Required
	public void setSampleStepSize(int sampleStepSize) {
		this.sampleStepSize = sampleStepSize;
	}

	@Override
	public String getDisplayName() {
		return "Sampling Markov Model";
	}
}