/**
 * Copyright 2013 George Belden
 * 
 * This file is part of ZodiacEngine.
 * 
 * ZodiacEngine is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ZodiacEngine is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ZodiacEngine. If not, see <http://www.gnu.org/licenses/>.
 */
package com.ciphertool.zodiacengine.algorithms.selection.modes;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.genetics.Population;
import com.ciphertool.genetics.algorithms.selection.modes.TournamentSelector;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;

public class TournamentSelectorTest {
	private static Logger log = Logger.getLogger(TournamentSelectorTest.class);

	private static Population population = new Population();

	@BeforeClass
	public static void setUp() {
		TournamentSelector tournamentSelector = new TournamentSelector();
		tournamentSelector.setSelectionAccuracy(0.9);
		population.setSelector(tournamentSelector);

		SolutionChromosome solution1 = new SolutionChromosome();
		solution1.setFitness(1.0);
		solution1.setDirty(false);
		population.addIndividual(solution1);

		SolutionChromosome solution2 = new SolutionChromosome();
		solution2.setFitness(10.0);
		solution2.setDirty(false);
		population.addIndividual(solution2);

		SolutionChromosome solution3 = new SolutionChromosome();
		solution3.setFitness(100.0);
		solution3.setDirty(false);
		population.addIndividual(solution3);

		SolutionChromosome solution4 = new SolutionChromosome();
		solution4.setFitness(1000.0);
		solution4.setDirty(false);
		population.addIndividual(solution4);

		SolutionChromosome solution5 = new SolutionChromosome();
		solution5.setFitness(10000.0);
		solution5.setDirty(false);
		population.addIndividual(solution5);

		SolutionChromosome solution6 = new SolutionChromosome();
		solution6.setFitness(0.9);
		solution6.setDirty(false);
		population.addIndividual(solution6);

		SolutionChromosome solution7 = new SolutionChromosome();
		solution7.setFitness(9.0);
		solution7.setDirty(false);
		population.addIndividual(solution7);

		SolutionChromosome solution8 = new SolutionChromosome();
		solution8.setFitness(99.0);
		solution8.setDirty(false);
		population.addIndividual(solution8);

		SolutionChromosome solution9 = new SolutionChromosome();
		solution9.setFitness(999.0);
		solution9.setDirty(false);
		population.addIndividual(solution9);

		SolutionChromosome solution10 = new SolutionChromosome();
		solution10.setFitness(9999.0);
		solution10.setDirty(false);
		population.addIndividual(solution10);
	}

	@Test
	public void testSpinIndexRouletteWheel() {
		int winningNumber = -1;

		winningNumber = population.selectIndex();

		assertTrue(winningNumber > -1);

		log.info("And the winner's fitness is: "
				+ population.getIndividuals().get(winningNumber).getFitness());
	}
}
