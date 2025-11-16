# Setup Guide - AI-Enhanced Recruitment Platform

## Quick Start

### System Requirements
- Java 17+
- Node.js 16+
- PostgreSQL 12+
- Maven 3.8+
- npm or yarn

## Backend Setup

### Step 1: PostgreSQL Database Setup

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE recruitment_db;

# Create user (optional)
CREATE USER recruitment_user WITH PASSWORD 'your_password';
ALTER ROLE recruitment_user WITH CREATEDB;

# Grant privileges
GRANT ALL PRIVILEGES ON DATABASE recruitment_db TO recruitment_user;
```

### Step 2: Backend Installation

```bash
cd backend

# Edit application.yml with your database credentials
nano src/main/resources/application.yml
```

Update these values:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/recruitment_db
    username: postgres
    password: your_password
```

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The backend will be available at `http://localhost:8080/api`

### Step 3: Verify Backend

```bash
# Check health endpoint
curl http://localhost:8080/api/health

# Get all candidates
curl http://localhost:8080/api/candidates
```

## Frontend Setup

### Step 1: Install Dependencies

```bash
cd frontend
npm install
```

### Step 2: Configure Environment

Create `.env.local`:
```
VITE_API_URL=http://localhost:8080/api
```

### Step 3: Start Development Server

```bash
npm run dev
```

The frontend will be available at `http://localhost:3000`

## OpenAI Integration (Optional)

1. Get API key from https://platform.openai.com/api-keys
2. Update backend configuration:

```yaml
openai:
  api:
    key: sk-your-api-key-here
    model: gpt-3.5-turbo
```

Or set environment variable:
```bash
export OPENAI_API_KEY=sk-your-api-key-here
```

## Database Initialization

The application uses Hibernate's `ddl-auto: update` to automatically create tables. On first run:

1. Tables will be created automatically
2. Sample data can be inserted via API calls

### Sample Data Setup

```bash
# Create sample job positions
curl -X POST http://localhost:8080/api/jobs \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Senior Java Developer",
    "description": "Looking for experienced Java developer",
    "department": "Engineering",
    "location": "Remote",
    "salaryMin": 100000,
    "salaryMax": 150000
  }'

# Create sample candidate
curl -X POST http://localhost:8080/api/candidates \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phoneNumber": "+1234567890",
    "summary": "Experienced Java developer with 5 years of experience"
  }'
```

## Common Issues & Solutions

### Issue 1: PostgreSQL Connection Error
**Error**: `Unable to connect to PostgreSQL server`

**Solution**:
```bash
# Check PostgreSQL is running
pg_isready

# Start PostgreSQL (if not running)
# macOS with Homebrew
brew services start postgresql

# Linux
sudo systemctl start postgresql
```

### Issue 2: Port Already in Use
**Error**: `Address already in use: bind`

**Solution**:
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>

# Or change port in application.yml
server:
  port: 8081
```

### Issue 3: Node Modules Issues
**Error**: `Module not found`

**Solution**:
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
npm run dev
```

### Issue 4: TypeScript Compilation Error
**Error**: `Cannot find module`

**Solution**:
```bash
# Ensure tsconfig.json is correct
npx tsc --noEmit

# Rebuild
npm run build
```

## Environment Variables

### Backend
- `OPENAI_API_KEY`: OpenAI API key
- `JWT_SECRET`: JWT signing key
- `DB_URL`: Database URL
- `DB_USER`: Database username
- `DB_PASSWORD`: Database password

### Frontend
- `VITE_API_URL`: Backend API URL

## Project Structure Verification

After setup, verify the project structure:

```bash
# Check backend structure
backend/
├── pom.xml
├── src/
│   ├── main/java/com/recruitment/
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── ml/
│   │   └── RecruitmentApplication.java
│   └── main/resources/
│       └── application.yml

# Check frontend structure
frontend/
├── package.json
├── tsconfig.json
├── vite.config.ts
├── src/
│   ├── components/
│   ├── pages/
│   ├── services/
│   ├── store/
│   ├── types/
│   ├── styles/
│   ├── App.tsx
│   └── main.tsx
└── index.html
```

## Running Tests

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm run test
```

## Building for Production

### Backend
```bash
cd backend
mvn clean package

# Creates JAR file
ls target/ai-recruitment-platform-1.0.0.jar
```

### Frontend
```bash
cd frontend
npm run build

# Creates dist folder
ls dist/
```

## Docker Setup

### Backend Docker

Create `backend/Dockerfile`:
```dockerfile
FROM maven:3.8-openjdk-17 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

### Frontend Docker

Create `frontend/Dockerfile`:
```dockerfile
FROM node:16-alpine as build
WORKDIR /app
COPY . .
RUN npm install && npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### Docker Compose

Create `docker-compose.yml`:
```yaml
version: '3.8'

services:
  postgres:
    image: postgres:14
    environment:
      POSTGRES_DB: recruitment_db
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/recruitment_db
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: password

  frontend:
    build: ./frontend
    ports:
      - "3000:80"
    depends_on:
      - backend

volumes:
  postgres_data:
```

Run with:
```bash
docker-compose up
```

## Monitoring & Debugging

### Backend Logging
```bash
# Check application logs
tail -f backend/logs/application.log

# Run with debug logging
mvn spring-boot:run -Dlogging.level.root=DEBUG
```

### Frontend Console
- Open browser DevTools (F12)
- Check Console and Network tabs

## Next Steps

1. Review the API documentation
2. Create additional services as needed
3. Implement authentication
4. Add more ML features
5. Deploy to production

## Support & Troubleshooting

For issues, check:
- Backend logs: `backend/logs/`
- Browser console (Frontend)
- PostgreSQL logs: `/var/log/postgresql/`
- Application configuration: `application.yml`

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev)
- [PostgreSQL Documentation](https://www.postgresql.org/docs)
- [TypeScript Documentation](https://www.typescriptlang.org/docs)
