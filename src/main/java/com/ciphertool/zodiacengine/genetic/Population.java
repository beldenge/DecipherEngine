package com.ciphertool.zodiacengine.genetic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.zodiacengine.util.SolutionGenerator;

public class Population<T extends Gene> {
	private SolutionGenerator solutionGenerator;
	private List<Chromosome> individuals;
	private FitnessEvaluator fitnessEvaluator;

	public void populateIndividuals(Integer numIndividuals) {
		if (this.individuals == null) {
			this.individuals = new ArrayList<Chromosome>();
		}

		for (int i = this.individuals.size(); i < numIndividuals; i++) {
			this.individuals.add((Chromosome) solutionGenerator.generateSolution());
		}
	}

	public long evaluateFitness() {
		long totalFitness = 0;

		for (Chromosome individual : individuals) {
			totalFitness += fitnessEvaluator.evaluate(individual);
		}

		return totalFitness;
	}

	public Chromosome getBestFitIndividual() {
		/*
		 * Evaluate fitness once more for safety so that we are guaranteed to
		 * have updated fitness values
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

	public Chromosome spinRouletteWheel() {
		long totalFitness = this.evaluateFitness();

		long randomIndex = (int) (Math.random() * totalFitness);

		for (Chromosome individual : individuals) {
			randomIndex -= fitnessEvaluator.evaluate(individual);

			/*
			 * If we have subtracted everything from randomIndex, then the ball
			 * has stopped on the wheel.
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
	}

	/**
	 * @param individual
	 */
	public void addIndividual(Chromosome individual) {
		this.individuals.add(individual);
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
