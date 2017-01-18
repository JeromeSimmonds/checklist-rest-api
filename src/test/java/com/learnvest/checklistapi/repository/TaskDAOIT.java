package com.learnvest.checklistapi.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.learnvest.checklistapi.PersistenceConfig;
import com.learnvest.checklistapi.TestData;
import com.learnvest.checklistapi.entity.Task;
import com.learnvest.checklistapi.service.FindFilterType;
import com.learnvest.checklistapi.service.FindParameters;
import com.learnvest.checklistapi.specification.TaskSpecification;

/**
 * @author Jerome Simmonds
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
@Transactional
@Sql({"classpath:it-data-users.sql", "classpath:it-data-checklists.sql", "classpath:it-data-tasks.sql"})
public class TaskDAOIT {

	@Inject
	private TaskDAO dao;

	@Test
	public void findOne() throws Exception {
		Task entity = dao.findOne(TestData.TASK_1.getId());
		assertNotNull(entity);
		assertEquals(TestData.TASK_1.getDescription(), entity.getDescription());

		assertNull(dao.findOne(999));
	}

	@Test
	public void save() throws Exception {
		Task entity = new Task();
		entity.setDescription("desc");
		entity.setChecklist(TestData.CHECKLIST_1);
		entity = dao.save(entity);
		assertNotNull(entity);
		assertTrue(entity.getId() > 0);
	}

	@Test
	public void delete() throws Exception {
		Task entity = dao.findOne(TestData.TASK_1.getId());
		assertNotNull(entity);
		dao.delete(entity.getId());
		entity = dao.findOne(TestData.TASK_1.getId());
		assertNull(entity);
	}

	@Test
	public void search() throws Exception {
		FindParameters params = new FindParameters(1, 12);
		List<Task> results = dao.findAll(new TaskSpecification(params));
		assertNotNull(results);
		assertTrue(results.size() == 2);

		// All by checklist
		params = new FindParameters(1, 12)
			.with(FindFilterType.CHECKLIST, TestData.CHECKLIST_1.getId());
		TaskSpecification specs = new TaskSpecification(params);
		Page<Task> page = dao.findAll(specs, specs.toPageable());
		assertNotNull(page);
		results = page.getContent();
		assertTrue(results.size() == 2);
		
		// All by non existing checklist
		params = new FindParameters(1, 12)
			.with(FindFilterType.CHECKLIST, 999);
		specs = new TaskSpecification(params);
		page = dao.findAll(specs, specs.toPageable());
		assertNotNull(page);
		results = page.getContent();
		assertTrue(results.size() == 0);
	}
}
