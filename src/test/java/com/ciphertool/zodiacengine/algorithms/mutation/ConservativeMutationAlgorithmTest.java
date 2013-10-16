package com.ciphertool.zodiacengine.algorithms.mutation;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.algorithms.mutation.ConservativeMutationAlgorithm;
import com.ciphertool.zodiacengine.mocks.WordGeneListDaoMock;

public class ConservativeMutationAlgorithmTest extends MutationAlgorithmTestBase {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ConservativeMutationAlgorithmTest.class);
	private final static int MAX_MUTATIONS = 5;

	@BeforeClass
	public static void setUp() {
		mutationAlgorithm = new ConservativeMutationAlgorithm();
		((ConservativeMutationAlgorithm) mutationAlgorithm)
				.setMaxMutationsPerChromosome(MAX_MUTATIONS);
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
