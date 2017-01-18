package com.learnvest.checklistapi.exception;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
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
public class ExceptionMapperForbidden implements ExceptionMapper<ForbiddenException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapperForbidden.class);

	@Override
	public Response toResponse(ForbiddenException e) {
		return Response.status(Response.Status.FORBIDDEN)
				.entity(new ChecklistApiResponse<>(
						new ChecklistApiResponse.Error(ChecklistApiResponse.Error.ERRORCODE_AUTHORIZATION, e.getMessage())))
				.type(MediaType.APPLICATION_JSON).build();
	}
}
