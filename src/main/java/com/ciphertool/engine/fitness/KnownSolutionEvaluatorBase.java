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

package com.ciphertool.engine.fitness;

import com.ciphertool.engine.entities.SolutionChromosome;
import com.ciphertool.engine.entities.WordGene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;

public class KnownSolutionEvaluatorBase extends SolutionTruncatedEvaluatorBase {
	protected static SolutionChromosome knownSolution;

	static {
		knownSolution = new SolutionChromosome();

		knownSolution.setRows(24);
		knownSolution.setColumns(17);

		/*
		 * PartOfSpeech is not used by the evaluator, so set it to something arbitrary
		 */
		WordGene nextWordGene;

		nextWordGene = new WordGene(new Word("i", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("like", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("killing", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("people", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("because", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("it", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("is", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("so", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("much", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("fun", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("it", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("is", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("more", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("fun", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("than", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("killing", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("wild", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("game", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("in", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("the", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("forrest", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("because", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("man", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("is", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("the", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("moat", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("dangeroue", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("anamal", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("of", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("all", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("to", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("kill", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("something", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("gives", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("me", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("the", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("moat", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("thrilling", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("experence", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("it", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("is", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("even", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("better", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("than", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("getting", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("your", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("rocks", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("off", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("with", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("a", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("girl", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("the", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("best", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("part", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("of", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("it", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("ia", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("thae", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("when", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("i", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("die", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("i", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("will", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("be", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("reborn", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("in", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("paradice", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("snd", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("all", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("the", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("i", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("have", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("killed", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("will", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("become", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("my", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("slaves", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("i", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("will", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("not", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("give", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("you", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("my", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("name", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("because", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("you", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("will", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("try", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("to", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("sloi", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("down", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("or", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("stop", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("my", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("collecting", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("of", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("slaves", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("for", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("my", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("afterlife", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("e", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word("beorietemethhpiti", PartOfSpeechType.NONE), knownSolution);
		knownSolution.addGene(nextWordGene);
	}
}
