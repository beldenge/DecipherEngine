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

package com.ciphertool.engine.printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.ChromosomePrinter;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.wordgraph.IndexNode;
import com.ciphertool.sentencebuilder.wordgraph.Match;
import com.ciphertool.sentencebuilder.wordgraph.MatchNode;
import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.cipherkey.CipherKeyChromosome;

public class CipherKeyCorpusChromosomePrinter implements ChromosomePrinter {
	private int minWordLength;
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
		topWords.add(new Word(new WordId("animal", null)));
		topWords.add(new Word(new WordId("of", null)));
		topWords.add(new Word(new WordId("all", null)));
		topWords.add(new Word(new WordId("to", null)));
		topWords.add(new Word(new WordId("kill", null)));
		topWords.add(new Word(new WordId("something", null)));
		topWords.add(new Word(new WordId("gives", null)));
		topWords.add(new Word(new WordId("me", null)));
		topWords.add(new Word(new WordId("thrilling", null)));
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

		// 2-grams
		topWords.add(new Word(new WordId("ilike", null)));
		topWords.add(new Word(new WordId("likekilling", null)));
		topWords.add(new Word(new WordId("killingpeople", null)));
		topWords.add(new Word(new WordId("becauseit", null)));
		topWords.add(new Word(new WordId("itis", null)));
		topWords.add(new Word(new WordId("isso", null)));
		topWords.add(new Word(new WordId("somuch", null)));
		topWords.add(new Word(new WordId("muchfun", null)));
		topWords.add(new Word(new WordId("ismore", null)));
		topWords.add(new Word(new WordId("morefun", null)));
		topWords.add(new Word(new WordId("funthan", null)));
		topWords.add(new Word(new WordId("thankilling", null)));
		topWords.add(new Word(new WordId("wildgame", null)));
		topWords.add(new Word(new WordId("inthe", null)));
		topWords.add(new Word(new WordId("theforrest", null)));
		topWords.add(new Word(new WordId("becauseman", null)));
		topWords.add(new Word(new WordId("manis", null)));
		topWords.add(new Word(new WordId("isthe", null)));
		topWords.add(new Word(new WordId("themoat", null)));
		topWords.add(new Word(new WordId("ofall", null)));
		topWords.add(new Word(new WordId("tokill", null)));
		topWords.add(new Word(new WordId("killsomething", null)));
		topWords.add(new Word(new WordId("givesme", null)));
		topWords.add(new Word(new WordId("methe", null)));
		topWords.add(new Word(new WordId("iseven", null)));
		topWords.add(new Word(new WordId("evenbetter", null)));
		topWords.add(new Word(new WordId("betterthan", null)));
		topWords.add(new Word(new WordId("thangetting", null)));
		topWords.add(new Word(new WordId("gettingyour", null)));
		topWords.add(new Word(new WordId("rocksoff", null)));
		topWords.add(new Word(new WordId("offwith", null)));
		topWords.add(new Word(new WordId("witha", null)));
		topWords.add(new Word(new WordId("agirl", null)));
		topWords.add(new Word(new WordId("thebest", null)));
		topWords.add(new Word(new WordId("bestpart", null)));
		topWords.add(new Word(new WordId("partof", null)));
		topWords.add(new Word(new WordId("ofit", null)));
		topWords.add(new Word(new WordId("wheni", null)));
		topWords.add(new Word(new WordId("idie", null)));
		topWords.add(new Word(new WordId("diei", null)));
		topWords.add(new Word(new WordId("iwill", null)));
		topWords.add(new Word(new WordId("willbe", null)));
		topWords.add(new Word(new WordId("bereborn", null)));
		topWords.add(new Word(new WordId("rebornin", null)));
		topWords.add(new Word(new WordId("allthe", null)));
		topWords.add(new Word(new WordId("thei", null)));
		topWords.add(new Word(new WordId("ihave", null)));
		topWords.add(new Word(new WordId("havekilled", null)));
		topWords.add(new Word(new WordId("willbecome", null)));
		topWords.add(new Word(new WordId("becomemy", null)));
		topWords.add(new Word(new WordId("myslave", null)));
		topWords.add(new Word(new WordId("willnot", null)));
		topWords.add(new Word(new WordId("notgive", null)));
		topWords.add(new Word(new WordId("giveyou", null)));
		topWords.add(new Word(new WordId("youmy", null)));
		topWords.add(new Word(new WordId("myname", null)));
		topWords.add(new Word(new WordId("namebecause", null)));
		topWords.add(new Word(new WordId("becauseyou", null)));
		topWords.add(new Word(new WordId("youwill", null)));
		topWords.add(new Word(new WordId("willtry", null)));
		topWords.add(new Word(new WordId("tryto", null)));
		topWords.add(new Word(new WordId("downor", null)));
		topWords.add(new Word(new WordId("orstop", null)));
		topWords.add(new Word(new WordId("stopmy", null)));
		topWords.add(new Word(new WordId("collectingof", null)));
		topWords.add(new Word(new WordId("ofslaves", null)));
		topWords.add(new Word(new WordId("formy", null)));

