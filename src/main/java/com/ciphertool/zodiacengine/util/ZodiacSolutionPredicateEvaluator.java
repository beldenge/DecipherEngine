package com.ciphertool.zodiacengine.util;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.Plaintext;
import com.ciphertool.zodiacengine.entities.Solution;
import com.ciphertool.zodiacengine.singleton.CipherSingleton;

public class ZodiacSolutionPredicateEvaluator extends AbstractSolutionEvaluatorBase implements
		SolutionEvaluator {

	private static Logger log = Logger.getLogger(ZodiacSolutionEvaluator.class);
	HashMap<String, List<Ciphertext>> ciphertextKey;

	/**
	 * @param cipherName
	 * @param cipherDao
	 */
	public ZodiacSolutionPredicateEvaluator(CipherSingleton cipherSingleton) {
		cipher = cipherSingleton.getInstance();
		ciphertextKey = createKeyFromCiphertext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ciphertool.zodiacengine.util.SolutionEvaluator#determineConfidenceLevel
	 * (com.ciphertool.zodiacengine.entities.Solution)
	 * 
	 * Calculates the confidence level as the number of instances that a
	 * ciphertext character has the same plaintext character mapped to it. If a
	 * ciphertext character has multiple matches, then select the plaintext
	 * character with the most matches (or if there's a tie, then flip a coin).
	 */
	@Override
	public int determineConfidenceLevel(Solution solution) {
		clearHasMatchValues(solution);

		Plaintext plaintext = null;
		Plaintext otherPlaintext = null;
		int total = 0;
		int totalUnique = 0;
		boolean uniqueMatch = false;
		/*
		 * Iterate for each List of occurrences of the same Ciphertext
		 */
		for (List<Ciphertext> ciphertextIndices : ciphertextKey.values()) {
			int maxMatches = 0;
			uniqueMatch = false;

			/*
			 * Now iterate for each occurrence of the current Ciphertext
			 * character
			 */
			for (Ciphertext ciphertextIndice : ciphertextIndices) {
				int matches = 0;

				/*
				 * The predicate used here just returns the Plaintext character
				 * that corresponds to the given Ciphertext character. Using the
				 * Predicate to find the Plaintext Id is about 10 times less
				 * performant than using List.get() on the CiphertextId
				 */
				plaintext = (Plaintext) CollectionUtils.find(solution.getPlaintextCharacters(),
						new PlaintextPredicate(ciphertextIndice));

				/*
				 * Iterate through the same list of Ciphertext characters,
				 * checking if the corresponding Plaintext character has any
				 * matches
				 */
				for (Ciphertext otherCiphertext : ciphertextIndices) {

					/*
					 * The predicate used here just returns the Plaintext
					 * character that corresponds to the given Ciphertext
					 * character. Using the Predicate to find the Plaintext Id
					 * is about 10 times less performant than using List.get()
					 * on the CiphertextId
					 */
					otherPlaintext = (Plaintext) CollectionUtils.find(solution
							.getPlaintextCharacters(), new PlaintextPredicate(otherCiphertext));

					/*
					 * Check if there are any Plaintext characters which are the
					 * same for this Ciphertext character. If so, then it is
					 * more likely that we have found the correct Plaintext
					 * character. Remember to ignore case here since proper
					 * nouns can have capital letters in the database
					 */
					if (!plaintext.equals(otherPlaintext)
							&& plaintext.getValue().equalsIgnoreCase(otherPlaintext.getValue())) {
						matches++;
						uniqueMatch = true;
					}
				}

				/*
				 * We want to use the most optimistic number of Plaintext
				 * character matches possible for the given Ciphertext character
				 */
				if (matches > maxMatches) {
					maxMatches = matches;
				}
			}

			/*
			 * Add the Plaintext matches on this Ciphertext character to the
			 * overall confidence value, represented by total
			 */
			total += maxMatches;

			/*
			 * Increment the unique matches by converting a boolean to an int
			 */
			totalUnique += (uniqueMatch ? 1 : 0);
		}
		solution.setTotalMatches(total);
		solution.setUniqueMatches(totalUnique);

		log.info("Solution " + solution.getSolutionId() + " has a confidence level of: " + total);

		return total;
	}
}