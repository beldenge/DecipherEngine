/**
 * Copyright 2012 George Belden
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

package com.ciphertool.zodiacengine.genetic.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.algorithms.crossover.ConservativeCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.ConservativeUnevaluatedCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.LiberalCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.LiberalUnevaluatedCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.LowestCommonGroupCrossoverAlgorithm;
import com.ciphertool.genetics.algorithms.crossover.LowestCommonGroupUnevaluatedCrossoverAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.util.ChromosomeHelper;
import com.ciphertool.genetics.util.FitnessEvaluator;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.genetic.GeneticAlgorithmTestBase;
import com.ciphertool.zodiacengine.genetic.adapters.PlaintextSequence;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.util.CipherSolutionKnownSolutionFitnessEvaluator;

public class CrossoverAlgorithmTest extends GeneticAlgorithmTestBase {
	private static Logger log = Logger.getLogger(CrossoverAlgorithmTest.class);

	private static FitnessEvaluator fitnessEvaluator;
	private static SolutionChromosome dummySolution;

	@BeforeClass
	public static void setUp() {
		fitnessEvaluator = new CipherSolutionKnownSolutionFitnessEvaluator();
		fitnessEvaluator.setGeneticStructure(zodiac408);

		dummySolution = knownSolution.clone();

		dummySolution.getGenes().get(0).insertSequence(
				0,
				new PlaintextSequence(new PlaintextId(dummySolution, 0), "i", dummySolution
						.getGenes().get(0)));

		for (Plaintext plaintext : dummySolution.getPlaintextCharacters()) {
			plaintext.setValue("*");
		}
	}

	@Test
	public void testLiberalCrossoverAlgorithm() {
		LiberalCrossoverAlgorithm crossoverAlgorithm = new LiberalCrossoverAlgorithm();
		crossoverAlgorithm.setFitnessEvaluator(fitnessEvaluator);
		crossoverAlgorithm.setChromosomeHelper(new ChromosomeHelper());

		validateCrossoverAlgorithm(crossoverAlgorithm, false);
	}

	@Test
	public void testConservativeCrossoverAlgorithm() {
		ConservativeCrossoverAlgorithm crossoverAlgorithm = new ConservativeCrossoverAlgorithm();
		crossoverAlgorithm.setFitnessEvaluator(fitnessEvaluator);

		validateCrossoverAlgorithm(crossoverAlgorithm, true);
	}

	@Test
	public void testLowestCommonGroupCrossoverAlgorithm() {
		LowestCommonGroupCrossoverAlgorithm crossoverAlgorithm = new LowestCommonGroupCrossoverAlgorithm();
		crossoverAlgorithm.setFitnessEvaluator(fitnessEvaluator);

		validateCrossoverAlgorithm(crossoverAlgorithm, true);
	}

	@Test
	public void testLiberalUnevaluatedCrossoverAlgorithm() {
		LiberalUnevaluatedCrossoverAlgorithm crossoverAlgorithm = new LiberalUnevaluatedCrossoverAlgorithm();
		crossoverAlgorithm.setFitnessEvaluator(fitnessEvaluator);
		crossoverAlgorithm.setChromosomeHelper(new ChromosomeHelper());

		validateCrossoverAlgorithm(crossoverAlgorithm, false);
	}

	@Test
	public void testConservativeUnevaluatedCrossoverAlgorithm() {
		ConservativeUnevaluatedCrossoverAlgorithm crossoverAlgorithm = new ConservativeUnevaluatedCrossoverAlgorithm();
		crossoverAlgorithm.setFitnessEvaluator(fitnessEvaluator);

		validateCrossoverAlgorithm(crossoverAlgorithm, true);
	}

	@Test
	public void testLowestCommonGroupUnevaluatedCrossoverAlgorithm() {
		LowestCommonGroupUnevaluatedCrossoverAlgorithm crossoverAlgorithm = new LowestCommonGroupUnevaluatedCrossoverAlgorithm();
		crossoverAlgorithm.setFitnessEvaluator(fitnessEvaluator);

		validateCrossoverAlgorithm(crossoverAlgorithm, true);
	}

	private void validateCrossoverAlgorithm(CrossoverAlgorithm crossoverAlgorithm,
			boolean validatePlaintextSequences) {
		Chromosome mom = knownSolution;
		log.info("Mom: " + mom);

		Chromosome dad = dummySolution;
		log.info("Dad: " + dad);

		List<Chromosome> children = crossoverAlgorithm.crossover(mom, dad);

		assertNotNull(children);
		assertNotNull(children.get(0));

		for (Chromosome child : children) {
			validateChild(child);

			/*
			 * Only for certain implementations
			 */
			if (validatePlaintextSequences) {
				validatePlaintextSequences(child, mom, dad);
			}
		}
	}

	private void validateChild(Chromosome child) {
		log.info("Child: " + child);

		int count = 0;
		for (Gene gene : child.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(((SolutionChromosome) child).getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				assertEquals(((SolutionChromosome) child).getPlaintextCharacters().get(count)
						.getId().getCiphertextId(), count);

				count++;
			}
		}

		assertEquals(child.actualSize().intValue(), ((SolutionChromosome) child)
				.getPlaintextCharacters().size());

		log.info("Solution size: " + ((SolutionChromosome) child).getPlaintextCharacters().size());
	}

	private void validatePlaintextSequences(Chromosome firstChild, Chromosome mom, Chromosome dad) {
		for (Plaintext plaintext : ((SolutionChromosome) firstChild).getPlaintextCharacters()) {
			if (((((SolutionChromosome) mom).actualSize() > plaintext.getId().getCiphertextId()) && !plaintext
					.getValue().equals(
							((SolutionChromosome) mom).getPlaintextCharacters().get(
									plaintext.getId().getCiphertextId()).getValue()))
					&& ((((SolutionChromosome) dad).actualSize() > plaintext.getId()
							.getCiphertextId()) && !plaintext.getValue().equals(
							((SolutionChromosome) dad).getPlaintextCharacters().get(
									plaintext.getId().getCiphertextId()).getValue()))) {
				fail("Plaintext value from child does not match Plaintext from either parent: "
						+ plaintext);
			}
		}
	}
}
