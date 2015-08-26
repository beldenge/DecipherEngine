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

package com.ciphertool.zodiacengine.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.ChromosomePrinter;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.sentencebuilder.dao.UniqueWordListDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.zodiacengine.entities.cipherkey.CipherKeyChromosome;
import com.ciphertool.zodiacengine.entities.cipherkey.CipherKeyGene;

public class CipherKeyChromosomePrinter implements ChromosomePrinter {
	private Logger log = Logger.getLogger(getClass());

	private int minWordLength = 4;
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
			populateMap(rootNode, lowerCaseWord, lowerCaseWord);
		}
	}

	protected void populateMap(IndexNode currentNode, String wordPart, String terminal) {
		Character firstLetter = wordPart.charAt(0);

		if (wordPart.length() == 1) {
			if (currentNode.containsChild(firstLetter)) {
				currentNode.getChild(firstLetter).setTerminal(terminal);
			} else {
				currentNode.putChild(firstLetter, new IndexNode(terminal));
			}
		} else {
			if (!currentNode.containsChild(firstLetter)) {
				currentNode.putChild(firstLetter, new IndexNode());
			}

			populateMap(currentNode.getChild(firstLetter), wordPart.substring(1), terminal);
		}
	}

	protected String findLongestWordMatch(IndexNode node, int index, String solutionString, String longestMatch) {
		if (index >= solutionString.length()) {
			return longestMatch;
		}

		Character currentChar = solutionString.charAt(index);

		if (node.getTerminal() != null) {
			longestMatch = node.getTerminal();
		}

		if (node.containsChild(currentChar)) {
			return findLongestWordMatch(node.getChild(currentChar), ++index, solutionString, longestMatch);
		} else {
			return longestMatch;
		}
	}

	private class IndexNode {
		private String terminal;
		private Map<Character, IndexNode> letterMap = new HashMap<Character, IndexNode>();

		/**
		 * Default no-args constructor
		 */
		public IndexNode() {
		}

		/**
		 * @param terminal
		 */
		public IndexNode(String terminal) {
			super();
			this.terminal = terminal;
		}

		public boolean containsChild(Character c) {
			return this.letterMap.containsKey(c);
		}

		public IndexNode getChild(Character c) {
			return this.letterMap.get(c);
		}

		public void putChild(Character c, IndexNode child) {
			this.letterMap.put(c, child);
		}

		/**
		 * @return the terminal
		 */
		public String getTerminal() {
			return terminal;
		}

		/**
		 * @param terminal
		 *            the terminal to set
		 */
		public void setTerminal(String terminal) {
			this.terminal = terminal;
		}
	}

	@Override
	public String print(Chromosome chromosome) {
		Map<Integer, List<Match>> matchMap = new HashMap<Integer, List<Match>>();

		int lastRowBegin = (((CipherKeyChromosome) chromosome).getCipher().getColumns() * (((CipherKeyChromosome) chromosome)
				.getCipher().getRows() - 1));

		String fullSolutionString = getSolutionAsString((CipherKeyChromosome) chromosome);

		String currentSolutionString = fullSolutionString.substring(0, lastRowBegin);

		String longestMatch;
		for (int i = 0; i < currentSolutionString.length(); i++) {
			longestMatch = findLongestWordMatch(rootNode, i, currentSolutionString, null);

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
				if (nonOverlapping(beginPos, rootNodes)) {
					break;
				}

				for (Match match : matchMap.get(beginPos)) {
					rootNodes.add(new MatchNode(match));
				}
			}
		}

		List<String> branches = new ArrayList<String>();
		for (MatchNode node : rootNodes) {
			findOverlappingChildren(node.getSelf().getEndPos() + 1, lastRowBegin, matchMap, node);

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

		StringBuffer sb = new StringBuffer();

		sb.append("Solution [id=" + chromosome.getId() + ", cipherId="
				+ ((CipherKeyChromosome) chromosome).getCipher().getId() + ", fitness="
				+ String.format("%1$,.2f", chromosome.getFitness()) + ", age=" + chromosome.getAge()
				+ ", numberOfChildren=" + chromosome.getNumberOfChildren() + ", evaluationNeeded="
				+ chromosome.isEvaluationNeeded() + "]\n");

		List<String> words = new ArrayList<String>();

		// In the off chance that no words were found at all
		if (!bestBranch.isEmpty()) {
			words.addAll(Arrays.asList(bestBranch.split(", ")));
		}

		String word = words.isEmpty() ? null : words.get(0);
		for (int i = 0; i < fullSolutionString.length(); i++) {
			if (!words.isEmpty() && i < lastRowBegin && word.equals(fullSolutionString.substring(i, i + word.length()))) {
				sb.append("[");

				for (int j = 0; j < word.length(); j++) {
					if (j > 0) {
						sb.append(" ");
					}

					sb.append(fullSolutionString.charAt(i + j));

					if (j < word.length() - 1) {
						sb.append(" ");
					} else if (j == word.length() - 1) {
						sb.append("]");
					}

					/*
					 * Print a newline if we are at the end of the row. Add 1 to the index so the modulus function
					 * doesn't break.
					 */
					if (((i + j + 1) % ((CipherKeyChromosome) chromosome).getCipher().getColumns()) == 0) {
						sb.append("\n");
					} else {
						sb.append(" ");
					}

					// Prevent ArrayIndexOutOfBoundsException
					if (i + j >= fullSolutionString.length()) {
						break;
					}
				}

				i += word.length() - 1;

				words.remove(0);

				if (!words.isEmpty()) {
					word = words.get(0);
				}

				continue;
			}

			sb.append(" ");
			sb.append(fullSolutionString.charAt(i));
			sb.append(" ");

			/*
			 * Print a newline if we are at the end of the row. Add 1 to the index so the modulus function doesn't
			 * break.
			 */
			if (((i + 1) % ((CipherKeyChromosome) chromosome).getCipher().getColumns()) == 0) {
				sb.append("\n");
			} else {
				sb.append(" ");
			}
		}

		return sb.toString();
	}

	protected void findOverlappingChildren(int beginPos, int endPos, Map<Integer, List<Match>> matchMap,
			MatchNode currentNode) {
		int i;
		MatchNode newNode;
		for (i = beginPos; i < endPos; i++) {
			if (i <= currentNode.getSelf().getEndPos()) {
				continue;
			}

			if (matchMap.containsKey(i)) {
				if (nonOverlapping(i, currentNode.getChildren())) {
					break;
				}

				Match bestMatch = null;
				for (Match match : matchMap.get(i)) {
					if (bestMatch == null || match.getWord().length() > bestMatch.getWord().length()) {
						bestMatch = match;
					}
				}

				newNode = new MatchNode(bestMatch);
				currentNode.addChild(newNode);
				currentNode = newNode;
				// findOverlappingChildren(newNode.getSelf().getEndPos() + 1, endPos, matchMap, newNode);
			}
		}
	}

	protected boolean nonOverlapping(int beginPos, List<MatchNode> rootNodes) {
		for (MatchNode node : rootNodes) {
			if (beginPos > node.getSelf().getEndPos()) {
				return true;
			}
		}

		return false;
	}

	protected String getSolutionAsString(CipherKeyChromosome chromosome) {
		StringBuffer sb = new StringBuffer();

		if (null == ((CipherKeyChromosome) chromosome).getCipher()) {
			throw new IllegalStateException(
					"Called getSolutionAsString(), but found a null Cipher.  Cannot create valid solution string unless the Cipher is properly set.");
		}

		CipherKeyGene nextPlaintext = null;
		int actualSize = ((CipherKeyChromosome) chromosome).getCipher().getCiphertextCharacters().size();

		for (int i = 0; i < actualSize; i++) {
			nextPlaintext = (CipherKeyGene) chromosome.getGenes().get(
					((CipherKeyChromosome) chromosome).getCipher().getCiphertextCharacters().get(i).getValue());

			sb.append(nextPlaintext.getValue());
		}

		return sb.toString();
	}

	private class Match {
		private int beginPos;
		private int endPos;
		private String word;

		/**
		 * @param beginPos
		 * @param endPos
		 * @param word
		 */
		public Match(int beginPos, int endPos, String word) {
			this.beginPos = beginPos;
			this.endPos = endPos;
			this.word = word;
		}

		public final int getEndPos() {
			return endPos;
		}

		public final String getWord() {
			return word;
		}

		@Override
		public String toString() {
			return "Match [beginPos=" + beginPos + ", endPos=" + endPos + ", word=" + word + "]";
		}
	}

	private class MatchNode {
		private Match self;
		private List<MatchNode> children = new ArrayList<MatchNode>();

		/**
		 * @param self
		 */
		public MatchNode(Match self) {
			this.self = self;
		}

		public final Match getSelf() {
			return self;
		}

		public List<MatchNode> getChildren() {
			return Collections.unmodifiableList(children);
		}

		public void addChild(MatchNode child) {
			this.children.add(child);
		}

		public List<String> printBranches() {
			List<String> branches = new ArrayList<String>();

			walk(branches, this.self.getWord());

			return branches;
		}

		private void walk(List<String> branches, String branch) {
			if (!this.getChildren().isEmpty()) {
				for (MatchNode child : this.children) {
					child.walk(branches, branch + ", " + child.self.getWord());
				}
			}

			branches.add(branch);
		}

		@Override
		public String toString() {
			return "MatchNode [self=" + self + "]";
		}
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
}