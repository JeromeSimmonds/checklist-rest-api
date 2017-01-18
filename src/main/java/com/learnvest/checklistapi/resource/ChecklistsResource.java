package com.learnvest.checklistapi.resource;

import static com.learnvest.checklistapi.ChecklistApiConstants.CHECKLIST_ID;
import static com.learnvest.checklistapi.ChecklistApiConstants.ID;
import static com.learnvest.checklistapi.ChecklistApiConstants.PAGE;
import static com.learnvest.checklistapi.ChecklistApiConstants.PATH_PARAM_ID;
import static com.learnvest.checklistapi.ChecklistApiConstants.USER_ID;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.learnvest.checklistapi.ChecklistApiPath;
import com.learnvest.checklistapi.ChecklistApiResponse;
import com.learnvest.checklistapi.dto.ChecklistDTO;
import com.learnvest.checklistapi.dto.UserDTO;
import com.learnvest.checklistapi.entity.Checklist;
import com.learnvest.checklistapi.entity.User;
import com.learnvest.checklistapi.service.BaseService;
import com.learnvest.checklistapi.service.ChecklistService;
import com.learnvest.checklistapi.service.FindFilterType;
import com.learnvest.checklistapi.service.FindParameters;
import com.learnvest.checklistapi.service.FindSort;
import com.learnvest.checklistapi.service.Findings;

/**
 * @author Jerome Simmonds
 */
@Path(ChecklistApiPath.CHECKLISTS)
public class ChecklistsResource extends AbstractIdentifiableResource<Checklist, Integer, BaseService<Checklist, Integer>, ChecklistDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChecklistsResource.class);

	public static final int DEFAULT_PAGE_SIZE = 12;
	
	@Inject
	public ChecklistsResource(ChecklistService checklistService) {
		super(checklistService);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChecklists(
			@QueryParam(PAGE) @DefaultValue("1") int page,
			@QueryParam(USER_ID) @DefaultValue("0") int userId) {
		
		//TODO if not a coding challenge, implement filters and sorts
		
		FindParameters parameters = new FindParameters(page, DEFAULT_PAGE_SIZE)
			.with(FindSort.ALPHA);
		if (userId > 0)
			parameters.with(FindFilterType.USER, userId);
		Findings<Checklist> findings = service.find(parameters);
		
		List<ChecklistDTO> dtos = findings.getResults().stream()
			.map(e -> getDTO(e))
			.collect(Collectors.<ChecklistDTO>toList());
		
		ChecklistApiResponse.Metadata metadata = new ChecklistApiResponse.Metadata();
		metadata.setPage(page);
		metadata.setPageSize(DEFAULT_PAGE_SIZE);
		metadata.setTotalAvailable(findings.getTotalElements());
		
		ChecklistApiResponse<List<ChecklistDTO>> entity = new ChecklistApiResponse<>();
		entity.setData(dtos);
		entity.setMetadata(metadata);
		return Response.status(Response.Status.OK).entity(entity).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createChecklist(@NotNull @Valid ChecklistDTO dto, @Context UriInfo uriInfo) {
		ChecklistApiResponse<ChecklistDTO> entity = new ChecklistApiResponse<>();

		//TODO if not a coding challenge, check that the user exists
		//TODO if not a coding challenge, check that user doesn't already have a checklist with the same name
		
		Checklist checklist = getEntity(dto);
		checklist = service.save(checklist);
		
		entity.setData(getDTO(checklist));
		URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(checklist.getId())).build();
		return Response.created(uri).entity(entity).build();
	}

	@PUT //TODO if not a coding challenge, also implements PATCH, here PUT requires the whole entity
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(PATH_PARAM_ID)
	public Response updateChecklist(@PathParam(ID) int id, @NotNull(message = "Payload required") @Valid ChecklistDTO dto) {
		//TODO if not a coding challenge, check if exists, then check authorization/ownership

		Checklist checklist = getEntity(dto);
		checklist.setId(id);
		checklist = service.save(checklist);
		
		return Response.status(Response.Status.OK).entity(getDTO(checklist)).build();
	}

	@Override
	protected ChecklistDTO getDTO(Checklist entity) {
		ChecklistDTO dto = new ChecklistDTO();
		dto.setId(entity.getId());
		if (entity.getUser() != null) {
			UserDTO userDTO = new UserDTO();
			userDTO.setId(entity.getUser().getId());
			userDTO.setUsername(entity.getUser().getUsername());
			dto.setUserId(entity.getUser().getId());
			dto.setUser(userDTO);
		}
		dto.setName(entity.getName());
		return dto;
	}

	protected Checklist getEntity(ChecklistDTO dto) {
		Checklist entity = new Checklist();
		entity.setUser(new User(dto.getUserId()));
		entity.setName(dto.getName());
		return entity;
	}
}
