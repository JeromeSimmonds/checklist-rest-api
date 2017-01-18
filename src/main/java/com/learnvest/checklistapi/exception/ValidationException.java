package com.learnvest.checklistapi.exception;

/**
 * @author Jerome Simmonds
 *
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 6153952718394260756L;

	public static final String MSG_VALIDATION_ERROR = "Validation error";
	public static final String MSG_NAME_MISSING = "Name missing";
	public static final String MSG_DESCRIPTION_MISSING = "Description missing";

	public ValidationException() {
		super(MSG_VALIDATION_ERROR);
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
