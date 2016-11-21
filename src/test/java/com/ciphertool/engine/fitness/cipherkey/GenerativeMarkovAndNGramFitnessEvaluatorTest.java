/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.fitness.cipherkey;

import static org.mockito.Mockito.spy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ciphertool.engine.dao.TopWordsFacade;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.engine.entities.CipherKeyGene;
import com.ciphertool.engine.fitness.FitnessEvaluatorTestBase;
import com.ciphertool.sherlock.dao.NGramDao;
import com.ciphertool.sherlock.dao.UniqueNGramListDao;
import com.ciphertool.sherlock.dao.UniqueWordListDao;
import com.ciphertool.sherlock.dao.WordDao;
import com.ciphertool.sherlock.etl.importers.LetterNGramMarkovImporter;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.mongodb.MongoClient;

public class GenerativeMarkovAndNGramFitnessEvaluatorTest extends FitnessEvaluatorTestBase {
	private static Logger									log			= LoggerFactory.getLogger(GenerativeMarkovAndNGramFitnessEvaluatorTest.class);

	private static LetterNGramMarkovImporter						importer;
	private static MarkovModel								markovModel;

	private static GenerativeMarkovAndNGramFitnessEvaluator	fitnessEvaluator;

	private static CipherKeyChromosome						solution	= new CipherKeyChromosome();

	static {
		solution.putGene("tri", new CipherKeyGene(solution, "i"));
		solution.putGene("lrbox", new CipherKeyGene(solution, "l"));
		solution.putGene("p", new CipherKeyGene(solution, "i"));
		solution.putGene("forslash", new CipherKeyGene(solution, "k"));
		solution.putGene("z", new CipherKeyGene(solution, "e"));
		solution.putGene("u", new CipherKeyGene(solution, "i"));
		solution.putGene("b", new CipherKeyGene(solution, "l"));
		solution.putGene("backk", new CipherKeyGene(solution, "i"));
		solution.putGene("o", new CipherKeyGene(solution, "n"));
		solution.putGene("r", new CipherKeyGene(solution, "g"));
		solution.putGene("pi", new CipherKeyGene(solution, "p"));
		solution.putGene("backp", new CipherKeyGene(solution, "e"));
		solution.putGene("x", new CipherKeyGene(solution, "o"));
		solution.putGene("w", new CipherKeyGene(solution, "e"));
		solution.putGene("v", new CipherKeyGene(solution, "b"));
		solution.putGene("plus", new CipherKeyGene(solution, "e"));
		solution.putGene("backe", new CipherKeyGene(solution, "c"));
		solution.putGene("g", new CipherKeyGene(solution, "a"));
		solution.putGene("y", new CipherKeyGene(solution, "u"));
		solution.putGene("f", new CipherKeyGene(solution, "s"));
		solution.putGene("circledot", new CipherKeyGene(solution, "e"));
		solution.putGene("h", new CipherKeyGene(solution, "t"));
		solution.putGene("boxdot", new CipherKeyGene(solution, "s"));
		solution.putGene("k", new CipherKeyGene(solution, "s"));
		solution.putGene("anchor", new CipherKeyGene(solution, "o"));
		solution.putGene("backq", new CipherKeyGene(solution, "m"));
		solution.putGene("m", new CipherKeyGene(solution, "h"));
		solution.putGene("j", new CipherKeyGene(solution, "f"));
		solution.putGene("carrot", new CipherKeyGene(solution, "n"));
		solution.putGene("i", new CipherKeyGene(solution, "t"));
		solution.putGene("tridot", new CipherKeyGene(solution, "s"));
		solution.putGene("t", new CipherKeyGene(solution, "o"));
		solution.putGene("flipt", new CipherKeyGene(solution, "r"));
		solution.putGene("n", new CipherKeyGene(solution, "e"));
		solution.putGene("q", new CipherKeyGene(solution, "f"));
		solution.putGene("d", new CipherKeyGene(solution, "n"));
		solution.putGene("fullcircle", new CipherKeyGene(solution, "t"));
		solution.putGene("horstrike", new CipherKeyGene(solution, "h"));
		solution.putGene("s", new CipherKeyGene(solution, "a"));
		solution.putGene("vertstrike", new CipherKeyGene(solution, "n"));
		solution.putGene("fullbox", new CipherKeyGene(solution, "l"));
		solution.putGene("a", new CipherKeyGene(solution, "w"));
		solution.putGene("backf", new CipherKeyGene(solution, "d"));
		solution.putGene("backl", new CipherKeyGene(solution, "a"));
		solution.putGene("e", new CipherKeyGene(solution, "e"));
		solution.putGene("l", new CipherKeyGene(solution, "t"));
		solution.putGene("backd", new CipherKeyGene(solution, "o"));
		solution.putGene("backr", new CipherKeyGene(solution, "r"));
		solution.putGene("backslash", new CipherKeyGene(solution, "r"));
		solution.putGene("fulltri", new CipherKeyGene(solution, "a"));
		solution.putGene("zodiac", new CipherKeyGene(solution, "d"));
		solution.putGene("backc", new CipherKeyGene(solution, "v"));
		solution.putGene("backj", new CipherKeyGene(solution, "x"));
		solution.putGene("box", new CipherKeyGene(solution, "y"));

		solution.setCipher(zodiac408);
	}

