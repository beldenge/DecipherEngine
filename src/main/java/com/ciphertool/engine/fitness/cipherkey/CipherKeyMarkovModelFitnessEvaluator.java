package com.ciphertool.engine.fitness.cipherkey;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.dao.cipherkey.MarkovModelDao;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.cipherkey.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.Transition;
import com.ciphertool.sherlock.wordgraph.IndexNode;

public class CipherKeyMarkovModelFitnessEvaluator implements FitnessEvaluator {
	private Logger				log	= LoggerFactory.getLogger(getClass());

	protected Cipher			cipher;

	protected MarkovModelDao	markovModelDao;
	private MarkovModel			model;

	private int					lastRowBegin;
	private IndexNode			rootNode;

	@PostConstruct
	public void init() {
		model = markovModelDao.getModel();
		// rootNode = markovModelDao.getIndexedKGrams();
	}

	@Override
	public Double evaluate(Chromosome chromosome) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		int order = model.getOrder();

		Double total = 0.0;
		for (int i = 0; i < currentSolutionString.length() - order; i++) {
			String kGramString = currentSolutionString.substring(i, i + order);

			char[] kGramChars = kGramString.toCharArray();

			Character[] kGram = new Character[order];

			for (int j = 0; j < kGramChars.length; j++) {
				kGram[j] = kGramChars[j];
			}

			ArrayList<Transition> transitions = model.getModel().get(kGram);

			int transitionIndex = transitions.indexOf(new Transition(currentSolutionString.charAt(i + order)));

			if (transitionIndex > 0) {
				total += Math.pow(order, 2.0) * transitions.get(transitionIndex).getFrequencyRatio().doubleValue();
			}
		}

		return 0.0;
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
		return "Cipher Key Markov Model";
	}
}
