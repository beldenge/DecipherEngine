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
