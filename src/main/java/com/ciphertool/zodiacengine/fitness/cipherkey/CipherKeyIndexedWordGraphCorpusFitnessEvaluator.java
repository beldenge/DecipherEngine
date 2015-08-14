package com.ciphertool.zodiacengine.fitness.cipherkey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.cipherkey.CipherKeyChromosome;
import com.ciphertool.zodiacengine.entities.cipherkey.CipherKeyGene;

/**
 * Copyright 2015 George Belden
 * 
 * This file is part of ZodiacEngine.
 * 
 * ZodiacEngine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ZodiacEngine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with ZodiacEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

public class CipherKeyIndexedWordGraphCorpusFitnessEvaluator implements FitnessEvaluator {
	protected Cipher cipher;
	private int MIN_WORD_LENGTH = 4;
	private static List<Word> topWords = new ArrayList<Word>();

	static {
		topWords.add(new Word(new WordId("i", null)));
		topWords.add(new Word(new WordId("like", null)));
		topWords.add(new Word(new WordId("killing", null)));
		topWords.add(new Word(new WordId("people", null)));
		topWords.add(new Word(new WordId("because", null)));
		topWords.add(new Word(new WordId("it", null)));
		topWords.add(new Word(new WordId("is", null)));
		topWords.add(new Word(new WordId("so", null)));
		topWords.add(new Word(new WordId("much", null)));
		topWords.add(new Word(new WordId("fun", null)));
		topWords.add(new Word(new WordId("more", null)));
		topWords.add(new Word(new WordId("than", null)));
		topWords.add(new Word(new WordId("wild", null)));
		topWords.add(new Word(new WordId("game", null)));
		topWords.add(new Word(new WordId("the", null)));
		topWords.add(new Word(new WordId("forrest", null)));
		topWords.add(new Word(new WordId("because", null)));
		topWords.add(new Word(new WordId("man", null)));
		topWords.add(new Word(new WordId("most", null)));
		topWords.add(new Word(new WordId("moat", null))); // this misspelling is repeated twice
		topWords.add(new Word(new WordId("dangerous", null)));
		topWords.add(new Word(new WordId("animal", null)));
		topWords.add(new Word(new WordId("of", null)));
		topWords.add(new Word(new WordId("all", null)));
		topWords.add(new Word(new WordId("to", null)));
		topWords.add(new Word(new WordId("all", null)));
		topWords.add(new Word(new WordId("kill", null)));
		topWords.add(new Word(new WordId("something", null)));
		topWords.add(new Word(new WordId("gives", null)));
		topWords.add(new Word(new WordId("me", null)));
		topWords.add(new Word(new WordId("thrilling", null)));
		topWords.add(new Word(new WordId("experience", null)));
		topWords.add(new Word(new WordId("even", null)));
		topWords.add(new Word(new WordId("better", null)));
		topWords.add(new Word(new WordId("than", null)));
		topWords.add(new Word(new WordId("getting", null)));
		topWords.add(new Word(new WordId("your", null)));
		topWords.add(new Word(new WordId("rocks", null)));
		topWords.add(new Word(new WordId("off", null)));
		topWords.add(new Word(new WordId("with", null)));
		topWords.add(new Word(new WordId("a", null)));
		topWords.add(new Word(new WordId("girl", null)));
		topWords.add(new Word(new WordId("best", null)));
		topWords.add(new Word(new WordId("part", null)));
		topWords.add(new Word(new WordId("that", null)));
		topWords.add(new Word(new WordId("when", null)));
		topWords.add(new Word(new WordId("die", null)));
		topWords.add(new Word(new WordId("will", null)));
		topWords.add(new Word(new WordId("be", null)));
		topWords.add(new Word(new WordId("reborn", null)));
		topWords.add(new Word(new WordId("in", null)));
		topWords.add(new Word(new WordId("paradise", null)));
		topWords.add(new Word(new WordId("and", null)));
		topWords.add(new Word(new WordId("killed", null)));
		topWords.add(new Word(new WordId("become", null)));
		topWords.add(new Word(new WordId("my", null)));
		topWords.add(new Word(new WordId("slaves", null)));
		topWords.add(new Word(new WordId("not", null)));
		topWords.add(new Word(new WordId("give", null)));
		topWords.add(new Word(new WordId("you", null)));
		topWords.add(new Word(new WordId("name", null)));
		topWords.add(new Word(new WordId("try", null)));
		topWords.add(new Word(new WordId("slow", null)));
		topWords.add(new Word(new WordId("down", null)));
		topWords.add(new Word(new WordId("or", null)));
		topWords.add(new Word(new WordId("stop", null)));
		topWords.add(new Word(new WordId("collecting", null)));
		topWords.add(new Word(new WordId("for", null)));
		topWords.add(new Word(new WordId("afterlife", null)));
	}

	private IndexNode rootNode = new IndexNode();

	@PostConstruct
	public void init() {
		String lowerCaseWord;
		for (Word word : topWords) {
			if (word.getId().getWord().length() < MIN_WORD_LENGTH) {
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
	public Double evaluate(Chromosome chromosome) {
		Map<Integer, List<Match>> matchMap = new HashMap<Integer, List<Match>>();

		int lastRowBegin = (cipher.getColumns() * (cipher.getRows() - 1));

		String currentSolutionString = getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

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

		if (null == this.cipher) {
			throw new IllegalStateException(
					"Called getSolutionAsString(), but found a null Cipher.  Cannot create valid solution string unless the Cipher is properly set.");
		}

		CipherKeyGene nextPlaintext = null;
		int actualSize = this.cipher.getCiphertextCharacters().size();

		for (int i = 0; i < actualSize; i++) {
			nextPlaintext = (CipherKeyGene) chromosome.getGenes().get(
					this.cipher.getCiphertextCharacters().get(i).getValue());

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

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;
	}

	@Override
	public String getDisplayName() {
		return "Cipher Key Indexed Word Graph Corpus";
	}
}