	// @BeforeClass
	public static void setUp() throws InterruptedException, ExecutionException {
		ThreadPoolTaskExecutor taskExecutorSpy = spy(new ThreadPoolTaskExecutor());
		taskExecutorSpy.setCorePoolSize(4);
		taskExecutorSpy.setMaxPoolSize(4);
		taskExecutorSpy.setQueueCapacity(10000);
		taskExecutorSpy.setKeepAliveSeconds(1);
		taskExecutorSpy.setAllowCoreThreadTimeOut(true);
		taskExecutorSpy.initialize();

		markovModel = new MarkovModel();
		markovModel.setOrder(5);
		markovModel.setTaskExecutor(taskExecutorSpy);

		importer = new LetterNGramMarkovImporter();
		importer.setModel(markovModel);
		importer.setCorpusDirectory("../Sherlock/src/main/data/corpus");
		importer.setMinCount(1);
		importer.setTaskExecutor(taskExecutorSpy);
		importer.importCorpus();

		fitnessEvaluator = new GenerativeMarkovAndNGramFitnessEvaluator();
		fitnessEvaluator.setModel(markovModel);

		TopWordsFacade topWordsFacade = new TopWordsFacade();
		topWordsFacade.setTop(1);
		topWordsFacade.setMinWordLength(3);

		MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("localhost", 27017), "DecipherEngine");

		NGramDao nGramDao = new NGramDao();
		nGramDao.setMongoTemplate(mongoTemplate);

		UniqueNGramListDao nGramListDao = new UniqueNGramListDao();
		nGramListDao.setnGramDao(nGramDao);
		nGramListDao.setTopFiveGrams(1);
		nGramListDao.setTopFourGrams(1);
		nGramListDao.setTopThreeGrams(1);
		nGramListDao.setTopTwoGrams(1);
		nGramListDao.init();
		topWordsFacade.setnGramListDao(nGramListDao);

		WordDao wordDao = new WordDao();
		wordDao.setMongoTemplate(mongoTemplate);

		UniqueWordListDao wordListDao = new UniqueWordListDao();
		wordListDao.setTopWords(16000);
		wordListDao.setWordDao(wordDao);
		wordListDao.init();
		topWordsFacade.setWordListDao(wordListDao);

		topWordsFacade.init();

		fitnessEvaluator.setTopWordsFacade(topWordsFacade);

		Map<Character, Double> frequenciesToSet = new HashMap<Character, Double>(26);
		frequenciesToSet.put('a', 0.0812);
		frequenciesToSet.put('b', 0.0149);
		frequenciesToSet.put('c', 0.0271);
		frequenciesToSet.put('d', 0.0432);
		frequenciesToSet.put('e', 0.1202);
		frequenciesToSet.put('f', 0.0230);
		frequenciesToSet.put('g', 0.0203);
		frequenciesToSet.put('h', 0.0592);
		frequenciesToSet.put('i', 0.0731);
		frequenciesToSet.put('j', 0.0010);
		frequenciesToSet.put('k', 0.0069);
		frequenciesToSet.put('l', 0.0398);
		frequenciesToSet.put('m', 0.0261);
		frequenciesToSet.put('n', 0.0695);
		frequenciesToSet.put('o', 0.0768);
		frequenciesToSet.put('p', 0.0182);
		frequenciesToSet.put('q', 0.0011);
		frequenciesToSet.put('r', 0.0602);
		frequenciesToSet.put('s', 0.0628);
		frequenciesToSet.put('t', 0.0910);
		frequenciesToSet.put('u', 0.0288);
		frequenciesToSet.put('v', 0.0111);
		frequenciesToSet.put('w', 0.0209);
		frequenciesToSet.put('x', 0.0017);
		frequenciesToSet.put('y', 0.0211);
		frequenciesToSet.put('z', 0.0007);

		fitnessEvaluator.setExpectedLetterFrequencies(frequenciesToSet);
		fitnessEvaluator.setGeneticStructure(zodiac408);
		fitnessEvaluator.setFrequencyWeight(0.1);
		fitnessEvaluator.setLetterNGramWeight(0.45);
		fitnessEvaluator.setWordNGramWeight(0.45);
		fitnessEvaluator.setMinimumProbability(0.75);
		fitnessEvaluator.setMinimumOrder(5);
		fitnessEvaluator.init();
	}

	// @Test
	public void testEvaluate() {
		log.info("fitness: " + fitnessEvaluator.evaluate(solution));
	}

	// @Test
	public void testPerf() {
		long start = System.currentTimeMillis();
		long evaluations = 10000;

		for (int i = 0; i < evaluations; i++) {
			fitnessEvaluator.evaluate(solution);
		}

		log.info(evaluations + " evaluations took: " + (System.currentTimeMillis() - start) + "ms.");
	}
}