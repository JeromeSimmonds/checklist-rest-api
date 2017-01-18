package com.learnvest.checklistapi.exception;

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
public class ExceptionMapperNotFound implements ExceptionMapper<NotFoundException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapperNotFound.class);

	@Override
	public Response toResponse(NotFoundException e) {
		return Response.status(Response.Status.NOT_FOUND)
				.entity(new ChecklistApiResponse<>(
						new ChecklistApiResponse.Error(ChecklistApiResponse.Error.ERRORCODE_CLIENT, e.getMessage())))
				.type(MediaType.APPLICATION_JSON).build();
	}
}
