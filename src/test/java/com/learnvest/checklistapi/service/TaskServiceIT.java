package com.learnvest.checklistapi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.learnvest.checklistapi.PersistenceConfig;
import com.learnvest.checklistapi.SpringConfig;
import com.learnvest.checklistapi.TestData;
import com.learnvest.checklistapi.entity.Task;

/**
 * @author Jerome Simmonds
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, PersistenceConfig.class})
@Transactional
@Sql({"classpath:it-data-users.sql", "classpath:it-data-checklists.sql", "classpath:it-data-tasks.sql"})
public class TaskServiceIT {

	@Inject
	private TaskService service;

	@Test
	public void findOne() {
		Optional<Task> optionalEntity = service.findOne(TestData.TASK_1.getId());
		assertNotNull(optionalEntity);
		assertTrue(optionalEntity.isPresent());
		assertEquals(TestData.TASK_1.getDescription(), optionalEntity.get().getDescription());

		assertNull(service.findOne(999));
	}

	@Test
	public void find() {
		FindParameters parameters = new FindParameters(1, 12);
		Findings<Task> findings = service.find(parameters);
		assertNotNull(findings);
		assertNotNull(findings.getResults());
		assertTrue(findings.getResults().size() == 2);
		assertTrue(findings.getTotalElements() == 2);
		
		parameters = new FindParameters(2, 12);
		findings = service.find(parameters);
		assertNotNull(findings);
		assertNotNull(findings.getResults());
		assertTrue(findings.getResults().size() == 0);
		assertTrue(findings.getTotalElements() == 2);
		
		parameters = new FindParameters().with(FindFilterType.CHECKLIST, TestData.CHECKLIST_1.getId());
		findings = service.find(parameters);
		assertNotNull(findings);
		assertNotNull(findings.getResults());
		assertTrue(findings.getResults().size() == 2);
		assertTrue(findings.getTotalElements() == 2);
		
		parameters = new FindParameters().with(FindFilterType.CHECKLIST, 999);
		findings = service.find(parameters);
		assertNotNull(findings);
		assertNotNull(findings.getResults());
		assertTrue(findings.getResults().size() == 0);
		assertTrue(findings.getTotalElements() == 0);
	}
}
