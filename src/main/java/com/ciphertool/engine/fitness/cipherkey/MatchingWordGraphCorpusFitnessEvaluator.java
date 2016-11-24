/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with DecipherEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.fitness.cipherkey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.entities.Word;
import com.ciphertool.sherlock.enumerations.TerminalType;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.wordgraph.Match;
import com.ciphertool.sherlock.wordgraph.MatchNode;

public class MatchingWordGraphCorpusFitnessEvaluator implements FitnessEvaluator {
	private Logger				log				= LoggerFactory.getLogger(getClass());

	protected Cipher			cipher;
	private static List<Word>	topOneGrams		= new ArrayList<Word>();
	private static List<Word>	topTwoGrams		= new ArrayList<Word>();
	private static List<Word>	topThreeGrams	= new ArrayList<Word>();
	private static List<Word>	topFourGrams	= new ArrayList<Word>();
	private static List<Word>	topFiveGrams	= new ArrayList<Word>();

	private MarkovModel			markovModel;

	int							lastRowBegin;

	static {
		topOneGrams.add(new Word("i", null));
		topOneGrams.add(new Word("like", null));
		topOneGrams.add(new Word("killing", null));
		topOneGrams.add(new Word("people", null));
		topOneGrams.add(new Word("because", null));
		topOneGrams.add(new Word("it", null));
		topOneGrams.add(new Word("is", null));
		topOneGrams.add(new Word("so", null));
		topOneGrams.add(new Word("much", null));
		topOneGrams.add(new Word("fun", null));
		topOneGrams.add(new Word("more", null));
		topOneGrams.add(new Word("than", null));
		topOneGrams.add(new Word("wild", null));
		topOneGrams.add(new Word("game", null));
		topOneGrams.add(new Word("the", null));
		topOneGrams.add(new Word("forrest", null));
		topOneGrams.add(new Word("because", null));
		topOneGrams.add(new Word("man", null));
		topOneGrams.add(new Word("most", null));
		topOneGrams.add(new Word("moat", null)); // this misspelling is repeated twice
		topOneGrams.add(new Word("animal", null));
		topOneGrams.add(new Word("of", null));
		topOneGrams.add(new Word("all", null));
		topOneGrams.add(new Word("to", null));
		topOneGrams.add(new Word("kill", null));
		topOneGrams.add(new Word("something", null));
		topOneGrams.add(new Word("gives", null));
		topOneGrams.add(new Word("me", null));
		topOneGrams.add(new Word("thrilling", null));
		topOneGrams.add(new Word("even", null));
		topOneGrams.add(new Word("better", null));
		topOneGrams.add(new Word("than", null));
		topOneGrams.add(new Word("getting", null));
		topOneGrams.add(new Word("your", null));
		topOneGrams.add(new Word("rocks", null));
		topOneGrams.add(new Word("off", null));
		topOneGrams.add(new Word("with", null));
		topOneGrams.add(new Word("a", null));
		topOneGrams.add(new Word("girl", null));
		topOneGrams.add(new Word("best", null));
		topOneGrams.add(new Word("part", null));
		topOneGrams.add(new Word("that", null));
		topOneGrams.add(new Word("when", null));
		topOneGrams.add(new Word("die", null));
		topOneGrams.add(new Word("will", null));
		topOneGrams.add(new Word("be", null));
		topOneGrams.add(new Word("reborn", null));
		topOneGrams.add(new Word("in", null));
		topOneGrams.add(new Word("paradise", null));
		topOneGrams.add(new Word("and", null));
		topOneGrams.add(new Word("killed", null));
		topOneGrams.add(new Word("become", null));
		topOneGrams.add(new Word("my", null));
		topOneGrams.add(new Word("slaves", null));
		topOneGrams.add(new Word("not", null));
		topOneGrams.add(new Word("give", null));
		topOneGrams.add(new Word("you", null));
		topOneGrams.add(new Word("name", null));
		topOneGrams.add(new Word("try", null));
		topOneGrams.add(new Word("slow", null));
		topOneGrams.add(new Word("down", null));
		topOneGrams.add(new Word("or", null));
		topOneGrams.add(new Word("stop", null));
		topOneGrams.add(new Word("collecting", null));
		topOneGrams.add(new Word("for", null));
		topOneGrams.add(new Word("afterlife", null));

		// 2-grams
		topTwoGrams.add(new Word("ilike", null));
		topTwoGrams.add(new Word("likekilling", null));
		topTwoGrams.add(new Word("killingpeople", null));
		topTwoGrams.add(new Word("becauseit", null));
		topTwoGrams.add(new Word("itis", null));
		topTwoGrams.add(new Word("isso", null));
		topTwoGrams.add(new Word("somuch", null));
		topTwoGrams.add(new Word("muchfun", null));
		topTwoGrams.add(new Word("ismore", null));
		topTwoGrams.add(new Word("morefun", null));
		topTwoGrams.add(new Word("funthan", null));
		topTwoGrams.add(new Word("thankilling", null));
		topTwoGrams.add(new Word("wildgame", null));
		topTwoGrams.add(new Word("inthe", null));
		topTwoGrams.add(new Word("theforrest", null));
		topTwoGrams.add(new Word("becauseman", null));
		topTwoGrams.add(new Word("manis", null));
		topTwoGrams.add(new Word("isthe", null));
		topTwoGrams.add(new Word("themoat", null));
		topTwoGrams.add(new Word("ofall", null));
		topTwoGrams.add(new Word("tokill", null));
		topTwoGrams.add(new Word("killsomething", null));
		topTwoGrams.add(new Word("givesme", null));
		topTwoGrams.add(new Word("methe", null));
		topTwoGrams.add(new Word("iseven", null));
		topTwoGrams.add(new Word("evenbetter", null));
		topTwoGrams.add(new Word("betterthan", null));
		topTwoGrams.add(new Word("thangetting", null));
		topTwoGrams.add(new Word("gettingyour", null));
		topTwoGrams.add(new Word("rocksoff", null));
		topTwoGrams.add(new Word("offwith", null));
		topTwoGrams.add(new Word("witha", null));
		topTwoGrams.add(new Word("agirl", null));
		topTwoGrams.add(new Word("thebest", null));
		topTwoGrams.add(new Word("bestpart", null));
		topTwoGrams.add(new Word("partof", null));
		topTwoGrams.add(new Word("ofit", null));
		topTwoGrams.add(new Word("wheni", null));
		topTwoGrams.add(new Word("idie", null));
		topTwoGrams.add(new Word("diei", null));
		topTwoGrams.add(new Word("iwill", null));
		topTwoGrams.add(new Word("willbe", null));
		topTwoGrams.add(new Word("bereborn", null));
		topTwoGrams.add(new Word("rebornin", null));
		topTwoGrams.add(new Word("allthe", null));
		topTwoGrams.add(new Word("thei", null));
		topTwoGrams.add(new Word("ihave", null));
		topTwoGrams.add(new Word("havekilled", null));
		topTwoGrams.add(new Word("willbecome", null));
		topTwoGrams.add(new Word("becomemy", null));
		topTwoGrams.add(new Word("myslave", null));
		topTwoGrams.add(new Word("willnot", null));
		topTwoGrams.add(new Word("notgive", null));
		topTwoGrams.add(new Word("giveyou", null));
		topTwoGrams.add(new Word("youmy", null));
		topTwoGrams.add(new Word("myname", null));
		topTwoGrams.add(new Word("namebecause", null));
		topTwoGrams.add(new Word("becauseyou", null));
		topTwoGrams.add(new Word("youwill", null));
		topTwoGrams.add(new Word("willtry", null));
		topTwoGrams.add(new Word("tryto", null));
		topTwoGrams.add(new Word("downor", null));
		topTwoGrams.add(new Word("orstop", null));
		topTwoGrams.add(new Word("stopmy", null));
		topTwoGrams.add(new Word("collectingof", null));
		topTwoGrams.add(new Word("ofslaves", null));
		topTwoGrams.add(new Word("formy", null));

		// 3-grams
		topThreeGrams.add(new Word("becauseitis", null));
		topThreeGrams.add(new Word("itisso", null));
		topThreeGrams.add(new Word("issomuch", null));
		topThreeGrams.add(new Word("somuchfun", null));
		topThreeGrams.add(new Word("itismore", null));
		topThreeGrams.add(new Word("ismorefun", null));
		topThreeGrams.add(new Word("morefunthan", null));
		topThreeGrams.add(new Word("givesmethe", null));
		topThreeGrams.add(new Word("itiseven", null));
		topThreeGrams.add(new Word("isevenbetter", null));
		topThreeGrams.add(new Word("evenbetterthan", null));
		topThreeGrams.add(new Word("withagirl", null));
		topThreeGrams.add(new Word("thebestpart", null));
		topThreeGrams.add(new Word("bestpartof", null));
		topThreeGrams.add(new Word("partofit", null));
		topThreeGrams.add(new Word("whenidie", null));
		topThreeGrams.add(new Word("iwillbe", null));
		topThreeGrams.add(new Word("ihavekilled", null));
		topThreeGrams.add(new Word("iwillnot", null));
		topThreeGrams.add(new Word("willnotgive", null));
		topThreeGrams.add(new Word("notgiveyou", null));
		topThreeGrams.add(new Word("giveyoumy", null));
		topThreeGrams.add(new Word("youmyname", null));
		topThreeGrams.add(new Word("becauseyouwill", null));
		topThreeGrams.add(new Word("willtryto", null));

		// 4-grams
		topFourGrams.add(new Word("issomuchfun", null));
		topFourGrams.add(new Word("becauseitisso", null));
		topFourGrams.add(new Word("itissomuch", null));
		topFourGrams.add(new Word("ismorefunthan", null));
		topFourGrams.add(new Word("isevenbetterthan", null));
		topFourGrams.add(new Word("thebestpartof", null));
		topFourGrams.add(new Word("bestpartofit", null));
		topFourGrams.add(new Word("iwillnotgive", null));
		topFourGrams.add(new Word("willnotgiveyou", null));

		// 5-grams
		topFiveGrams.add(new Word("becauseitissomuch", null));
		topFiveGrams.add(new Word("itissomuchfun", null));
		topFiveGrams.add(new Word("thebestpartofit", null));
		topFiveGrams.add(new Word("iwillnotgiveyou", null));
	}

