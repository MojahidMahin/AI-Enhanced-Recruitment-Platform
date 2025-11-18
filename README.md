# 🤖 AI-Enhanced Intelligent Recruitment Platform

A comprehensive, production-ready recruitment platform powered by **Artificial Intelligence** and **Machine Learning** for intelligent candidate matching, resume parsing, interview optimization, and data-driven hiring decisions.

## 🎯 Overview

This project implements a full-stack intelligent recruitment system with cutting-edge AI/ML features:

### ✅ **FULLY IMPLEMENTED FEATURES:**

- ✅ **AI-Powered Resume Parsing** - OpenAI GPT integration with NLP fallback
- ✅ **ML-Based Candidate Ranking** - Multi-factor scoring with feature engineering
- ✅ **Intelligent Interview Scheduling** - AI-optimized time slot suggestions
- ✅ **Advanced Skill Matching** - Pattern matching + AI skill extraction
- ✅ **Sentiment Analysis** - Professionalism scoring for candidates
- ✅ **Candidate Recommendation System** - Statistical ranking and similarity matching
- ✅ **50+ RESTful APIs** - Comprehensive REST API with Swagger documentation
- ✅ **Advanced Search & Filtering** - Multi-criteria candidate/job search
- ✅ **Job Fit Analysis** - AI analyzes candidate-job compatibility
- ✅ **AI Interview Questions** - Generates relevant questions based on job/candidate
- ✅ **OpenAPI/Swagger Documentation** - Interactive API testing interface

## Technology Stack

### Backend
- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **Database**: PostgreSQL
- **ORM**: Hibernate/JPA
- **Security**: Spring Security with JWT
- **Build Tool**: Maven

### Frontend
- **Framework**: React 18.2
- **Language**: TypeScript
- **Build Tool**: Vite
- **State Management**: Zustand
- **Styling**: Tailwind CSS
- **HTTP Client**: Axios
- **Icons**: Lucide React

### AI/ML Components
- **OpenAI GPT-3.5-turbo**: Enhanced resume parsing, skill extraction, interview questions, job fit analysis
- **Apache Commons Math3**: Statistical analysis, ML algorithms, feature engineering
- **Stanford CoreNLP 4.5.4**: Advanced NLP capabilities (configured, ready to use)
- **Apache PDFBox 3.0.0**: PDF resume parsing and text extraction
- **Custom ML Models**: Multi-factor ranking, cosine similarity, weighted scoring

## Project Structure

```
AI-Enhanced-Recruitment-Platform/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/recruitment/
│   │   │   │   ├── entity/          # JPA Entities
│   │   │   │   ├── repository/      # Data Access Layer
│   │   │   │   ├── service/         # Business Logic
│   │   │   │   ├── controller/      # REST Controllers
│   │   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── ml/              # ML/NLP Services
│   │   │   │   ├── security/        # Security Configuration
│   │   │   │   ├── util/            # Utility Classes
│   │   │   │   └── RecruitmentApplication.java
│   │   │   └── resources/
│   │   │       └── application.yml  # Configuration
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/              # Reusable Components
│   │   ├── pages/                   # Page Components
│   │   ├── services/                # API Services
│   │   ├── store/                   # State Management
│   │   ├── types/                   # TypeScript Types
│   │   ├── styles/                  # CSS Styles
│   │   ├── App.tsx
│   │   └── main.tsx
│   ├── public/
│   ├── package.json
│   ├── tsconfig.json
│   ├── vite.config.ts
│   ├── tailwind.config.js
│   └── index.html
└── README.md
```

## Database Schema

### Key Tables

- **candidates**: Candidate profiles with skill matching scores
- **skills**: Available skills with categories
- **job_positions**: Job openings and requirements
- **interviews**: Interview schedules and ratings
- **assessments**: Candidate assessments and evaluations
- **job_applications**: Application tracking
- **users**: System users with roles

## ✨ Key Features

### 1. 🤖 AI-Powered Resume Parsing
- **OpenAI GPT Integration**: Uses GPT-3.5-turbo for intelligent data extraction
- **Structured Extraction**: Name, email, phone, education, experience, skills, summary
- **PDF Support**: Parse PDF resumes with Apache PDFBox
- **Sentiment Analysis**: Analyzes professionalism and tone (0-1 scale)
- **Fallback Mechanism**: Works with basic NLP if OpenAI unavailable
- **45+ Skills Detection**: Automatically identifies programming languages, frameworks, databases, cloud tools

