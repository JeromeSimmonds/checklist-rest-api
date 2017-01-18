package com.learnvest.checklistapi.specification;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.learnvest.checklistapi.entity.Checklist;
import com.learnvest.checklistapi.entity.Checklist_;
import com.learnvest.checklistapi.exception.FilterException;
import com.learnvest.checklistapi.exception.SortNotSupportedException;
import com.learnvest.checklistapi.service.FindFilterType;
import com.learnvest.checklistapi.service.FindParameters;
import com.learnvest.checklistapi.service.FindSort;

/**
 * @author Jerome Simmonds
 *
 */
public class ChecklistSpecification extends AbstractSpecification<Checklist> {

	public ChecklistSpecification(FindParameters parameters) {
		super(parameters);
	}

	@Override
	protected <S> Expression<S> getField(Root<Checklist> root, Class<S> resultType, FindFilterType type) {
		switch (type) {
			case ID:
			case USER:
				return (Expression<S>) root.get(Checklist_.user);
			case NAME:
				return (Expression<S>) root.get(Checklist_.name);
			default:
				throw new FilterException(type);
		}
	}

	@Override
	protected void applySort(FindSort findSort, List<Order> orders) {
		switch (findSort) {
			case ALPHA:
				orders.add(new Order(Sort.Direction.ASC, Checklist_.name.getName()));
				break;
			case REVERSE_ALPHA:
				orders.add(new Order(Sort.Direction.DESC, Checklist_.name.getName()));
				break;
			case MOST_RECENT:
				orders.add(new Order(Sort.Direction.ASC, Checklist_.id.getName()));
				break;
			default:
				throw new SortNotSupportedException(findSort);
		}
	}
}
