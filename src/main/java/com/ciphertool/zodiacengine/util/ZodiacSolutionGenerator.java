package com.ciphertool.zodiacengine.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.util.SentenceHelper;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.entities.Solution;

public class ZodiacSolutionGenerator implements SolutionGenerator {
	private SentenceHelper sentenceHelper;
	private Cipher cipher;
	private Logger log = Logger.getLogger(getClass());
	int cipherLength;
	
	public ZodiacSolutionGenerator(String cipherName, CipherDao cipherDao) {
		cipher = cipherDao.findByCipherName(cipherName);
		cipherLength = cipher.length();
	}
	
	/**
	 * @return
	 * 
	 * Generates a solution by calling the helper method getSentences() and 
	 * passing the result to convertSentencesToPlaintext(List<Sentence>)
	 * @throws JAXBException 
	 */
	@Override
	public Solution generateSolution() {	
		// Set confidence levels to lowest possible
		Solution solution = new Solution(cipher.getId(), 0, 0);
		
		// TODO: may want to remove this setCipher since it should be lazy loaded somehow
		solution.setCipher(cipher);
		
		List<Sentence> sentences = getSentences();
		
		convertSentencesToPlaintext(solution, sentences);
		
		log.debug(solution);
		
		return solution;
	}
	
	public List<Sentence> getSentences() {
		List<Sentence> sentenceList = new ArrayList<Sentence>();
		Sentence nextSentence;
		int length = 0;
		do  {
			nextSentence = sentenceHelper.generateRandomSentence();
			length += nextSentence.length();
			sentenceList.add(nextSentence);
		} while (length < cipherLength);
		return sentenceList;
	}
	
	public void convertSentencesToPlaintext(Solution solution, List<Sentence> sentenceList) {
		String rawText = "";
		for (Sentence sentence : sentenceList) {
			for (Word w: sentence.getWords()) {
				rawText += w.getWordId().getWord();
			}
		}
		char [] chars = new char [cipherLength];
		rawText.getChars(0, cipherLength, chars, 0);
		int id = 1;
		Plaintext pt;
		for (char c : chars) {
			pt = new Plaintext(new PlaintextId(solution, id), String.valueOf(c));
			solution.addPlaintext(pt);
			id ++;
		}
	}

	@Autowired
	public void setSentenceHelper(SentenceHelper sentenceHelper) {
		this.sentenceHelper = sentenceHelper;
	}
}
