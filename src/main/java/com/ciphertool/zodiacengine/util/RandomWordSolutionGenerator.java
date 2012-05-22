package com.ciphertool.zodiacengine.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ciphertool.sentencebuilder.dao.WordListDao;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.entities.Solution;

public class RandomWordSolutionGenerator implements SolutionGenerator {
	private Cipher cipher;
	private WordListDao wordListDao;
	private Logger log = Logger.getLogger(getClass());

	public RandomWordSolutionGenerator(String cipherName, CipherDao cipherDao) {
		cipher = cipherDao.findByCipherName(cipherName);
	}

	@Override
	public Solution generateSolution() {
		// Set confidence levels to lowest possible
		Solution solution = new Solution(cipher.getId(), 0, 0, 0);

		// TODO: may want to remove this setCipher since it should be lazy
		// loaded somehow
		solution.setCipher(cipher);

		List<Word> words = getWords(solution);

		convertWordsToPlaintext(solution, words);

		log.debug(solution);

		return solution;
	}

	private List<Word> getWords(Solution solution) {
		List<Word> wordList = new ArrayList<Word>();
		Word nextWord;
		int length = 0;
		do {
			nextWord = wordListDao.findRandomWord();
			length += nextWord.getWordId().getWord().length();
			wordList.add(nextWord);
		} while (length < cipher.length());
		return wordList;
	}

	public void convertWordsToPlaintext(Solution solution, List<Word> wordList) {
		StringBuffer rawText = new StringBuffer();
		for (Word w : wordList) {
			rawText.append(w.getWordId().getWord());
		}
		char[] chars = new char[cipher.length()];
		rawText.getChars(0, cipher.length(), chars, 0);
		int id = 1;
		Plaintext pt;
		for (char c : chars) {
			pt = new Plaintext(new PlaintextId(solution, id), String.valueOf(c));
			solution.addPlaintext(pt);
			id++;
		}
	}

	/**
	 * @param wordListDao
	 *            the wordListDao to set
	 */
	@Autowired
	public void setWordListDao(WordListDao wordListDao) {
		this.wordListDao = wordListDao;
	}

}