	@PostConstruct
	public void init() {
		for (Word word : topOneGrams) {
			if (markovModel.findLongest(word.getWord(), TerminalType.WORD) == null) {
				markovModel.addWordTransition(word.getWord(), 1);
			}
		}

		for (Word word : topTwoGrams) {
			if (markovModel.findLongest(word.getWord(), TerminalType.WORD) == null) {
				markovModel.addWordTransition(word.getWord(), 1);
			}
		}

		for (Word word : topThreeGrams) {
			if (markovModel.findLongest(word.getWord(), TerminalType.WORD) == null) {
				markovModel.addWordTransition(word.getWord(), 1);
			}
		}

		for (Word word : topFourGrams) {
			if (markovModel.findLongest(word.getWord(), TerminalType.WORD) == null) {
				markovModel.addWordTransition(word.getWord(), 1);
			}
		}
		for (Word word : topFiveGrams) {
			if (markovModel.findLongest(word.getWord(), TerminalType.WORD) == null) {
				markovModel.addWordTransition(word.getWord(), 1);
			}
		}
	}

	@Override
	public Double evaluate(Chromosome chromosome) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		Map<Integer, Match> matchMap = new HashMap<Integer, Match>(currentSolutionString.length());
		String longestMatch;

		/*
		 * Find the longest match at each indice. This helps to prevent short matches from precluding longer subsequent
		 * matches from being found.
		 */
		for (int i = 0; i < currentSolutionString.length(); i++) {
			longestMatch = markovModel.findLongestAsString(currentSolutionString.substring(i), TerminalType.WORD);

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
		for (MatchNode node : rootNodes) {
			WordGraphUtils.findOverlappingChildren(node.getSelf().getEndPos() + 1, lastRowBegin, matchMap, node);

			branches.addAll(node.printBranches());
		}

		double score;
		double highestScore = 0;

		String bestBranch = "";
		// branches = Arrays
		// .asList(new String[] {
		// "ilike, people, becauseitissomuch, ismorefunthan, killing, game, inthe, forrest, becauseman, isthe, moat,
		// ofall, tokill, methe, givesmethe, moat, thrilling, isevenbetterthan, gettingyour, rocksoff, witha,
		// thebestpartofit, wheni, iwillbe, rebornin, allthe, ihavekilled, willbecome, myslave, iwillnot, youmyname,
		// becauseyouwill, tryto, downor, stopmy, collectingof, slaves, formy, afterlife"
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

		List<Integer> matchIndices = getMatchIndices(lastRowBegin, bestBranch, currentSolutionString);

		Map<String, Integer> solutionMap = getSolutionAsMap(matchIndices, (CipherKeyChromosome) chromosome);

		for (String ciphertext : solutionMap.keySet()) {
			highestScore += Math.pow(2.15, solutionMap.get(ciphertext));
		}

		if (log.isDebugEnabled()) {
			log.debug("Best branch: " + bestBranch);
		}

		return highestScore;
	}

