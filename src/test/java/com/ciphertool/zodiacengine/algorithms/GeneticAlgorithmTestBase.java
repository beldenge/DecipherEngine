/**
 * Copyright 2013 George Belden
 * 
 * This file is part of ZodiacEngine.
 * 
 * ZodiacEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ZodiacEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ZodiacEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.zodiacengine.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.GenericTestBase;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;

public class GeneticAlgorithmTestBase extends GenericTestBase {
	protected static SolutionChromosome knownSolution = new SolutionChromosome();

	static {
		knownSolution.setCipherId(zodiac408.getId());

		knownSolution.setRows(24);
		knownSolution.setColumns(17);

		List<PlaintextSequence> plaintextCharacters = new ArrayList<PlaintextSequence>();
		/*
		 * PartOfSpeech is not used by the evaluator, so set it to something
		 * arbitrary
		 */
		WordGene nextWordGene;

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("like", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killing", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("people", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("so", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("much", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("fun", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("more", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("fun", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("than", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killing", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("wild", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("game", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("in", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("forrest", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("man", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("moat", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("dangeroue", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("anamal", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("all", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("to", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("kill", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("something", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("gives", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("me", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("moat", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("thrilling", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("experence", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("x", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("even", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("better", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("than", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("getting", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("your", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("rocks", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("off", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("with", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("a", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("girl", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("best", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("part", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("ia", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("thae", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("when", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("die", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("be", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("reborn", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("in", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("paradice", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("snd", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("all", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("have", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killed", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("become", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("slaves", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("not", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("give", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("you", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("name", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("you", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("try", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("to", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("sloi", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("down", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("or", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("stop", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("collecting", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("slaves", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("for", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("afterlife", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("e", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("beorietemethhpiti", 'X')), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
	}
}
