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

import static org.mockito.Mockito.spy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.ciphertool.sherlock.etl.importers.LetterNGramMarkovImporter;
import com.ciphertool.sherlock.markov.MarkovModel;
import com.ciphertool.sherlock.markov.NGramIndexNode;

public class RouletteSamplerTest {

	// @Test
	public void testSampling() {
		ThreadPoolTaskExecutor taskExecutorSpy = spy(new ThreadPoolTaskExecutor());
		taskExecutorSpy.setCorePoolSize(4);
		taskExecutorSpy.setMaxPoolSize(4);
		taskExecutorSpy.setQueueCapacity(1000000);
		taskExecutorSpy.setKeepAliveSeconds(1);
		taskExecutorSpy.setAllowCoreThreadTimeOut(true);
		taskExecutorSpy.initialize();

		MarkovModel letterMarkovModel = new MarkovModel();
		letterMarkovModel.setOrder(3);
		letterMarkovModel.setTaskExecutor(taskExecutorSpy);

		LetterNGramMarkovImporter importer = new LetterNGramMarkovImporter();
		importer.setLetterMarkovModel(letterMarkovModel);
		importer.setCorpusDirectory("/Users/george/Desktop/sherlock-transformed");
		importer.setMinCount(2);
		importer.setTaskExecutor(taskExecutorSpy);
		importer.importCorpus();

		List<LetterProbability> letterUnigramProbabilities = new ArrayList<>();

		for (Map.Entry<Character, NGramIndexNode> entry : letterMarkovModel.getRootNode().getTransitions().entrySet()) {
			letterUnigramProbabilities.add(new LetterProbability(entry.getKey(),
					entry.getValue().getTerminalInfo().getProbability()));
		}

		RouletteSampler<LetterProbability> rouletteSampler = new RouletteSampler<>();

		rouletteSampler.reIndex(letterUnigramProbabilities);

		Map<Character, Integer> characterCounts = new HashMap<>();
		int nextIndex;

		int i = 0;

		for (; i < 1000000; i++) {
			nextIndex = rouletteSampler.getNextIndex(letterUnigramProbabilities);

			if (characterCounts.get(letterUnigramProbabilities.get(nextIndex).getValue()) == null) {
				characterCounts.put(letterUnigramProbabilities.get(nextIndex).getValue(), 1);
			}

			characterCounts.put(letterUnigramProbabilities.get(nextIndex).getValue(), characterCounts.get(letterUnigramProbabilities.get(nextIndex).getValue())
					+ 1);
		}

		for (Map.Entry<Character, NGramIndexNode> entry : letterMarkovModel.getRootNode().getTransitions().entrySet()) {
			BigDecimal actual = entry.getValue().getTerminalInfo().getProbability().setScale(5, RoundingMode.HALF_UP);
			BigDecimal estimated = BigDecimal.valueOf(((double) characterCounts.get(entry.getKey())
					/ (double) i)).setScale(5, RoundingMode.HALF_UP);

			System.out.printf(entry.getKey() + ": actual=" + actual.toString() + ", estimated=" + estimated.toString()
					+ ", difference=" + actual.subtract(estimated).abs().setScale(5, RoundingMode.HALF_UP).toString()
					+ "\n");
		}
	}
}
