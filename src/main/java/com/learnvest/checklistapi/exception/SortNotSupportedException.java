package com.learnvest.checklistapi.exception;

import com.learnvest.checklistapi.service.FindSort;

/**
 * @author Jerome Simmonds
 *
 */
public class SortNotSupportedException extends ChecklistDataException {

	private static final long serialVersionUID = -9109142439576957671L;

	private FindSort sort;
	
	public void setSort(FindSort sort) {
		this.sort = sort;
	}

	public FindSort getSort() {
		return sort;
	}

	public SortNotSupportedException(FindSort sort) {
		this(sort, sort.name(), null);
	}
	
	public SortNotSupportedException(FindSort sort, String message) {
		this(sort, message, null);
	}

	public SortNotSupportedException(FindSort sort, String message, Throwable cause) {
		super(message, cause);
		this.sort = sort;
	}

	public SortNotSupportedException(FindSort sort, Throwable cause) {
		this(sort, sort.name(), cause);		
	}
}
