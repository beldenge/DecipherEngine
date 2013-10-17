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

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.algorithms.mutation.SingleSequenceMutationAlgorithm;
import com.ciphertool.genetics.dao.SequenceDao;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.dao.PlaintextSequenceDao;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.WordGene;

public class SingleSequenceMutationAlgorithmTest extends MutationAlgorithmTestBase {
	private static Logger log = Logger.getLogger(SingleSequenceMutationAlgorithmTest.class);
	private static Word word = new Word(new WordId("smile", 'N'));
	private static int beginCiphertextIndex = 0;
	private final static int MAX_MUTATIONS = 5;

	@BeforeClass
	public static void setUp() {
		mutationAlgorithm = new SingleSequenceMutationAlgorithm();
		((SingleSequenceMutationAlgorithm) mutationAlgorithm)
				.setMaxMutationsPerChromosome(MAX_MUTATIONS);

		SequenceDao sequenceDao = new PlaintextSequenceDao();
		((SingleSequenceMutationAlgorithm) mutationAlgorithm).setSequenceDao(sequenceDao);
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

	@Test
	public void testMutateSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		solutionChromosome.addGene(wordGene);

		Class<?>[] params = { Gene.class, int.class };
		Object[] args = { wordGene, 4 };
		try {
			invokeMethod(mutationAlgorithm, "mutateSequence", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertEquals(((PlaintextSequence) wordGene.getSequences().get(0)).getValue(), "s");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(1)).getValue(), "m");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(2)).getValue(), "i");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(3)).getValue(), "l");
		assertFalse(((PlaintextSequence) wordGene.getSequences().get(4)).getValue().equals("e"));

		log.info(wordGene);
	}

	@Test
	public void testMutateInvalidSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		solutionChromosome.addGene(wordGene);

		Class<?>[] params = { Gene.class, int.class };
		Object[] args = { wordGene, 5 };
		try {
			invokeMethod(mutationAlgorithm, "mutateSequence", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertEquals(((PlaintextSequence) wordGene.getSequences().get(0)).getValue(), "s");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(1)).getValue(), "m");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(2)).getValue(), "i");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(3)).getValue(), "l");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(4)).getValue(), "e");
	}

	@Test
	public void testMutateRandomSequence() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		solutionChromosome.addGene(wordGene);

		Class<?>[] params = { Gene.class };
		Object[] args = { wordGene };
		try {
			invokeMethod(mutationAlgorithm, "mutateRandomSequence", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertFalse(((PlaintextSequence) wordGene.getSequences().get(0)).getValue().equals("s")
				&& ((PlaintextSequence) wordGene.getSequences().get(1)).getValue().equals("m")
				&& ((PlaintextSequence) wordGene.getSequences().get(2)).getValue().equals("i")
				&& ((PlaintextSequence) wordGene.getSequences().get(3)).getValue().equals("l")
				&& ((PlaintextSequence) wordGene.getSequences().get(4)).getValue().equals("e"));

		log.info(wordGene);
	}
}
