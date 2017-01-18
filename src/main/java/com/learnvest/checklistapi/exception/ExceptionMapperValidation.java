package com.learnvest.checklistapi.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.learnvest.checklistapi.ChecklistApiResponse;

/**
 * @author Jerome Simmonds
 *
 */
@Provider
public class ExceptionMapperValidation implements ExceptionMapper<ValidationException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapperValidation.class);

	@Override
	public Response toResponse(ValidationException e) {
		return Response.status(ChecklistApiResponse.HTTP_STATUS_CODE_422)
				.entity(new ChecklistApiResponse<>(
						new ChecklistApiResponse.Error(ChecklistApiResponse.Error.ERRORCODE_VALIDATION, e.getMessage())))
				.type(MediaType.APPLICATION_JSON).build();
	}
}
