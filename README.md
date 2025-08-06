# Track API - Spring Boot Application

A simple REST API for managing music tracks using Spring Boot, JPA, and MySQL.

## Current Version: Service Layer Architecture

This version introduces a proper service layer, demonstrating clean architecture principles and dependency injection best practices.

### Features

- **JPA Entity**: The `Track` entity is a JPA entity with proper annotations
- **Service Layer**: Business logic separated into `TrackService`
- **Repository Layer**: Data access through Spring Data Repository
- **Constructor-based Dependency Injection**: Demonstrates Spring's recommended DI approach
- **MySQL Database**: Persistence in MySQL with Docker
- **Sample Data**: Automatic initialization with sample tracks
- **Complete CRUD Operations**: Create, Read, Update, Delete

### 🏗️ Architecture Overview

The application now follows a proper layered architecture:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   REST Client   │───▶│   Controller    │───▶│     Service     │───▶│   Repository    │
│  (Postman/curl) │    │ (HTTP handling) │    │ (Business logic)│    │ (Data access)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘    └─────────────────┘
                                                                               │
                                                                               ▼
                                                                       ┌─────────────────┐
                                                                       │ MySQL Database  │
                                                                       └─────────────────┘
```

**Layer Responsibilities:**
- **Controller**: HTTP request/response handling, data validation, status codes
- **Service**: Business logic, data transformation, transaction management
- **Repository**: Data persistence, database queries
- **Entity**: Data model representation

### 📚 Dependency Injection Learning Points

This application demonstrates **constructor-based dependency injection**:

1. **TrackController** depends on **TrackService**
2. **TrackService** depends on **TrackRepository**
3. **DataInitializer** depends on **TrackService**

**Why Constructor Injection over @Autowired:**
- ✅ Makes dependencies explicit and immutable
- ✅ Enables easier unit testing (can inject mocks)
- ✅ Prevents NullPointerException issues
- ✅ Follows Spring's recommended practices
- ✅ Ensures required dependencies are provided at construction time

**Spring's IoC Container manages the dependency chain:**
```
TrackRepository (created first)
    ↓
TrackService (TrackRepository injected)
    ↓
TrackController (TrackService injected)
```

## MySQL Configuration with Docker

### 1. Install Docker

If you don't have Docker installed:
```bash
# On macOS with Homebrew
brew install --cask docker

# Or download Docker Desktop from: https://www.docker.com/products/docker-desktop/
```

### 2. Run MySQL with Docker

A `docker-compose.yml` file is included in the project root:

```bash
# Start MySQL
docker-compose up -d

# Stop MySQL
docker-compose down

# View logs
docker-compose logs mysql
```

## Running the Application

### 1. Start MySQL (Docker)
```bash
docker-compose up -d
```

### 2. Compile and run the application
```bash
# Compile dependencies
mvn clean compile

# Run the application
mvn spring-boot:run
```

### 3. Verify it works
```bash
# Test endpoint
curl http://localhost:8080/api/tracks
```

The application will:
1. Connect to MySQL running in Docker
2. Automatically create the necessary tables
3. Insert 5 sample tracks if the database is empty
4. Be available at `http://localhost:8080`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tracks` | Get all tracks |
| GET | `/api/tracks/{id}` | Get a track by ID |
| POST | `/api/tracks` | Create a new track |
| PUT | `/api/tracks/{id}` | Update an existing track |
| DELETE | `/api/tracks/{id}` | Delete a track |

## Track Structure

```json
{
    "id": "uuid-string",
    "title": "Song title",
    "artist": "Artist name",
    "duration": 355,
    "creationDate": "2024-01-01T12:00:00"
}
```

## Testing

- **Postman**: Import `Track_API_Collection.postman_collection.json`
- **IntelliJ IDEA**: Use the `track-api-requests.http` file

## Project Structure

```
src/
├── main/java/com/neueda/trackapi/
│   ├── TrackApiApplication.java      # Main Spring Boot class
│   ├── controller/
│   │   └── TrackController.java      # REST endpoints (uses TrackService)
│   ├── service/
│   │   └── TrackService.java         # Business logic layer
│   ├── repository/
│   │   └── TrackRepository.java      # Data access layer
│   ├── model/
│   │   └── Track.java                # JPA Entity
│   └── config/
│       └── DataInitializer.java     # Sample data setup (uses TrackService)
└── resources/
    └── application.properties        # MySQL configuration
```

## Code Examples

### Constructor-based Dependency Injection

**TrackController Example:**
```java
@RestController
public class TrackController {
    private final TrackService trackService;
    
    // Constructor injection - Spring automatically provides TrackService
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }
    
    @GetMapping("/api/tracks")
    public List<Track> getAllTracks() {
        return trackService.getAllTracks(); // Delegate to service
    }
}
```

**TrackService Example:**
```java
@Service
public class TrackService {
    private final TrackRepository trackRepository;
    
    // Constructor injection - Spring automatically provides TrackRepository
    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }
    
    public List<Track> getAllTracks() {
        return trackRepository.findAll(); // Delegate to repository
    }
}
```

## Useful Docker Commands

```bash
# View running containers
docker ps

# View MySQL logs
docker logs mysql-trackapi

# Connect to MySQL from command line
docker exec -it mysql-trackapi mysql -u root -p

# Stop MySQL
docker stop mysql-trackapi

# Start MySQL
docker start mysql-trackapi

# Remove container (WARNING! Data will be lost)
docker rm mysql-trackapi

# With docker-compose - remove everything including volumes
docker-compose down -v
```

## Troubleshooting

### MySQL connection error:
```bash
# Verify MySQL is running
docker ps

# If not running, start it
docker start mysql-trackapi
# or
docker-compose up -d
```

### Port 3306 already in use:
```bash
# See what process is using the port
lsof -i :3306

# Change port in docker-compose.yml if needed:
ports:
  - "3307:3306"

# And update application.properties:
spring.datasource.url=jdbc:mysql://localhost:3307/trackdb...
```

### Reset database:
```bash
# Stop and remove container with data
docker-compose down -v

# Recreate
docker-compose up -d
```

## Next Steps

Potential future improvements:
1. Add input validation with `@Valid` annotations
2. Implement global exception handling with `@ControllerAdvice`
3. Add unit and integration tests
4. Implement pagination for large datasets
5. Add API documentation with Swagger/OpenAPI
6. Implement security with Spring Security