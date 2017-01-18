package com.learnvest.checklistapi;

import java.time.LocalDate;

import com.learnvest.checklistapi.entity.Checklist;
import com.learnvest.checklistapi.entity.Task;
import com.learnvest.checklistapi.entity.User;

/**
 * @author Jerome Simmonds
 *
 */
public class TestData {

	public final static User USER_1 = new User(1);

	public final static Checklist CHECKLIST_1 = new Checklist(1);
	
	public final static Task TASK_1 = new Task(1);
	public final static Task TASK_2 = new Task(2);

	static {
		USER_1.setUsername("user1");

		CHECKLIST_1.setName("List 1");
		CHECKLIST_1.setUser(USER_1);
		
		TASK_1.setDescription("Task 1");
		TASK_1.setChecklist(CHECKLIST_1);
		TASK_2.setDescription("Task 2");
		TASK_2.setChecklist(CHECKLIST_1);
		TASK_2.setDueDate(LocalDate.now().plusYears(1));
	}
}
