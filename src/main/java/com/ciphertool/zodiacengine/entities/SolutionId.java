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
import javax.persistence.ManyToOne;

@Embeddable
public class SolutionId implements Serializable {
	private static final long serialVersionUID = -788355825214706921L;
	protected int id;
	protected SolutionSet solutionSet;

	public SolutionId() {
	}

	/**
	 * @param id
	 * @param solutionSet
	 */

	public SolutionId(int id, SolutionSet solutionSet) {
		this.id = id;
		this.solutionSet = solutionSet;
	}

	/**
	 * @return the id
	 */
	@Column(name = "id")
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the solutionSet
	 */
	@ManyToOne
	@JoinColumn(name = "solution_set_id")
	public SolutionSet getSolutionSet() {
		return solutionSet;
	}

	/**
	 * @param solutionSet
	 *            the solutionSet to set
	 */
	public void setSolutionSet(SolutionSet solutionSet) {
		this.solutionSet = solutionSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 * 
	 * We need to use the solutionSetId only instead of the whole SolutionSet
	 * object. Otherwise we will run into a stack overflow.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((solutionSet == null) ? 0 : solutionSet.getId());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * We need to use the solutionSetId only instead of the whole SolutionSet
	 * object. Otherwise we will run into a stack overflow.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SolutionId other = (SolutionId) obj;
		if (id != other.id) {
			return false;
		}

		if (solutionSet == null) {
			if (other.solutionSet != null) {
				return false;
			}
		} else if (solutionSet.getId() != other.solutionSet.getId()) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 * 
	 * We need to use the solutionSetId only instead of the whole SolutionSet
	 * object. Otherwise we will run into a stack overflow.
	 */
	@Override
	public String toString() {
		return "SolutionId [id=" + ((id == 0) ? "NOT_SET" : id) + ", solutionSet="
				+ ((solutionSet == null) ? "NOT_SET" : solutionSet.getId()) + "]";
	}
}
