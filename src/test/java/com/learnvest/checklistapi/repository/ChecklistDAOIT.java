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
import com.learnvest.checklistapi.entity.Checklist;
import com.learnvest.checklistapi.service.FindFilterType;
import com.learnvest.checklistapi.service.FindParameters;
import com.learnvest.checklistapi.specification.ChecklistSpecification;

/**
 * @author Jerome Simmonds
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
@Transactional
@Sql({"classpath:it-data-users.sql", "classpath:it-data-checklists.sql"})
public class ChecklistDAOIT {

	@Inject
	private ChecklistDAO dao;

	@Test
	public void findOne() throws Exception {
		Checklist entity = dao.findOne(TestData.CHECKLIST_1.getId());
		assertNotNull(entity);
		assertEquals(TestData.CHECKLIST_1.getName(), entity.getName());

		assertNull(dao.findOne(999));
	}

	@Test
	public void save() throws Exception {
		Checklist entity = new Checklist();
		entity.setName("List test");
		entity.setUser(TestData.USER_1);
		entity = dao.save(entity);
		assertNotNull(entity);
		assertTrue(entity.getId() > 0);
	}

	@Test
	public void delete() throws Exception {
		Checklist entity = dao.findOne(TestData.CHECKLIST_1.getId());
		assertNotNull(entity);
		dao.delete(entity.getId());
		entity = dao.findOne(TestData.CHECKLIST_1.getId());
		assertNull(entity);
	}

	@Test
	public void search() throws Exception {
		FindParameters params = new FindParameters(1, 12);
		List<Checklist> results = dao.findAll(new ChecklistSpecification(params));
		assertNotNull(results);
		assertTrue(results.size() == 1);

		// All by user
		params = new FindParameters(1, 12)
			.with(FindFilterType.USER, TestData.USER_1.getId());
		ChecklistSpecification specs = new ChecklistSpecification(params);
		Page<Checklist> page = dao.findAll(specs, specs.toPageable());
		assertNotNull(page);
		results = page.getContent();
		assertTrue(results.size() == 1);
		
		// All by non existing user
		params = new FindParameters(1, 12)
			.with(FindFilterType.USER, 999);
		specs = new ChecklistSpecification(params);
		page = dao.findAll(specs, specs.toPageable());
		assertNotNull(page);
		results = page.getContent();
		assertTrue(results.size() == 0);
	}
}
