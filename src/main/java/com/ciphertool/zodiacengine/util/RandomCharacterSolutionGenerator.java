package com.ciphertool.zodiacengine.util;

import org.apache.log4j.Logger;

import com.ciphertool.sentencebuilder.util.LetterUtils;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.entities.Solution;

public class RandomCharacterSolutionGenerator implements SolutionGenerator {
	@SuppressWarnings("unused")
	private String cipherName;
	@SuppressWarnings("unused")
	private CipherDao cipherDao;
	private Cipher cipher;
	private Logger log = Logger.getLogger(getClass());
	
	public RandomCharacterSolutionGenerator(String cipherName, CipherDao cipherDao) {
		this.cipherName = cipherName;
		this.cipherDao = cipherDao;
		cipher = cipherDao.findByCipherName(cipherName);
	}
	
	/**
	 * Generates a solution by calling the helper method getSentences() and 
	 * passing the result to convertSentencesToPlaintext(List<Sentence>)
	 */
	@Override
	public Solution generateSolution() {
		// Set confidence levels to lowest possible
		Solution solution = new Solution(cipher.getId(), 0, 0);
		
		// TODO: may want to remove this setCipher since it should be lazy loaded somehow
		solution.setCipher(cipher);
		
		getCharacters(solution);
		
		log.debug(solution);
		
		return solution;
	}
	
	public void getCharacters(Solution solution) {
		Plaintext nextPlaintext;
		int id = 0;
		char randomChar;
		
		do  {
			id ++;
			
			randomChar = LetterUtils.getRandomLetter();
			
			nextPlaintext = new Plaintext(new PlaintextId(solution, id), String.valueOf(randomChar));
			
			solution.addPlaintext(nextPlaintext);
		} while (id < cipher.length());
	}
}