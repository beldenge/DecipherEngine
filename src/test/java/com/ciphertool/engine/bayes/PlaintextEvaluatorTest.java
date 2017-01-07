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
