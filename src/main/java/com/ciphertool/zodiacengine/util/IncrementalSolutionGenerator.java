package com.ciphertool.zodiacengine.util;

import java.util.ArrayList;
import java.util.List;

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
	 * Generates a solution
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
			
			log.info("New index of " + solution.getCommittedIndex() + " achieved.");
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
		List<Plaintext> candidatePlaintextList = new ArrayList<Plaintext>(); 
		
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
				pt = new Plaintext(new PlaintextId(solution, newIndex), String.valueOf(c));
				
				candidatePlaintextList.add(pt);
	
				newIndex ++;
			}
		}
		
		/*
		 * If the indexes are equal, then this is the first sentence tried at this index, so go ahead and add it.
		 */
		if (solution.getCommittedIndex() == solution.getUncommittedIndex())
		{
			solution.getPlaintextCharacters().addAll(candidatePlaintextList);			
			
			solution.setUncommittedIndex(newIndex);
			
			solutionEvaluator.determineConfidenceLevel(solution);
		}
		/*
		 * Otherwise, compare it to what already exists and see if there is a better match.
		 */
		else
		{
			((IncrementalSolutionEvaluator) solutionEvaluator).comparePlaintextToSolution(solution, candidatePlaintextList);	
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
