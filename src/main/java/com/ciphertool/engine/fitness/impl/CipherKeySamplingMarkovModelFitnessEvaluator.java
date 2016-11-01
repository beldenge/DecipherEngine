package com.ciphertool.engine.fitness.impl;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.dao.MarkovModelDao;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.markov.KGramIndexNode;
import com.ciphertool.sherlock.markov.MarkovModel;

public class CipherKeySamplingMarkovModelFitnessEvaluator implements FitnessEvaluator {
	protected Cipher			cipher;

	protected MarkovModelDao	markovModelDao;
	private MarkovModel			model;

	private int					lastRowBegin;
	private int					sampleStepSize;

	@PostConstruct
	public void init() {
		model = markovModelDao.getModel();
	}

	@Override
	public Double evaluate(Chromosome chromosome) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		int order = model.getOrder();

		double total = 0.0;
		double matches = 0.0;
		double weight = 0.0;
		KGramIndexNode match = null;
		int offset = ThreadLocalRandom.current().nextInt(sampleStepSize);

		for (int i = offset; i < currentSolutionString.length() - order; i += sampleStepSize) {
			match = model.find(currentSolutionString.substring(i, i + order + 1));

			if (match == null) {
				continue;
			}

			matches += 1.0;
			weight = (matches / ((lastRowBegin - order) / sampleStepSize));
			total += (100.0 * weight);
		}

		return total;
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;

		lastRowBegin = (this.cipher.getColumns() * (this.cipher.getRows() - 1));
	}

	/**
	 * @param markovModelDao
	 *            the markovModelDao to set
	 */
	@Required
	public void setMarkovModelDao(MarkovModelDao markovModelDao) {
		this.markovModelDao = markovModelDao;
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
		return "Cipher Key Sampling Markov Model";
	}
}