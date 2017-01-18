-- Requires it-data-checklists.sql to be run first
INSERT INTO tasks (id, description, checklist_id) VALUES (1, 'Task 1', 1);
INSERT INTO tasks (id, description, checklist_id, due_date) VALUES (2, 'Task 2', 1, DATE(DATE_ADD(now(), INTERVAL 1 YEAR)));