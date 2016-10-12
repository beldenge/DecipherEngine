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

package com.ciphertool.DecipherEngine.fitness;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;
import com.ciphertool.DecipherEngine.entities.SolutionChromosome;
import com.ciphertool.DecipherEngine.entities.WordGene;

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

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("like", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("killing", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("people", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("because", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("so", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("much", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("fun", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("more", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("fun", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("than", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("killing", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("wild", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("game", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("in", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("forrest", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("because", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("man", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("moat", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("dangeroue", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("anamal", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("of", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("all", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("to", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("kill", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("something", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("gives", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("me", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("moat", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("thrilling", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("experence", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("even", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("better", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("than", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("getting", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("your", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("rocks", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("off", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("with", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("a", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("girl", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("best", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("part", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("of", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("ia", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("thae", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("when", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("die", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("be", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("reborn", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("in", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("paradice", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("snd", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("all", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("have", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("killed", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("become", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("slaves", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("not", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("give", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("you", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("name", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("because", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("you", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("try", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("to", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("sloi", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("down", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("or", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("stop", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("collecting", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("of", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("slaves", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("for", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("afterlife", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("e", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);

		nextWordGene = new WordGene(new Word(new WordId("beorietemethhpiti", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
	}
}
