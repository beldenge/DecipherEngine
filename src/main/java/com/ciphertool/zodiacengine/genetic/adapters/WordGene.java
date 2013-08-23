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

package com.ciphertool.zodiacengine.genetic.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.annotation.Transient;

import com.ciphertool.genetics.annotations.Dirty;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.genetics.entities.Sequence;
import com.ciphertool.sentencebuilder.entities.Word;

public class WordGene implements Gene {
	private static Logger log = Logger.getLogger(WordGene.class);

	@Transient
	private Chromosome chromosome;

	private List<Sequence> sequences = new ArrayList<Sequence>();

	public WordGene() {
	}

	/**
	 * @param wordId
	 */
	public WordGene(Word word, SolutionChromosome solutionChromosome, int beginCiphertextId) {
		this.chromosome = solutionChromosome;

		int wordLength = word.getId().getWord().length();

		for (int i = 0; i < wordLength; i++) {
			PlaintextSequence plaintextSequence = new PlaintextSequence(beginCiphertextId + i,
					String.valueOf(word.getId().getWord().charAt(i)).toLowerCase(), this);

			this.sequences.add(plaintextSequence);
		}
	}

	@Override
	public WordGene clone() {
		WordGene copyGene = null;

		try {
			copyGene = (WordGene) super.clone();
		} catch (CloneNotSupportedException cnse) {
			log.error("Caught CloneNotSupportedException while attempting to clone WordGene.", cnse);
		}

		/*
		 * The Chromosome should be set at a higher level, so we just set it to
		 * a dummy Chromosome which should be overwritten.
		 */
		copyGene.chromosome = new SolutionChromosome();

		Sequence clonedSequence = null;

		copyGene.resetSequences();

		for (Sequence sequenceToClone : this.sequences) {
			clonedSequence = sequenceToClone.clone();

			copyGene.addSequence(clonedSequence);

			/*
			 * We do NOT want to set the SolutionChromosome for the
			 * PlaintextSequence here, since the only SolutionChromosome we have
			 * access to here is the one referenced by the WordGene being
			 * cloned. It should be set at a higher level.
			 */

			clonedSequence.setGene(copyGene);
		}

		return copyGene;
	}

	@Override
	public int size() {
		return this.sequences.size();
	}

	@Override
	public Chromosome getChromosome() {
		return this.chromosome;
	}

	@Override
	public void setChromosome(Chromosome chromosome) {
		this.chromosome = chromosome;
	}

	@Override
	public List<Sequence> getSequences() {
		return Collections.unmodifiableList(this.sequences);
	}

	/*
	 * This should only be called from cloning methods. Otherwise, resetting the
	 * sequences of a Gene should also remove those sequences from the
	 * Chromosome.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.genetics.entities.Gene#resetSequences()
	 */
	@Override
	@Dirty
	public void resetSequences() {
		this.sequences = new ArrayList<Sequence>();
	}

	@Override
	@Dirty
	public void addSequence(Sequence sequence) {
		this.sequences.add(sequence);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Gene#insertSequence(int,
	 * com.ciphertool.zodiacengine.genetic.Sequence)
	 * 
	 * It is necessary to shift the ciphertextIds at a higher level so that they
	 * remain in order in the SolutionChromosome.
	 * 
	 * TODO: I think it makes more sense to manage the Sequence-Chromosome
	 * relationship at the Chromosome level, so that Sequences within a Gene can
	 * be managed independently of a Chromosome until they are actually added to
	 * the Chromosome.
	 */
	@Override
	@Dirty
	public void insertSequence(int index, Sequence sequence) {
		if (sequence == null) {
			log.warn("Attempted to insert a Sequence into WordGene, but the supplied Sequence was null.  Cannot continue. "
					+ this);

			return;
		}

		this.sequences.add(index, sequence);

		/*
		 * We additionally have to shift the ciphertextIds since the current
		 * ciphertextIds will no longer be accurate.
		 */
		List<PlaintextSequence> plaintextCharacters = ((SolutionChromosome) this.chromosome)
				.getPlaintextCharacters();

		int chromosomeSize = plaintextCharacters.size();
		for (int i = sequence.getSequenceId() + 1; i < chromosomeSize; i++) {
			plaintextCharacters.get(i).shiftRight(1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Gene#removeSequence(int)
	 * 
	 * It is necessary to shift the ciphertextIds at a higher level so that they
	 * remain in order in the SolutionChromosome.
	 * 
	 * TODO: I think it makes more sense to manage the Sequence-Chromosome
	 * relationship at the Chromosome level, so that Sequences within a Gene can
	 * be managed independently of a Chromosome until they are actually added to
	 * the Chromosome.
	 */
	@Override
	@Dirty
	public void removeSequence(Sequence sequence) {
		if (sequence == null) {
			log.warn("Attempted to remove a Sequence from WordGene, but the supplied Sequence was null.  Cannot continue. "
					+ this);

			return;
		}

		this.sequences.remove(sequence);

		/*
		 * We additionally have to shift the ciphertextIds since the current
		 * ciphertextIds will no longer be accurate.
		 */
		List<PlaintextSequence> plaintextCharacters = ((SolutionChromosome) this.chromosome)
				.getPlaintextCharacters();

		int chromosomeSize = plaintextCharacters.size();
		for (int i = sequence.getSequenceId(); i < chromosomeSize; i++) {
			plaintextCharacters.get(i).shiftLeft(1);
		}
	}

	/*
	 * This should just be a combination of the remove and insert methods.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Gene#replaceSequence(int,
	 * com.ciphertool.zodiacengine.genetic.Sequence)
	 */
	@Override
	public void replaceSequence(int index, Sequence newSequence) {
		this.removeSequence(this.sequences.get(index));

		this.insertSequence(index, newSequence);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chromosome == null) ? 0 : chromosome.hashCode());
		result = prime * result + ((sequences == null) ? 0 : sequences.hashCode());
		return result;
	}

	/*
	 * We don't check the Chromosome here since it should be set at a higher
	 * level.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		WordGene other = (WordGene) obj;

		if (sequences == null) {
			if (other.sequences != null) {
				return false;
			}
		} else if (!sequences.equals(other.sequences)) {
			return false;
		}

		return true;
	}

	public String getWordString() {
		StringBuilder sb = new StringBuilder();

		for (Sequence sequence : this.getSequences()) {
			sb.append(((PlaintextSequence) sequence).getValue());
		}

		return sb.toString();
	}

	public int countMatches() {
		int count = 0;

		for (Sequence sequenceToCheck : this.sequences) {
			if (((PlaintextSequence) sequenceToCheck).getHasMatch()) {
				count++;
			}
		}

		return count;
	}

	@Override
	public String toString() {
		return "WordGene [sequences=" + sequences + "]";
	}
}
