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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;
import com.ciphertool.sherlock.wordgraph.Match;
import com.ciphertool.sherlock.wordgraph.MatchNode;

public class PlaintextEvaluator {
	private Logger		log	= LoggerFactory.getLogger(getClass());

	protected Cipher	cipher;

	private MarkovModel	letterMarkovModel;
	private MarkovModel	wordMarkovModel;

	private int			lastRowBegin;
	private double		letterNGramWeight;
	private double		wordNGramWeight;

	@PostConstruct
	public void init() {
		double weightTotal = (letterNGramWeight + wordNGramWeight);

		if (Math.abs(1.0 - weightTotal) > 0.0001) {
			throw new IllegalArgumentException(
					"The sum of letterNGramWeight, wordNGramWeight, and frequencyWeight must equal exactly 1.0, but letterNGramWeight="
							+ letterNGramWeight + " and wordNGramWeight=" + wordNGramWeight + " sums to "
							+ weightTotal);
		}
	}

	public Double evaluate(Chromosome chromosome) {
		double total = 0.0;
		total += (letterNGramWeight == 0.0) ? 0.0 : (letterNGramWeight * evaluateLetterNGrams(chromosome));
		total += (wordNGramWeight == 0.0) ? 0.0 : (wordNGramWeight * evaluateWords(chromosome));

		return total;
	}

	public Double evaluateLetterNGrams(Chromosome chromosome) {
		CipherKeyChromosome cipherKeyChromosome = (CipherKeyChromosome) chromosome;

		String currentSolutionString = WordGraphUtils.getSolutionAsString(cipherKeyChromosome).substring(0, lastRowBegin);

		int order = letterMarkovModel.getOrder();

		double matches = 0.0;
		NGramIndexNode match = null;
		for (int i = 0; i < currentSolutionString.length() - order; i++) {
			if (match != null) {
				match = match.getChild(currentSolutionString.charAt(i + order - 1));
			} else {
				match = letterMarkovModel.findLongest(currentSolutionString.substring(i, i + order));
			}

			if (match == null) {
				continue;
			}

			matches += 1.0;
		}

		double letterNGramProbability = (matches / (lastRowBegin - order));

		if (letterNGramProbability < 0.0) {
			letterNGramProbability = 0.0;
		}

		return letterNGramProbability;
	}

	public Double evaluateWords(Chromosome chromosome) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		Map<Integer, Match> matchMap = new HashMap<Integer, Match>(currentSolutionString.length());
		String longestMatch;

		/*
		 * Find the longest match at each indice. This helps to prevent short matches from precluding longer subsequent
		 * matches from being found.
		 */
		for (int i = 0; i < currentSolutionString.length(); i++) {
			longestMatch = wordMarkovModel.findLongestAsString(currentSolutionString.substring(i));

			if (longestMatch != null) {
				matchMap.put(i, new Match(i, i + longestMatch.length() - 1, longestMatch));
			}
		}

		List<MatchNode> rootNodes = new ArrayList<MatchNode>();

		/*
		 * Find all the starting matches (first match from the beginning of the solution string) which overlap with one
		 * another. These are the candidate root matches. This must be done in order, hence why we cannot iterate over
		 * the Map's entry set.
		 */
		for (int beginPos = 0; beginPos < lastRowBegin; beginPos++) {
			if (matchMap.containsKey(beginPos)) {
				if (WordGraphUtils.nonOverlapping(beginPos, rootNodes)) {
					// Fail fast -- there are no candidates beyond this point
					break;
				}

				rootNodes.add(new MatchNode(matchMap.get(beginPos)));
			}
		}

		List<String> branches = new ArrayList<String>();

		/*
		 * Beginning with each candidate root node, find all possible branches of overlapping matches from the initial
		 * map of matches.
		 */
		for (MatchNode node : rootNodes) {
			WordGraphUtils.findOverlappingChildren(node.getSelf().getEndPos() + 1, lastRowBegin, matchMap, node);

			branches.addAll(node.printBranches());
		}

		double score;
		double highestScore = 0.0;

		String bestBranch = "";

		// Find the longest branch by number of characters. Ties are skipped.
		for (String branch : branches) {
			score = branch.replaceAll(", ", "").length();

			if (score > highestScore) {
				highestScore = score;
				bestBranch = branch;
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("Best branch: " + bestBranch);
		}

		return (double) highestScore / (double) currentSolutionString.length();
	}

	public void setStructure(Object cipher) {
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
}