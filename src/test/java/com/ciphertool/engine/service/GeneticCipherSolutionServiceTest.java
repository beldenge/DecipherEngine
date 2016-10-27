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

package com.ciphertool.engine.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.util.ReflectionUtils;

import com.ciphertool.engine.dao.SolutionDao;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.engine.view.GenericCallback;
import com.ciphertool.genetics.ChromosomePrinter;
import com.ciphertool.genetics.GeneticAlgorithmStrategy;
import com.ciphertool.genetics.StandardPopulation;
import com.ciphertool.genetics.algorithms.GeneticAlgorithm;

public class GeneticCipherSolutionServiceTest {
	private static ChromosomePrinter chromosomePrinterMock = mock(ChromosomePrinter.class);

	@Test
	public void testSetGeneticAlgorithm() {
		GeneticAlgorithm geneticAlgorithmToSet = mock(GeneticAlgorithm.class);
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();
		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithmToSet);

		Field geneticAlgorithmField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "geneticAlgorithm");
		ReflectionUtils.makeAccessible(geneticAlgorithmField);
		GeneticAlgorithm geneticAlgorithmFromObject = (GeneticAlgorithm) ReflectionUtils.getField(geneticAlgorithmField, geneticCipherSolutionService);

		assertSame(geneticAlgorithmToSet, geneticAlgorithmFromObject);
	}

	@Test
	public void testSetCommandsBefore() {
		String[] commandsBeforeToSet = new String[1];
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();
		geneticCipherSolutionService.setCommandsBefore(commandsBeforeToSet);

		Field commandsBeforeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "commandsBefore");
		ReflectionUtils.makeAccessible(commandsBeforeField);
		String[] commandsBeforeFromObject = (String[]) ReflectionUtils.getField(commandsBeforeField, geneticCipherSolutionService);

		assertSame(commandsBeforeToSet, commandsBeforeFromObject);
	}

	@Test
	public void testSetCommandsAfter() {
		String[] commandsAfterToSet = new String[1];
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();
		geneticCipherSolutionService.setCommandsAfter(commandsAfterToSet);

		Field commandsAfterField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "commandsAfter");
		ReflectionUtils.makeAccessible(commandsAfterField);
		String[] commandsAfterFromObject = (String[]) ReflectionUtils.getField(commandsAfterField, geneticCipherSolutionService);

		assertSame(commandsAfterToSet, commandsAfterFromObject);
	}

	@Test
	public void testSetSolutionDao() {
		SolutionDao solutionDaoToSet = mock(SolutionDao.class);
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();
		geneticCipherSolutionService.setSolutionDao(solutionDaoToSet);

		Field solutionDaoField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "solutionDao");
		ReflectionUtils.makeAccessible(solutionDaoField);
		SolutionDao solutionDaoFromObject = (SolutionDao) ReflectionUtils.getField(solutionDaoField, geneticCipherSolutionService);

		assertSame(solutionDaoToSet, solutionDaoFromObject);
	}

	@Test
	public void testBegin() throws IOException, InterruptedException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);

		StandardPopulation population = new StandardPopulation();
		population.setChromosomePrinter(chromosomePrinterMock);
		CipherKeyChromosome solutionChromosome = new CipherKeyChromosome();
		solutionChromosome.setEvaluationNeeded(false);
		population.addIndividual(solutionChromosome);
		when(geneticAlgorithm.getPopulation()).thenReturn(population);

		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		SolutionDao solutionDaoMock = mock(SolutionDao.class);
		geneticCipherSolutionService.setSolutionDao(solutionDaoMock);

		GenericCallback genericCallbackMock = mock(GenericCallback.class);

		Runtime runtimeMock = mock(Runtime.class);

		Field runtimeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "runtime");
		ReflectionUtils.makeAccessible(runtimeField);
		ReflectionUtils.setField(runtimeField, geneticCipherSolutionService, runtimeMock);

		String[] commandsAfter = { "command1", "command2" };
		geneticCipherSolutionService.setCommandsAfter(commandsAfter);

		String[] commandsBefore = { "command3", "command4" };
		geneticCipherSolutionService.setCommandsBefore(commandsBefore);

		GeneticAlgorithmStrategy geneticAlgorithmStrategy = new GeneticAlgorithmStrategy();

		// Before the algorithm begins, this will be false
		assertFalse(geneticCipherSolutionService.isRunning());
		geneticCipherSolutionService.begin(geneticAlgorithmStrategy, genericCallbackMock, false);
		/*
		 * After the algorithm ends, this will again be false. It is only true actually during the algorithm.
		 */
		assertFalse(geneticCipherSolutionService.isRunning());

		verify(solutionDaoMock, times(1)).insert(same(solutionChromosome));

		verify(genericCallbackMock, times(1)).doCallback();

		/*
		 * These commands should still get called even though an exception was caught
		 */
		verify(runtimeMock, times(1)).exec(eq("command1"));
		verify(runtimeMock, times(1)).exec(eq("command2"));
		verify(runtimeMock, times(1)).exec(eq("command3"));
		verify(runtimeMock, times(1)).exec(eq("command4"));
		verifyNoMoreInteractions(runtimeMock);

		verify(geneticAlgorithm, times(1)).evolveAutonomously();

		verify(geneticAlgorithm, times(1)).setStrategy(same(geneticAlgorithmStrategy));
	}

	@Test
	public void testBegin_DebugMode() throws IOException, InterruptedException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);

		StandardPopulation population = new StandardPopulation();
		population.setChromosomePrinter(chromosomePrinterMock);
		CipherKeyChromosome solutionChromosome = new CipherKeyChromosome();
		solutionChromosome.setEvaluationNeeded(false);
		population.addIndividual(solutionChromosome);
		when(geneticAlgorithm.getPopulation()).thenReturn(population);

		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		GenericCallback genericCallbackMock = mock(GenericCallback.class);

		GeneticAlgorithmStrategy geneticAlgorithmStrategy = new GeneticAlgorithmStrategy();

		Runtime runtimeMock = mock(Runtime.class);

		Field runtimeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "runtime");
		ReflectionUtils.makeAccessible(runtimeField);
		ReflectionUtils.setField(runtimeField, geneticCipherSolutionService, runtimeMock);

		String[] commandsBefore = { "command3", "command4" };
		geneticCipherSolutionService.setCommandsBefore(commandsBefore);

		assertFalse(geneticCipherSolutionService.isRunning());
		geneticCipherSolutionService.begin(geneticAlgorithmStrategy, genericCallbackMock, true);
		assertTrue(geneticCipherSolutionService.isRunning());

		verify(runtimeMock, times(1)).exec(eq("command3"));
		verify(runtimeMock, times(1)).exec(eq("command4"));
		verifyNoMoreInteractions(runtimeMock);

		verifyZeroInteractions(genericCallbackMock);

		verify(geneticAlgorithm, times(1)).initialize();
		verify(geneticAlgorithm, times(1)).getPopulation();
		verify(geneticAlgorithm, times(1)).proceedWithNextGeneration();

		verify(geneticAlgorithm, times(1)).setStrategy(same(geneticAlgorithmStrategy));
	}

	@Test
	public void testSetUp() throws IOException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);
		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		Runtime runtimeMock = mock(Runtime.class);

		Field runtimeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "runtime");
		ReflectionUtils.makeAccessible(runtimeField);
		ReflectionUtils.setField(runtimeField, geneticCipherSolutionService, runtimeMock);

		String[] commandsBefore = { "command1", "command2" };
		geneticCipherSolutionService.setCommandsBefore(commandsBefore);

		GeneticAlgorithmStrategy geneticAlgorithmStrategy = new GeneticAlgorithmStrategy();
		geneticCipherSolutionService.setUp(geneticAlgorithmStrategy);

		verify(runtimeMock, times(1)).exec(eq("command1"));
		verify(runtimeMock, times(1)).exec(eq("command2"));
		verifyNoMoreInteractions(runtimeMock);

		verify(geneticAlgorithm, times(1)).setStrategy(same(geneticAlgorithmStrategy));
	}

	@Test
	public void testEndImmediately() {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);

		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		geneticCipherSolutionService.endImmediately(false);

		verify(geneticAlgorithm, times(1)).requestStop();
	}

	@Test
	public void testEndImmediately_DebugMode() throws IOException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();
		geneticCipherSolutionService.toggleRunning();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);
		StandardPopulation population = new StandardPopulation();
		population.setChromosomePrinter(chromosomePrinterMock);

		CipherKeyChromosome solutionChromosome = new CipherKeyChromosome();
		solutionChromosome.setEvaluationNeeded(false);
		population.addIndividual(solutionChromosome);
		when(geneticAlgorithm.getPopulation()).thenReturn(population);
		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		Runtime runtimeMock = mock(Runtime.class);

		Field runtimeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "runtime");
		ReflectionUtils.makeAccessible(runtimeField);
		ReflectionUtils.setField(runtimeField, geneticCipherSolutionService, runtimeMock);

		String[] commandsAfter = { "command1", "command2" };
		geneticCipherSolutionService.setCommandsAfter(commandsAfter);

		SolutionDao solutionDaoMock = mock(SolutionDao.class);
		geneticCipherSolutionService.setSolutionDao(solutionDaoMock);

		assertTrue(geneticCipherSolutionService.isRunning());
		geneticCipherSolutionService.endImmediately(true);
		assertFalse(geneticCipherSolutionService.isRunning());

		verify(solutionDaoMock, times(1)).insert(same(solutionChromosome));

		verify(runtimeMock, times(1)).exec(eq("command1"));
		verify(runtimeMock, times(1)).exec(eq("command2"));
		verifyNoMoreInteractions(runtimeMock);
	}

	@Test
	public void testResume() throws IOException, InterruptedException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);

		StandardPopulation population = new StandardPopulation();
		population.setChromosomePrinter(chromosomePrinterMock);
		CipherKeyChromosome solutionChromosome = new CipherKeyChromosome();
		solutionChromosome.setEvaluationNeeded(false);
		population.addIndividual(solutionChromosome);
		when(geneticAlgorithm.getPopulation()).thenReturn(population);

		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		geneticCipherSolutionService.resume();

		verify(geneticAlgorithm, times(1)).getPopulation();
		verify(geneticAlgorithm, times(1)).proceedWithNextGeneration();
	}

	@Test
	public void testResume_ExceptionThrown() throws IOException, InterruptedException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();
		geneticCipherSolutionService.toggleRunning();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);
		doThrow(IllegalStateException.class).when(geneticAlgorithm).proceedWithNextGeneration();

		StandardPopulation population = new StandardPopulation();
		population.setChromosomePrinter(chromosomePrinterMock);
		CipherKeyChromosome solutionChromosome = new CipherKeyChromosome();
		solutionChromosome.setEvaluationNeeded(false);
		population.addIndividual(solutionChromosome);
		when(geneticAlgorithm.getPopulation()).thenReturn(population);

		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		Runtime runtimeMock = mock(Runtime.class);

		Field runtimeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "runtime");
		ReflectionUtils.makeAccessible(runtimeField);
		ReflectionUtils.setField(runtimeField, geneticCipherSolutionService, runtimeMock);

		String[] commandsAfter = { "command1", "command2" };
		geneticCipherSolutionService.setCommandsAfter(commandsAfter);

		SolutionDao solutionDaoMock = mock(SolutionDao.class);
		geneticCipherSolutionService.setSolutionDao(solutionDaoMock);

		Field logField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "log");
		Logger mockLogger = mock(Logger.class);
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, geneticCipherSolutionService, mockLogger);

		doNothing().when(mockLogger).error(anyString(), any(Throwable.class));

		assertTrue(geneticCipherSolutionService.isRunning());
		geneticCipherSolutionService.resume();
		assertFalse(geneticCipherSolutionService.isRunning());

		verify(solutionDaoMock, times(1)).insert(same(solutionChromosome));

		verify(mockLogger, times(1)).error(anyString(), any(Throwable.class));

		/*
		 * These commands should still get called even though an exception was caught
		 */
		verify(runtimeMock, times(1)).exec(eq("command1"));
		verify(runtimeMock, times(1)).exec(eq("command2"));
		verifyNoMoreInteractions(runtimeMock);
	}

	@Test
	public void testRunAlgorithmAutonomously() throws IOException, InterruptedException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();
		geneticCipherSolutionService.toggleRunning();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);

		StandardPopulation population = new StandardPopulation();
		population.setChromosomePrinter(chromosomePrinterMock);
		CipherKeyChromosome solutionChromosome = new CipherKeyChromosome();
		solutionChromosome.setEvaluationNeeded(false);
		population.addIndividual(solutionChromosome);
		when(geneticAlgorithm.getPopulation()).thenReturn(population);

		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		SolutionDao solutionDaoMock = mock(SolutionDao.class);
		geneticCipherSolutionService.setSolutionDao(solutionDaoMock);

		GenericCallback genericCallbackMock = mock(GenericCallback.class);

		Runtime runtimeMock = mock(Runtime.class);

		Field runtimeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "runtime");
		ReflectionUtils.makeAccessible(runtimeField);
		ReflectionUtils.setField(runtimeField, geneticCipherSolutionService, runtimeMock);

		String[] commandsAfter = { "command1", "command2" };
		geneticCipherSolutionService.setCommandsAfter(commandsAfter);

		assertTrue(geneticCipherSolutionService.isRunning());
		geneticCipherSolutionService.runAlgorithmAutonomously(genericCallbackMock);
		assertFalse(geneticCipherSolutionService.isRunning());

		verify(solutionDaoMock, times(1)).insert(same(solutionChromosome));

		verify(genericCallbackMock, times(1)).doCallback();

		/*
		 * These commands should still get called even though an exception was caught
		 */
		verify(runtimeMock, times(1)).exec(eq("command1"));
		verify(runtimeMock, times(1)).exec(eq("command2"));
		verifyNoMoreInteractions(runtimeMock);

		verify(geneticAlgorithm, times(1)).evolveAutonomously();
	}

	@Test
	public void testRunAlgorithmAutonomously_ExceptionThrown() throws IOException, InterruptedException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();
		geneticCipherSolutionService.toggleRunning();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);

		StandardPopulation population = new StandardPopulation();
		population.setChromosomePrinter(chromosomePrinterMock);
		CipherKeyChromosome solutionChromosome = new CipherKeyChromosome();
		solutionChromosome.setEvaluationNeeded(false);
		population.addIndividual(solutionChromosome);
		when(geneticAlgorithm.getPopulation()).thenReturn(population);

		doThrow(new IllegalStateException()).when(geneticAlgorithm).evolveAutonomously();

		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		SolutionDao solutionDaoMock = mock(SolutionDao.class);
		geneticCipherSolutionService.setSolutionDao(solutionDaoMock);

		GenericCallback genericCallbackMock = mock(GenericCallback.class);

		Runtime runtimeMock = mock(Runtime.class);

		Field runtimeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "runtime");
		ReflectionUtils.makeAccessible(runtimeField);
		ReflectionUtils.setField(runtimeField, geneticCipherSolutionService, runtimeMock);

		String[] commandsAfter = { "command1", "command2" };
		geneticCipherSolutionService.setCommandsAfter(commandsAfter);

		Field logField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "log");
		Logger mockLogger = mock(Logger.class);
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, geneticCipherSolutionService, mockLogger);

		doNothing().when(mockLogger).error(anyString(), any(Throwable.class));

		assertTrue(geneticCipherSolutionService.isRunning());
		geneticCipherSolutionService.runAlgorithmAutonomously(genericCallbackMock);
		assertFalse(geneticCipherSolutionService.isRunning());

		verify(solutionDaoMock, times(1)).insert(same(solutionChromosome));

		verify(genericCallbackMock, times(1)).doCallback();

		verify(mockLogger, times(1)).error(anyString(), any(Throwable.class));

		/*
		 * These commands should still get called even though an exception was caught
		 */
		verify(runtimeMock, times(1)).exec(eq("command1"));
		verify(runtimeMock, times(1)).exec(eq("command2"));
		verifyNoMoreInteractions(runtimeMock);

		verify(geneticAlgorithm, times(1)).evolveAutonomously();
	}

	@Test
	public void testRunAlgorithmStepwise() throws InterruptedException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);

		StandardPopulation population = new StandardPopulation();
		population.setChromosomePrinter(chromosomePrinterMock);
		CipherKeyChromosome solutionChromosome = new CipherKeyChromosome();
		solutionChromosome.setEvaluationNeeded(false);
		population.addIndividual(solutionChromosome);
		when(geneticAlgorithm.getPopulation()).thenReturn(population);

		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		geneticCipherSolutionService.runAlgorithmStepwise();

		verify(geneticAlgorithm, times(1)).initialize();
		verify(geneticAlgorithm, times(1)).getPopulation();
		verify(geneticAlgorithm, times(1)).proceedWithNextGeneration();
	}

	@Test
	public void testRunAlgorithmStepwise_ExceptionThrown() throws IOException, InterruptedException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();
		geneticCipherSolutionService.toggleRunning();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);
		doThrow(IllegalStateException.class).when(geneticAlgorithm).initialize();

		StandardPopulation population = new StandardPopulation();
		population.setChromosomePrinter(chromosomePrinterMock);
		CipherKeyChromosome solutionChromosome = new CipherKeyChromosome();
		solutionChromosome.setEvaluationNeeded(false);
		population.addIndividual(solutionChromosome);
		when(geneticAlgorithm.getPopulation()).thenReturn(population);

		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		Runtime runtimeMock = mock(Runtime.class);

		Field runtimeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "runtime");
		ReflectionUtils.makeAccessible(runtimeField);
		ReflectionUtils.setField(runtimeField, geneticCipherSolutionService, runtimeMock);

		String[] commandsAfter = { "command1", "command2" };
		geneticCipherSolutionService.setCommandsAfter(commandsAfter);

		SolutionDao solutionDaoMock = mock(SolutionDao.class);
		geneticCipherSolutionService.setSolutionDao(solutionDaoMock);

		Field logField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "log");
		Logger mockLogger = mock(Logger.class);
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, geneticCipherSolutionService, mockLogger);

		doNothing().when(mockLogger).error(anyString(), any(Throwable.class));

		assertTrue(geneticCipherSolutionService.isRunning());
		geneticCipherSolutionService.runAlgorithmStepwise();
		assertFalse(geneticCipherSolutionService.isRunning());

		verify(solutionDaoMock, times(1)).insert(same(solutionChromosome));

		verify(mockLogger, times(1)).error(anyString(), any(Throwable.class));

		/*
		 * These commands should still get called even though an exception was caught
		 */
		verify(runtimeMock, times(1)).exec(eq("command1"));
		verify(runtimeMock, times(1)).exec(eq("command2"));
		verifyNoMoreInteractions(runtimeMock);
	}

	@Test
	public void testEnd() throws IOException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();
		geneticCipherSolutionService.toggleRunning();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);
		StandardPopulation population = new StandardPopulation();
		population.setChromosomePrinter(chromosomePrinterMock);

		CipherKeyChromosome solutionChromosome = new CipherKeyChromosome();
		solutionChromosome.setEvaluationNeeded(false);
		population.addIndividual(solutionChromosome);
		when(geneticAlgorithm.getPopulation()).thenReturn(population);
		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		Runtime runtimeMock = mock(Runtime.class);

		Field runtimeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "runtime");
		ReflectionUtils.makeAccessible(runtimeField);
		ReflectionUtils.setField(runtimeField, geneticCipherSolutionService, runtimeMock);

		String[] commandsAfter = { "command1", "command2" };
		geneticCipherSolutionService.setCommandsAfter(commandsAfter);

		SolutionDao solutionDaoMock = mock(SolutionDao.class);
		geneticCipherSolutionService.setSolutionDao(solutionDaoMock);

		assertTrue(geneticCipherSolutionService.isRunning());
		geneticCipherSolutionService.end();
		assertFalse(geneticCipherSolutionService.isRunning());

		verify(solutionDaoMock, times(1)).insert(same(solutionChromosome));

		verify(runtimeMock, times(1)).exec(eq("command1"));
		verify(runtimeMock, times(1)).exec(eq("command2"));
		verifyNoMoreInteractions(runtimeMock);
	}

	@Test
	public void testEnd_ExceptionThrown() throws IOException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();
		geneticCipherSolutionService.toggleRunning();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);
		when(geneticAlgorithm.getPopulation()).thenThrow(new IllegalStateException());
		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		Runtime runtimeMock = mock(Runtime.class);

		Field runtimeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "runtime");
		ReflectionUtils.makeAccessible(runtimeField);
		ReflectionUtils.setField(runtimeField, geneticCipherSolutionService, runtimeMock);

		String[] commandsAfter = { "command1", "command2" };
		geneticCipherSolutionService.setCommandsAfter(commandsAfter);

		SolutionDao solutionDaoMock = mock(SolutionDao.class);
		geneticCipherSolutionService.setSolutionDao(solutionDaoMock);

		Field logField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "log");
		Logger mockLogger = mock(Logger.class);
		ReflectionUtils.makeAccessible(logField);
		ReflectionUtils.setField(logField, geneticCipherSolutionService, mockLogger);

		doNothing().when(mockLogger).error(anyString(), any(Throwable.class));

		assertTrue(geneticCipherSolutionService.isRunning());
		geneticCipherSolutionService.end();
		assertFalse(geneticCipherSolutionService.isRunning());

		verifyZeroInteractions(solutionDaoMock);

		verify(mockLogger, times(1)).error(anyString(), any(Throwable.class));

		/*
		 * These commands should still get called even though an exception was caught
		 */
		verify(runtimeMock, times(1)).exec(eq("command1"));
		verify(runtimeMock, times(1)).exec(eq("command2"));
		verifyNoMoreInteractions(runtimeMock);
	}

	@Test
	public void testTearDown() throws IOException {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();

		Runtime runtimeMock = mock(Runtime.class);

		Field runtimeField = ReflectionUtils.findField(GeneticCipherSolutionService.class, "runtime");
		ReflectionUtils.makeAccessible(runtimeField);
		ReflectionUtils.setField(runtimeField, geneticCipherSolutionService, runtimeMock);

		String[] commandsAfter = { "command1", "command2" };
		geneticCipherSolutionService.setCommandsAfter(commandsAfter);

		geneticCipherSolutionService.tearDown();

		verify(runtimeMock, times(1)).exec(eq("command1"));
		verify(runtimeMock, times(1)).exec(eq("command2"));
		verifyNoMoreInteractions(runtimeMock);
	}

	@Test
	public void testPersistPopulation() {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);
		StandardPopulation population = new StandardPopulation();

		CipherKeyChromosome solutionChromosome = new CipherKeyChromosome();
		solutionChromosome.setEvaluationNeeded(false);
		population.addIndividual(solutionChromosome);
		when(geneticAlgorithm.getPopulation()).thenReturn(population);
		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		SolutionDao solutionDaoMock = mock(SolutionDao.class);
		geneticCipherSolutionService.setSolutionDao(solutionDaoMock);

		geneticCipherSolutionService.persistPopulation();

		verify(solutionDaoMock, times(1)).insert(same(solutionChromosome));
	}

	@Test
	public void testPersistPopulation_Empty() {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();

		GeneticAlgorithm geneticAlgorithm = mock(GeneticAlgorithm.class);
		StandardPopulation population = new StandardPopulation();
		when(geneticAlgorithm.getPopulation()).thenReturn(population);
		geneticCipherSolutionService.setGeneticAlgorithm(geneticAlgorithm);

		SolutionDao solutionDaoMock = mock(SolutionDao.class);
		geneticCipherSolutionService.setSolutionDao(solutionDaoMock);

		geneticCipherSolutionService.persistPopulation();

		verifyZeroInteractions(solutionDaoMock);
	}

	@Test
	public void testIsRunningAndToggleRunning() {
		GeneticCipherSolutionService geneticCipherSolutionService = new GeneticCipherSolutionService();

		assertFalse(geneticCipherSolutionService.isRunning());

		geneticCipherSolutionService.toggleRunning();

		assertTrue(geneticCipherSolutionService.isRunning());

		geneticCipherSolutionService.toggleRunning();

		assertFalse(geneticCipherSolutionService.isRunning());
	}
}
