
# TODO APP

This project is a todo application built with Spring Boot, which allows users to manage their tasks. It provides RESTful endpoints for users to interact with, including authentication, user management, and task management.

## Endpoints

### Auth Controller

-   `POST /auth/register`: allows users to register a new account.
-   `POST /auth/login`: allows users to login and receive a token in response.

### User Controller

-   `GET /user`: returns a list of all users.
-   `GET /user/{id}`: returns the user with the specified ID.
-   `GET /user/me`: returns the currently logged in user.
-   `GET /user/{id}/task`: returns a list of tasks owned by the user with the specified ID.
-   `PUT /user/me`: updates the information of the currently logged in user.

### Task Controller

-   `POST /task`: creates a new task.
-   `GET /task`: returns a list of all tasks.
-   `GET /task/{id}`: returns the task with the specified ID.
-   `PUT /task/{id}`: updates the task with the specified ID.
-   `DELETE /task/{id}`: deletes the task with the specified ID.

### Global Controller Advice

-   Handles exceptions thrown by controllers and returns error responses.

## Entities

### Auditable

-   `createdDate`: timestamp for when an entity was created.
-   `modifiedDate`: timestamp for when an entity was last modified.

### User

-   `id`: unique identifier for the user.
-   `name`: first name of the user.
-   `surname`: last name of the user.
-   `email`: email address of the user.
-   `password hash`: hashed password of the user.
-   `createdDate`: timestamp for when the user was created.
-   `modifiedDate`: timestamp for when the user was last modified.

### Task

-   `id`: unique identifier for the task.
-   `title`: title of the task.
-   `description`: description of the task.
-   `due`: due date of the task.
-   `completed`: whether the task is completed or not.
-   `owner`: the user who owns the task.
-   `createdDate`: timestamp for when the task was created.
-   `modifiedDate`: timestamp for when the task was last modified.

### Models

#### Response

-   `LoginResponse`: response object returned when a user successfully logs in.
-   `TaskResponse`: response object representing a task.
-   `UserResponse`: response object representing a user.
-   `ErrorModel`: response object representing an error.

#### Request

-   `LoginRequest`: request object used for logging in.
-   `RegisterRequest`: request object used for registering a new account.
-   `CreateTaskRequest`: request object used for creating a new task.
-   `UpdateTaskRequest`: request object used for updating an existing task.
-   `CreateUserRequest`: request object used for creating a new user.
-   `UpdateUserRequest`: request object used for updating an existing user.

## Validation and Error Handling

Requests are validated to ensure that they contain the required fields and that the values are in the correct format. The application returns appropriate error responses if the validation fails or if an exception is thrown.

## Configuration

The application configuration is located in the `application.yml` file, which contains properties such as the database URL, username, and password. The project uses Maven for dependency management, and the dependencies are listed in the `pom.xml` file.

## Relationships

Each task is owned by a single user, and a user can have zero or more tasks associated with them. The `User` and `Task` entities extend the `Auditable` class.
