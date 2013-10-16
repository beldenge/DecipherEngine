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

package com.ciphertool.zodiacengine.algorithms.mutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.algorithms.mutation.GroupMutationAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;
import com.ciphertool.zodiacengine.mocks.WordGeneListDaoMock;

public class GroupMutationAlgorithmTest extends MutationAlgorithmTestBase {
	private static Logger log = Logger.getLogger(GroupMutationAlgorithmTest.class);

	private final static int MAX_MUTATIONS = 5;

	@BeforeClass
	public static void setUp() {
		mutationAlgorithm = new GroupMutationAlgorithm();
		((GroupMutationAlgorithm) mutationAlgorithm).setMaxMutationsPerChromosome(MAX_MUTATIONS);
		((GroupMutationAlgorithm) mutationAlgorithm).setGeneListDao(new WordGeneListDaoMock());
	}

	@Test
	public void testDoesNotOverlapPreviousMutationPreceding() {

		Map<Integer, Integer> geneIndices = new HashMap<Integer, Integer>();
		geneIndices.put(5, 10);
		Integer proposedBeginIndex = 0;
		Integer proposedEndIndex = 1;

		Class<?>[] params = { Map.class, Integer.class, Integer.class };
		Object[] args = { geneIndices, proposedBeginIndex, proposedEndIndex };
		Object result = null;
		try {
			result = invokeMethod(mutationAlgorithm, "overlapsPreviousMutation", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertFalse(((boolean) result));
	}

	@Test
	public void testDoesNotOverlapPreviousMutationFollowing() {

		Map<Integer, Integer> geneIndices = new HashMap<Integer, Integer>();
		geneIndices.put(5, 10);
		Integer proposedBeginIndex = 11;
		Integer proposedEndIndex = 12;

		Class<?>[] params = { Map.class, Integer.class, Integer.class };
		Object[] args = { geneIndices, proposedBeginIndex, proposedEndIndex };
		Object result = null;
		try {
			result = invokeMethod(mutationAlgorithm, "overlapsPreviousMutation", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertFalse(((boolean) result));
	}

	@Test
	public void testOverlapsPreviousMutationPreceding() {

		Map<Integer, Integer> geneIndices = new HashMap<Integer, Integer>();
		geneIndices.put(5, 10);
		Integer proposedBeginIndex = 3;
		Integer proposedEndIndex = 6;

		Class<?>[] params = { Map.class, Integer.class, Integer.class };
		Object[] args = { geneIndices, proposedBeginIndex, proposedEndIndex };
		Object result = null;
		try {
			result = invokeMethod(mutationAlgorithm, "overlapsPreviousMutation", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertTrue(((boolean) result));
	}

	@Test
	public void testEqualsPreviousMutation() {

		Map<Integer, Integer> geneIndices = new HashMap<Integer, Integer>();
		geneIndices.put(5, 10);
		Integer proposedBeginIndex = 5;
		Integer proposedEndIndex = 10;

		Class<?>[] params = { Map.class, Integer.class, Integer.class };
		Object[] args = { geneIndices, proposedBeginIndex, proposedEndIndex };
		Object result = null;
		try {
			result = invokeMethod(mutationAlgorithm, "overlapsPreviousMutation", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertTrue(((boolean) result));
	}

	@Test
	public void testOverlapsPreviousMutationFollowing() {

		Map<Integer, Integer> geneIndices = new HashMap<Integer, Integer>();
		geneIndices.put(5, 10);
		Integer proposedBeginIndex = 9;
		Integer proposedEndIndex = 12;

		Class<?>[] params = { Map.class, Integer.class, Integer.class };
		Object[] args = { geneIndices, proposedBeginIndex, proposedEndIndex };
		Object result = null;
		try {
			result = invokeMethod(mutationAlgorithm, "overlapsPreviousMutation", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertTrue(((boolean) result));
	}

	@Test
	public void testEqualsBeginningIndex() {

		Map<Integer, Integer> geneIndices = new HashMap<Integer, Integer>();
		geneIndices.put(5, 10);
		Integer proposedBeginIndex = 3;
		Integer proposedEndIndex = 5;

		Class<?>[] params = { Map.class, Integer.class, Integer.class };
		Object[] args = { geneIndices, proposedBeginIndex, proposedEndIndex };
		Object result = null;
		try {
			result = invokeMethod(mutationAlgorithm, "overlapsPreviousMutation", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertTrue(((boolean) result));
	}

	@Test
	public void testEqualsEndIndex() {

		Map<Integer, Integer> geneIndices = new HashMap<Integer, Integer>();
		geneIndices.put(5, 10);
		Integer proposedBeginIndex = 10;
		Integer proposedEndIndex = 12;

		Class<?>[] params = { Map.class, Integer.class, Integer.class };
		Object[] args = { geneIndices, proposedBeginIndex, proposedEndIndex };
		Object result = null;
		try {
			result = invokeMethod(mutationAlgorithm, "overlapsPreviousMutation", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertTrue(((boolean) result));
	}

	@Test
	public void testEncompassesPreviousMatch() {

		Map<Integer, Integer> geneIndices = new HashMap<Integer, Integer>();
		geneIndices.put(5, 10);
		Integer proposedBeginIndex = 4;
		Integer proposedEndIndex = 11;

		Class<?>[] params = { Map.class, Integer.class, Integer.class };
		Object[] args = { geneIndices, proposedBeginIndex, proposedEndIndex };
		Object result = null;
		try {
			result = invokeMethod(mutationAlgorithm, "overlapsPreviousMutation", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertTrue(((boolean) result));
	}

	@Test
	public void testInsidePreviousMatch() {

		Map<Integer, Integer> geneIndices = new HashMap<Integer, Integer>();
		geneIndices.put(5, 10);
		Integer proposedBeginIndex = 6;
		Integer proposedEndIndex = 9;

		Class<?>[] params = { Map.class, Integer.class, Integer.class };
		Object[] args = { geneIndices, proposedBeginIndex, proposedEndIndex };
		Object result = null;
		try {
			result = invokeMethod(mutationAlgorithm, "overlapsPreviousMutation", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertTrue(((boolean) result));
	}

	@Test
	public void testRevertGenes() {
		Chromosome chromosome = new SolutionChromosome(zodiac408.getId(), 0, 0, 0, zodiac408
				.getRows(), zodiac408.getColumns());
		List<Gene> genes = new ArrayList<Gene>();
		int beginIndex = 2;

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		chromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		chromosome.addGene(wordGene2);

		Word word3 = new Word(new WordId("is", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		genes.add(wordGene3);

		Word word4 = new Word(new WordId("awesome", 'N'));
		WordGene wordGene4 = new WordGene(word4, solutionChromosome, 0);
		genes.add(wordGene4);

		Class<?>[] params = { Chromosome.class, List.class, int.class };
		Object[] args = { chromosome, genes, beginIndex };

		assertEquals(chromosome.getGenes().size(), 2);
		assertEquals(chromosome.getGenes().get(0), wordGene1);
		assertEquals(chromosome.getGenes().get(1), wordGene2);

		try {
			invokeMethod(mutationAlgorithm, "revertGenes", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertEquals(chromosome.getGenes().size(), 4);
		assertEquals(chromosome.getGenes().get(0), wordGene1);
		assertEquals(chromosome.getGenes().get(1), wordGene2);
		assertEquals(chromosome.getGenes().get(2), wordGene3);
		assertEquals(chromosome.getGenes().get(3), wordGene4);
	}

	@Test
	public void testRemoveGenes() {
		Chromosome chromosome = new SolutionChromosome(zodiac408.getId(), 0, 0, 0, zodiac408
				.getRows(), zodiac408.getColumns());
		int beginIndex = 3;
		int numGenes = 2;

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		chromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		chromosome.addGene(wordGene2);

		Word word3 = new Word(new WordId("is", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		chromosome.addGene(wordGene3);

		Word word4 = new Word(new WordId("really", 'N'));
		WordGene wordGene4 = new WordGene(word4, solutionChromosome, 0);
		chromosome.addGene(wordGene4);

		Word word5 = new Word(new WordId("just", 'N'));
		WordGene wordGene5 = new WordGene(word5, solutionChromosome, 0);
		chromosome.addGene(wordGene5);

		Word word6 = new Word(new WordId("awesome", 'N'));
		WordGene wordGene6 = new WordGene(word6, solutionChromosome, 0);
		chromosome.addGene(wordGene6);

		Class<?>[] params = { Chromosome.class, int.class, int.class };
		Object[] args = { chromosome, beginIndex, numGenes };

		assertEquals(chromosome.getGenes().size(), 6);
		assertEquals(chromosome.getGenes().get(0), wordGene1);
		assertEquals(chromosome.getGenes().get(1), wordGene2);
		assertEquals(chromosome.getGenes().get(2), wordGene3);
		assertEquals(chromosome.getGenes().get(3), wordGene4);
		assertEquals(chromosome.getGenes().get(4), wordGene5);
		assertEquals(chromosome.getGenes().get(5), wordGene6);

		try {
			invokeMethod(mutationAlgorithm, "removeGenes", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertEquals(chromosome.getGenes().size(), 4);
		assertEquals(chromosome.getGenes().get(0), wordGene1);
		assertEquals(chromosome.getGenes().get(1), wordGene2);
		assertEquals(chromosome.getGenes().get(2), wordGene3);
		assertEquals(chromosome.getGenes().get(3), wordGene6);
	}

	@Test
	public void testInsertRandomGenes() {
		Chromosome chromosome = new SolutionChromosome(zodiac408.getId(), 0, 0, 0, zodiac408
				.getRows(), zodiac408.getColumns());
		int beginIndex = 3;
		int beginningSequenceIndex = 13;
		int sequencesRemoved = 14;

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		chromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		chromosome.addGene(wordGene2);

		Word word3 = new Word(new WordId("is", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		chromosome.addGene(wordGene3);

		Word word4 = new Word(new WordId("awesome", 'N'));
		WordGene wordGene4 = new WordGene(word4, solutionChromosome, 0);
		chromosome.addGene(wordGene4);

		Class<?>[] params = { Chromosome.class, int.class, int.class, int.class };
		Object[] args = { chromosome, beginIndex, beginningSequenceIndex, sequencesRemoved };

		log.info("Before: " + chromosome);

		assertEquals(chromosome.getGenes().size(), 4);
		assertEquals(chromosome.getGenes().get(0), wordGene1);
		assertEquals(chromosome.getGenes().get(1), wordGene2);
		assertEquals(chromosome.getGenes().get(2), wordGene3);
		assertEquals(chromosome.getGenes().get(3), wordGene4);

		try {
			invokeMethod(mutationAlgorithm, "insertRandomGenes", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		log.info("After: " + chromosome);

		assertTrue(chromosome.getGenes().size() > 4);
		assertEquals(chromosome.getGenes().get(0), wordGene1);
		assertEquals(chromosome.getGenes().get(1), wordGene2);
		assertEquals(chromosome.getGenes().get(2), wordGene3);
		assertEquals(chromosome.getGenes().get(chromosome.getGenes().size() - 1), wordGene4);
	}

	@Test
	public void testMutateGeneGroup() {
		Chromosome chromosome = new SolutionChromosome(zodiac408.getId(), 0, 0, 0, zodiac408
				.getRows(), zodiac408.getColumns());
		int beginIndex = 1;
		int numGenes = 2;

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		chromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		chromosome.addGene(wordGene2);

		Word word3 = new Word(new WordId("is", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		chromosome.addGene(wordGene3);

		Word word4 = new Word(new WordId("awesome", 'N'));
		WordGene wordGene4 = new WordGene(word4, solutionChromosome, 0);
		chromosome.addGene(wordGene4);

		Class<?>[] params = { Chromosome.class, int.class, int.class };
		Object[] args = { chromosome, beginIndex, numGenes };

		log.info("Before: " + chromosome);

		assertEquals(chromosome.getGenes().size(), 4);
		assertEquals(chromosome.getGenes().get(0), wordGene1);
		assertEquals(chromosome.getGenes().get(1), wordGene2);
		assertEquals(chromosome.getGenes().get(2), wordGene3);
		assertEquals(chromosome.getGenes().get(3), wordGene4);

		try {
			invokeMethod(mutationAlgorithm, "mutateGeneGroup", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		log.info("After: " + chromosome);

		assertTrue(chromosome.getGenes().size() >= 3);
		assertEquals(chromosome.getGenes().get(0), wordGene1);
		assertEquals(chromosome.getGenes().get(chromosome.getGenes().size() - 1), wordGene4);
	}

	@Test
	public void testExceedsChromosomeSize() {
		Chromosome chromosome = new SolutionChromosome(zodiac408.getId(), 0, 0, 0, zodiac408
				.getRows(), zodiac408.getColumns());
		int proposedEndIndex = 2;

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		chromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		chromosome.addGene(wordGene2);

		Class<?>[] params = { Chromosome.class, int.class };
		Object[] args = { chromosome, proposedEndIndex };

		Object result = null;
		try {
			result = invokeMethod(mutationAlgorithm, "exceedsChromosomeSize", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertTrue((boolean) result);
	}

	@Test
	public void testDoesNotExceedChromosomeSize() {
		Chromosome chromosome = new SolutionChromosome(zodiac408.getId(), 0, 0, 0, zodiac408
				.getRows(), zodiac408.getColumns());
		int proposedEndIndex = 1;

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		chromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		chromosome.addGene(wordGene2);

		Class<?>[] params = { Chromosome.class, int.class };
		Object[] args = { chromosome, proposedEndIndex };

		Object result = null;
		try {
			result = invokeMethod(mutationAlgorithm, "exceedsChromosomeSize", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertFalse((boolean) result);
	}
}
