package com.ciphertool.zodiacengine.genetic;

import org.apache.log4j.Logger;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;

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

	@Override
	public int size() {
		return this.wordId.getWord().length();
	}
}
