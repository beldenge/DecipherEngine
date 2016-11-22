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
import com.ciphertool.sherlock.wordgraph.Match;
import com.ciphertool.sherlock.wordgraph.MatchNode;

public class GenerativeMarkovAndNGramFitnessEvaluator implements FitnessEvaluator {
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
	private int								minimumOrder;

	private Map<Character, Double>			expectedLetterFrequencies;
	private Map<Character, Integer>			expectedLetterCounts	= new HashMap<Character, Integer>(
			LOWERCASE_LETTERS.size());
	private static List<Word>				topWords				= new ArrayList<Word>();

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

	@PostConstruct
	public void init() {
		double weightTotal = (letterNGramWeight + frequencyWeight + wordNGramWeight);

		if (Math.abs(1.0 - weightTotal) > 0.0001) {
			throw new IllegalArgumentException(
					"The sum of letterNGramWeight, wordNGramWeight, and frequencyWeight must equal exactly 1.0, but letterNGramWeight="
							+ letterNGramWeight + " and frequencyWeight=" + frequencyWeight + " and wordNGramWeight="
							+ wordNGramWeight + " sums to " + weightTotal);
		}

		if (this.minimumOrder > this.letterMarkovModel.getOrder()) {
			log.warn("Minimum order is set to " + this.minimumOrder
					+ ", which is greater than the Markov model order of " + this.letterMarkovModel.getOrder()
					+ ".  Reducing minimumOrder to " + this.letterMarkovModel.getOrder());

			this.minimumOrder = this.letterMarkovModel.getOrder();
		}

		for (Word word : topWords) {
			if (wordMarkovModel.findLongest(word.getWord()) == null) {
				wordMarkovModel.addTransition(word.getWord(), false);
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
		for (int i = 0; i < currentSolutionString.length() - order; i++) {
			if (match != null) {
				match = match.getChild(currentSolutionString.charAt(i + order - 1));
			}

			if (match == null) {
				match = letterMarkovModel.findLongest(currentSolutionString.substring(i, i + order));
			}

			if (match != null && match.getLevel() >= minimumOrder) {
				matches += (double) match.getLevel() / (double) order;
			}

			if ((matches / (double) i) < minimumProbability) {
				break;
			}

			if (match.getLevel() != order) {
				match = null;
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
	 * @param minimumOrder
	 *            the minimumOrder to set
	 */
	@Required
	public void setMinimumOrder(int minimumOrder) {
		this.minimumOrder = minimumOrder;
	}

	@Override
	public String getDisplayName() {
		return "Generative Markov And N-Gram";
	}
}
