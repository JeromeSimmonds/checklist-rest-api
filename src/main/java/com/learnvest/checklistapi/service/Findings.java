package com.learnvest.checklistapi.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerome Simmonds
 *
 */
public class Findings<T> {

	private List<T> results = new ArrayList<T>();
	private int totalElements = 0;

	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	public int getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
}
