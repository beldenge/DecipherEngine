package com.ciphertool.zodiacengine.entities;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SolutionSetTest {

	@Test
	public void testGetNullPlaintextCharacters() {
		SolutionSet solutionSet = new SolutionSet();

		assertNotNull(solutionSet.getSolutions());
	}
}
