package com.ciphertool.engine.bayes;

import static org.mockito.Mockito.spy;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ciphertool.engine.entities.Ciphertext;
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
		solution.putMapping(new Ciphertext("tri"), new Plaintext("i"));
		solution.putMapping(new Ciphertext("lrbox"), new Plaintext("l"));
		solution.putMapping(new Ciphertext("p"), new Plaintext("i"));
		solution.putMapping(new Ciphertext("forslash"), new Plaintext("k"));
		solution.putMapping(new Ciphertext("z"), new Plaintext("e"));
		solution.putMapping(new Ciphertext("u"), new Plaintext("i"));
		solution.putMapping(new Ciphertext("b"), new Plaintext("l"));
		solution.putMapping(new Ciphertext("backk"), new Plaintext("i"));
		solution.putMapping(new Ciphertext("o"), new Plaintext("n"));
		solution.putMapping(new Ciphertext("r"), new Plaintext("g"));
		solution.putMapping(new Ciphertext("pi"), new Plaintext("p"));
		solution.putMapping(new Ciphertext("backp"), new Plaintext("e"));
		solution.putMapping(new Ciphertext("x"), new Plaintext("o"));
		solution.putMapping(new Ciphertext("w"), new Plaintext("e"));
		solution.putMapping(new Ciphertext("v"), new Plaintext("b"));
		solution.putMapping(new Ciphertext("plus"), new Plaintext("e"));
		solution.putMapping(new Ciphertext("backe"), new Plaintext("c"));
		solution.putMapping(new Ciphertext("g"), new Plaintext("a"));
		solution.putMapping(new Ciphertext("y"), new Plaintext("u"));
		solution.putMapping(new Ciphertext("f"), new Plaintext("s"));
		solution.putMapping(new Ciphertext("circledot"), new Plaintext("e"));
		solution.putMapping(new Ciphertext("h"), new Plaintext("t"));
		solution.putMapping(new Ciphertext("boxdot"), new Plaintext("s"));
		solution.putMapping(new Ciphertext("k"), new Plaintext("s"));
		solution.putMapping(new Ciphertext("anchor"), new Plaintext("o"));
		solution.putMapping(new Ciphertext("backq"), new Plaintext("m"));
		solution.putMapping(new Ciphertext("m"), new Plaintext("h"));
		solution.putMapping(new Ciphertext("j"), new Plaintext("f"));
		solution.putMapping(new Ciphertext("carrot"), new Plaintext("n"));
		solution.putMapping(new Ciphertext("i"), new Plaintext("t"));
		solution.putMapping(new Ciphertext("tridot"), new Plaintext("s"));
		solution.putMapping(new Ciphertext("t"), new Plaintext("o"));
		solution.putMapping(new Ciphertext("flipt"), new Plaintext("r"));
		solution.putMapping(new Ciphertext("n"), new Plaintext("e"));
		solution.putMapping(new Ciphertext("q"), new Plaintext("f"));
		solution.putMapping(new Ciphertext("d"), new Plaintext("n"));
		solution.putMapping(new Ciphertext("fullcircle"), new Plaintext("t"));
		solution.putMapping(new Ciphertext("horstrike"), new Plaintext("h"));
		solution.putMapping(new Ciphertext("s"), new Plaintext("a"));
		solution.putMapping(new Ciphertext("vertstrike"), new Plaintext("n"));
		solution.putMapping(new Ciphertext("fullbox"), new Plaintext("l"));
		solution.putMapping(new Ciphertext("a"), new Plaintext("w"));
		solution.putMapping(new Ciphertext("backf"), new Plaintext("d"));
		solution.putMapping(new Ciphertext("backl"), new Plaintext("a"));
		solution.putMapping(new Ciphertext("e"), new Plaintext("e"));
		solution.putMapping(new Ciphertext("l"), new Plaintext("t"));
		solution.putMapping(new Ciphertext("backd"), new Plaintext("o"));
		solution.putMapping(new Ciphertext("backr"), new Plaintext("r"));
		solution.putMapping(new Ciphertext("backslash"), new Plaintext("r"));
		solution.putMapping(new Ciphertext("fulltri"), new Plaintext("a"));
		solution.putMapping(new Ciphertext("zodiac"), new Plaintext("d"));
		solution.putMapping(new Ciphertext("backc"), new Plaintext("v"));
		solution.putMapping(new Ciphertext("backj"), new Plaintext("x"));
		solution.putMapping(new Ciphertext("box"), new Plaintext("y"));

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
