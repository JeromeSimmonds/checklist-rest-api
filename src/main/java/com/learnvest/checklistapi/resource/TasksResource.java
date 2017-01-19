package com.learnvest.checklistapi.resource;

import static com.learnvest.checklistapi.ChecklistApiConstants.CHECKLIST_ID;
import static com.learnvest.checklistapi.ChecklistApiConstants.ID;
import static com.learnvest.checklistapi.ChecklistApiConstants.PAGE;
import static com.learnvest.checklistapi.ChecklistApiConstants.PATH_PARAM_ID;
import static com.learnvest.checklistapi.ChecklistApiConstants.SORT;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.learnvest.checklistapi.dto.TaskDTO;
import com.learnvest.checklistapi.entity.Checklist;
import com.learnvest.checklistapi.entity.Task;
import com.learnvest.checklistapi.service.BaseService;
import com.learnvest.checklistapi.service.FindFilterType;
import com.learnvest.checklistapi.service.FindParameters;
import com.learnvest.checklistapi.service.FindSort;
import com.learnvest.checklistapi.service.Findings;
import com.learnvest.checklistapi.service.TaskService;

/**
 * @author Jerome Simmonds
 */
@Path(ChecklistApiPath.TASKS)
public class TasksResource extends AbstractIdentifiableResource<Task, Integer, BaseService<Task, Integer>, TaskDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TasksResource.class);

	public static final int DEFAULT_PAGE_SIZE = 12;
	
	@Inject
	public TasksResource(TaskService taskService) {
		super(taskService);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTasks(
			@QueryParam(PAGE) @DefaultValue("1") int page,
			@QueryParam(SORT) @DefaultValue("dueDate") String sort,
			@QueryParam(CHECKLIST_ID) @DefaultValue("0") int checkListId) {
		
		//TODO if not a coding challenge, also allow path /checklists/{ID}/tasks in addition to /tasks?checklistId={ID}
		
		//TODO if not a coding challenge, implement filters and sorts
		
		FindParameters parameters = new FindParameters(page, DEFAULT_PAGE_SIZE);
		if (checkListId > 0)
			parameters.with(FindFilterType.CHECKLIST, checkListId);
		if (sort.equals("created")) //TODO if not a coding challenge, code enum with available sorts and implement more sorts and order (asc/desc)
			parameters.with(FindSort.MOST_RECENT);
		else
			parameters.with(FindSort.DUE_DATE);
		
		Findings<Task> findings = service.find(parameters);
		
		List<TaskDTO> dtos = findings.getResults().stream()
			.map(e -> getDTO(e))
			.collect(Collectors.<TaskDTO>toList());
		
		ChecklistApiResponse.Metadata metadata = new ChecklistApiResponse.Metadata();
		metadata.setPage(page);
		metadata.setPageSize(DEFAULT_PAGE_SIZE);
		metadata.setTotalAvailable(findings.getTotalElements());
		
		ChecklistApiResponse<List<TaskDTO>> entity = new ChecklistApiResponse<>();
		entity.setData(dtos);
		entity.setMetadata(metadata);
		return Response.status(Response.Status.OK).entity(entity).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTask(@NotNull @Valid TaskDTO dto, @Context UriInfo uriInfo) {
		ChecklistApiResponse<TaskDTO> entity = new ChecklistApiResponse<>();

		//TODO if not a coding challenge, check that the checklist exists
		
		Task task = getEntity(dto);
		task.setComplete(false);
		task = service.save(task);
		
		entity.setData(getDTO(task));
		URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(task.getId())).build();
		return Response.created(uri).entity(entity).build();
	}

	@PUT //TODO if not a coding challenge, also implements PATCH, here PUT requires the whole entity
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(PATH_PARAM_ID)
	public Response updateTask(@PathParam(ID) int id, @NotNull(message = "Payload required") @Valid TaskDTO dto) {
		//TODO if not a coding challenge, check if exists, then check authorization/ownership

		Task task = getEntity(dto);
		task.setId(id);
		task = service.save(task);
		
		return Response.status(Response.Status.OK).entity(getDTO(task)).build();
	}

	@Override
	protected TaskDTO getDTO(Task entity) {
		TaskDTO dto = new TaskDTO();
		dto.setId(entity.getId());
		dto.setChecklistId(entity.getChecklist().getId());
		dto.setDescription(entity.getDescription());
		if (entity.getDueDate() != null)
			dto.setDueDate(entity.getDueDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
		dto.setComplete(entity.getComplete());
		return dto;
	}

	protected Task getEntity(TaskDTO dto) {
		Task entity = new Task();
		entity.setId(dto.getId());
		entity.setChecklist(new Checklist(dto.getChecklistId()));
		entity.setDescription(dto.getDescription());
		if (dto.getDueDate() != null) //TODO handle malformed date
			entity.setDueDate(LocalDate.parse(dto.getDueDate(), DateTimeFormatter.ISO_LOCAL_DATE));
		entity.setComplete(dto.getComplete());
		return entity;
	}
}
