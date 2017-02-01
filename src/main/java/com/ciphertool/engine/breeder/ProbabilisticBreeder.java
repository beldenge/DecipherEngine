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

package com.ciphertool.engine.breeder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.engine.entities.CipherKeyGene;
import com.ciphertool.genetics.Breeder;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.sherlock.MathConstants;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;

public class ProbabilisticBreeder implements Breeder {
	private Logger							log							= LoggerFactory.getLogger(getClass());

	private static List<LetterProbability>	letterUnigramProbabilities	= new ArrayList<>();
	private MarkovModel						letterMarkovModel;
	private Cipher							cipher;
	private static final String[]			KEYS						= { "a", "anchor", "b", "backc", "backd",
			"backe", "backf", "backj", "backk", "backl", "backp", "backq", "backr", "backslash", "box", "boxdot",
			"carrot", "circledot", "d", "e", "f", "flipt", "forslash", "fullbox", "fullcircle", "fulltri", "g", "h",
			"horstrike", "i", "j", "k", "l", "lrbox", "m", "n", "o", "p", "pi", "plus", "q", "r", "s", "t", "tri",
			"tridot", "u", "v", "vertstrike", "w", "x", "y", "z", "zodiac" };

	@PostConstruct
	public void init() {
		long total = 0;
		for (Map.Entry<Character, NGramIndexNode> entry : letterMarkovModel.getRootNode().getTransitions().entrySet()) {
			if (!entry.getKey().equals(' ')) {
				total += entry.getValue().getTerminalInfo().getCount();
			}
		}

		BigDecimal probability;
		for (Map.Entry<Character, NGramIndexNode> entry : letterMarkovModel.getRootNode().getTransitions().entrySet()) {
			if (!entry.getKey().equals(' ')) {
				probability = BigDecimal.valueOf(entry.getValue().getTerminalInfo().getCount()).divide(BigDecimal.valueOf(total), MathConstants.PREC_10_HALF_UP);

				letterUnigramProbabilities.add(new LetterProbability(entry.getKey(), probability));
			}
		}
	}

	@Override
	public Chromosome breed() {
		CipherKeyChromosome chromosome = new CipherKeyChromosome(cipher, KEYS.length);

		RouletteSampler<LetterProbability> rouletteSampler = new RouletteSampler<>();
		Collections.sort(letterUnigramProbabilities);
		BigDecimal totalProbability = rouletteSampler.reIndex(letterUnigramProbabilities);

		cipher.getCiphertextCharacters().stream().map(ciphertext -> ciphertext.getValue()).distinct().forEach(ciphertext -> {
			// Pick a plaintext at random according to the language model
			String nextPlaintext = letterUnigramProbabilities.get(rouletteSampler.getNextIndex(letterUnigramProbabilities, totalProbability)).getValue().toString();

			CipherKeyGene newGene = new CipherKeyGene();
			newGene.setValue(nextPlaintext);
			chromosome.putGene(ciphertext, newGene);
		});

		if (log.isDebugEnabled()) {
			log.debug(chromosome.toString());
		}

		return chromosome;
	}

	@Override
	public void setGeneticStructure(Object obj) {
		this.cipher = (Cipher) cipher;
	}

	/**
	 * @param letterMarkovModel
	 *            the letterMarkovModel to set
	 */
	@Required
	public void setLetterMarkovModel(MarkovModel letterMarkovModel) {
		this.letterMarkovModel = letterMarkovModel;
	}
}
