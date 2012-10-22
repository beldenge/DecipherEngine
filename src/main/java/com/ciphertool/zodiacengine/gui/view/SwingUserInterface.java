/**
 * Copyright 2012 George Belden
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

package com.ciphertool.zodiacengine.gui.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.zodiacengine.gui.controller.CipherSolutionController;

public class SwingUserInterface extends JFrame implements UserInterface {
	private static final long serialVersionUID = -7682403631152076457L;

	private String windowTitle;
	private int windowWidth;
	private int windowHeight;
	private String startButtonText = "Start";
	private String stopButtonText = "Stop";
	private String generationsText = "Generations: ";
	private String populationText = "Population Size: ";
	private String survivalRateText = "Survival Rate: ";
	private String mutationRateText = "Mutation Rate: ";
	private String crossoverRateText = "Crossover Rate: ";
	private String continuousText = "Run until user stops";
	private String statusRunning = "Running.";
	private String statusNotRunning = "Not running.";
	private static final int GENERATIONS_INITIAL = 50;
	private static final int GENERATIONS_MIN = 1;
	private static final int GENERATIONS_MAX = 100000;
	private static final int GENERATIONS_STEP = 50;
	private static final int POPULATION_INITIAL = 100;
	private static final int POPULATION_MIN = 1;
	private static final int POPULATION_MAX = 100000;
	private static final int POPULATION_STEP = 100;
	private static final double SURVIVAL_INITIAL = 0.9;
	private static final double SURVIVAL_MIN = 0.001;
	private static final double SURVIVAL_MAX = 1.0;
	private static final double SURVIVAL_STEP = 0.01;
	private static final double MUTATION_INITIAL = 0.001;
	private static final double MUTATION_MIN = 0.0001;
	private static final double MUTATION_MAX = 1;
	private static final double MUTATION_STEP = 0.001;
	private static final double CROSSOVER_INITIAL = 0.05;
	private static final double CROSSOVER_MIN = 0.001;
	private static final double CROSSOVER_MAX = 1.0;
	private static final double CROSSOVER_STEP = 0.01;

	private CipherSolutionController cipherSolutionController;

	private JCheckBox runContinuouslyCheckBox;
	private JSpinner generationsSpinner;
	private JLabel statusLabel;

	public SwingUserInterface() {
	}

	public void init() {
		this.setTitle(windowTitle);
		this.setSize(windowWidth, windowHeight);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel containerPanel = new JPanel();
		getContentPane().add(containerPanel);

		/*
		 * Use a BorderLayout for the main container so we can use the SOUTH
		 * panel as a status bar and use the NORTH panel as a menu bar, etc.
		 */
		containerPanel.setLayout(new BorderLayout());

		JPanel statusPanel = new JPanel();
		containerPanel.add(statusPanel, BorderLayout.SOUTH);
		statusLabel = new JLabel(statusNotRunning);
		statusPanel.add(statusLabel);

		/*
		 * Next make a 7-row, 2-column grid. This is for the five input boxes
		 * with labels on the left and spinners on the right, and then the two
		 * action buttons.
		 */
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(7, 2, 5, 5));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		containerPanel.add(mainPanel, BorderLayout.CENTER);

		SpinnerModel generationsModel = new SpinnerNumberModel(GENERATIONS_INITIAL,
				GENERATIONS_MIN, GENERATIONS_MAX, GENERATIONS_STEP);
		generationsSpinner = new JSpinner(generationsModel);
		JLabel generationsLabel = new JLabel(generationsText);
		generationsLabel.setLabelFor(generationsSpinner);

		mainPanel.add(generationsLabel);
		mainPanel.add(generationsSpinner);

		runContinuouslyCheckBox = new JCheckBox(continuousText);
		runContinuouslyCheckBox.addActionListener(getRunContinuouslyCheckBoxActionListener());

		mainPanel.add(new JLabel());
		mainPanel.add(runContinuouslyCheckBox);

		SpinnerModel populationModel = new SpinnerNumberModel(POPULATION_INITIAL, POPULATION_MIN,
				POPULATION_MAX, POPULATION_STEP);
		JSpinner populationSpinner = new JSpinner(populationModel);
		JLabel populationLabel = new JLabel(populationText);
		populationLabel.setLabelFor(populationSpinner);

		mainPanel.add(populationLabel);
		mainPanel.add(populationSpinner);

		SpinnerModel survivalRateModel = new SpinnerNumberModel(SURVIVAL_INITIAL, SURVIVAL_MIN,
				SURVIVAL_MAX, SURVIVAL_STEP);
		JSpinner survivalRateSpinner = new JSpinner(survivalRateModel);
		JLabel survivalRateLabel = new JLabel(survivalRateText);
		survivalRateLabel.setLabelFor(survivalRateSpinner);

		mainPanel.add(survivalRateLabel);
		mainPanel.add(survivalRateSpinner);

		SpinnerModel mutationRateModel = new SpinnerNumberModel(MUTATION_INITIAL, MUTATION_MIN,
				MUTATION_MAX, MUTATION_STEP);
		JSpinner mutationRateSpinner = new JSpinner(mutationRateModel);
		JLabel mutationRateLabel = new JLabel(mutationRateText);
		mutationRateLabel.setLabelFor(mutationRateSpinner);

		mainPanel.add(mutationRateLabel);
		mainPanel.add(mutationRateSpinner);

		SpinnerModel crossoverRateModel = new SpinnerNumberModel(CROSSOVER_INITIAL, CROSSOVER_MIN,
				CROSSOVER_MAX, CROSSOVER_STEP);
		JSpinner crossoverRateSpinner = new JSpinner(crossoverRateModel);
		JLabel crossoverRateLabel = new JLabel(crossoverRateText);
		crossoverRateLabel.setLabelFor(crossoverRateSpinner);

		mainPanel.add(crossoverRateLabel);
		mainPanel.add(crossoverRateSpinner);

		JButton startButton = new JButton(startButtonText);
		startButton.setSize(80, 30);
		startButton.addActionListener(getStartButtonActionListener());

		mainPanel.add(startButton);

		JButton stopButton = new JButton(stopButtonText);
		stopButton.setSize(80, 30);
		stopButton.addActionListener(getStopButtonActionListener());

		mainPanel.add(stopButton);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
				toFront();
			}
		});
	}

	public ActionListener getRunContinuouslyCheckBoxActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (runContinuouslyCheckBox.isSelected()) {
					generationsSpinner.setEnabled(false);
				} else {
					generationsSpinner.setEnabled(true);
				}
			}
		};
	}

	public ActionListener getStartButtonActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				statusLabel.setText(statusRunning);

				cipherSolutionController.startServiceThread();
			}
		};
	}

	public ActionListener getStopButtonActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cipherSolutionController.stopServiceThread();

				statusLabel.setText(statusNotRunning);
			}
		};
	}

	/**
	 * @param windowTitle
	 *            the windowTitle to set
	 */
	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	/**
	 * @param windowWidth
	 *            the windowWidth to set
	 */
	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	/**
	 * @param windowHeight
	 *            the windowHeight to set
	 */
	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	/**
	 * @param startButtonText
	 *            the startButtonText to set
	 */
	public void setStartButtonText(String startButtonText) {
		this.startButtonText = startButtonText;
	}

	/**
	 * @param stopButtonText
	 *            the stopButtonText to set
	 */
	public void setStopButtonText(String stopButtonText) {
		this.stopButtonText = stopButtonText;
	}

	/**
	 * @param cipherSolutionController
	 *            the cipherSolutionController to set
	 */
	@Required
	public void setCipherSolutionController(CipherSolutionController cipherSolutionController) {
		this.cipherSolutionController = cipherSolutionController;
	}
}
