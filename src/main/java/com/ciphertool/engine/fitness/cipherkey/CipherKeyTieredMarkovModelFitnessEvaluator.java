package com.ciphertool.engine.fitness.cipherkey;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.dao.cipherkey.MarkovModelDao;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.cipherkey.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.markov.KGramIndexNode;
import com.ciphertool.sherlock.markov.MarkovModel;

public class CipherKeyTieredMarkovModelFitnessEvaluator implements FitnessEvaluator {
	protected Cipher			cipher;

	protected MarkovModelDao	markovModelDao;
	private MarkovModel			model;

	private int					lastRowBegin;

	@PostConstruct
	public void init() {
		model = markovModelDao.getModel();
	}

	@Override
	public Double evaluate(Chromosome chromosome) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		int order = model.getOrder();

		Double total = 1.0;
		Double matches = 0.0;
		KGramIndexNode transition = null;
		for (int i = 0; i < currentSolutionString.length() - order; i++) {
			KGramIndexNode match = null;

			match = model.findLongest(currentSolutionString.substring(i, i + order));

			if (match == null) {
				continue;
			}

			transition = match.getChild(currentSolutionString.charAt(i + order));

			if (transition == null) {
				continue;
			}

			matches += 1.0;
			total += Math.pow(4, transition.getLevel());
		}

		return total * (matches / (lastRowBegin - order));
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

	@Override
	public String getDisplayName() {
		return "Cipher Key Tiered Markov Model";
	}
}
