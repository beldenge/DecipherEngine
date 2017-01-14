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

package com.ciphertool.engine.bayes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.nevec.rjm.BigDecimalMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.sherlock.MathConstants;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;

public class PlaintextEvaluator {
	private Logger		log	= LoggerFactory.getLogger(getClass());

	private MarkovModel	letterMarkovModel;
	private MarkovModel	wordMarkovModel;

	private double		letterNGramWeight;
	private double		wordNGramWeight;

	private BigDecimal	unknownLetterNGramProbability;
	private BigDecimal	unknownWordProbability;

	@PostConstruct
	public void init() {
		BigDecimal weightTotal = BigDecimal.valueOf(letterNGramWeight).add(BigDecimal.valueOf(wordNGramWeight), MathConstants.PREC_10_HALF_UP);

		if (BigDecimal.ONE.subtract(weightTotal, MathConstants.PREC_10_HALF_UP).abs().compareTo(BigDecimal.ZERO) > 0) {
			throw new IllegalArgumentException(
					"The sum of letterNGramWeight, wordNGramWeight, and frequencyWeight must equal exactly 1.0, but letterNGramWeight="
							+ letterNGramWeight + " and wordNGramWeight=" + wordNGramWeight + " sums to "
							+ weightTotal);
		}

		unknownLetterNGramProbability = BigDecimal.ONE.divide(BigDecimal.valueOf(letterMarkovModel.getRootNode().getTerminalInfo().getCount()
				+ 1), MathConstants.PREC_10_HALF_UP);

		unknownWordProbability = BigDecimal.ONE.divide(BigDecimal.valueOf(wordMarkovModel.getRootNode().getTerminalInfo().getCount()
				+ 1), MathConstants.PREC_10_HALF_UP);

		log.debug("unknownLetterNGramProbability: {}", unknownLetterNGramProbability);
		log.debug("unknownWordProbability: {}", unknownWordProbability);
	}

	public EvaluationResults evaluate(CipherSolution solution) {
		return evaluate(null, false, solution);
	}

	public EvaluationResults evaluate(String ciphertextKey, boolean includeCiphertextParameterOnly, CipherSolution solution) {
		BigDecimal interpolatedProbability = null;
		BigDecimal interpolatedLogProbability = null;
		BigDecimal newProbability = BigDecimal.ONE;
		BigDecimal newLogProbability = BigDecimal.ZERO;

		List<WordProbability> letterNGramResults = evaluateLetterNGrams(ciphertextKey, includeCiphertextParameterOnly, solution);
		List<WordProbability> wordNGramResults = evaluateWords(ciphertextKey, includeCiphertextParameterOnly, solution);

		if (letterNGramResults.size() != wordNGramResults.size()) {
			throw new IllegalStateException(
					"Cannot interpolate probabilities from language model because the number of probabilities does not match.  letterNGrams="
							+ letterNGramResults.size() + ", words=" + wordNGramResults.size());
		}

		for (WordProbability wordProbability : wordNGramResults) {
			WordProbability letterNGramProbability;

			try {
				letterNGramProbability = letterNGramResults.get(letterNGramResults.indexOf(wordProbability));
			} catch (IndexOutOfBoundsException ioobe) {
				throw new IllegalStateException(
						"Cannot interpolate probabilities from language model because a mismatch was found.  No letter n-gram found for: "
								+ wordProbability);
			}

			interpolatedProbability = BigDecimal.ZERO;
			interpolatedProbability = interpolatedProbability.add(((letterNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(letterNGramWeight).multiply(letterNGramProbability.getProbability(), MathConstants.PREC_10_HALF_UP))), MathConstants.PREC_10_HALF_UP);
			interpolatedProbability = interpolatedProbability.add(((wordNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(wordNGramWeight).multiply(wordProbability.getProbability(), MathConstants.PREC_10_HALF_UP))), MathConstants.PREC_10_HALF_UP);

			interpolatedLogProbability = BigDecimalMath.log(interpolatedProbability);

			newProbability = newProbability.multiply(interpolatedProbability, MathConstants.PREC_10_HALF_UP);
			newLogProbability = newLogProbability.add(interpolatedLogProbability, MathConstants.PREC_10_HALF_UP);
		}

		return new EvaluationResults(newProbability, newLogProbability);
	}

