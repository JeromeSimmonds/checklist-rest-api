package com.learnvest.checklistapi.service;

/**
 * @author Jerome Simmonds
 *
 */
public class FindFilter<T> {

	private T value;
	private FindFilterType type;
	private FindFilterOperator operator = FindFilterOperator.EQUALS;

	public FindFilter() {
		super();
	}

	public FindFilter(FindFilterType type, T value) {
		this(type, FindFilterOperator.EQUALS, value);
	}

	public FindFilter(FindFilterType type, FindFilterOperator operator, T value) {
		this.value = value;
		this.type = type;
		this.operator = operator;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public FindFilterType getType() {
		return type;
	}

	public void setType(FindFilterType type) {
		this.type = type;
	}

	public FindFilterOperator getOperator() {
		return operator;
	}

	public void setOperator(FindFilterOperator operator) {
		this.operator = operator;
	}
}
