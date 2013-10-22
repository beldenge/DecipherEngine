package com.ciphertool.zodiacengine.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

public class CipherTest {
	@Test
	public void testConstructor() {
		String nameToSet = "cipherName";
		int rowsToSet = 5;
		int columnsToSet = 10;
		Cipher cipher = new Cipher(nameToSet, rowsToSet, columnsToSet);

		assertSame(nameToSet, cipher.getName());
		assertEquals(rowsToSet, cipher.getRows());
		assertEquals(columnsToSet, cipher.getColumns());
	}

	@Test
	public void testSetId() {
		Cipher cipher = new Cipher();
		BigInteger idToSet = new BigInteger("999");
		cipher.setId(idToSet);

		assertSame(idToSet, cipher.getId());
	}

	@Test
	public void testSetName() {
		Cipher cipher = new Cipher();
		String nameToSet = "cipherName";
		cipher.setName(nameToSet);

		assertEquals(nameToSet, cipher.getName());
	}

	@Test
	public void testSetColumns() {
		Cipher cipher = new Cipher();
		int columnsToSet = 10;
		cipher.setColumns(columnsToSet);

		assertEquals(columnsToSet, cipher.getColumns());
	}

	@Test
	public void testSetRows() {
		Cipher cipher = new Cipher();
		int rowsToSet = 5;
		cipher.setRows(rowsToSet);

		assertEquals(rowsToSet, cipher.getRows());
	}

