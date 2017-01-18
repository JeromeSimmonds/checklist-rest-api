package com.learnvest.checklistapi.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import com.learnvest.checklistapi.exception.FilterException;
import com.learnvest.checklistapi.exception.FilterException.FilterProperty;
import com.learnvest.checklistapi.service.FindFilter;
import com.learnvest.checklistapi.service.FindFilterType;
import com.learnvest.checklistapi.service.FindParameters;
import com.learnvest.checklistapi.service.FindSort;

/**
 * @author Jerome Simmonds
 *
 * @param <T> Entity type
 */
public abstract class AbstractSpecification<T> implements Specification<T>, PageableSpecification {

	private static final String PERCENT_SIGN = "%";

	private FindParameters parameters;
	
	public AbstractSpecification(FindParameters parameters) {
		this.parameters = parameters;
	}
	
	public Pageable toPageable() {
		return new PageRequest(parameters.getPageNumber() - 1, parameters.getPageSize(), getSort());
	}

	private Sort getSort() {
		if (parameters.getSorts() == null || parameters.getSorts().isEmpty()) return null;
		return new Sort(getOrders(parameters.getSorts()));
	}

	private List<Order> getOrders(List<FindSort> findSorts) {
		List<Order> orders = new ArrayList<Sort.Order>();
		for (FindSort s : findSorts) {
			applySort(s, orders);
		}
		return orders;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Predicate predicate = builder.isTrue(builder.literal(Boolean.TRUE)); // To start concatenating
		
		for (FindFilter<?> filter : parameters.getFilters()) {
			predicate = builder.and(predicate, applyFilter(root, builder, filter));
		}
			
		return predicate;
	}

	private Predicate applyFilter(Root<T> root, CriteriaBuilder builder, FindFilter<?> filter) {
		switch (filter.getOperator()) {
			case EQUALS:
				return builder.equal(getField(root, Object.class, filter.getType()), filter.getValue());
			case NOT_EQUALS:
				return builder.notEqual(getField(root, Object.class, filter.getType()), filter.getValue());
			case GREATER_OR_EQUALS:
				if (filter.getValue() instanceof Date) {
					return builder.greaterThanOrEqualTo(getField(root, Date.class, filter.getType()), (Date) filter.getValue());
				} else {
					return builder.ge(getField(root, Number.class, filter.getType()), (Number) filter.getValue());
				}
			case GREATER_THAN:
				if (filter.getValue() instanceof Date) {
					return builder.greaterThan(getField(root, Date.class, filter.getType()), (Date) filter.getValue());
				} else {
					return builder.gt(getField(root, Number.class, filter.getType()), (Number) filter.getValue());
				}			
			case IN:
				if (filter.getValue() != null && filter.getValue() instanceof Collection && !((Collection<?>) filter.getValue()).isEmpty())
					return getField(root, Collection.class, filter.getType()).in((Collection<?>) filter.getValue());
				else
					throw new FilterException(filter, FilterProperty.Mode, "Filter value is required");
			case NOT_IN:
				return builder.not(getField(root, Collection.class, filter.getType()).in(filter.getValue()));
			case IS_NOT_NULL:
				return builder.isNotNull(getField(root, Object.class, filter.getType()));
			case IS_NULL:
				return builder.isNull(getField(root, Object.class, filter.getType()));
			case LESS_OR_EQUALS:
				if (filter.getValue() instanceof Date) {
					return builder.lessThanOrEqualTo(getField(root, Date.class, filter.getType()), (Date) filter.getValue());
				} else {
					return builder.le(getField(root, Number.class, filter.getType()), (Number) filter.getValue());
				}
			case LESS_THAN:
				if (filter.getValue() instanceof Date) {
					return builder.lessThan(getField(root, Date.class, filter.getType()), (Date) filter.getValue());
				} else {
					return builder.lt(getField(root, Number.class, filter.getType()), (Number) filter.getValue());
				}
			case LIKE:
				return builder.like(getField(root, String.class, filter.getType()), PERCENT_SIGN + filter.getValue() + PERCENT_SIGN);
			case NOT_LIKE:
				return builder.notLike(getField(root, String.class, filter.getType()), (String) filter.getValue());
			case STARTS_WITH:
				return builder.like(getField(root, String.class, filter.getType()), filter.getValue() + PERCENT_SIGN);
			case ENDS_WITH:
				return builder.like(getField(root, String.class, filter.getType()), PERCENT_SIGN + filter.getValue());
			default:
				throw new FilterException(filter, FilterProperty.Mode);
		}
	}
	
	public FindParameters getParameters() {
		return parameters;
	}

	protected abstract <S> Expression<S> getField(Root<T> root, Class<S> resultType, FindFilterType type);
	
	protected abstract void applySort(FindSort findSort, List<Order> orders);
}
