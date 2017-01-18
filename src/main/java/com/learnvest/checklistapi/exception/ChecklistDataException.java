package com.learnvest.checklistapi.exception;

/**
 * @author Jerome Simmonds
 *
 */
public class ChecklistDataException extends RuntimeException {

	private static final long serialVersionUID = -6946631198200761449L;

	public ChecklistDataException() {
	}

	public ChecklistDataException(String message) {
		super(message);
	}

	public ChecklistDataException(Throwable cause) {
		super(cause);
	}

	public ChecklistDataException(String message, Throwable cause) {
		super(message, cause);
	}
}
