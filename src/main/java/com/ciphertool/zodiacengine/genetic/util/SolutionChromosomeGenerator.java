package com.ciphertool.zodiacengine.genetic.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.util.ChromosomeGenerator;
import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;
import com.ciphertool.zodiacengine.singleton.CipherSingleton;

public class SolutionChromosomeGenerator implements ChromosomeGenerator {
	private Cipher cipher;
	private WordListDao wordListDao;
	private Logger log = Logger.getLogger(getClass());

	public SolutionChromosomeGenerator(CipherSingleton cipherSingleton) {
		cipher = cipherSingleton.getInstance();
	}

	@Override
	public Chromosome generateChromosome() {
		// Set confidence levels to lowest possible
		SolutionChromosome solution = new SolutionChromosome(cipher.getId(), 0, 0, 0);

		solution.setCipher(cipher);

		List<Gene> wordGenes = getWordGenes(solution);

		for (Gene gene : wordGenes) {
			solution.addGene(gene);
		}

		log.debug(solution);

		return (Chromosome) solution;
	}

	private List<Gene> getWordGenes(Solution solution) {
		List<Gene> geneList = new ArrayList<Gene>();
		Word nextWord;
		WordGene nextGene;
		int length = 0;

		do {
			nextWord = wordListDao.findRandomWord();

			nextGene = new WordGene(nextWord, (SolutionChromosome) solution, length);

			length += nextGene.size();

			geneList.add(nextGene);
		} while (length < cipher.length());

		return geneList;
	}

	/**
	 * @param wordListDao
	 *            the wordListDao to set
	 */
	@Required
	public void setWordListDao(WordListDao wordListDao) {
		this.wordListDao = wordListDao;
	}
}
