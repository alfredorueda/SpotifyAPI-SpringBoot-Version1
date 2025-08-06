# Track API - Spring Boot Application

A simple REST API for managing music tracks using Spring Boot, JPA, and MySQL.

## Current Version: JPA with MySQL

This version refactors the application to use MySQL database persistence instead of in-memory storage.

### Features

- **JPA Entity**: The `Track` entity is now a JPA entity with proper annotations
- **Spring Data Repository**: Direct use of `TrackRepository` from the controller
- **MySQL Database**: Persistence in MySQL instead of memory
- **Sample Data**: Automatic initialization with sample tracks
- **Complete CRUD Operations**: Create, Read, Update, Delete

### ⚠️ Architectural Note

**This version intentionally violates the Single Responsibility Principle** by using the Repository directly from the Controller.
This is deliberate to maintain simplicity in this incremental iteration. 
In future versions, we will introduce a service layer between the Controller and Repository.

## MySQL Configuration with Docker

### 1. Install Docker

If you don't have Docker installed:
```bash
# On macOS with Homebrew
brew install --cask docker

# Or download Docker Desktop from: https://www.docker.com/products/docker-desktop/
```

### 2. Run MySQL with Docker

#### Option A: Simple command
```bash
# Run MySQL container
docker run --name mysql-trackapi \
  -e MYSQL_ROOT_PASSWORD=password \
  -e MYSQL_DATABASE=trackdb \
  -p 3306:3306 \
  -d mysql:8.0

# Verify it's running
docker ps
```

#### Option B: Docker Compose (Recommended)

A `docker-compose.yml` file is already included in the project root:

```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: mysql-trackapi
    environment:
      MYSQL_ROOT_PASSWORD: password
      MYSQL_DATABASE: trackdb
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    restart: unless-stopped

volumes:
  mysql_data:
```

Then run:
```bash
# Start MySQL
docker-compose up -d

# Stop MySQL
docker-compose down

# View logs
docker-compose logs mysql
```

### 3. Application Configuration

The current configuration in `application.properties` is already set up for Docker:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/trackdb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=password
```

## Running the Application

### 1. Start MySQL (Docker)
```bash
# With docker-compose (recommended)
docker-compose up -d

# Or with docker run
docker start mysql-trackapi
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

## Database Access

### From command line:
```bash
# Connect to MySQL in the container
docker exec -it mysql-trackapi mysql -u root -p
# Password: password

# View tables
USE trackdb;
SHOW TABLES;
SELECT * FROM tracks;
```

### From GUI tools:
- **Host**: localhost
- **Port**: 3306
- **User**: root
- **Password**: password
- **Database**: trackdb

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
│   ├── TrackApiApplication.java      # Main class
│   ├── controller/
│   │   └── TrackController.java      # REST Controller (uses Repository directly)
│   ├── model/
│   │   └── Track.java                # JPA Entity
│   ├── repository/
│   │   └── TrackRepository.java      # Spring Data Repository
│   └── config/
│       └── DataInitializer.java     # Sample data initialization
└── resources/
    └── application.properties        # MySQL configuration
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

In future iterations:
1. Introduce service layer (`TrackService`)
2. Move business logic from Controller to Service
3. Add validations
4. Implement exception handling
5. Add unit and integration testing