package com.learnvest.checklistapi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.learnvest.checklistapi.TestData;
import com.learnvest.checklistapi.entity.Task;
import com.learnvest.checklistapi.repository.TaskDAO;

/**
 * @author Jerome Simmonds
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

	@Mock
	private TaskDAO dao;

	@InjectMocks
	private TaskServiceImpl service;
	
	@Test
	public void findOne() {
		when(dao.findOne(TestData.TASK_1.getId())).thenReturn(TestData.TASK_1);
		Optional<Task> optionalEntity = service.findOne(TestData.TASK_1.getId());
		verify(dao, times(1)).findOne(TestData.TASK_1.getId());
		assertNotNull(optionalEntity);
		assertTrue(optionalEntity.isPresent());
		assertEquals(TestData.TASK_1.getId(), optionalEntity.get().getId());
	}

	@Test
	public void save() {
		Task entity = new Task();
		entity.setDescription("d");
		entity.setChecklist(TestData.CHECKLIST_1);
		
		when(dao.save(entity)).thenReturn(entityWithGeneratedId(entity));
		entity = service.save(entity);
		verify(dao, times(1)).save(entity);
		assertNotNull(entity);
		assertTrue(entity.getId() > 0);
	}

	@Test
	public void delete() {
		service.delete(TestData.TASK_1.getId());
		verify(dao, times(1)).delete(TestData.TASK_1.getId());
	}

	private Task entityWithGeneratedId(Task entity) {
		entity.setId(999);
		return entity;
	}
}
