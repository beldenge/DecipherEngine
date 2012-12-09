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

package com.ciphertool.zodiacengine.genetic.adapters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.PlaintextId;

public class WordGeneTest {
	private static Logger log = Logger.getLogger(WordGeneTest.class);

	private static Word word = new Word(new WordId("smile", 'N'));
	private static int beginCiphertextIndex = 0;
	private static SolutionChromosome solutionChromosome = new SolutionChromosome();

	@Test
	public void testConstructor() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		assertEquals(solutionChromosome.actualSize(), new Integer(0));

		assertTrue(solutionChromosome == wordGene.getChromosome());

		assertEquals(wordGene.size(), word.getId().getWord().length());
	}

	@Test
	public void testCloneWordGene() {
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		WordGene clonedWordGene = wordGene.clone();

		/*
		 * Make sure the WordGenes reference different memory addresses.
		 */
		assertFalse(wordGene == clonedWordGene);

		/*
		 * Make sure the content of the WordGenes are equal.
		 */
		assertEquals(wordGene, clonedWordGene);

		/*
		 * Make sure the Chromosomes reference different memory addresses even
		 * though that Object is not cloned.
		 */
		assertFalse(wordGene.getChromosome() == clonedWordGene.getChromosome());

		/*
		 * Make sure the Sequences reference different memory addresses.
		 */
		for (int i = 0; i < wordGene.size(); i++) {
			assertFalse(wordGene.getSequences().get(i) == clonedWordGene.getSequences().get(i));
		}

		/*
		 * Make sure the content of the Sequences are equal.
		 */
		for (int i = 0; i < wordGene.size(); i++) {
			assertEquals(wordGene.getSequences().get(i), clonedWordGene.getSequences().get(i));
		}
	}

	@Test
	public void testSize() {
		Word george = new Word(new WordId("george", 'N'));

		WordGene wordGene = new WordGene(george, solutionChromosome, beginCiphertextIndex);

		assertEquals(wordGene.size(), george.getId().getWord().length());
	}

	@Test
	public void testAddSequence() {
		solutionChromosome = new SolutionChromosome();
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		int ciphertextId = wordGene.size();

		PlaintextSequence plaintextSequence = new PlaintextSequence(new PlaintextId(
				solutionChromosome, ciphertextId), "y", wordGene);

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		wordGene.addSequence(plaintextSequence);

		assertEquals(wordGene.size(), geneSizeBefore + 1);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore + 1);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertTrue(solutionChromosome.getPlaintextCharacters().get(ciphertextId) == plaintextSequence);

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count).getId()
						.getCiphertextId(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId().intValue(), count);

				count++;
			}
		}
	}

	@Test
	public void testInsertSequence() {
		solutionChromosome = new SolutionChromosome();
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		int ciphertextId = 0;

		PlaintextSequence plaintextSequence = new PlaintextSequence(new PlaintextId(
				solutionChromosome, ciphertextId), "h", wordGene);

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		wordGene.insertSequence(ciphertextId, plaintextSequence);

		assertEquals(wordGene.size(), geneSizeBefore + 1);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore + 1);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertTrue(solutionChromosome.getPlaintextCharacters().get(ciphertextId) == plaintextSequence);

		for (int i = 0; i < solutionChromosome.getPlaintextCharacters().size(); i++) {
			log.info(solutionChromosome.getPlaintextCharacters().get(i));
			/*
			 * We don't test the ciphertextId since that should only be updated
			 * when adding the Gene back to the SolutionChromosome.
			 */

			assertTrue(solutionChromosome.getPlaintextCharacters().get(i) == wordGene
					.getSequences().get(i));
		}

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count).getId()
						.getCiphertextId(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId().intValue(), count);

				count++;
			}
		}

	}

	@Test
	public void testRemoveInvalidSequence() {
		solutionChromosome = new SolutionChromosome();
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		wordGene.removeSequence(null);
	}

	@Test
	public void testRemoveSequence() {
		solutionChromosome = new SolutionChromosome();
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		int ciphertextId = 1;

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		wordGene.removeSequence(wordGene.getSequences().get(ciphertextId));

		assertEquals(wordGene.size(), geneSizeBefore - 1);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore - 1);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertEquals(((PlaintextSequence) wordGene.getSequences().get(0)).getValue(), "s");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(1)).getValue(), "i");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(2)).getValue(), "l");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(3)).getValue(), "e");

		for (int i = 0; i < solutionChromosome.getPlaintextCharacters().size(); i++) {
			log.info(solutionChromosome.getPlaintextCharacters().get(i));
			/*
			 * We don't test the ciphertextId since that should only be updated
			 * when removing the Gene from the SolutionChromosome.
			 */

			assertTrue(solutionChromosome.getPlaintextCharacters().get(i) == wordGene
					.getSequences().get(i));
		}

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count).getId()
						.getCiphertextId(), count);

				assertEquals(gene.getSequences().get(j).getSequenceId().intValue(), count);

				count++;
			}
		}
	}

	@Test
	public void testReplaceInvalidSequence() {
		solutionChromosome = new SolutionChromosome();
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		wordGene.replaceSequence(0, null);
	}

	@Test
	public void testReplaceSequence() {
		solutionChromosome = new SolutionChromosome();
		WordGene wordGene = new WordGene(word, solutionChromosome, beginCiphertextIndex);

		int ciphertextId = 0;

		PlaintextSequence plaintextSequence = new PlaintextSequence(new PlaintextId(
				solutionChromosome, ciphertextId), "h", wordGene);

		int geneSizeBefore = wordGene.size();

		solutionChromosome.addGene(wordGene);

		int chromosomeSizeBefore = solutionChromosome.getPlaintextCharacters().size();

		wordGene.replaceSequence(ciphertextId, plaintextSequence);

		assertEquals(wordGene.size(), geneSizeBefore);
		assertEquals(solutionChromosome.getPlaintextCharacters().size(), chromosomeSizeBefore);

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		assertTrue(solutionChromosome.getPlaintextCharacters().get(ciphertextId) == plaintextSequence);

		for (int i = 0; i < solutionChromosome.getPlaintextCharacters().size(); i++) {
			log.info(solutionChromosome.getPlaintextCharacters().get(i));
			assertEquals(solutionChromosome.getPlaintextCharacters().get(i).getId()
					.getCiphertextId(), i);

			assertTrue(solutionChromosome.getPlaintextCharacters().get(i) == wordGene
					.getSequences().get(i));
		}
	}
}
