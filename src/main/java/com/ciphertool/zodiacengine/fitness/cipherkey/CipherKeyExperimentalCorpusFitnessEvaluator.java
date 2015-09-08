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
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.wordgraph.IndexNode;
import com.ciphertool.sentencebuilder.wordgraph.Match;
import com.ciphertool.sentencebuilder.wordgraph.MatchNode;
import com.ciphertool.zodiacengine.common.WordGraphUtils;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.cipherkey.CipherKeyChromosome;

public class CipherKeyExperimentalCorpusFitnessEvaluator implements FitnessEvaluator {
	private Logger log = Logger.getLogger(getClass());

	private static final double FREQUENCY_DIFFERENCE_THRESHOLD = 0.05;
	private static final double MATCHES_BONUS = 1.033;

	protected Cipher cipher;
	private int minWordLength;
	private static List<Word> topWords = new ArrayList<Word>();

	private Map<Character, Double> expectedLetterFrequencies;

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
	public Double evaluate(Chromosome chromosome) {
		Map<Integer, List<Match>> matchMap = new HashMap<Integer, List<Match>>();

		int lastRowBegin = (cipher.getColumns() * (cipher.getRows() - 1));

		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(
				0, lastRowBegin);

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
		// branches = Arrays
		// .asList(new String[] {
		// "ilike, people, becauseitissomuch, ismorefunthan, killing, game, inthe, forrest, becauseman, isthe, moat, ofall, tokill, methe, givesmethe, moat, thrilling, isevenbetterthan, gettingyour, rocksoff, witha, thebestpartofit, wheni, iwillbe, rebornin, allthe, ihavekilled, willbecome, myslave, iwillnot, youmyname, becauseyouwill, tryto, downor, stopmy, collectingof, slaves, formy, afterlife"
		// });

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

		if (log.isDebugEnabled()) {
			log.debug("Best branch: " + bestBranch);
		}

		double fitness = highestScore + Math.pow(MATCHES_BONUS, bestBranch.replaceAll("[^a-z]", "").length());

		Map<Character, Double> actualLetterFrequencies = new HashMap<Character, Double>();

		/*
		 * Initialize the actualLetterFrequencies Map
		 */
		for (Character letter : expectedLetterFrequencies.keySet()) {
			actualLetterFrequencies.put(letter, 0.0);
		}

		/*
		 * Don't use the last row when calculating the oneCharacterFrequency for this evaluator
		 */
		Double oneCharacterFrequency = 1.0 / (double) currentSolutionString.length();
		Double currentFrequency = 0.0;

		for (int i = 0; i < currentSolutionString.length(); i++) {
			Character currentCharacter = currentSolutionString.charAt(i);

			currentFrequency = actualLetterFrequencies.get(currentCharacter);
			if (currentFrequency != null) {
				actualLetterFrequencies.put(currentCharacter, currentFrequency + oneCharacterFrequency);
			} else {
				log.debug("Found non-alpha character in Plaintext: " + currentCharacter);
			}
		}

		Double difference = 0.0;
		Double lengthRatio = ((double) bestBranch.replaceAll("[^a-z]", "").length() / (double) currentSolutionString
				.length());

		for (Character letter : expectedLetterFrequencies.keySet()) {
			difference = Math.abs(expectedLetterFrequencies.get(letter) - actualLetterFrequencies.get(letter));

			if (difference > FREQUENCY_DIFFERENCE_THRESHOLD) {
				/*
				 * Scale the difference by the current solution's length, so that the frequencyFactor doesn't have as
				 * much of an effect in early generations
				 */
				double frequencyFactor = (1 - ((difference - FREQUENCY_DIFFERENCE_THRESHOLD) * lengthRatio));

				fitness = fitness * frequencyFactor;
			}
		}

		return Double.valueOf(fitness);
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;
	}

	/**
	 * @param minWordLength
	 *            the minWordLength to set
	 */
	@Required
	public void setMinWordLength(int minWordLength) {
		this.minWordLength = minWordLength;
	}

	/**
	 * @param expectedLetterFrequencies
	 *            the expectedLetterFrequencies to set
	 */
	@Required
	public void setExpectedLetterFrequencies(Map<Character, Double> expectedLetterFrequencies) {
		this.expectedLetterFrequencies = expectedLetterFrequencies;
	}

	@Override
	public String getDisplayName() {
		return "Cipher Key Experimental Corpus";
	}
}
