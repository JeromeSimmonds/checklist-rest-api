package com.learnvest.checklistapi.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerome Simmonds
 *
 */
public class FindParameters {

	private int pageNumber = 1;
	private int pageSize = 12; // size of the page to be returned
	private List<FindSort> sorts = new ArrayList<FindSort>();
	private List<FindFilter<?>> filters = new ArrayList<FindFilter<?>>();

	public FindParameters() {
		super();
	}

	public FindParameters(int pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int page) {
		this.pageNumber = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<FindSort> getSorts() {
		return sorts;
	}
	public void setSorts(List<FindSort> sorts) {
		this.sorts = sorts;
	}
	public List<FindFilter<?>> getFilters() {
		return filters;
	}
	public void setFilters(List<FindFilter<?>> filters) {
		this.filters = filters;
	}
	
	public <T> FindParameters with(FindFilterType type, FindFilterOperator operator, T value) {
		if (getFilters() == null) setFilters(new ArrayList<FindFilter<?>>());
		getFilters().add(new FindFilter<T>(type, operator, value));
		return this;
	}
	
	public <T> FindParameters with(FindFilterType type, T value) {
		if (getFilters() == null) setFilters(new ArrayList<FindFilter<?>>());
		getFilters().add(new FindFilter<T>(type, FindFilterOperator.EQUALS, value));
		return this;
	}
	
	public FindParameters with(FindSort sort) {
		sorts.add(sort);
		return this;
	}
}
