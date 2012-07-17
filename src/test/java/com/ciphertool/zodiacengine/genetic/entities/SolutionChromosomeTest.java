package com.ciphertool.zodiacengine.genetic.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;

public class SolutionChromosomeTest {
	private static Logger log = Logger.getLogger(SolutionChromosomeTest.class);

	private static ApplicationContext context;
	private static Cipher cipher;

	@BeforeClass
	public static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-genetic.xml");
		log.info("Spring context created successfully!");

		CipherDao cipherDao = (CipherDao) context.getBean("cipherDao");

		cipher = cipherDao.findByCipherName("zodiac408");
	}

	/**
	 * Without setting these to null, the humongous wordMap will not be garbage
	 * collected and subsequent unit tests may encounter an out of memory
	 * exception
	 */
	@AfterClass
	public static void cleanUp() {
		cipher = null;
		context = null;
	}

	@Test
	public void testGetNullPlaintextCharacters() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		assertNotNull(solutionChromosome.getPlaintextCharacters());
	}

	@Test
	public void testGetNullGenes() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		assertNotNull(solutionChromosome.getGenes());
	}

	@Test
	public void testActualSize() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		assertEquals(solutionChromosome.actualSize().intValue(), 0);

		Word word = new Word(new WordId("smile", 'N'));

		WordGene wordGene = new WordGene(word, solutionChromosome, 0);

		solutionChromosome.addGene(wordGene);

		assertEquals(solutionChromosome.actualSize().intValue(), 5);

		solutionChromosome.addGene(wordGene);

		assertEquals(solutionChromosome.actualSize().intValue(), 10);
	}

	@Test
	public void testTargetSize() {
		SolutionChromosome solutionChromosome = new SolutionChromosome();

		Cipher cipher = new Cipher();

		solutionChromosome.setCipher(cipher);

		cipher.setColumns(5);
		cipher.setRows(5);

		assertEquals(solutionChromosome.targetSize().intValue(), 25);
	}

	@Test
	public void testAddGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(1, 0, 0, 0);
		solutionChromosome.setFitness(0);
		solutionChromosome.setCipher(cipher);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		assertEquals(solutionChromosome.getGenes().size(), 2);

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getWordId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2
				.getWordId().getWord());

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId().getCiphertextId(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testCloneSolutionChromosome() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(1, 0, 0, 0);
		solutionChromosome.setFitness(0);
		solutionChromosome.setCipher(cipher);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("elmer", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene3);

		SolutionChromosome clonedSolutionChromosome = solutionChromosome.clone();

		/*
		 * Make sure the Ciphers reference the same memory address since that
		 * Object is not cloned.
		 */
		assertTrue(solutionChromosome.getCipher() == clonedSolutionChromosome.getCipher());

		/*
		 * Make sure the Chromosomes reference different memory addresses.
		 */
		assertFalse(solutionChromosome == clonedSolutionChromosome);

		/*
		 * Make sure the content of the Chromosomes are equal.
		 */
		assertEquals(solutionChromosome, clonedSolutionChromosome);

		/*
		 * Make sure the WordGenes reference different memory addresses.
		 */
		for (int i = 0; i < solutionChromosome.getGenes().size(); i++) {
			assertFalse(solutionChromosome.getGenes().get(i) == clonedSolutionChromosome.getGenes()
					.get(i));
		}

		/*
		 * Make sure the content of the WordGenes are equal.
		 */
		for (int i = 0; i < solutionChromosome.getGenes().size(); i++) {
			assertEquals(solutionChromosome.getGenes().get(i), clonedSolutionChromosome.getGenes()
					.get(i));
		}

		/*
		 * Make sure the Sequences reference different memory addresses.
		 */
		for (int i = 0; i < solutionChromosome.actualSize(); i++) {
			assertFalse(solutionChromosome.getPlaintextCharacters().get(i) == clonedSolutionChromosome
					.getPlaintextCharacters().get(i));
		}

		/*
		 * Make sure the content of the Sequences are equal.
		 */
		for (int i = 0; i < solutionChromosome.actualSize(); i++) {
			assertEquals(solutionChromosome.getPlaintextCharacters().get(i),
					clonedSolutionChromosome.getPlaintextCharacters().get(i));
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		int count = 0;
		for (Gene gene : clonedSolutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(clonedSolutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				assertEquals(clonedSolutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId().getCiphertextId(), count);

				count++;
			}
		}
	}

	@Test
	public void testInsertGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(1, 0, 0, 0);
		solutionChromosome.setFitness(0);
		solutionChromosome.setCipher(cipher);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene3);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene1);

		Word word2 = new Word(new WordId("elmer", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.insertGene(1, wordGene2);

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getWordId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2
				.getWordId().getWord());

		log.info("Gene 3: " + ((WordGene) solutionChromosome.getGenes().get(2)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(2)).getWordString(), word3
				.getWordId().getWord());

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId().getCiphertextId(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testRemoveInvalidGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(1, 0, 0, 0);

		assertNull(solutionChromosome.removeGene(0));
	}

	@Test
	public void testRemoveGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(1, 0, 0, 0);
		solutionChromosome.setFitness(0);
		solutionChromosome.setCipher(cipher);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene1);

		Word word2 = new Word(new WordId("elmer", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.insertGene(1, wordGene2);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.insertGene(2, wordGene3);

		assertEquals(solutionChromosome.getGenes().size(), 3);

		solutionChromosome.removeGene(1);

		assertEquals(solutionChromosome.getGenes().size(), 2);

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getWordId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word3
				.getWordId().getWord());

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId().getCiphertextId(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testReplaceInvalidGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(1, 0, 0, 0);

		/*
		 * We just want to verify the log message and that no exceptions are
		 * thrown.
		 */
		solutionChromosome.replaceGene(0, null);
	}

	@Test
	public void testReplaceGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(1, 0, 0, 0);
		solutionChromosome.setFitness(0);
		solutionChromosome.setCipher(cipher);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.insertGene(0, wordGene1);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.insertGene(1, wordGene3);

		int beforeSize = solutionChromosome.getGenes().size();

		Word word2 = new Word(new WordId("elmer", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);

		solutionChromosome.replaceGene(1, wordGene2);

		assertEquals(solutionChromosome.getGenes().size(), beforeSize);

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(0)).getWordString(), word1
				.getWordId().getWord());

		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
		assertEquals(((WordGene) solutionChromosome.getGenes().get(1)).getWordString(), word2
				.getWordId().getWord());

		int count = 0;
		for (Gene gene : solutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(solutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(solutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(solutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId().getCiphertextId(), count);

				count++;
			}
		}

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());
	}

	@Test
	public void testMutateGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(1, 0, 0, 0);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		solutionChromosome.mutateGene(0);

		assertFalse(wordGene1.getWordString().equals(word1.getWordId().getWord()));

		assertEquals(wordGene2.getWordString(), word2.getWordId().getWord());

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
	}

	@Test
	public void testMutateInvalidGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(1, 0, 0, 0);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		solutionChromosome.mutateGene(5);

		assertEquals(wordGene1.getWordString(), word1.getWordId().getWord());

		assertEquals(wordGene2.getWordString(), word2.getWordId().getWord());
	}

	@Test
	public void testMutateRandomGene() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(1, 0, 0, 0);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word2 = new Word(new WordId("belden", 'N'));
		WordGene wordGene2 = new WordGene(word2, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene2);

		solutionChromosome.mutateRandomGene();

		assertFalse(wordGene1.getWordString().equals(word1.getWordId().getWord())
				&& wordGene2.getWordString().equals(word2.getWordId().getWord()));

		/*
		 * Only one gene should be changed.
		 */
		assertTrue(wordGene1.getWordString().equals(word1.getWordId().getWord())
				|| wordGene2.getWordString().equals(word2.getWordId().getWord()));

		assertEquals(solutionChromosome.actualSize().intValue(), solutionChromosome
				.getPlaintextCharacters().size());

		log.info("Gene 1: " + ((WordGene) solutionChromosome.getGenes().get(0)).getWordString());
		log.info("Gene 2: " + ((WordGene) solutionChromosome.getGenes().get(1)).getWordString());
	}

	@Test
	public void testRemoveGeneOnClonedSolution() {
		SolutionChromosome solutionChromosome = new SolutionChromosome(1, 0, 0, 0);
		solutionChromosome.setFitness(0);
		solutionChromosome.setCipher(cipher);

		Word word1 = new Word(new WordId("george", 'N'));
		WordGene wordGene1 = new WordGene(word1, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene1);

		Word word3 = new Word(new WordId("belden", 'N'));
		WordGene wordGene3 = new WordGene(word3, solutionChromosome, 0);
		solutionChromosome.addGene(wordGene3);

		SolutionChromosome clonedSolutionChromosome = solutionChromosome.clone();

		clonedSolutionChromosome.removeGene(1);

		log.info("Gene 1: "
				+ ((WordGene) clonedSolutionChromosome.getGenes().get(0)).getWordString());
		assertEquals(((WordGene) clonedSolutionChromosome.getGenes().get(0)).getWordString(), word1
				.getWordId().getWord());

		int count = 0;
		for (Gene gene : clonedSolutionChromosome.getGenes()) {
			for (int j = 0; j < gene.size(); j++) {
				assertTrue(clonedSolutionChromosome.getPlaintextCharacters().get(count) == gene
						.getSequences().get(j));

				log.info(clonedSolutionChromosome.getPlaintextCharacters().get(count));
				assertEquals(clonedSolutionChromosome.getPlaintextCharacters().get(count)
						.getPlaintextId().getCiphertextId(), count);

				count++;
			}
		}

		assertEquals(clonedSolutionChromosome.actualSize().intValue(), clonedSolutionChromosome
				.getPlaintextCharacters().size());
	}
}
