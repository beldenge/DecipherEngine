package com.ciphertool.zodiacengine.util;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.GenericTestBase;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;

public class ZodiacTestBase extends GenericTestBase {
	protected static SolutionChromosome knownSolution = new SolutionChromosome();

	static {
		knownSolution.setCipherId(zodiac408.getId());
		knownSolution.setRows(zodiac408.getRows());
		knownSolution.setColumns(zodiac408.getColumns());

		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 0));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 1));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 2));
		knownSolution.addGene(new WordGene(new Word(new WordId("k", '*')), knownSolution, 3));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 4));
		knownSolution.addGene(new WordGene(new Word(new WordId("k", '*')), knownSolution, 5));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 6));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 7));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 8));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 9));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 10));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 11));
		knownSolution.addGene(new WordGene(new Word(new WordId("p", '*')), knownSolution, 12));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 13));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 14));
		knownSolution.addGene(new WordGene(new Word(new WordId("p", '*')), knownSolution, 15));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 16));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 17));
		knownSolution.addGene(new WordGene(new Word(new WordId("b", '*')), knownSolution, 18));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 19));
		knownSolution.addGene(new WordGene(new Word(new WordId("c", '*')), knownSolution, 20));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 21));
		knownSolution.addGene(new WordGene(new Word(new WordId("u", '*')), knownSolution, 22));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 23));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 24));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 25));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 26));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 27));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 28));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 29));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 30));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 31));
		knownSolution.addGene(new WordGene(new Word(new WordId("u", '*')), knownSolution, 32));
		knownSolution.addGene(new WordGene(new Word(new WordId("c", '*')), knownSolution, 33));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 34));
		knownSolution.addGene(new WordGene(new Word(new WordId("f", '*')), knownSolution, 35));
		knownSolution.addGene(new WordGene(new Word(new WordId("u", '*')), knownSolution, 36));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 37));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 38));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 39));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 40));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 41));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 42));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 43));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 44));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 45));
		knownSolution.addGene(new WordGene(new Word(new WordId("f", '*')), knownSolution, 46));
		knownSolution.addGene(new WordGene(new Word(new WordId("u", '*')), knownSolution, 47));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 48));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 49));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 50));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 51));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 52));
		knownSolution.addGene(new WordGene(new Word(new WordId("k", '*')), knownSolution, 53));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 54));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 55));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 56));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 57));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 58));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 59));
		knownSolution.addGene(new WordGene(new Word(new WordId("w", '*')), knownSolution, 60));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 61));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 62));
		knownSolution.addGene(new WordGene(new Word(new WordId("d", '*')), knownSolution, 63));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 64));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 65));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 66));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 67));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 68));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 69));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 70));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 71));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 72));
		knownSolution.addGene(new WordGene(new Word(new WordId("f", '*')), knownSolution, 73));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 74));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 75));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 76));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 77));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 78));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 79));
		knownSolution.addGene(new WordGene(new Word(new WordId("b", '*')), knownSolution, 80));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 81));
		knownSolution.addGene(new WordGene(new Word(new WordId("c", '*')), knownSolution, 82));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 83));
		knownSolution.addGene(new WordGene(new Word(new WordId("u", '*')), knownSolution, 84));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 85));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 86));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 87));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 88));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 89));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 90));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 91));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 92));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 93));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 94));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 95));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 96));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 97));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 98));
		knownSolution.addGene(new WordGene(new Word(new WordId("d", '*')), knownSolution, 99));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 100));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 101));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 102));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 103));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 104));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 105));
		knownSolution.addGene(new WordGene(new Word(new WordId("u", '*')), knownSolution, 106));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 107));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 108));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 109));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 110));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 111));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 112));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 113));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 114));
		knownSolution.addGene(new WordGene(new Word(new WordId("f", '*')), knownSolution, 115));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 116));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 117));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 118));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 119));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 120));
		knownSolution.addGene(new WordGene(new Word(new WordId("k", '*')), knownSolution, 121));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 122));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 123));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 124));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 125));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 126));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 127));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 128));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 129));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 130));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 131));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 132));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 133));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 134));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 135));
		knownSolution.addGene(new WordGene(new Word(new WordId("v", '*')), knownSolution, 136));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 137));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 138));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 139));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 140));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 141));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 142));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 143));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 144));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 145));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 146));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 147));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 148));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 149));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 150));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 151));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 152));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 153));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 154));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 155));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 156));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 157));
		knownSolution.addGene(new WordGene(new Word(new WordId("x", '*')), knownSolution, 158));
		knownSolution.addGene(new WordGene(new Word(new WordId("p", '*')), knownSolution, 159));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 160));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 161));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 162));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 163));
		knownSolution.addGene(new WordGene(new Word(new WordId("c", '*')), knownSolution, 164));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 165));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 166));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 167));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 168));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 169));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 170));
		knownSolution.addGene(new WordGene(new Word(new WordId("v", '*')), knownSolution, 171));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 172));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 173));
		knownSolution.addGene(new WordGene(new Word(new WordId("b", '*')), knownSolution, 174));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 175));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 176));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 177));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 178));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 179));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 180));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 181));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 182));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 183));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 184));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 185));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 186));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 187));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 188));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 189));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 190));
		knownSolution.addGene(new WordGene(new Word(new WordId("y", '*')), knownSolution, 191));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 192));
		knownSolution.addGene(new WordGene(new Word(new WordId("u", '*')), knownSolution, 193));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 194));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 195));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 196));
		knownSolution.addGene(new WordGene(new Word(new WordId("c", '*')), knownSolution, 197));
		knownSolution.addGene(new WordGene(new Word(new WordId("k", '*')), knownSolution, 198));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 199));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 200));
		knownSolution.addGene(new WordGene(new Word(new WordId("f", '*')), knownSolution, 201));
		knownSolution.addGene(new WordGene(new Word(new WordId("f", '*')), knownSolution, 202));
		knownSolution.addGene(new WordGene(new Word(new WordId("w", '*')), knownSolution, 203));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 204));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 205));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 206));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 207));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 208));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 209));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 210));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 211));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 212));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 213));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 214));
		knownSolution.addGene(new WordGene(new Word(new WordId("b", '*')), knownSolution, 215));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 216));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 217));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 218));
		knownSolution.addGene(new WordGene(new Word(new WordId("p", '*')), knownSolution, 219));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 220));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 221));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 222));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 223));
		knownSolution.addGene(new WordGene(new Word(new WordId("f", '*')), knownSolution, 224));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 225));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 226));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 227));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 228));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 229));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 230));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 231));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 232));
		knownSolution.addGene(new WordGene(new Word(new WordId("w", '*')), knownSolution, 233));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 234));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 235));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 236));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 237));
		knownSolution.addGene(new WordGene(new Word(new WordId("d", '*')), knownSolution, 238));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 239));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 240));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 241));
		knownSolution.addGene(new WordGene(new Word(new WordId("w", '*')), knownSolution, 242));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 243));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 244));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 245));
		knownSolution.addGene(new WordGene(new Word(new WordId("b", '*')), knownSolution, 246));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 247));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 248));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 249));
		knownSolution.addGene(new WordGene(new Word(new WordId("b", '*')), knownSolution, 250));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 251));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 252));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 253));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 254));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 255));
		knownSolution.addGene(new WordGene(new Word(new WordId("p", '*')), knownSolution, 256));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 257));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 258));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 259));
		knownSolution.addGene(new WordGene(new Word(new WordId("d", '*')), knownSolution, 260));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 261));
		knownSolution.addGene(new WordGene(new Word(new WordId("c", '*')), knownSolution, 262));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 263));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 264));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 265));
		knownSolution.addGene(new WordGene(new Word(new WordId("d", '*')), knownSolution, 266));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 267));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 268));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 269));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 270));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 271));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 272));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 273));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 274));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 275));
		knownSolution.addGene(new WordGene(new Word(new WordId("v", '*')), knownSolution, 276));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 277));
		knownSolution.addGene(new WordGene(new Word(new WordId("k", '*')), knownSolution, 278));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 279));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 280));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 281));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 282));
		knownSolution.addGene(new WordGene(new Word(new WordId("d", '*')), knownSolution, 283));
		knownSolution.addGene(new WordGene(new Word(new WordId("w", '*')), knownSolution, 284));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 285));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 286));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 287));
		knownSolution.addGene(new WordGene(new Word(new WordId("b", '*')), knownSolution, 288));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 289));
		knownSolution.addGene(new WordGene(new Word(new WordId("c", '*')), knownSolution, 290));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 291));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 292));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 293));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 294));
		knownSolution.addGene(new WordGene(new Word(new WordId("y", '*')), knownSolution, 295));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 296));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 297));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 298));
		knownSolution.addGene(new WordGene(new Word(new WordId("v", '*')), knownSolution, 299));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 300));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 301));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 302));
		knownSolution.addGene(new WordGene(new Word(new WordId("w", '*')), knownSolution, 303));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 304));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 305));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 306));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 307));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 308));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 309));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 310));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 311));
		knownSolution.addGene(new WordGene(new Word(new WordId("v", '*')), knownSolution, 312));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 313));
		knownSolution.addGene(new WordGene(new Word(new WordId("y", '*')), knownSolution, 314));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 315));
		knownSolution.addGene(new WordGene(new Word(new WordId("u", '*')), knownSolution, 316));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 317));
		knownSolution.addGene(new WordGene(new Word(new WordId("y", '*')), knownSolution, 318));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 319));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 320));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 321));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 322));
		knownSolution.addGene(new WordGene(new Word(new WordId("b", '*')), knownSolution, 323));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 324));
		knownSolution.addGene(new WordGene(new Word(new WordId("c", '*')), knownSolution, 325));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 326));
		knownSolution.addGene(new WordGene(new Word(new WordId("u", '*')), knownSolution, 327));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 328));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 329));
		knownSolution.addGene(new WordGene(new Word(new WordId("y", '*')), knownSolution, 330));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 331));
		knownSolution.addGene(new WordGene(new Word(new WordId("u", '*')), knownSolution, 332));
		knownSolution.addGene(new WordGene(new Word(new WordId("w", '*')), knownSolution, 333));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 334));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 335));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 336));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 337));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 338));
		knownSolution.addGene(new WordGene(new Word(new WordId("y", '*')), knownSolution, 339));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 340));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 341));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 342));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 343));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 344));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 345));
		knownSolution.addGene(new WordGene(new Word(new WordId("d", '*')), knownSolution, 346));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 347));
		knownSolution.addGene(new WordGene(new Word(new WordId("w", '*')), knownSolution, 348));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 349));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 350));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 351));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 352));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 353));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 354));
		knownSolution.addGene(new WordGene(new Word(new WordId("p", '*')), knownSolution, 355));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 356));
		knownSolution.addGene(new WordGene(new Word(new WordId("y", '*')), knownSolution, 357));
		knownSolution.addGene(new WordGene(new Word(new WordId("c", '*')), knownSolution, 358));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 359));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 360));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 361));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 362));
		knownSolution.addGene(new WordGene(new Word(new WordId("c", '*')), knownSolution, 363));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 364));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 365));
		knownSolution.addGene(new WordGene(new Word(new WordId("n", '*')), knownSolution, 366));
		knownSolution.addGene(new WordGene(new Word(new WordId("g", '*')), knownSolution, 367));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 368));
		knownSolution.addGene(new WordGene(new Word(new WordId("f", '*')), knownSolution, 369));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 370));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 371));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 372));
		knownSolution.addGene(new WordGene(new Word(new WordId("v", '*')), knownSolution, 373));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 374));
		knownSolution.addGene(new WordGene(new Word(new WordId("s", '*')), knownSolution, 375));
		knownSolution.addGene(new WordGene(new Word(new WordId("f", '*')), knownSolution, 376));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 377));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 378));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 379));
		knownSolution.addGene(new WordGene(new Word(new WordId("y", '*')), knownSolution, 380));
		knownSolution.addGene(new WordGene(new Word(new WordId("a", '*')), knownSolution, 381));
		knownSolution.addGene(new WordGene(new Word(new WordId("f", '*')), knownSolution, 382));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 383));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 384));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 385));
		knownSolution.addGene(new WordGene(new Word(new WordId("l", '*')), knownSolution, 386));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 387));
		knownSolution.addGene(new WordGene(new Word(new WordId("f", '*')), knownSolution, 388));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 389));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 390));
		knownSolution.addGene(new WordGene(new Word(new WordId("b", '*')), knownSolution, 391));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 392));
		knownSolution.addGene(new WordGene(new Word(new WordId("o", '*')), knownSolution, 393));
		knownSolution.addGene(new WordGene(new Word(new WordId("r", '*')), knownSolution, 394));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 395));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 396));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 397));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 398));
		knownSolution.addGene(new WordGene(new Word(new WordId("m", '*')), knownSolution, 399));
		knownSolution.addGene(new WordGene(new Word(new WordId("e", '*')), knownSolution, 400));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 401));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 402));
		knownSolution.addGene(new WordGene(new Word(new WordId("h", '*')), knownSolution, 403));
		knownSolution.addGene(new WordGene(new Word(new WordId("p", '*')), knownSolution, 404));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 405));
		knownSolution.addGene(new WordGene(new Word(new WordId("t", '*')), knownSolution, 406));
		knownSolution.addGene(new WordGene(new Word(new WordId("i", '*')), knownSolution, 407));
	}
}
