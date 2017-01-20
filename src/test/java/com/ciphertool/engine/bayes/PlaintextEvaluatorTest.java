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

		solution.addWordBoundary(0); // I
		solution.addWordBoundary(4); // like
		solution.addWordBoundary(11); // killing
		solution.addWordBoundary(17); // people
		solution.addWordBoundary(24); // because
		solution.addWordBoundary(26); // it
		solution.addWordBoundary(28); // is
		solution.addWordBoundary(30); // so
		solution.addWordBoundary(34); // much
		solution.addWordBoundary(37); // fun
		solution.addWordBoundary(39); // it
		solution.addWordBoundary(41); // is
		solution.addWordBoundary(45); // more
		solution.addWordBoundary(48); // fun
		solution.addWordBoundary(52); // than
		solution.addWordBoundary(59); // killing
		solution.addWordBoundary(63); // wild
		solution.addWordBoundary(67); // game
		solution.addWordBoundary(69); // in
		solution.addWordBoundary(72); // the
		solution.addWordBoundary(79); // forrest
		solution.addWordBoundary(86); // because
		solution.addWordBoundary(89); // man
		solution.addWordBoundary(91); // is
		solution.addWordBoundary(94); // the
		solution.addWordBoundary(98); // most
		solution.addWordBoundary(107); // dangeroue
		solution.addWordBoundary(113); // anamal
		solution.addWordBoundary(115); // of
		solution.addWordBoundary(118); // all
		solution.addWordBoundary(120); // to
		solution.addWordBoundary(124); // kill
		solution.addWordBoundary(133); // something
		solution.addWordBoundary(138); // gives
		solution.addWordBoundary(140); // me
		solution.addWordBoundary(143); // the
		solution.addWordBoundary(147); // moat
		solution.addWordBoundary(156); // thrilling
		solution.addWordBoundary(165); // experence
		solution.addWordBoundary(167); // it
		solution.addWordBoundary(169); // is
		solution.addWordBoundary(173); // even
		solution.addWordBoundary(179); // better
		solution.addWordBoundary(183); // than
		solution.addWordBoundary(190); // getting
		solution.addWordBoundary(194); // your
		solution.addWordBoundary(199); // rocks
		solution.addWordBoundary(202); // off
		solution.addWordBoundary(206); // with
		solution.addWordBoundary(207); // a
		solution.addWordBoundary(211); // girl
		solution.addWordBoundary(214); // the
		solution.addWordBoundary(218); // best
		solution.addWordBoundary(222); // part
		solution.addWordBoundary(224); // of
		solution.addWordBoundary(226); // it
		solution.addWordBoundary(228); // ia
		solution.addWordBoundary(232); // thae
		solution.addWordBoundary(236); // when
		solution.addWordBoundary(237); // i
		solution.addWordBoundary(240); // die
		solution.addWordBoundary(241); // i
		solution.addWordBoundary(245); // will
		solution.addWordBoundary(247); // be
		solution.addWordBoundary(253); // reborn
		solution.addWordBoundary(255); // in
		solution.addWordBoundary(263); // paradice
		solution.addWordBoundary(266); // snd
		solution.addWordBoundary(269); // all
		solution.addWordBoundary(272); // the
		solution.addWordBoundary(273); // i
		solution.addWordBoundary(277); // have
		solution.addWordBoundary(283); // killed
		solution.addWordBoundary(287); // will
		solution.addWordBoundary(293); // become
		solution.addWordBoundary(295); // my
		solution.addWordBoundary(301); // slaves
		solution.addWordBoundary(302); // i
		solution.addWordBoundary(306); // will
		solution.addWordBoundary(309); // not
		solution.addWordBoundary(313); // give
		solution.addWordBoundary(316); // you
		solution.addWordBoundary(318); // my
		solution.addWordBoundary(322); // name
		solution.addWordBoundary(329); // because
		solution.addWordBoundary(332); // you
		solution.addWordBoundary(336); // will
		solution.addWordBoundary(339); // try
		solution.addWordBoundary(341); // to
		solution.addWordBoundary(345); // sloi
		solution.addWordBoundary(349); // down
		solution.addWordBoundary(351); // or
		solution.addWordBoundary(355); // stop
		solution.addWordBoundary(357); // my
		solution.addWordBoundary(367); // collecting
		solution.addWordBoundary(369); // of
		solution.addWordBoundary(375); // slaves
		solution.addWordBoundary(378); // for
		solution.addWordBoundary(380); // my
		solution.addWordBoundary(389); // afterlife

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
		plaintextEvaluator.setLetterNGramWeight(0.1);
		plaintextEvaluator.setWordNGramWeight(0.9);
		plaintextEvaluator.setBigDecimalFunctions(new BigDecimalFunctions());
		plaintextEvaluator.init();
	}

	// @Test
	public void testEvaluate() {
		log.info("fitness: " + plaintextEvaluator.evaluate(solution));
		log.info("solution: " + solution);
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
