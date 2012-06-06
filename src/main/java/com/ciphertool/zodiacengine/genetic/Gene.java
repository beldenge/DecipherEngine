package com.ciphertool.zodiacengine.genetic;

public interface Gene extends Cloneable {

	/*
	 * Return the size of this Gene, measured as the number of sequences making
	 * up this Gene.
	 */
	public int size();

	public Gene clone();

	/*
	 * Mutate a random sequence
	 */
	public void mutateSequence();
}
