# Blog APIs (Spring Boot)

This repository is a Spring Boot-based REST API for a blog application.

Core entrypoint

- [`com.myProject.blogApis.BlogApisApplication`](src/main/java/com/myProject/blogApis/BlogApisApplication.java) — application bootstrap.

Key files

- [pom.xml](pom.xml) — Maven project configuration.
- [docker-compose.yaml](docker-compose.yaml) — Docker compose setup.
- [mvnw](mvnw) and [mvnw.cmd](mvnw.cmd) — Maven wrapper.
- [src/main/resources/application.properties](src/main/resources/application.properties) — application configuration.
- [`com.myProject.blogApis.BlogApisApplicationTests`](src/test/java/com/myProject/blogApis/BlogApisApplicationTests.java) — basic test scaffold.

Project layout (important packages)

- [src/main/java/com/myProject/blogApis/controllers](src/main/java/com/myProject/blogApis/controllers) — HTTP controllers (request handlers).
- [src/main/java/com/myProject/blogApis/services](src/main/java/com/myProject/blogApis/services) — business logic and service layer.
- [src/main/java/com/myProject/blogApis/repositories](src/main/java/com/myProject/blogApis/repositories) — data access (Spring Data repositories).
- [src/main/java/com/myProject/blogApis/models](src/main/java/com/myProject/blogApis/models) — JPA entities / domain models.
- [src/main/java/com/myProject/blogApis/payloads](src/main/java/com/myProject/blogApis/payloads) — DTOs / request & response payloads.
- [src/main/java/com/myProject/blogApis/exceptions](src/main/java/com/myProject/blogApis/exceptions) — centralized exception handling.
- [src/main/java/com/myProject/blogApis/security](src/main/java/com/myProject/blogApis/security) — security configuration (authentication/authorization).
- [src/main/java/com/myProject/blogApis/UserServiceImpl](src/main/java/com/myProject/blogApis/UserServiceImpl) — user service implementation (user lookup / details).

Request flow (high level)

1. The app starts via [`com.myProject.blogApis.BlogApisApplication`](src/main/java/com/myProject/blogApis/BlogApisApplication.java).
2. Incoming HTTP requests are routed to controllers in [src/main/java/com/myProject/blogApis/controllers](src/main/java/com/myProject/blogApis/controllers).
3. Controllers delegate to services in [src/main/java/com/myProject/blogApis/services](src/main/java/com/myProject/blogApis/services) for business logic.
4. Services interact with repositories in [src/main/java/com/myProject/blogApis/repositories](src/main/java/com/myProject/blogApis/repositories) to persist/load domain objects from entities in [src/main/java/com/myProject/blogApis/models](src/main/java/com/myProject/blogApis/models).
5. DTOs in [src/main/java/com/myProject/blogApis/payloads](src/main/java/com/myProject/blogApis/payloads) shape request/response bodies.
6. Errors are translated by handlers in [src/main/java/com/myProject/blogApis/exceptions](src/main/java/com/myProject/blogApis/exceptions).
7. Security concerns are enforced by components in [src/main/java/com/myProject/blogApis/security](src/main/java/com/myProject/blogApis/security) (authentication, filters, token handling, etc.).

Build & run

- Using Maven wrapper:

```sh
./mvnw clean package
./mvnw spring-boot:run
```
