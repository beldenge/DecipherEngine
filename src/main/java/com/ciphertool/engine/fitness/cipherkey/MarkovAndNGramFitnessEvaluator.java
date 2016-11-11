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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.dao.TopWordsFacade;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.engine.entities.CipherKeyGene;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.markov.KGramIndexNode;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.wordgraph.IndexNode;
import com.ciphertool.sherlock.wordgraph.Match;
import com.ciphertool.sherlock.wordgraph.MatchNode;

public class MarkovAndNGramFitnessEvaluator implements FitnessEvaluator {
	private Logger							log						= LoggerFactory.getLogger(getClass());

	private static final List<Character>	LOWERCASE_LETTERS		= Arrays.asList(new Character[] { 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z' });
	private static final int				GRACE_WINDOW_SIZE		= 1;

	protected Cipher						cipher;

	private MarkovModel						model;
	protected TopWordsFacade				topWordsFacade;

	private int								lastRowBegin;
	private IndexNode						rootNode;
	private double							frequencyWeight;
	private double							letterNGramWeight;
	private double							wordNGramWeight;

	private Map<Character, Double>			expectedLetterFrequencies;
	private Map<Character, Integer>			expectedLetterCounts	= new HashMap<Character, Integer>(
			LOWERCASE_LETTERS.size());

	@PostConstruct
	public void init() {
		rootNode = topWordsFacade.getIndexedWordsAndNGrams();

		double weightTotal = (letterNGramWeight + frequencyWeight + wordNGramWeight);

		if (Math.abs(1.0 - weightTotal) > 0.0001) {
			throw new IllegalArgumentException(
					"The sum of kGramWeight and frequencyWeight must equal exactly 1.0, but letterNGramWeight="
							+ letterNGramWeight + " and frequencyWeight=" + frequencyWeight + " and wordNGramWeight="
							+ wordNGramWeight + " sums to " + weightTotal);
		}
	}

	@Override
	public Double evaluate(Chromosome chromosome) {
		Double frequencyFitness = evaluateFrequency(chromosome);
		Double markovFitness = evaluateMarkovModel(chromosome);
		Double nGramFitness = evaluateNGram(chromosome);

		return (markovFitness * letterNGramWeight) + (frequencyFitness * frequencyWeight) + (nGramFitness
				* wordNGramWeight);
	}

	public Double evaluateFrequency(Chromosome chromosome) {
		CipherKeyChromosome cipherKeyChromosome = (CipherKeyChromosome) chromosome;

		Map<Character, Integer> actualLetterCounts = new HashMap<Character, Integer>(LOWERCASE_LETTERS.size());

		for (Map.Entry<String, Gene> entry : cipherKeyChromosome.getGenes().entrySet()) {
			char key = ((CipherKeyGene) entry.getValue()).getValue().charAt(0);

			Integer value = actualLetterCounts.get(key);

			if (value == null) {
				actualLetterCounts.put(key, 0);

				value = actualLetterCounts.get(key);
			}

			actualLetterCounts.put(key, value + 1);
		}

		int numCorrect = 0;

		for (char letter : LOWERCASE_LETTERS) {
			int actualCount = actualLetterCounts.containsKey(letter) ? actualLetterCounts.get(letter) : 0;
			int expectedCount = expectedLetterCounts.get(letter);

			if (expectedCount + GRACE_WINDOW_SIZE >= actualCount && expectedCount - GRACE_WINDOW_SIZE <= actualCount) {
				numCorrect += 1;
			} else if (expectedCount + GRACE_WINDOW_SIZE < actualCount) {
				numCorrect -= (actualCount - (expectedCount + GRACE_WINDOW_SIZE));
			} else if (expectedCount - GRACE_WINDOW_SIZE > actualCount) {
				numCorrect -= ((expectedCount + GRACE_WINDOW_SIZE) - actualCount);
			}
		}

		double frequencyProbability = (double) numCorrect / (double) LOWERCASE_LETTERS.size();

		if (frequencyProbability < 0.0) {
			frequencyProbability = 0.0;
		}

		return frequencyProbability * frequencyWeight;
	}

	public Double evaluateMarkovModel(Chromosome chromosome) {
		CipherKeyChromosome cipherKeyChromosome = (CipherKeyChromosome) chromosome;

		String currentSolutionString = WordGraphUtils.getSolutionAsString(cipherKeyChromosome).substring(0, lastRowBegin);

		int order = model.getOrder();

		double matches = 0.0;
		KGramIndexNode match = null;
		for (int i = 0; i < currentSolutionString.length() - order; i++) {
			if (match != null) {
				match = match.getChild(currentSolutionString.charAt(i + order));
			} else {
				match = model.find(currentSolutionString.substring(i, i + order + 1));
			}

			if (match == null) {
				continue;
			}

			matches += 1.0;
		}

		double letterNGramProbability = (matches / (lastRowBegin - order - 1));

		if (letterNGramProbability < 0.0) {
			letterNGramProbability = 0.0;
		}

		return letterNGramProbability;
	}

	public Double evaluateNGram(Chromosome chromosome) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		Map<Integer, List<Match>> matchMap = new HashMap<Integer, List<Match>>(currentSolutionString.length());

		String longestMatch;
		for (int i = 0; i < currentSolutionString.length(); i++) {
			longestMatch = WordGraphUtils.findLongestWordMatch(rootNode, 0, currentSolutionString.substring(i), null);

			if (longestMatch != null) {
				if (!matchMap.containsKey(i)) {
					matchMap.put(i, new ArrayList<Match>());
				}

				matchMap.get(i).add(new Match(i, i + longestMatch.length() - 1, longestMatch));
			}
		}

		List<MatchNode> rootNodes = new ArrayList<MatchNode>();
		int beginPos;
		for (beginPos = 0; beginPos < lastRowBegin; beginPos++) {
			if (matchMap.containsKey(beginPos)) {
				if (WordGraphUtils.nonOverlapping(beginPos, rootNodes)) {
					break;
				}

				for (Match match : matchMap.get(beginPos)) {
					rootNodes.add(new MatchNode(match));
				}
			}
		}

		List<String> branches = new ArrayList<String>();
		for (MatchNode node : rootNodes) {
			WordGraphUtils.findOverlappingChildren(node.getSelf().getEndPos() + 1, lastRowBegin, matchMap, node);

			branches.addAll(node.printBranches());
		}

		double score;
		double highestScore = 0;

		String bestBranch = "";

		for (String branch : branches) {
			score = 0;

			for (String word : branch.split(", ")) {
				score += word.length();
			}

			if (score > highestScore) {
				highestScore = score;
				bestBranch = branch;
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("Best branch: " + bestBranch);
		}

		return (double) bestBranch.length() / (double) currentSolutionString.length();
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;

		lastRowBegin = (this.cipher.getColumns() * (this.cipher.getRows() - 1));

		int cipherKeySize = (int) this.cipher.getCiphertextCharacters().stream().map(c -> c.getValue()).distinct().count();

		for (Map.Entry<Character, Double> entry : expectedLetterFrequencies.entrySet()) {
			expectedLetterCounts.put(entry.getKey(), (int) Math.round(entry.getValue() * cipherKeySize));
		}
	}

	/**
	 * @param model
	 *            the model to set
	 */
	@Required
	public void setModel(MarkovModel model) {
		this.model = model;
	}

	/**
	 * @param topWordsFacade
	 *            the topWordsFacade to set
	 */
	@Required
	public void setTopWordsFacade(TopWordsFacade topWordsFacade) {
		this.topWordsFacade = topWordsFacade;
	}

	/**
	 * @param expectedLetterFrequencies
	 *            the expectedLetterFrequencies to set
	 */
	@Required
	public void setExpectedLetterFrequencies(Map<Character, Double> expectedLetterFrequencies) {
		this.expectedLetterFrequencies = expectedLetterFrequencies;
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
	 * @param frequencyWeight
	 *            the frequencyWeight to set
	 */
	@Required
	public void setFrequencyWeight(double frequencyWeight) {
		this.frequencyWeight = frequencyWeight;
	}

	@Override
	public String getDisplayName() {
		return "Markov And N-Gram";
	}
}
