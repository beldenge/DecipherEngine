package com.ciphertool.zodiacengine.genetic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LiberalCrossoverAlgorithmTest {
	private static Logger log = Logger.getLogger(LiberalCrossoverAlgorithmTest.class);

	private static ApplicationContext context;
	private static CrossoverAlgorithm crossoverAlgorithm;
	private static Population population;
	private static FitnessEvaluator fitnessEvaluator;

	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-genetic.xml");
		log.info("Spring context created successfully!");

		fitnessEvaluator = (FitnessEvaluator) context.getBean("fitnessEvaluator");

		crossoverAlgorithm = (CrossoverAlgorithm) context.getBean("crossoverAlgorithm");

		population = (Population) context.getBean("population");

		population.populateIndividuals(100);
		population.evaluateFitness();
	}

	/**
	 * Without setting these to null, the humongous wordMap will not be garbage
	 * collected and subsequent unit tests may encounter an out of memory
	 * exception
	 */
	@AfterClass
	public static void cleanUp() {
		context = null;
	}

	@Test
	public void testCrossover() {
		Chromosome mom = population.spinRouletteWheel();
		log.info("Mom: " + mom);

		Chromosome dad = population.spinRouletteWheel();
		log.info("Dad: " + dad);

		Chromosome child = crossoverAlgorithm.crossover(mom, dad);

		assertNotNull(child);

		int genesBefore = child.getGenes().size();

		log.info("Child: " + child);

		int count = 0;
		for (Gene gene : child.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(((SolutionChromosome) child).getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				assertEquals(((SolutionChromosome) child).getPlaintextCharacters().get(count)
						.getPlaintextId().getCiphertextId(), count);

				count++;
			}
		}

		assertEquals(child.actualSize().intValue(), ((SolutionChromosome) child)
				.getPlaintextCharacters().size());

		log.info("Solution size: " + ((SolutionChromosome) child).getPlaintextCharacters().size());

		assertEquals(genesBefore, child.getGenes().size());
	}
}
