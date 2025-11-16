# API Documentation - AI-Enhanced Recruitment Platform

## Base URL
```
http://localhost:8080/api
```

## Authentication
All endpoints except login/register require JWT token in Authorization header:
```
Authorization: Bearer <token>
```

## Response Format
All responses are in JSON format with the following structure:

### Success Response
```json
{
  "status": "success",
  "data": { /* response data */ }
}
```

### Error Response
```json
{
  "status": "error",
  "message": "Error description",
  "code": "ERROR_CODE"
}
```

---

## Candidates Endpoints

### Get All Candidates
```
GET /candidates
```

**Response:**
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phoneNumber": "+1234567890",
    "summary": "Experienced developer",
    "sentimentScore": 0.75,
    "matchingScore": 0.85,
    "status": "SHORTLISTED",
    "skills": [
      {
        "id": 1,
        "name": "Java",
        "category": "PROGRAMMING",
        "proficiencyLevel": 5
      }
    ],
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

### Get Candidate by ID
```
GET /candidates/{id}
```

**Response:** Single candidate object

### Get Candidate by Email
```
GET /candidates/email/{email}
```

**Parameters:**
- `email`: Candidate email address

**Response:** Single candidate object

### Create Candidate
```
POST /candidates
```

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phoneNumber": "+1234567890",
  "summary": "Experienced developer"
}
```

**Response:** Created candidate object with ID

### Update Candidate
```
PUT /candidates/{id}
```

**Request Body:** (all fields optional)
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "summary": "Updated summary"
}
```

**Response:** Updated candidate object

### Delete Candidate
```
DELETE /candidates/{id}
```

**Response:** HTTP 204 No Content

### Search Candidates
```
GET /candidates/search?name={searchTerm}
```

**Parameters:**
- `name`: Search term (first or last name)

**Response:** Array of matching candidates

### Get Top Candidates
```
GET /candidates/top-candidates?limit={limit}
```

**Parameters:**
- `limit`: Number of top candidates (default: 10)

**Response:** Array of top candidates sorted by matching score

### Parse Resume
```
POST /candidates/{id}/parse-resume
```

**Request Body:**
```json
{
  "resumeText": "Full resume text content..."
}
```

