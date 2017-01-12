package com.ciphertool.engine.bayes;

import java.math.BigDecimal;

public class BoundaryProbability implements Probability<Boolean> {
	private Boolean		option;
	private BigDecimal	probability;

	/**
	 * @param option
	 *            the option
	 * @param probability
	 *            the probability
	 */
	public BoundaryProbability(Boolean option, BigDecimal probability) {
		super();
		this.option = option;
		this.probability = probability;
	}

	@Override
	public Boolean getValue() {
		return this.option;
	}

	@Override
	public BigDecimal getProbability() {
		return this.probability;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((option == null) ? 0 : option.hashCode());
		result = prime * result + ((probability == null) ? 0 : probability.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BoundaryProbability)) {
			return false;
		}
		BoundaryProbability other = (BoundaryProbability) obj;
		if (option == null) {
			if (other.option != null) {
				return false;
			}
		} else if (!option.equals(other.option)) {
			return false;
		}
		if (probability == null) {
			if (other.probability != null) {
				return false;
			}
		} else if (!probability.equals(other.probability)) {
			return false;
		}
		return true;
	}
}
