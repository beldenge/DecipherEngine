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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.algorithms.mutation.LiberalMutationAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.util.ChromosomeHelper;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.WordGene;
import com.ciphertool.zodiacengine.mocks.WordGeneListDaoMock;

public class LiberalMutationAlgorithmTest extends MutationAlgorithmTestBase {
	private static Logger log = Logger.getLogger(LiberalMutationAlgorithmTest.class);
	private final static int MAX_MUTATIONS = 5;

	@BeforeClass
	public static void setUp() {
		mutationAlgorithm = new LiberalMutationAlgorithm();
		((LiberalMutationAlgorithm) mutationAlgorithm).setGeneListDao(new WordGeneListDaoMock());
		((LiberalMutationAlgorithm) mutationAlgorithm).setMaxMutationsPerChromosome(MAX_MUTATIONS);

		ChromosomeHelper chromosomeHelper = new ChromosomeHelper();
		chromosomeHelper.setGeneListDao(new WordGeneListDaoMock());
		((LiberalMutationAlgorithm) mutationAlgorithm).setChromosomeHelper(chromosomeHelper);
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
		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		Class<?>[] params = { Chromosome.class, List.class };
		Object[] args = { solutionChromosome, new ArrayList<Integer>() };
		try {
			invokeMethod(mutationAlgorithm, "mutateRandomGene", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		/*
		 * At least one gene should be changed.
		 */
		assertFalse(((WordGene) solutionChromosome.getGenes().get(0)).getWordString().equals(
				word1.getId().getWord())
				&& ((WordGene) solutionChromosome.getGenes().get(1)).getWordString().equals(
						word2.getId().getWord()));

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
	}
}