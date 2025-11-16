# AI-Enhanced Intelligent Recruitment Platform

A comprehensive recruitment platform powered by AI/ML technologies for intelligent candidate matching, resume parsing, and recruitment optimization.

## Overview

This project implements an intelligent recruitment system with the following key features:

- **Resume Parsing**: Automated extraction of skills, experience, and qualifications from resumes using NLP
- **Skill Matching Algorithm**: Advanced matching between candidate profiles and job requirements
- **Candidate Recommendation System**: AI-powered candidate ranking and recommendations
- **Interview Scheduling**: Intelligent scheduling with AI optimization
- **Sentiment Analysis**: Assessment of candidate communication and professionalism
- **RESTful APIs**: Complete REST API for all operations
- **Advanced Search**: Powerful search and filter mechanisms
- **OpenAI Integration**: Optional integration with OpenAI for enhanced NLP capabilities

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
- **NLP**: Stanford CoreNLP
- **Resume Parsing**: Apache PDFBox
- **OpenAI Integration**: OpenAI GPT-3.5 API
- **ML Libraries**: Apache Commons Math3

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

## Key Features

### 1. Resume Parsing & NLP
- Automatic extraction of skills from resume text
- Email and phone number extraction
- Education and experience level detection
- Sentiment analysis of resume content
- Support for PDF and text resumes

### 2. Skill Matching
- Automatic skill extraction from resumes
- Matching algorithms comparing candidate skills to job requirements
- Compatibility scoring between candidates and positions
- Skill proficiency level tracking

### 3. Candidate Recommendation
- ML-based candidate ranking for specific positions
- Multi-factor scoring (skills, experience, sentiment)
- Top candidate identification
- Recommendation scoring system

### 4. Interview Scheduling
- Smart scheduling optimization
- Interview status tracking
- Interview ratings and feedback
- Upcoming interview notifications

### 5. Advanced Search
- Search candidates by name, skills, or email
- Filter job positions by title, department, location
- Status-based filtering
- Salary range filtering

## API Endpoints

### Candidates
- `GET /api/candidates` - Get all candidates
- `POST /api/candidates` - Create candidate
- `GET /api/candidates/{id}` - Get candidate by ID
- `PUT /api/candidates/{id}` - Update candidate
- `DELETE /api/candidates/{id}` - Delete candidate
- `GET /api/candidates/search?name=` - Search candidates
- `POST /api/candidates/{id}/parse-resume` - Parse resume and extract data

### Job Positions
- `GET /api/jobs` - Get all jobs
- `POST /api/jobs` - Create job
- `GET /api/jobs/{id}` - Get job by ID
- `PUT /api/jobs/{id}` - Update job
- `DELETE /api/jobs/{id}` - Delete job
- `GET /api/jobs/open` - Get open positions
- `GET /api/jobs/search/title?title=` - Search by title
- `GET /api/jobs/search/department?department=` - Search by department
- `GET /api/jobs/search/location?location=` - Search by location

### Interviews
- `GET /api/interviews` - Get all interviews
- `POST /api/interviews` - Schedule interview
- `GET /api/interviews/{id}` - Get interview
- `PUT /api/interviews/{id}` - Update interview
- `DELETE /api/interviews/{id}` - Cancel interview
- `GET /api/interviews/upcoming` - Get upcoming interviews

### Assessments
- `GET /api/assessments` - Get all assessments
- `POST /api/assessments` - Create assessment
- `GET /api/assessments/{id}` - Get assessment
- `PUT /api/assessments/{id}` - Update assessment

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