	public List<WordProbability> evaluateLetterNGrams(String ciphertextKey, boolean includeCiphertextParameterOnly, CipherSolution solution) {
		List<WordProbability> words = transformToWordList(ciphertextKey, includeCiphertextParameterOnly, solution);

		int order = letterMarkovModel.getOrder();

		BigDecimal nGramProbability = null;
		BigDecimal probability = null;
		NGramIndexNode match = null;

		for (WordProbability word : words) {
			nGramProbability = BigDecimal.ONE;

			// Loop over all indices so that non-matching indices negatively affect the probabitliy
			for (int i = 0; i <= word.getValue().length() - order; i++) {
				match = letterMarkovModel.findLongest(word.getValue().substring(i, i + order));

				if (match != null && match.getTerminalInfo().getLevel() == letterMarkovModel.getOrder()) {
					probability = match.getTerminalInfo().getProbability();
					log.debug("Letter N-Gram Match={}, Probability={}", match.getCumulativeStringValue(), probability);
				} else {
					probability = unknownWordProbability;
					log.debug("No Letter N-Gram Match");
				}

				nGramProbability = nGramProbability.multiply(probability, MathConstants.PREC_10_HALF_UP);
			}

			word.setProbability(nGramProbability);
		}

		return words;
	}

	public List<WordProbability> evaluateWords(String ciphertextKey, boolean includeCiphertextParameterOnly, CipherSolution solution) {
		List<WordProbability> words = transformToWordList(ciphertextKey, includeCiphertextParameterOnly, solution);

		NGramIndexNode match = null;
		BigDecimal probability = null;
		for (WordProbability word : words) {
			probability = BigDecimal.ONE;

			match = wordMarkovModel.findLongest(word.getValue());

			if (match != null) {
				probability = match.getTerminalInfo().getProbability();
				log.debug("Word Match={}, Probability={}", match.getCumulativeStringValue(), probability);
			}

			if (match == null || match.getCumulativeStringValue().length() < word.getValue().length()) {
				probability = probability.multiply(unknownWordProbability.pow(word.getValue().length()
						- (match == null ? 0 : match.getCumulativeStringValue().length()), MathConstants.PREC_10_HALF_UP), MathConstants.PREC_10_HALF_UP);
				log.debug("No Word Match");
			}

			word.setProbability(probability);
		}

		return words;
	}

	protected List<WordProbability> transformToWordList(String ciphertextKey, boolean includeCiphertextParameterOnly, CipherSolution solution) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString(solution).substring(0, solution.getCipher().getCiphertextCharacters().size());

		List<WordProbability> words = new ArrayList<>();
		Integer begin = null;
		boolean add = false;

		for (int i = 0; i < currentSolutionString.length(); i++) {
			if (ciphertextKey == null) {
				add = true;
			} else if (includeCiphertextParameterOnly) {
				if (ciphertextKey.equals(solution.getCipher().getCiphertextCharacters().get(i).getValue())) {
					add = true;
				} else {
					add = false;
				}
			} else if (ciphertextKey.equals(solution.getCipher().getCiphertextCharacters().get(i).getValue())) {
				add = false;
			} else {
				add = true;
			}

			if (i < (currentSolutionString.length() - 1) && solution.getWordBoundaries().contains(i)) {
				if (add) {
					words.add(new WordProbability(begin, i, currentSolutionString.substring((begin == null ? 0 : begin
							+ 1), i + 1)));
				}

				begin = i;
				add = false;
			}
		}

		if (add) {
			words.add(new WordProbability(begin, null, currentSolutionString.substring((begin == null ? 0 : begin
					+ 1), currentSolutionString.length())));
		}

		return words;
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
	 * @param wordMarkovModel
	 *            the wordMarkovModel to set
	 */
	@Required
	public void setWordMarkovModel(MarkovModel wordMarkovModel) {
		this.wordMarkovModel = wordMarkovModel;
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
}