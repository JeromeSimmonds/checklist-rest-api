package com.learnvest.checklistapi.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.learnvest.checklistapi.exception.ChecklistDataException;
import com.learnvest.checklistapi.repository.BaseDAO;
import com.learnvest.checklistapi.specification.PageableSpecification;

/**
 * @author Jerome Simmonds
 *
 * @param <T> Entity type
 * @param <ID> Entity ID type
 * @param <S> Data Access Object type
 */
public abstract class AbstractService<T, ID extends Serializable, S extends BaseDAO<T, ID>> implements BaseService<T, ID> {

	protected final S dao;

	public AbstractService(S dao) {
		super();
		this.dao = dao;
	}

	@Override
	public long count() throws ChecklistDataException {
		return dao.count();
	}
	
	@Override
	public Optional<T> findOne(ID id) {
		return Optional.ofNullable(dao.findOne(id));
	}

	@Override
	public List<T> findAll(Collection<ID> ids) {
		return dao.findAll(ids);
	}

	@Override
	@Transactional(readOnly = true)
	public Findings<T> find(FindParameters parameters) {
		if (parameters == null)
			throw new ChecklistDataException("Search parameters can not be null.");

		Findings<T> findings = new Findings<T>();
		Specification<T> specification = specification(parameters);
		Page<T> page = null;
		if (specification != null) {
			Pageable pageable = null;
			if (specification instanceof PageableSpecification) {
				PageableSpecification findSpec = (PageableSpecification) specification;
				pageable = findSpec.toPageable();
			}
			page = dao.findAll(specification, pageable);
		} else {
			page = dao.findAll(new PageRequest(parameters.getPageNumber() - 1, parameters.getPageSize()));
		}
		findings.setResults(page.getContent());
		findings.setTotalElements((int) page.getTotalElements());

		return findings;
	}

	@Override
	@Transactional
	public T save(T entity) throws ChecklistDataException {
		if (entity == null)
			throw new ChecklistDataException("Entity to save cannot be null.");
		return dao.save(entity);
	}

	@Override
	@Transactional
	public void delete(T entity) throws ChecklistDataException {
		if (entity == null)
			throw new ChecklistDataException("Entity to delete cannot be null");
		dao.delete(entity);
	}

	@Override
	@Transactional
	public void delete(ID id) {
		dao.delete(id);
	}

	protected abstract Specification<T> specification(FindParameters findParameters) throws ChecklistDataException;
}
