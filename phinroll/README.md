<div align="center">
  <br>
  <h1>Phinroll</h1>
  <p>💸 An app to help manage employee salary 💸</p>
  <br>
</div>

## Table of Contents

- [Description](#description)
- [Minimum Requirements](#minimum-requirements)
- [Tech Stack](#tech-stack)
- [Run Locally](#run-locally)

## Description

[`^ back to top ^`](#table-of-contents)

**Phinroll** is an app to help manage employee salary. It is created for the midterm project in the Java phase of the Backend Development Training.

## Minimum Requirements

[`^ back to top ^`](#table-of-contents)

- Add an employee.
- Display list of employees.
- Display list of salary matrix entries.
- Add a payroll.
- Display payroll data of an employee.

## Tech Stack

[`^ back to top ^`](#table-of-contents)

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Hibernate Validator](https://hibernate.org/validator)
- [PostgreSQL](https://www.postgresql.org)

## Run Locally

[`^ back to top ^`](#table-of-contents)

- Make sure you have [Java](https://www.oracle.com/java) & [PostgreSQL](https://www.postgresql.org) installed on your computer.

- Connect to the PostgreSQL server by providing a user name & password.

  ```bash
  psql -U postgres
  ```

  Then create a databases for the API. You can name it as `phinroll`.

  ```bash
  CREATE DATABASE phinroll;
  ```

- Clone the repo.

  ```bash
  git clone https://github.com/nadiannis/bdt-java.git
  ```

  ```bash
  cd ./bdt-java/phinroll
  ```

- Connect the API to the database by writing the information about the database you use in the `application.properties` file.

  ```
  # example

  spring.datasource.url=jdbc:postgresql://localhost:5432/phinroll
  spring.datasource.username=phinroll
  spring.datasource.password=pass1234
  spring.datasrouce.driver-class-name=org.postgresql.Driver
  ```

- Run the server. The web server will run on port 8080.

  ```bash
  ./mvnw spring-boot:run
  ```
