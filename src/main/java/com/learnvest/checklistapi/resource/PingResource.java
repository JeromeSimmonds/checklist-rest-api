package com.learnvest.checklistapi.resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.learnvest.checklistapi.ChecklistApiPath;

/**
 * @author Jerome Simmonds
 */
@Path(ChecklistApiPath.PING)
public class PingResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping() {
		return Response.status(Response.Status.OK).entity(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).build();
	}
}