		// 3-grams
		topWords.add(new Word(new WordId("becauseitis", null)));
		topWords.add(new Word(new WordId("itisso", null)));
		topWords.add(new Word(new WordId("issomuch", null)));
		topWords.add(new Word(new WordId("somuchfun", null)));
		topWords.add(new Word(new WordId("itismore", null)));
		topWords.add(new Word(new WordId("ismorefun", null)));
		topWords.add(new Word(new WordId("morefunthan", null)));
		topWords.add(new Word(new WordId("givesmethe", null)));
		topWords.add(new Word(new WordId("itiseven", null)));
		topWords.add(new Word(new WordId("isevenbetter", null)));
		topWords.add(new Word(new WordId("evenbetterthan", null)));
		topWords.add(new Word(new WordId("withagirl", null)));
		topWords.add(new Word(new WordId("thebestpart", null)));
		topWords.add(new Word(new WordId("bestpartof", null)));
		topWords.add(new Word(new WordId("partofit", null)));
		topWords.add(new Word(new WordId("whenidie", null)));
		topWords.add(new Word(new WordId("iwillbe", null)));
		topWords.add(new Word(new WordId("ihavekilled", null)));
		topWords.add(new Word(new WordId("iwillnot", null)));
		topWords.add(new Word(new WordId("willnotgive", null)));
		topWords.add(new Word(new WordId("notgiveyou", null)));
		topWords.add(new Word(new WordId("giveyoumy", null)));
		topWords.add(new Word(new WordId("youmyname", null)));
		topWords.add(new Word(new WordId("becauseyouwill", null)));
		topWords.add(new Word(new WordId("willtryto", null)));

		// 4-grams
		topWords.add(new Word(new WordId("issomuchfun", null)));
		topWords.add(new Word(new WordId("becauseitisso", null)));
		topWords.add(new Word(new WordId("itissomuch", null)));
		topWords.add(new Word(new WordId("ismorefunthan", null)));
		topWords.add(new Word(new WordId("isevenbetterthan", null)));
		topWords.add(new Word(new WordId("thebestpartof", null)));
		topWords.add(new Word(new WordId("bestpartofit", null)));
		topWords.add(new Word(new WordId("iwillnotgive", null)));
		topWords.add(new Word(new WordId("willnotgiveyou", null)));

		// 5-grams
		topWords.add(new Word(new WordId("becauseitissomuch", null)));
		topWords.add(new Word(new WordId("itissomuchfun", null)));
		topWords.add(new Word(new WordId("thebestpartofit", null)));
		topWords.add(new Word(new WordId("iwillnotgiveyou", null)));
	}

	private IndexNode rootNode = new IndexNode();

	@PostConstruct
	public void init() {
		String lowerCaseWord;
		for (Word word : topWords) {
			if (word.getId().getWord().length() < minWordLength) {
				continue;
			}

			lowerCaseWord = word.getId().getWord().toLowerCase();
			WordGraphUtils.populateMap(rootNode, lowerCaseWord);
		}
	}

	@Override
	public String print(Chromosome chromosome) {
		Map<Integer, List<Match>> matchMap = new HashMap<Integer, List<Match>>();

		int lastRowBegin = (((CipherKeyChromosome) chromosome).getCipher().getColumns() * (((CipherKeyChromosome) chromosome)
				.getCipher().getRows() - 1));

		String fullSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome);

		String currentSolutionString = fullSolutionString.substring(0, lastRowBegin);

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

	/**
	 * @param minWordLength
	 *            the minWordLength to set
	 */
	@Required
	public void setMinWordLength(int minWordLength) {
		this.minWordLength = minWordLength;
	}
}
