package com.ciphertool.zodiacengine.genetic;

import java.util.List;

public interface Gene extends Cloneable {

	/*
	 * Return the size of this Gene, measured as the number of sequences making
	 * up this Gene.
	 */
	public int size();

	public Gene clone();

	public List<Sequence> getSequences();

	public void setSequences(List<Sequence> sequences);

	public void addSequence(Sequence sequence);

	public Sequence removeSequence(int index);

	/*
	 * Mutate a random sequence
	 */
	public void mutateRandomSequence();

	public void mutateSequence(int index);
}
