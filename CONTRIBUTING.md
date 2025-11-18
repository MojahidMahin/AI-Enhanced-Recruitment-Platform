# 🤝 Contributing to AI-Enhanced Recruitment Platform

Thank you for your interest in contributing to the AI-Enhanced Recruitment Platform! This document provides guidelines and instructions for contributing to the project.

## Table of Contents

1. [Code of Conduct](#code-of-conduct)
2. [Getting Started](#getting-started)
3. [Development Workflow](#development-workflow)
4. [Coding Standards](#coding-standards)
5. [Commit Guidelines](#commit-guidelines)
6. [Pull Request Process](#pull-request-process)
7. [Testing Requirements](#testing-requirements)
8. [Documentation](#documentation)
9. [Issue Reporting](#issue-reporting)
10. [Community](#community)

---

## Code of Conduct

### Our Pledge

We are committed to providing a welcoming and inclusive experience for everyone. We expect all contributors to:

- Use welcoming and inclusive language
- Be respectful of differing viewpoints and experiences
- Gracefully accept constructive criticism
- Focus on what is best for the community
- Show empathy towards other community members

### Unacceptable Behavior

- Harassment, discrimination, or offensive comments
- Trolling, insulting, or derogatory comments
- Publishing others' private information without permission
- Other conduct which could reasonably be considered inappropriate

---

## Getting Started

### Prerequisites

Before you begin, ensure you have:

- Java 17 or higher
- Node.js 16+ and npm
- PostgreSQL 12+
- Maven 3.8+
- Git
- A GitHub account
- OpenAI API key (optional, for AI features)

### Fork and Clone

1. **Fork the repository** on GitHub
2. **Clone your fork:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/AI-Enhanced-Recruitment-Platform.git
   cd AI-Enhanced-Recruitment-Platform
   ```

3. **Add upstream remote:**
   ```bash
   git remote add upstream https://github.com/ORIGINAL_OWNER/AI-Enhanced-Recruitment-Platform.git
   ```

### Setup Development Environment

1. **Backend Setup:**
   ```bash
   cd backend
   mvn clean install
   ```

2. **Frontend Setup:**
   ```bash
   cd frontend
   npm install
   ```

3. **Database Setup:**
   ```bash
   createdb recruitment_db
   ```

4. **Environment Variables:**
   ```bash
   export OPENAI_API_KEY="your-key-here"
   export DB_USERNAME="postgres"
   export DB_PASSWORD="your-password"
   ```

5. **Run the Application:**
   ```bash
   # Terminal 1: Backend
   cd backend
   mvn spring-boot:run

   # Terminal 2: Frontend
   cd frontend
   npm run dev
   ```

---

## Development Workflow

### Branch Strategy

We follow the **Git Flow** branching model:

- `main` - Production-ready code
- `develop` - Integration branch for features
- `feature/*` - New features
- `bugfix/*` - Bug fixes
- `hotfix/*` - Urgent production fixes

### Creating a Feature Branch

```bash
# Update your local repository
git checkout develop
git pull upstream develop

# Create a feature branch
git checkout -b feature/your-feature-name

# Make your changes and commit
git add .
git commit -m "feat: Add your feature"

# Push to your fork
git push origin feature/your-feature-name
```

### Keeping Your Fork Updated

```bash
# Fetch upstream changes
git fetch upstream

# Merge upstream develop into your develop
git checkout develop
git merge upstream/develop

# Rebase your feature branch
git checkout feature/your-feature-name
git rebase develop
```

---

## Coding Standards

### Java/Spring Boot Standards

**Style Guide:** Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)

**Key Conventions:**
- Use 4 spaces for indentation (no tabs)
- Maximum line length: 120 characters
- Use meaningful variable and method names
- Always use `@Override` annotation
- Use Lombok annotations to reduce boilerplate

**Example:**
```java
@Service
@Slf4j
public class CandidateService {

    @Autowired
    private CandidateRepository repository;

    /**
     * Create a new candidate.
     *
     * @param candidate the candidate to create
     * @return the created candidate
     */
    public Candidate createCandidate(Candidate candidate) {
        log.info("Creating candidate: {}", candidate.getEmail());
        validateCandidate(candidate);
        return repository.save(candidate);
    }

    private void validateCandidate(Candidate candidate) {
        if (candidate.getEmail() == null || candidate.getEmail().isEmpty()) {
            throw new ValidationException("Email is required");
        }
    }
}
```

### TypeScript/React Standards

**Style Guide:** Follow [Airbnb JavaScript/React Style Guide](https://github.com/airbnb/javascript)

**Key Conventions:**
- Use 2 spaces for indentation
- Use functional components with hooks
- Use TypeScript strict mode
- Use arrow functions for callbacks
- Use PascalCase for components, camelCase for functions

**Example:**
```typescript
import React, { useState, useEffect } from 'react';
import { Candidate } from '../types';
import { api } from '../services/api';

interface CandidateListProps {
  jobId: number;
}

export const CandidateList: React.FC<CandidateListProps> = ({ jobId }) => {
  const [candidates, setCandidates] = useState<Candidate[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCandidates = async () => {
      try {
        const response = await api.candidates.getAll();
        setCandidates(response.data);
      } catch (error) {
        console.error('Error fetching candidates:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchCandidates();
  }, [jobId]);

  if (loading) return <div>Loading...</div>;

  return (
    <div className="grid grid-cols-3 gap-4">
      {candidates.map((candidate) => (
        <CandidateCard key={candidate.id} candidate={candidate} />
      ))}
    </div>
  );
};
```

### File Organization

**Backend:**
```
src/main/java/com/recruitment/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/             # Data Transfer Objects
├── entity/          # JPA entities
├── exception/       # Custom exceptions
├── ml/              # AI/ML services
├── repository/      # Spring Data repositories
├── service/         # Business logic
└── util/            # Utility classes
```

**Frontend:**
```
src/
├── components/      # Reusable UI components
├── pages/           # Page components
├── services/        # API services
├── store/           # State management
├── types/           # TypeScript interfaces
├── utils/           # Utility functions
└── styles/          # CSS files
```

---

## Commit Guidelines

We follow the [Conventional Commits](https://www.conventionalcommits.org/) specification.

### Commit Message Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- `feat`: A new feature
- `fix`: A bug fix
- `docs`: Documentation only changes
- `style`: Code style changes (formatting, missing semi-colons, etc.)
- `refactor`: Code change that neither fixes a bug nor adds a feature
- `perf`: Performance improvements
- `test`: Adding or updating tests
- `build`: Changes to build system or dependencies
- `ci`: Changes to CI configuration
- `chore`: Other changes that don't modify src or test files

### Examples

**Feature:**
```
feat(ml): Add candidate similarity matching algorithm

Implement cosine similarity-based matching to find candidates
with similar skill profiles. This helps recruiters identify
backup candidates for positions.

Closes #123
```

**Bug Fix:**
```
fix(api): Correct interview scheduling conflict detection

Fix bug where interviews scheduled within 30 minutes of each
other were not detected as conflicts. Updated the time range
check in InterviewService.optimizeInterviewTime().

Fixes #456
```

**Documentation:**
```
docs(readme): Update installation instructions

Add Docker Compose setup instructions and troubleshooting
section for common database connection issues.
```

### Commit Message Rules

- Use present tense ("Add feature" not "Added feature")
- Use imperative mood ("Move cursor to..." not "Moves cursor to...")
- First line should be 50 characters or less
- Reference issues and pull requests in the footer
- Explain **what** and **why**, not **how**

---

## Pull Request Process

### Before Submitting

1. **Update your branch:**
   ```bash
   git checkout develop
   git pull upstream develop
   git checkout feature/your-feature
   git rebase develop
   ```

2. **Run tests:**
   ```bash
   # Backend
   cd backend
   mvn test

   # Frontend
   cd frontend
   npm test
   ```

3. **Check code style:**
   ```bash
   # Backend
   mvn checkstyle:check

   # Frontend
   npm run lint
   ```

4. **Update documentation** if needed

### Creating the Pull Request

1. **Push your changes:**
   ```bash
   git push origin feature/your-feature
   ```

2. **Create PR on GitHub**
   - Base: `develop`
   - Compare: `your-fork/feature/your-feature`
   - Fill in the PR template

3. **PR Title Format:**
   ```
   feat(scope): Brief description
   ```

### PR Description Template

```markdown
## Description
Brief description of what this PR does.

## Type of Change
- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Documentation update

## Related Issues
Closes #123
Relates to #456

## Changes Made
- Added new ML algorithm for candidate ranking
- Updated CandidateService to use new algorithm
- Added unit tests for new functionality
- Updated API documentation

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing completed
- [ ] Code coverage maintained/improved

## Screenshots (if applicable)
[Add screenshots here]

## Checklist
- [ ] My code follows the project's style guidelines
- [ ] I have performed a self-review of my own code
- [ ] I have commented my code, particularly in hard-to-understand areas
- [ ] I have made corresponding changes to the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix is effective or that my feature works
- [ ] New and existing unit tests pass locally with my changes
- [ ] Any dependent changes have been merged and published

## Additional Notes
Any additional information or context.
```

### Review Process

1. **Automated checks** must pass:
   - Build successful
   - Tests passing
   - Code style checks
   - No merge conflicts

2. **Code review** by at least 1 maintainer:
   - Code quality
   - Test coverage
   - Documentation
   - Design patterns

3. **Changes requested:**
   - Make requested changes
   - Push new commits to same branch
   - Respond to review comments

4. **Approval and merge:**
   - Maintainer will merge PR
   - Delete feature branch after merge

---

## Testing Requirements

### Backend Testing

**Required:**
- Unit tests for all service methods
- Integration tests for all API endpoints
- Test coverage: minimum 70%

**Example Unit Test:**
```java
@SpringBootTest
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

        Candidate created = service.createCandidate(candidate);

        assertNotNull(created);
        assertEquals("John", created.getFirstName());
        verify(repository, times(1)).save(candidate);
    }

    @Test
    void testCreateCandidateWithInvalidEmail() {
        Candidate candidate = Candidate.builder()
            .firstName("John")
            .build();

        assertThrows(ValidationException.class, () -> {
            service.createCandidate(candidate);
        });
    }
}
```

**Example Integration Test:**
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

    @Test
    void testCreateCandidate() throws Exception {
        String candidateJson = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john@example.com"
            }
            """;

        mockMvc.perform(post("/api/candidates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(candidateJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.firstName").value("John"));
    }
}
```

### Frontend Testing

**Required:**
- Component tests for all components
- Integration tests for pages
- Test coverage: minimum 60%

**Example Component Test:**
```typescript
import { render, screen } from '@testing-library/react';
import { CandidateCard } from './CandidateCard';

describe('CandidateCard', () => {
  const mockCandidate = {
    id: 1,
    firstName: 'John',
    lastName: 'Doe',
    email: 'john@example.com',
    matchingScore: 0.85,
    skills: [{ id: 1, name: 'Java' }]
  };

  it('renders candidate information', () => {
    render(<CandidateCard candidate={mockCandidate} />);

    expect(screen.getByText('John Doe')).toBeInTheDocument();
    expect(screen.getByText('john@example.com')).toBeInTheDocument();
    expect(screen.getByText('Java')).toBeInTheDocument();
  });

  it('displays matching score as percentage', () => {
    render(<CandidateCard candidate={mockCandidate} />);

    expect(screen.getByText('85%')).toBeInTheDocument();
  });
});
```

### Running Tests

```bash
# Backend - all tests
mvn test

# Backend - specific test
mvn test -Dtest=CandidateServiceTest

# Backend - with coverage
mvn test jacoco:report

# Frontend - all tests
npm test

# Frontend - with coverage
npm test -- --coverage

# Frontend - watch mode
npm test -- --watch
```

---

## Documentation

### Code Documentation

**Java:**
- Use JavaDoc for all public methods
- Document complex algorithms
- Include @param and @return tags
- Add usage examples for complex features

**TypeScript:**
- Use JSDoc for exported functions/components
- Document complex logic
- Include @param and @returns tags

**Example JavaDoc:**
```java
/**
 * Calculate ML-based ranking score for a candidate.
 *
 * Uses a multi-factor weighted scoring model that considers:
 * - Skill match (35%)
 * - Experience (25%)
 * - Sentiment (15%)
 * - Education (15%)
 * - AI fit score (10%)
 *
 * @param candidate the candidate to score
 * @param jobPosition the job position to match against
 * @return normalized score between 0.0 and 1.0
 * @throws IllegalArgumentException if candidate or jobPosition is null
 */
public double calculateMLRankingScore(Candidate candidate, JobPosition jobPosition) {
    // Implementation
}
```

### README Updates

When adding new features, update:
- Feature list in README.md
- API endpoints section
- Installation/setup instructions
- Configuration options

### API Documentation

Update Swagger annotations for new endpoints:

```java
@Operation(summary = "Get ML-based candidate ranking",
           description = "Ranks all candidates for a job using ML algorithm")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successfully ranked candidates"),
    @ApiResponse(responseCode = "404", description = "Job position not found"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
})
@GetMapping("/ml-rank")
public ResponseEntity<Map<String, Object>> rankCandidates(
    @Parameter(description = "Job position ID") @RequestParam Long jobId) {
    // Implementation
}
```

---

## Issue Reporting

### Bug Reports

Use the bug report template:

```markdown
**Describe the bug**
A clear and concise description of what the bug is.

**To Reproduce**
Steps to reproduce the behavior:
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

**Expected behavior**
A clear and concise description of what you expected to happen.

**Screenshots**
If applicable, add screenshots to help explain your problem.

**Environment:**
- OS: [e.g. Ubuntu 20.04]
- Java version: [e.g. 17]
- Node version: [e.g. 16.14]
- Browser: [e.g. chrome, safari]

**Additional context**
Add any other context about the problem here.
```

### Feature Requests

Use the feature request template:

```markdown
**Is your feature request related to a problem?**
A clear and concise description of what the problem is.

**Describe the solution you'd like**
A clear and concise description of what you want to happen.

**Describe alternatives you've considered**
A clear and concise description of any alternative solutions or features you've considered.

**Additional context**
Add any other context or screenshots about the feature request here.

**Implementation ideas**
If you have ideas on how to implement this, share them here.
```

---

## Community

### Communication Channels

- **GitHub Issues:** Bug reports and feature requests
- **GitHub Discussions:** General questions and discussions
- **Email:** dev@recruitment-platform.com

### Getting Help

1. Check existing documentation
2. Search closed issues
3. Ask in GitHub Discussions
4. Email support team

### Recognition

Contributors will be:
- Listed in CONTRIBUTORS.md
- Mentioned in release notes
- Credited in documentation

---

## License

By contributing, you agree that your contributions will be licensed under the project's MIT License.

---

**Thank you for contributing to the AI-Enhanced Recruitment Platform!** 🎉

Your efforts help make recruitment better for everyone.
