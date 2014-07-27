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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;
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

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("like", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killing", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("people", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("so", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("much", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("fun", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("more", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("fun", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("than", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killing", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("wild", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("game", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("in", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("forrest", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("man", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("moat", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("dangeroue", PartOfSpeechType.NONE)),
				knownSolution);
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

		nextWordGene = new WordGene(new Word(new WordId("anamal", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("all", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("to", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("kill", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("something", PartOfSpeechType.NONE)),
				knownSolution);
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

		nextWordGene = new WordGene(new Word(new WordId("gives", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("me", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("moat", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("thrilling", PartOfSpeechType.NONE)),
				knownSolution);
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

		nextWordGene = new WordGene(new Word(new WordId("experence", PartOfSpeechType.NONE)),
				knownSolution);
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

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("even", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("better", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("than", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("getting", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("your", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("rocks", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("off", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("with", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("a", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("girl", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("best", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("part", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("ia", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("thae", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("when", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("die", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("be", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("reborn", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("in", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("paradice", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("snd", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("all", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("have", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killed", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("become", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("slaves", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("not", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("give", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("you", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("name", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("you", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("try", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("to", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("sloi", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("down", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("or", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("stop", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("collecting", PartOfSpeechType.NONE)),
				knownSolution);
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

		nextWordGene = new WordGene(new Word(new WordId("of", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("slaves", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("for", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)),
				knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("afterlife", PartOfSpeechType.NONE)),
				knownSolution);
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

		nextWordGene = new WordGene(new Word(new WordId("e", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(
				new Word(new WordId("beorietemethhpiti", PartOfSpeechType.NONE)), knownSolution);
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

	@Before
	public void resetDirtiness() {
		knownSolution.setEvaluationNeeded(true);
		knownSolution.setTotalMatches(0);
		knownSolution.setUniqueMatches(0);
		knownSolution.setAdjacentMatches(0);
		assertTrue(knownSolution.isEvaluationNeeded());
	}
}
