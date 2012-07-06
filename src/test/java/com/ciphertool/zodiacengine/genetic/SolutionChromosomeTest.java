package com.ciphertool.zodiacengine.genetic;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SolutionChromosomeTest {
	@Test
	public void testGetNullPlaintextCharacters() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		assertNotNull(solutionChromosome.getPlaintextCharacters());
	}
}
