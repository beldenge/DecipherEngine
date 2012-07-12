package com.ciphertool.zodiacengine.genetic;

public interface GeneListDao {
	public Gene findRandomGene(Chromosome chromosome, int beginIndex);
}
