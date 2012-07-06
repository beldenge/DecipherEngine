package com.ciphertool.zodiacengine.genetic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.Solution;

public class WordGeneTest {
	private static Logger log = Logger.getLogger(WordGeneTest.class);

	private static Word word = new Word(new WordId("smile", 'N'));
	private static int beginCiphertextIndex = 0;
	private static Solution solution = new Solution();

	@Test
	public void testMutateSequence() {
		WordGene wordGene = new WordGene(word, solution, beginCiphertextIndex);

		wordGene.mutateSequence(4);

		assertEquals(((PlaintextSequence) wordGene.getSequences().get(0)).getValue(), "s");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(1)).getValue(), "m");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(2)).getValue(), "i");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(3)).getValue(), "l");
		assertFalse(((PlaintextSequence) wordGene.getSequences().get(4)).getValue().equals("e"));

		log.info(wordGene);
	}

	@Test
	public void testMutateInvalidSequence() {
		WordGene wordGene = new WordGene(word, solution, beginCiphertextIndex);

		wordGene.mutateSequence(5);

		assertEquals(((PlaintextSequence) wordGene.getSequences().get(0)).getValue(), "s");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(1)).getValue(), "m");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(2)).getValue(), "i");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(3)).getValue(), "l");
		assertEquals(((PlaintextSequence) wordGene.getSequences().get(4)).getValue(), "e");
	}

	@Test
	public void testMutateRandomSequence() {
		WordGene wordGene = new WordGene(word, solution, beginCiphertextIndex);

		wordGene.mutateRandomSequence();

		assertFalse(((PlaintextSequence) wordGene.getSequences().get(0)).getValue().equals("s")
				&& ((PlaintextSequence) wordGene.getSequences().get(1)).getValue().equals("m")
				&& ((PlaintextSequence) wordGene.getSequences().get(2)).getValue().equals("i")
				&& ((PlaintextSequence) wordGene.getSequences().get(3)).getValue().equals("l")
				&& ((PlaintextSequence) wordGene.getSequences().get(4)).getValue().equals("e"));

		log.info(wordGene);
	}
}
