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

import java.util.ArrayList;
import java.util.Arrays;
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
import com.ciphertool.engine.entities.CipherKeyGene;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.entities.Word;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;
import com.ciphertool.sherlock.markov.TerminalInfo;
import com.ciphertool.sherlock.wordgraph.Match;
import com.ciphertool.sherlock.wordgraph.MatchNode;

public class GenerativeLetterAndWordMarkovFitnessEvaluator implements FitnessEvaluator {
	private Logger							log						= LoggerFactory.getLogger(getClass());

	private static final List<Character>	LOWERCASE_LETTERS		= Arrays.asList(new Character[] { 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z' });
	private static final int				GRACE_WINDOW_SIZE		= 1;
	private static final int				MIN_POS					= 4;

	protected Cipher						cipher;

	private MarkovModel						letterMarkovModel;
	private MarkovModel						wordMarkovModel;

	private int								lastRowBegin;
	private double							frequencyWeight;
	private double							letterNGramWeight;
	private double							wordNGramWeight;
	private double							minimumProbability;
	private int								minimumLetterOrder;

	private Map<Character, Double>			expectedLetterFrequencies;
	private Map<Character, Integer>			expectedLetterCounts	= new HashMap<Character, Integer>(
			LOWERCASE_LETTERS.size());
	private static List<Word>				topOneGrams				= new ArrayList<Word>();
	private static List<Word>				topTwoGrams				= new ArrayList<Word>();
	private static List<Word>				topThreeGrams			= new ArrayList<Word>();
	private static List<Word>				topFourGrams			= new ArrayList<Word>();
	private static List<Word>				topFiveGrams			= new ArrayList<Word>();

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
		double weightTotal = (letterNGramWeight + frequencyWeight + wordNGramWeight);

		if (Math.abs(1.0 - weightTotal) > 0.0001) {
			throw new IllegalArgumentException(
					"The sum of letterNGramWeight, wordNGramWeight, and frequencyWeight must equal exactly 1.0, but letterNGramWeight="
							+ letterNGramWeight + " and frequencyWeight=" + frequencyWeight + " and wordNGramWeight="
							+ wordNGramWeight + " sums to " + weightTotal);
		}

		int letterOrder = this.letterMarkovModel.getOrder();

		if (this.minimumLetterOrder > letterOrder) {
			log.warn("Minimum order is set to " + this.minimumLetterOrder
					+ ", which is greater than the Markov model order of " + letterOrder
					+ ".  Reducing minimumOrder to " + letterOrder);

			this.minimumLetterOrder = letterOrder;
		}

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

	@Override
	public Double evaluate(Chromosome chromosome) {
		double total = 0.0;
		total += (frequencyWeight == 0.0) ? 0.0 : (frequencyWeight * evaluateFrequency(chromosome));
		total += (letterNGramWeight == 0.0) ? 0.0 : (letterNGramWeight * evaluateLetterNGram(chromosome));
		total += (wordNGramWeight == 0.0) ? 0.0 : (wordNGramWeight * evaluateWordNGram(chromosome));

		return total;
	}

	public Double evaluateFrequency(Chromosome chromosome) {
		CipherKeyChromosome cipherKeyChromosome = (CipherKeyChromosome) chromosome;

		Map<Character, Integer> actualLetterCounts = new HashMap<Character, Integer>(LOWERCASE_LETTERS.size());

		for (Map.Entry<String, Gene> entry : cipherKeyChromosome.getGenes().entrySet()) {
			char key = ((CipherKeyGene) entry.getValue()).getValue().charAt(0);

			Integer value = actualLetterCounts.get(key);

			if (value == null) {
				actualLetterCounts.put(key, 0);

				value = actualLetterCounts.get(key);
			}

			actualLetterCounts.put(key, value + 1);
		}

		int numCorrect = 0;

		for (char letter : LOWERCASE_LETTERS) {
			int actualCount = actualLetterCounts.containsKey(letter) ? actualLetterCounts.get(letter) : 0;
			int expectedCount = expectedLetterCounts.get(letter);

			if (expectedCount + GRACE_WINDOW_SIZE >= actualCount && expectedCount - GRACE_WINDOW_SIZE <= actualCount) {
				numCorrect += 1;
			} else if (expectedCount + GRACE_WINDOW_SIZE < actualCount) {
				numCorrect -= (actualCount - (expectedCount + GRACE_WINDOW_SIZE));
			} else if (expectedCount - GRACE_WINDOW_SIZE > actualCount) {
				numCorrect -= ((expectedCount + GRACE_WINDOW_SIZE) - actualCount);
			}
		}

		double frequencyProbability = (double) numCorrect / (double) LOWERCASE_LETTERS.size();

		if (frequencyProbability < 0.0) {
			frequencyProbability = 0.0;
		}

		return frequencyProbability;
	}

	public Double evaluateLetterNGram(Chromosome chromosome) {
		CipherKeyChromosome cipherKeyChromosome = (CipherKeyChromosome) chromosome;

		String currentSolutionString = WordGraphUtils.getSolutionAsString(cipherKeyChromosome).substring(0, lastRowBegin);

		int order = letterMarkovModel.getOrder();

		double matches = 0.0;
		NGramIndexNode match = null;
		TerminalInfo terminalInfo;
		for (int i = 0; i < currentSolutionString.length() - order; i++) {
			if (match != null) {
				match = match.getChild(currentSolutionString.charAt(i + order - 1));
			}

			if (match == null) {
				match = letterMarkovModel.findLongest(currentSolutionString.substring(i, i + order));
			}

			terminalInfo = match.getTerminalInfo();

			if (terminalInfo != null) {
				if (terminalInfo.getLevel() >= minimumLetterOrder) {
					matches += (double) terminalInfo.getLevel() / (double) order;
				}

				if (terminalInfo.getLevel() != order) {
					match = null;
				}
			}

			if ((matches / (double) i) < minimumProbability) {
				break;
			}
		}

		double letterNGramProbability = (matches / (double) (lastRowBegin - order));

		if (letterNGramProbability < 0.0) {
			letterNGramProbability = 0.0;
		}

		return letterNGramProbability;
	}

	public Double evaluateWordNGram(Chromosome chromosome) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) chromosome).substring(0, lastRowBegin);

