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

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;
import com.ciphertool.sherlock.markov.TerminalInfo;

public class TieredMarkovModelFitnessEvaluator implements FitnessEvaluator {
	protected static Logger	log	= LoggerFactory.getLogger(TieredMarkovModelFitnessEvaluator.class);

	protected Cipher		cipher;

	private MarkovModel		markovModel;

	private int				lastRowBegin;
	private int				minimumLetterOrder;

	@PostConstruct
	public void init() {
		int letterOrder = this.markovModel.getLetterOrder();

		if (this.minimumLetterOrder > letterOrder) {
			log.warn("Minimum letter order is set to " + this.minimumLetterOrder
					+ ", which is greater than the Markov model letter order of " + letterOrder
					+ ".  Reducing minimumLetterOrder to " + letterOrder);

			this.minimumLetterOrder = letterOrder;
		}
	}

	@Override
	public Double evaluate(Chromosome chromosome) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		int order = markovModel.getLetterOrder();

		double matches = 0.0;
		NGramIndexNode match = null;
		TerminalInfo terminalInfo;
		for (int i = 0; i < currentSolutionString.length() - order; i++) {
			if (match != null) {
				match = match.getChild(currentSolutionString.charAt(i + order - 1));
			}

			if (match == null) {
				match = markovModel.findLongest(currentSolutionString.substring(i, i + order));
			}

			if (match == null) {
				continue;
			}

			terminalInfo = match.getTerminalInfo();

			if (terminalInfo != null) {
				if (terminalInfo.getLevel() >= minimumLetterOrder) {
					matches += (double) terminalInfo.getLevel() / (double) order;
				}

				if (terminalInfo.getLevel() != order) {
					match = null;
				}
			}
		}

		return (matches / (lastRowBegin - order));
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;

		lastRowBegin = (this.cipher.getColumns() * (this.cipher.getRows() - 1));
	}

	/**
	 * @param markovModel
	 *            the markovModel to set
	 */
	@Required
	public void setMarkovModel(MarkovModel markovModel) {
		this.markovModel = markovModel;
	}

	/**
	 * @param minimumLetterOrder
	 *            the minimumLetterOrder to set
	 */
	@Required
	public void setMinimumLetterOrder(int minimumLetterOrder) {
		this.minimumLetterOrder = minimumLetterOrder;
	}

	@Override
	public String getDisplayName() {
		return "Tiered Markov Model";
	}
}
