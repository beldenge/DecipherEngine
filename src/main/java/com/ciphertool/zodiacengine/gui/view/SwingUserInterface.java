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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.ciphertool.genetics.algorithms.crossover.CrossoverAlgorithmType;
import com.ciphertool.genetics.algorithms.mutation.MutationAlgorithmType;
import com.ciphertool.genetics.algorithms.selection.SelectionAlgorithmType;
import com.ciphertool.zodiacengine.dao.CipherDao;
import com.ciphertool.zodiacengine.entities.Cipher;
import com.ciphertool.zodiacengine.genetic.util.FitnessEvaluatorType;
import com.ciphertool.zodiacengine.gui.controller.CipherSolutionController;

public class SwingUserInterface extends JFrame implements UserInterface {
	private static final long serialVersionUID = -7682403631152076457L;

	private Logger log = Logger.getLogger(getClass());
	private static final int PROGRAM_EXIT_SLEEP_MILLIS = 1000;

	private String windowTitle;
	private int windowWidth;
	private int windowHeight;
	private String cipherNameText = "Cipher: ";
	private String startButtonText = "Start";
	private String stopButtonText = "Stop";
	private String generationsText = "Generations: ";
	private String populationText = "Population Size: ";
	private String lifespanText = "Individual Lifespan: ";
	private String survivalRateText = "Survival Rate: ";
	private String mutationRateText = "Mutation Rate: ";
	private String crossoverRateText = "Crossover Rate: ";
	private String continuousText = "Run until user stops";
	private String fitnessEvaluatorNameText = "Fitness Evaluator: ";
	private String crossoverAlgorithmNameText = "Crossover Algorithm: ";
	private String mutationAlgorithmNameText = "Mutation Algorithm: ";
	private String selectionAlgorithmNameText = "Selection Algorithm: ";
	private String statusRunning = "Running.";
	private String statusNotRunning = "Not running.";
	private String compareToKnownSolutionText = "Compare to known solution";
	private int lifespanInitial;
	private static final int LIFESPAN_MIN = -1;
	private static final int LIFESPAN_MAX = 1000;
	private static final int LIFESPAN_STEP = 1;
	private int generationsInitial;
	private static final int GENERATIONS_MIN = 1;
	private static final int GENERATIONS_MAX = 100000;
	private static final int GENERATIONS_STEP = 50;
	private int populationInitial;
	private static final int POPULATION_MIN = 1;
	private static final int POPULATION_MAX = 100000;
	private static final int POPULATION_STEP = 100;
	private double survivalInitial;
	private static final double SURVIVAL_MIN = 0.0;
	private static final double SURVIVAL_MAX = 1.0;
	private static final double SURVIVAL_STEP = 0.01;
	private double mutationInitial;
	private static final double MUTATION_MIN = 0.0;
	private static final double MUTATION_MAX = 1;
	private static final double MUTATION_STEP = 0.001;
	private double crossoverInitial;
	private static final double CROSSOVER_MIN = 0.0;
	private static final double CROSSOVER_MAX = 1.0;
	private static final double CROSSOVER_STEP = 0.01;

	private CipherSolutionController cipherSolutionController;
	private CipherDao cipherDao;

	private JComboBox<String> cipherComboBox;
	private JComboBox<String> fitnessEvaluatorComboBox;
	private JComboBox<String> crossoverAlgorithmComboBox;
	private JComboBox<String> mutationAlgorithmComboBox;
	private JComboBox<String> selectionAlgorithmComboBox;
	private JCheckBox runContinuouslyCheckBox;
	private JSpinner generationsSpinner;
	private JSpinner populationSpinner;
	private JSpinner lifespanSpinner;
	private JSpinner survivalRateSpinner;
	private JSpinner mutationRateSpinner;
	private JSpinner crossoverRateSpinner;
	private JLabel statusLabel;
	private JCheckBox compareToKnownSolutionCheckBox;

	public SwingUserInterface() {
	}

