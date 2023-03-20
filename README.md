# Blogging as a Java Spring-Boot Application

The backend for a blogging platform which supports storing of blogs using RESTful webservices. 
The application is developed using Java, Spring Boot and Postgres.

This restful backend have got the following end points

-for Posts

| Method | Path        | Description                                                                                                   |
|--------|-------------|---------------------------------------------------------------------------------------------------------------|
| GET    | /posts      | Returns a paginated list of posts, including only the `id` and `title`,Excluding the posts from blocked users |
| POST   | /posts      | Adds a new post                                                                                               |
| GET    | /posts/{id} | Retrieves the full details of a single post                                                                   |
| DELETE | /posts/{id} | Deletes a post. Only Author of the post is allowed to delete the post                                         |
| PUT    | /posts/     | Edits a posts. Only Author of the post is allowed to edit the post                                            |

- for comments:

| Method | Path                       | Description                                       |
|--------|----------------------------|---------------------------------------------------|
| GET    | /posts/{id}/comments       | Returns a paginated list of comments for a post   |
| POST   | /posts/{id}/comments       | Adds a new comment to a post                      |
| GET    | /posts/{id}/comments/{id}  | Retrieves the full details of a single comment    |
| PUT    | /posts/{id}/comments       | Deletes a comment. Only Author is allowed to edit |
| DELETE | /posts/{id}/comments/{id}  | Deletes a comment.Only Author is allwoed to edit  |

-for Blocking/Unblocking of users

| Method | Path              | Description                                    |
|--------|-------------------|------------------------------------------------|
| POST   | /posts/block/{id} | Enables the current user to block another user |
| DELETE | /posts/block/{id} | Enables the current user to unblock a user     |


## Steps to install

1. Pull the repository into your local drive.
2. Install a postgres server in your system or as a container.
3. The schema for the database is available in `resources/schema.sql`.
4. Configure the DB connection details in `application.properties`. 
5. Run the application using mvn `spring-boot:run` or from your IDE.

## Postman Collection
A postman collection `Blogs.postman_collection.json` is included in the projects folder which could be used to test 
and validate once the API is up and running.