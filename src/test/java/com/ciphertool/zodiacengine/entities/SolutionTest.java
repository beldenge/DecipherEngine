package com.ciphertool.zodiacengine.entities;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SolutionTest {

	@Test
	public void testGetNullPlaintextCharacters() {
		Solution solution = new Solution();

		assertNotNull(solution.getPlaintextCharacters());
	}
}
