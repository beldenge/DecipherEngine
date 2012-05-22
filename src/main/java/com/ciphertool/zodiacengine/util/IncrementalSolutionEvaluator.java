package com.ciphertool.zodiacengine.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.Solution;

public class IncrementalSolutionEvaluator extends AbstractSolutionEvaluatorBase implements SolutionEvaluator {

	private static Logger log = Logger.getLogger(IncrementalSolutionEvaluator.class);
	HashMap<String, List<Ciphertext>> ciphertextKey;
	
	/**
	 * @param cipherName
	 * @param cipherDao
	 */
	public IncrementalSolutionEvaluator(String cipherName, CipherDao cipherDao) {
		cipher = cipherDao.findByCipherName(cipherName);
		ciphertextKey = createKeyFromCiphertext();
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.ciphertool.zodiacengine.util.SolutionEvaluator#determineConfidenceLevel(com.ciphertool.zodiacengine.entities.Solution)
	 */
	@Override
	public int determineConfidenceLevel(Solution solution) {
		Plaintext plaintext = null;
		int total = 0;
		int totalUnique = 0;
		int maxMatches = 0;
		String bestMatch = null;
		boolean uniqueMatch = false;
		String currentValue = null;
		List<Plaintext> plaintextCharacters = solution.getPlaintextCharacters();
		Map<String, List<Plaintext>> plaintextMatchMap;
		
		/*
		 * Iterate for each List of occurrences of the same Ciphertext
		 */
		for (List<Ciphertext> ciphertextIndices : ciphertextKey.values()) {
			maxMatches = 0;
			uniqueMatch = false;
			bestMatch = null;
			plaintextMatchMap = new HashMap<String, List<Plaintext>>();
			
			/*
			 * Now iterate for each occurrence of the current Ciphertext character
			 */
			for (Ciphertext ciphertextIndice : ciphertextIndices) {
				/*
				 *  This just returns the Plaintext character that corresponds to the given Ciphertext character.
				 *  The usage of List.get() assumes that the ArrayList is properly sorted by CiphertextId
				 *  
				 *  Only check this ciphertext if it is within the uncommitted index range. 
				 *  
				 *  We could also make this into a map with the ciphertextId as the key.  Then we would no 
				 *  longer have to worry about order and or subtracting one from the id.  It does come with a 
				 *  performance hit though.
				 */
				if (ciphertextIndice.getCiphertextId().getId() < solution.getUncommittedIndex()) {
					plaintext = plaintextCharacters.get(ciphertextIndice.getCiphertextId().getId()-1);
					
					currentValue = plaintext.getValue();
					
					if (!plaintextMatchMap.containsKey(currentValue)) {
						plaintextMatchMap.put(currentValue, new ArrayList<Plaintext>());
					}
					else {
						uniqueMatch = true;
					}
					
					plaintextMatchMap.get(currentValue).add(plaintext);
					
					if (plaintextMatchMap.get(currentValue).size() > maxMatches) {
						/*
						 * Subtract by one when setting maxMatches so that a match on just a pair does not count as two matches.
						 */
						maxMatches = plaintextMatchMap.get(currentValue).size() - 1;
						
						bestMatch = currentValue;
					}
				}
			}
			
			/*
			 * If there was a match on this Ciphertext, set the hasMatch property to true on all the Plaintext matches.
			 * Use the bestMatch value so that only the Plaintext with the optimal number of matches is set. 
			 */
			if (bestMatch != null) {
				for (Plaintext pt : plaintextMatchMap.get(bestMatch)) {
					pt.setHasMatch(true);
				}
			}
			
			/*
			 * Add the Plaintext matches on this Ciphertext character to the overall confidence value, represented by total
			 */
			total += maxMatches;
			
			/*
			 * Increment the unique matches by converting a boolean to an int
			 */
			totalUnique += (uniqueMatch ? 1 : 0);
		}
		solution.setTotalMatches(total);
		solution.setUniqueMatches(totalUnique);
		
		boolean countAdjacent = false;
		int adjacentMatchCount = 0;
		for (Ciphertext ct : cipher.getCiphertextCharacters()) {
			/*
			 * Only check this ciphertext if it is within the uncommitted index range. 
			 */
			if (ct.getCiphertextId().getId() < solution.getUncommittedIndex()) {
				if(countAdjacent == false && plaintextCharacters.get(ct.getCiphertextId().getId()-1).getHasMatch()) {
					countAdjacent = true;
				}
				else if (countAdjacent == true && plaintextCharacters.get(ct.getCiphertextId().getId()-1).getHasMatch()) {
					adjacentMatchCount ++;
				}
				else {
					countAdjacent = false;
				}
			}
		}
		
		solution.setAdjacentMatchCount(adjacentMatchCount);
		
		log.debug("Solution " + solution.getId() + " has a confidence level of: " + total);
		
		return total;
	}
	
	
	/**
	 * Compares a list of Plaintext characters to a Solution and adds or replaces the Plaintext if it results in a better Solution.
	 * 
	 * @param solution
	 * @param plaintextList
	 * @return
	 */
	public int comparePlaintextToSolution(Solution solution, List<Plaintext> plaintextList) {
		Plaintext plaintext = null;
		int total = 0;
		int totalUnique = 0;
		int maxMatches = 0;
		String bestMatch = null;
		boolean uniqueMatch = false;
		String currentValue = null;
		List<Plaintext> plaintextCharacters = solution.getPlaintextCharacters();
		Map<String, List<Plaintext>> plaintextMatchMap;
		int uncommittedIndex = (solution.getCommittedIndex() + plaintextList.size());
		
		/*
		 * Iterate for each List of occurrences of the same Ciphertext
		 */
		for (List<Ciphertext> ciphertextIndices : ciphertextKey.values()) {
			maxMatches = 0;
			uniqueMatch = false;
			bestMatch = null;
			plaintextMatchMap = new HashMap<String, List<Plaintext>>();
			
			/*
			 * Now iterate for each occurrence of the current Ciphertext character
			 */
			for (Ciphertext ciphertextIndice : ciphertextIndices) {
				/*
				 *  This just returns the Plaintext character that corresponds to the given Ciphertext character.
				 *  The usage of List.get() assumes that the ArrayList is properly sorted by CiphertextId
				 *  
				 *  Only check this ciphertext if it is within the uncommitted index range. 
				 *  
				 *  We could also make this into a map with the ciphertextId as the key.  Then we would no 
				 *  longer have to worry about order and or subtracting one from the id.  It does come with a 
				 *  performance hit though.
				 */
				if (ciphertextIndice.getCiphertextId().getId() <= uncommittedIndex) {
					/*
					 * Set the plaintext from the existing solution if the index is within the committed range
					 */
					if (ciphertextIndice.getCiphertextId().getId() <= solution.getCommittedIndex()) {
						plaintext = plaintextCharacters.get(ciphertextIndice.getCiphertextId().getId()-1);
					}
					/*
					 * Otherwise, set the plaintext from the new list to compare
					 */
					else {
						plaintext = plaintextList.get(ciphertextIndice.getCiphertextId().getId()-solution.getCommittedIndex()-1);
					}
					
					currentValue = plaintext.getValue();
					
					if (!plaintextMatchMap.containsKey(currentValue)) {
						plaintextMatchMap.put(currentValue, new ArrayList<Plaintext>());
					}
					else {
						uniqueMatch = true;
					}
					
					plaintextMatchMap.get(currentValue).add(plaintext);
					
					if (plaintextMatchMap.get(currentValue).size() > maxMatches) {
						/*
						 * Subtract by one when setting maxMatches so that a match on just a pair does not count as two matches.
						 */
						maxMatches = plaintextMatchMap.get(currentValue).size() - 1;
						
						bestMatch = currentValue;
					}
				}
			}
			
			/*
			 * If there was a match on this Ciphertext, set the hasMatch property to true on all the Plaintext matches.
			 * Use the bestMatch value so that only the Plaintext with the optimal number of matches is set. 
			 */
			if (bestMatch != null) {
				for (Plaintext pt : plaintextMatchMap.get(bestMatch)) {
					pt.setHasMatch(true);
				}
			}
			
			/*
			 * Add the Plaintext matches on this Ciphertext character to the overall confidence value, represented by total
			 */
			total += maxMatches;
			
			/*
			 * Increment the unique matches by converting a boolean to an int
			 */
			totalUnique += (uniqueMatch ? 1 : 0);
		}
		
		boolean countAdjacent = false;
		int adjacentMatchCount = 0;
		Plaintext pt;
		for (Ciphertext ct : cipher.getCiphertextCharacters()) {
			/*
			 * Only check this ciphertext if it is within the uncommitted index range. 
			 */
			if (ct.getCiphertextId().getId() <= uncommittedIndex) {
				/*
				 * Set the plaintext from the existing solution if the index is within the committed range
				 */
				if (ct.getCiphertextId().getId() <= solution.getCommittedIndex()) {
					pt = plaintextCharacters.get(ct.getCiphertextId().getId()-1);
				}
				/*
				 * Otherwise, set the plaintext from the new list to compare
				 */
				else {
					pt = plaintextList.get(ct.getCiphertextId().getId()-solution.getCommittedIndex()-1);
				}
				
				if(countAdjacent == false && pt.getHasMatch()) {
					countAdjacent = true;
				}
				else if (countAdjacent == true && pt.getHasMatch()) {
					adjacentMatchCount ++;
				}
				else {
					countAdjacent = false;
				}
			}
		}
		
		log.debug("Solution " + solution.getId() + " has a confidence level of: " + total);
		
		/*
		 * Longer sentences will naturally have more matches, so we need to select on sentences that have the
		 * least amount of mismatches instead.
		 * 
		 * TODO: Unfortunately, by taking this approach, shorter sentences will naturally have less mismatches and so we will tend towards shorter sentences on each increment.
		 */
		int placeholderMismatches = uncommittedIndex - (total + totalUnique);
		int currentMismatches = solution.getUncommittedIndex() - (solution.getTotalMatches() + solution.getUniqueMatches());
		
		/*
		 * If this sentence is a better match, set it as the next sentence at this index.
		 */
		if(placeholderMismatches < currentMismatches) {
			/*
			 * First remove all the Plaintext characters from the previous match
			 */
			for (int i = solution.getCommittedIndex(); i < solution.getUncommittedIndex(); i++) {
				// we always have to remove the last element in the list since the index decrements each time
				solution.getPlaintextCharacters().remove(solution.getPlaintextCharacters().size()-1);
			}
			/*
			 * Then add all the Plaintext characters from the new, better match
			 */
			for (int j = solution.getCommittedIndex(); j < uncommittedIndex; j++) {
				solution.getPlaintextCharacters().add(plaintextList.get(j-solution.getCommittedIndex()));
			}
			
			solution.setUncommittedIndex(uncommittedIndex);
			
			solution.setTotalMatches(total);
			solution.setUniqueMatches(totalUnique);
			solution.setAdjacentMatchCount(adjacentMatchCount);
		}
		
		return total;
	}
}
