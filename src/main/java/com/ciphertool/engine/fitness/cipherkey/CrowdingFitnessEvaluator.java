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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.wordgraph.Match;
import com.ciphertool.sherlock.wordgraph.MatchNode;

public class CrowdingFitnessEvaluator implements FitnessEvaluator {
	protected Cipher	cipher;

	private MarkovModel	wordMarkovModel;

	private int			minCrowdSize;
	private double		penaltyFactor;
	private double		sigma;
	private int			lastRowBegin;

	@Override
	public BigDecimal evaluate(Chromosome chromosome) {
		Map<Integer, Match> matchMap = new HashMap<Integer, Match>();

		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		String longestMatch;

		for (int i = 0; i < currentSolutionString.length(); i++) {
			longestMatch = wordMarkovModel.findLongestAsString(currentSolutionString.substring(i));

			if (longestMatch != null) {
				matchMap.put(i, new Match(i, i + longestMatch.length() - 1, longestMatch));
			}
		}

		List<MatchNode> rootNodes = new ArrayList<MatchNode>();
		int beginPos;
		for (beginPos = 0; beginPos < lastRowBegin; beginPos++) {
			if (matchMap.containsKey(beginPos)) {
				if (WordGraphUtils.nonOverlapping(beginPos, rootNodes)) {
					break;
				}

				rootNodes.add(new MatchNode(matchMap.get(beginPos)));
			}
		}

		List<String> branches = new ArrayList<String>();
		for (MatchNode node : rootNodes) {
			WordGraphUtils.findOverlappingChildren(node.getSelf().getEndPos() + 1, lastRowBegin, matchMap, node);

			branches.addAll(node.printBranches());
		}

		long score;
		long highestScore = 0;

		@SuppressWarnings("unused")
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

		double fitness = Double.valueOf(highestScore);

		int crowdSize = 1;
		for (Chromosome other : chromosome.getPopulation().getIndividuals()) {
			if (chromosome.similarityTo(other) > sigma) {
				crowdSize++;
			}
		}

		for (int i = crowdSize - minCrowdSize; i > 0; i -= minCrowdSize) {
			fitness *= penaltyFactor;
		}

		return BigDecimal.valueOf(fitness);
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;

		lastRowBegin = (this.cipher.getColumns() * (this.cipher.getRows() - 1));
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
	 * @param minCrowdSize
	 *            the minGroupSize to set
	 */
	@Required
	public void setMinCrowdSize(int minCrowdSize) {
		this.minCrowdSize = minCrowdSize;
	}

	/**
	 * @param penaltyFactor
	 *            the penaltyFactor to set
	 */
	@Required
	public void setPenaltyFactor(double penaltyFactor) {
		this.penaltyFactor = penaltyFactor;
	}

	/**
	 * @param sigma
	 *            the sigma to set
	 */
	@Required
	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	@Override
	public String getDisplayName() {
		return "Crowding";
	}
}
