package com.ciphertool.zodiacengine.genetic.algorithms.mutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;

import com.ciphertool.genetics.algorithms.mutation.MutationAlgorithm;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.GenericTestBase;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;
import com.ciphertool.zodiacengine.genetic.dao.WordGeneListDao;

public class MutationAlgorithmTestBase extends GenericTestBase {
	private static Logger log = Logger.getLogger(MutationAlgorithmTestBase.class);
	protected static MutationAlgorithm mutationAlgorithm;
	protected static SolutionChromosome solutionChromosome;

	@Before
	public void resetSolutionChromosome() {
		solutionChromosome = new SolutionChromosome(zodiac408, 0, 0, 0);
	}

	public void testMutateGene() {
		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		Class<?>[] params = { Chromosome.class, int.class };
		Object[] args = { solutionChromosome, 0 };
		try {
			invokeMethod(mutationAlgorithm, "mutateGene", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertNotSame(wordGene1.getWordString(), word1.getId().getWord());

		assertEquals(wordGene2.getWordString(), word2.getId().getWord());

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
	}

	public void testMutateInvalidGene() {
		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		Class<?>[] params = { Chromosome.class, int.class };
		Object[] args = { solutionChromosome, 5 };
		try {
			invokeMethod(mutationAlgorithm, "mutateGene", params, args);
		} catch (InvocationTargetException e) {
			fail(e.getMessage());
		}

		assertEquals(wordGene1.getWordString(), word1.getId().getWord());

		assertEquals(wordGene2.getWordString(), word2.getId().getWord());
	}

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
		 * Only one gene should be changed.
		 */
		assertFalse(wordGene1.getWordString().equals(word1.getId().getWord())
				&& wordGene2.getWordString().equals(word2.getId().getWord()));
		assertTrue(wordGene1.getWordString().equals(word1.getId().getWord())
				|| wordGene2.getWordString().equals(word2.getId().getWord()));

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
	}

	protected static class WordGeneListDaoMock extends WordGeneListDao {
		@Override
		public Gene findRandomGene(Chromosome chromosome, int beginIndex) {
			WordGene wordGene = new WordGene(new Word(new WordId("***", 'X')),
					(SolutionChromosome) chromosome, beginIndex);

			return wordGene;
		}

		@Override
		public Gene findRandomGeneOfLength(Chromosome chromosome, int beginIndex, int length) {
			StringBuffer word = new StringBuffer("");
			for (int i = 0; i < length; i++) {
				word.append("*");
			}

			WordGene wordGene = new WordGene(new Word(new WordId(word.toString(), 'X')),
					(SolutionChromosome) chromosome, beginIndex);

			return wordGene;
		}
	}
}
