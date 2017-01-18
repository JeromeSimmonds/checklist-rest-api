package com.learnvest.checklistapi.resource;

import static com.learnvest.checklistapi.ChecklistApiConstants.ID;
import static com.learnvest.checklistapi.ChecklistApiConstants.PATH_PARAM_ID;

import java.io.Serializable;
import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.learnvest.checklistapi.ChecklistApiResponse;
import com.learnvest.checklistapi.service.BaseService;

public abstract class AbstractIdentifiableResource<E, ID extends Serializable, S extends BaseService<E, ID>, D> {

	protected S service;

	public AbstractIdentifiableResource(S service) {
		super();
		this.service = service;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(PATH_PARAM_ID)
	public Response get(@PathParam(ID) ID id) {
		Optional<E> optionalEntity = service.findOne(id);
		if (!optionalEntity.isPresent())
			return Response.status(Response.Status.NO_CONTENT).build();

		ChecklistApiResponse<D> entity = new ChecklistApiResponse<>();

		entity.setData(getDTO(optionalEntity.get()));
		return Response.status(Response.Status.OK).entity(entity).build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path(PATH_PARAM_ID)
	public Response delete(@PathParam(ID) ID id) {
		//TODO if not a coding challenge, check authorization/ownership
		service.delete(id);
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	protected abstract D getDTO(E entity);
}