### 2. 🎯 ML-Based Candidate Ranking
- **Multi-Factor Scoring Model**:
  - 35% Skill Match
  - 25% Experience Score
  - 15% Sentiment Score
  - 15% Education Score
  - 10% AI Fit Score
- **Feature Engineering**: Extracts 7+ features from candidate profiles
- **Statistical Analysis**: Mean, median, std dev, percentiles
- **Similarity Matching**: Find candidates similar to top performers using cosine similarity
- **Transparent Scoring**: Detailed breakdown of all factors

### 3. 📅 Intelligent Interview Scheduling
- **AI-Optimized Time Slots**: Automatically suggests best interview times
- **Smart Scheduling Rules**:
  - Prefers Tuesday-Thursday (avoids Monday/Friday fatigue)
  - Optimal hours: 10 AM - 3 PM (peak focus time)
  - Avoids lunch: 12-1 PM
  - Respects weekends and conflicts
- **AI Interview Questions**: Generate 10 tailored questions per candidate/job
- **Job Fit Scoring**: AI analyzes compatibility with detailed recommendations

### 4. 🔍 Advanced Skill Matching
- **Pattern Matching**: Detects 45+ technical skills across 4 categories
- **AI Enhancement**: Uses OpenAI for more accurate skill extraction
- **Skill Categories**:
  - Programming: Java, Python, JavaScript, TypeScript, Go, Rust, Kotlin, C++, C#, Ruby, PHP, Swift, Scala, R, MATLAB
  - Frameworks: Spring Boot, React, Angular, Vue, Django, Flask, Express, etc.
  - Databases: PostgreSQL, MySQL, MongoDB, Redis, Elasticsearch, etc.
  - Cloud/DevOps: AWS, Azure, GCP, Kubernetes, Docker, Jenkins, etc.
- **Weighted Scoring**: Customizable weights for different skill types

### 5. 📊 Candidate Recommendation System
- **Automated Ranking**: Ranks all candidates for a specific job
- **Top-N Selection**: Get the top 10 best-fit candidates
- **Pool Statistics**: Mean, median, std deviation, min/max scores
- **Similar Candidate Finder**: Identify candidates with similar profiles

### 6. 🔎 Advanced Search & Filtering
- **Candidate Search**: Name, email, skills, matching score, sentiment
- **Job Search**: Title, department, location, status, salary range
- **Interview Filtering**: Status, date range, candidate, job position
- **Statistical Queries**: Pool analytics, similarity search

## 📚 API Endpoints

### 📊 Swagger UI Documentation

**Access interactive API documentation:**
```
http://localhost:8080/api/swagger-ui.html
http://localhost:8080/api/api-docs (OpenAPI JSON)
```

### 👥 Candidates (13 endpoints)
- `GET /api/candidates` - Get all candidates
- `POST /api/candidates` - Create candidate
- `GET /api/candidates/{id}` - Get candidate by ID
- `PUT /api/candidates/{id}` - Update candidate
- `DELETE /api/candidates/{id}` - Delete candidate
- `GET /api/candidates/search?name=` - Search candidates by name
- `POST /api/candidates/{id}/parse-resume` - **AI Parse resume**
- **`GET /api/candidates/ml-rank?jobId=`** - **ML-based ranking for job**
- **`GET /api/candidates/ml-top-candidates?jobId=&limit=`** - **Top N by ML score**
- **`GET /api/candidates/{id}/features?jobId=`** - **Get ML features**
- **`GET /api/candidates/pool-statistics?jobId=`** - **Pool analytics**
- **`GET /api/candidates/{id}/similar?jobId=&limit=`** - **Find similar candidates**

### 💼 Job Positions (9 endpoints)
- `GET /api/jobs` - Get all jobs
- `POST /api/jobs` - Create job
- `GET /api/jobs/{id}` - Get job by ID
- `PUT /api/jobs/{id}` - Update job
- `DELETE /api/jobs/{id}` - Delete job
- `GET /api/jobs/open` - Get open positions
- `GET /api/jobs/search/title?title=` - Search by title
- `GET /api/jobs/search/department?dept=` - Search by department
- `GET /api/jobs/search/location?loc=` - Search by location

