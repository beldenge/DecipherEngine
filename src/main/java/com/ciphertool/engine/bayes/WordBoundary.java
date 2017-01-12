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

package com.ciphertool.engine.bayes;

public class WordBoundary {
	private int previousIndex;

	/**
	 * @param previousIndex
	 *            the previous index
	 */
	public WordBoundary(int previousIndex) {
		this.previousIndex = previousIndex;
	}

	/**
	 * @return the previousIndex
	 */
	public int getPreviousIndex() {
		return previousIndex;
	}

	public WordBoundary clone() {
		return new WordBoundary(this.previousIndex);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + previousIndex;
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
		if (!(obj instanceof WordBoundary)) {
			return false;
		}
		WordBoundary other = (WordBoundary) obj;
		if (previousIndex != other.previousIndex) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "WordBoundary [previousIndex=" + previousIndex + "]";
	}
}
