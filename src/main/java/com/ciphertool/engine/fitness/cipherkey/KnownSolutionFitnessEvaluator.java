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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.engine.entities.CipherKeyGene;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.fitness.FitnessEvaluator;

public class KnownSolutionFitnessEvaluator implements FitnessEvaluator {
	private Logger						log				= LoggerFactory.getLogger(getClass());

	private static CipherKeyChromosome	knownSolution	= new CipherKeyChromosome();

	static {
		knownSolution.putGene("tri", new CipherKeyGene(knownSolution, "i"));
		knownSolution.putGene("lrbox", new CipherKeyGene(knownSolution, "l"));
		knownSolution.putGene("p", new CipherKeyGene(knownSolution, "i"));
		knownSolution.putGene("forslash", new CipherKeyGene(knownSolution, "k"));
		knownSolution.putGene("z", new CipherKeyGene(knownSolution, "e"));
		knownSolution.putGene("u", new CipherKeyGene(knownSolution, "i"));
		knownSolution.putGene("b", new CipherKeyGene(knownSolution, "l"));
		knownSolution.putGene("backk", new CipherKeyGene(knownSolution, "i"));
		knownSolution.putGene("o", new CipherKeyGene(knownSolution, "n"));
		knownSolution.putGene("r", new CipherKeyGene(knownSolution, "g"));
		knownSolution.putGene("pi", new CipherKeyGene(knownSolution, "p"));
		knownSolution.putGene("backp", new CipherKeyGene(knownSolution, "e"));
		knownSolution.putGene("x", new CipherKeyGene(knownSolution, "o"));
		knownSolution.putGene("w", new CipherKeyGene(knownSolution, "e"));
		knownSolution.putGene("v", new CipherKeyGene(knownSolution, "b"));
		knownSolution.putGene("plus", new CipherKeyGene(knownSolution, "e"));
		knownSolution.putGene("backe", new CipherKeyGene(knownSolution, "c"));
		knownSolution.putGene("g", new CipherKeyGene(knownSolution, "a"));
		knownSolution.putGene("y", new CipherKeyGene(knownSolution, "u"));
		knownSolution.putGene("f", new CipherKeyGene(knownSolution, "s"));
		knownSolution.putGene("circledot", new CipherKeyGene(knownSolution, "e"));
		knownSolution.putGene("h", new CipherKeyGene(knownSolution, "t"));
		knownSolution.putGene("boxdot", new CipherKeyGene(knownSolution, "s"));
		knownSolution.putGene("k", new CipherKeyGene(knownSolution, "s"));
		knownSolution.putGene("anchor", new CipherKeyGene(knownSolution, "o"));
		knownSolution.putGene("backq", new CipherKeyGene(knownSolution, "m"));
		knownSolution.putGene("m", new CipherKeyGene(knownSolution, "h"));
		knownSolution.putGene("j", new CipherKeyGene(knownSolution, "f"));
		knownSolution.putGene("carrot", new CipherKeyGene(knownSolution, "n"));
		knownSolution.putGene("i", new CipherKeyGene(knownSolution, "t"));
		knownSolution.putGene("tridot", new CipherKeyGene(knownSolution, "s"));
		knownSolution.putGene("t", new CipherKeyGene(knownSolution, "o"));
		knownSolution.putGene("flipt", new CipherKeyGene(knownSolution, "r"));
		knownSolution.putGene("n", new CipherKeyGene(knownSolution, "e"));
		knownSolution.putGene("q", new CipherKeyGene(knownSolution, "f"));
		knownSolution.putGene("d", new CipherKeyGene(knownSolution, "n"));
		knownSolution.putGene("fullcircle", new CipherKeyGene(knownSolution, "t"));
		knownSolution.putGene("horstrike", new CipherKeyGene(knownSolution, "h"));
		knownSolution.putGene("s", new CipherKeyGene(knownSolution, "a"));
		knownSolution.putGene("vertstrike", new CipherKeyGene(knownSolution, "n"));
		knownSolution.putGene("fullbox", new CipherKeyGene(knownSolution, "l"));
		knownSolution.putGene("a", new CipherKeyGene(knownSolution, "w"));
		knownSolution.putGene("backf", new CipherKeyGene(knownSolution, "d"));
		knownSolution.putGene("backl", new CipherKeyGene(knownSolution, "a"));
		knownSolution.putGene("e", new CipherKeyGene(knownSolution, "e"));
		knownSolution.putGene("l", new CipherKeyGene(knownSolution, "t"));
		knownSolution.putGene("backd", new CipherKeyGene(knownSolution, "o"));
		knownSolution.putGene("backr", new CipherKeyGene(knownSolution, "r"));
		knownSolution.putGene("backslash", new CipherKeyGene(knownSolution, "r"));
		knownSolution.putGene("fulltri", new CipherKeyGene(knownSolution, "a"));
		knownSolution.putGene("zodiac", new CipherKeyGene(knownSolution, "d"));
		knownSolution.putGene("backc", new CipherKeyGene(knownSolution, "v"));
		knownSolution.putGene("backj", new CipherKeyGene(knownSolution, "x"));
		knownSolution.putGene("box", new CipherKeyGene(knownSolution, "y"));
	}

	/**
	 * Default no-args constructor
	 */
	public KnownSolutionFitnessEvaluator() {
	}

	@Override
	public Double evaluate(Chromosome chromosome) {
		double total = 0.0;

		if (knownSolution.getGenes().size() != ((CipherKeyChromosome) chromosome).getGenes().size()) {
			log.error("Current solution size of " + ((CipherKeyChromosome) chromosome).getGenes().size()
					+ " does not match the known solution size of " + knownSolution.getGenes().size()
					+ ".  This will cause innacurate fitness calculations.  Solution: " + chromosome);
		}

		for (String key : knownSolution.getGenes().keySet()) {
			if (knownSolution.getGenes().get(key).equals(((CipherKeyGene) ((CipherKeyChromosome) chromosome).getGenes().get(key)))) {
				total++;
			}
		}

		double proximityToKnownSolution = (total / (double) ((CipherKeyChromosome) chromosome).getGenes().size())
				* 100.0;

		if (log.isDebugEnabled()) {
			log.debug("Solution " + ((CipherKeyChromosome) chromosome).getId() + " has a confidence level of: "
					+ proximityToKnownSolution);
		}

		return proximityToKnownSolution;
	}

	@Override
	public void setGeneticStructure(Object cipher) {
		// Not needed for this implementation
	}

	@Override
	public String getDisplayName() {
		return "Known Solution";
	}
}
