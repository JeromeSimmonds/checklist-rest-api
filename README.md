# checklist-rest-api
Checklist management application.

## Requirements
Java 8  
Maven 3+  
MySQL 5.6.5+  

## Setup

Copy `etc/checklist-api.properties.sample` to `etc/checklist-api.properties` and modify the properties in `checklist-api.properties` accordingly. 

Run the following command (with the same configuration from checklist-api.properties)  
`mvn liquibase:update -Dchecklist.database.host=localhost -Dchecklist.database.schema=checklist -Dchecklist.database.username=xxx -Dchecklist.database.password=xxx`

Create at least 1 user in the users table:  
`INSERT INTO users(id, username) VALUES(1, 'user1');`

Run  
`mvn jetty:run`

## Endpoints

Root path http://localhost:8980/v1  

* POST /checklists  
Create a checklist. Payload example `{"name": "List 1", "userId": 1}`  
* GET /checklists  
Get all checklists. Optional filter with query string param `userId=1`  
* GET /checklists/{ID}  
Get a checklist by ID.
* PUT /checklists/{ID}  
Update a checklist. Payload example `{"name": "List 1", "userId": 1}`

* POST /tasks  
Create a task. Payload example `{"description": "Task 1", "checklistId": 1}` optional parameters `"dueDate": "2017-01-30"`
* GET /tasks  
Get all tasks. Optional filter with query string param `checklistId=1`. Optional sort with query string param `sort=dueDate` (default) or `sort=created` (least recent first).  
* GET /tasks/{ID}  
Get a task by ID.  
* PUT /tasks/{ID}  
Update a task. Payload example `{"description": "Task 1", "checklistId": 1}` optional parameters `"dueDate": "2017-01-30"` and  `"complete": true`  

## Tests

For integration tests you need a test database: copy `src/test/resources/persistence.properties.sample` to `src/test/resources/persistence.properties` and modify the properties in `persistence.properties` accordingly (use a different schema than the one created above in setup).  
Run the following command (with the same configuration from persistence.properties)  
`mvn liquibase:update -Dchecklist.database.host=localhost -Dchecklist.database.schema=checklist_test -Dchecklist.database.username=xxx -Dchecklist.database.password=xxx`

## Notes

For a project that encompasses a RESTful API with a persistence layer, including the unit/integration tests and documentation, it definitely requires much more than "two to four hours" to code properly. I spent more than 4 hours on it but I would need more time to implement certain features, like more validations and more tests. Some of the things I would do next are seen in the code under the TODOs comments.