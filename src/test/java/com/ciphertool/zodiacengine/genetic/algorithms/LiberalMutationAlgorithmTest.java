package com.ciphertool.zodiacengine.genetic.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.algorithms.mutation.LiberalMutationAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.util.ChromosomeHelper;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;

public class LiberalMutationAlgorithmTest extends MutationAlgorithmTestBase {
	private static Logger log = Logger.getLogger(LiberalMutationAlgorithmTest.class);

	@BeforeClass
	public static void setUp() {
		mutationAlgorithm = new LiberalMutationAlgorithm();
		((LiberalMutationAlgorithm) mutationAlgorithm).setGeneListDao(new WordGeneListDaoMock());

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

		Class<?>[] params = { Chromosome.class };
		Object[] args = { solutionChromosome };
		try {
			invokeMethod(mutationAlgorithm, "mutateRandomGene", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		/*
		 * At least one gene should be changed.
		 */
		assertFalse(wordGene1.getWordString().equals(word1.getId().getWord())
				&& wordGene2.getWordString().equals(word2.getId().getWord()));

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
	}
}