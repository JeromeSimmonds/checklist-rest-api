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

POST /checklists  
GET /checklists  
GET /checklists/{id}  
PUT /checklists/{id}  

POST /tasks  
GET /tasks  
GET /tasks/{id}  
PUT /tasks/{id}  

## Notes

This kind of project definitely requires much more than "two to four hours" to code properly a RESTful API, a persistence layer, the unit/integration tests and provide a documentation. I spent much more than 4 hours on it but I would need more time to implement certain features, more validation, more tests. You'll see in the code some TODOs comments about some of the things I would do next.
