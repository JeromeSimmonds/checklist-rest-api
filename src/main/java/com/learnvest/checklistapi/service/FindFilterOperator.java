package com.learnvest.checklistapi.service;

/**
 * @author Jerome Simmonds
 *
 */
public enum FindFilterOperator {
	EQUALS,
	NOT_EQUALS,
	IN,
	NOT_IN,
	IS_NULL,
	IS_NOT_NULL,
	GREATER_THAN,
	GREATER_OR_EQUALS,
	LESS_THAN,
	LESS_OR_EQUALS,
	LIKE,
	NOT_LIKE,
	STARTS_WITH,
	ENDS_WITH
}
