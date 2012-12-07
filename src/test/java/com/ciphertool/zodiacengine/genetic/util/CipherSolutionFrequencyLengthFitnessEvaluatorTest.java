package com.ciphertool.zodiacengine.genetic.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.CiphertextId;
import com.ciphertool.zodiacengine.entities.PlaintextId;
import com.ciphertool.zodiacengine.genetic.adapters.PlaintextSequence;
import com.ciphertool.zodiacengine.genetic.adapters.SolutionChromosome;
import com.ciphertool.zodiacengine.genetic.adapters.WordGene;

public class CipherSolutionFrequencyLengthFitnessEvaluatorTest {
	private static Logger log = Logger
			.getLogger(CipherSolutionFrequencyLengthFitnessEvaluatorTest.class);

	private static CipherSolutionFrequencyLengthFitnessEvaluator fitnessEvaluator = new CipherSolutionFrequencyLengthFitnessEvaluator();
	private static SolutionChromosome knownSolution = new SolutionChromosome();

	@BeforeClass
	public static void setUp() {
		Map<Character, Double> expectedLetterFrequencies = new HashMap<Character, Double>();
		expectedLetterFrequencies.put('a', 0.0812);
		expectedLetterFrequencies.put('b', 0.0149);
		expectedLetterFrequencies.put('c', 0.0271);
		expectedLetterFrequencies.put('d', 0.0432);
		expectedLetterFrequencies.put('e', 0.1202);
		expectedLetterFrequencies.put('f', 0.0230);
		expectedLetterFrequencies.put('g', 0.0203);
		expectedLetterFrequencies.put('h', 0.0592);
		expectedLetterFrequencies.put('i', 0.0731);
		expectedLetterFrequencies.put('j', 0.0010);
		expectedLetterFrequencies.put('k', 0.0069);
		expectedLetterFrequencies.put('l', 0.0398);
		expectedLetterFrequencies.put('m', 0.0261);
		expectedLetterFrequencies.put('n', 0.0695);
		expectedLetterFrequencies.put('o', 0.0768);
		expectedLetterFrequencies.put('p', 0.0182);
		expectedLetterFrequencies.put('q', 0.0011);
		expectedLetterFrequencies.put('r', 0.0602);
		expectedLetterFrequencies.put('s', 0.0628);
		expectedLetterFrequencies.put('t', 0.0910);
		expectedLetterFrequencies.put('u', 0.0288);
		expectedLetterFrequencies.put('v', 0.0111);
		expectedLetterFrequencies.put('w', 0.0209);
		expectedLetterFrequencies.put('x', 0.0017);
		expectedLetterFrequencies.put('y', 0.0211);
		expectedLetterFrequencies.put('z', 0.0007);
		fitnessEvaluator.setExpectedLetterFrequencies(expectedLetterFrequencies);

		Double averageWordLength = 5.1;
		fitnessEvaluator.setAverageWordLength(averageWordLength);

		Cipher zodiac408 = new Cipher("zodiac408", 24, 17);
		List<Ciphertext> ciphertextCharacters = new ArrayList<Ciphertext>();
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 0), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 1), "lrbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 2), "p"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 3), "forslash"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 4), "z"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 5), "forslash"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 6), "u"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 7), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 8), "lrbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 9), "backk"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 10), "o"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 11), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 12), "pi"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 13), "backp"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 14), "x"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 15), "pi"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 16), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 17), "w"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 18), "v"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 19), "plus"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 20), "backe"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 21), "g"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 22), "y"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 23), "f"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 24), "circledot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 25), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 26), "h"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 27), "p"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 28), "boxdot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 29), "k"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 30), "anchor"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 31), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 32), "y"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 33), "backe"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 34), "m"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 35), "j"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 36), "y"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 37), "carrot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 38), "u"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 39), "i"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 40), "backk"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 41), "tridot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 42), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 43), "t"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 44), "flipt"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 45), "n"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 46), "q"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 47), "y"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 48), "d"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 49), "fullcircle"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 50), "horstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 51), "s"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 52), "vertstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 53), "forslash"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 54), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 55), "fullbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 56), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 57), "p"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 58), "o"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 59), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 60), "a"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 61), "u"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 62), "lrbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 63), "backf"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 64), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 65), "backl"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 66), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 67), "e"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 68), "backk"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 69), "carrot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 70), "l"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 71), "m"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 72), "z"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 73), "j"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 74), "backd"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 75), "backr"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 76), "backslash"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 77), "backp"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 78), "f"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 79), "h"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 80), "v"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 81), "w"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 82), "backe"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 83), "fulltri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 84), "y"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 85), "boxdot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 86), "plus"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 87), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 88), "g"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 89), "d"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 90), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 91), "k"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 92), "i"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 93), "horstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 94), "circledot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 95), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 96), "x"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 97), "fulltri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 98), "fullcircle"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 99), "zodiac"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 100), "s"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 101), "vertstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 102), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 103), "n"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 104), "flipt"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 105), "anchor"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 106), "y"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 107), "e"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 108), "backl"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 109), "o"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 110), "fulltri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 111), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 112), "g"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 113), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 114), "t"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 115), "q"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 116), "s"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 117), "fullbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 118), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 119), "l"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 120), "backd"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 121), "forslash"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 122), "p"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 123), "fullbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 124), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 125), "boxdot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 126), "x"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 127), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 128), "e"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 129), "h"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 130), "m"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 131), "u"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 132), "carrot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 133), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 134), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 135), "backk"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 136), "backc"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 137), "z"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 138), "k"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 139), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 140), "backp"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 141), "i"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 142), "horstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 143), "w"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 144), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 145), "anchor"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 146), "fulltri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 147), "fullcircle"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 148), "l"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 149), "m"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 150), "backr"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 151), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 152), "fullbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 153), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 154), "p"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 155), "d"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 156), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 157), "plus"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 158), "backj"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 159), "pi"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 160), "circledot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 161), "backslash"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 162), "n"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 163), "vertstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 164), "backe"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 165), "e"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 166), "u"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 167), "h"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 168), "backk"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 169), "f"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 170), "z"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 171), "backc"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 172), "backp"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 173), "o"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 174), "v"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 175), "w"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 176), "i"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 177), "fullcircle"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 178), "plus"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 179), "flipt"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 180), "l"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 181), "horstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 182), "backl"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 183), "carrot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 184), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 185), "circledot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 186), "h"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 187), "i"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 188), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 189), "d"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 190), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 191), "box"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 192), "t"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 193), "y"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 194), "backr"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 195), "backslash"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 196), "backd"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 197), "backe"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 198), "forslash"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 199), "boxdot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 200), "x"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 201), "j"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 202), "q"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 203), "a"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 204), "p"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 205), "fullcircle"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 206), "m"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 207), "fulltri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 208), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 209), "u"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 210), "flipt"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 211), "lrbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 212), "l"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 213), "horstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 214), "n"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 215), "v"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 216), "e"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 217), "k"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 218), "h"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 219), "pi"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 220), "g"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 221), "backr"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 222), "i"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 223), "anchor"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 224), "j"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 225), "backk"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 226), "fullcircle"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 227), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 228), "fulltri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 229), "l"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 230), "m"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 231), "backl"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 232), "n"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 233), "a"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 234), "horstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 235), "z"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 236), "vertstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 237), "p"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 238), "zodiac"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 239), "u"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 240), "backp"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 241), "backk"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 242), "a"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 243), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 244), "fullbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 245), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 246), "v"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 247), "w"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 248), "backslash"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 249), "plus"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 250), "v"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 251), "t"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 252), "flipt"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 253), "o"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 254), "p"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 255), "carrot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 256), "pi"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 257), "s"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 258), "backr"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 259), "backl"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 260), "backf"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 261), "u"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 262), "backe"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 263), "circledot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 264), "tridot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 265), "d"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 266), "zodiac"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 267), "g"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 268), "lrbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 269), "lrbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 270), "i"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 271), "m"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 272), "n"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 273), "backk"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 274), "horstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 275), "s"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 276), "backc"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 277), "e"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 278), "forslash"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 279), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 280), "lrbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 281), "lrbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 282), "z"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 283), "backf"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 284), "a"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 285), "p"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 286), "fullbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 287), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 288), "v"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 289), "backp"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 290), "backe"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 291), "x"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 292), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 293), "w"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 294), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 295), "box"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 296), "f"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 297), "fullbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 298), "fulltri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 299), "backc"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 300), "plus"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 301), "boxdot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 302), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 303), "a"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 304), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 305), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 306), "lrbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 307), "o"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 308), "t"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 309), "fullcircle"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 310), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 311), "u"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 312), "backc"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 313), "plus"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 314), "box"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 315), "backd"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 316), "y"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 317), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 318), "box"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 319), "carrot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 320), "s"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 321), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 322), "w"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 323), "v"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 324), "z"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 325), "backe"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 326), "g"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 327), "y"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 328), "k"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 329), "e"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 330), "box"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 331), "t"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 332), "y"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 333), "a"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 334), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 335), "lrbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 336), "fullbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 337), "l"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 338), "flipt"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 339), "box"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 340), "h"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 341), "anchor"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 342), "f"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 343), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 344), "x"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 345), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 346), "zodiac"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 347), "x"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 348), "a"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 349), "d"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 350), "backd"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 351), "backslash"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 352), "tridot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 353), "l"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 354), "anchor"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 355), "pi"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 356), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 357), "box"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 358), "backe"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 359), "backd"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 360), "fullbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 361), "fullbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 362), "circledot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 363), "backe"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 364), "fullcircle"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 365), "p"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 366), "o"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 367), "r"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 368), "x"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 369), "q"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 370), "f"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 371), "lrbox"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 372), "g"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 373), "backc"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 374), "z"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 375), "boxdot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 376), "j"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 377), "t"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 378), "flipt"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 379), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 380), "box"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 381), "fulltri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 382), "j"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 383), "i"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 384), "plus"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 385), "backr"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 386), "b"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 387), "p"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 388), "q"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 389), "w"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 390), "circledot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 391), "v"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 392), "e"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 393), "x"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 394), "backr"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 395), "tri"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 396), "w"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 397), "i"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 398), "circledot"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 399), "backq"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 400), "e"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 401), "h"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 402), "m"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 403), "horstrike"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 404), "pi"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 405), "u"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 406), "i"));
		ciphertextCharacters.add(new Ciphertext(new CiphertextId(zodiac408, 407), "backk"));

		zodiac408.setCiphertextCharacters(ciphertextCharacters);
		fitnessEvaluator.setGeneticStructure(zodiac408);
		knownSolution.setCipher(zodiac408);

		List<PlaintextSequence> plaintextCharacters = new ArrayList<PlaintextSequence>();
		/*
		 * PartOfSpeech is not used by the evaluator, so set it to something
		 * arbitrary
		 */
		WordGene nextWordGene;

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 0);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 0), "i",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("like", 'X')), knownSolution, 1);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 1), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 2), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 3), "k",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 4), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killing", 'X')), knownSolution, 5);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 5), "k",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 6), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 7), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 8), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 9), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 10), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 11), "g",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("people", 'X')), knownSolution, 12);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 12), "p",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 13), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 14), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 15), "p",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 16), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 17), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution, 18);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 18), "b",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 19), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 20), "c",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 21), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 22), "u",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 23), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 24), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 25);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 25), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 26), "t",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 27);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 27), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 28), "s",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("so", 'X')), knownSolution, 29);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 29), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 30), "o",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("much", 'X')), knownSolution, 31);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 31), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 32), "u",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 33), "c",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 34), "h",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("fun", 'X')), knownSolution, 35);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 35), "f",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 36), "u",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 37), "n",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 38);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 38), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 39), "t",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 40);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 40), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 41), "s",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("more", 'X')), knownSolution, 42);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 42), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 43), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 44), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 45), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("fun", 'X')), knownSolution, 46);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 46), "f",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 47), "u",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 48), "n",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("than", 'X')), knownSolution, 49);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 49), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 50), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 51), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 52), "n",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killing", 'X')), knownSolution, 53);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 53), "k",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 54), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 55), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 56), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 57), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 58), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 59), "g",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("wild", 'X')), knownSolution, 60);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 60), "w",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 61), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 62), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 63), "d",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("game", 'X')), knownSolution, 64);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 64), "g",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 65), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 66), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 67), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("in", 'X')), knownSolution, 68);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 68), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 69), "n",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 70);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 70), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 71), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 72), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("forrest", 'X')), knownSolution, 73);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 73), "f",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 74), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 75), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 76), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 77), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 78), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 79), "t",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution, 80);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 80), "b",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 81), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 82), "c",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 83), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 84), "u",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 85), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 86), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("man", 'X')), knownSolution, 87);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 87), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 88), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 89), "n",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 90);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 90), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 91), "s",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 92);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 92), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 93), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 94), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("moat", 'X')), knownSolution, 95);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 95), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 96), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 97), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 98), "t",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("dangeroue", 'X')), knownSolution, 99);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 99), "d",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 100), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 101), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 102), "g",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 103), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 104), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 105), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 106), "u",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 107), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("anamal", 'X')), knownSolution, 108);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 108), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 109), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 110), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 111), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 112), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 113), "l",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution, 114);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 114), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 115), "f",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("all", 'X')), knownSolution, 116);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 116), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 117), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 118), "l",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("to", 'X')), knownSolution, 119);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 119), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 120), "o",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("kill", 'X')), knownSolution, 121);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 121), "k",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 122), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 123), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 124), "l",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("something", 'X')), knownSolution, 125);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 125), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 126), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 127), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 128), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 129), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 130), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 131), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 132), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 133), "g",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("gives", 'X')), knownSolution, 134);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 134), "g",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 135), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 136), "v",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 137), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 138), "s",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("me", 'X')), knownSolution, 139);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 139), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 140), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 141);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 141), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 142), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 143), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("moat", 'X')), knownSolution, 144);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 144), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 145), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 146), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 147), "t",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("thrilling", 'X')), knownSolution, 148);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 148), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 149), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 150), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 151), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 152), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 153), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 154), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 155), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 156), "g",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("experence", 'X')), knownSolution, 157);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 157), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 158), "x",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 159), "p",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 160), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 161), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 162), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 163), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 164), "c",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 165), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 166);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 166), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 167), "t",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 168);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 168), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 169), "s",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("even", 'X')), knownSolution, 170);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 170), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 171), "v",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 172), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 173), "n",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("better", 'X')), knownSolution, 174);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 174), "b",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 175), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 176), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 177), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 178), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 179), "r",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("than", 'X')), knownSolution, 180);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 180), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 181), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 182), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 183), "n",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("getting", 'X')), knownSolution, 184);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 184), "g",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 185), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 186), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 187), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 188), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 189), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 190), "g",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("your", 'X')), knownSolution, 191);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 191), "y",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 192), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 193), "u",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 194), "r",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("rocks", 'X')), knownSolution, 195);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 195), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 196), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 197), "c",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 198), "k",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 199), "s",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("off", 'X')), knownSolution, 200);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 200), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 201), "f",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 202), "f",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("with", 'X')), knownSolution, 203);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 203), "w",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 204), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 205), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 206), "h",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("a", 'X')), knownSolution, 207);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 207), "a",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("girl", 'X')), knownSolution, 208);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 208), "g",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 209), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 210), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 211), "l",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 212);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 212), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 213), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 214), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("best", 'X')), knownSolution, 215);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 215), "b",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 216), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 217), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 218), "t",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("part", 'X')), knownSolution, 219);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 219), "p",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 220), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 221), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 222), "t",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution, 223);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 223), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 224), "f",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 225);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 225), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 226), "t",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("ia", 'X')), knownSolution, 227);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 227), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 228), "s",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("thae", 'X')), knownSolution, 229);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 229), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 230), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 231), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 232), "t",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("when", 'X')), knownSolution, 233);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 233), "w",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 234), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 235), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 236), "n",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 237);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 237), "i",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("die", 'X')), knownSolution, 238);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 238), "d",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 239), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 240), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 241);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 241), "i",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 242);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 242), "w",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 243), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 244), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 245), "l",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("be", 'X')), knownSolution, 246);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 246), "b",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 247), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("reborn", 'X')), knownSolution, 248);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 248), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 249), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 250), "b",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 251), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 252), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 253), "n",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("in", 'X')), knownSolution, 254);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 254), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 255), "n",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("paradice", 'X')), knownSolution, 256);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 256), "p",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 257), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 258), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 259), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 260), "d",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 261), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 262), "c",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 263), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("snd", 'X')), knownSolution, 264);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 264), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 265), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 266), "d",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("all", 'X')), knownSolution, 267);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 267), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 268), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 269), "l",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 270);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 270), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 271), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 272), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 273);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 273), "i",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("have", 'X')), knownSolution, 274);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 274), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 275), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 276), "v",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 277), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killed", 'X')), knownSolution, 278);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 278), "k",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 279), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 280), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 281), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 282), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 283), "d",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 284);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 284), "w",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 285), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 286), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 287), "l",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("become", 'X')), knownSolution, 288);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 288), "b",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 289), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 290), "c",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 291), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 292), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 293), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 294);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 294), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 295), "y",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("slaves", 'X')), knownSolution, 296);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 296), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 297), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 298), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 299), "v",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 300), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 301), "s",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 302);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 302), "i",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 303);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 303), "w",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 304), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 305), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 306), "l",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("not", 'X')), knownSolution, 307);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 307), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 308), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 309), "t",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("give", 'X')), knownSolution, 310);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 310), "g",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 311), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 312), "v",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 313), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("you", 'X')), knownSolution, 314);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 314), "y",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 315), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 316), "u",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 317);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 317), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 318), "y",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("name", 'X')), knownSolution, 319);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 319), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 320), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 321), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 322), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution, 323);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 323), "b",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 324), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 325), "c",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 326), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 327), "u",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 328), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 329), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("you", 'X')), knownSolution, 330);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 330), "y",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 331), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 332), "u",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 333);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 333), "w",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 334), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 335), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 336), "l",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("try", 'X')), knownSolution, 337);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 337), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 338), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 339), "y",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("to", 'X')), knownSolution, 340);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 340), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 341), "o",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("sloi", 'X')), knownSolution, 342);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 342), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 343), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 344), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 345), "i",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("down", 'X')), knownSolution, 346);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 346), "d",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 347), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 348), "w",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 349), "n",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("or", 'X')), knownSolution, 350);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 350), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 351), "r",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("stop", 'X')), knownSolution, 352);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 352), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 353), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 354), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 355), "p",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 356);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 356), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 357), "y",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("collecting", 'X')), knownSolution, 358);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 358), "c",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 359), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 360), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 361), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 362), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 363), "c",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 364), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 365), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 366), "n",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 367), "g",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution, 368);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 368), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 369), "f",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("slaves", 'X')), knownSolution, 370);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 370), "s",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 371), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 372), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 373), "v",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 374), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 375), "s",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("for", 'X')), knownSolution, 376);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 376), "f",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 377), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 378), "r",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 379);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 379), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 380), "y",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("afterlife", 'X')), knownSolution, 381);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 381), "a",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 382), "f",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 383), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 384), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 385), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 386), "l",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 387), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 388), "f",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 389), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("e", 'X')), knownSolution, 390);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 390), "e",
				nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("beorietemethhpiti", 'X')), knownSolution,
				391);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 391), "b",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 392), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 393), "o",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 394), "r",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 395), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 396), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 397), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 398), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 399), "m",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 400), "e",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 401), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 402), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 403), "h",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 404), "p",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 405), "i",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 406), "t",
				nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(new PlaintextId(knownSolution, 407), "i",
				nextWordGene));
	}

	@Test
	public void testEvaluate() {
		Double fitness = fitnessEvaluator.evaluate(knownSolution);
		log.info(knownSolution);
		log.info("Fitness: " + fitness);
	}
}