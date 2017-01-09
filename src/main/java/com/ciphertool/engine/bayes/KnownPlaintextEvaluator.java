package com.ciphertool.engine.bayes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KnownPlaintextEvaluator {
	private Logger					log				= LoggerFactory.getLogger(getClass());

	private static CipherSolution	knownSolution	= new CipherSolution();

	static {
		knownSolution.putMapping("tri", new Plaintext("i"));
		knownSolution.putMapping("lrbox", new Plaintext("l"));
		knownSolution.putMapping("p", new Plaintext("i"));
		knownSolution.putMapping("forslash", new Plaintext("k"));
		knownSolution.putMapping("z", new Plaintext("e"));
		knownSolution.putMapping("u", new Plaintext("i"));
		knownSolution.putMapping("b", new Plaintext("l"));
		knownSolution.putMapping("backk", new Plaintext("i"));
		knownSolution.putMapping("o", new Plaintext("n"));
		knownSolution.putMapping("r", new Plaintext("g"));
		knownSolution.putMapping("pi", new Plaintext("p"));
		knownSolution.putMapping("backp", new Plaintext("e"));
		knownSolution.putMapping("x", new Plaintext("o"));
		knownSolution.putMapping("w", new Plaintext("e"));
		knownSolution.putMapping("v", new Plaintext("b"));
		knownSolution.putMapping("plus", new Plaintext("e"));
		knownSolution.putMapping("backe", new Plaintext("c"));
		knownSolution.putMapping("g", new Plaintext("a"));
		knownSolution.putMapping("y", new Plaintext("u"));
		knownSolution.putMapping("f", new Plaintext("s"));
		knownSolution.putMapping("circledot", new Plaintext("e"));
		knownSolution.putMapping("h", new Plaintext("t"));
		knownSolution.putMapping("boxdot", new Plaintext("s"));
		knownSolution.putMapping("k", new Plaintext("s"));
		knownSolution.putMapping("anchor", new Plaintext("o"));
		knownSolution.putMapping("backq", new Plaintext("m"));
		knownSolution.putMapping("m", new Plaintext("h"));
		knownSolution.putMapping("j", new Plaintext("f"));
		knownSolution.putMapping("carrot", new Plaintext("n"));
		knownSolution.putMapping("i", new Plaintext("t"));
		knownSolution.putMapping("tridot", new Plaintext("s"));
		knownSolution.putMapping("t", new Plaintext("o"));
		knownSolution.putMapping("flipt", new Plaintext("r"));
		knownSolution.putMapping("n", new Plaintext("e"));
		knownSolution.putMapping("q", new Plaintext("f"));
		knownSolution.putMapping("d", new Plaintext("n"));
		knownSolution.putMapping("fullcircle", new Plaintext("t"));
		knownSolution.putMapping("horstrike", new Plaintext("h"));
		knownSolution.putMapping("s", new Plaintext("a"));
		knownSolution.putMapping("vertstrike", new Plaintext("n"));
		knownSolution.putMapping("fullbox", new Plaintext("l"));
		knownSolution.putMapping("a", new Plaintext("w"));
		knownSolution.putMapping("backf", new Plaintext("d"));
		knownSolution.putMapping("backl", new Plaintext("a"));
		knownSolution.putMapping("e", new Plaintext("e"));
		knownSolution.putMapping("l", new Plaintext("t"));
		knownSolution.putMapping("backd", new Plaintext("o"));
		knownSolution.putMapping("backr", new Plaintext("r"));
		knownSolution.putMapping("backslash", new Plaintext("r"));
		knownSolution.putMapping("fulltri", new Plaintext("a"));
		knownSolution.putMapping("zodiac", new Plaintext("d"));
		knownSolution.putMapping("backc", new Plaintext("v"));
		knownSolution.putMapping("backj", new Plaintext("x"));
		knownSolution.putMapping("box", new Plaintext("y"));
	}

	/**
	 * Default no-args constructor
	 */
	public KnownPlaintextEvaluator() {
	}

	public Double evaluate(CipherSolution solution) {
		double total = 0.0;

		if (knownSolution.getMappings().size() != solution.getMappings().size()) {
			log.error("Current solution size of " + solution.getMappings().size()
					+ " does not match the known solution size of " + knownSolution.getMappings().size()
					+ ".  This will cause innacurate fitness calculations.  Solution: " + solution);
		}

		for (String key : knownSolution.getMappings().keySet()) {
			if (knownSolution.getMappings().get(key).equals(solution.getMappings().get(key))) {
				total++;
			}
		}

		double proximityToKnownSolution = (total / (double) solution.getMappings().size());

		if (log.isDebugEnabled()) {
			log.debug("Solution has a confidence level of: " + proximityToKnownSolution);
		}

		return proximityToKnownSolution;
	}
}