package com.learnvest.checklistapi.resource;

import static com.learnvest.checklistapi.ChecklistApiConstants.APPLICATION_PATH;
import static com.learnvest.checklistapi.ChecklistApiConstants.EMPTY;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Jerome Simmonds
 */
@Path(EMPTY)
public class HomeResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response home() {
		return Response.status(Response.Status.OK).entity("Checklist API " + APPLICATION_PATH).build();
	}
}
