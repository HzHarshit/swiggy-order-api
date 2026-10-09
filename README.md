\# Swiggy Order API



A backend REST API project built using Java and Spring Boot to demonstrate order management, authentication, caching, and event-driven processing.



\## Tech Stack



\- Java 21 (Docker runtime)

\- Spring Boot

\- Spring Data JPA and Hibernate

\- MySQL

\- Spring Security and JWT

\- Redis

\- Apache Kafka and ZooKeeper

\- Maven

\- Docker

\- Swagger UI / OpenAPI



\## Features



\- REST APIs for orders, customers, and restaurants

\- CRUD operations and request validation

\- Pagination, sorting, and custom queries

\- Centralized exception handling

\- JWT-based registration and login

\- Role-based authorization

\- Redis caching

\- Kafka producer and consumer

\- Transactional Outbox pattern for order events

\- Interactive API documentation with Swagger UI

\- Docker-based deployment



\## Architecture



1\. REST controllers receive API requests.

2\. Service classes implement business logic.

3\. Spring Data JPA repositories interact with MySQL.

4\. Redis caches selected customer data.

5\. Order creation stores the order and its outbox event in a database transaction.

6\. An outbox publisher sends pending events to Kafka.

7\. A Kafka consumer processes order-created events.



\## Running with Docker



Build the application image from the project root:



```bash

docker build -t swiggy-order-api .

```



The current local deployment uses Windows port `8086`, mapped to application port `8085`.



Swagger UI:



http://localhost:8086/swagger-ui/index.html



The MySQL database, Redis, Kafka, and ZooKeeper services must be running and reachable using the Docker profile configuration.



\## API Documentation



The Swagger UI provides interactive documentation for authentication, order, customer, and restaurant endpoints.



Use Swagger to inspect request schemas, execute API calls, and review responses.



\## Security



Database credentials are supplied through environment variables. Do not commit passwords, JWT secrets, or other sensitive information to version control.



\## Testing



API endpoints can be tested through Swagger UI or Postman. Automated tests and complete integration verification should be run before claiming full test coverage.



\## Future Improvements



\- Expand automated unit and integration tests

\- Add Docker Compose for easier multi-service startup

\- Add CI/CD automation

\- Improve monitoring and structured logging

