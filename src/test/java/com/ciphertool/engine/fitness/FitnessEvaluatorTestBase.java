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

package com.ciphertool.engine.fitness;

import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import com.ciphertool.sentencebuilder.entities.Word;
import com.ciphertool.sentencebuilder.entities.WordId;
import com.ciphertool.sentencebuilder.enumerations.PartOfSpeechType;
import com.ciphertool.engine.entities.Cipher;
import com.ciphertool.engine.entities.Ciphertext;
import com.ciphertool.engine.entities.PlaintextSequence;
import com.ciphertool.engine.entities.SolutionChromosome;
import com.ciphertool.engine.entities.WordGene;

public class FitnessEvaluatorTestBase {
	protected static Cipher zodiac408 = new Cipher("zodiac408", 24, 17);
	protected static SolutionChromosome knownSolution = new SolutionChromosome();

	static {
		zodiac408.setId(BigInteger.ZERO);
		zodiac408.setHasKnownSolution(true);

		zodiac408.addCiphertextCharacter(new Ciphertext(0, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(1, "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(2, "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(3, "forslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(4, "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(5, "forslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(6, "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(7, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(8, "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(9, "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(10, "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(11, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(12, "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(13, "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(14, "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(15, "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(16, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(17, "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(18, "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(19, "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(20, "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(21, "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(22, "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(23, "f"));
		zodiac408.addCiphertextCharacter(new Ciphertext(24, "circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(25, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(26, "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(27, "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(28, "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(29, "k"));
		zodiac408.addCiphertextCharacter(new Ciphertext(30, "anchor"));
		zodiac408.addCiphertextCharacter(new Ciphertext(31, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(32, "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(33, "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(34, "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(35, "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(36, "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(37, "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(38, "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(39, "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(40, "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(41, "tridot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(42, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(43, "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(44, "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(45, "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(46, "q"));
		zodiac408.addCiphertextCharacter(new Ciphertext(47, "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(48, "d"));
		zodiac408.addCiphertextCharacter(new Ciphertext(49, "fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(50, "horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(51, "s"));
		zodiac408.addCiphertextCharacter(new Ciphertext(52, "vertstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(53, "forslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(54, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(55, "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(56, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(57, "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(58, "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(59, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(60, "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(61, "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(62, "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(63, "backf"));
		zodiac408.addCiphertextCharacter(new Ciphertext(64, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(65, "backl"));
		zodiac408.addCiphertextCharacter(new Ciphertext(66, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(67, "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(68, "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(69, "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(70, "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(71, "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(72, "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(73, "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(74, "backd"));
		zodiac408.addCiphertextCharacter(new Ciphertext(75, "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(76, "backslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(77, "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(78, "f"));
		zodiac408.addCiphertextCharacter(new Ciphertext(79, "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(80, "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(81, "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(82, "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(83, "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(84, "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(85, "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(86, "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(87, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(88, "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(89, "d"));
		zodiac408.addCiphertextCharacter(new Ciphertext(90, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(91, "k"));
		zodiac408.addCiphertextCharacter(new Ciphertext(92, "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(93, "horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(94, "circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(95, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(96, "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(97, "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(98, "fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(99, "zodiac"));
		zodiac408.addCiphertextCharacter(new Ciphertext(100, "s"));
		zodiac408.addCiphertextCharacter(new Ciphertext(101, "vertstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(102, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(103, "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(104, "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(105, "anchor"));
		zodiac408.addCiphertextCharacter(new Ciphertext(106, "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(107, "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(108, "backl"));
		zodiac408.addCiphertextCharacter(new Ciphertext(109, "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(110, "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(111, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(112, "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(113, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(114, "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(115, "q"));
		zodiac408.addCiphertextCharacter(new Ciphertext(116, "s"));
		zodiac408.addCiphertextCharacter(new Ciphertext(117, "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(118, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(119, "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(120, "backd"));
		zodiac408.addCiphertextCharacter(new Ciphertext(121, "forslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(122, "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(123, "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(124, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(125, "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(126, "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(127, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(128, "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(129, "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(130, "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(131, "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(132, "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(133, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(134, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(135, "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(136, "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(137, "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(138, "k"));
		zodiac408.addCiphertextCharacter(new Ciphertext(139, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(140, "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(141, "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(142, "horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(143, "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(144, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(145, "anchor"));
		zodiac408.addCiphertextCharacter(new Ciphertext(146, "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(147, "fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(148, "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(149, "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(150, "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(151, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(152, "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(153, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(154, "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(155, "d"));
		zodiac408.addCiphertextCharacter(new Ciphertext(156, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(157, "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(158, "backj"));
		zodiac408.addCiphertextCharacter(new Ciphertext(159, "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(160, "circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(161, "backslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(162, "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(163, "vertstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(164, "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(165, "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(166, "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(167, "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(168, "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(169, "f"));
		zodiac408.addCiphertextCharacter(new Ciphertext(170, "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(171, "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(172, "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(173, "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(174, "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(175, "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(176, "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(177, "fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(178, "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(179, "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(180, "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(181, "horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(182, "backl"));
		zodiac408.addCiphertextCharacter(new Ciphertext(183, "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(184, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(185, "circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(186, "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(187, "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(188, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(189, "d"));
		zodiac408.addCiphertextCharacter(new Ciphertext(190, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(191, "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(192, "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(193, "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(194, "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(195, "backslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(196, "backd"));
		zodiac408.addCiphertextCharacter(new Ciphertext(197, "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(198, "forslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(199, "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(200, "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(201, "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(202, "q"));
		zodiac408.addCiphertextCharacter(new Ciphertext(203, "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(204, "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(205, "fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(206, "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(207, "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(208, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(209, "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(210, "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(211, "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(212, "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(213, "horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(214, "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(215, "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(216, "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(217, "k"));
		zodiac408.addCiphertextCharacter(new Ciphertext(218, "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(219, "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(220, "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(221, "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(222, "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(223, "anchor"));
		zodiac408.addCiphertextCharacter(new Ciphertext(224, "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(225, "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(226, "fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(227, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(228, "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(229, "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(230, "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(231, "backl"));
		zodiac408.addCiphertextCharacter(new Ciphertext(232, "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(233, "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(234, "horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(235, "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(236, "vertstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(237, "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(238, "zodiac"));
		zodiac408.addCiphertextCharacter(new Ciphertext(239, "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(240, "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(241, "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(242, "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(243, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(244, "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(245, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(246, "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(247, "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(248, "backslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(249, "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(250, "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(251, "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(252, "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(253, "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(254, "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(255, "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(256, "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(257, "s"));
		zodiac408.addCiphertextCharacter(new Ciphertext(258, "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(259, "backl"));
		zodiac408.addCiphertextCharacter(new Ciphertext(260, "backf"));
		zodiac408.addCiphertextCharacter(new Ciphertext(261, "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(262, "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(263, "circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(264, "tridot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(265, "d"));
		zodiac408.addCiphertextCharacter(new Ciphertext(266, "zodiac"));
		zodiac408.addCiphertextCharacter(new Ciphertext(267, "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(268, "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(269, "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(270, "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(271, "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(272, "n"));
		zodiac408.addCiphertextCharacter(new Ciphertext(273, "backk"));
		zodiac408.addCiphertextCharacter(new Ciphertext(274, "horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(275, "s"));
		zodiac408.addCiphertextCharacter(new Ciphertext(276, "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(277, "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(278, "forslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(279, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(280, "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(281, "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(282, "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(283, "backf"));
		zodiac408.addCiphertextCharacter(new Ciphertext(284, "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(285, "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(286, "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(287, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(288, "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(289, "backp"));
		zodiac408.addCiphertextCharacter(new Ciphertext(290, "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(291, "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(292, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(293, "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(294, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(295, "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(296, "f"));
		zodiac408.addCiphertextCharacter(new Ciphertext(297, "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(298, "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(299, "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(300, "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(301, "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(302, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(303, "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(304, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(305, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(306, "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(307, "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(308, "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(309, "fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(310, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(311, "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(312, "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(313, "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(314, "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(315, "backd"));
		zodiac408.addCiphertextCharacter(new Ciphertext(316, "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(317, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(318, "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(319, "carrot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(320, "s"));
		zodiac408.addCiphertextCharacter(new Ciphertext(321, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(322, "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(323, "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(324, "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(325, "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(326, "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(327, "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(328, "k"));
		zodiac408.addCiphertextCharacter(new Ciphertext(329, "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(330, "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(331, "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(332, "y"));
		zodiac408.addCiphertextCharacter(new Ciphertext(333, "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(334, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(335, "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(336, "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(337, "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(338, "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(339, "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(340, "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(341, "anchor"));
		zodiac408.addCiphertextCharacter(new Ciphertext(342, "f"));
		zodiac408.addCiphertextCharacter(new Ciphertext(343, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(344, "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(345, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(346, "zodiac"));
		zodiac408.addCiphertextCharacter(new Ciphertext(347, "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(348, "a"));
		zodiac408.addCiphertextCharacter(new Ciphertext(349, "d"));
		zodiac408.addCiphertextCharacter(new Ciphertext(350, "backd"));
		zodiac408.addCiphertextCharacter(new Ciphertext(351, "backslash"));
		zodiac408.addCiphertextCharacter(new Ciphertext(352, "tridot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(353, "l"));
		zodiac408.addCiphertextCharacter(new Ciphertext(354, "anchor"));
		zodiac408.addCiphertextCharacter(new Ciphertext(355, "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(356, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(357, "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(358, "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(359, "backd"));
		zodiac408.addCiphertextCharacter(new Ciphertext(360, "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(361, "fullbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(362, "circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(363, "backe"));
		zodiac408.addCiphertextCharacter(new Ciphertext(364, "fullcircle"));
		zodiac408.addCiphertextCharacter(new Ciphertext(365, "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(366, "o"));
		zodiac408.addCiphertextCharacter(new Ciphertext(367, "r"));
		zodiac408.addCiphertextCharacter(new Ciphertext(368, "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(369, "q"));
		zodiac408.addCiphertextCharacter(new Ciphertext(370, "f"));
		zodiac408.addCiphertextCharacter(new Ciphertext(371, "lrbox"));
		zodiac408.addCiphertextCharacter(new Ciphertext(372, "g"));
		zodiac408.addCiphertextCharacter(new Ciphertext(373, "backc"));
		zodiac408.addCiphertextCharacter(new Ciphertext(374, "z"));
		zodiac408.addCiphertextCharacter(new Ciphertext(375, "boxdot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(376, "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(377, "t"));
		zodiac408.addCiphertextCharacter(new Ciphertext(378, "flipt"));
		zodiac408.addCiphertextCharacter(new Ciphertext(379, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(380, "box"));
		zodiac408.addCiphertextCharacter(new Ciphertext(381, "fulltri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(382, "j"));
		zodiac408.addCiphertextCharacter(new Ciphertext(383, "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(384, "plus"));
		zodiac408.addCiphertextCharacter(new Ciphertext(385, "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(386, "b"));
		zodiac408.addCiphertextCharacter(new Ciphertext(387, "p"));
		zodiac408.addCiphertextCharacter(new Ciphertext(388, "q"));
		zodiac408.addCiphertextCharacter(new Ciphertext(389, "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(390, "circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(391, "v"));
		zodiac408.addCiphertextCharacter(new Ciphertext(392, "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(393, "x"));
		zodiac408.addCiphertextCharacter(new Ciphertext(394, "backr"));
		zodiac408.addCiphertextCharacter(new Ciphertext(395, "tri"));
		zodiac408.addCiphertextCharacter(new Ciphertext(396, "w"));
		zodiac408.addCiphertextCharacter(new Ciphertext(397, "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(398, "circledot"));
		zodiac408.addCiphertextCharacter(new Ciphertext(399, "backq"));
		zodiac408.addCiphertextCharacter(new Ciphertext(400, "e"));
		zodiac408.addCiphertextCharacter(new Ciphertext(401, "h"));
		zodiac408.addCiphertextCharacter(new Ciphertext(402, "m"));
		zodiac408.addCiphertextCharacter(new Ciphertext(403, "horstrike"));
		zodiac408.addCiphertextCharacter(new Ciphertext(404, "pi"));
		zodiac408.addCiphertextCharacter(new Ciphertext(405, "u"));
		zodiac408.addCiphertextCharacter(new Ciphertext(406, "i"));
		zodiac408.addCiphertextCharacter(new Ciphertext(407, "backk"));
	}

	static {
		knownSolution.setCipherId(zodiac408.getId());

		knownSolution.setRows(24);
		knownSolution.setColumns(17);

		List<PlaintextSequence> plaintextCharacters = new ArrayList<PlaintextSequence>();
		/*
		 * PartOfSpeech is not used by the evaluator, so set it to something arbitrary
		 */
		WordGene nextWordGene;

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("like", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killing", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("people", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("so", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("much", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("fun", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("more", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("fun", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("than", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killing", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("wild", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("game", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("in", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("forrest", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("man", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("moat", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("dangeroue", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("anamal", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("all", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("to", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("kill", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("something", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("gives", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("me", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("moat", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("thrilling", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("experence", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("x", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("is", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("even", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("better", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("than", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("getting", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("your", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("rocks", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("off", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("with", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("a", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("girl", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("best", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("part", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("it", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("ia", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("thae", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("when", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("die", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("be", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("reborn", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("in", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("paradice", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("snd", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("all", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("the", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("have", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("killed", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("k", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("become", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("slaves", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("i", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("not", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("give", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("you", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("name", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("because", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("you", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("u", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("will", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("try", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("to", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("sloi", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("down", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("d", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("w", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("or", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("stop", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("collecting", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("c", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("n", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("g", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("of", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("slaves", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("v", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("s", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("for", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("my", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("y", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("afterlife", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("a", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("l", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("f", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("e", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));

		nextWordGene = new WordGene(new Word(new WordId("beorietemethhpiti", PartOfSpeechType.NONE)), knownSolution);
		knownSolution.addGene(nextWordGene);
		plaintextCharacters.add(new PlaintextSequence("b", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("o", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("r", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("m", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("e", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("h", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("p", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("t", nextWordGene));
		plaintextCharacters.add(new PlaintextSequence("i", nextWordGene));
	}

	@Before
	public void resetDirtiness() {
		knownSolution.setEvaluationNeeded(true);
		knownSolution.setTotalMatches(0);
		knownSolution.setUniqueMatches(0);
		knownSolution.setAdjacentMatches(0);
		assertTrue(knownSolution.isEvaluationNeeded());
	}
}