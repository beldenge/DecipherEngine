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

package com.ciphertool.engine.bayes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.dao.CipherDao;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;

public class BayesianDecipherManager {
	private static Logger log = LoggerFactory.getLogger(BayesianDecipherManager.class);

	private String cipherName;
	private CipherDao cipherDao;
	private Cipher cipher;
	private MarkovModel letterMarkovModel;
	private MarkovModel wordMarkovModel;
	private double letterNGramWeight;
	private double wordNGramWeight;
	private int samplerIterations;
	private double sourceModelPrior;
	private double channelModelPrior;
	private int annealingTemperatureStart;
	private int annealingTemperatureStop;
	private int lastRowBegin;
	private int cipherKeySize;

	@PostConstruct
	public void setUp() {
		this.cipher = cipherDao.findByCipherName(cipherName);

		lastRowBegin = cipher.getColumns() * (cipher.getRows() - 1);
		cipherKeySize = (int) cipher.getCiphertextCharacters().stream().map(c -> c.getValue()).distinct().count();

		List<LetterProbability> letterUnigramProbabilities = new ArrayList<>();

		for (Map.Entry<Character, NGramIndexNode> entry : letterMarkovModel.getRootNode().getTransitions().entrySet()) {
			letterUnigramProbabilities.add(new LetterProbability(entry.getKey(), entry.getValue().getTerminalInfo()
					.getProbability()));
		}

		// Initialize the solution key
		CipherKeyChromosome initialSolution = new CipherKeyChromosome(cipher, cipherKeySize);

		cipher.getCiphertextCharacters().stream().map(c -> c.getValue()).distinct().forEach(c -> {
			// Pick a plaintext at random using the language model and max annealing temperature
			Gene nextGene = null;

			initialSolution.putGene(c, nextGene);
		});

		for (int i = 0; i < samplerIterations; i++) {
			runSampler(initialSolution);
		}
	}

	private void runSampler(CipherKeyChromosome solution) {
		// For each cipher symbol type, run the gibbs sampling
	}

	/**
	 * @param cipherName
	 *            the cipherName to set
	 */
	@Required
	public void setCipherName(String cipherName) {
		this.cipherName = cipherName;
	}

	/**
	 * @param cipherDao
	 *            the cipherDao to set
	 */
	@Required
	public void setCipherDao(CipherDao cipherDao) {
		this.cipherDao = cipherDao;
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
	 * @param wordMarkovModel
	 *            the wordMarkovModel to set
	 */
	@Required
	public void setWordMarkovModel(MarkovModel wordMarkovModel) {
		this.wordMarkovModel = wordMarkovModel;
	}

	/**
	 * @param letterNGramWeight
	 *            the letterNGramWeight to set
	 */
	@Required
	public void setLetterNGramWeight(double letterNGramWeight) {
		this.letterNGramWeight = letterNGramWeight;
	}

	/**
	 * @param wordNGramWeight
	 *            the wordNGramWeight to set
	 */
	@Required
	public void setWordNGramWeight(double wordNGramWeight) {
		this.wordNGramWeight = wordNGramWeight;
	}

	/**
	 * @param samplerIterations
	 *            the samplerIterations to set
	 */
	@Required
	public void setSamplerIterations(int samplerIterations) {
		this.samplerIterations = samplerIterations;
	}

	/**
	 * @param sourceModelPrior
	 *            the sourceModelPrior to set
	 */
	@Required
	public void setSourceModelPrior(double sourceModelPrior) {
		this.sourceModelPrior = sourceModelPrior;
	}

	/**
	 * @param channelModelPrior
	 *            the channelModelPrior to set
	 */
	@Required
	public void setChannelModelPrior(double channelModelPrior) {
		this.channelModelPrior = channelModelPrior;
	}

	/**
	 * @param annealingTemperatureStart
	 *            the annealingTemperatureStart to set
	 */
	@Required
	public void setAnnealingTemperatureStart(int annealingTemperatureStart) {
		this.annealingTemperatureStart = annealingTemperatureStart;
	}

	/**
	 * @param annealingTemperatureStop
	 *            the annealingTemperatureStop to set
	 */
	@Required
	public void setAnnealingTemperatureStop(int annealingTemperatureStop) {
		this.annealingTemperatureStop = annealingTemperatureStop;
	}
}
