package com.ciphertool.zodiacengine;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.entities.Ciphertext;
import com.ciphertool.zodiacengine.entities.CiphertextId;

public class GenericTestBase {
	protected static Cipher zodiac408 = new Cipher("zodiac408", 24, 17);

	static {
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 0), "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 1), "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 2), "p"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 3), "forslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 4), "z"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 5), "forslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 6), "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 7), "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 8), "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 9), "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 10), "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 11), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 12), "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 13), "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 14), "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 15), "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 16), "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 17), "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 18), "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 19), "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 20), "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 21), "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 22), "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 23), "f"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 24),
				"circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 25), "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 26), "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 27), "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 28), "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 29), "k"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 30), "anchor"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 31), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 32), "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 33), "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 34), "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 35), "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 36), "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 37), "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 38), "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 39), "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 40), "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 41), "tridot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 42), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 43), "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 44), "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 45), "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 46), "q"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 47), "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 48), "d"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 49),
				"fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 50),
				"horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 51), "s"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 52),
				"vertstrike"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 53), "forslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 54), "tri"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 55), "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 56), "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 57), "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 58), "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 59), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 60), "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 61), "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 62), "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 63), "backf"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 64), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 65), "backl"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 66), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 67), "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 68), "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 69), "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 70), "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 71), "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 72), "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 73), "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 74), "backd"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 75), "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 76),
				"backslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 77), "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 78), "f"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 79), "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 80), "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 81), "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 82), "backe"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 83), "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 84), "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 85), "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 86), "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 87), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 88), "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 89), "d"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 90), "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 91), "k"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 92), "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 93),
				"horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 94),
				"circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 95), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 96), "x"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 97), "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 98),
				"fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 99), "zodiac"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 100), "s"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 101),
				"vertstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 102), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 103), "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 104), "flipt"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 105), "anchor"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 106), "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 107), "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 108), "backl"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 109), "o"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 110), "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 111), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 112), "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 113), "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 114), "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 115), "q"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 116), "s"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 117), "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 118), "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 119), "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 120), "backd"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 121),
				"forslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 122), "p"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 123), "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 124), "b"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 125), "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 126), "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 127), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 128), "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 129), "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 130), "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 131), "u"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 132), "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 133), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 134), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 135), "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 136), "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 137), "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 138), "k"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 139), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 140), "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 141), "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 142),
				"horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 143), "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 144), "backq"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 145), "anchor"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 146), "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 147),
				"fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 148), "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 149), "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 150), "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 151), "tri"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 152), "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 153), "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 154), "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 155), "d"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 156), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 157), "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 158), "backj"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 159), "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 160),
				"circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 161),
				"backslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 162), "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 163),
				"vertstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 164), "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 165), "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 166), "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 167), "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 168), "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 169), "f"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 170), "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 171), "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 172), "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 173), "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 174), "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 175), "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 176), "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 177),
				"fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 178), "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 179), "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 180), "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 181),
				"horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 182), "backl"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 183), "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 184), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 185),
				"circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 186), "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 187), "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 188), "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 189), "d"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 190), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 191), "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 192), "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 193), "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 194), "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 195),
				"backslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 196), "backd"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 197), "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 198),
				"forslash"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 199), "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 200), "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 201), "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 202), "q"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 203), "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 204), "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 205),
				"fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 206), "m"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 207), "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 208), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 209), "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 210), "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 211), "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 212), "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 213),
				"horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 214), "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 215), "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 216), "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 217), "k"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 218), "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 219), "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 220), "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 221), "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 222), "i"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 223), "anchor"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 224), "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 225), "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 226),
				"fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 227), "tri"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 228), "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 229), "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 230), "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 231), "backl"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 232), "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 233), "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 234),
				"horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 235), "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 236),
				"vertstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 237), "p"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 238), "zodiac"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 239), "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 240), "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 241), "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 242), "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 243), "tri"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 244), "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 245), "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 246), "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 247), "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 248),
				"backslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 249), "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 250), "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 251), "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 252), "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 253), "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 254), "p"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 255), "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 256), "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 257), "s"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 258), "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 259), "backl"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 260), "backf"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 261), "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 262), "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 263),
				"circledot"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 264), "tridot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 265), "d"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 266), "zodiac"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 267), "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 268), "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 269), "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 270), "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 271), "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 272), "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 273), "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 274),
				"horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 275), "s"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 276), "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 277), "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 278),
				"forslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 279), "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 280), "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 281), "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 282), "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 283), "backf"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 284), "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 285), "p"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 286), "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 287), "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 288), "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 289), "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 290), "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 291), "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 292), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 293), "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 294), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 295), "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 296), "f"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 297), "fullbox"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 298), "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 299), "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 300), "plus"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 301), "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 302), "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 303), "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 304), "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 305), "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 306), "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 307), "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 308), "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 309),
				"fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 310), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 311), "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 312), "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 313), "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 314), "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 315), "backd"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 316), "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 317), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 318), "box"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 319), "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 320), "s"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 321), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 322), "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 323), "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 324), "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 325), "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 326), "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 327), "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 328), "k"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 329), "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 330), "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 331), "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 332), "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 333), "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 334), "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 335), "lrbox"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 336), "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 337), "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 338), "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 339), "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 340), "h"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 341), "anchor"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 342), "f"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 343), "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 344), "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 345), "tri"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 346), "zodiac"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 347), "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 348), "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 349), "d"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 350), "backd"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 351),
				"backslash"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 352), "tridot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 353), "l"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 354), "anchor"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 355), "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 356), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 357), "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 358), "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 359), "backd"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 360), "fullbox"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 361), "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 362),
				"circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 363), "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 364),
				"fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 365), "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 366), "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 367), "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 368), "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 369), "q"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 370), "f"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 371), "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 372), "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 373), "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 374), "z"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 375), "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 376), "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 377), "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 378), "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 379), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 380), "box"));
		zodiac408
				.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 381), "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 382), "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 383), "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 384), "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 385), "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 386), "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 387), "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 388), "q"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 389), "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 390),
				"circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 391), "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 392), "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 393), "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 394), "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 395), "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 396), "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 397), "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 398),
				"circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 399), "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 400), "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 401), "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 402), "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 403),
				"horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 404), "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 405), "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 406), "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(new CiphertextId(zodiac408, 407), "backk"));
	}

	/**
	 * Utility method for setting private properties on real and mock objects
	 * 
	 * @param instance
	 *            the object instance
	 * @param c
	 *            the type of the object
	 * @param field
	 *            the field to set
	 * @param val
	 *            the value to set the field to
	 */
	protected static void setObject(final Object instance, final String field, final Object val) {
		try {
			Field f = instance.getClass().getDeclaredField(field);
			f.setAccessible(true); // in case the property is private
			f.set(instance, val);
		} catch (NoSuchFieldException e) {
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Utility method for invoking private methods on real and mock objects
	 * 
	 * @param instance
	 *            the object instance
	 * @param method
	 *            the method to invoke
	 * @param params
	 *            the method parameters
	 * @param args
	 *            the method arguments
	 * @throws InvocationTargetException
	 *             if an exception is thrown within the called method
	 */
	protected static Object invokeMethod(final Object instance, final String method,
			final Class<?>[] params, final Object[] args) throws InvocationTargetException {
		try {
			Method m;

			try {
				m = instance.getClass().getDeclaredMethod(method, params);
			} catch (NoSuchMethodException e) {
				m = instance.getClass().getMethod(method, params);
			}

			m.setAccessible(true); // in case the method is private
			return m.invoke(instance, args);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (IllegalAccessException e) {
			fail(e.getMessage());
		} catch (NoSuchMethodException e) {
			fail(e.getMessage());
		}

		return null;
	}
}
