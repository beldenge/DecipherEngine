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

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sentencebuilder.dao.UniqueWordListDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.wordgraph.IndexNode;
import com.ciphertool.sentencebuilder.wordgraph.Match;
import com.ciphertool.sentencebuilder.wordgraph.MatchNode;
import com.ciphertool.zodiacengine.common.WordGraphUtils;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.cipherkey.CipherKeyChromosome;

public class CipherKeyIndexedWordGraphFitnessEvaluator implements FitnessEvaluator {
	private Logger log = Logger.getLogger(getClass());

	protected Cipher cipher;
	private int minWordLength;
	private int top;

	private UniqueWordListDao wordListDao;

	private List<Word> topWords = new ArrayList<Word>();

	private IndexNode rootNode = new IndexNode();

	@PostConstruct
	public void init() {
		topWords = wordListDao.getTopWords(top);

		if (topWords == null || topWords.size() < top) {
			String message = "Attempted to get top " + top + " words from populated DAO, but only "
					+ (topWords == null ? 0 : topWords.size()) + " words were available.";
			log.error(message);
			throw new IllegalStateException(message);
		}

		String lowerCaseWord;
		for (Word word : topWords) {
			if (word.getId().getWord().length() < minWordLength) {
				continue;
			}

			lowerCaseWord = word.getId().getWord().toLowerCase();
			WordGraphUtils.populateMap(rootNode, lowerCaseWord, lowerCaseWord);
		}
	}

	@Override
	public Double evaluate(Chromosome chromosome) {
		Map<Integer, List<Match>> matchMap = new HashMap<Integer, List<Match>>();

		int lastRowBegin = (cipher.getColumns() * (cipher.getRows() - 1));

		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(
				0, lastRowBegin);

		String longestMatch;
		for (int i = 0; i < currentSolutionString.length(); i++) {
			longestMatch = WordGraphUtils.findLongestWordMatch(rootNode, i, currentSolutionString, null);

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

		return Double.valueOf(highestScore);
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;
	}

	/**
	 * @param top
	 *            the top to set
	 */
	@Required
	public void setTop(int top) {
		if (top <= 0) {
			String message = "Value of " + top + " is invalid for top in " + this.getClass()
					+ ".  Value must be greater than zero.";
			log.error(message);
			throw new IllegalArgumentException(message);
		}

		this.top = top;
	}

	/**
	 * @param wordListDao
	 *            the wordListDao to set
	 */
	@Required
	public void setWordListDao(UniqueWordListDao wordListDao) {
		this.wordListDao = wordListDao;
	}

	/**
	 * @param minWordLength
	 *            the minWordLength to set
	 */
	@Required
	public void setMinWordLength(int minWordLength) {
		this.minWordLength = minWordLength;
	}

	@Override
	public String getDisplayName() {
		return "Cipher Key Indexed Word Graph";
	}
}
