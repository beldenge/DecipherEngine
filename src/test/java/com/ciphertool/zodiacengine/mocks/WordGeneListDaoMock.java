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

package com.ciphertool.zodiacengine.mocks;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.dao.WordGeneListDao;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;

public class WordGeneListDaoMock extends WordGeneListDao {
	private static Logger log = Logger.getLogger(WordGeneListDaoMock.class);

	@Override
	public Gene findRandomGene(Chromosome chromosome, int beginIndex) {
		WordGene wordGene = new WordGene(new Word(new WordId("$$$", 'X')),
				(SolutionChromosome) chromosome, beginIndex);

		return wordGene;
	}

	@Override
	public Gene findRandomGeneOfLength(Chromosome chromosome, int beginIndex, int length) {
		if (length < 1) {
			log.warn("Unable to find random gene of length "
					+ length
					+ " since it is less than 1, which is obviously the minimum length.  Returning null.");

			return null;
		}

		if (length <= 10) {
			return findRandomGeneWithinTen(chromosome, beginIndex, length);
		}

		return findRandomGeneOverTen(chromosome, beginIndex, length);
	}

	public Gene findRandomGeneWithinTen(Chromosome chromosome, int beginIndex, int length) {
		Map<Integer, Gene> geneLengthMap = new HashMap<Integer, Gene>();

		Word word1 = new Word(new WordId("I", 'N'));
		WordGene wordGene1 = new WordGene(word1, (SolutionChromosome) chromosome, beginIndex);
		geneLengthMap.put(1, wordGene1);

		Word word2 = new Word(new WordId("am", 'p'));
		WordGene wordGene2 = new WordGene(word2, (SolutionChromosome) chromosome, beginIndex);
		geneLengthMap.put(2, wordGene2);

		Word word3 = new Word(new WordId("the", 'h'));
		WordGene wordGene3 = new WordGene(word3, (SolutionChromosome) chromosome, beginIndex);
		geneLengthMap.put(3, wordGene3);

		Word word4 = new Word(new WordId("best", 'V'));
		WordGene wordGene4 = new WordGene(word4, (SolutionChromosome) chromosome, beginIndex);
		geneLengthMap.put(4, wordGene4);

		Word word5 = new Word(new WordId("homie", 't'));
		WordGene wordGene5 = new WordGene(word5, (SolutionChromosome) chromosome, beginIndex);
		geneLengthMap.put(5, wordGene5);

		Word word6 = new Word(new WordId("hollah", 'i'));
		WordGene wordGene6 = new WordGene(word6, (SolutionChromosome) chromosome, beginIndex);
		geneLengthMap.put(6, wordGene6);

		Word word7 = new Word(new WordId("seventy", 'A'));
		WordGene wordGene7 = new WordGene(word7, (SolutionChromosome) chromosome, beginIndex);
		geneLengthMap.put(7, wordGene7);

		Word word8 = new Word(new WordId("trillion", 'v'));
		WordGene wordGene8 = new WordGene(word8, (SolutionChromosome) chromosome, beginIndex);
		geneLengthMap.put(8, wordGene8);

		Word word9 = new Word(new WordId("benjamins", 'C'));
		WordGene wordGene9 = new WordGene(word9, (SolutionChromosome) chromosome, beginIndex);
		geneLengthMap.put(9, wordGene9);

		Word word10 = new Word(new WordId("investment", 'P'));
		WordGene wordGene10 = new WordGene(word10, (SolutionChromosome) chromosome, beginIndex);
		geneLengthMap.put(10, wordGene10);

		return geneLengthMap.get(length);
	}

	public Gene findRandomGeneOverTen(Chromosome chromosome, int beginIndex, int length) {
		StringBuffer word = new StringBuffer("");
		for (int i = 0; i < length; i++) {
			word.append("$");
		}

		WordGene wordGene = new WordGene(new Word(new WordId(word.toString(), 'X')),
				(SolutionChromosome) chromosome, beginIndex);

		return wordGene;
	}
}