package com.ciphertool.zodiacengine.genetic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.zodiacengine.util.SolutionGenerator;

public class Population {
	private Logger log = Logger.getLogger(getClass());
	private SolutionGenerator solutionGenerator;
	private List<Chromosome> individuals;
	private FitnessEvaluator fitnessEvaluator;
	private long totalFitness;

	public Population() {
	}

	public void populateIndividuals(Integer numIndividuals) {
		if (this.individuals == null) {
			this.individuals = new ArrayList<Chromosome>();
		}

		int individualsAdded = 0;

		for (int i = this.individuals.size(); i < numIndividuals; i++) {
			this.individuals.add((Chromosome) solutionGenerator.generateSolution());

			individualsAdded++;
		}

		log.info("Added " + individualsAdded + " individuals to the population.");
	}

	public long evaluateFitness() {
		long totalFitness = 0;

		for (Chromosome individual : individuals) {
			totalFitness += fitnessEvaluator.evaluate(individual);
		}

		return totalFitness;
	}

	/**
	 * This method should only really be called at the end of a genetic
	 * algorithm.
	 * 
	 * @return
	 */
	public Chromosome getBestFitIndividual() {
		/*
		 * Evaluate fitness once more for safety so that we are guaranteed to
		 * have updated fitness values.
		 */
		this.evaluateFitness();

		Chromosome bestFitIndividual = null;

		for (Chromosome individual : individuals) {
			if (bestFitIndividual == null
					|| individual.getFitness() > bestFitIndividual.getFitness()) {
				bestFitIndividual = individual;
			}
		}

		return bestFitIndividual;
	}

	/*
	 * This method depends on the totalFitness and individuals' fitness being
	 * accurately maintained.
	 */
	public Chromosome spinRouletteWheel() {
		long randomIndex = (int) (Math.random() * totalFitness);

		for (Chromosome individual : individuals) {
			randomIndex -= individual.getFitness();

			/*
			 * If we have subtracted everything from randomIndex, then the ball
			 * has stopped rolling.
			 */
			if (randomIndex <= 0) {
				break;
			}
		}

		return null;
	}

	/**
	 * @param individual
	 */
	public void removeIndividual(Chromosome individual) {
		this.individuals.remove(individual);

		this.totalFitness -= individual.getFitness();
	}

	/**
	 * @param individual
	 */
	public void addIndividual(Chromosome individual) {
		this.individuals.add(individual);

		fitnessEvaluator.evaluate(individual);

		this.totalFitness += individual.getFitness();
	}

	public int size() {
		return this.individuals.size();
	}

	/**
	 * @param solutionGenerator
	 *            the solutionGenerator to set
	 */
	@Required
	public void setSolutionGenerator(SolutionGenerator solutionGenerator) {
		this.solutionGenerator = solutionGenerator;
	}

	/**
	 * @param fitnessEvaluator
	 *            the fitnessEvaluator to set
	 */
	@Required
	public void setFitnessEvaluator(FitnessEvaluator fitnessEvaluator) {
		this.fitnessEvaluator = fitnessEvaluator;
	}
}
