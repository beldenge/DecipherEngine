/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DecipherEngine. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.fitness.cipherkey;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.entities.Chromosome;

public class KnownSolutionCrowdingFitnessEvaluator extends KnownSolutionFitnessEvaluator {
	private int		minCrowdSize;
	private double	penaltyFactor;
	private double	sigma;

	@Override
	public BigDecimal evaluate(Chromosome chromosome) {
		BigDecimal fitness = super.evaluate(chromosome);

		int crowdSize = 1;
		for (Chromosome other : chromosome.getPopulation().getIndividuals()) {
			if (chromosome.similarityTo(other) > sigma) {
				crowdSize++;
			}
		}

		for (int i = crowdSize - minCrowdSize; i > 0; i -= minCrowdSize) {
			fitness = fitness.multiply(BigDecimal.valueOf(penaltyFactor));
		}

		return fitness;
	}

	/**
	 * @param minCrowdSize
	 *            the minGroupSize to set
	 */
	@Required
	public void setMinCrowdSize(int minCrowdSize) {
		this.minCrowdSize = minCrowdSize;
	}

	/**
	 * @param penaltyFactor
	 *            the penaltyFactor to set
	 */
	@Required
	public void setPenaltyFactor(double penaltyFactor) {
		this.penaltyFactor = penaltyFactor;
	}

	/**
	 * @param sigma
	 *            the sigma to set
	 */
	@Required
	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	@Override
	public String getDisplayName() {
		return "Known Solution Crowding";
	}
}
