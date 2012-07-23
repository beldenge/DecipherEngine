package com.ciphertool.zodiacengine.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.util.SentenceHelper;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.singleton.CipherSingleton;

public class ZodiacSolutionGenerator implements SolutionGenerator {
	private SentenceHelper sentenceHelper;
	private Cipher cipher;
	private Logger log = Logger.getLogger(getClass());
	int cipherLength;

	public ZodiacSolutionGenerator(CipherSingleton cipherSingleton) {
		cipher = cipherSingleton.getInstance();
		cipherLength = cipher.length();
	}

	/**
	 * @return
	 * 
	 *         Generates a solution by calling the helper method getSentences()
	 *         and passing the result to
	 *         convertSentencesToPlaintext(List<Sentence>)
	 */
	@Override
	public Solution generateSolution() {
		// Set confidence levels to lowest possible
		Solution solution = new Solution(cipher.getId(), 0, 0, 0);

		/*
		 * TODO: May want to remove this setCipher since it should be lazy
		 * loaded somehow, but it doesn't cause any performance degradation.
		 */
		solution.setCipher(cipher);

		convertSentencesToPlaintext(solution, getSentences());

		log.debug(solution);

		return solution;
	}

	private List<Sentence> getSentences() {
		List<Sentence> sentenceList = new ArrayList<Sentence>();
		Sentence nextSentence;
		int length = 0;
		do {
			nextSentence = sentenceHelper.generateRandomSentence();
			length += nextSentence.length();
			sentenceList.add(nextSentence);
		} while (length < cipherLength);
		return sentenceList;
	}

	private void convertSentencesToPlaintext(Solution solution, List<Sentence> sentenceList) {
		StringBuilder rawText = new StringBuilder();
		for (Sentence sentence : sentenceList) {
			for (Word w : sentence.getWords()) {
				rawText.append(w.getWordId().getWord());
			}
		}
		char[] chars = new char[cipherLength];
		rawText.getChars(0, cipherLength, chars, 0);
		int id = 1;
		Plaintext pt;
		for (char c : chars) {
			/*
			 * It is very important to convert to lowercase here, since map
			 * lookups within the evaluator are case-sensitive.
			 */
			pt = new Plaintext(new PlaintextId(solution, id), String.valueOf(c).toLowerCase());
			solution.addPlaintext(pt);
			id++;
		}
	}

	/*
	 * This method just tests if it is any faster to call the getChars on each
	 * word individually rather than on the entire sentence list
	 * 
	 * From preliminary tests, it seems like this is about twice as fast as the
	 * old version when running single threaded, but when running multithreaded,
	 * it's about twice as slow...
	 * 
	 * This might be due to the fact that it is actually more CPU intensive but
	 * less memory intensive
	 */
	@SuppressWarnings("unused")
	private void convertSentencesToPlaintextLowMemory(Solution solution, List<Sentence> sentenceList) {
		char[] chars;
		Plaintext pt;
		int id = 1;
		StringBuilder rawText = new StringBuilder();
		for (Sentence sentence : sentenceList) {
			for (Word w : sentence.getWords()) {
				rawText.append(w.getWordId().getWord());
				chars = new char[rawText.length()];
				rawText.getChars(0, rawText.length(), chars, 0);
				for (char c : chars) {
					pt = new Plaintext(new PlaintextId(solution, id), String.valueOf(c));
					solution.addPlaintext(pt);
					id++;
				}
			}
		}
	}

	@Required
	public void setSentenceHelper(SentenceHelper sentenceHelper) {
		this.sentenceHelper = sentenceHelper;
	}
}
