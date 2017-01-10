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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.annotation.PostConstruct;

import org.nevec.rjm.BigDecimalMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.task.TaskExecutor;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.sherlock.MathConstants;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;

public class PlaintextEvaluator {
	private Logger			log	= LoggerFactory.getLogger(getClass());

	protected Cipher		cipher;

	private MarkovModel		letterMarkovModel;
	private MarkovModel		wordMarkovModel;

	private int				lastRowBegin;
	private double			letterNGramWeight;
	private double			wordNGramWeight;

	private BigDecimal		unknownLetterNGramProbability;
	private BigDecimal		unknownWordProbability;

	private TaskExecutor	taskExecutor;

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
		EvaluationResults letterNGramResults = evaluateLetterNGrams(solution);
		EvaluationResults wordNGramResults = evaluateWords(solution);

		BigDecimal interpolatedProbability = BigDecimal.ZERO;
		interpolatedProbability = interpolatedProbability.add(((letterNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(letterNGramWeight).multiply(letterNGramResults.getProbability(), MathConstants.PREC_10_HALF_UP))), MathConstants.PREC_10_HALF_UP);
		interpolatedProbability = interpolatedProbability.add(((wordNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(wordNGramWeight).multiply(wordNGramResults.getProbability(), MathConstants.PREC_10_HALF_UP))), MathConstants.PREC_10_HALF_UP);

		BigDecimal interpolatedLogProbability = BigDecimal.ZERO;
		interpolatedLogProbability = interpolatedLogProbability.add(((letterNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(letterNGramWeight).multiply(letterNGramResults.getLogProbability(), MathConstants.PREC_10_HALF_UP))), MathConstants.PREC_10_HALF_UP);
		interpolatedLogProbability = interpolatedLogProbability.add(((wordNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(wordNGramWeight).multiply(wordNGramResults.getLogProbability(), MathConstants.PREC_10_HALF_UP))), MathConstants.PREC_10_HALF_UP);

		return new EvaluationResults(interpolatedProbability, interpolatedLogProbability);
	}

	public EvaluationResults evaluateLetterNGrams(CipherSolution solution) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString(solution).substring(0, lastRowBegin);

		int order = letterMarkovModel.getOrder();

		BigDecimal jointProbability = BigDecimal.ONE;
		BigDecimal jointLogProbability = BigDecimal.ZERO;

		List<FutureTask<BigDecimal>> futures = new ArrayList<>(26);
		FutureTask<BigDecimal> task;

		// Calculate the full conditional probability for each possible plaintext substitution
		BigDecimal probability;
		NGramIndexNode match = null;
		for (int i = 0; i < currentSolutionString.length()
				- order; i += (match == null ? 1 : match.getCumulativeStringValue().length())) {
			match = letterMarkovModel.findLongest(currentSolutionString.substring(i, i + order));

			if (match != null && match.getTerminalInfo().getLevel() == letterMarkovModel.getOrder()) {
				jointProbability = jointProbability.multiply(match.getTerminalInfo().getProbability(), MathConstants.PREC_10_HALF_UP);
				probability = match.getTerminalInfo().getProbability();
				log.debug("Letter N-Gram Match={}, Probability={}", match.getCumulativeStringValue(), probability);
			} else {
				jointProbability = jointProbability.multiply(unknownLetterNGramProbability, MathConstants.PREC_10_HALF_UP);
				probability = unknownWordProbability;
				log.debug("No Letter N-Gram Match");
			}

			task = new FutureTask<>(new CovertLogProbabilityTask(probability));
			futures.add(task);
			this.taskExecutor.execute(task);
		}

		for (FutureTask<BigDecimal> future : futures) {
			try {
				jointLogProbability = jointLogProbability.add(future.get(), MathConstants.PREC_10_HALF_UP);
			} catch (InterruptedException ie) {
				log.error("Caught InterruptedException while waiting for BigDecimal ", ie);
			} catch (ExecutionException ee) {
				log.error("Caught ExecutionException while waiting for BigDecimal ", ee);
			}
		}

		return new EvaluationResults(jointProbability, jointLogProbability);
	}

	public EvaluationResults evaluateWords(CipherSolution solution) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString(solution).substring(0, lastRowBegin);

		BigDecimal jointProbability = BigDecimal.ONE;
		BigDecimal jointLogProbability = BigDecimal.ZERO;

		List<FutureTask<BigDecimal>> futures = new ArrayList<>(26);
		FutureTask<BigDecimal> task;

		// Calculate the full conditional probability for each possible plaintext substitution
		NGramIndexNode match = null;
		BigDecimal probability;
		for (int i = 0; i < currentSolutionString.length(); i += (match == null ? 1 : match.getCumulativeStringValue().length())) {
			match = wordMarkovModel.findLongest(currentSolutionString.substring(i));

			if (match == null) {
				jointProbability = jointProbability.multiply(unknownWordProbability, MathConstants.PREC_10_HALF_UP);
				probability = unknownWordProbability;
				log.debug("No Word Match");
			} else {
				jointProbability = jointProbability.multiply(match.getTerminalInfo().getProbability(), MathConstants.PREC_10_HALF_UP);
				probability = match.getTerminalInfo().getProbability();
				log.debug("Word Match={}, Probability={}", match.getCumulativeStringValue(), probability);
			}

			task = new FutureTask<>(new CovertLogProbabilityTask(probability));
			futures.add(task);
			this.taskExecutor.execute(task);
		}

		for (FutureTask<BigDecimal> future : futures) {
			try {
				jointLogProbability = jointLogProbability.add(future.get(), MathConstants.PREC_10_HALF_UP);
			} catch (InterruptedException ie) {
				log.error("Caught InterruptedException while waiting for BigDecimal ", ie);
			} catch (ExecutionException ee) {
				log.error("Caught ExecutionException while waiting for BigDecimal ", ee);
			}
		}

		return new EvaluationResults(jointProbability, jointLogProbability);
	}

	/**
	 * A concurrent task for computing log probability.
	 */
	protected class CovertLogProbabilityTask implements Callable<BigDecimal> {
		private BigDecimal probability;

		/**
		 * @param probability
		 */
		public CovertLogProbabilityTask(BigDecimal probability) {
			this.probability = probability;
		}

		@Override
		public BigDecimal call() throws Exception {
			return convertToLogProbability(this.probability);
		}
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

	/**
	 * @param taskExecutor
	 *            the taskExecutor to set
	 */
	@Required
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}