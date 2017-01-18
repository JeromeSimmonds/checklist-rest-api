package com.learnvest.checklistapi.service;

import javax.inject.Inject;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.learnvest.checklistapi.entity.Checklist;
import com.learnvest.checklistapi.exception.ChecklistDataException;
import com.learnvest.checklistapi.repository.ChecklistDAO;
import com.learnvest.checklistapi.specification.ChecklistSpecification;

/**
 * @author Jerome Simmonds
 *
 */
@Service
public class ChecklistServiceImpl extends AbstractService<Checklist, Integer, ChecklistDAO> implements ChecklistService {

	@Inject
	public ChecklistServiceImpl(ChecklistDAO dao) {
		super(dao);
	}

	@Override
	protected Specification<Checklist> specification(FindParameters findParameters) throws ChecklistDataException {
		return new ChecklistSpecification(findParameters);
	}
}
