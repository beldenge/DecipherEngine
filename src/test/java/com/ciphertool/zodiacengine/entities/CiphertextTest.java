package com.ciphertool.zodiacengine.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class CiphertextTest {
	@Test
	public void testConstructor() {
		Integer ciphertextIdToSet = new Integer(123);
		String valueToSet = "ciphertextValue";
		Ciphertext ciphertext = new Ciphertext(ciphertextIdToSet, valueToSet);

		assertSame(ciphertextIdToSet, ciphertext.getCiphertextId());
		assertEquals(valueToSet, ciphertext.getValue());
	}

	@Test
	public void testSetCiphertextId() {
		Integer ciphertextIdToSet = new Integer(123);
		Ciphertext ciphertext = new Ciphertext();
		ciphertext.setCiphertextId(ciphertextIdToSet);

		assertSame(ciphertextIdToSet, ciphertext.getCiphertextId());
	}

	@Test
	public void testSetValue() {
		String valueToSet = "ciphertextValue";
		Ciphertext ciphertext = new Ciphertext();
		ciphertext.setValue(valueToSet);

		assertEquals(valueToSet, ciphertext.getValue());
	}

	@Test
	public void testEquals() {
		Integer baseCiphertextId = new Integer(123);
		String baseValue = "baseValue";

		Ciphertext base = new Ciphertext(baseCiphertextId, baseValue);

		Ciphertext ciphertextEqualToBase = new Ciphertext(baseCiphertextId, baseValue);
		assertEquals(base, ciphertextEqualToBase);

		Ciphertext ciphertextWithDifferentCiphertextId = new Ciphertext(321, baseValue);
		assertFalse(base.equals(ciphertextWithDifferentCiphertextId));

		Ciphertext ciphertextWithDifferentValue = new Ciphertext(baseCiphertextId, "differentValue");
		assertFalse(base.equals(ciphertextWithDifferentValue));

		Ciphertext ciphertextWithNullPropertiesA = new Ciphertext();
		Ciphertext ciphertextWithNullPropertiesB = new Ciphertext();
		assertEquals(ciphertextWithNullPropertiesA, ciphertextWithNullPropertiesB);
	}
}
