/**
 * Copyright 2015 George Belden
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

package com.ciphertool.zodiacengine.fitness.cipherkey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sentencebuilder.wordgraph.IndexNode;
import com.ciphertool.sentencebuilder.wordgraph.Match;
import com.ciphertool.sentencebuilder.wordgraph.MatchNode;
import com.ciphertool.zodiacengine.common.WordGraphUtils;
import com.ciphertool.zodiacengine.dao.cipherkey.TopWordsFacade;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.cipherkey.CipherKeyChromosome;

public class CipherKeyUniqueIndexedWordGraphFitnessEvaluator implements FitnessEvaluator {
	private int matchThreshold = 2;
	protected Cipher cipher;

	protected TopWordsFacade topWordsFacade;

	@Override
	public Double evaluate(Chromosome chromosome) {
		Map<Integer, List<Match>> matchMap = new HashMap<Integer, List<Match>>();

		int lastRowBegin = (cipher.getColumns() * (cipher.getRows() - 1));

		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(
				0, lastRowBegin);

		String longestMatch;
		IndexNode rootNode = topWordsFacade.getIndexedWords();

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

		long score;
		long highestScore = 0;

		String bestBranch = "";

		for (String branch : branches) {
			score = 0;

			for (String word : branch.split(", ")) {
				score += Math.pow(2, word.length());
			}

			if (score > highestScore) {
				highestScore = score;
				bestBranch = branch;
			}
		}

		double uniquenessPenalty = highestScore * determineUniquenessPenalty(bestBranch.split(", "));

		return ((double) (highestScore)) - uniquenessPenalty;
	}

	private double determineUniquenessPenalty(String[] words) {
		Map<String, Integer> wordOccurrenceMap = new HashMap<String, Integer>();

		/*
		 * Count the number of occurrences of each word and stick it in a map.
		 */
		for (String word : words) {
			if (!wordOccurrenceMap.containsKey(word)) {
				wordOccurrenceMap.put(word, 0);
			}

			wordOccurrenceMap.put(word, wordOccurrenceMap.get(word) + 1);
		}

		double penalty = 0;

		/*
		 * We don't care about the Strings themselves anymore. Just their numbers of occurrences.
		 */
		for (Integer numOccurrences : wordOccurrenceMap.values()) {
			penalty += (0.01 * (numOccurrences - matchThreshold));
		}

		return penalty;
	}

	/**
	 * @param matchThreshold
	 *            the matchThreshold to set
	 */
	@Required
	public void setMatchThreshold(int matchThreshold) {
		this.matchThreshold = matchThreshold;
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;
	}

	/**
	 * @param topWordsFacade
	 *            the topWordsFacade to set
	 */
	@Required
	public void setTopWordsFacade(TopWordsFacade topWordsFacade) {
		this.topWordsFacade = topWordsFacade;
	}

	@Override
	public String getDisplayName() {
		return "Cipher Key Unique Indexed Word Graph";
	}
}
