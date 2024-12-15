# Video Streaming API

## Prerequisites

1. Install Java 17
    - Download and install [Java 17](https://jdk.java.net/java-se-ri/17).
    - Set up `JAVA_HOME` environment variable pointing to your Java installation directory.
    - Add `JAVA_HOME/bin` to the `Path` environment variable.

2. Install Gradle
    - Download and install [Gradle](https://gradle.org/install/).
    - Set up `GRADLE_HOME` environment variable pointing to your Gradle installation directory.
    - Add `GRADLE_HOME/bin` to the `Path` environment variable.

3. Install PostgreSQL
    - Download and install [PostgreSQL](https://www.postgresql.org/download/) or set up on Docker.
    - Create a new database and user:
      ```bash
      psql -U postgres
      CREATE DATABASE video_streaming_db;
      CREATE USER video_user WITH PASSWORD 'yourpassword';
      GRANT ALL PRIVILEGES ON DATABASE video_streaming_db TO video_user;
      ```
    - Save the credentials for configuring the `application.yml`.

4. Configure Intellij IDEA
    * Install Lombok Plugin if it's not installed yet and enable annotation processing.
    * Set up required environment variables (see [application.yml](streaming-api/src/main/resources/application.yaml)).
    * Build project: `./gradlew clean build` or `./gradlew clean build -x test` to skip tests.
    * By default, the application runs on port 8080. Customize the port by editing application.yml or passing the
      argument: -Dserver.port=<PORT>.

5. API Documentation
    * You can access the API documentation and interact with the API endpoints using Swagger
      at: http://localhost:8080/swagger-ui/index.html#/
    * Retrieve the OpenAPI specification: http://localhost:8080/v3/api-docs
