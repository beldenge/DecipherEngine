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

		solution.addWordBoundary(new WordBoundary(0)); // I
		solution.addWordBoundary(new WordBoundary(4)); // like
		solution.addWordBoundary(new WordBoundary(11)); // killing
		solution.addWordBoundary(new WordBoundary(17)); // people
		solution.addWordBoundary(new WordBoundary(24)); // because
		solution.addWordBoundary(new WordBoundary(26)); // it
		solution.addWordBoundary(new WordBoundary(28)); // is
		solution.addWordBoundary(new WordBoundary(30)); // so
		solution.addWordBoundary(new WordBoundary(34)); // much
		solution.addWordBoundary(new WordBoundary(37)); // fun
		solution.addWordBoundary(new WordBoundary(39)); // it
		solution.addWordBoundary(new WordBoundary(41)); // is
		solution.addWordBoundary(new WordBoundary(45)); // more
		solution.addWordBoundary(new WordBoundary(48)); // fun
		solution.addWordBoundary(new WordBoundary(52)); // than
		solution.addWordBoundary(new WordBoundary(59)); // killing
		solution.addWordBoundary(new WordBoundary(63)); // wild
		solution.addWordBoundary(new WordBoundary(67)); // game
		solution.addWordBoundary(new WordBoundary(69)); // in
		solution.addWordBoundary(new WordBoundary(72)); // the
		solution.addWordBoundary(new WordBoundary(79)); // forrest
		solution.addWordBoundary(new WordBoundary(86)); // because
		solution.addWordBoundary(new WordBoundary(89)); // man
		solution.addWordBoundary(new WordBoundary(91)); // is
		solution.addWordBoundary(new WordBoundary(94)); // the
		solution.addWordBoundary(new WordBoundary(98)); // most
		solution.addWordBoundary(new WordBoundary(107)); // dangeroue
		solution.addWordBoundary(new WordBoundary(113)); // anamal
		solution.addWordBoundary(new WordBoundary(115)); // of
		solution.addWordBoundary(new WordBoundary(118)); // all
		solution.addWordBoundary(new WordBoundary(120)); // to
		solution.addWordBoundary(new WordBoundary(124)); // kill
		solution.addWordBoundary(new WordBoundary(133)); // something
		solution.addWordBoundary(new WordBoundary(138)); // gives
		solution.addWordBoundary(new WordBoundary(140)); // me
		solution.addWordBoundary(new WordBoundary(143)); // the
		solution.addWordBoundary(new WordBoundary(147)); // moat
		solution.addWordBoundary(new WordBoundary(156)); // thrilling
		solution.addWordBoundary(new WordBoundary(165)); // experence
		solution.addWordBoundary(new WordBoundary(167)); // it
		solution.addWordBoundary(new WordBoundary(169)); // is
		solution.addWordBoundary(new WordBoundary(173)); // even
		solution.addWordBoundary(new WordBoundary(179)); // better
		solution.addWordBoundary(new WordBoundary(183)); // than
		solution.addWordBoundary(new WordBoundary(190)); // getting
		solution.addWordBoundary(new WordBoundary(194)); // your
		solution.addWordBoundary(new WordBoundary(199)); // rocks
		solution.addWordBoundary(new WordBoundary(202)); // off
		solution.addWordBoundary(new WordBoundary(206)); // with
		solution.addWordBoundary(new WordBoundary(207)); // a
		solution.addWordBoundary(new WordBoundary(211)); // girl
		solution.addWordBoundary(new WordBoundary(214)); // the
		solution.addWordBoundary(new WordBoundary(218)); // best
		solution.addWordBoundary(new WordBoundary(222)); // part
		solution.addWordBoundary(new WordBoundary(224)); // of
		solution.addWordBoundary(new WordBoundary(226)); // it
		solution.addWordBoundary(new WordBoundary(228)); // ia
		solution.addWordBoundary(new WordBoundary(232)); // thae
		solution.addWordBoundary(new WordBoundary(236)); // when
		solution.addWordBoundary(new WordBoundary(237)); // i
		solution.addWordBoundary(new WordBoundary(240)); // die
		solution.addWordBoundary(new WordBoundary(241)); // i
		solution.addWordBoundary(new WordBoundary(245)); // will
		solution.addWordBoundary(new WordBoundary(247)); // be
		solution.addWordBoundary(new WordBoundary(253)); // reborn
		solution.addWordBoundary(new WordBoundary(255)); // in
		solution.addWordBoundary(new WordBoundary(263)); // paradice
		solution.addWordBoundary(new WordBoundary(266)); // snd
		solution.addWordBoundary(new WordBoundary(269)); // all
		solution.addWordBoundary(new WordBoundary(272)); // the
		solution.addWordBoundary(new WordBoundary(273)); // i
		solution.addWordBoundary(new WordBoundary(277)); // have
		solution.addWordBoundary(new WordBoundary(283)); // killed
		solution.addWordBoundary(new WordBoundary(287)); // will
		solution.addWordBoundary(new WordBoundary(293)); // become
		solution.addWordBoundary(new WordBoundary(295)); // my
		solution.addWordBoundary(new WordBoundary(301)); // slaves
		solution.addWordBoundary(new WordBoundary(302)); // i
		solution.addWordBoundary(new WordBoundary(306)); // will
		solution.addWordBoundary(new WordBoundary(309)); // not
		solution.addWordBoundary(new WordBoundary(313)); // give
		solution.addWordBoundary(new WordBoundary(316)); // you
		solution.addWordBoundary(new WordBoundary(318)); // my
		solution.addWordBoundary(new WordBoundary(322)); // name
		solution.addWordBoundary(new WordBoundary(329)); // because
		solution.addWordBoundary(new WordBoundary(332)); // you
		solution.addWordBoundary(new WordBoundary(336)); // will
		solution.addWordBoundary(new WordBoundary(339)); // try
		solution.addWordBoundary(new WordBoundary(341)); // to
		solution.addWordBoundary(new WordBoundary(345)); // sloi
		solution.addWordBoundary(new WordBoundary(349)); // down
		solution.addWordBoundary(new WordBoundary(351)); // or
		solution.addWordBoundary(new WordBoundary(355)); // stop
		solution.addWordBoundary(new WordBoundary(357)); // my
		solution.addWordBoundary(new WordBoundary(367)); // collecting
		solution.addWordBoundary(new WordBoundary(369)); // of
		solution.addWordBoundary(new WordBoundary(375)); // slaves
		solution.addWordBoundary(new WordBoundary(378)); // for
		solution.addWordBoundary(new WordBoundary(380)); // my
		solution.addWordBoundary(new WordBoundary(389)); // afterlife

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
		letterNGramMarkovImporter.setMinCount(1);
		letterNGramMarkovImporter.setTaskExecutor(taskExecutorSpy);
		letterNGramMarkovImporter.importCorpus();

		wordMarkovModel = new MarkovModel();
		wordMarkovModel.setOrder(1);
		wordMarkovModel.setTaskExecutor(taskExecutorSpy);

		wordNGramMarkovImporter = new WordNGramMarkovImporter();
		wordNGramMarkovImporter.setWordMarkovModel(wordMarkovModel);
		wordNGramMarkovImporter.setCorpusDirectory("/Users/george/Desktop/sherlock-transformed");
		wordNGramMarkovImporter.setMinCount(1);
		wordNGramMarkovImporter.setTaskExecutor(taskExecutorSpy);
		wordNGramMarkovImporter.importCorpus();

		plaintextEvaluator = new PlaintextEvaluator();
		plaintextEvaluator.setLetterMarkovModel(letterMarkovModel);
		plaintextEvaluator.setWordMarkovModel(wordMarkovModel);
		plaintextEvaluator.setCipher(zodiac408);
		plaintextEvaluator.setLetterNGramWeight(0.1);
		plaintextEvaluator.setWordNGramWeight(0.9);
		plaintextEvaluator.setTaskExecutor(taskExecutorSpy);
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
