# 📡 API Reference - AI-Enhanced Recruitment Platform

## Base URL

```
http://localhost:8080/api    (Development)
https://api.yourplatform.com/api  (Production)
```

## Interactive Documentation

**Swagger UI:** `http://localhost:8080/api/swagger-ui.html`
**OpenAPI JSON:** `http://localhost:8080/api/api-docs`

---

## Table of Contents

1. [Authentication](#authentication)
2. [Response Format](#response-format)
3. [Candidates API](#candidates-api)
4. [Job Positions API](#job-positions-api)
5. [Interviews API](#interviews-api)
6. [Assessments API](#assessments-api)
7. [Applications API](#applications-api)
8. [ML/AI Endpoints](#mlai-endpoints)
9. [Error Codes](#error-codes)

---

## Authentication

**Status:** Coming Soon (JWT-based)

**Header Format:**
```http
Authorization: Bearer <jwt_token>
```

**Current:** All endpoints are open (development mode)

---

## Response Format

### Success Response

```json
{
  "status": "success",
  "data": { /* response data */ },
  "message": "Optional success message",
  "count": 10  // For list responses
}
```

### Error Response

```json
{
  "status": "error",
  "message": "Error description",
  "code": "ERROR_CODE",
  "details": "Additional error details"
}
```

---

## Candidates API

### Create Candidate

**POST** `/candidates`

Creates a new candidate profile.

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890",
  "summary": "Experienced Java developer with 5 years...",
  "skills": [
    {
      "id": 1,
      "name": "Java"
    },
    {
      "id": 2,
      "name": "Spring Boot"
    }
  ]
}
```

**Response:** `201 Created`
```json
{
  "status": "success",
  "data": {
    "id": 123,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "summary": "Experienced Java developer...",
    "matchingScore": 0.0,
    "sentimentScore": 0.0,
    "status": "NEW",
    "skills": [...],
    "createdAt": "2025-11-18T10:30:00"
  }
}
```

---

### Get All Candidates

**GET** `/candidates`

Retrieves all candidates in the system.

**Query Parameters:**
- None

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "matchingScore": 0.85,
      "sentimentScore": 0.72,
      "status": "SHORTLISTED",
      "skills": [...]
    },
    ...
  ],
  "count": 45
}
```

---

### Get Candidate by ID

**GET** `/candidates/{id}`

Retrieves a specific candidate by ID.

**Path Parameters:**
- `id` (Long): Candidate ID

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "+1234567890",
    "summary": "...",
    "resumeText": "Full resume text...",
    "matchingScore": 0.85,
    "sentimentScore": 0.72,
    "status": "SHORTLISTED",
    "skills": [...],
    "createdAt": "2025-11-01T10:30:00",
    "updatedAt": "2025-11-18T14:20:00"
  }
}
```

**Error:** `404 Not Found`
```json
{
  "status": "error",
  "message": "Candidate not found with id: 999",
  "code": "NOT_FOUND"
}
```

---

### Update Candidate

**PUT** `/candidates/{id}`

Updates an existing candidate.

**Path Parameters:**
- `id` (Long): Candidate ID

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Smith",
  "summary": "Updated summary...",
  "status": "UNDER_REVIEW"
}
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Smith",
    ...
  }
}
```

---

### Delete Candidate

**DELETE** `/candidates/{id}`

Deletes a candidate from the system.

**Path Parameters:**
- `id` (Long): Candidate ID

**Response:** `204 No Content`

---

### Search Candidates

**GET** `/candidates/search`

Search candidates by name.

**Query Parameters:**
- `name` (String): Search term for first or last name

**Example:**
```http
GET /candidates/search?name=John
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    { "id": 1, "firstName": "John", "lastName": "Doe", ... },
    { "id": 5, "firstName": "Johnny", "lastName": "Smith", ... }
  ],
  "count": 2
}
```

---

### Get Top Candidates

**GET** `/candidates/top-candidates`

Get top candidates by matching score.

**Query Parameters:**
- `limit` (Integer, default: 10): Number of candidates to return

**Example:**
```http
GET /candidates/top-candidates?limit=5
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    { "id": 1, "matchingScore": 0.95, ... },
    { "id": 7, "matchingScore": 0.92, ... },
    ...
  ],
  "count": 5
}
```

---

### Parse Resume (AI)

**POST** `/candidates/{id}/parse-resume`

Parse and analyze candidate resume using AI.

**Path Parameters:**
- `id` (Long): Candidate ID

**Request Body:**
```json
{
  "resumeText": "John Doe\nSenior Java Developer\nemail: john@example.com\n\nExperience:\n- 5 years Java development...\n\nSkills: Java, Spring Boot, PostgreSQL, AWS..."
}
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": {
    "id": 1,
    "email": "john@example.com",
    "phoneNumber": "+1234567890",
    "skills": ["Java", "Spring Boot", "PostgreSQL", "AWS"],
    "education": "Bachelor in Computer Science",
    "yearsOfExperience": 5,
    "summary": "Experienced Java developer...",
    "sentimentScore": 0.78,
    ...
  }
}
```

---

## ML/AI Endpoints (Candidates)

### ML-Based Candidate Ranking

**GET** `/candidates/ml-rank`

Rank all candidates for a specific job using ML algorithm.

**Query Parameters:**
- `jobId` (Long, required): Job position ID

**Example:**
```http
GET /candidates/ml-rank?jobId=10
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    {
      "candidate": {
        "id": 5,
        "firstName": "Alice",
        "lastName": "Johnson",
        ...
      },
      "score": 0.92,
      "percentile": 98.5,
      "rank": 1,
      "features": {
        "skillMatch": 0.95,
        "experienceScore": 0.90,
        "sentimentScore": 0.85,
        "educationScore": 1.0,
        "aiFitScore": 0.88,
        "skillDiversity": 0.85,
        "matchingScore": 0.92
      }
    },
    ...
  ],
  "jobId": 10,
  "totalCandidates": 45
}
```

---

### Get Top ML-Ranked Candidates

**GET** `/candidates/ml-top-candidates`

Get top N candidates ranked by ML score.

**Query Parameters:**
- `jobId` (Long, required): Job position ID
- `limit` (Integer, default: 10): Number of candidates

**Example:**
```http
GET /candidates/ml-top-candidates?jobId=10&limit=5
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    { "id": 5, "firstName": "Alice", "score": 0.92, ... },
    { "id": 12, "firstName": "Bob", "score": 0.88, ... },
    ...
  ],
  "count": 5
}
```

---

### Get Candidate ML Features

**GET** `/candidates/{id}/features`

Get ML feature breakdown for a candidate.

**Path Parameters:**
- `id` (Long): Candidate ID

**Query Parameters:**
- `jobId` (Long, required): Job position ID

**Example:**
```http
GET /candidates/5/features?jobId=10
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "candidateId": 5,
  "jobId": 10,
  "mlScore": 0.92,
  "features": {
    "skillMatch": 0.95,
    "experienceScore": 0.90,
    "sentimentScore": 0.85,
    "educationScore": 1.0,
    "aiFitScore": 0.88,
    "skillDiversity": 0.85,
    "matchingScore": 0.92
  }
}
```

---

### Get Candidate Pool Statistics

**GET** `/candidates/pool-statistics`

Get statistical analysis of candidate pool for a job.

**Query Parameters:**
- `jobId` (Long, required): Job position ID

**Example:**
```http
GET /candidates/pool-statistics?jobId=10
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": {
    "totalCandidates": 45,
    "averageMLScore": 0.67,
    "medianMLScore": 0.65,
    "stdDevMLScore": 0.15,
    "averageSkillMatch": 0.58,
    "averageSentiment": 0.72,
    "topScore": 0.95,
    "bottomScore": 0.22
  },
  "jobId": 10
}
```

---

### Find Similar Candidates

**GET** `/candidates/{id}/similar`

Find candidates similar to a reference candidate.

**Path Parameters:**
- `id` (Long): Reference candidate ID

**Query Parameters:**
- `jobId` (Long, required): Job position ID
- `limit` (Integer, default: 5): Number of similar candidates

**Example:**
```http
GET /candidates/5/similar?jobId=10&limit=3
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    {
      "candidate": { "id": 12, "firstName": "Bob", ... },
      "similarity": 0.94
    },
    {
      "candidate": { "id": 23, "firstName": "Carol", ... },
      "similarity": 0.89
    },
    {
      "candidate": { "id": 17, "firstName": "David", ... },
      "similarity": 0.85
    }
  ],
  "referenceCandidateId": 5,
  "count": 3
}
```

**Similarity Score:** 0.0 (completely different) to 1.0 (identical profiles)

---

## Job Positions API

### Create Job Position

**POST** `/jobs`

Create a new job position.

**Request Body:**
```json
{
  "title": "Senior Java Developer",
  "description": "We are seeking an experienced Java developer...",
  "department": "Engineering",
  "location": "San Francisco, CA",
  "salaryMin": 120000.00,
  "salaryMax": 160000.00,
  "status": "OPEN",
  "requiredSkills": [
    { "id": 1, "name": "Java" },
    { "id": 2, "name": "Spring Boot" },
    { "id": 5, "name": "PostgreSQL" }
  ]
}
```

**Response:** `201 Created`
```json
{
  "status": "success",
  "data": {
    "id": 10,
    "title": "Senior Java Developer",
    "description": "...",
    "department": "Engineering",
    "location": "San Francisco, CA",
    "salaryMin": 120000.00,
    "salaryMax": 160000.00,
    "status": "OPEN",
    "requiredSkills": [...],
    "createdAt": "2025-11-18T10:00:00"
  }
}
```

---

### Get All Jobs

**GET** `/jobs`

Retrieve all job positions.

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    {
      "id": 10,
      "title": "Senior Java Developer",
      "department": "Engineering",
      "location": "San Francisco, CA",
      "status": "OPEN",
      ...
    },
    ...
  ],
  "count": 15
}
```

---

### Get Open Positions

**GET** `/jobs/open`

Get only open job positions.

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    { "id": 10, "title": "Senior Java Developer", "status": "OPEN", ... },
    { "id": 12, "title": "React Developer", "status": "OPEN", ... }
  ],
  "count": 8
}
```

---

### Search Jobs by Title

**GET** `/jobs/search/title`

Search job positions by title.

**Query Parameters:**
- `title` (String, required): Search term

**Example:**
```http
GET /jobs/search/title?title=developer
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    { "id": 10, "title": "Senior Java Developer", ... },
    { "id": 12, "title": "React Developer", ... }
  ],
  "count": 2
}
```

---

### Search Jobs by Department

**GET** `/jobs/search/department`

Filter jobs by department.

**Query Parameters:**
- `department` (String, required): Department name

**Example:**
```http
GET /jobs/search/department?department=Engineering
```

---

### Search Jobs by Location

**GET** `/jobs/search/location`

Filter jobs by location.

**Query Parameters:**
- `location` (String, required): Location name

**Example:**
```http
GET /jobs/search/location?location=San Francisco
```

---

## Interviews API

### Schedule Interview (AI-Optimized)

**POST** `/interviews`

Schedule a new interview with optional AI time optimization.

**Request Body:**
```json
{
  "candidateId": 5,
  "jobId": 10,
  "interviewer": "Jane Smith",
  "preferredTime": "2025-11-20T14:00:00"  // Optional
}
```

If `preferredTime` is omitted, AI will optimize the time slot.

**Response:** `201 Created`
```json
{
  "status": "success",
  "data": {
    "id": 100,
    "candidate": { ... },
    "jobPosition": { ... },
    "scheduledTime": "2025-11-20T14:00:00",
    "status": "SCHEDULED",
    "interviewer": "Jane Smith",
    "createdAt": "2025-11-18T10:30:00"
  },
  "message": "Interview scheduled successfully"
}
```

---

### Get All Interviews

**GET** `/interviews`

Retrieve all interviews.

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    {
      "id": 100,
      "candidate": { ... },
      "jobPosition": { ... },
      "scheduledTime": "2025-11-20T14:00:00",
      "status": "SCHEDULED",
      ...
    },
    ...
  ],
  "count": 25
}
```

---

### Get Upcoming Interviews

**GET** `/interviews/upcoming`

Get upcoming scheduled interviews.

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    {
      "id": 100,
      "scheduledTime": "2025-11-20T14:00:00",
      "status": "SCHEDULED",
      ...
    },
    ...
  ],
  "count": 8
}
```

---

### Update Interview

**PUT** `/interviews/{id}`

Update interview status, notes, or rating.

**Path Parameters:**
- `id` (Long): Interview ID

**Request Body:**
```json
{
  "status": "COMPLETED",
  "notes": "Excellent technical skills. Strong communication.",
  "rating": 9.0
}
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": {
    "id": 100,
    "status": "COMPLETED",
    "completedTime": "2025-11-20T15:30:00",
    "notes": "...",
    "rating": 9.0,
    ...
  },
  "message": "Interview updated successfully"
}
```

---

### Cancel Interview

**DELETE** `/interviews/{id}`

Cancel a scheduled interview.

**Path Parameters:**
- `id` (Long): Interview ID

**Response:** `200 OK`
```json
{
  "status": "success",
  "message": "Interview cancelled successfully"
}
```

---

## AI Interview Endpoints

### Generate Interview Questions (AI)

**GET** `/interviews/generate-questions`

Generate AI-powered interview questions tailored to candidate and job.

**Query Parameters:**
- `candidateId` (Long, required): Candidate ID
- `jobId` (Long, required): Job position ID

**Example:**
```http
GET /interviews/generate-questions?candidateId=5&jobId=10
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    "Tell me about your experience with microservices architecture and how you've implemented service-to-service communication.",
    "You mentioned 5 years of Java experience. Can you describe a challenging concurrency problem you solved?",
    "How do you approach database optimization in Spring Boot applications?",
    "Describe a situation where you had to make a difficult technical decision. What was your process?",
    "What's your experience with cloud deployment, specifically AWS or Azure?",
    "How do you ensure code quality in a fast-paced development environment?",
    "Tell me about a time you mentored junior developers.",
    "What's your approach to writing unit and integration tests?",
    "How do you stay updated with the latest Java and Spring Boot features?",
    "Do you have any questions about our team or the role?"
  ],
  "count": 10,
  "message": "Interview questions generated successfully"
}
```

---

### Get Job Fit Score (AI)

**GET** `/interviews/job-fit-score`

Get AI analysis of candidate-job compatibility.

**Query Parameters:**
- `candidateId` (Long, required): Candidate ID
- `jobId` (Long, required): Job position ID

**Example:**
```http
GET /interviews/job-fit-score?candidateId=5&jobId=10
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": {
    "score": 87.5,
    "fitLevel": "excellent",
    "strengths": "Strong Java and Spring Boot experience (5+ years), Proven microservices expertise, Excellent problem-solving skills",
    "gaps": "Limited AWS experience, No mention of containerization (Docker/Kubernetes)",
    "recommendation": "HIRE - Strong technical fit with minor gaps that can be addressed through training. Candidate demonstrates excellent fundamentals and learning ability."
  }
}
```

**Fit Levels:** poor, fair, good, excellent

---

### Suggest Optimal Interview Slots

**GET** `/interviews/suggest-slots`

Get AI-suggested optimal interview time slots.

**Query Parameters:**
- `numberOfSlots` (Integer, default: 10): Number of slots to suggest

**Example:**
```http
GET /interviews/suggest-slots?numberOfSlots=5
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    "2025-11-19T10:00:00",
    "2025-11-19T11:00:00",
    "2025-11-19T14:00:00",
    "2025-11-20T10:00:00",
    "2025-11-20T14:00:00"
  ],
  "count": 5,
  "message": "Suggested interview slots for optimal scheduling"
}
```

**Optimization Criteria:**
- Prefers Tue-Thu (better focus)
- Times: 10 AM - 3 PM (peak productivity)
- Avoids: Lunch (12-1 PM), weekends, conflicts

---

### Optimize Interview Time

**GET** `/interviews/optimize-time`

Get AI-optimized time for specific candidate-job pair.

**Query Parameters:**
- `candidateId` (Long, required): Candidate ID
- `jobId` (Long, required): Job position ID

**Example:**
```http
GET /interviews/optimize-time?candidateId=5&jobId=10
```

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": "2025-11-20T10:00:00",
  "message": "Optimized interview time calculated using AI"
}
```

---

### Get Interview Statistics

**GET** `/interviews/statistics`

Get overall interview statistics.

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": {
    "total": 150,
    "scheduled": 25,
    "completed": 100,
    "cancelled": 20,
    "averageRating": 7.5
  }
}
```

---

## Assessments API

### Create Assessment

**POST** `/assessments`

Create a new assessment for a candidate.

**Request Body:**
```json
{
  "candidateId": 5,
  "assessmentType": "TECHNICAL",
  "assessmentContent": "Java coding challenge: Implement a LRU cache...",
  "score": 85.0,
  "status": "COMPLETED"
}
```

**Response:** `201 Created`

---

### Get Assessment by ID

**GET** `/assessments/{id}`

Retrieve a specific assessment.

**Path Parameters:**
- `id` (Long): Assessment ID

---

### Get Candidate Assessments

**GET** `/assessments/candidate/{id}`

Get all assessments for a specific candidate.

**Path Parameters:**
- `id` (Long): Candidate ID

**Response:** `200 OK`
```json
{
  "status": "success",
  "data": [
    {
      "id": 50,
      "assessmentType": "TECHNICAL",
      "score": 85.0,
      "status": "COMPLETED",
      ...
    },
    {
      "id": 51,
      "assessmentType": "BEHAVIORAL",
      "score": 90.0,
      "status": "COMPLETED",
      ...
    }
  ],
  "count": 2
}
```

---

## Error Codes

| Code | HTTP Status | Description |
|------|-------------|-------------|
| `NOT_FOUND` | 404 | Resource not found |
| `VALIDATION_ERROR` | 400 | Invalid input data |
| `DUPLICATE_ENTRY` | 409 | Resource already exists |
| `INTERNAL_ERROR` | 500 | Internal server error |
| `UNAUTHORIZED` | 401 | Authentication required |
| `FORBIDDEN` | 403 | Insufficient permissions |
| `AI_SERVICE_ERROR` | 503 | OpenAI API unavailable |

---

## Rate Limits

**Current:** No rate limiting (development)
**Production:**
- 100 requests/minute per IP
- 1000 requests/hour per user

---

## Pagination

**Coming Soon**

Future endpoints will support pagination:

```http
GET /candidates?page=0&size=20&sort=matchingScore,desc
```

---

## Versioning

**Current:** v1 (implicit)
**Future:** Path-based versioning

```http
GET /api/v1/candidates
GET /api/v2/candidates
```

---

## Additional Resources

- **Swagger UI:** Interactive API testing
- **Postman Collection:** Available on request
- **SDK:** JavaScript/Python clients coming soon

---

**Last Updated:** November 2025
**API Version:** 1.0.0
**Support:** api@recruitment-platform.com
