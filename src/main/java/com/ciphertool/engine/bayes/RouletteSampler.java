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

package com.ciphertool.engine.bayes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ciphertool.genetics.algorithms.selection.BinaryRouletteNode;
import com.ciphertool.genetics.algorithms.selection.BinaryRouletteTree;
import com.ciphertool.sherlock.MathConstants;

public class RouletteSampler<T extends Probability<?>> {
	private Logger				log	= LoggerFactory.getLogger(getClass());

	private BinaryRouletteTree	rouletteWheel;

	public synchronized void reIndex(List<T> probabilities) {
		this.rouletteWheel = new BinaryRouletteTree();

		List<BinaryRouletteNode> nodes = new ArrayList<BinaryRouletteNode>();

		BigDecimal totalFitness = BigDecimal.ZERO;

		for (int i = 0; i < probabilities.size(); i++) {
			if (probabilities.get(i) == null || probabilities.get(i).getProbability().equals(BigDecimal.ZERO)) {
				continue;
			}

			if (probabilities.get(i).getProbability() == null) {
				log.warn("Attempted to spin roulette wheel but an individual was found with a null fitness value.  Please make a call to evaluateFitness() before attempting to spin the roulette wheel. "
						+ probabilities.get(i));

				continue;
			}

			totalFitness = totalFitness.add(probabilities.get(i).getProbability(), MathConstants.PREC_10_HALF_UP);

			nodes.add(new BinaryRouletteNode(i, totalFitness));
		}

		if (totalFitness.compareTo(BigDecimal.ZERO) > 0) {
			addToTreeBalanced(nodes);
		}
	}

	protected void addToTreeBalanced(List<BinaryRouletteNode> nodes) {
		int half = nodes.size() / 2;

		this.rouletteWheel.insert(nodes.get(half));

		if (nodes.size() == 1) {
			return;
		}

		addToTreeBalanced(nodes.subList(0, half));

		if (nodes.size() == 2) {
			return;
		}

		addToTreeBalanced(nodes.subList(half + 1, nodes.size()));
	}

	public int getNextIndex(List<T> probabilities) {
		if (probabilities == null || probabilities.isEmpty()) {
			log.warn("Attempted to select an individual from a null or empty population.  Unable to continue.");

			return -1;
		}

		BigDecimal randomIndex = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble());

		BinaryRouletteNode winner = this.rouletteWheel.find(randomIndex);

		return winner.getIndex();
	}
}
