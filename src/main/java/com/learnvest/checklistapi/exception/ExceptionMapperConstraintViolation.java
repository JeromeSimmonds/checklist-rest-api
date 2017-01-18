package com.learnvest.checklistapi.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.learnvest.checklistapi.ChecklistApiConstants;
import com.learnvest.checklistapi.ChecklistApiResponse;

/**
 * @author Jerome Simmonds
 *
 */
@Provider
public class ExceptionMapperConstraintViolation implements ExceptionMapper<ConstraintViolationException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapperConstraintViolation.class);

	@Override
	public Response toResponse(ConstraintViolationException e) {
		ChecklistApiResponse.Error error = new ChecklistApiResponse.Error();
		error.setCode(ChecklistApiResponse.Error.ERRORCODE_VALIDATION);
		List<ChecklistApiResponse.Error> errors = new ArrayList<>();
		for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
			String path = cv.getPropertyPath().toString();
			errors.add(new ChecklistApiResponse.Error(cv.getMessage(), path.substring(path.lastIndexOf(ChecklistApiConstants.DOT) + 1)));
		}
		error.setErrors(errors);
		return Response.status(ChecklistApiResponse.HTTP_STATUS_CODE_422)
				.entity(new ChecklistApiResponse<>(error))
				.type(MediaType.APPLICATION_JSON).build();
	}
}
