package com.ciphertool.engine.fitness.cipherkey;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sherlock.MathConstants;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;

public class InterpolatedFitnessEvaluator implements FitnessEvaluator {
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
		BigDecimal weightTotal = BigDecimal.valueOf(letterNGramWeight).add(BigDecimal.valueOf(wordNGramWeight), MathConstants.PREC_10_HALF_UP);

		if (BigDecimal.ONE.subtract(weightTotal, MathConstants.PREC_10_HALF_UP).abs().compareTo(BigDecimal.ZERO) > 1) {
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

	public BigDecimal evaluate(Chromosome chromosome) {
		BigDecimal total = BigDecimal.ZERO;
		total = total.add((letterNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(letterNGramWeight).multiply(evaluateLetterNGrams(chromosome), MathConstants.PREC_10_HALF_UP)));
		total = total.add((wordNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(wordNGramWeight).multiply(evaluateWords(chromosome), MathConstants.PREC_10_HALF_UP)));

		return total;
	}

	public BigDecimal evaluateLetterNGrams(Chromosome solution) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) solution).substring(0, lastRowBegin);

		int order = letterMarkovModel.getOrder();

		BigDecimal jointProbability = BigDecimal.ONE;

		NGramIndexNode match = null;
		for (int i = 0; i < currentSolutionString.length() - order; i++) {
			match = letterMarkovModel.findLongest(currentSolutionString.substring(i, i + order));

			if (match != null && match.getTerminalInfo().getLevel() == letterMarkovModel.getOrder()) {
				jointProbability = jointProbability.multiply(match.getTerminalInfo().getProbability(), MathConstants.PREC_10_HALF_UP);
			} else {
				jointProbability = jointProbability.multiply(unknownLetterNGramProbability, MathConstants.PREC_10_HALF_UP);
			}
		}

		return jointProbability;
	}

	public BigDecimal evaluateWords(Chromosome solution) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) solution).substring(0, lastRowBegin);

		BigDecimal jointProbability = BigDecimal.ONE;

		NGramIndexNode match = null;
		for (int i = 0; i < currentSolutionString.length(); i += (match == null ? 1 : match.getCumulativeStringValue().length())) {
			match = wordMarkovModel.findLongest(currentSolutionString.substring(i));

			if (match == null) {
				jointProbability = jointProbability.multiply(unknownWordProbability, MathConstants.PREC_10_HALF_UP);
			} else {
				log.debug("matchString: {}", match.getCumulativeStringValue());

				jointProbability = jointProbability.multiply(match.getTerminalInfo().getProbability(), MathConstants.PREC_10_HALF_UP);
			}
		}

		return jointProbability;
	}

	@Override
	public void setGeneticStructure(Object cipher) {
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

	@Override
	public String getDisplayName() {
		return "Interpolated";
	}
}