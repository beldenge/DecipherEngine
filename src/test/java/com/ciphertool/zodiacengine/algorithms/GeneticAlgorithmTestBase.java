package com.ciphertool.zodiacengine.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.zodiacengine.GenericTestBase;
import com.ciphertool.zodiacengine.entities.PlaintextSequence;
import com.ciphertool.zodiacengine.entities.SolutionChromosome;
import com.ciphertool.zodiacengine.entities.WordGene;

public class GeneticAlgorithmTestBase extends GenericTestBase {
	protected static SolutionChromosome knownSolution = new SolutionChromosome();

	static {
		knownSolution.setCipherId(zodiac408.getId());

		knownSolution.setRows(24);
		knownSolution.setColumns(17);

		List<PlaintextSequence> plaintextCharacters = new ArrayList<PlaintextSequence>();
		/*
		 * PartOfSpeech is not used by the evaluator, so set it to something
		 * arbitrary
		 */
		WordGene nextWordGene;

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 0);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(0, "i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("like", 'X')), knownSolution, 1);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(1, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(2, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(3, "k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(4, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killing", 'X')), knownSolution, 5);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(5, "k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(6, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(7, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(8, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(9, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(10, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(11, "g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("people", 'X')), knownSolution, 12);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(12, "p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(13, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(14, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(15, "p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(16, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(17, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution, 18);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(18, "b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(19, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(20, "c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(21, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(22, "u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(23, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(24, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 25);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(25, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(26, "t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 27);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(27, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(28, "s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("so", 'X')), knownSolution, 29);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(29, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(30, "o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("much", 'X')), knownSolution, 31);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(31, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(32, "u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(33, "c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(34, "h", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("fun", 'X')), knownSolution, 35);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(35, "f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(36, "u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(37, "n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 38);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(38, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(39, "t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 40);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(40, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(41, "s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("more", 'X')), knownSolution, 42);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(42, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(43, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(44, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(45, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("fun", 'X')), knownSolution, 46);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(46, "f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(47, "u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(48, "n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("than", 'X')), knownSolution, 49);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(49, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(50, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(51, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(52, "n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killing", 'X')), knownSolution, 53);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(53, "k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(54, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(55, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(56, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(57, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(58, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(59, "g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("wild", 'X')), knownSolution, 60);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(60, "w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(61, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(62, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(63, "d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("game", 'X')), knownSolution, 64);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(64, "g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(65, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(66, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(67, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("in", 'X')), knownSolution, 68);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(68, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(69, "n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 70);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(70, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(71, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(72, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("forrest", 'X')), knownSolution, 73);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(73, "f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(74, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(75, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(76, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(77, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(78, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(79, "t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution, 80);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(80, "b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(81, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(82, "c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(83, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(84, "u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(85, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(86, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("man", 'X')), knownSolution, 87);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(87, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(88, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(89, "n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 90);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(90, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(91, "s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 92);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(92, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(93, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(94, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("moat", 'X')), knownSolution, 95);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(95, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(96, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(97, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(98, "t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("dangeroue", 'X')), knownSolution, 99);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(99, "d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(100, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(101, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(102, "g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(103, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(104, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(105, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(106, "u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(107, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("anamal", 'X')), knownSolution, 108);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(108, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(109, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(110, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(111, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(112, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(113, "l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution, 114);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(114, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(115, "f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("all", 'X')), knownSolution, 116);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(116, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(117, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(118, "l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("to", 'X')), knownSolution, 119);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(119, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(120, "o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("kill", 'X')), knownSolution, 121);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(121, "k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(122, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(123, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(124, "l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("something", 'X')), knownSolution, 125);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(125, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(126, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(127, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(128, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(129, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(130, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(131, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(132, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(133, "g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("gives", 'X')), knownSolution, 134);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(134, "g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(135, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(136, "v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(137, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(138, "s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("me", 'X')), knownSolution, 139);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(139, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(140, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 141);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(141, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(142, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(143, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("moat", 'X')), knownSolution, 144);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(144, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(145, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(146, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(147, "t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("thrilling", 'X')), knownSolution, 148);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(148, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(149, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(150, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(151, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(152, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(153, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(154, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(155, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(156, "g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("experence", 'X')), knownSolution, 157);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(157, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(158, "x", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(159, "p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(160, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(161, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(162, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(163, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(164, "c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(165, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 166);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(166, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(167, "t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", 'X')), knownSolution, 168);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(168, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(169, "s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("even", 'X')), knownSolution, 170);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(170, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(171, "v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(172, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(173, "n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("better", 'X')), knownSolution, 174);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(174, "b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(175, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(176, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(177, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(178, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(179, "r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("than", 'X')), knownSolution, 180);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(180, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(181, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(182, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(183, "n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("getting", 'X')), knownSolution, 184);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(184, "g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(185, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(186, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(187, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(188, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(189, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(190, "g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("your", 'X')), knownSolution, 191);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(191, "y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(192, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(193, "u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(194, "r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("rocks", 'X')), knownSolution, 195);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(195, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(196, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(197, "c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(198, "k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(199, "s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("off", 'X')), knownSolution, 200);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(200, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(201, "f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(202, "f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("with", 'X')), knownSolution, 203);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(203, "w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(204, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(205, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(206, "h", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("a", 'X')), knownSolution, 207);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(207, "a", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("girl", 'X')), knownSolution, 208);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(208, "g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(209, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(210, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(211, "l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 212);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(212, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(213, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(214, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("best", 'X')), knownSolution, 215);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(215, "b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(216, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(217, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(218, "t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("part", 'X')), knownSolution, 219);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(219, "p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(220, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(221, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(222, "t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution, 223);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(223, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(224, "f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", 'X')), knownSolution, 225);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(225, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(226, "t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("ia", 'X')), knownSolution, 227);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(227, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(228, "a", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("thae", 'X')), knownSolution, 229);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(229, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(230, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(231, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(232, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("when", 'X')), knownSolution, 233);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(233, "w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(234, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(235, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(236, "n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 237);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(237, "i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("die", 'X')), knownSolution, 238);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(238, "d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(239, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(240, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 241);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(241, "i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 242);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(242, "w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(243, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(244, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(245, "l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("be", 'X')), knownSolution, 246);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(246, "b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(247, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("reborn", 'X')), knownSolution, 248);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(248, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(249, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(250, "b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(251, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(252, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(253, "n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("in", 'X')), knownSolution, 254);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(254, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(255, "n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("paradice", 'X')), knownSolution, 256);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(256, "p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(257, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(258, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(259, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(260, "d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(261, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(262, "c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(263, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("snd", 'X')), knownSolution, 264);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(264, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(265, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(266, "d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("all", 'X')), knownSolution, 267);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(267, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(268, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(269, "l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", 'X')), knownSolution, 270);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(270, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(271, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(272, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 273);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(273, "i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("have", 'X')), knownSolution, 274);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(274, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(275, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(276, "v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(277, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killed", 'X')), knownSolution, 278);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(278, "k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(279, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(280, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(281, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(282, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(283, "d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 284);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(284, "w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(285, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(286, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(287, "l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("become", 'X')), knownSolution, 288);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(288, "b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(289, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(290, "c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(291, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(292, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(293, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 294);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(294, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(295, "y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("slaves", 'X')), knownSolution, 296);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(296, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(297, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(298, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(299, "v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(300, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(301, "s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", 'X')), knownSolution, 302);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(302, "i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 303);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(303, "w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(304, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(305, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(306, "l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("not", 'X')), knownSolution, 307);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(307, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(308, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(309, "t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("give", 'X')), knownSolution, 310);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(310, "g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(311, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(312, "v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(313, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("you", 'X')), knownSolution, 314);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(314, "y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(315, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(316, "u", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 317);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(317, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(318, "y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("name", 'X')), knownSolution, 319);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(319, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(320, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(321, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(322, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", 'X')), knownSolution, 323);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(323, "b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(324, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(325, "c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(326, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(327, "u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(328, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(329, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("you", 'X')), knownSolution, 330);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(330, "y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(331, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(332, "u", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", 'X')), knownSolution, 333);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(333, "w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(334, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(335, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(336, "l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("try", 'X')), knownSolution, 337);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(337, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(338, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(339, "y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("to", 'X')), knownSolution, 340);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(340, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(341, "o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("sloi", 'X')), knownSolution, 342);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(342, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(343, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(344, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(345, "i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("down", 'X')), knownSolution, 346);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(346, "d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(347, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(348, "w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(349, "n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("or", 'X')), knownSolution, 350);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(350, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(351, "r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("stop", 'X')), knownSolution, 352);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(352, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(353, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(354, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(355, "p", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 356);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(356, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(357, "y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("collecting", 'X')), knownSolution, 358);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(358, "c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(359, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(360, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(361, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(362, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(363, "c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(364, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(365, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(366, "n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(367, "g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", 'X')), knownSolution, 368);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(368, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(369, "f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("slaves", 'X')), knownSolution, 370);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(370, "s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(371, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(372, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(373, "v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(374, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(375, "s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("for", 'X')), knownSolution, 376);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(376, "f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(377, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(378, "r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", 'X')), knownSolution, 379);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(379, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(380, "y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("afterlife", 'X')), knownSolution, 381);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(381, "a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(382, "f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(383, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(384, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(385, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(386, "l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(387, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(388, "f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(389, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("e", 'X')), knownSolution, 390);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(390, "e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("beorietemethhpiti", 'X')), knownSolution,
				391);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence(391, "b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(392, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(393, "o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(394, "r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(395, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(396, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(397, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(398, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(399, "m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(400, "e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(401, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(402, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(403, "h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(404, "p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(405, "i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(406, "t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence(407, "i", nextWordGene));
	}
}
