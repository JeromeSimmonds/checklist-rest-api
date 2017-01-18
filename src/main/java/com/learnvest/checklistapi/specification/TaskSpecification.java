package com.learnvest.checklistapi.specification;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.learnvest.checklistapi.entity.Task;
import com.learnvest.checklistapi.entity.Task_;
import com.learnvest.checklistapi.exception.FilterException;
import com.learnvest.checklistapi.exception.SortNotSupportedException;
import com.learnvest.checklistapi.service.FindFilterType;
import com.learnvest.checklistapi.service.FindParameters;
import com.learnvest.checklistapi.service.FindSort;

/**
 * @author Jerome Simmonds
 *
 */
public class TaskSpecification extends AbstractSpecification<Task> {

	public TaskSpecification(FindParameters parameters) {
		super(parameters);
	}

	@Override
	protected <S> Expression<S> getField(Root<Task> root, Class<S> resultType, FindFilterType type) {
		switch (type) {
			case ID:
			case CHECKLIST:
				return (Expression<S>) root.get(Task_.checklist);
			case COMPLETE:
				return (Expression<S>) root.get(Task_.complete);
			case DUE_DATE:
				return (Expression<S>) root.get(Task_.dueDate);
			default:
				throw new FilterException(type);
		}
	}

	@Override
	protected void applySort(FindSort findSort, List<Order> orders) {
		switch (findSort) {
			case MOST_RECENT:
				orders.add(new Order(Sort.Direction.ASC, Task_.id.getName()));
				break;
			case DUE_DATE:
				orders.add(new Order(Sort.Direction.ASC, Task_.dueDate.getName()));
				break;
			default:
				throw new SortNotSupportedException(findSort);
		}
	}
}