**Response:** Candidate with parsed resume data:
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "sentimentScore": 0.75,
  "matchingScore": 0.85,
  "skills": [...],
  "yearsOfExperience": 5
}
```

---

## Job Positions Endpoints

### Get All Jobs
```
GET /jobs
```

**Response:** Array of job position objects

### Get Job by ID
```
GET /jobs/{id}
```

**Response:** Single job position object

### Create Job Position
```
POST /jobs
```

**Request Body:**
```json
{
  "title": "Senior Java Developer",
  "description": "We are looking for...",
  "department": "Engineering",
  "location": "Remote",
  "salaryMin": 100000,
  "salaryMax": 150000
}
```

**Response:** Created job position with ID

### Update Job Position
```
PUT /jobs/{id}
```

**Request Body:** (all fields optional)
```json
{
  "title": "Senior Java Developer",
  "description": "Updated description",
  "location": "San Francisco, CA"
}
```

**Response:** Updated job position object

### Delete Job Position
```
DELETE /jobs/{id}
```

**Response:** HTTP 204 No Content

### Get Open Positions
```
GET /jobs/open
```

**Response:** Array of open job positions

### Search Jobs by Title
```
GET /jobs/search/title?title={title}
```

**Parameters:**
- `title`: Job title search term

**Response:** Array of matching job positions

### Search Jobs by Department
```
GET /jobs/search/department?department={department}
```

**Parameters:**
- `department`: Department name

**Response:** Array of matching job positions

### Search Jobs by Location
```
GET /jobs/search/location?location={location}
```

**Parameters:**
- `location`: Location name

**Response:** Array of matching job positions

### Job Position Response Example
```json
{
  "id": 1,
  "title": "Senior Java Developer",
  "description": "We are looking for...",
  "department": "Engineering",
  "location": "Remote",
  "salaryMin": 100000,
  "salaryMax": 150000,
  "status": "OPEN",
  "requiredSkills": [
    {
      "id": 1,
      "name": "Java",
      "category": "PROGRAMMING",
      "proficiencyLevel": 4
    }
  ],
  "applicationCount": 5,
  "createdAt": "2024-01-10T09:00:00",
  "updatedAt": "2024-01-10T09:00:00"
}
```

---

## Interviews Endpoints

### Get All Interviews
```
GET /interviews
```

**Response:** Array of interview objects

### Get Interview by ID
```
GET /interviews/{id}
```

**Response:** Single interview object

### Get Interviews by Candidate
```
GET /interviews/candidate/{candidateId}
```

**Parameters:**
- `candidateId`: Candidate ID

**Response:** Array of candidate's interviews

### Get Interviews by Job
```
GET /interviews/job/{jobId}
```

**Parameters:**
- `jobId`: Job position ID

**Response:** Array of interviews for job position

### Create Interview
```
POST /interviews
```

**Request Body:**
```json
{
  "candidateId": 1,
  "jobId": 1,
  "scheduledTime": "2024-02-15T14:00:00",
  "interviewer": "Jane Smith"
}
```

**Response:** Created interview object

### Update Interview
```
PUT /interviews/{id}
```

**Request Body:** (all fields optional)
```json
{
  "status": "COMPLETED",
  "rating": 4.5,
  "notes": "Good communication skills"
}
```

**Response:** Updated interview object

### Get Upcoming Interviews
```
GET /interviews/upcoming
```

**Response:** Array of upcoming interviews

### Interview Response Example
```json
{
  "id": 1,
  "candidateId": 1,
  "jobId": 1,
  "scheduledTime": "2024-02-15T14:00:00",
  "completedTime": null,
  "status": "SCHEDULED",
  "interviewer": "Jane Smith",
  "notes": null,
  "rating": null
}
```

---

## Skills Endpoints

### Get All Skills
```
GET /skills
```

**Response:** Array of skill objects

### Search Skills by Category
```
GET /skills/search/category?category={category}
```

**Parameters:**
- `category`: Skill category (PROGRAMMING, DATABASE, CLOUD, TOOLS, SOFT_SKILLS, FRAMEWORKS, METHODOLOGY)

**Response:** Array of matching skills

### Skill Response Example
```json
{
  "id": 1,
  "name": "Java",
  "description": "Java programming language",
  "category": "PROGRAMMING",
  "proficiencyLevel": 5
}
```

---

## Assessments Endpoints

### Get All Assessments
```
GET /assessments
```

**Response:** Array of assessment objects

### Get Assessment by ID
```
GET /assessments/{id}
```

**Response:** Single assessment object

### Get Assessments by Candidate
```
GET /assessments/candidate/{candidateId}
```

**Response:** Array of candidate's assessments

### Create Assessment
```
POST /assessments
```

**Request Body:**
```json
{
  "candidateId": 1,
  "assessmentType": "TECHNICAL",
  "assessmentContent": "Test content...",
  "score": 85
}
```

**Response:** Created assessment object

### Update Assessment
```
PUT /assessments/{id}
```

**Request Body:**
```json
{
  "status": "COMPLETED",
  "score": 90,
  "feedback": "Excellent performance"
}
```

**Response:** Updated assessment object

### Assessment Response Example
```json
{
  "id": 1,
  "candidateId": 1,
  "assessmentType": "TECHNICAL",
  "assessmentContent": "Test content...",
  "score": 85,
  "sentimentScore": 0.8,
  "feedback": null,
  "status": "IN_PROGRESS",
  "createdAt": "2024-01-20T10:00:00",
  "updatedAt": "2024-01-20T10:00:00"
}
```

---

## Job Applications Endpoints

### Get All Applications
```
GET /applications
```

**Response:** Array of application objects

### Get Application by ID
```
GET /applications/{id}
```

**Response:** Single application object

### Get Applications by Candidate
```
GET /applications/candidate/{candidateId}
```

**Response:** Array of candidate's applications

### Get Applications by Job
```
GET /applications/job/{jobId}
```

**Response:** Array of applications for job position

### Create Application
```
POST /applications
```

**Request Body:**
```json
{
  "candidateId": 1,
  "jobId": 1
}
```

**Response:** Created application object

### Update Application
```
PUT /applications/{id}
```

**Request Body:**
```json
{
  "status": "SHORTLISTED",
  "compatibilityScore": 0.85
}
```

**Response:** Updated application object

### Application Response Example
```json
{
  "id": 1,
  "candidateId": 1,
  "jobId": 1,
  "status": "APPLIED",
  "compatibilityScore": 0.85,
  "reasonForRejection": null,
  "appliedAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

---

## Status Codes

| Code | Description |
|------|-------------|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 204 | No Content - Successful deletion |
| 400 | Bad Request - Invalid parameters |
| 401 | Unauthorized - Missing or invalid token |
| 403 | Forbidden - Insufficient permissions |
| 404 | Not Found - Resource not found |
| 500 | Internal Server Error - Server error |

---

## Error Examples

### 400 Bad Request
```json
{
  "status": "error",
  "message": "Invalid email format",
  "code": "VALIDATION_ERROR"
}
```

### 404 Not Found
```json
{
  "status": "error",
  "message": "Candidate not found",
  "code": "RESOURCE_NOT_FOUND"
}
```

### 500 Internal Server Error
```json
{
  "status": "error",
  "message": "An unexpected error occurred",
  "code": "INTERNAL_SERVER_ERROR"
}
```

---

## Rate Limiting

- Default rate limit: 100 requests per minute
- Rate limit headers are included in responses:
  - `X-RateLimit-Limit`
  - `X-RateLimit-Remaining`
  - `X-RateLimit-Reset`

---

## Pagination

List endpoints support pagination:
```
GET /candidates?page=0&size=10&sort=createdAt,desc
```

**Response includes:**
- `content`: Array of items
- `totalElements`: Total number of items
- `totalPages`: Total number of pages
- `currentPage`: Current page number

---

## Example cURL Commands

### Create a candidate
```bash
curl -X POST http://localhost:8080/api/candidates \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phoneNumber": "+1234567890",
    "summary": "Experienced Java developer"
  }'
```

### Search candidates
```bash
curl -X GET "http://localhost:8080/api/candidates/search?name=John" \
  -H "Authorization: Bearer <token>"
```

### Parse resume
```bash
curl -X POST http://localhost:8080/api/candidates/1/parse-resume \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "resumeText": "John Doe, Java Developer, Skills: Java, Spring Boot, PostgreSQL..."
  }'
```

---

## Webhook Events (Future Feature)

The system will support webhooks for:
- `candidate.created`
- `candidate.updated`
- `candidate.deleted`
- `interview.scheduled`
- `interview.completed`
- `application.status_changed`
