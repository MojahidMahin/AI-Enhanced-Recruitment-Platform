# 🛠 Developer Guide - AI-Enhanced Recruitment Platform

## Table of Contents
1. [Architecture Overview](#architecture-overview)
2. [Technology Stack](#technology-stack)
3. [Project Structure](#project-structure)
4. [Setup & Installation](#setup--installation)
5. [Backend Development](#backend-development)
6. [Frontend Development](#frontend-development)
7. [AI/ML Components](#aiml-components)
8. [API Reference](#api-reference)
9. [Database Schema](#database-schema)
10. [Testing](#testing)
11. [Deployment](#deployment)
12. [Contributing](#contributing)

---

## Architecture Overview

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                     Client Layer (React SPA)                    │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐       │
│  │Dashboard │  │Candidates│  │   Jobs   │  │Interviews│       │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘       │
└─────────────────────────────────────────────────────────────────┘
                            ▼ HTTP/REST
┌─────────────────────────────────────────────────────────────────┐
│                  API Gateway (Spring Boot)                      │
│                    Context Path: /api                           │
└─────────────────────────────────────────────────────────────────┘
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Controller Layer                             │
│  ┌──────────────────┐  ┌──────────────────┐                    │
│  │ CandidateCtrl    │  │ JobPositionCtrl  │  ...               │
│  │ InterviewCtrl    │  │ AssessmentCtrl   │                    │
│  └──────────────────┘  └──────────────────┘                    │
└─────────────────────────────────────────────────────────────────┘
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Service Layer                                │
│  ┌──────────────────┐  ┌──────────────────┐                    │
│  │ CandidateService │  │ JobPositionSvc   │  ...               │
│  │ InterviewService │  │ AssessmentSvc    │                    │
│  └──────────────────┘  └──────────────────┘                    │
└─────────────────────────────────────────────────────────────────┘
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│                    AI/ML Layer                                  │
│  ┌─────────────────────────────────────────────────────┐       │
│  │ OpenAIService        - GPT integration              │       │
│  │ NLPService           - Text processing              │       │
│  │ MLRankingService     - Candidate ranking            │       │
│  │ ResumeParssingService - Resume extraction           │       │
│  │ SkillMatchingService - Skill algorithms             │       │
│  │ InterviewService     - AI scheduling                │       │
│  └─────────────────────────────────────────────────────┘       │
└─────────────────────────────────────────────────────────────────┘
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│                Repository Layer (Spring Data JPA)               │
│  ┌──────────────────┐  ┌──────────────────┐                    │
│  │ CandidateRepo    │  │ JobPositionRepo  │  ...               │
│  │ InterviewRepo    │  │ SkillRepo        │                    │
│  └──────────────────┘  └──────────────────┘                    │
└─────────────────────────────────────────────────────────────────┘
                            ▼ JDBC
┌─────────────────────────────────────────────────────────────────┐
│                    PostgreSQL Database                          │
│    9 Tables: candidates, jobs, skills, interviews, etc.        │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    External Services                            │
│  • OpenAI API (GPT-3.5-turbo)                                  │
│  • Stanford CoreNLP Models (optional)                          │
└─────────────────────────────────────────────────────────────────┘
```

### Design Patterns Used

1. **MVC Pattern**: Controllers, Services, Repositories
2. **Dependency Injection**: Spring @Autowired
3. **Repository Pattern**: Spring Data JPA
4. **DTO Pattern**: Data Transfer Objects
5. **Builder Pattern**: Lombok @Builder
6. **Strategy Pattern**: ML algorithms
7. **Template Method**: Service base classes
8. **Singleton**: Spring Beans
9. **Factory Pattern**: Entity creation
10. **Observer Pattern**: Event listeners

---

## Technology Stack

### Backend Stack

```yaml
Framework: Spring Boot 3.1.5
Language: Java 17
Build Tool: Maven 3.8+
Database: PostgreSQL 12+
ORM: Hibernate 6.2 (via Spring Data JPA)

Dependencies:
  - spring-boot-starter-web
  - spring-boot-starter-data-jpa
  - spring-boot-starter-security
  - spring-boot-starter-validation
  - spring-boot-starter-webflux

AI/ML Libraries:
  - OpenAI GPT API (theokanning 0.18.0)
  - Stanford CoreNLP 4.5.4
  - Apache Commons Math3 3.6.1
  - Apache PDFBox 3.0.0

Security:
  - JWT (jjwt 0.12.3)
  - Spring Security

Documentation:
  - Springdoc OpenAPI 2.2.0

Utilities:
  - Lombok
  - Jackson
```

### Frontend Stack

```yaml
Framework: React 18.2
Language: TypeScript 5.1
Build Tool: Vite 4.4.9
Package Manager: npm

Core Libraries:
  - react-router-dom 6.14
  - axios 1.4.0
  - zustand 4.3.8 (state management)

UI/Styling:
  - tailwindcss 3.3.2
  - lucide-react 0.263.1 (icons)
  - postcss
  - autoprefixer

Dev Tools:
  - TypeScript
  - ESLint
  - Vite Dev Server
```

---

## Project Structure

### Backend Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/recruitment/
│   │   │   ├── config/                    # Configuration classes
│   │   │   │   ├── OpenAPIConfig.java     # Swagger configuration
│   │   │   │   ├── SecurityConfig.java    # Spring Security (future)
│   │   │   │   └── WebConfig.java         # CORS, MVC config
│   │   │   │
│   │   │   ├── controller/                # REST Controllers
│   │   │   │   ├── CandidateController.java      (13 endpoints)
│   │   │   │   ├── JobPositionController.java    (9 endpoints)
│   │   │   │   ├── InterviewController.java      (12 endpoints)
│   │   │   │   ├── AssessmentController.java     (5 endpoints)
│   │   │   │   └── ApplicationController.java    (5 endpoints)
│   │   │   │
│   │   │   ├── dto/                       # Data Transfer Objects
│   │   │   │   ├── CandidateDTO.java
│   │   │   │   ├── JobPositionDTO.java
│   │   │   │   ├── SkillDTO.java
│   │   │   │   └── ResponseWrapper.java   # Standardized response
│   │   │   │
│   │   │   ├── entity/                    # JPA Entities (7 total)
│   │   │   │   ├── Candidate.java         # Main candidate entity
│   │   │   │   ├── JobPosition.java       # Job listings
│   │   │   │   ├── Skill.java             # Skill catalog
│   │   │   │   ├── Interview.java         # Interview records
│   │   │   │   ├── InterviewStatus.java   # Enum for status
│   │   │   │   ├── Assessment.java        # Assessments
│   │   │   │   ├── JobApplication.java    # Applications
│   │   │   │   └── User.java              # User accounts
│   │   │   │
│   │   │   ├── ml/                        # AI/ML Services (6 total)
│   │   │   │   ├── OpenAIService.java              # OpenAI GPT integration
│   │   │   │   ├── NLPService.java                 # NLP processing
│   │   │   │   ├── ResumeParssingService.java      # Resume parsing
│   │   │   │   ├── SkillMatchingService.java       # Skill algorithms
│   │   │   │   ├── MLCandidateRankingService.java  # ML ranking
│   │   │   │   └── InterviewService.java           # AI scheduling
│   │   │   │
│   │   │   ├── repository/                # Spring Data Repositories
│   │   │   │   ├── CandidateRepository.java
│   │   │   │   ├── JobPositionRepository.java
│   │   │   │   ├── SkillRepository.java
│   │   │   │   ├── InterviewRepository.java
│   │   │   │   ├── AssessmentRepository.java
│   │   │   │   ├── ApplicationRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   │
│   │   │   ├── service/                   # Business Logic
│   │   │   │   ├── CandidateService.java
│   │   │   │   ├── JobPositionService.java
│   │   │   │   └── InterviewService.java
│   │   │   │
│   │   │   ├── exception/                 # Custom exceptions
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── ValidationException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │
│   │   │   └── RecruitmentApplication.java  # Main Spring Boot app
│   │   │
│   │   └── resources/
│   │       ├── application.yml            # Main configuration
│   │       ├── application-dev.yml        # Dev configuration
│   │       ├── application-prod.yml       # Production config
│   │       └── static/                    # Static resources
│   │
│   └── test/
│       └── java/com/recruitment/          # Unit & Integration tests
│           ├── controller/
│           ├── service/
│           ├── ml/
│           └── repository/
│
├── pom.xml                                # Maven dependencies
├── Dockerfile                             # Docker image
└── README.md
```

### Frontend Structure

```
frontend/
├── src/
│   ├── components/                # Reusable components
│   │   ├── Navbar.tsx
│   │   ├── CandidateCard.tsx
│   │   ├── JobCard.tsx
│   │   ├── ScoreBar.tsx
│   │   └── SkillBadge.tsx
│   │
│   ├── pages/                     # Page components
│   │   ├── Dashboard.tsx          # Main dashboard
│   │   ├── CandidatesList.tsx     # Candidate list view
│   │   ├── CandidateDetail.tsx    # Candidate detail
│   │   ├── JobsList.tsx           # Job list view
│   │   ├── JobDetail.tsx          # Job detail
│   │   └── InterviewScheduler.tsx # Interview scheduling
│   │
│   ├── services/                  # API services
│   │   └── api.ts                 # Axios API client
│   │
│   ├── store/                     # State management
│   │   └── useStore.ts            # Zustand store
│   │
│   ├── types/                     # TypeScript types
│   │   └── index.ts               # All type definitions
│   │
│   ├── styles/                    # CSS styles
│   │   └── index.css              # Global styles + Tailwind
│   │
│   ├── utils/                     # Utility functions
│   │   ├── formatters.ts
│   │   └── validators.ts
│   │
│   ├── App.tsx                    # Main app component
│   └── main.tsx                   # Entry point
│
├── public/                        # Static assets
├── package.json                   # NPM dependencies
├── tsconfig.json                  # TypeScript config
├── vite.config.ts                 # Vite configuration
├── tailwind.config.js             # Tailwind CSS config
├── postcss.config.js              # PostCSS config
├── Dockerfile                     # Docker image
├── nginx.conf                     # Nginx configuration
└── README.md
```

---

## Setup & Installation

### Prerequisites

```bash
# Required
Java 17+          # java -version
Node.js 16+       # node -version
PostgreSQL 12+    # psql --version
Maven 3.8+        # mvn -version

# Optional
Docker 20+        # docker --version
Docker Compose 2+ # docker-compose --version
```

### Backend Setup

#### 1. Clone Repository

```bash
git clone https://github.com/your-org/AI-Enhanced-Recruitment-Platform.git
cd AI-Enhanced-Recruitment-Platform
```

#### 2. Database Setup

```bash
# Create database
createdb recruitment_db

# Or using psql
psql -U postgres
CREATE DATABASE recruitment_db;
\q
```

#### 3. Configure Application

Edit `backend/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/recruitment_db
    username: your_username
    password: your_password

openai:
  api:
    key: ${OPENAI_API_KEY:your-key-here}
```

#### 4. Set Environment Variables

```bash
# OpenAI API key (optional but recommended)
export OPENAI_API_KEY="sk-your-openai-key-here"

# Database credentials (if not in application.yml)
export DB_USERNAME="postgres"
export DB_PASSWORD="your_password"

# JWT secret (for production)
export JWT_SECRET="your-super-secret-jwt-key-min-256-bits"
```

#### 5. Build and Run

```bash
cd backend

# Clean and build
mvn clean install

# Run application
mvn spring-boot:run

# Or run JAR directly
java -jar target/ai-recruitment-platform-1.0.0.jar
```

Application starts at: `http://localhost:8080`
Swagger UI: `http://localhost:8080/api/swagger-ui.html`

### Frontend Setup

#### 1. Install Dependencies

```bash
cd frontend
npm install
```

#### 2. Configure API URL

Create `.env` file:

```bash
VITE_API_URL=http://localhost:8080/api
```

#### 3. Run Development Server

```bash
npm run dev
```

Application starts at: `http://localhost:5173`

#### 4. Build for Production

```bash
npm run build
npm run preview  # Preview production build
```

### Docker Setup (Recommended)

#### Using Docker Compose

```bash
# From project root
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

This starts:
- PostgreSQL: `localhost:5432`
- Backend API: `localhost:8080`
- Frontend: `localhost:3000`

---

## Backend Development

### Creating a New Entity

```java
package com.recruitment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "your_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foreign_key_id")
    private OtherEntity otherEntity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
```

### Creating a Repository

```java
package com.recruitment.repository;

import com.recruitment.entity.YourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface YourRepository extends JpaRepository<YourEntity, Long> {

    // Method name queries (Spring Data magic)
    Optional<YourEntity> findByName(String name);
    List<YourEntity> findByNameContainingIgnoreCase(String name);

    // Custom JPQL queries
    @Query("SELECT e FROM YourEntity e WHERE e.field = :value")
    List<YourEntity> findByCustomCriteria(@Param("value") String value);

    // Native SQL queries
    @Query(value = "SELECT * FROM your_table WHERE condition", nativeQuery = true)
    List<YourEntity> findByNativeQuery();
}
```

### Creating a Service

```java
package com.recruitment.service;

import com.recruitment.entity.YourEntity;
import com.recruitment.repository.YourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class YourService {

    @Autowired
    private YourRepository repository;

    public YourEntity create(YourEntity entity) {
        log.info("Creating entity: {}", entity.getName());
        return repository.save(entity);
    }

    public YourEntity getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found: " + id));
    }

    public List<YourEntity> getAll() {
        return repository.findAll();
    }

    public YourEntity update(Long id, YourEntity updates) {
        YourEntity existing = getById(id);
        existing.setName(updates.getName());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
```

### Creating a Controller

```java
package com.recruitment.controller;

import com.recruitment.entity.YourEntity;
import com.recruitment.service.YourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/your-entities")
@CrossOrigin(origins = "*")
@Tag(name = "Your Entities", description = "Manage your entities")
public class YourController {

    @Autowired
    private YourService service;

    @PostMapping
    @Operation(summary = "Create entity")
    public ResponseEntity<Map<String, Object>> create(@RequestBody YourEntity entity) {
        YourEntity created = service.create(entity);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all entities")
    public ResponseEntity<Map<String, Object>> getAll() {
        List<YourEntity> entities = service.getAll();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", entities);
        response.put("count", entities.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get entity by ID")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        YourEntity entity = service.getById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", entity);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update entity")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @RequestBody YourEntity updates) {
        YourEntity updated = service.update(id, updates);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", updated);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete entity")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        service.delete(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Entity deleted");

        return ResponseEntity.ok(response);
    }
}
```

### Error Handling

```java
package com.recruitment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", "error");
        error.put("message", ex.getMessage());
        error.put("code", "NOT_FOUND");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", "error");
        error.put("message", "Internal server error");
        error.put("details", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
```

---

## AI/ML Components

### OpenAI Integration

#### Configuration

```yaml
openai:
  api:
    key: ${OPENAI_API_KEY}
    model: gpt-3.5-turbo
```

#### Usage Example

```java
@Autowired
private OpenAIService openAIService;

// Extract skills from resume
Set<String> skills = openAIService.extractSkillsWithAI(resumeText);

// Analyze sentiment
Map<String, Object> sentiment = openAIService.analyzeSentimentWithAI(text);
double score = (double) sentiment.get("score");

// Generate interview questions
List<String> questions = openAIService.generateInterviewQuestions(
    jobDescription,
    candidateResume
);

// Calculate job fit
Map<String, Object> fit = openAIService.calculateJobFitScore(
    jobDescription,
    candidateResume
);
```

### ML Candidate Ranking

#### Feature Extraction

```java
@Autowired
private MLCandidateRankingService mlService;

// Extract features
Map<String, Double> features = mlService.extractFeatures(candidate, job);

Features returned:
- skillMatch: 0.0-1.0
- experienceScore: 0.0-1.0
- sentimentScore: 0.0-1.0
- educationScore: 0.0-1.0
- aiFitScore: 0.0-1.0
- skillDiversity: 0.0-1.0
- matchingScore: 0.0-1.0
```

#### Scoring Algorithm

```java
ML Score =
    (0.35 × skillMatch) +
    (0.25 × experienceScore) +
    (0.15 × sentimentScore) +
    (0.15 × educationScore) +
    (0.10 × aiFitScore)

// Calculate score
double mlScore = mlService.calculateMLRankingScore(candidate, job);

// Rank all candidates
List<Map<String, Object>> ranked = mlService.rankCandidates(candidates, job);

// Get top N
List<Candidate> topN = mlService.getTopCandidates(candidates, job, 10);
```

#### Statistical Analysis

```java
// Pool statistics
Map<String, Object> stats = mlService.getCandidatePoolStatistics(candidates, job);

Returns:
- totalCandidates: count
- averageMLScore: mean
- medianMLScore: median
- stdDevMLScore: standard deviation
- averageSkillMatch: mean skill match
- averageSentiment: mean sentiment
- topScore: max score
- bottomScore: min score
```

#### Similarity Matching

```java
// Find similar candidates using cosine similarity
List<Map<String, Object>> similar = mlService.findSimilarCandidates(
    referenceCandidate,
    candidatePool,
    jobPosition,
    5  // top 5 similar
);

Algorithm: Cosine similarity of feature vectors
```

### Interview AI Scheduling

#### Optimization Algorithm

```java
@Autowired
private InterviewService interviewService;

// Optimize interview time
LocalDateTime optimal = interviewService.optimizeInterviewTime(candidate, job);

Rules:
1. Prefer Tue-Thu (avoid Mon/Fri)
2. Prefer 10 AM - 3 PM
3. Avoid 12-1 PM (lunch)
4. Avoid weekends
5. Check for conflicts
6. Start 2 days from now
```

#### Suggested Slots

```java
// Get optimal time slots
List<LocalDateTime> slots = interviewService.suggestInterviewSlots(10);

Returns 10 conflict-free, optimized time slots
```

---

## Database Schema

### ER Diagram

```
┌──────────────┐       ┌────────────────┐       ┌──────────────┐
│   User       │       │   Candidate    │       │     Skill    │
├──────────────┤       ├────────────────┤       ├──────────────┤
│ id (PK)      │       │ id (PK)        │◄─────►│ id (PK)      │
│ username     │       │ firstName      │  M:M  │ name         │
│ email        │       │ lastName       │       │ category     │
│ role         │       │ email          │       │ description  │
│ created_at   │       │ phoneNumber    │       └──────────────┘
└──────────────┘       │ summary        │              │
                       │ resumeText     │              │
                       │ matchingScore  │              │ M:M
                       │ sentimentScore │              │
                       │ status         │       ┌──────▼────────┐
                       │ created_at     │       │ JobPosition   │
                       └────────────────┘       ├───────────────┤
                              │                 │ id (PK)       │
                              │ 1:N             │ title         │
                              │                 │ description   │
                       ┌──────▼────────┐       │ department    │
                       │  Interview    │       │ location      │
                       ├───────────────┤       │ salaryMin     │
                       │ id (PK)       │       │ salaryMax     │
                       │ candidate_id  │       │ status        │
                       │ job_id        ├──────►│ created_at    │
                       │ scheduledTime │  N:1  └───────────────┘
                       │ status        │              │
                       │ interviewer   │              │ 1:N
                       │ notes         │              │
                       │ rating        │       ┌──────▼────────┐
                       │ created_at    │       │ Application   │
                       └───────────────┘       ├───────────────┤
                                               │ id (PK)       │
                       ┌───────────────┐       │ candidate_id  │
                       │  Assessment   │       │ job_id        │
                       ├───────────────┤       │ status        │
                       │ id (PK)       │       │ score         │
                       │ candidate_id  ├──────►│ appliedAt     │
                       │ type          │  N:1  └───────────────┘
                       │ content       │
                       │ score         │
                       │ status        │
                       └───────────────┘
```

### Tables

#### candidates
```sql
CREATE TABLE candidates (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    summary TEXT,
    resume_text TEXT,
    resume_file_path VARCHAR(255),
    sentiment_score DOUBLE PRECISION,
    matching_score DOUBLE PRECISION,
    status VARCHAR(20),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
```

#### skills
```sql
CREATE TABLE skills (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    category VARCHAR(50),
    proficiency_level INT
);
```

#### job_positions
```sql
CREATE TABLE job_positions (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    department VARCHAR(100),
    location VARCHAR(100),
    salary_min DECIMAL(10, 2),
    salary_max DECIMAL(10, 2),
    status VARCHAR(20),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
```

#### interviews
```sql
CREATE TABLE interviews (
    id BIGSERIAL PRIMARY KEY,
    candidate_id BIGINT REFERENCES candidates(id),
    job_id BIGINT REFERENCES job_positions(id),
    scheduled_time TIMESTAMP NOT NULL,
    completed_time TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    interviewer VARCHAR(100),
    notes TEXT,
    rating DOUBLE PRECISION,
    created_at TIMESTAMP NOT NULL
);
```

### Indexes

```sql
-- Performance indexes
CREATE INDEX idx_candidates_email ON candidates(email);
CREATE INDEX idx_candidates_matching_score ON candidates(matching_score DESC);
CREATE INDEX idx_candidates_status ON candidates(status);

CREATE INDEX idx_jobs_title ON job_positions(title);
CREATE INDEX idx_jobs_status ON job_positions(status);
CREATE INDEX idx_jobs_department ON job_positions(department);

CREATE INDEX idx_interviews_candidate ON interviews(candidate_id);
CREATE INDEX idx_interviews_job ON interviews(job_id);
CREATE INDEX idx_interviews_scheduled_time ON interviews(scheduled_time);
CREATE INDEX idx_interviews_status ON interviews(status);
```

---

## Testing

### Unit Testing

```java
@SpringBootTest
@AutoConfigureMockMvc
class CandidateServiceTest {

    @Autowired
    private CandidateService service;

    @MockBean
    private CandidateRepository repository;

    @Test
    void testCreateCandidate() {
        Candidate candidate = Candidate.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john@example.com")
            .build();

        when(repository.save(any(Candidate.class))).thenReturn(candidate);

        Candidate created = service.create(candidate);

        assertNotNull(created);
        assertEquals("John", created.getFirstName());
    }
}
```

### Integration Testing

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CandidateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllCandidates() throws Exception {
        mockMvc.perform(get("/api/candidates"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("success"))
            .andExpect(jsonPath("$.data").isArray());
    }
}
```

### Running Tests

```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=CandidateServiceTest

# Integration tests only
mvn verify

# With coverage
mvn test jacoco:report
```

---

## Deployment

### Production Configuration

```yaml
# application-prod.yml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST}:5432/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5

  jpa:
    hibernate:
      ddl-auto: validate  # Never use 'update' in production
    show-sql: false

logging:
  level:
    root: INFO
    com.recruitment: INFO
```

### Docker Deployment

```dockerfile
# Multi-stage build
FROM maven:3.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Environment Variables

```bash
# Production environment
export SPRING_PROFILES_ACTIVE=prod
export DB_HOST=your-db-host
export DB_NAME=recruitment_db
export DB_USERNAME=prod_user
export DB_PASSWORD=secure_password
export OPENAI_API_KEY=sk-your-production-key
export JWT_SECRET=your-super-secure-256-bit-key
```

---

## Contributing

### Code Style

**Java:**
- Follow Google Java Style Guide
- Use Lombok to reduce boilerplate
- Maximum line length: 120 characters
- Use meaningful variable names

**TypeScript:**
- Follow Airbnb TypeScript Style Guide
- Use functional components
- Prefer arrow functions
- Use TypeScript strict mode

### Git Workflow

```bash
# 1. Create feature branch
git checkout -b feature/your-feature-name

# 2. Make changes and commit
git add .
git commit -m "feat: Add your feature"

# 3. Push to remote
git push origin feature/your-feature-name

# 4. Create Pull Request
# Via GitHub UI
```

### Commit Message Format

```
type(scope): subject

body

footer
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation
- `style`: Formatting
- `refactor`: Code restructuring
- `test`: Tests
- `chore`: Maintenance

---

**Last Updated:** November 2025
**Version:** 1.0.0
**For questions:** dev@recruitment-platform.com
