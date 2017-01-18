package com.learnvest.checklistapi.specification;

import org.springframework.data.domain.Pageable;

/**
 * @author Jerome Simmonds
 *
 */
public interface PageableSpecification {

	public Pageable toPageable();
}
