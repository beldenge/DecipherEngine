package com.ciphertool.zodiacengine.genetic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.dto.CipherDto;
import com.ciphertool.zodiacengine.entities.Solution;

public class GeneticCipherSolutionEngine {
	private static Logger log = Logger.getLogger(GeneticCipherSolutionEngine.class);
	private static ApplicationContext context;

	private static CipherDao cipherDao;
	private static String cipherName;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// Spin up the Spring application context
		setUp();

		int cipherId = cipherDao.findByCipherName(cipherName).getId();

		long start = System.currentTimeMillis();

		List<CipherDto> cipherDtos = new ArrayList<CipherDto>();

		long totalSolutions = 0;
		long totalMatchSum = 0;
		long uniqueMatchSum = 0;
		long adjacentMatchSum = 0;

		Solution solutionMostMatches = new Solution(cipherId, 0, 0, 0);
		Solution solutionMostUnique = new Solution(cipherId, 0, 0, 0);
		Solution solutionMostAdjacent = new Solution(cipherId, 0, 0, 0);

		/*
		 * Sum up all data from all CipherDtos passed to the threads
		 */
		for (CipherDto nextCipherDto : cipherDtos) {
			log.debug("Best solution from thread " + nextCipherDto.getThreadName() + ": "
					+ nextCipherDto.getSolutionMostMatches());
			log.debug("Most unique solution from thread " + nextCipherDto.getThreadName() + ": "
					+ nextCipherDto.getSolutionMostUnique());
			log.debug("Solution with most adjacent matches from thread "
					+ nextCipherDto.getThreadName() + ": "
					+ nextCipherDto.getSolutionMostAdjacent());

			totalSolutions += nextCipherDto.getNumSolutions();
			totalMatchSum += nextCipherDto.getTotalMatchSum();
			uniqueMatchSum += nextCipherDto.getUniqueMatchSum();
			adjacentMatchSum += nextCipherDto.getAdjacentMatchSum();

			/*
			 * Find the Solution with the highest number of total matches
			 */
			if (nextCipherDto.getSolutionMostMatches().getTotalMatches() > solutionMostMatches
					.getTotalMatches()) {
				solutionMostMatches = nextCipherDto.getSolutionMostMatches();
			}

			/*
			 * Find the Solution with the highest number of unique matches in
			 * plaintext
			 */
			if (nextCipherDto.getSolutionMostUnique().getUniqueMatches() > solutionMostUnique
					.getUniqueMatches()) {
				solutionMostUnique = nextCipherDto.getSolutionMostUnique();
			}

			/*
			 * Find the Solution with the highest number of adjacent matches in
			 * plaintext
			 */
			if (nextCipherDto.getSolutionMostAdjacent().getAdjacentMatchCount() > solutionMostAdjacent
					.getAdjacentMatchCount()) {
				solutionMostAdjacent = nextCipherDto.getSolutionMostAdjacent();
			}
		}

		/*
		 * Print out summary information
		 */
		log.info("Took " + (System.currentTimeMillis() - start) + "ms to generate and validate "
				+ totalSolutions + " solutions.");
		log.info("Most total matches achieved: " + solutionMostMatches.getTotalMatches());
		log.info("Average total matches: " + (totalMatchSum / totalSolutions));
		log.info("Best solution found: " + solutionMostMatches);
		log.info("Most unique matches achieved: " + solutionMostUnique.getUniqueMatches());
		log.info("Average unique matches: " + (uniqueMatchSum / totalSolutions));
		log.info("Solution with most unique matches found: " + solutionMostUnique);
		log.info("Most adjacent matches achieved: " + solutionMostAdjacent.getAdjacentMatchCount());
		log.info("Average adjacent matches: " + (adjacentMatchSum / totalSolutions));
		log.info("Solution with most adjacent matches found: " + solutionMostAdjacent);
	}

	/**
	 * Spins up the Spring application context
	 */
	private static void setUp() {
		context = new ClassPathXmlApplicationContext("beans-zodiac.xml");

		log.info("Spring context created successfully!");
	}

	/**
	 * @param cipherDao
	 *            the cipherDao to set
	 */
	@Required
	public void setCipherDao(CipherDao cipherDao) {
		GeneticCipherSolutionEngine.cipherDao = cipherDao;
	}

	/**
	 * @param cipherName
	 *            the cipherName to set
	 */
	@Required
	public void setCipherName(String cipherName) {
		GeneticCipherSolutionEngine.cipherName = cipherName;
	}
}