	@Test
	public void testSetHasKnownSolution() {
		Cipher cipher = new Cipher();
		cipher.setHasKnownSolution(true);

		assertTrue(cipher.hasKnownSolution());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testCiphertextCharactersUnmodifiable() {
		Cipher cipher = new Cipher();
		cipher.addCiphertextCharacter(new Ciphertext(1, "a"));
		cipher.addCiphertextCharacter(new Ciphertext(2, "b"));
		cipher.addCiphertextCharacter(new Ciphertext(3, "c"));

		List<Ciphertext> ciphertextCharacters = cipher.getCiphertextCharacters();
		ciphertextCharacters.remove(0); // should throw exception
	}

	@Test
	public void testAddCiphertextCharacter() {
		Cipher cipher = new Cipher();
		assertEquals(0, cipher.getCiphertextCharacters().size());

		Ciphertext ciphertext1 = new Ciphertext(1, "a");
		cipher.addCiphertextCharacter(ciphertext1);
		Ciphertext ciphertext2 = new Ciphertext(2, "b");
		cipher.addCiphertextCharacter(ciphertext2);
		Ciphertext ciphertext3 = new Ciphertext(3, "c");
		cipher.addCiphertextCharacter(ciphertext3);

		assertEquals(3, cipher.getCiphertextCharacters().size());
		assertSame(ciphertext1, cipher.getCiphertextCharacters().get(0));
		assertSame(ciphertext2, cipher.getCiphertextCharacters().get(1));
		assertSame(ciphertext3, cipher.getCiphertextCharacters().get(2));
	}

	@Test
	public void testRemoveCiphertextCharacter() {
		Cipher cipher = new Cipher();

		Ciphertext ciphertext1 = new Ciphertext(1, "a");
		cipher.addCiphertextCharacter(ciphertext1);
		Ciphertext ciphertext2 = new Ciphertext(2, "b");
		cipher.addCiphertextCharacter(ciphertext2);
		Ciphertext ciphertext3 = new Ciphertext(3, "c");
		cipher.addCiphertextCharacter(ciphertext3);

		assertEquals(3, cipher.getCiphertextCharacters().size());

		cipher.removeCiphertextCharacter(ciphertext2);

		assertEquals(2, cipher.getCiphertextCharacters().size());
		assertSame(ciphertext1, cipher.getCiphertextCharacters().get(0));
		assertSame(ciphertext3, cipher.getCiphertextCharacters().get(1));
	}

	@Test
	public void testLength() {
		Cipher cipher = new Cipher();
		assertEquals(0, cipher.length());

		cipher.setRows(2);
		cipher.setColumns(3);

		assertEquals(6, cipher.length());
	}

	@Test
	public void testEquals() {
		BigInteger baseId = new BigInteger("123");
		String baseName = "baseName";
		int baseRows = 10;
		int baseColumns = 5;
		boolean baseHasKnownSolution = true;
		Ciphertext ciphertext1 = new Ciphertext(1, "a");
		Ciphertext ciphertext2 = new Ciphertext(2, "b");
		Ciphertext ciphertext3 = new Ciphertext(3, "c");

		Cipher base = new Cipher(baseName, baseRows, baseColumns);
		base.setId(baseId);
		base.setHasKnownSolution(baseHasKnownSolution);
		base.addCiphertextCharacter(ciphertext1);
		base.addCiphertextCharacter(ciphertext2);
		base.addCiphertextCharacter(ciphertext3);

		Cipher cipherEqualToBase = new Cipher(baseName, baseRows, baseColumns);
		cipherEqualToBase.setId(baseId);
		cipherEqualToBase.setHasKnownSolution(baseHasKnownSolution);
		cipherEqualToBase.addCiphertextCharacter(ciphertext1);
		cipherEqualToBase.addCiphertextCharacter(ciphertext2);
		cipherEqualToBase.addCiphertextCharacter(ciphertext3);
		assertEquals(base, cipherEqualToBase);

		Cipher cipherWithDifferentName = new Cipher("differentName", baseRows, baseColumns);
		cipherWithDifferentName.setId(baseId);
		cipherWithDifferentName.setHasKnownSolution(baseHasKnownSolution);
		cipherWithDifferentName.addCiphertextCharacter(ciphertext1);
		cipherWithDifferentName.addCiphertextCharacter(ciphertext2);
		cipherWithDifferentName.addCiphertextCharacter(ciphertext3);
		assertFalse(base.equals(cipherWithDifferentName));

		Cipher cipherWithDifferentRows = new Cipher(baseName, 9, baseColumns);
		cipherWithDifferentRows.setId(baseId);
		cipherWithDifferentRows.setHasKnownSolution(baseHasKnownSolution);
		cipherWithDifferentRows.addCiphertextCharacter(ciphertext1);
		cipherWithDifferentRows.addCiphertextCharacter(ciphertext2);
		cipherWithDifferentRows.addCiphertextCharacter(ciphertext3);
		assertFalse(base.equals(cipherWithDifferentRows));

		Cipher cipherWithDifferentColumns = new Cipher(baseName, baseRows, 4);
		cipherWithDifferentColumns.setId(baseId);
		cipherWithDifferentColumns.setHasKnownSolution(baseHasKnownSolution);
		cipherWithDifferentColumns.addCiphertextCharacter(ciphertext1);
		cipherWithDifferentColumns.addCiphertextCharacter(ciphertext2);
		cipherWithDifferentColumns.addCiphertextCharacter(ciphertext3);
		assertFalse(base.equals(cipherWithDifferentColumns));

		Cipher cipherWithDifferentId = new Cipher(baseName, baseRows, baseColumns);
		cipherWithDifferentId.setId(new BigInteger("321"));
		cipherWithDifferentId.setHasKnownSolution(baseHasKnownSolution);
		cipherWithDifferentId.addCiphertextCharacter(ciphertext1);
		cipherWithDifferentId.addCiphertextCharacter(ciphertext2);
		cipherWithDifferentId.addCiphertextCharacter(ciphertext3);
		assertFalse(base.equals(cipherWithDifferentId));

		Cipher cipherWithDifferentHasKnownSolution = new Cipher(baseName, baseRows, baseColumns);
		cipherWithDifferentHasKnownSolution.setId(baseId);
		cipherWithDifferentHasKnownSolution.setHasKnownSolution(false);
		cipherWithDifferentHasKnownSolution.addCiphertextCharacter(ciphertext1);
		cipherWithDifferentHasKnownSolution.addCiphertextCharacter(ciphertext2);
		cipherWithDifferentHasKnownSolution.addCiphertextCharacter(ciphertext3);
		assertFalse(base.equals(cipherWithDifferentHasKnownSolution));

		Cipher cipherWithDifferentCiphertextCharacters = new Cipher(baseName, baseRows, baseColumns);
		cipherWithDifferentCiphertextCharacters.setId(baseId);
		cipherWithDifferentCiphertextCharacters.setHasKnownSolution(baseHasKnownSolution);
		cipherWithDifferentCiphertextCharacters.addCiphertextCharacter(ciphertext3);
		cipherWithDifferentCiphertextCharacters.addCiphertextCharacter(ciphertext2);
		cipherWithDifferentCiphertextCharacters.addCiphertextCharacter(ciphertext1);
		assertFalse(base.equals(cipherWithDifferentColumns));

		Cipher cipherWithNullPropertiesA = new Cipher();
		Cipher cipherWithNullPropertiesB = new Cipher();
		assertEquals(cipherWithNullPropertiesA, cipherWithNullPropertiesB);
	}
}
