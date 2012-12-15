package com.ciphertool.zodiacengine.genetic.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.algorithms.crossover.ConservativeCentromereCrossoverAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.genetic.GeneticAlgorithmTestBase;
import com.ciphertool.zodiacengine.genetic.adapters.PlaintextSequence;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;

public class ConservativeCentromereCrossoverAlgorithmTest extends GeneticAlgorithmTestBase {
	private static ConservativeCentromereCrossoverAlgorithm crossoverAlgorithm = new ConservativeCentromereCrossoverAlgorithm();
	private static SolutionChromosome dummySolution;
	private Logger log = Logger.getLogger(getClass());

	@BeforeClass
	public static void setUp() {
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
	@SuppressWarnings("unchecked")
	public void testFindPotentialCentromeres() {
		Class<?>[] params = { Chromosome.class, Chromosome.class };

		Object[] args = { knownSolution, dummySolution };
		List<Integer> potentialCentromeres = null;

		try {
			potentialCentromeres = (List<Integer>) invokeMethod(crossoverAlgorithm,
					"findPotentialCentromeres", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		List<Integer> expectedCentromeres = Arrays.asList(new Integer(208), new Integer(238),
				new Integer(242), new Integer(274), new Integer(303), new Integer(391));

		assertEquals(potentialCentromeres, expectedCentromeres);
	}

	@Test
	public void testCrossover() {
		List<Chromosome> children = crossoverAlgorithm.crossover(knownSolution, dummySolution);

		assertFalse(children.get(0).equals(children.get(1)));
		assertEquals(children.get(0).actualSize(), dummySolution.actualSize());
		assertEquals(children.get(1).actualSize(), knownSolution.actualSize());

		log.info(children);
	}
}
