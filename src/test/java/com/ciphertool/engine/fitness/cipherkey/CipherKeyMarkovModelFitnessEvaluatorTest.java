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

import com.ciphertool.engine.dao.cipherkey.MarkovModelDao;
import com.ciphertool.engine.entities.cipherkey.CipherKeyChromosome;
import com.ciphertool.engine.entities.cipherkey.CipherKeyGene;
import com.ciphertool.engine.fitness.FitnessEvaluatorTestBase;
import com.ciphertool.sherlock.etl.importers.MarkovImporterImpl;

public class CipherKeyMarkovModelFitnessEvaluatorTest extends FitnessEvaluatorTestBase {
	private static final int							ORDER		= 6;

	private static MarkovModelDao						markovModelDao;
	private static MarkovImporterImpl					importer;

	private static CipherKeyMarkovModelFitnessEvaluator	fitnessEvaluator;

	private static CipherKeyChromosome					solution	= new CipherKeyChromosome();

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
	public static void setUp() {
		importer = new MarkovImporterImpl();
		importer.setCorpusDirectory("../Sherlock/src/main/data/corpus");
		importer.setOrder(ORDER);

		markovModelDao = new MarkovModelDao();
		markovModelDao.setImporter(importer);
		markovModelDao.init();

		fitnessEvaluator = new CipherKeyMarkovModelFitnessEvaluator();
		fitnessEvaluator.setMarkovModelDao(markovModelDao);
		fitnessEvaluator.setGeneticStructure(zodiac408);
		fitnessEvaluator.init();
	}

	// @Test
	public void testEvaluate() {
		System.out.println("fitness: " + fitnessEvaluator.evaluate(solution));
	}
}
