package com.ciphertool.zodiacengine.genetic;

import org.apache.log4j.Logger;

import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;

public class PlaintextSequence extends Plaintext implements Sequence {
	private static Logger log = Logger.getLogger(WordGene.class);
	private Gene gene;

	public PlaintextSequence() {
	}

	public PlaintextSequence(PlaintextId plaintextId, String value, Gene gene) {
		super(plaintextId, value);

		this.gene = gene;
	}

	@Override
	public Gene getGene() {
		return this.gene;
	}

	@Override
	public void setGene(Gene gene) {
		this.gene = gene;
	}

	@Override
	public PlaintextSequence clone() {
		PlaintextSequence copySequence = null;

		try {
			copySequence = (PlaintextSequence) super.clone();
		} catch (CloneNotSupportedException cnse) {
			log.error(
					"Caught CloneNoteSupportedException while attempting to clone PlaintextSequence.",
					cnse);
		}

		copySequence.setPlaintextId(new PlaintextId());

		copySequence.plaintextId.setCiphertextId(this.plaintextId.getCiphertextId());

		/*
		 * The Solution must be set at a higher level.
		 */
		copySequence.plaintextId.setSolution(null);

		/*
		 * The Gene must bet set at a higher level.
		 */
		copySequence.gene = null;

		return copySequence;
	}

	/**
	 * Shifts all the plaintext characters to the right the specified number of
	 * places.
	 * 
	 * @param places
	 */
	public void shiftRight(int places) {
		this.plaintextId.setCiphertextId(this.plaintextId.getCiphertextId() + places);
	}

	/**
	 * Shifts all the plaintext characters to the left the specified number of
	 * places.
	 * 
	 * @param places
	 */
	public void shiftLeft(int places) {
		this.plaintextId.setCiphertextId(this.plaintextId.getCiphertextId() - places);
	}
}