	private WindowAdapter getWindowClosingListener() {
		return new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				/*
				 * Run in a separate thread so the window closes, but the
				 * process remains alive until we are finished handling the
				 * windowClosing event.
				 */
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						/*
						 * In case the program was terminated abruptly, try to
						 * stop the service as normal so that the post-execution
						 * tasks are performed.
						 */
						if (cipherSolutionController.isServiceThreadActive()) {
							cipherSolutionController.stopServiceThread();

							/*
							 * Keep waiting for the program to finish
							 * post-execution tasks.
							 */
							try {
								while (cipherSolutionController.isServiceThreadActive()) {
									Thread.sleep(PROGRAM_EXIT_SLEEP_MILLIS);
								}
							} catch (InterruptedException e) {
								log.error(
										"Caught InterruptedException while waiting for service thread to complete after window close event.  Unable to continue.",
										e);
							}
						}

					}
				});
			}
		};
	}

	public void init() {
		this.addWindowListener(getWindowClosingListener());
		this.setTitle(windowTitle);
		this.setSize(windowWidth, windowHeight);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
		 * Next make a 14-row, 2-column grid. This is for the twelve input
		 * elements with labels on the left and spinners on the right, and then
		 * the two action buttons.
		 */
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(14, 2, 5, 5));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		containerPanel.add(mainPanel, BorderLayout.CENTER);

		List<Cipher> ciphers = cipherDao.findAll();
		cipherComboBox = new JComboBox<String>();
		for (Cipher cipher : ciphers) {
			cipherComboBox.addItem(cipher.getName());
		}
		JLabel cipherNameLabel = new JLabel(cipherNameText);

		mainPanel.add(cipherNameLabel);
		mainPanel.add(cipherComboBox);

		SpinnerModel generationsModel = new SpinnerNumberModel(generationsInitial, GENERATIONS_MIN,
				GENERATIONS_MAX, GENERATIONS_STEP);
		generationsSpinner = new JSpinner(generationsModel);
		JLabel generationsLabel = new JLabel(generationsText);
		generationsLabel.setLabelFor(generationsSpinner);

		mainPanel.add(generationsLabel);
		mainPanel.add(generationsSpinner);

		runContinuouslyCheckBox = new JCheckBox(continuousText);
		runContinuouslyCheckBox.addActionListener(getRunContinuouslyCheckBoxActionListener());

		mainPanel.add(new JLabel());
		mainPanel.add(runContinuouslyCheckBox);

		SpinnerModel populationModel = new SpinnerNumberModel(populationInitial, POPULATION_MIN,
				POPULATION_MAX, POPULATION_STEP);
		populationSpinner = new JSpinner(populationModel);
		JLabel populationLabel = new JLabel(populationText);
		populationLabel.setLabelFor(populationSpinner);

		mainPanel.add(populationLabel);
		mainPanel.add(populationSpinner);

		SpinnerModel lifespanModel = new SpinnerNumberModel(lifespanInitial, LIFESPAN_MIN,
				LIFESPAN_MAX, LIFESPAN_STEP);
		lifespanSpinner = new JSpinner(lifespanModel);
		JLabel lifespanLabel = new JLabel(lifespanText);
		lifespanLabel.setLabelFor(lifespanSpinner);

		mainPanel.add(lifespanLabel);
		mainPanel.add(lifespanSpinner);

		SpinnerModel survivalRateModel = new SpinnerNumberModel(survivalInitial, SURVIVAL_MIN,
				SURVIVAL_MAX, SURVIVAL_STEP);
		survivalRateSpinner = new JSpinner(survivalRateModel);
		JLabel survivalRateLabel = new JLabel(survivalRateText);
		survivalRateLabel.setLabelFor(survivalRateSpinner);

		mainPanel.add(survivalRateLabel);
		mainPanel.add(survivalRateSpinner);

		SpinnerModel mutationRateModel = new SpinnerNumberModel(mutationInitial, MUTATION_MIN,
				MUTATION_MAX, MUTATION_STEP);
		mutationRateSpinner = new JSpinner(mutationRateModel);
		JLabel mutationRateLabel = new JLabel(mutationRateText);
		mutationRateLabel.setLabelFor(mutationRateSpinner);

		mainPanel.add(mutationRateLabel);
		mainPanel.add(mutationRateSpinner);

		SpinnerModel crossoverRateModel = new SpinnerNumberModel(crossoverInitial, CROSSOVER_MIN,
				CROSSOVER_MAX, CROSSOVER_STEP);
		crossoverRateSpinner = new JSpinner(crossoverRateModel);
		JLabel crossoverRateLabel = new JLabel(crossoverRateText);
		crossoverRateLabel.setLabelFor(crossoverRateSpinner);

		mainPanel.add(crossoverRateLabel);
		mainPanel.add(crossoverRateSpinner);

		fitnessEvaluatorComboBox = new JComboBox<String>();
		for (FitnessEvaluatorType fitnessEvaluatorType : FitnessEvaluatorType.values()) {
			fitnessEvaluatorComboBox.addItem(fitnessEvaluatorType.name());
		}
		fitnessEvaluatorComboBox
				.setSelectedItem(FitnessEvaluatorType.CIPHER_SOLUTION_MATCH_DISTANCE.name());
		JLabel fitnessEvaluatorNameLabel = new JLabel(fitnessEvaluatorNameText);

		mainPanel.add(fitnessEvaluatorNameLabel);
		mainPanel.add(fitnessEvaluatorComboBox);

		crossoverAlgorithmComboBox = new JComboBox<String>();
		for (CrossoverAlgorithmType crossoverAlgorithmType : CrossoverAlgorithmType.values()) {
			crossoverAlgorithmComboBox.addItem(crossoverAlgorithmType.name());
		}
		crossoverAlgorithmComboBox.setSelectedItem(CrossoverAlgorithmType.LOWEST_COMMON_GROUP
				.name());
		JLabel crossoverAlgorithmNameLabel = new JLabel(crossoverAlgorithmNameText);

		mainPanel.add(crossoverAlgorithmNameLabel);
		mainPanel.add(crossoverAlgorithmComboBox);

		mutationAlgorithmComboBox = new JComboBox<String>();
		for (MutationAlgorithmType mutationAlgorithmType : MutationAlgorithmType.values()) {
			mutationAlgorithmComboBox.addItem(mutationAlgorithmType.name());
		}
		mutationAlgorithmComboBox.setSelectedItem(MutationAlgorithmType.CONSERVATIVE.name());
		JLabel mutationAlgorithmNameLabel = new JLabel(mutationAlgorithmNameText);

		mainPanel.add(mutationAlgorithmNameLabel);
		mainPanel.add(mutationAlgorithmComboBox);

		selectionAlgorithmComboBox = new JComboBox<String>();
		for (SelectionAlgorithmType selectionAlgorithmType : SelectionAlgorithmType.values()) {
			selectionAlgorithmComboBox.addItem(selectionAlgorithmType.name());
		}
		selectionAlgorithmComboBox.setSelectedItem(SelectionAlgorithmType.PROBABILISTIC.name());
		JLabel selectionAlgorithmNameLabel = new JLabel(selectionAlgorithmNameText);

		mainPanel.add(selectionAlgorithmNameLabel);
		mainPanel.add(selectionAlgorithmComboBox);

		compareToKnownSolutionCheckBox = new JCheckBox(compareToKnownSolutionText);

		mainPanel.add(new JLabel());
		mainPanel.add(compareToKnownSolutionCheckBox);

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

				int generations = (Integer) generationsSpinner.getValue();

				if (runContinuouslyCheckBox.isSelected()) {
					generations = -1;
				}

				cipherSolutionController.startServiceThread((String) cipherComboBox
						.getSelectedItem(), (Integer) populationSpinner.getValue(),
						(Integer) lifespanSpinner.getValue(), generations,
						(Double) survivalRateSpinner.getValue(), (Double) mutationRateSpinner
								.getValue(), (Double) crossoverRateSpinner.getValue(),
						(String) fitnessEvaluatorComboBox.getSelectedItem(),
						(String) crossoverAlgorithmComboBox.getSelectedItem(),
						(String) mutationAlgorithmComboBox.getSelectedItem(),
						(String) selectionAlgorithmComboBox.getSelectedItem(),
						compareToKnownSolutionCheckBox.isSelected());
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

	/**
	 * @param cipherDao
	 *            the cipherDao to set
	 */
	@Required
	public void setCipherDao(CipherDao cipherDao) {
		this.cipherDao = cipherDao;
	}

	/**
	 * @param generationsInitial
	 *            the generationsInitial to set
	 */
	@Required
	public void setGenerationsInitial(int generationsInitial) {
		this.generationsInitial = generationsInitial;
	}

	/**
	 * @param populationInitial
	 *            the populationInitial to set
	 */
	@Required
	public void setPopulationInitial(int populationInitial) {
		this.populationInitial = populationInitial;
	}

	/**
	 * @param lifespanInitial
	 *            the lifespanInitial to set
	 */
	@Required
	public void setLifespanInitial(int lifespanInitial) {
		this.lifespanInitial = lifespanInitial;
	}

	/**
	 * @param survivalInitial
	 *            the survivalInitial to set
	 */
	@Required
	public void setSurvivalInitial(double survivalInitial) {
		this.survivalInitial = survivalInitial;
	}

	/**
	 * @param mutationInitial
	 *            the mutationInitial to set
	 */
	@Required
	public void setMutationInitial(double mutationInitial) {
		this.mutationInitial = mutationInitial;
	}

	/**
	 * @param crossoverInitial
	 *            the crossoverInitial to set
	 */
	@Required
	public void setCrossoverInitial(double crossoverInitial) {
		this.crossoverInitial = crossoverInitial;
	}
}
