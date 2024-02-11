# GitHub repositories

This project is a Spring Boot application that provides an API for GitHub repositories. The API allows users to list repositories for a provided GitHub username, excluding forks and get information about them, including branch names and the last commit SHA for each branch.

## Installation

### Prerequisites
- Java 21
- Maven (version 3.11.0)
- Spring Boot 3.2.2

### Steps
1. Clone the repository:
   ```bash
   git clone  https://github.com/damskw/github-repositories.git
   ```
2. Navigate to the project directory:
   ```bash
   cd github-repositories
   ```
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

## Usage

### Running the Application

To run the Spring application, you can use Maven. Execute the following command in your terminal:

```bash
mvn spring-boot:run
```

### Running Tests

To run all tests execute the following command in your terminal:

```bash
mvn test
```

### GitHub API Access

This project interacts with the GitHub API for it's functionalities. Authentication is not required for the endpoints being used in this project.

### Configuration

This project doesn't require any additional configuration.
However, make sure that GitHub API endpoint in resources/application.properties is being up to date.

## Features

### GitHub Repository Endpoint

- **List User Repositories**: 
  - Endpoint: `/api/v1/users/repositories`
  - Method: `GET`
  - Description: Returns a list of GitHub repositories for a given username, excluding forks.
  - Request Parameters:
    - `username`: The GitHub username for which to get repositories.
  - Request Headers:
    - `Accept`: `application/json`
  - Response Body (JSON):
    ```json
    {
      "repositories": [
        {
          "name": "example-name",
          "ownerLogin": "example-login",
          "branches": [
            {
              "name": "main",
              "lastCommitSha": "commit-sha-1"
            },
            {
              "name": "development",
              "lastCommitSha": "commit-sha-2"
            }
          ]
        }
      ]
    }

    ```
    
- **Error Handling**:
  - If the provided GitHub user does not exist, the API will respond with a `404` status code and a JSON error message:
    ```json
    {
        "status": 404,
        "message": "Repositories for user not found."
    }
    ```
## Contact

For any inquiries or feedback feel free to get in touch with me:

- **Email**: damian.skwierawski@gmail.com
- **LinkedIn**: [Damian Skwierawski](https://www.linkedin.com/in/damian-skwierawski/)
