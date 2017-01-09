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
import java.math.MathContext;

import javax.annotation.PostConstruct;

import org.nevec.rjm.BigDecimalMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;

public class PlaintextEvaluator {
	private Logger		log	= LoggerFactory.getLogger(getClass());

	protected Cipher	cipher;

	private MarkovModel	letterMarkovModel;
	private MarkovModel	wordMarkovModel;

	private int			lastRowBegin;
	private double		letterNGramWeight;
	private double		wordNGramWeight;

	private BigDecimal	unknownLetterNGramProbability;
	private BigDecimal	unknownWordProbability;

	@PostConstruct
	public void init() {
		BigDecimal weightTotal = BigDecimal.valueOf(letterNGramWeight).add(BigDecimal.valueOf(wordNGramWeight));

		if (BigDecimal.ONE.subtract(weightTotal).abs().compareTo(BigDecimal.ZERO) > 1) {
			throw new IllegalArgumentException(
					"The sum of letterNGramWeight, wordNGramWeight, and frequencyWeight must equal exactly 1.0, but letterNGramWeight="
							+ letterNGramWeight + " and wordNGramWeight=" + wordNGramWeight + " sums to "
							+ weightTotal);
		}

		unknownLetterNGramProbability = BigDecimal.ONE.divide(BigDecimal.valueOf(letterMarkovModel.getRootNode().getTerminalInfo().getCount()
				+ 1), MathContext.DECIMAL128);

		unknownWordProbability = BigDecimal.ONE.divide(BigDecimal.valueOf(wordMarkovModel.getRootNode().getTerminalInfo().getCount()
				+ 1), MathContext.DECIMAL128);

		log.debug("unknownLetterNGramProbability: {}", unknownLetterNGramProbability);
		log.debug("unknownWordProbability: {}", unknownWordProbability);
	}

	public EvaluationResults evaluate(CipherSolution solution) {
		EvaluationResults letterNGramResults = evaluateLetterNGrams(solution);
		EvaluationResults wordNGramResults = evaluateWords(solution);

		BigDecimal interpolatedProbability = BigDecimal.ZERO;
		interpolatedProbability = interpolatedProbability.add((letterNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(letterNGramWeight).multiply(letterNGramResults.getProbability(), MathContext.DECIMAL128)));
		interpolatedProbability = interpolatedProbability.add((wordNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(wordNGramWeight).multiply(wordNGramResults.getProbability(), MathContext.DECIMAL128)));

		BigDecimal interpolatedLogProbability = BigDecimal.ZERO;
		interpolatedLogProbability = interpolatedLogProbability.add((letterNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(letterNGramWeight).multiply(letterNGramResults.getLogProbability(), MathContext.DECIMAL128)));
		interpolatedLogProbability = interpolatedLogProbability.add((wordNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(wordNGramWeight).multiply(wordNGramResults.getLogProbability(), MathContext.DECIMAL128)));

		return new EvaluationResults(interpolatedProbability, interpolatedLogProbability);
	}

	public EvaluationResults evaluateLetterNGrams(CipherSolution solution) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString(solution).substring(0, lastRowBegin);

		int order = letterMarkovModel.getOrder();

		BigDecimal jointProbability = BigDecimal.ONE;
		BigDecimal jointLogProbability = BigDecimal.ZERO;

		NGramIndexNode match = null;
		for (int i = 0; i < currentSolutionString.length() - order; i++) {
			match = letterMarkovModel.findLongest(currentSolutionString.substring(i, i + order));

			if (match != null && match.getTerminalInfo().getLevel() == letterMarkovModel.getOrder()) {
				jointProbability = jointProbability.multiply(match.getTerminalInfo().getProbability(), MathContext.DECIMAL128);
				jointLogProbability = jointLogProbability.add(convertToLogProbability(match.getTerminalInfo().getProbability()));
			} else {
				jointProbability = jointProbability.multiply(unknownLetterNGramProbability, MathContext.DECIMAL128);
				jointLogProbability = jointLogProbability.add(convertToLogProbability(unknownLetterNGramProbability));
			}
		}

		return new EvaluationResults(jointProbability, jointLogProbability);
	}

	public EvaluationResults evaluateWords(CipherSolution solution) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString(solution).substring(0, lastRowBegin);

		BigDecimal jointProbability = BigDecimal.ONE;
		BigDecimal jointLogProbability = BigDecimal.ZERO;

		NGramIndexNode match = null;
		for (int i = 0; i < currentSolutionString.length(); i += (match == null ? 1 : match.getCumulativeStringValue().length())) {
			match = wordMarkovModel.findLongest(currentSolutionString.substring(i));

			if (match == null) {
				jointProbability = jointProbability.multiply(unknownWordProbability, MathContext.DECIMAL128);
				jointLogProbability = jointLogProbability.add(convertToLogProbability(unknownWordProbability));
			} else {
				log.debug("matchString: {}", match.getCumulativeStringValue());

				jointProbability = jointProbability.multiply(match.getTerminalInfo().getProbability(), MathContext.DECIMAL128);
				jointLogProbability = jointLogProbability.add(convertToLogProbability(match.getTerminalInfo().getProbability()));
			}
		}

		return new EvaluationResults(jointProbability, jointLogProbability);
	}

	protected static BigDecimal convertToLogProbability(BigDecimal probability) {
		if (probability == null) {
			return BigDecimal.ZERO;
		}

		return BigDecimalMath.log(probability);
	}

	public void setCipher(Object cipher) {
		this.cipher = (Cipher) cipher;

		lastRowBegin = (this.cipher.getColumns() * (this.cipher.getRows() - 1));
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