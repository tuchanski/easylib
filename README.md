
# EasyLib 📚

**EasyLib** is a personal library management API, designed to help users organize their personal book collections and manage their library with ease.

## Features

- **User Management**
  - CRUD operations for users.
  
- **Book Management**
  - CRUD operations for books.
  
- **Library Management**
  - Create a library for a user.
  - Add and remove books from the library.
  - Delete or clear libraries.

**Entity Relationships:**
- A **User** has a one-to-one relationship with a **Library**.
- A **Library** has a many-to-many relationship with **Books**.

## Getting Started

### Prerequisites

- **Java 21**
- **Maven 3.x**
- **Spring Boot 3.x**
- **PostgreSQL** (or another database supported by Spring Data JPA)

**Note:** If you are using a different database, make sure to include the appropriate database driver dependency in your `pom.xml`.

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/tuchanski/easylib
   ```

2. **Navigate to the project directory:**
   ```bash
   cd easylib
   ```

3. **Run SQL script to create database:**
   ```bash
   psql -U [username] -f db_script/easylib.sql
   ```
   Note: If you are using a different database than PostgreSQL, this command will vary.

4. **Build the project:**
   ```bash
   mvn clean install
   ```

5. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

6. **Access the API documentation:**
   - Open your browser and go to `http://localhost:8080/swagger-ui.html` to view the OpenAPI documentation.

### Main Dependencies

- **Spring Boot Starter Data JPA**
- **Spring Boot Starter Web**
- **Spring Boot Starter Validation**
- **PostgreSQL Driver**
- **SpringDoc OpenAPI**
- **Lombok**

## Usage

### API Endpoints

Here are some of the main endpoints:

- **User Endpoints:**
  - `POST api/users`: Create a new user.
  - `GET api/users`: Retrieve all users.
  - `GET api/users/{userId}`: Retrieve a user by ID.
  - `PUT api/users/{userId}`: Update a user by ID.
  - `DELETE api/users/{userId}`: Delete a user by ID.

- **Book Endpoints:**
  - `POST api/books`: Create a new book.
  - `GET api/books`: Retrieve all books.
  - `GET api/books/{bookId}`: Retrieve a book by ID.
  - `PUT api/books/{bookId}`: Update a book by ID.
  - `DELETE api/books/{bookId}`: Delete a book by ID.

- **Library Endpoints:**
  - `POST api/libraries/{userId}`: Create a library for a user.
  - `POST api/libraries/{libraryId}/add-book/{bookId}`: Add a book to a library.
  - `GET api/libraries`: Retrieve all libraries.
  - `GET api/libraries/{libraryId}`: Retrieve a library by ID.
  - `DELETE api/libraries/{libraryId}/del-book/{bookId}`: Remove a book from a library.
  - `DELETE api/libraries/{libraryId}`: Delete a library by ID.
  - `DELETE api/libraries/{libraryId}/clear`: Clear all books from a library.

## Author
 
- Guilherme Tuchanski Rocha | [GitHub](https://github.com/tuchanski) | [LinkedIn](https://www.linkedin.com/in/tuchanski/)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
