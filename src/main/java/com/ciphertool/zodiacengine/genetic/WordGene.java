package com.ciphertool.zodiacengine.genetic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.util.LetterUtils;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.entities.Solution;

public class WordGene implements Gene {
	private static Logger log = Logger.getLogger(WordGene.class);
	private List<Sequence> sequences;

	/**
	 * @param wordId
	 */
	public WordGene(Word word, Solution solution, int beginCiphertextIndex) {
		this.sequences = new ArrayList<Sequence>();

		int wordLength = word.getWordId().getWord().length();

		for (int i = 0; i < wordLength; i++) {
			PlaintextSequence plaintextSequence = new PlaintextSequence(new PlaintextId(solution,
					beginCiphertextIndex + i), String.valueOf(word.getWordId().getWord().charAt(i))
					.toLowerCase(), this);

			solution.addPlaintext(plaintextSequence);

			this.sequences.add(plaintextSequence);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public WordGene clone() {
		WordGene copyGene = null;

		try {
			copyGene = (WordGene) super.clone();
		} catch (CloneNotSupportedException cnse) {
			log.error("Caught CloneNoteSupportedException while attempting to clone WordGene.",
					cnse);
		}

		List<Sequence> sequencesToClone = new ArrayList<Sequence>();

		for (Sequence sequenceToClone : this.sequences) {
			sequencesToClone.add(((Sequence) sequenceToClone).clone());
		}

		copyGene.setSequences(sequencesToClone);

		return copyGene;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Gene#size()
	 */
	@Override
	public int size() {
		return this.sequences.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Gene#mutateRandomSequence()
	 */
	@Override
	public void mutateRandomSequence() {
		int randomIndex = (int) (Math.random() * this.size());

		mutateSequence(randomIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Gene#mutateSequence(int)
	 */
	@Override
	public void mutateSequence(int index) {
		if (index > this.size() - 1) {
			log.info("Attempted to mutate a sequence in WordGene with index of " + index
					+ " (zero-indexed), but the size is only " + this.size()
					+ ".  Cannot continue.");

			return;
		}

		String newLetter;

		/*
		 * Loop just in case the random letter is the same as the existing
		 * letter, since that would defeat the purpose of the mutation.
		 */
		do {
			newLetter = String.valueOf(LetterUtils.getRandomLetter());
		} while (((PlaintextSequence) this.sequences.get(index)).getValue().equals(newLetter));

		((PlaintextSequence) this.sequences.get(index)).setValue(newLetter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WordGene [sequences=" + sequences + "]";
	}

	@Override
	public List<Sequence> getSequences() {
		return this.sequences;
	}

	@Override
	public void setSequences(List<Sequence> sequences) {
		this.sequences = sequences;
	}

	@Override
	public void addSequence(Sequence sequence) {
		this.sequences.add(sequence);
	}

	@Override
	public Sequence removeSequence(int index) {
		return this.sequences.remove(index);
	}
}
