package com.learnvest.checklistapi.exception;

import com.learnvest.checklistapi.service.FindFilter;
import com.learnvest.checklistapi.service.FindFilterOperator;
import com.learnvest.checklistapi.service.FindFilterType;

/**
 * @author Jerome Simmonds
 *
 */
public class FilterException extends ChecklistDataException {

	private static final long serialVersionUID = -7648070219802572025L;

	public enum FilterProperty {
		Type,
		Mode
	}
	
	private FindFilter<?> filter;
	private FilterProperty property;
	
	public FilterException(FindFilter<?> filter, FilterProperty property) {
		super(filter.toString() + " : " + property.toString());
		this.filter = filter;
		this.property = property;
	}

	public FilterException(FindFilter<?> filter, FilterProperty property, String message, Throwable cause) {
		super(filter.toString() + " : " + property.toString() + ". " + message, cause);
		this.filter = filter;
		this.property = property;
	}
	
	public FilterException(FindFilter<?> filter, FilterProperty property, String message) {
		super(filter.toString() + " : " + property.toString() + ". " + message);
		this.filter = filter;
		this.property = property;
	}

	public FilterException(FindFilter<?> filter, FilterProperty property, Throwable cause) {
		super(filter.toString() + " : " + property.toString(), cause);
		this.filter = filter;
		this.property = property;
	}

	public FilterException(FindFilterOperator filterOperator) {
		super("Filter operator not supported: " + filterOperator.toString());
	}

	public FilterException(FindFilterType filterType) {
		super("Filter type not supported: " + filterType.toString());
	}

	public FindFilter<?> getFilter() {
		return filter;
	}

	public FilterProperty getProperty() {
		return property;
	}
}
