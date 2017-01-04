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

package com.ciphertool.engine.common;

import java.util.List;
import java.util.Map;

import com.ciphertool.engine.bayes.CipherSolution;
import com.ciphertool.engine.entities.CipherKeyChromosome;
import com.ciphertool.engine.entities.CipherKeyGene;
import com.ciphertool.engine.entities.Ciphertext;
import com.ciphertool.sherlock.markov.NGramIndexNode;
import com.ciphertool.sherlock.markov.TerminalInfo;
import com.ciphertool.sherlock.wordgraph.Match;
import com.ciphertool.sherlock.wordgraph.MatchNode;

public class WordGraphUtils {
	public static void populateMap(NGramIndexNode currentNode, String wordPart) {
		Character firstLetter = wordPart.charAt(0);

		if (wordPart.length() == 1) {
			if (currentNode.containsChild(firstLetter)) {
				currentNode.getChild(firstLetter).setTerminalInfo(new TerminalInfo());
			} else {
				currentNode.putChild(firstLetter, new NGramIndexNode(currentNode, new TerminalInfo()));
			}
		} else {
			if (!currentNode.containsChild(firstLetter)) {
				currentNode.putChild(firstLetter, new NGramIndexNode(currentNode));
			}

			populateMap(currentNode.getChild(firstLetter), wordPart.substring(1));
		}
	}

	public static String findLongestWordMatch(NGramIndexNode node, int index, String solutionString, String longestMatch) {
		if (node == null || index >= solutionString.length()) {
			return longestMatch;
		}

		Character currentChar = solutionString.charAt(index);

		if (node.getTerminalInfo() != null) {
			longestMatch = solutionString.substring(0, index);
		}

		return findLongestWordMatch(node.getChild(currentChar), ++index, solutionString, longestMatch);
	}

	public static void findOverlappingChildren(int beginPos, int endPos, Map<Integer, Match> matchMap, MatchNode currentNode) {
		MatchNode newNode;

		for (int i = beginPos; i < endPos; i++) {
			if (i <= currentNode.getSelf().getEndPos()) {
				i = currentNode.getSelf().getEndPos() + 1;
			}

			if (matchMap.containsKey(i)) {
				newNode = new MatchNode(matchMap.get(i));
				currentNode.addChild(newNode);
				currentNode = newNode;
			}
		}
	}

	public static boolean nonOverlapping(int beginPos, List<MatchNode> rootNodes) {
		for (MatchNode node : rootNodes) {
			if (beginPos > node.getSelf().getEndPos()) {
				return true;
			}
		}

		return false;
	}

	public static String getSolutionAsString(CipherKeyChromosome chromosome) {
		StringBuilder sb = new StringBuilder();

		if (null == ((CipherKeyChromosome) chromosome).getCipher()) {
			throw new IllegalStateException(
					"Called getSolutionAsString(), but found a null Cipher.  Cannot create valid solution string unless the Cipher is properly set.");
		}

		CipherKeyGene nextPlaintext = null;

		for (Ciphertext ciphertext : ((CipherKeyChromosome) chromosome).getCipher().getCiphertextCharacters()) {
			nextPlaintext = (CipherKeyGene) chromosome.getGenes().get(ciphertext.getValue());

			sb.append(nextPlaintext.getValue());
		}

		return sb.toString();
	}

	public static String getSolutionAsString(CipherSolution cipherSolution) {
		StringBuilder sb = new StringBuilder();

		if (null == cipherSolution.getCipher()) {
			throw new IllegalStateException(
					"Called getSolutionAsString(), but found a null Cipher.  Cannot create valid solution string unless the Cipher is properly set.");
		}

		String nextPlaintext = null;

		for (Ciphertext ciphertext : cipherSolution.getCipher().getCiphertextCharacters()) {
			nextPlaintext = cipherSolution.getMappings().get(ciphertext.getValue());

			sb.append(nextPlaintext);
		}

		return sb.toString();
	}
}
