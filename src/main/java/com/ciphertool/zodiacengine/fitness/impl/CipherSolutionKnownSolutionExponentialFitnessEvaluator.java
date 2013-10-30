/**
 * Copyright 2013 George Belden
 * 
 * This file is part of ZodiacEngine.
 * 
 * ZodiacEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ZodiacEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ZodiacEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.zodiacengine.fitness.impl;

import org.apache.log4j.Logger;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;
import com.ciphertool.zodiacengine.fitness.AbstractSolutionTruncatedEvaluatorBase;

public class CipherSolutionKnownSolutionExponentialFitnessEvaluator extends
		AbstractSolutionTruncatedEvaluatorBase implements FitnessEvaluator {

	private Logger log = Logger.getLogger(getClass());

	private static SolutionChromosome knownSolution;

	static {
		knownSolution = new SolutionChromosome();

		knownSolution.setRows(24);
		knownSolution.setColumns(17);

		/*
		 * PartOfSpeech is not used by the evaluator, so set it to something
		 * arbitrary
		 */
		WordGene nextWordGene;

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 0);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("like", 'X')), knownSolution, 1);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("killing", 'X')), knownSolution, 5);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("people", 'X')), knownSolution, 12);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution, 18);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 25);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 27);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("so", 'X')), knownSolution, 29);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("much", 'X')), knownSolution, 31);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("fun", 'X')), knownSolution, 35);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 38);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 40);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("more", 'X')), knownSolution, 42);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("fun", 'X')), knownSolution, 46);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("than", 'X')), knownSolution, 49);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("killing", 'X')), knownSolution, 53);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("wild", 'X')), knownSolution, 60);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("game", 'X')), knownSolution, 64);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("in", 'X')), knownSolution, 68);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 70);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("forrest", 'X')), knownSolution, 73);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution, 80);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("man", 'X')), knownSolution, 87);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 90);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 92);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("moat", 'X')), knownSolution, 95);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("dangeroue", 'X')), knownSolution, 99);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("anamal", 'X')), knownSolution, 108);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution, 114);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("all", 'X')), knownSolution, 116);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("to", 'X')), knownSolution, 119);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("kill", 'X')), knownSolution, 121);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("something", 'X')), knownSolution, 125);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("gives", 'X')), knownSolution, 134);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("me", 'X')), knownSolution, 139);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 141);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("moat", 'X')), knownSolution, 144);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("thrilling", 'X')), knownSolution, 148);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("experence", 'X')), knownSolution, 157);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 166);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 168);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("even", 'X')), knownSolution, 170);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("better", 'X')), knownSolution, 174);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("than", 'X')), knownSolution, 180);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("getting", 'X')), knownSolution, 184);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("your", 'X')), knownSolution, 191);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("rocks", 'X')), knownSolution, 195);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("off", 'X')), knownSolution, 200);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("with", 'X')), knownSolution, 203);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("a", 'X')), knownSolution, 207);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("girl", 'X')), knownSolution, 208);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 212);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("best", 'X')), knownSolution, 215);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("part", 'X')), knownSolution, 219);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution, 223);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 225);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("ia", 'X')), knownSolution, 227);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("thae", 'X')), knownSolution, 229);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("when", 'X')), knownSolution, 233);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 237);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("die", 'X')), knownSolution, 238);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 241);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 242);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("be", 'X')), knownSolution, 246);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("reborn", 'X')), knownSolution, 248);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("in", 'X')), knownSolution, 254);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("paradice", 'X')), knownSolution, 256);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("snd", 'X')), knownSolution, 264);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("all", 'X')), knownSolution, 267);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 270);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 273);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("have", 'X')), knownSolution, 274);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("killed", 'X')), knownSolution, 278);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 284);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("become", 'X')), knownSolution, 288);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 294);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("slaves", 'X')), knownSolution, 296);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 302);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 303);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("not", 'X')), knownSolution, 307);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("give", 'X')), knownSolution, 310);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("you", 'X')), knownSolution, 314);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 317);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("name", 'X')), knownSolution, 319);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution, 323);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("you", 'X')), knownSolution, 330);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 333);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("try", 'X')), knownSolution, 337);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("to", 'X')), knownSolution, 340);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("sloi", 'X')), knownSolution, 342);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("down", 'X')), knownSolution, 346);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("or", 'X')), knownSolution, 350);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("stop", 'X')), knownSolution, 352);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 356);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("collecting", 'X')), knownSolution, 358);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution, 368);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("slaves", 'X')), knownSolution, 370);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("for", 'X')), knownSolution, 376);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 379);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("afterlife", 'X')), knownSolution, 381);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("e", 'X')), knownSolution, 390);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("beorietemethhpiti", 'X')), knownSolution,
				391);
		knownSolution.addGene(nextWordGene);
	}

	/**
	 * Default no-args constructor
	 */
	public CipherSolutionKnownSolutionExponentialFitnessEvaluator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.zodiacengine.util.SolutionEvaluator#determineConfidenceLevel
	 * (com.ciphertool.zodiacengine.entities.Solution)
	 * 
	 * This is essentially a test class. It evaluates fitness by comparing
	 * against a known solution so that we can rule out issues with the
	 * evaluator when debugging issues.
	 */
	@Override
	public Double evaluate(Chromosome chromosome) {
		clearHasMatchValues((SolutionChromosome) chromosome);

		SolutionChromosome solution = (SolutionChromosome) chromosome;
		int total = 0;

		int lastRowBegin = (cipher.getColumns() * (cipher.getRows() - 1));

		for (int i = 0; i < lastRowBegin; i++) {
			if (knownSolution.getPlaintextCharacters().get(i).getValue().equals(
					solution.getPlaintextCharacters().get(i).getValue().toLowerCase())) {
				solution.getPlaintextCharacters().get(i).setHasMatch(true);
				total++;
			}
		}

		solution.setTotalMatches(total);

		int subTotal = 0;
		for (int i = 1; i <= total; i++) {
			subTotal += i;
		}

		double fitness = (double) subTotal;

		solution.setFitness(fitness);

		if (log.isDebugEnabled()) {
			log.debug("Solution " + solution.getId() + " has a confidence level of: " + fitness);
		}

		double proximityToKnownSolution = (((double) total) / lastRowBegin) * 100;

		return proximityToKnownSolution;
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		this.cipher = (Cipher) cipher;
		this.ciphertextKey = createKeyFromCiphertext();
	}
}