### 📅 Interviews (12 endpoints)
- `POST /api/interviews` - **Schedule interview (AI-optimized)**
- `GET /api/interviews` - Get all interviews
- `GET /api/interviews/{id}` - Get interview by ID
- `PUT /api/interviews/{id}` - Update interview
- `DELETE /api/interviews/{id}` - Cancel interview
- `GET /api/interviews/candidate/{id}` - Get candidate's interviews
- `GET /api/interviews/job/{id}` - Get job's interviews
- `GET /api/interviews/upcoming` - Get upcoming interviews
- **`GET /api/interviews/generate-questions?candidateId=&jobId=`** - **AI questions**
- **`GET /api/interviews/job-fit-score?candidateId=&jobId=`** - **AI fit score**
- **`GET /api/interviews/suggest-slots?numberOfSlots=`** - **Optimal time slots**
- **`GET /api/interviews/optimize-time?candidateId=&jobId=`** - **Optimize time**
- `GET /api/interviews/statistics` - Interview statistics

### 📝 Assessments (5 endpoints)
- `GET /api/assessments` - Get all assessments
- `POST /api/assessments` - Create assessment
- `GET /api/assessments/{id}` - Get assessment
- `PUT /api/assessments/{id}` - Update assessment
- `GET /api/assessments/candidate/{id}` - Get candidate assessments

## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 16+ and npm
- PostgreSQL 12+
- Maven 3.8+

### Backend Setup

1. **Database Configuration**
   ```bash
   # Create PostgreSQL database
   createdb recruitment_db
   ```

2. **Backend Installation**
   ```bash
   cd backend

   # Update application.yml with your database credentials
   # Then build and run
   mvn clean install
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

### Frontend Setup

1. **Frontend Installation**
   ```bash
   cd frontend
   npm install
   ```

2. **Start Development Server**
   ```bash
   npm run dev
   ```

   The frontend will start on `http://localhost:3000`

3. **Build for Production**
   ```bash
   npm run build
   ```

## Configuration

### Backend Configuration (`backend/src/main/resources/application.yml`)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/recruitment_db
    username: postgres
    password: your_password

jwt:
  secret: your-secret-key
  expiration: 86400000

openai:
  api:
    key: your-openai-api-key
    model: gpt-3.5-turbo
```

### Frontend Configuration

Create a `.env` file in the frontend directory:

```
VITE_API_URL=http://localhost:8080/api
```

## NLP/ML Services

### NLPService
- **extractSkills()**: Extract skills from text
- **analyzeSentiment()**: Sentiment analysis (-1 to 1 scale)
- **extractEmail()**: Extract email address
- **extractPhoneNumber()**: Extract phone number
- **extractEducation()**: Extract education details
- **extractYearsOfExperience()**: Extract experience level

### ResumeParssingService
- **parseResume()**: Parse PDF resume
- **parseTextResume()**: Parse text resume

### SkillMatchingService
- **calculateSkillMatch()**: Calculate skill match percentage
- **calculateCompatibilityScore()**: Overall compatibility scoring
- **getRecommendationScore()**: Recommendation ranking
- **compareCandidates()**: Compare multiple candidates

## Future Enhancements

- [ ] Video interview integration
- [ ] Advanced ML models for better predictions
- [ ] Email integration for candidates
- [ ] Automated interview questions based on job requirements
- [ ] Real-time notifications
- [ ] Analytics dashboard
- [ ] Bulk candidate import
- [ ] Multi-language support
- [ ] Compliance reporting
- [ ] Integration with popular HR tools

## Security Features

- JWT-based authentication
- Role-based access control (RBAC)
- Password encryption
- CORS protection
- Input validation
- SQL injection prevention

## Performance Considerations

- Database indexing on frequently queried fields
- Pagination for large datasets
- Caching strategies for frequently accessed data
- Connection pooling for database
- Lazy loading for relationships
- Optimized queries with projections

## Testing

### Backend Testing
```bash
cd backend
mvn test
```

### Frontend Testing
```bash
cd frontend
npm run test
```

## Deployment

### Docker Deployment

Create `Dockerfile` for backend:
```dockerfile
FROM openjdk:17-slim
COPY target/ai-recruitment-platform.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

Build and run:
```bash
docker build -t recruitment-platform:latest .
docker run -p 8080:8080 recruitment-platform:latest
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.

## Support

For support, email support@recruitment-platform.com or create an issue in the repository.

## Acknowledgments

- Stanford CoreNLP for NLP capabilities
- Apache PDFBox for PDF processing
- OpenAI for advanced AI features
- React and Spring Boot communities
