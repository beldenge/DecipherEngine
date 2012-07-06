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

		copySequence.plaintextId.setCiphertextId(this.plaintextId.getCiphertextId());

		/*
		 * The Solution must be set at a higher level
		 */
		copySequence.plaintextId.setSolution(null);

		return copySequence;
	}
}
