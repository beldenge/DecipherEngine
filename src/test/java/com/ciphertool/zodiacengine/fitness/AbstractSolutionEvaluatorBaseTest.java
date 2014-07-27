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

package com.ciphertool.zodiacengine.fitness;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.ciphertool.sentencebuilder.common.PartOfSpeechType;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;

public class AbstractSolutionEvaluatorBaseTest {
	/**
	 * Dummy concrete class extending AbstractSolutionEvaluatorBase so that we
	 * can instantiate it for unit testing purposes
	 */
	private class ConcreteSolutionEvaluatorBase extends AbstractSolutionEvaluatorBase {
		@Override
		protected HashMap<String, List<Ciphertext>> createKeyFromCiphertext() {
			return null;
		}

		@Override
		protected void clearHasMatchValues(SolutionChromosome solutionChromosome) {
		}

		protected void setGeneticStructure(Object cipher) {
			this.cipher = (Cipher) cipher;
		}
	}

	private static Cipher simpleCipher = new Cipher("simpleCipher", 1, 10);
	private static SolutionChromosome simpleSolution = new SolutionChromosome();

	static {
		simpleCipher.setId(BigInteger.ZERO);
		simpleCipher.setHasKnownSolution(true);

		simpleCipher.addCiphertextCharacter(new Ciphertext(0, "a"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(1, "b"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(2, "c"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(3, "d"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(4, "e"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(5, "f"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(6, "g"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(7, "h"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(8, "i"));
		simpleCipher.addCiphertextCharacter(new Ciphertext(9, "j"));

		simpleSolution.setCipherId(simpleCipher.getId());

		simpleSolution.setRows(1);
		simpleSolution.setColumns(10);

		WordGene simpleWordGene = new WordGene(new Word(new WordId("abcdefghij",
				PartOfSpeechType.NONE)), simpleSolution);
		simpleSolution.addGene(simpleWordGene);

		((PlaintextSequence) simpleWordGene.getSequences().get(0)).setHasMatch(true);
		((PlaintextSequence) simpleWordGene.getSequences().get(1)).setHasMatch(true);
		((PlaintextSequence) simpleWordGene.getSequences().get(4)).setHasMatch(true);
		((PlaintextSequence) simpleWordGene.getSequences().get(5)).setHasMatch(true);
		((PlaintextSequence) simpleWordGene.getSequences().get(6)).setHasMatch(true);
		((PlaintextSequence) simpleWordGene.getSequences().get(8)).setHasMatch(true);
		((PlaintextSequence) simpleWordGene.getSequences().get(9)).setHasMatch(true);
	}

	@Test
	public void calculateAdjacentMatches() {
		ConcreteSolutionEvaluatorBase abstractSolutionEvaluatorBase = new ConcreteSolutionEvaluatorBase();

		assertEquals(0, simpleSolution.getAdjacentMatches());

		abstractSolutionEvaluatorBase.setGeneticStructure(simpleCipher);
		int adjacentMatches = abstractSolutionEvaluatorBase.calculateAdjacentMatches(simpleSolution
				.getPlaintextCharacters());

		/*
		 * The adjacent match count should not be updated on the solution. It
		 * should only be returned by the method.
		 */
		assertEquals(0, simpleSolution.getAdjacentMatches());
		assertEquals(4, adjacentMatches);
	}
}