		Map<Integer, Match> matchMap = new HashMap<Integer, Match>(currentSolutionString.length());
		String longestMatch;

		/*
		 * Find the longest match at each indice. This helps to prevent short matches from precluding longer subsequent
		 * matches from being found.
		 */
		for (int i = 0; i < currentSolutionString.length(); i++) {
			longestMatch = wordMarkovModel.findLongestAsString(currentSolutionString.substring(i));

			if (longestMatch != null) {
				matchMap.put(i, new Match(i, i + longestMatch.length() - 1, longestMatch));
			}
		}

		List<MatchNode> nodes = new ArrayList<MatchNode>();
		MatchNode best = null;
		MatchNode root = null;
		MatchNode parent = null;

		/*
		 * Find all the starting matches (first match from the beginning of the solution string) which overlap with one
		 * another. These are the candidate root matches. This must be done in order, hence why we cannot iterate over
		 * the Map's entry set.
		 */
		for (int beginPos = 0; beginPos < lastRowBegin; beginPos++) {
			if (matchMap.containsKey(beginPos) || beginPos == lastRowBegin - 1) {
				if (WordGraphUtils.nonOverlapping(beginPos, nodes)) {
					if (root == null) {
						root = best;
						parent = root;
					} else if (parent != best) {
						parent.addChild(best);
						parent = best;
					}

					beginPos = best.getSelf().getEndPos();
					nodes.clear();
					best = null;
					continue;
				}

				MatchNode node = new MatchNode(matchMap.get(beginPos));

				if (best == null || node.getSelf().getWord().length() > best.getSelf().getWord().length()) {
					best = node;
				}

				nodes.add(node);
			}

			if (root != null && best == null && ((double) root.printBranches().get(0).replaceAll(", ", "").length()
					/ (double) beginPos) < minimumProbability) {
				break;
			} else if (beginPos > MIN_POS && root == null && best == null) {
				break;
			}
		}

		int length = 0;

		if (root != null) {
			length = root.printBranches().get(0).replaceAll(", ", "").length();
		}

		double wordNGramProbability = (double) length / (double) currentSolutionString.length();

		if (wordNGramProbability < 0.0) {
			wordNGramProbability = 0.0;
		}

		return wordNGramProbability;
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;

		lastRowBegin = (this.cipher.getColumns() * (this.cipher.getRows() - 1));

		int cipherKeySize = (int) this.cipher.getCiphertextCharacters().stream().map(c -> c.getValue()).distinct().count();

		for (Map.Entry<Character, Double> entry : expectedLetterFrequencies.entrySet()) {
			expectedLetterCounts.put(entry.getKey(), (int) Math.round(entry.getValue() * cipherKeySize));
		}
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
	 * @param letterMarkovModel
	 *            the letterMarkovModel to set
	 */
	@Required
	public void setLetterMarkovModel(MarkovModel letterMarkovModel) {
		this.letterMarkovModel = letterMarkovModel;
	}

	/**
	 * @param expectedLetterFrequencies
	 *            the expectedLetterFrequencies to set
	 */
	@Required
	public void setExpectedLetterFrequencies(Map<Character, Double> expectedLetterFrequencies) {
		this.expectedLetterFrequencies = expectedLetterFrequencies;
	}

	/**
	 * @param letterNGramWeight
	 *            the letterNGramWeight to set
	 */
	@Required
	public void setLetterNGramWeight(double letterNGramWeight) {
		this.letterNGramWeight = letterNGramWeight;
	}

	/**
	 * @param wordNGramWeight
	 *            the wordNGramWeight to set
	 */
	@Required
	public void setWordNGramWeight(double wordNGramWeight) {
		this.wordNGramWeight = wordNGramWeight;
	}

	/**
	 * @param frequencyWeight
	 *            the frequencyWeight to set
	 */
	@Required
	public void setFrequencyWeight(double frequencyWeight) {
		this.frequencyWeight = frequencyWeight;
	}

	/**
	 * @param minimumProbability
	 *            the minimumProbability to set
	 */
	@Required
	public void setMinimumProbability(double minimumProbability) {
		this.minimumProbability = minimumProbability;
	}

	/**
	 * @param minimumLetterOrder
	 *            the minimumLetterOrder to set
	 */
	@Required
	public void setMinimumLetterOrder(int minimumLetterOrder) {
		this.minimumLetterOrder = minimumLetterOrder;
	}

	@Override
	public String getDisplayName() {
		return "Generative Markov And N-Gram";
	}
}
