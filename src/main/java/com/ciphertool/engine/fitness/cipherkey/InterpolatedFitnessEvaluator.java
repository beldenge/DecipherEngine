package com.ciphertool.engine.fitness.cipherkey;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.common.WordGraphUtils;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
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

	public Double evaluate(Chromosome chromosome) {
		BigDecimal total = BigDecimal.ZERO;
		total = total.add((letterNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(letterNGramWeight).multiply(evaluateLetterNGrams(chromosome), MathContext.DECIMAL128)));
		total = total.add((wordNGramWeight == 0.0) ? BigDecimal.ZERO : (BigDecimal.valueOf(wordNGramWeight).multiply(evaluateWords(chromosome), MathContext.DECIMAL128)));

		return total.doubleValue();
	}

	public BigDecimal evaluateLetterNGrams(Chromosome solution) {
		String currentSolutionString = WordGraphUtils.getSolutionAsString((CipherKeyChromosome) solution).substring(0, lastRowBegin);

		int order = letterMarkovModel.getOrder();

		BigDecimal jointProbability = BigDecimal.ONE;

		NGramIndexNode match = null;
		for (int i = 0; i < currentSolutionString.length() - order; i++) {
			match = letterMarkovModel.findLongest(currentSolutionString.substring(i, i + order));

			if (match != null && match.getTerminalInfo().getLevel() == letterMarkovModel.getOrder()) {
				jointProbability = jointProbability.multiply(match.getTerminalInfo().getProbability(), MathContext.DECIMAL128);
			} else {
				jointProbability = jointProbability.multiply(unknownLetterNGramProbability, MathContext.DECIMAL128);
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
				jointProbability = jointProbability.multiply(unknownWordProbability, MathContext.DECIMAL128);
			} else {
				log.debug("matchString: {}", match.getCumulativeStringValue());

				jointProbability = jointProbability.multiply(match.getTerminalInfo().getProbability(), MathContext.DECIMAL128);
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