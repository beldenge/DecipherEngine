/**
 * Copyright 2012 George Belden
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

package com.ciphertool.zodiacengine.genetic.iterators;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.entities.Sequence;
import com.ciphertool.zodiacengine.genetic.adapters.PlaintextSequence;

public class PlaintextIterator implements Iterator<PlaintextSequence> {

	private Iterator<Gene> geneIterator;
	private Iterator<Sequence> nextSequenceIterator;

	public PlaintextIterator(List<Gene> genes) {
		if (genes != null) {
			this.geneIterator = genes.iterator();

			if (this.geneIterator.hasNext()) {
				this.nextSequenceIterator = this.geneIterator.next().getSequences().iterator();
			}
		}
	}

	@Override
	public boolean hasNext() {
		if (this.nextSequenceIterator.hasNext()) {
			return true;
		} else if (this.geneIterator.hasNext()) {
			this.nextSequenceIterator = this.geneIterator.next().getSequences().iterator();

			return this.nextSequenceIterator.hasNext();
		}

		return false;
	}

	@Override
	public PlaintextSequence next() {
		if (this.geneIterator == null || this.nextSequenceIterator == null) {
			throw new NoSuchElementException(
					"PlaintextIterator does not have a next PlaintextSequence.");
		}

		if (this.nextSequenceIterator.hasNext()) {
			return (PlaintextSequence) this.nextSequenceIterator.next();
		} else if (this.geneIterator.hasNext()) {
			this.nextSequenceIterator = this.geneIterator.next().getSequences().iterator();

			return (PlaintextSequence) this.nextSequenceIterator.next();
		}

		throw new NoSuchElementException(
				"PlaintextIterator does not have a next PlaintextSequence.");
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(
				"PlaintextIterator does not support the remove() operation.");
	}
}