package com.ciphertool.zodiacengine.genetic.algorithms.mutation;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.algorithms.mutation.ConservativeMutationAlgorithm;

public class ConservativeMutationAlgorithmTest extends MutationAlgorithmTestBase {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ConservativeMutationAlgorithmTest.class);

	@BeforeClass
	public static void setUp() {
		mutationAlgorithm = new ConservativeMutationAlgorithm();
		((ConservativeMutationAlgorithm) mutationAlgorithm)
				.setGeneListDao(new WordGeneListDaoMock());
	}

	@Test
	public void testMutateGene() {
		super.testMutateGene();
	}

	@Test
	public void testMutateInvalidGene() {
		super.testMutateInvalidGene();
	}

	@Test
	public void testMutateRandomGene() {
		super.testMutateRandomGene();
	}
}
