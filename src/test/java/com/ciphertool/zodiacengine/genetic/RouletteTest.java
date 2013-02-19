package com.ciphertool.zodiacengine.genetic;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.Population;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;

public class RouletteTest {
	private static Logger log = Logger.getLogger(RouletteTest.class);

	private static Population population = new Population();

	@BeforeClass
	public static void setUp() {
		SolutionChromosome solution1 = new SolutionChromosome();
		solution1.setFitness(1.0);
		solution1.setDirty(false);
		population.addIndividual(solution1);

		SolutionChromosome solution2 = new SolutionChromosome();
		solution2.setFitness(10.0);
		solution2.setDirty(false);
		population.addIndividual(solution2);

		SolutionChromosome solution3 = new SolutionChromosome();
		solution3.setFitness(100.0);
		solution3.setDirty(false);
		population.addIndividual(solution3);

		SolutionChromosome solution4 = new SolutionChromosome();
		solution4.setFitness(1000.0);
		solution4.setDirty(false);
		population.addIndividual(solution4);

		SolutionChromosome solution5 = new SolutionChromosome();
		solution5.setFitness(10000.0);
		solution5.setDirty(false);
		population.addIndividual(solution5);

		SolutionChromosome solution6 = new SolutionChromosome();
		solution6.setFitness(0.9);
		solution6.setDirty(false);
		population.addIndividual(solution6);

		SolutionChromosome solution7 = new SolutionChromosome();
		solution7.setFitness(9.0);
		solution7.setDirty(false);
		population.addIndividual(solution7);

		SolutionChromosome solution8 = new SolutionChromosome();
		solution8.setFitness(99.0);
		solution8.setDirty(false);
		population.addIndividual(solution8);

		SolutionChromosome solution9 = new SolutionChromosome();
		solution9.setFitness(999.0);
		solution9.setDirty(false);
		population.addIndividual(solution9);

		SolutionChromosome solution10 = new SolutionChromosome();
		solution10.setFitness(9999.0);
		solution10.setDirty(false);
		population.addIndividual(solution10);
	}

	@Test
	public void testSpinIndexRouletteWheel() {
		int winningNumber = -1;

		winningNumber = population.spinIndexRouletteWheel();

		assertTrue(winningNumber > -1);

		log.info("And the winner's fitness is: "
				+ population.getIndividuals().get(winningNumber).getFitness());
	}
}
