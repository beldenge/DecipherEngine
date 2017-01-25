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
	private static CipherSolution				solution2	= new CipherSolution();
	private static CipherSolution				solution3	= new CipherSolution();

	static {
		int lastRowBegin = (zodiac408.getColumns() * (zodiac408.getRows() - 1));
		int totalCharacters = zodiac408.getCiphertextCharacters().size();

		// Remove the last row altogether
		for (int i = lastRowBegin; i < totalCharacters; i++) {
			zodiac408.removeCiphertextCharacter(zodiac408.getCiphertextCharacters().get(lastRowBegin));
		}

		solution.putMapping("tri", new Plaintext("i"));
		solution.putMapping("lrbox", new Plaintext("l"));
		solution.putMapping("p", new Plaintext("i"));
		solution.putMapping("forslash", new Plaintext("k"));
		solution.putMapping("z", new Plaintext("e"));
		solution.putMapping("u", new Plaintext("i"));
		solution.putMapping("b", new Plaintext("l"));
		solution.putMapping("backk", new Plaintext("i"));
		solution.putMapping("o", new Plaintext("n"));
		solution.putMapping("r", new Plaintext("g"));
		solution.putMapping("pi", new Plaintext("p"));
		solution.putMapping("backp", new Plaintext("e"));
		solution.putMapping("x", new Plaintext("o"));
		solution.putMapping("w", new Plaintext("e"));
		solution.putMapping("v", new Plaintext("b"));
		solution.putMapping("plus", new Plaintext("e"));
		solution.putMapping("backe", new Plaintext("c"));
		solution.putMapping("g", new Plaintext("a"));
		solution.putMapping("y", new Plaintext("u"));
		solution.putMapping("f", new Plaintext("s"));
		solution.putMapping("circledot", new Plaintext("e"));
		solution.putMapping("h", new Plaintext("t"));
		solution.putMapping("boxdot", new Plaintext("s"));
		solution.putMapping("k", new Plaintext("s"));
		solution.putMapping("anchor", new Plaintext("o"));
		solution.putMapping("backq", new Plaintext("m"));
		solution.putMapping("m", new Plaintext("h"));
		solution.putMapping("j", new Plaintext("f"));
		solution.putMapping("carrot", new Plaintext("n"));
		solution.putMapping("i", new Plaintext("t"));
		solution.putMapping("tridot", new Plaintext("s"));
		solution.putMapping("t", new Plaintext("o"));
		solution.putMapping("flipt", new Plaintext("r"));
		solution.putMapping("n", new Plaintext("e"));
		solution.putMapping("q", new Plaintext("f"));
		solution.putMapping("d", new Plaintext("n"));
		solution.putMapping("fullcircle", new Plaintext("t"));
		solution.putMapping("horstrike", new Plaintext("h"));
		solution.putMapping("s", new Plaintext("a"));
		solution.putMapping("vertstrike", new Plaintext("n"));
		solution.putMapping("fullbox", new Plaintext("l"));
		solution.putMapping("a", new Plaintext("w"));
		solution.putMapping("backf", new Plaintext("d"));
		solution.putMapping("backl", new Plaintext("a"));
		solution.putMapping("e", new Plaintext("e"));
		solution.putMapping("l", new Plaintext("t"));
		solution.putMapping("backd", new Plaintext("o"));
		solution.putMapping("backr", new Plaintext("r"));
		solution.putMapping("backslash", new Plaintext("r"));
		solution.putMapping("fulltri", new Plaintext("a"));
		solution.putMapping("zodiac", new Plaintext("d"));
		solution.putMapping("backc", new Plaintext("v"));
		solution.putMapping("backj", new Plaintext("x"));
		solution.putMapping("box", new Plaintext("y"));

		solution.setCipher(zodiac408);

		solution2.putMapping("tri", new Plaintext("i"));
		solution2.putMapping("lrbox", new Plaintext("s"));
		solution2.putMapping("p", new Plaintext("o"));
		solution2.putMapping("forslash", new Plaintext("s"));
		solution2.putMapping("z", new Plaintext("e"));
		solution2.putMapping("u", new Plaintext("e"));
		solution2.putMapping("b", new Plaintext("t"));
		solution2.putMapping("backk", new Plaintext("a"));
		solution2.putMapping("o", new Plaintext("t"));
		solution2.putMapping("r", new Plaintext("h"));
		solution2.putMapping("pi", new Plaintext("r"));
		solution2.putMapping("backp", new Plaintext("e"));
		solution2.putMapping("x", new Plaintext("e"));
		solution2.putMapping("w", new Plaintext("e"));
		solution2.putMapping("v", new Plaintext("h"));
		solution2.putMapping("plus", new Plaintext("e"));
		solution2.putMapping("backe", new Plaintext("r"));
		solution2.putMapping("g", new Plaintext("e"));
		solution2.putMapping("y", new Plaintext("a"));
		solution2.putMapping("f", new Plaintext("s"));
		solution2.putMapping("circledot", new Plaintext("e"));
		solution2.putMapping("h", new Plaintext("t"));
		solution2.putMapping("boxdot", new Plaintext("r"));
		solution2.putMapping("k", new Plaintext("s"));
		solution2.putMapping("anchor", new Plaintext("e"));
		solution2.putMapping("backq", new Plaintext("s"));
		solution2.putMapping("m", new Plaintext("h"));
		solution2.putMapping("j", new Plaintext("r"));
		solution2.putMapping("carrot", new Plaintext("t"));
		solution2.putMapping("i", new Plaintext("s"));
		solution2.putMapping("tridot", new Plaintext("s"));
		solution2.putMapping("t", new Plaintext("o"));
		solution2.putMapping("flipt", new Plaintext("r"));
		solution2.putMapping("n", new Plaintext("e"));
		solution2.putMapping("q", new Plaintext("s"));
		solution2.putMapping("d", new Plaintext("t"));
		solution2.putMapping("fullcircle", new Plaintext("t"));
		solution2.putMapping("horstrike", new Plaintext("h"));
		solution2.putMapping("s", new Plaintext("a"));
		solution2.putMapping("vertstrike", new Plaintext("t"));
		solution2.putMapping("fullbox", new Plaintext("n"));
		solution2.putMapping("a", new Plaintext("t"));
		solution2.putMapping("backf", new Plaintext("t"));
		solution2.putMapping("backl", new Plaintext("e"));
		solution2.putMapping("e", new Plaintext("e"));
		solution2.putMapping("l", new Plaintext("t"));
		solution2.putMapping("backd", new Plaintext("e"));
		solution2.putMapping("backr", new Plaintext("s"));
		solution2.putMapping("backslash", new Plaintext("s"));
		solution2.putMapping("fulltri", new Plaintext("e"));
		solution2.putMapping("zodiac", new Plaintext("r"));
		solution2.putMapping("backc", new Plaintext("v"));
		solution2.putMapping("backj", new Plaintext("p"));
		solution2.putMapping("box", new Plaintext("t"));

		solution2.setCipher(zodiac408);

		solution3.putMapping("tri", new Plaintext("i"));
		solution3.putMapping("lrbox", new Plaintext("l"));
		solution3.putMapping("p", new Plaintext("i"));
		solution3.putMapping("forslash", new Plaintext("l"));
		solution3.putMapping("z", new Plaintext("e"));
		solution3.putMapping("u", new Plaintext("a"));
		solution3.putMapping("b", new Plaintext("l"));
		solution3.putMapping("backk", new Plaintext("q"));
		solution3.putMapping("o", new Plaintext("s"));
		solution3.putMapping("r", new Plaintext("t"));
		solution3.putMapping("pi", new Plaintext("p"));
		solution3.putMapping("backp", new Plaintext("e"));
		solution3.putMapping("x", new Plaintext("o"));
		solution3.putMapping("w", new Plaintext("e"));
		solution3.putMapping("v", new Plaintext("s"));
		solution3.putMapping("plus", new Plaintext("e"));
		solution3.putMapping("backe", new Plaintext("t"));
		solution3.putMapping("g", new Plaintext("e"));
		solution3.putMapping("y", new Plaintext("s"));
		solution3.putMapping("f", new Plaintext("l"));
		solution3.putMapping("circledot", new Plaintext("e"));
		solution3.putMapping("h", new Plaintext("s"));
		solution3.putMapping("boxdot", new Plaintext("s"));
		solution3.putMapping("k", new Plaintext("t"));
		solution3.putMapping("anchor", new Plaintext("e"));
		solution3.putMapping("backq", new Plaintext("r"));
		solution3.putMapping("m", new Plaintext("h"));
		solution3.putMapping("j", new Plaintext("e"));
		solution3.putMapping("carrot", new Plaintext("n"));
		solution3.putMapping("i", new Plaintext("t"));
		solution3.putMapping("tridot", new Plaintext("a"));
		solution3.putMapping("t", new Plaintext("e"));
		solution3.putMapping("flipt", new Plaintext("r"));
		solution3.putMapping("n", new Plaintext("e"));
		solution3.putMapping("q", new Plaintext("a"));
		solution3.putMapping("d", new Plaintext("s"));
		solution3.putMapping("fullcircle", new Plaintext("t"));
		solution3.putMapping("horstrike", new Plaintext("h"));
		solution3.putMapping("s", new Plaintext("m"));
		solution3.putMapping("vertstrike", new Plaintext("n"));
		solution3.putMapping("fullbox", new Plaintext("l"));
		solution3.putMapping("a", new Plaintext("t"));
		solution3.putMapping("backf", new Plaintext("s"));
		solution3.putMapping("backl", new Plaintext("e"));
		solution3.putMapping("e", new Plaintext("e"));
		solution3.putMapping("l", new Plaintext("t"));
		solution3.putMapping("backd", new Plaintext("a"));
		solution3.putMapping("backr", new Plaintext("r"));
		solution3.putMapping("backslash", new Plaintext("r"));
		solution3.putMapping("fulltri", new Plaintext("a"));
		solution3.putMapping("zodiac", new Plaintext("t"));
		solution3.putMapping("backc", new Plaintext("r"));
		solution3.putMapping("backj", new Plaintext("m"));
		solution3.putMapping("box", new Plaintext("l"));

		solution3.setCipher(zodiac408);
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
		letterMarkovModel.setOrder(6);
		letterMarkovModel.setTaskExecutor(taskExecutorSpy);

		letterNGramMarkovImporter = new LetterNGramMarkovImporter();
		letterNGramMarkovImporter.setLetterMarkovModel(letterMarkovModel);
		letterNGramMarkovImporter.setCorpusDirectory("/Users/george/Desktop/sherlock-transformed");
		letterNGramMarkovImporter.setTaskExecutor(taskExecutorSpy);
		letterNGramMarkovImporter.importCorpus();

		wordMarkovModel = new MarkovModel();
		wordMarkovModel.setOrder(1);
		wordMarkovModel.setTaskExecutor(taskExecutorSpy);

		wordNGramMarkovImporter = new WordNGramMarkovImporter();
		wordNGramMarkovImporter.setWordMarkovModel(wordMarkovModel);
		wordNGramMarkovImporter.setCorpusDirectory("/Users/george/Desktop/sherlock-transformed");
		wordNGramMarkovImporter.setTaskExecutor(taskExecutorSpy);
		wordNGramMarkovImporter.importCorpus();

		plaintextEvaluator = new PlaintextEvaluator();
		plaintextEvaluator.setLetterMarkovModel(letterMarkovModel);
		plaintextEvaluator.setWordMarkovModel(wordMarkovModel);
		plaintextEvaluator.setLetterNGramWeight(1.0);
		plaintextEvaluator.setWordNGramWeight(0.0);
		plaintextEvaluator.setBigDecimalFunctions(new BigDecimalFunctions());
		plaintextEvaluator.init();
	}

	// @Test
	public void testEvaluate() {
		log.info("fitness1: " + plaintextEvaluator.evaluate(solution));
		log.info("solution1: " + solution);

		log.info("fitness2: " + plaintextEvaluator.evaluate(solution2));
		log.info("solution2: " + solution2);

		log.info("fitness3: " + plaintextEvaluator.evaluate(solution3));
		log.info("solution3: " + solution3);
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
