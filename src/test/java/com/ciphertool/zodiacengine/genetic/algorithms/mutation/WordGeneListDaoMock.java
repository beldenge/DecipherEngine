package com.ciphertool.zodiacengine.genetic.algorithms.mutation;

import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;
import com.ciphertool.zodiacengine.genetic.dao.WordGeneListDao;

public class WordGeneListDaoMock extends WordGeneListDao {
	@Override
	public Gene findRandomGene(Chromosome chromosome, int beginIndex) {
		WordGene wordGene = new WordGene(new Word(new WordId("$$$", 'X')),
				(SolutionChromosome) chromosome, beginIndex);

		return wordGene;
	}

	@Override
	public Gene findRandomGeneOfLength(Chromosome chromosome, int beginIndex, int length) {
		StringBuffer word = new StringBuffer("");
		for (int i = 0; i < length; i++) {
			word.append("$");
		}

		WordGene wordGene = new WordGene(new Word(new WordId(word.toString(), 'X')),
				(SolutionChromosome) chromosome, beginIndex);

		return wordGene;
	}
}