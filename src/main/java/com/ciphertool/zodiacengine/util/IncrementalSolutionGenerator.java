package com.ciphertool.zodiacengine.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.sentencebuilder.beans.Sentence;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.util.SentenceHelper;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.entities.Solution;

public class IncrementalSolutionGenerator extends AbstractSolutionEvaluatorBase implements SolutionGenerator {
	private SentenceHelper sentenceHelper;
	private Logger log = Logger.getLogger(getClass());
	private int cipherLength;
	private long improvementAttempts;
	private SolutionEvaluator solutionEvaluator;
	
	/**
	 * @param cipherName
	 * @param cipherDao
	 */
	public IncrementalSolutionGenerator(String cipherName, CipherDao cipherDao) {
		cipher = cipherDao.findByCipherName(cipherName);
		cipherLength = cipher.length();
	}
	
	/**
	 * @return
	 * 
	 * Generates a solution by calling the helper method getSentences() and 
	 * passing the result to convertSentencesToPlaintext(List<Sentence>)
	 */
	@Override
	public Solution generateSolution() {
		// Set confidence levels to lowest possible
		Solution solution = new Solution(cipher.getId(), 0, 0, 0);
		
		/*
		 *  TODO: May want to remove this setCipher since it should be lazy loaded somehow, but it doesn't cause any performance degradation.
		 */
		solution.setCipher(cipher);
		
		solution.setCommittedIndex(0);
		solution.setUncommittedIndex(0);
		
		do  {
			appendNextBestSentence(solution);
			
			/*
			 * Advance the committed index
			 */
			solution.setCommittedIndex(solution.getUncommittedIndex());
		} while (solution.getCommittedIndex() < cipherLength);
		
		log.debug(solution);
		
		return solution;
	}
	
	/**
	 * Select the best possible sentence starting at the current index, and 
	 * then advance the index. 
	 * 
	 * @param solution
	 */
	private void appendNextBestSentence(Solution solution) {
		Sentence nextSentence = null;
		
		for (long i = 0; i < improvementAttempts; i ++) {
			nextSentence = sentenceHelper.generateRandomSentence();
			
			compareSentenceToSolution(solution, nextSentence);
		}
	}

	/**
	 * Creates a new solution with the sentence supplied and then compares it to the current
	 * solution.  Returns the solution with the greater confidence level.
	 * 
	 * @param solution
	 * @param sentence
	 * @return
	 */
	private void compareSentenceToSolution(Solution solution, Sentence sentence) {
		Solution placeholderSolution = new Solution(cipher.getId(), 0, 0, 0);
		placeholderSolution.setCipher(cipher);
		
		/*
		 * Clone all the plaintext characters into the placeholder solution
		 */
		Plaintext clonedPlaintext = null;
		for (Plaintext currentPlaintext : solution.getPlaintextCharacters()) {
			clonedPlaintext = new Plaintext(new PlaintextId(placeholderSolution, currentPlaintext.getPlaintextId().getCiphertextId()), currentPlaintext.getValue());
			placeholderSolution.addPlaintext(clonedPlaintext);
		}
				
		StringBuilder rawText = new StringBuilder();
		
		for (Word w: sentence.getWords()) {
			rawText.append(w.getWordId().getWord());
		}
		
		int sentenceLength = sentence.length();
		
		char [] chars = new char [sentenceLength];
		
		rawText.getChars(0, sentenceLength, chars, 0);
		
		int newIndex = solution.getCommittedIndex();
		
		Plaintext pt = null;
		for (char c : chars) {
			/*
			 * Don't add the plaintext character if the index has surpassed the cipher length.  It's pointless.
			 */
			if(newIndex <= cipherLength) {
				pt = new Plaintext(new PlaintextId(placeholderSolution, newIndex), String.valueOf(c));
				
				placeholderSolution.addPlaintext(pt);
	
				newIndex ++;
			}
		}
		
		placeholderSolution.setUncommittedIndex(newIndex);
		
		solutionEvaluator.determineConfidenceLevel(placeholderSolution);
		
		/*
		 * Longer sentences will naturally have more matches, so we need to select on sentences that have the
		 * least amount of mismatches instead.
		 */
		int placeholderMismatches = placeholderSolution.getUncommittedIndex() - (placeholderSolution.getTotalMatches() + placeholderSolution.getUniqueMatches());
		int currentMismatches = solution.getUncommittedIndex() - (solution.getTotalMatches() + solution.getUniqueMatches());
		
		/*
		 * If this is the first sentence attempted at this index, or if this sentence is a better match, 
		 * set it as the next sentence at this index.
		 */
		if((solution.getCommittedIndex() == solution.getUncommittedIndex())
				|| (placeholderMismatches < currentMismatches)) {
			solution.setPlaintextCharacters(placeholderSolution.getPlaintextCharacters());
			
			for (Plaintext plaintext : solution.getPlaintextCharacters()) {
				plaintext.getPlaintextId().setSolution(solution);
			}
			
			solution.setUncommittedIndex(newIndex);
		}
	}
	
	/**
	 * @param sentenceHelper
	 */
	@Required
	public void setSentenceHelper(SentenceHelper sentenceHelper) {
		this.sentenceHelper = sentenceHelper;
	}

	/**
	 * @param improvementAttempts the improvementAttempts to set
	 */
	@Required
	public void setImprovementAttempts(long improvementAttempts) {
		this.improvementAttempts = improvementAttempts;
	}

	/**
	 * @param solutionEvaluator the solutionEvaluator to set
	 */
	@Required
	public void setSolutionEvaluator(SolutionEvaluator solutionEvaluator) {
		this.solutionEvaluator = solutionEvaluator;
	}
}
