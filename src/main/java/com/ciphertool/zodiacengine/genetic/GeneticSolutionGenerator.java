package com.ciphertool.zodiacengine.genetic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.util.SolutionGenerator;

public class GeneticSolutionGenerator implements SolutionGenerator {
	private Cipher cipher;
	private WordListDao wordListDao;
	private Logger log = Logger.getLogger(getClass());

	public GeneticSolutionGenerator(String cipherName, CipherDao cipherDao) {
		cipher = cipherDao.findByCipherName(cipherName);
	}

	@Override
	public Solution generateSolution() {
		// Set confidence levels to lowest possible
		SolutionChromosome solution = new SolutionChromosome(cipher.getId(), 0, 0, 0);

		solution.setCipher(cipher);

		List<Gene> wordGenes = getWordGenes(solution);

		solution.setGenes(wordGenes);

		log.debug(solution);

		return solution;
	}

	private List<Gene> getWordGenes(Solution solution) {
		List<Gene> geneList = new ArrayList<Gene>();
		Word nextWord;
		WordGene nextGene;
		int length = 0;

		do {
			nextWord = wordListDao.findRandomWord();

			nextGene = new WordGene(nextWord, solution, length);

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
