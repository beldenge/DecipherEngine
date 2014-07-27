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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

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
			return new HashMap<String, List<Ciphertext>>();
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
	}

	@Test
	public void testCalculateAdjacentMatches() {
		ConcreteSolutionEvaluatorBase abstractSolutionEvaluatorBase = new ConcreteSolutionEvaluatorBase();

		Field logField = ReflectionUtils.findField(ConcreteSolutionEvaluatorBase.class, "log");
		Logger mockLogger = mock(Logger.class);
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, abstractSolutionEvaluatorBase, mockLogger);

		simpleSolution.getPlaintextCharacters().get(0).setHasMatch(true);
		simpleSolution.getPlaintextCharacters().get(1).setHasMatch(true);
		simpleSolution.getPlaintextCharacters().get(4).setHasMatch(true);
		simpleSolution.getPlaintextCharacters().get(5).setHasMatch(true);
		simpleSolution.getPlaintextCharacters().get(6).setHasMatch(true);
		simpleSolution.getPlaintextCharacters().get(8).setHasMatch(true);
		simpleSolution.getPlaintextCharacters().get(9).setHasMatch(true);

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

		verifyZeroInteractions(mockLogger);
	}

	@Test
	public void testCalculateAdjacentMatches_NullPlaintextCharacters() {
		ConcreteSolutionEvaluatorBase abstractSolutionEvaluatorBase = new ConcreteSolutionEvaluatorBase();

		Field logField = ReflectionUtils.findField(ConcreteSolutionEvaluatorBase.class, "log");
		Logger mockLogger = mock(Logger.class);
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, abstractSolutionEvaluatorBase, mockLogger);

		abstractSolutionEvaluatorBase.calculateAdjacentMatches(null);

		verify(mockLogger, times(1))
				.warn("Attempted to calculate adjacent matches, but the List of plaintextCharacters was null or empty.  Returning -1.");
	}

	@Test
	public void testClearHasMatchValues() {
		ConcreteSolutionEvaluatorBase abstractSolutionEvaluatorBase = new ConcreteSolutionEvaluatorBase();

		Field logField = ReflectionUtils.findField(ConcreteSolutionEvaluatorBase.class, "log");
		Logger mockLogger = mock(Logger.class);
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, abstractSolutionEvaluatorBase, mockLogger);

		for (PlaintextSequence plaintextSequence : simpleSolution.getPlaintextCharacters()) {
			plaintextSequence.setHasMatch(true);
			assertTrue(plaintextSequence.getHasMatch());
		}

		abstractSolutionEvaluatorBase.clearHasMatchValues(simpleSolution);

		for (PlaintextSequence plaintextSequence : simpleSolution.getPlaintextCharacters()) {
			assertFalse(plaintextSequence.getHasMatch());
		}

		verifyZeroInteractions(mockLogger);
	}

	@Test
	public void testClearHasMatchValues_NullSolution() {
		ConcreteSolutionEvaluatorBase abstractSolutionEvaluatorBase = new ConcreteSolutionEvaluatorBase();

		Field logField = ReflectionUtils.findField(ConcreteSolutionEvaluatorBase.class, "log");
		Logger mockLogger = mock(Logger.class);
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, abstractSolutionEvaluatorBase, mockLogger);

		abstractSolutionEvaluatorBase.clearHasMatchValues(null);

		verify(mockLogger, times(1))
				.warn("Attempted to clear hasMatch values, but the SolutionChromosome was null.  Returning early.");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSetGeneticStructure() {
		ConcreteSolutionEvaluatorBase abstractSolutionEvaluatorBase = new ConcreteSolutionEvaluatorBase();

		abstractSolutionEvaluatorBase.setGeneticStructure(simpleCipher);

		Field cipherField = ReflectionUtils
				.findField(ConcreteSolutionEvaluatorBase.class, "cipher");
		ReflectionUtils.makeAccessible(cipherField);
		Cipher cipherFromObject = (Cipher) ReflectionUtils.getField(cipherField,
				abstractSolutionEvaluatorBase);

		assertEquals(simpleCipher, cipherFromObject);

		Field ciphertextKeyField = ReflectionUtils.findField(ConcreteSolutionEvaluatorBase.class,
				"ciphertextKey");
		ReflectionUtils.makeAccessible(ciphertextKeyField);
		HashMap<String, List<Ciphertext>> ciphertextKeyFromObject = (HashMap<String, List<Ciphertext>>) ReflectionUtils
				.getField(ciphertextKeyField, abstractSolutionEvaluatorBase);

		assertNotNull(ciphertextKeyFromObject);
	}
}
