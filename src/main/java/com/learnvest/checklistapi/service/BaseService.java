package com.learnvest.checklistapi.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.learnvest.checklistapi.exception.ChecklistDataException;
import com.learnvest.checklistapi.service.FindParameters;
import com.learnvest.checklistapi.service.Findings;

/**
 * @author Jerome Simmonds
 *
 * @param <T> Entity type
 * @param <ID> Entity ID type
 */
public interface BaseService<T, ID extends Serializable> {

	long count();

	Optional<T> findOne(ID id);

	List<T> findAll(Collection<ID> ids);

	Findings<T> find(FindParameters parameters);

	T save(T entity) throws ChecklistDataException;

	void delete(ID id) throws ChecklistDataException;

	void delete(T entity) throws ChecklistDataException;
}
