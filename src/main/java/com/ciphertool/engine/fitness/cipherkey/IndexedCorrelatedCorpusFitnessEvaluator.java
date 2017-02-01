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

import java.math.BigDecimal;
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
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.entities.Word;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.wordgraph.Match;

public class IndexedCorrelatedCorpusFitnessEvaluator implements FitnessEvaluator {
	@SuppressWarnings("unused")
	private Logger				log				= LoggerFactory.getLogger(getClass());

	protected Cipher			cipher;
	private static List<Word>	topOneGrams		= new ArrayList<Word>();
	private static List<Word>	topTwoGrams		= new ArrayList<Word>();
	private static List<Word>	topThreeGrams	= new ArrayList<Word>();
	private static List<Word>	topFourGrams	= new ArrayList<Word>();
	private static List<Word>	topFiveGrams	= new ArrayList<Word>();

	private MarkovModel			wordMarkovModel;

	private int					lastRowBegin;

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
			if (wordMarkovModel.findLongest(word.getWord()) == null) {
				wordMarkovModel.addWordTransition(word.getWord(), 1);
			}
		}

		for (Word word : topTwoGrams) {
			if (wordMarkovModel.findLongest(word.getWord()) == null) {
				wordMarkovModel.addWordTransition(word.getWord(), 1);
			}
		}

		for (Word word : topThreeGrams) {
			if (wordMarkovModel.findLongest(word.getWord()) == null) {
				wordMarkovModel.addWordTransition(word.getWord(), 1);
			}
		}

		for (Word word : topFourGrams) {
			if (wordMarkovModel.findLongest(word.getWord()) == null) {
				wordMarkovModel.addWordTransition(word.getWord(), 1);
			}
		}

		for (Word word : topFiveGrams) {
			if (wordMarkovModel.findLongest(word.getWord()) == null) {
				wordMarkovModel.addWordTransition(word.getWord(), 1);
			}
		}
	}

	protected List<String> getCorrespondingCiphertexts(int index, int wordLength) {
		List<String> ciphertextCharacters = new ArrayList<String>();

		for (int i = index; i < index + wordLength; i++) {
			ciphertextCharacters.add(cipher.getCiphertextCharacters().get(i).getValue());
		}

		return ciphertextCharacters;
	}

	@Override
	public BigDecimal evaluate(Chromosome chromosome) {
		Map<Integer, List<Match>> matchMap = new HashMap<Integer, List<Match>>();

		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		String longestMatch;
		for (int i = 0; i < currentSolutionString.length(); i++) {
			longestMatch = wordMarkovModel.findLongestAsString(currentSolutionString.substring(i));

			if (longestMatch != null) {
				if (!matchMap.containsKey(i)) {
					matchMap.put(i, new ArrayList<Match>());
				}

				matchMap.get(i).add(new Match(i, i + longestMatch.length() - 1, longestMatch));

				i += longestMatch.length() - 1;
			}
		}

		double betterFitness = 0.0;
		List<String> ciphertextCharacters;
		List<String> innerCiphertextCharacters;
		Map<String, Integer> countOfMatchesMap;
		for (int key : matchMap.keySet()) {
			ciphertextCharacters = getCorrespondingCiphertexts(key, matchMap.get(key).get(0).getWord().length());
			countOfMatchesMap = new HashMap<String, Integer>();

			for (String ciphertextCharacter : ciphertextCharacters) {
				countOfMatchesMap.put(ciphertextCharacter, 0);
			}

			for (int innerKey : matchMap.keySet()) {
				if (innerKey == key) {
					continue;
				}

				if (ciphertextCharacters.isEmpty()) {
					break;
				}

				innerCiphertextCharacters = getCorrespondingCiphertexts(innerKey, matchMap.get(innerKey).get(0).getWord().length());

				for (String innerCiphertext : innerCiphertextCharacters) {
					if (ciphertextCharacters.contains(innerCiphertext)) {
						countOfMatchesMap.put(innerCiphertext, countOfMatchesMap.get(innerCiphertext) + 1);
					}
				}
			}

			int numCiphertextMatches = 0;
			for (String ciphertext : ciphertextCharacters) {
				if (countOfMatchesMap.get(ciphertext) > 1) {
					numCiphertextMatches++;
				}
			}

			betterFitness += Math.pow(2, numCiphertextMatches);
		}

		return BigDecimal.valueOf(betterFitness);
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

	@Override
	public String getDisplayName() {
		return "Indexed Correlated Graph Corpus";
	}
}
