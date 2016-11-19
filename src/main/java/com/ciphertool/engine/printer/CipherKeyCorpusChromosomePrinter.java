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

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.ChromosomePrinter;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.sherlock.entities.Word;
import com.ciphertool.sherlock.wordgraph.IndexNode;
import com.ciphertool.sherlock.wordgraph.Match;
import com.ciphertool.sherlock.wordgraph.MatchNode;

public class CipherKeyCorpusChromosomePrinter implements ChromosomePrinter {
	private int					minWordLength;
	private static List<Word>	topWords	= new ArrayList<Word>();

	static {
		topWords.add(new Word("i", null));
		topWords.add(new Word("like", null));
		topWords.add(new Word("killing", null));
		topWords.add(new Word("people", null));
		topWords.add(new Word("because", null));
		topWords.add(new Word("it", null));
		topWords.add(new Word("is", null));
		topWords.add(new Word("so", null));
		topWords.add(new Word("much", null));
		topWords.add(new Word("fun", null));
		topWords.add(new Word("more", null));
		topWords.add(new Word("than", null));
		topWords.add(new Word("wild", null));
		topWords.add(new Word("game", null));
		topWords.add(new Word("the", null));
		topWords.add(new Word("forrest", null));
		topWords.add(new Word("because", null));
		topWords.add(new Word("man", null));
		topWords.add(new Word("most", null));
		topWords.add(new Word("moat", null)); // this misspelling is repeated twice
		topWords.add(new Word("animal", null));
		topWords.add(new Word("of", null));
		topWords.add(new Word("all", null));
		topWords.add(new Word("to", null));
		topWords.add(new Word("kill", null));
		topWords.add(new Word("something", null));
		topWords.add(new Word("gives", null));
		topWords.add(new Word("me", null));
		topWords.add(new Word("thrilling", null));
		topWords.add(new Word("even", null));
		topWords.add(new Word("better", null));
		topWords.add(new Word("than", null));
		topWords.add(new Word("getting", null));
		topWords.add(new Word("your", null));
		topWords.add(new Word("rocks", null));
		topWords.add(new Word("off", null));
		topWords.add(new Word("with", null));
		topWords.add(new Word("a", null));
		topWords.add(new Word("girl", null));
		topWords.add(new Word("best", null));
		topWords.add(new Word("part", null));
		topWords.add(new Word("that", null));
		topWords.add(new Word("when", null));
		topWords.add(new Word("die", null));
		topWords.add(new Word("will", null));
		topWords.add(new Word("be", null));
		topWords.add(new Word("reborn", null));
		topWords.add(new Word("in", null));
		topWords.add(new Word("paradise", null));
		topWords.add(new Word("and", null));
		topWords.add(new Word("killed", null));
		topWords.add(new Word("become", null));
		topWords.add(new Word("my", null));
		topWords.add(new Word("slaves", null));
		topWords.add(new Word("not", null));
		topWords.add(new Word("give", null));
		topWords.add(new Word("you", null));
		topWords.add(new Word("name", null));
		topWords.add(new Word("try", null));
		topWords.add(new Word("slow", null));
		topWords.add(new Word("down", null));
		topWords.add(new Word("or", null));
		topWords.add(new Word("stop", null));
		topWords.add(new Word("collecting", null));
		topWords.add(new Word("for", null));
		topWords.add(new Word("afterlife", null));

		// 2-grams
		topWords.add(new Word("ilike", null));
		topWords.add(new Word("likekilling", null));
		topWords.add(new Word("killingpeople", null));
		topWords.add(new Word("becauseit", null));
		topWords.add(new Word("itis", null));
		topWords.add(new Word("isso", null));
		topWords.add(new Word("somuch", null));
		topWords.add(new Word("muchfun", null));
		topWords.add(new Word("ismore", null));
		topWords.add(new Word("morefun", null));
		topWords.add(new Word("funthan", null));
		topWords.add(new Word("thankilling", null));
		topWords.add(new Word("wildgame", null));
		topWords.add(new Word("inthe", null));
		topWords.add(new Word("theforrest", null));
		topWords.add(new Word("becauseman", null));
		topWords.add(new Word("manis", null));
		topWords.add(new Word("isthe", null));
		topWords.add(new Word("themoat", null));
		topWords.add(new Word("ofall", null));
		topWords.add(new Word("tokill", null));
		topWords.add(new Word("killsomething", null));
		topWords.add(new Word("givesme", null));
		topWords.add(new Word("methe", null));
		topWords.add(new Word("iseven", null));
		topWords.add(new Word("evenbetter", null));
		topWords.add(new Word("betterthan", null));
		topWords.add(new Word("thangetting", null));
		topWords.add(new Word("gettingyour", null));
		topWords.add(new Word("rocksoff", null));
		topWords.add(new Word("offwith", null));
		topWords.add(new Word("witha", null));
		topWords.add(new Word("agirl", null));
		topWords.add(new Word("thebest", null));
		topWords.add(new Word("bestpart", null));
		topWords.add(new Word("partof", null));
		topWords.add(new Word("ofit", null));
		topWords.add(new Word("wheni", null));
		topWords.add(new Word("idie", null));
		topWords.add(new Word("diei", null));
		topWords.add(new Word("iwill", null));
		topWords.add(new Word("willbe", null));
		topWords.add(new Word("bereborn", null));
		topWords.add(new Word("rebornin", null));
		topWords.add(new Word("allthe", null));
		topWords.add(new Word("thei", null));
		topWords.add(new Word("ihave", null));
		topWords.add(new Word("havekilled", null));
		topWords.add(new Word("willbecome", null));
		topWords.add(new Word("becomemy", null));
		topWords.add(new Word("myslave", null));
		topWords.add(new Word("willnot", null));
		topWords.add(new Word("notgive", null));
		topWords.add(new Word("giveyou", null));
		topWords.add(new Word("youmy", null));
		topWords.add(new Word("myname", null));
		topWords.add(new Word("namebecause", null));
		topWords.add(new Word("becauseyou", null));
		topWords.add(new Word("youwill", null));
		topWords.add(new Word("willtry", null));
		topWords.add(new Word("tryto", null));
		topWords.add(new Word("downor", null));
		topWords.add(new Word("orstop", null));
		topWords.add(new Word("stopmy", null));
		topWords.add(new Word("collectingof", null));
		topWords.add(new Word("ofslaves", null));
		topWords.add(new Word("formy", null));

		// 3-grams
		topWords.add(new Word("becauseitis", null));
		topWords.add(new Word("itisso", null));
		topWords.add(new Word("issomuch", null));
		topWords.add(new Word("somuchfun", null));
		topWords.add(new Word("itismore", null));
		topWords.add(new Word("ismorefun", null));
		topWords.add(new Word("morefunthan", null));
		topWords.add(new Word("givesmethe", null));
		topWords.add(new Word("itiseven", null));
		topWords.add(new Word("isevenbetter", null));
		topWords.add(new Word("evenbetterthan", null));
		topWords.add(new Word("withagirl", null));
		topWords.add(new Word("thebestpart", null));
		topWords.add(new Word("bestpartof", null));
		topWords.add(new Word("partofit", null));
		topWords.add(new Word("whenidie", null));
		topWords.add(new Word("iwillbe", null));
		topWords.add(new Word("ihavekilled", null));
		topWords.add(new Word("iwillnot", null));
		topWords.add(new Word("willnotgive", null));
		topWords.add(new Word("notgiveyou", null));
		topWords.add(new Word("giveyoumy", null));
		topWords.add(new Word("youmyname", null));
		topWords.add(new Word("becauseyouwill", null));
		topWords.add(new Word("willtryto", null));

		// 4-grams
		topWords.add(new Word("issomuchfun", null));
		topWords.add(new Word("becauseitisso", null));
		topWords.add(new Word("itissomuch", null));
		topWords.add(new Word("ismorefunthan", null));
		topWords.add(new Word("isevenbetterthan", null));
		topWords.add(new Word("thebestpartof", null));
		topWords.add(new Word("bestpartofit", null));
		topWords.add(new Word("iwillnotgive", null));
		topWords.add(new Word("willnotgiveyou", null));

		// 5-grams
		topWords.add(new Word("becauseitissomuch", null));
		topWords.add(new Word("itissomuchfun", null));
		topWords.add(new Word("thebestpartofit", null));
		topWords.add(new Word("iwillnotgiveyou", null));
	}

	private IndexNode rootNode = new IndexNode();

	@PostConstruct
	public void init() {
		String lowerCaseWord;
		for (Word word : topWords) {
			if (word.getWord().length() < minWordLength) {
				continue;
			}

			lowerCaseWord = word.getWord().toLowerCase();
			WordGraphUtils.populateMap(rootNode, lowerCaseWord);
		}
	}

	@Override
	public String print(Chromosome chromosome) {
		Map<Integer, Match> matchMap = new HashMap<Integer, Match>();

		int lastRowBegin = (((CipherKeyChromosome) chromosome).getCipher().getColumns()
				* (((CipherKeyChromosome) chromosome).getCipher().getRows() - 1));

		String fullSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome);

		String currentSolutionString = fullSolutionString.substring(0, lastRowBegin);

		String longestMatch;
		for (int i = 0; i < currentSolutionString.length(); i++) {
			longestMatch = WordGraphUtils.findLongestWordMatch(rootNode, 0, currentSolutionString.substring(i), null);

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

		StringBuilder sb = new StringBuilder();

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
			if (!words.isEmpty() && i < lastRowBegin && word.equals(fullSolutionString.substring(i, i
					+ word.length()))) {
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
