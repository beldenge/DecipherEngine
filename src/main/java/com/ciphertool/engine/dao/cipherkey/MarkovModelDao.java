package com.ciphertool.engine.dao.cipherkey;

import javax.annotation.PostConstruct;

import com.ciphertool.sherlock.etl.importers.MarkovImporter;
import com.ciphertool.sherlock.markov.MarkovModel;

public class MarkovModelDao {
	private MarkovImporter	importer;
	private MarkovModel		model;

	@PostConstruct
	public void init() {
		model = importer.importCorpus();
	}

	/**
	 * @return the model
	 */
	public MarkovModel getModel() {
		return model;
	}

	/**
	 * @param importer
	 *            the importer to set
	 */
	public void setImporter(MarkovImporter importer) {
		this.importer = importer;
	}
}
