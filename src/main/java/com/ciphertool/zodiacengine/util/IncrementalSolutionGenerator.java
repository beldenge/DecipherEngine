/**
 * Copyright 2012 George Belden
 * 
 * This file is part of ZodiacEngine.
 * 
 * ZodiacEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ZodiacEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ZodiacEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.zodiacengine.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.util.SentenceHelper;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.IncrementalSolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;

public class IncrementalSolutionGenerator implements SolutionGenerator {
	private SentenceHelper sentenceHelper;
	private Logger log = Logger.getLogger(getClass());
	private Cipher cipher;
	private int cipherLength;
	private long improvementAttempts;
	private SolutionEvaluator solutionEvaluator;

	/**
	 * Default no-args constructor
	 */
	public IncrementalSolutionGenerator() {
	}

	@Override
	public SolutionChromosome generateSolution() {
		// Set confidence levels to lowest possible
		IncrementalSolutionChromosome solution = new IncrementalSolutionChromosome(cipher, 0, 0, 0);

		solution.setCipherId(cipher.getId());
		solution.setCommittedIndex(0);
		solution.setUncommittedIndex(0);

		do {
			appendNextBestSentence(solution);

			/*
			 * Advance the committed index
			 */
			solution.setCommittedIndex(solution.getUncommittedIndex());

			log.debug("New committed index achieved: " + solution.getCommittedIndex());
		} while (solution.getCommittedIndex() < cipherLength);

		/*
		 * Set the final confidence levels and the hasMatch values on each
		 * Plaintext
		 */
		solutionEvaluator.determineConfidenceLevel(solution);

		log.debug(solution);

		return solution;
	}

	/**
	 * Select the best possible sentence starting at the current index, and then
	 * advance the index.
	 * 
	 * @param incrementalSolution
	 */
	private void appendNextBestSentence(IncrementalSolutionChromosome incrementalSolution) {
		Sentence nextSentence = null;

		for (long i = 0; i < improvementAttempts; i++) {
			nextSentence = sentenceHelper.generateRandomSentence();

			compareSentenceToSolution(incrementalSolution, nextSentence);
		}
	}

	/**
	 * Creates a new solution with the sentence supplied and then compares it to
	 * the current solution. Returns the solution with the greater confidence
	 * level.
	 * 
	 * @param incrementalSolution
	 * @param sentence
	 * @return
	 */
	private void compareSentenceToSolution(IncrementalSolutionChromosome incrementalSolution,
			Sentence sentence) {
		List<WordGene> candidateGeneList = new ArrayList<WordGene>();

		StringBuilder rawText = new StringBuilder();

		for (Word w : sentence.getWords()) {
			rawText.append(w.getId().getWord());
		}

		int sentenceLength = sentence.length();

		char[] chars = new char[sentenceLength];

		rawText.getChars(0, sentenceLength, chars, 0);

		int newIndex = incrementalSolution.getCommittedIndex();

		for (char c : chars) {
			/*
			 * Don't add the plaintext character if the index has surpassed the
			 * cipher length. It's pointless.
			 * 
			 * It is very important to convert to lowercase here, since map
			 * lookups within the evaluator are case-sensitive.
			 */
			if (newIndex <= cipherLength) {
				WordGene gene = new WordGene(new Word(new WordId(String.valueOf(c).toLowerCase(),
						'*')), incrementalSolution, newIndex);

				candidateGeneList.add(gene);

				newIndex++;
			}
		}

		((IncrementalSolutionEvaluator) solutionEvaluator).comparePlaintextToSolution(
				incrementalSolution, candidateGeneList);
	}

	@Override
	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
		this.cipherLength = cipher.length();
	}

	/**
	 * @param sentenceHelper
	 */
	@Required
	public void setSentenceHelper(SentenceHelper sentenceHelper) {
		this.sentenceHelper = sentenceHelper;
	}

	/**
	 * @param improvementAttempts
	 *            the improvementAttempts to set
	 */
	@Required
	public void setImprovementAttempts(long improvementAttempts) {
		this.improvementAttempts = improvementAttempts;
	}

	/**
	 * @param solutionEvaluator
	 *            the solutionEvaluator to set
	 */
	@Required
	public void setSolutionEvaluator(SolutionEvaluator solutionEvaluator) {
		this.solutionEvaluator = solutionEvaluator;
	}
}
