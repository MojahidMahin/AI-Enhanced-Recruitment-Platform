# Quick Start Guide

Get the AI-Enhanced Recruitment Platform up and running in 5 minutes!

## Option 1: Docker (Fastest - 2 minutes)

### Prerequisites
- Docker and Docker Compose installed

### Steps
```bash
# Navigate to project directory
cd "/home/vortex/Therap javafest/AI-Enhanced-Recruitment-Platform"

# Start all services
docker-compose up

# Wait for services to start (about 30-60 seconds)
```

### Access
- **Frontend:** http://localhost:3000
- **Backend API:** http://localhost:8080/api
- **Database:** localhost:5432

---

## Option 2: Local Setup (5-10 minutes)

### Prerequisites
- Java 17+
- Node.js 16+
- PostgreSQL 12+
- Maven 3.8+

### Step 1: Database Setup
```bash
# Create database
createdb recruitment_db

# Or with psql
psql -U postgres
CREATE DATABASE recruitment_db;
```

### Step 2: Backend Setup
```bash
cd backend

# Edit application.yml with your database credentials
# Update: spring.datasource.url, username, password

# Build and run
mvn clean install
mvn spring-boot:run
```

**Expected Output:**
```
Started RecruitmentApplication in X.XXX seconds
```

### Step 3: Frontend Setup (New Terminal)
```bash
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

**Expected Output:**
```
VITE v4.4.9 ready in XXX ms
➜  Local:   http://localhost:3000/
```

### Access
- **Frontend:** http://localhost:3000
- **Backend API:** http://localhost:8080/api

---

## Test the System

### 1. Check Backend Health
```bash
curl http://localhost:8080/api/candidates
```

### 2. Create a Job Position
```bash
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
```

### 3. Create a Candidate
```bash
curl -X POST http://localhost:8080/api/candidates \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1 (555) 123-4567",
    "summary": "Experienced Java developer with 8 years of experience"
  }'
```

### 4. Parse a Resume
```bash
curl -X POST http://localhost:8080/api/candidates/1/parse-resume \
  -H "Content-Type: application/json" \
  -d '{
    "resumeText": "John Doe\nEmail: john@example.com\nPhone: 555-1234\n\nExperience:\n- Java Developer (5 years)\n\nSkills:\nJava, Spring Boot, PostgreSQL, Docker, Kubernetes, AWS"
  }'
```

### 5. View Dashboard
Open http://localhost:3000 in your browser

---

## Project Structure Overview

```
AI-Enhanced-Recruitment-Platform/
├── backend/              # Spring Boot REST API
├── frontend/             # React TypeScript UI
├── docker-compose.yml    # Docker orchestration
├── README.md            # Full documentation
├── SETUP.md             # Detailed setup guide
├── API_DOCUMENTATION.md # API reference
└── PROJECT_SUMMARY.md   # Project overview
```

---

## Key Features Available

✅ **Dashboard** - View key metrics and top candidates
✅ **Candidate Management** - Create, search, view candidate profiles
✅ **Job Management** - Post and manage job openings
✅ **Resume Parsing** - Automatic skill extraction from resumes
✅ **Skill Matching** - AI-powered candidate-job matching
✅ **Interview Scheduling** - Schedule and track interviews
✅ **Assessments** - Manage candidate assessments

---

## Common Commands

### Backend
```bash
# Build project
mvn clean install

# Run tests
mvn test

# Run application
mvn spring-boot:run

# Build JAR
mvn clean package
```

### Frontend
```bash
# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

---

## Troubleshooting

### Port Already in Use
```bash
# Find and kill process
# macOS/Linux:
lsof -i :8080
kill -9 <PID>

# Or change port in application.yml
server:
  port: 8081
```

### Database Connection Error
```bash
# Check PostgreSQL is running
pg_isready

# Start PostgreSQL
# macOS: brew services start postgresql
# Linux: sudo systemctl start postgresql
```

### Frontend Won't Start
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
npm run dev
```

### Port 3000 in Use
```bash
# Kill process on port 3000
lsof -i :3000
kill -9 <PID>
```

---

## Environment Variables (Optional)

### Backend (.env or application.yml)
```yaml
OPENAI_API_KEY=sk-your-key-here
JWT_SECRET=your-secret-key
DB_URL=jdbc:postgresql://localhost:5432/recruitment_db
DB_USER=postgres
DB_PASSWORD=password
```

### Frontend (.env.local)
```
VITE_API_URL=http://localhost:8080/api
```

---

## Next Steps

1. ✅ **Get it running** - Choose Docker or Local setup above
2. 📖 **Read README.md** - Full project documentation
3. 📚 **Check API_DOCUMENTATION.md** - API endpoints reference
4. 🏗️ **Review SETUP.md** - Detailed configuration guide
5. 🚀 **Deploy** - Follow deployment instructions in SETUP.md

---

## Support

- 📖 Check SETUP.md for detailed setup instructions
- 📚 See API_DOCUMENTATION.md for API endpoint details
- 🔍 Review PROJECT_SUMMARY.md for project overview
- 💬 Check GitHub issues for common problems

---

## Project Status

✅ **Complete**
- Full-stack application with backend + frontend
- Database schema with 9 tables
- 30+ REST API endpoints
- AI/ML features (NLP, resume parsing, skill matching)
- Docker containerization
- Comprehensive documentation

---

**Happy recruiting! 🚀**

For detailed information, see README.md in the project root.
