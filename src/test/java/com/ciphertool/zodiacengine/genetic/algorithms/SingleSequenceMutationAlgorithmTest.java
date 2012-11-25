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

package com.ciphertool.zodiacengine.genetic.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.algorithms.SingleSequenceMutationAlgorithm;
import com.ciphertool.genetics.dao.SequenceDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.genetic.adapters.PlaintextSequence;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;
import com.ciphertool.zodiacengine.genetic.dao.PlaintextSequenceDao;

public class SingleSequenceMutationAlgorithmTest {
	private static Logger log = Logger.getLogger(SingleSequenceMutationAlgorithmTest.class);
	private static SingleSequenceMutationAlgorithm mutationAlgorithm = new SingleSequenceMutationAlgorithm();;
	private static Word word = new Word(new WordId("smile", 'N'));
	private static int beginCiphertextIndex = 0;
	private static SolutionChromosome solutionChromosome = new SolutionChromosome();

	@BeforeClass
	public static void setUp() {
		SequenceDao sequenceDao = new PlaintextSequenceDao();
		mutationAlgorithm.setSequenceDao(sequenceDao);
	}

	@Test
	public void testMutateGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(new Cipher(), 0, 0, 0);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		mutationAlgorithm.mutateGene(solutionChromosome, 0);

		assertFalse(wordGene1.getWordString().equals(word1.getId().getWord()));

		assertEquals(wordGene2.getWordString(), word2.getId().getWord());

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
	}

	@Test
	public void testMutateInvalidGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(new Cipher(), 0, 0, 0);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		mutationAlgorithm.mutateGene(solutionChromosome, 5);

		assertEquals(wordGene1.getWordString(), word1.getId().getWord());

		assertEquals(wordGene2.getWordString(), word2.getId().getWord());
	}

	@Test
	public void testMutateRandomGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(new Cipher(), 0, 0, 0);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		mutationAlgorithm.mutateRandomGene(solutionChromosome);

		assertFalse(wordGene1.getWordString().equals(word1.getId().getWord())
				&& wordGene2.getWordString().equals(word2.getId().getWord()));

		/*
		 * Only one gene should be changed.
		 */
		assertTrue(wordGene1.getWordString().equals(word1.getId().getWord())
				|| wordGene2.getWordString().equals(word2.getId().getWord()));

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
	}

	@Test
	public void testMutateSequence() {
		solutionChromosome = new SolutionChromosome();
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		solutionChromosome.addGene(wordGene);

		mutationAlgorithm.mutateSequence(wordGene, 4);

		assertEquals(((PlaintextSequence) wordGene.getSequences().get(0)).getValue(), "s");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(1)).getValue(), "m");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(2)).getValue(), "i");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(3)).getValue(), "l");
		assertFalse(((PlaintextSequence) wordGene.getSequences().get(4)).getValue().equals("e"));

		log.info(wordGene);
	}

	@Test
	public void testMutateInvalidSequence() {
		solutionChromosome = new SolutionChromosome();
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		solutionChromosome.addGene(wordGene);

		mutationAlgorithm.mutateSequence(wordGene, 5);

		assertEquals(((PlaintextSequence) wordGene.getSequences().get(0)).getValue(), "s");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(1)).getValue(), "m");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(2)).getValue(), "i");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(3)).getValue(), "l");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(4)).getValue(), "e");
	}

	@Test
	public void testMutateRandomSequence() {
		solutionChromosome = new SolutionChromosome();
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);
		solutionChromosome.addGene(wordGene);

		mutationAlgorithm.mutateRandomSequence(wordGene);

		assertFalse(((PlaintextSequence) wordGene.getSequences().get(0)).getValue().equals("s")
				&& ((PlaintextSequence) wordGene.getSequences().get(1)).getValue().equals("m")
				&& ((PlaintextSequence) wordGene.getSequences().get(2)).getValue().equals("i")
				&& ((PlaintextSequence) wordGene.getSequences().get(3)).getValue().equals("l")
				&& ((PlaintextSequence) wordGene.getSequences().get(4)).getValue().equals("e"));

		log.info(wordGene);
	}
}
