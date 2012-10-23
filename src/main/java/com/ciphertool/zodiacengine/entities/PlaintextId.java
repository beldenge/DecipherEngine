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

package com.ciphertool.zodiacengine.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Embeddable
public class PlaintextId implements Serializable {
	private static final long serialVersionUID = -7647050018442693916L;
	private Solution solution;
	private int ciphertextId;

	public PlaintextId() {
	}

	public PlaintextId(Solution solution, int ciphertextId) {
		this.solution = solution;
		this.ciphertextId = ciphertextId;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "solution_id", referencedColumnName = "id"),
			@JoinColumn(name = "solution_set_id", referencedColumnName = "solution_set_id") })
	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	@Column(name = "ciphertext_id")
	public int getCiphertextId() {
		return ciphertextId;
	}

	public void setCiphertextId(int ciphertextId) {
		this.ciphertextId = ciphertextId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 * 
	 * We need to use the solutionId only instead of the whole Solution object.
	 * Otherwise we will run into a stack overflow.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ciphertextId;
		result = prime * result + solution.getSolutionId().hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * We need to check the solutionId only instead of the whole Solution
	 * object. Otherwise we will run into a stack overflow.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PlaintextId)) {
			return false;
		}

		PlaintextId other = (PlaintextId) obj;
		if (ciphertextId != other.ciphertextId) {
			return false;
		}

		/*
		 * We are not checking for null solution because it should never happen
		 * and we want an exception to be thrown if so.
		 */
		if (solution.getSolutionId() == null) {
			if (other.getSolution().getSolutionId() != null) {
				return false;
			}
		} else if (!solution.getSolutionId().equals(other.getSolution().getSolutionId())) {
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * 
	 * We need to use the solutionId only instead of the whole Solution object.
	 * Otherwise we will run into a stack overflow.
	 */
	@Override
	public String toString() {
		return "PlaintextId [solutionId="
				+ ((solution == null) ? "null" : solution.getSolutionId()) + ", ciphertextId="
				+ ciphertextId + "]";
	}

}
