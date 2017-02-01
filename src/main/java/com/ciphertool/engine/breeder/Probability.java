/**
 * Copyright 2015 George Belden
 * 
 * This file is part of DecipherEngine.
 * 
 * DecipherEngine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DecipherEngine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with DecipherEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.engine.breeder;

import java.math.BigDecimal;

public interface Probability<T> {
	/**
	 * @return the value
	 */
	public T getValue();

	/**
	 * @return the probability
	 */
	public BigDecimal getProbability();

	/**
	 * @return the log probability
	 */
	public BigDecimal getLogProbability();
}
