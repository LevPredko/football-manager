# FootballManager

### FootballManager is a football team, player and transfer management system implemented using Spring Boot, JPA and PostgreSQL.

## Functional capabilities:

- **Manage players:** create, update, delete players, view all players.
- **Manage teams:** create, update, delete teams, view team information.
- **Transfer system:** transfer of players between teams with automatic transfer cost calculation, viewing information about transfers.


## Technologies:

- **Java 21**
- **Spring Boot 3.4.1**
- **Hibernate/JPA** for working with the database
- **PostgreSQL** as a DBMS
- **Maven** for dependency management
- **Lombok 1.18.30** for reducing boilerplate code


## Project settings

### Prerequisites
- **Java 21** or higher
- **PostgreSQL**
- **Maven**
- **Lombok 1.18.30**

### Steps to get started
1. Clone the repository:
 ```bash
 git clone https://github.com/LevPredko/football-manager.git
 cd FootballManager
 ```

2. Configure the database:
 - Create a PostgreSQL database.
 - Add connection parameters to `application.properties` or via environment variables:
 ```properties
 spring.datasource.url=${DATASOURCE_URL}
 spring.datasource.username=${DATASOURCE_USER}
 spring.datasource.password=${DATASOURCE_PASSWORD}
 ```

3. Launch the project:
 ```bash
 mvn spring-boot:run
 ```

### Test data
- **Commands:**
 - Team A (balance: 1,000,000, commission: 5%)
 - Team B (balance: 2,000,000, commission: 3%)
- **Players:**
 - Player 1 (age: 25, experience: 36 months, team: Team A)
 - Player 2 (age: 22, experience: 24 months, team: Team B)

## Project architecture

### Entities
- **Player:** represents a player with parameters (id, name, age, experience, transfer value, team).
- **Team:** represents the football team (id, name, balance, commission, players).
- **Transfer:** represents information about player transfers (id, player, previous team, new team, transfer value).

### Main components
- **Controller:** processing of HTTP requests (REST API for players, teams, transfers).
- **Service:** business logic (transfer processing, cost calculation).
- **Repository:** database access via JPA.

## REST API

### Players
- **GET /players** — get a list of players
- **GET /players/{id}** — get information about a player by ID
- **POST /players** — create a new player
- **PUT /players/{id}** — update player information
- **DELETE /players/{id}** — delete a player

### Teams
- **GET /teams** — get a list of teams
- **GET /teams/{id}** — get information about the team by ID
- **POST /teams** — create a new team
- **PUT /teams/{id}** — update team information
- **DELETE /teams/{id}** — delete a team

### Transfers
- **GET /transfers** — get a list of transfers
- **POST /transfers** — transfer the player to another team (parameters: playerId, targetTeamId)

## Features
- Calculation of the transfer fee is based on the player's age and experience.
- Transfer fee support for teams.
- Protection of transactions (annotation `@Transactional`).

## Unit Tests

###The project includes comprehensive unit tests to ensure the reliability of core functionalities:

- *PlayerServiceImplTest:* 
  - Tests for creating, updating, deleting, and retrieving players.  
  - Validates business rules (e.g., minimum age for players).  
  - Ensures proper exception handling for invalid operations.

- *TeamServiceImplTest:*  
  - Tests for creating, updating, and retrieving teams.  
  - Verifies correct mapping to `TeamDTO` objects.  
  - Handles scenarios where a team is not found.

- *TransferServiceImplTest:*
  - Tests for transferring players between teams, covering scenarios such as successful transfers, player/team not found, and insufficient balance.
  - Verifies that the transfer details, including player name, previous team, and new team, are accurately mapped to the TransferDTO.
  - Ensures that proper exceptions are thrown for invalid operations, such as player not found, target team not found, or insufficient balance.
  - Confirms that the repository methods (save, findById) are called with the correct parameters and the transfer is saved appropriately.
