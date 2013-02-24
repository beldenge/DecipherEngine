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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import com.ciphertool.genetics.algorithms.selection.modes.SelectorType;
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
	private String selectorNameText = "Selector Method: ";
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
	private JComboBox<String> selectorComboBox;
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

		JPanel bottomPanel = new JPanel();
		containerPanel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		bottomPanel.add(buttonPanel, BorderLayout.CENTER);

		GridLayout gridLayout = new GridLayout(1, 2, 5, 5);
		buttonPanel.setLayout(gridLayout);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JButton startButton = new JButton(startButtonText);
		startButton.addActionListener(getStartButtonActionListener());

		buttonPanel.add(startButton);

		JButton stopButton = new JButton(stopButtonText);
		stopButton.addActionListener(getStopButtonActionListener());

		buttonPanel.add(stopButton);

		JPanel statusPanel = new JPanel();
		bottomPanel.add(statusPanel, BorderLayout.SOUTH);

		statusLabel = new JLabel(statusNotRunning);
		statusPanel.add(statusLabel);

		/*
		 * Next make a grid bag for the thirteen input elements with labels on
		 * the left and spinners on the right.
		 */
		JPanel mainPanel = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.anchor = GridBagConstraints.WEST;
		mainPanel.setLayout(gridBagLayout);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		containerPanel.add(mainPanel, BorderLayout.CENTER);

		List<Cipher> ciphers = cipherDao.findAll();
		cipherComboBox = new JComboBox<String>();
		for (Cipher cipher : ciphers) {
			cipherComboBox.addItem(cipher.getName());
		}
		JLabel cipherNameLabel = new JLabel(cipherNameText);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(cipherNameLabel, constraints);
		mainPanel.add(cipherNameLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(cipherComboBox, constraints);
		mainPanel.add(cipherComboBox);

		SpinnerModel generationsModel = new SpinnerNumberModel(generationsInitial, GENERATIONS_MIN,
				GENERATIONS_MAX, GENERATIONS_STEP);
		generationsSpinner = new JSpinner(generationsModel);
		generationsSpinner.setEnabled(false);
		JLabel generationsLabel = new JLabel(generationsText);
		generationsLabel.setLabelFor(generationsSpinner);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(generationsLabel, constraints);
		mainPanel.add(generationsLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(generationsSpinner, constraints);
		mainPanel.add(generationsSpinner);

		runContinuouslyCheckBox = new JCheckBox(continuousText);
		runContinuouslyCheckBox.addActionListener(getRunContinuouslyCheckBoxActionListener());
		runContinuouslyCheckBox.setSelected(true);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		JLabel dummyJLabel = new JLabel();
		gridBagLayout.setConstraints(dummyJLabel, constraints);
		mainPanel.add(dummyJLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(runContinuouslyCheckBox, constraints);
		mainPanel.add(runContinuouslyCheckBox);

		SpinnerModel populationModel = new SpinnerNumberModel(populationInitial, POPULATION_MIN,
				POPULATION_MAX, POPULATION_STEP);
		populationSpinner = new JSpinner(populationModel);
		JLabel populationLabel = new JLabel(populationText);
		populationLabel.setLabelFor(populationSpinner);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(populationLabel, constraints);
		mainPanel.add(populationLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(populationSpinner, constraints);
		mainPanel.add(populationSpinner);

		SpinnerModel lifespanModel = new SpinnerNumberModel(lifespanInitial, LIFESPAN_MIN,
				LIFESPAN_MAX, LIFESPAN_STEP);
		lifespanSpinner = new JSpinner(lifespanModel);
		JLabel lifespanLabel = new JLabel(lifespanText);
		lifespanLabel.setLabelFor(lifespanSpinner);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(lifespanLabel, constraints);
		mainPanel.add(lifespanLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(lifespanSpinner, constraints);
		mainPanel.add(lifespanSpinner);

		SpinnerModel survivalRateModel = new SpinnerNumberModel(survivalInitial, SURVIVAL_MIN,
				SURVIVAL_MAX, SURVIVAL_STEP);
		survivalRateSpinner = new JSpinner(survivalRateModel);
		JLabel survivalRateLabel = new JLabel(survivalRateText);
		survivalRateLabel.setLabelFor(survivalRateSpinner);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(survivalRateLabel, constraints);
		mainPanel.add(survivalRateLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(survivalRateSpinner, constraints);
		mainPanel.add(survivalRateSpinner);

		SpinnerModel mutationRateModel = new SpinnerNumberModel(mutationInitial, MUTATION_MIN,
				MUTATION_MAX, MUTATION_STEP);
		mutationRateSpinner = new JSpinner(mutationRateModel);
		JLabel mutationRateLabel = new JLabel(mutationRateText);
		mutationRateLabel.setLabelFor(mutationRateSpinner);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(mutationRateLabel, constraints);
		mainPanel.add(mutationRateLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(mutationRateSpinner, constraints);
		mainPanel.add(mutationRateSpinner);

		SpinnerModel crossoverRateModel = new SpinnerNumberModel(crossoverInitial, CROSSOVER_MIN,
				CROSSOVER_MAX, CROSSOVER_STEP);
		crossoverRateSpinner = new JSpinner(crossoverRateModel);
		JLabel crossoverRateLabel = new JLabel(crossoverRateText);
		crossoverRateLabel.setLabelFor(crossoverRateSpinner);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(crossoverRateLabel, constraints);
		mainPanel.add(crossoverRateLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(crossoverRateSpinner, constraints);
		mainPanel.add(crossoverRateSpinner);

		fitnessEvaluatorComboBox = new JComboBox<String>();
		for (FitnessEvaluatorType fitnessEvaluatorType : FitnessEvaluatorType.values()) {
			fitnessEvaluatorComboBox.addItem(fitnessEvaluatorType.name());
		}
		fitnessEvaluatorComboBox
				.setSelectedItem(FitnessEvaluatorType.CIPHER_SOLUTION_MATCH_DISTANCE.name());
		JLabel fitnessEvaluatorNameLabel = new JLabel(fitnessEvaluatorNameText);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(fitnessEvaluatorNameLabel, constraints);
		mainPanel.add(fitnessEvaluatorNameLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(fitnessEvaluatorComboBox, constraints);
		mainPanel.add(fitnessEvaluatorComboBox);

		crossoverAlgorithmComboBox = new JComboBox<String>();
		for (CrossoverAlgorithmType crossoverAlgorithmType : CrossoverAlgorithmType.values()) {
			crossoverAlgorithmComboBox.addItem(crossoverAlgorithmType.name());
		}
		crossoverAlgorithmComboBox.setSelectedItem(CrossoverAlgorithmType.LOWEST_COMMON_GROUP
				.name());
		JLabel crossoverAlgorithmNameLabel = new JLabel(crossoverAlgorithmNameText);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(crossoverAlgorithmNameLabel, constraints);
		mainPanel.add(crossoverAlgorithmNameLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(crossoverAlgorithmComboBox, constraints);
		mainPanel.add(crossoverAlgorithmComboBox);

		mutationAlgorithmComboBox = new JComboBox<String>();
		for (MutationAlgorithmType mutationAlgorithmType : MutationAlgorithmType.values()) {
			mutationAlgorithmComboBox.addItem(mutationAlgorithmType.name());
		}
		mutationAlgorithmComboBox.setSelectedItem(MutationAlgorithmType.CONSERVATIVE.name());
		JLabel mutationAlgorithmNameLabel = new JLabel(mutationAlgorithmNameText);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(mutationAlgorithmNameLabel, constraints);
		mainPanel.add(mutationAlgorithmNameLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(mutationAlgorithmComboBox, constraints);
		mainPanel.add(mutationAlgorithmComboBox);

		selectionAlgorithmComboBox = new JComboBox<String>();
		for (SelectionAlgorithmType selectionAlgorithmType : SelectionAlgorithmType.values()) {
			selectionAlgorithmComboBox.addItem(selectionAlgorithmType.name());
		}
		selectionAlgorithmComboBox.setSelectedItem(SelectionAlgorithmType.PROBABILISTIC.name());
		JLabel selectionAlgorithmNameLabel = new JLabel(selectionAlgorithmNameText);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(selectionAlgorithmNameLabel, constraints);
		mainPanel.add(selectionAlgorithmNameLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(selectionAlgorithmComboBox, constraints);
		mainPanel.add(selectionAlgorithmComboBox);

		selectorComboBox = new JComboBox<String>();
		for (SelectorType selectorType : SelectorType.values()) {
			selectorComboBox.addItem(selectorType.name());
		}
		selectorComboBox.setSelectedItem(SelectorType.ROULETTE.name());
		JLabel selectorNameLabel = new JLabel(selectorNameText);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridBagLayout.setConstraints(selectorNameLabel, constraints);
		mainPanel.add(selectorNameLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(selectorComboBox, constraints);
		mainPanel.add(selectorComboBox);

		compareToKnownSolutionCheckBox = new JCheckBox(compareToKnownSolutionText);

		constraints.weightx = 1.0;
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		dummyJLabel = new JLabel();
		gridBagLayout.setConstraints(dummyJLabel, constraints);
		mainPanel.add(dummyJLabel);
		constraints.weightx = 2.0;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(compareToKnownSolutionCheckBox, constraints);
		mainPanel.add(compareToKnownSolutionCheckBox);

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
						(String) selectorComboBox.getSelectedItem(), compareToKnownSolutionCheckBox
								.isSelected());
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
		if (survivalInitial < 0.0 || survivalInitial > 1.0) {
			throw new IllegalArgumentException(
					"Tried to set a survivalInitial of "
							+ survivalInitial
							+ ", but SwingUserInterface requires a survivalInitial between 0.0 and 1.0 inclusive.");
		}

		this.survivalInitial = survivalInitial;
	}

	/**
	 * @param mutationInitial
	 *            the mutationInitial to set
	 */
	@Required
	public void setMutationInitial(double mutationInitial) {
		if (mutationInitial < 0.0 || mutationInitial > 1.0) {
			throw new IllegalArgumentException(
					"Tried to set a mutationInitial of "
							+ mutationInitial
							+ ", but SwingUserInterface requires a mutationInitial between 0.0 and 1.0 inclusive.");
		}

		this.mutationInitial = mutationInitial;
	}

	/**
	 * @param crossoverInitial
	 *            the crossoverInitial to set
	 */
	@Required
	public void setCrossoverInitial(double crossoverInitial) {
		if (crossoverInitial < 0.0 || crossoverInitial > 1.0) {
			throw new IllegalArgumentException(
					"Tried to set a crossoverInitial of "
							+ crossoverInitial
							+ ", but SwingUserInterface requires a crossoverInitial between 0.0 and 1.0 inclusive.");
		}

		this.crossoverInitial = crossoverInitial;
	}
}
