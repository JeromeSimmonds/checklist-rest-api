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
import com.learnvest.checklistapi.entity.Checklist;

/**
 * @author Jerome Simmonds
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, PersistenceConfig.class})
@Transactional
@Sql({"classpath:it-data-users.sql", "classpath:it-data-checklists.sql"})
public class ChecklistServiceIT {

	@Inject
	private ChecklistService service;

	@Test
	public void findOne() {
		Optional<Checklist> optionalEntity = service.findOne(TestData.CHECKLIST_1.getId());
		assertNotNull(optionalEntity);
		assertTrue(optionalEntity.isPresent());
		assertEquals(TestData.CHECKLIST_1.getName(), optionalEntity.get().getName());

		assertNull(service.findOne(999));
	}

	@Test
	public void find() {
		FindParameters parameters = new FindParameters(1, 12);
		Findings<Checklist> findings = service.find(parameters);
		assertNotNull(findings);
		assertNotNull(findings.getResults());
		assertTrue(findings.getResults().size() == 1);
		assertTrue(findings.getTotalElements() == 1);
		
		parameters = new FindParameters(2, 12);
		findings = service.find(parameters);
		assertNotNull(findings);
		assertNotNull(findings.getResults());
		assertTrue(findings.getResults().size() == 0);
		assertTrue(findings.getTotalElements() == 1);
		
		parameters = new FindParameters().with(FindFilterType.USER, TestData.USER_1.getId());
		findings = service.find(parameters);
		assertNotNull(findings);
		assertNotNull(findings.getResults());
		assertTrue(findings.getResults().size() == 1);
		assertTrue(findings.getTotalElements() == 1);
		
		parameters = new FindParameters().with(FindFilterType.USER, 999);
		findings = service.find(parameters);
		assertNotNull(findings);
		assertNotNull(findings.getResults());
		assertTrue(findings.getResults().size() == 0);
		assertTrue(findings.getTotalElements() == 0);
	}
}
