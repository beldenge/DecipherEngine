package com.ciphertool.zodiacengine.genetic;

import org.apache.log4j.Logger;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.util.LetterUtils;

public class WordGene extends Word implements Gene {
	private static Logger log = Logger.getLogger(WordGene.class);

	/**
	 * @param wordId
	 */
	public WordGene(WordId wordId) {
		super(wordId);
	}

	/**
	 * @param wordId
	 * @param frequencyWeight
	 */
	public WordGene(WordId wordId, int frequencyWeight) {
		super(wordId, frequencyWeight);
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

		copyGene.wordId = (WordId) this.wordId.clone();

		return copyGene;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Gene#size()
	 */
	@Override
	public int size() {
		return this.wordId.getWord().length();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ciphertool.zodiacengine.genetic.Gene#mutateSequence()
	 */
	@Override
	public void mutateSequence() {
		int randomIndex = (int) (Math.random() * this.size());

		/*
		 * Replace the sequence at randomIndex with a random letter
		 */
		String newWord = this.wordId.getWord().substring(0, randomIndex);

		newWord += LetterUtils.getRandomLetter();

		newWord += this.wordId.getWord().substring(randomIndex + 1, this.size());

		this.wordId.setWord(newWord);
	}
}
