package com.ciphertool.zodiacengine.dao.cipherkey;

import com.ciphertool.genetics.dao.GeneDao;
import com.ciphertool.genetics.entities.Chromosome;
import com.ciphertool.genetics.entities.Gene;
import com.ciphertool.sentencebuilder.util.LetterUtils;
import com.ciphertool.zodiacengine.entities.cipherkey.CipherKeyGene;

public class CipherKeyGeneDao implements GeneDao {

	@Override
	public Gene findRandomGene(Chromosome chromosome) {
		CipherKeyGene newGene = new CipherKeyGene(chromosome, String.valueOf(LetterUtils.getRandomLetter()));
		
		return newGene;
	}
}