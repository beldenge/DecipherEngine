package com.ciphertool.engine.bayes;

import static org.mockito.Mockito.spy;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ciphertool.engine.fitness.FitnessEvaluatorTestBase;
import com.ciphertool.sherlock.etl.importers.LetterNGramMarkovImporter;
import com.ciphertool.sherlock.etl.importers.WordNGramMarkovImporter;
import com.ciphertool.sherlock.markov.MarkovModel;

public class PlaintextEvaluatorTest extends FitnessEvaluatorTestBase {
	private static Logger						log			= LoggerFactory.getLogger(PlaintextEvaluatorTest.class);

	private static LetterNGramMarkovImporter	letterNGramMarkovImporter;
	private static WordNGramMarkovImporter		wordNGramMarkovImporter;
	private static MarkovModel					letterMarkovModel;
	private static MarkovModel					wordMarkovModel;

	private static PlaintextEvaluator			plaintextEvaluator;

	private static CipherSolution				solution	= new CipherSolution();

	static {
		solution.putMapping("tri", "i");
		solution.putMapping("lrbox", "l");
		solution.putMapping("p", "i");
		solution.putMapping("forslash", "k");
		solution.putMapping("z", "e");
		solution.putMapping("u", "i");
		solution.putMapping("b", "l");
		solution.putMapping("backk", "i");
		solution.putMapping("o", "n");
		solution.putMapping("r", "g");
		solution.putMapping("pi", "p");
		solution.putMapping("backp", "e");
		solution.putMapping("x", "o");
		solution.putMapping("w", "e");
		solution.putMapping("v", "b");
		solution.putMapping("plus", "e");
		solution.putMapping("backe", "c");
		solution.putMapping("g", "a");
		solution.putMapping("y", "u");
		solution.putMapping("f", "s");
		solution.putMapping("circledot", "e");
		solution.putMapping("h", "t");
		solution.putMapping("boxdot", "s");
		solution.putMapping("k", "s");
		solution.putMapping("anchor", "o");
		solution.putMapping("backq", "m");
		solution.putMapping("m", "h");
		solution.putMapping("j", "f");
		solution.putMapping("carrot", "n");
		solution.putMapping("i", "t");
		solution.putMapping("tridot", "s");
		solution.putMapping("t", "o");
		solution.putMapping("flipt", "r");
		solution.putMapping("n", "e");
		solution.putMapping("q", "f");
		solution.putMapping("d", "n");
		solution.putMapping("fullcircle", "t");
		solution.putMapping("horstrike", "h");
		solution.putMapping("s", "a");
		solution.putMapping("vertstrike", "n");
		solution.putMapping("fullbox", "l");
		solution.putMapping("a", "w");
		solution.putMapping("backf", "d");
		solution.putMapping("backl", "a");
		solution.putMapping("e", "e");
		solution.putMapping("l", "t");
		solution.putMapping("backd", "o");
		solution.putMapping("backr", "r");
		solution.putMapping("backslash", "r");
		solution.putMapping("fulltri", "a");
		solution.putMapping("zodiac", "d");
		solution.putMapping("backc", "v");
		solution.putMapping("backj", "x");
		solution.putMapping("box", "y");

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

		letterMarkovModel = new MarkovModel();
		letterMarkovModel.setOrder(3);
		letterMarkovModel.setTaskExecutor(taskExecutorSpy);

		letterNGramMarkovImporter = new LetterNGramMarkovImporter();
		letterNGramMarkovImporter.setLetterMarkovModel(letterMarkovModel);
		letterNGramMarkovImporter.setCorpusDirectory("/Users/george/Desktop/sherlock-transformed");
		letterNGramMarkovImporter.setMinCount(2);
		letterNGramMarkovImporter.setTaskExecutor(taskExecutorSpy);
		letterNGramMarkovImporter.importCorpus();

		wordMarkovModel = new MarkovModel();
		wordMarkovModel.setOrder(1);
		wordMarkovModel.setTaskExecutor(taskExecutorSpy);

		wordNGramMarkovImporter = new WordNGramMarkovImporter();
		wordNGramMarkovImporter.setWordMarkovModel(wordMarkovModel);
		wordNGramMarkovImporter.setCorpusDirectory("/Users/george/Desktop/sherlock-transformed");
		wordNGramMarkovImporter.setMinCount(2);
		wordNGramMarkovImporter.setTaskExecutor(taskExecutorSpy);
		wordNGramMarkovImporter.importCorpus();

		plaintextEvaluator = new PlaintextEvaluator();
		plaintextEvaluator.setLetterMarkovModel(letterMarkovModel);
		plaintextEvaluator.setWordMarkovModel(wordMarkovModel);
		plaintextEvaluator.setStructure(zodiac408);
		plaintextEvaluator.setLetterNGramWeight(0.1);
		plaintextEvaluator.setWordNGramWeight(0.9);
		plaintextEvaluator.init();
	}

	// @Test
	public void testEvaluate() {
		log.info("fitness: " + plaintextEvaluator.evaluate(solution));
	}

	// @Test
	public void testPerf() {
		long start = System.currentTimeMillis();
		long evaluations = 10000;

		for (int i = 0; i < evaluations; i++) {
			plaintextEvaluator.evaluate(solution);
		}

		log.info(evaluations + " evaluations took: " + (System.currentTimeMillis() - start) + "ms.");
	}
}
