package com.learnvest.checklistapi.service;

import javax.inject.Inject;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.learnvest.checklistapi.entity.Task;
import com.learnvest.checklistapi.exception.ChecklistDataException;
import com.learnvest.checklistapi.repository.TaskDAO;
import com.learnvest.checklistapi.specification.TaskSpecification;

/**
 * @author Jerome Simmonds
 *
 */
@Service
public class TaskServiceImpl extends AbstractService<Task, Integer, TaskDAO> implements TaskService {

	@Inject
	public TaskServiceImpl(TaskDAO dao) {
		super(dao);
	}

	@Override
	protected Specification<Task> specification(FindParameters findParameters) throws ChecklistDataException {
		return new TaskSpecification(findParameters);
	}
}