	private static List<Integer> getMatchIndices(int lastRowBegin, String bestBranch, String currentSolutionString) {
		List<Integer> matchIndices = new ArrayList<Integer>();

		// In the off chance that no words were found at all
		if (bestBranch.isEmpty()) {
			return matchIndices;
		}

		List<String> words = new ArrayList<String>();

		words.addAll(Arrays.asList(bestBranch.split(", ")));

		Iterator<String> wordIter = words.iterator();

		String word = wordIter.next();

		for (int i = 0; i < lastRowBegin; i++) {
			if (word.equals(currentSolutionString.substring(i, i + word.length()))) {
				for (int j = 0; j < word.length(); j++) {
					matchIndices.add(i + j);
				}

				i += word.length() - 1;

				if (wordIter.hasNext()) {
					word = wordIter.next();
				} else {
					break;
				}
			}
		}

		return matchIndices;
	}

	private static Map<String, Integer> getSolutionAsMap(List<Integer> matchIndices, CipherKeyChromosome chromosome) {
		if (null == ((CipherKeyChromosome) chromosome).getCipher()) {
			throw new IllegalStateException(
					"Called getSolutionAsMap(), but found a null Cipher.  Cannot create valid solution string unless the Cipher is properly set.");
		}

		Map<String, Integer> ciphertextMatchMap = new HashMap<String, Integer>();

		if (matchIndices == null || matchIndices.isEmpty()) {
			return ciphertextMatchMap;
		}

		int actualSize = ((CipherKeyChromosome) chromosome).getCipher().getCiphertextCharacters().size();

		for (int i = 0; i < actualSize; i++) {
			if (matchIndices.contains(i)) {
				String key = ((CipherKeyChromosome) chromosome).getCipher().getCiphertextCharacters().get(i).getValue();

				if (!ciphertextMatchMap.containsKey(key)) {
					ciphertextMatchMap.put(key, 0);
				}

				ciphertextMatchMap.put(key, ciphertextMatchMap.get(key) + 1);
			}
		}

		return ciphertextMatchMap;
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;

		lastRowBegin = (this.cipher.getColumns() * (this.cipher.getRows() - 1));
	}

	/**
	 * @param markovModel
	 *            the markovModel to set
	 */
	@Required
	public void setMarkovModel(MarkovModel markovModel) {
		this.markovModel = markovModel;
	}

	@Override
	public String getDisplayName() {
		return "Matching Word Graph Corpus";
	}
}