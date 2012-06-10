package com.ciphertool.zodiacengine.genetic;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.ciphertool.sentencebuilder.entities.WordId;

public class WordGeneTest {
	private static Logger log = Logger.getLogger(WordGeneTest.class);

	@Test
	public void testMutateSequence() {
		WordGene wordGene = new WordGene(new WordId("smile", 'N'), 0);

		wordGene.mutateSequence();

		log.info(wordGene);
	}
}
