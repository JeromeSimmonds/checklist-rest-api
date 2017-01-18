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
import com.learnvest.checklistapi.entity.Checklist;
import com.learnvest.checklistapi.repository.ChecklistDAO;

/**
 * @author Jerome Simmonds
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ChecklistServiceTest {

	@Mock
	private ChecklistDAO dao;

	@InjectMocks
	private ChecklistServiceImpl service;
	
	@Test
	public void findOne() {
		when(dao.findOne(TestData.CHECKLIST_1.getId())).thenReturn(TestData.CHECKLIST_1);
		Optional<Checklist> optionalEntity = service.findOne(TestData.CHECKLIST_1.getId());
		verify(dao, times(1)).findOne(TestData.CHECKLIST_1.getId());
		assertNotNull(optionalEntity);
		assertTrue(optionalEntity.isPresent());
		assertEquals(TestData.CHECKLIST_1.getId(), optionalEntity.get().getId());
	}

	@Test
	public void save() {
		Checklist entity = new Checklist();
		entity.setName("List test");
		entity.setUser(TestData.USER_1);
		
		when(dao.save(entity)).thenReturn(entityWithGeneratedId(entity));
		entity = service.save(entity);
		verify(dao, times(1)).save(entity);
		assertNotNull(entity);
		assertTrue(entity.getId() > 0);
	}

	@Test
	public void delete() {
		service.delete(TestData.CHECKLIST_1.getId());
		verify(dao, times(1)).delete(TestData.CHECKLIST_1.getId());
	}

	private Checklist entityWithGeneratedId(Checklist entity) {
		entity.setId(999);
		return entity;
	}
}
