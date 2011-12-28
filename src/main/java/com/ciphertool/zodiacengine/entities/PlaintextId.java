package com.ciphertool.zodiacengine.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class PlaintextId implements Serializable {
	private static final long serialVersionUID = -7647050018442693916L;
	private Solution solution;
	private int ciphertextId;
	
	public PlaintextId() {}
	
	public PlaintextId(Solution solution, int ciphertextId) {
		this.solution=solution;
		this.ciphertextId=ciphertextId;
	}
	
	@ManyToOne
	@JoinColumn(name="solution_id")
	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	
	@Column(name="ciphertext_id")
	public int getCiphertextId() {
		return ciphertextId;
	}

	public void setCiphertextId(int ciphertextId) {
		this.ciphertextId = ciphertextId;
	}

	/* (non-Javadoc)
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
		result = prime * result + solution.getId();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * We need to check the solutionId only instead of the whole Solution object.  
	 * Otherwise we will run into a stack overflow.
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
		 * We are not checking for null solution because it should never happen and we want an exception to be thrown if so.
		 */ 
		if (solution.getId() != other.getSolution().getId()) {
			return false;
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * We need to use the solutionId only instead of the whole Solution object.  
	 * Otherwise we will run into a stack overflow.
	 */
	@Override
	public String toString() {
		return "PlaintextId [solutionId=" + solution.getId() + ", ciphertextId="
				+ ciphertextId + "]";
	}


}
