# 📖 User Guide - AI-Enhanced Recruitment Platform

## Table of Contents
1. [Introduction](#introduction)
2. [Getting Started](#getting-started)
3. [User Roles](#user-roles)
4. [Core Features](#core-features)
5. [Using the Platform](#using-the-platform)
6. [AI Features Explained](#ai-features-explained)
7. [Best Practices](#best-practices)
8. [Troubleshooting](#troubleshooting)
9. [FAQ](#faq)

---

## Introduction

The AI-Enhanced Recruitment Platform is an intelligent hiring solution that uses Artificial Intelligence and Machine Learning to help you find the best candidates for your job positions. It automates resume parsing, ranks candidates intelligently, and optimizes your interview scheduling.

### Who is this for?
- **HR Managers**: Manage recruitment pipeline efficiently
- **Recruiters**: Find and evaluate candidates quickly
- **Hiring Managers**: Review top candidates with data-driven insights
- **Interviewers**: Get AI-generated interview questions

---

## Getting Started

### Accessing the Platform

1. **Web Application**: Navigate to `http://your-domain.com` (or `http://localhost:3000` for local)
2. **API Access**: Use the REST API at `http://your-domain.com/api`
3. **API Documentation**: Interactive docs at `http://your-domain.com/api/swagger-ui.html`

### First-Time Setup

1. **Login**: Use your credentials provided by your administrator
2. **Profile**: Complete your profile information
3. **Permissions**: Verify your role and permissions
4. **Tutorial**: Follow the in-app tutorial (first login only)

---

## User Roles

### Admin
- Full system access
- User management
- System configuration
- Analytics and reporting

### Recruiter
- Create and manage job positions
- Search and review candidates
- Schedule interviews
- Use AI features

### Hiring Manager
- Review candidates for their positions
- Participate in interviews
- Access AI recommendations
- Provide feedback

### Interviewer
- View assigned interviews
- Access candidate profiles
- Use AI-generated questions
- Submit interview ratings

---

## Core Features

### 1. 📋 Candidate Management

#### Adding a Candidate

**Manual Entry:**
1. Navigate to **Candidates** > **Add New**
2. Fill in basic information:
   - First Name & Last Name
   - Email Address
   - Phone Number
   - Summary/Bio
3. Add skills manually or upload resume
4. Save candidate

**Resume Upload (AI-Powered):**
1. Click **Upload Resume** button
2. Select PDF or text file
3. AI automatically extracts:
   - Contact information
   - Skills
   - Education
   - Experience
   - Professional summary
4. Review and confirm extracted data
5. Save candidate

#### Searching Candidates

**Basic Search:**
- Use search bar to find by name or email
- Results show match score and key skills

**Advanced Filters:**
- Filter by skills
- Filter by matching score (0-100%)
- Filter by sentiment score
- Filter by status (New, Under Review, Shortlisted, etc.)

#### Candidate Profile

Each profile shows:
- **Basic Info**: Contact details, summary
- **Skills**: All identified technical and soft skills
- **Scores**:
  - Matching Score: How well they fit jobs
  - Sentiment Score: Professionalism rating
  - AI Fit Score: AI's overall assessment
- **Applications**: Jobs they've applied for
- **Interviews**: Scheduled and completed interviews
- **Resume**: Full text of resume

---

### 2. 💼 Job Position Management

#### Creating a Job Position

1. Go to **Jobs** > **Create New**
2. Enter job details:
   - Title (e.g., "Senior Java Developer")
   - Department
   - Location
   - Description
   - Salary Range (min/max)
3. Add required skills:
   - Select from existing skills
   - Add new skills if needed
4. Set status (Open/Closed/On Hold)
5. Save position

#### Job Matching

Once a job is created:
- AI automatically ranks all candidates
- See top matching candidates instantly
- View compatibility scores
- Access detailed skill match breakdown

---

### 3. 🎯 AI-Powered Candidate Ranking

#### How It Works

The platform uses Machine Learning to rank candidates with multiple factors:

**Scoring Breakdown:**
- **35% Skill Match**: Do they have required skills?
- **25% Experience**: Years of relevant experience
- **15% Sentiment**: Professionalism in resume
- **15% Education**: Degree level and relevance
- **10% AI Fit**: OpenAI's compatibility analysis

#### Using ML Ranking

1. Open a **Job Position**
2. Click **View Rankings** or **Top Candidates**
3. See ranked list with scores:
   - Overall ML Score (0-100)
   - Percentile ranking
   - Feature breakdown
4. Click any candidate to see detailed analysis

#### Understanding Scores

- **90-100**: Excellent match, highly recommended
- **75-89**: Very good match, strong candidate
- **60-74**: Good match, worth considering
- **45-59**: Moderate match, review carefully
- **Below 45**: Low match, may not be suitable

---

### 4. 📅 Interview Scheduling

#### Smart Scheduling (AI-Optimized)

1. Select a **Candidate**
2. Select a **Job Position**
3. Click **Schedule Interview**
4. Choose method:
   - **AI Optimize**: Let AI pick the best time
   - **Manual**: Pick your own time
5. Enter interviewer name
6. Confirm

**AI Considers:**
- Best days: Tuesday-Thursday (better focus)
- Best times: 10 AM - 3 PM (peak productivity)
- Avoids: Lunch hours, weekends, conflicts
- Existing interview schedule

#### Interview Management

**Before Interview:**
- Access **AI-Generated Questions** specific to candidate/job
- Review candidate profile
- See job fit analysis

**During Interview:**
- Mark status as "In Progress"
- Take notes in the system

**After Interview:**
- Update status to "Completed"
- Add rating (1-10)
- Add detailed notes/feedback
- System stores all history

#### AI-Generated Interview Questions

1. Open scheduled interview
2. Click **Generate Questions**
3. Get 10 tailored questions based on:
   - Candidate's resume
   - Job requirements
   - Skill gaps
   - Experience level

**Example Questions:**
- "You mentioned experience with microservices. Can you describe a challenging distributed system you built?"
- "The role requires React expertise. Tell me about a complex frontend feature you've implemented."

---

### 5. 🔍 Advanced Search & Analytics

#### Candidate Pool Statistics

For any job position, view:
- Total candidates in pool
- Average ML score
- Median score
- Standard deviation
- Top/bottom scores
- Skill distribution

#### Find Similar Candidates

1. Select a high-performing candidate
2. Click **Find Similar**
3. Get candidates with similar:
   - Skill profiles
   - Experience levels
   - Education backgrounds
   - Score patterns

Use case: Found a great candidate but need more? Find similar profiles!

---

### 6. 🤖 AI Features

#### Resume Parsing (OpenAI GPT)

**What it does:**
- Reads entire resume (PDF or text)
- Extracts structured data using GPT-3.5
- Identifies skills with high accuracy
- Analyzes tone and professionalism

**How to use:**
1. Upload resume file
2. Wait 5-10 seconds for AI processing
3. Review extracted data
4. Edit if needed
5. Save

#### Job Fit Analysis

**What it does:**
- AI analyzes candidate-job compatibility
- Provides detailed reasoning
- Identifies strengths and gaps
- Gives hiring recommendation

**How to use:**
1. Select candidate and job
2. Click **Get AI Fit Score**
3. View results:
   - Score: 0-100
   - Fit Level: Poor/Fair/Good/Excellent
   - Strengths: 2-3 key strengths
   - Gaps: Skill/experience gaps
   - Recommendation: Hire/Interview/Reject with reasoning

**Example Output:**
```
Score: 87/100
Fit Level: Excellent
Strengths:
  - Strong Java and Spring Boot experience (5+ years)
  - Proven track record in microservices architecture
  - Excellent communication skills evident in resume

Gaps:
  - Limited cloud deployment experience (AWS preferred)
  - No mention of Docker/Kubernetes

Recommendation: HIRE - Strong technical fit with minor gaps
that can be addressed through training. High potential for success.
```

#### Sentiment Analysis

**What it measures:**
- Professionalism in writing
- Positive vs negative language
- Confidence level
- Communication clarity

**Score range:** 0.0 (poor) to 1.0 (excellent)
- **0.8-1.0**: Highly professional, excellent communication
- **0.6-0.8**: Professional, good communication
- **0.4-0.6**: Acceptable, room for improvement
- **Below 0.4**: May indicate poor communication skills

---

## Using the Platform

### Daily Workflow for Recruiters

#### Morning Routine
1. **Check Dashboard**
   - New applications
   - Today's interviews
   - Pending tasks

2. **Review New Candidates**
   - Upload resumes
   - Let AI parse and score
   - Add to job pipelines

3. **Schedule Interviews**
   - Use AI optimization for best times
   - Generate interview questions
   - Send calendar invites

#### Throughout the Day
4. **Conduct Interviews**
   - Use AI-generated questions
   - Take notes in system
   - Rate candidates

5. **Analyze Candidates**
   - Review ML rankings
   - Check job fit scores
   - Compare similar candidates

6. **Make Decisions**
   - Shortlist top candidates
   - Reject poor fits
   - Move candidates through pipeline

---

### Best Practices

#### For Recruiters

1. **Always Upload Resumes**
   - Let AI extract data (saves 5-10 min per resume)
   - Review AI extractions for accuracy
   - Add missing information

2. **Trust the ML Scores**
   - Top 10 candidates are usually solid
   - Pay attention to percentile rankings
   - Review feature breakdown for insights

3. **Use AI Interview Questions**
   - More relevant than generic questions
   - Tailored to candidate's background
   - Saves preparation time

4. **Let AI Optimize Interview Times**
   - Better scheduling efficiency
   - Respects best practices
   - Reduces conflicts

#### For Hiring Managers

1. **Review Top Candidates First**
   - Focus on top 10% (90+ score)
   - Check feature breakdowns
   - Read AI fit analysis

2. **Compare Similar Candidates**
   - Use "Find Similar" feature
   - Batch review similar profiles
   - Make consistent decisions

3. **Provide Detailed Feedback**
   - Rate all interviews (1-10)
   - Add specific notes
   - Helps improve AI over time

---

### Troubleshooting

#### Resume Upload Issues

**Problem:** Resume won't upload
- **Solution**: Check file format (PDF or TXT only)
- **Solution**: File size must be under 10MB
- **Solution**: Ensure file is not corrupted

**Problem:** AI extraction is incomplete
- **Solution**: Resume may have unusual formatting
- **Solution**: Manually add missing information
- **Solution**: Try re-uploading in different format

#### Scoring Issues

**Problem:** Candidate has 0% match
- **Cause**: No overlapping skills with job
- **Solution**: Add more skills to candidate or job
- **Solution**: Check if skills are spelled correctly

**Problem:** AI features not working
- **Cause**: OpenAI API key not configured
- **Solution**: Contact administrator
- **Note**: Basic features still work without AI

#### Interview Scheduling

**Problem:** No suggested time slots
- **Cause**: Calendar is fully booked
- **Solution**: Expand time range
- **Solution**: Manually select a time

---

## FAQ

### General Questions

**Q: Do I need an OpenAI account?**
A: No, the system administrator configures this. Basic features work without it.

**Q: How accurate is the AI scoring?**
A: 85-90% accuracy based on testing. Always review AI recommendations.

**Q: Can I override AI recommendations?**
A: Yes! AI assists you, but you make final decisions.

**Q: Is my data secure?**
A: Yes. All data is encrypted. OpenAI doesn't store your data.

### Feature Questions

**Q: What file formats are supported for resumes?**
A: PDF and plain text (.txt) files up to 10MB.

**Q: How long does resume parsing take?**
A: 5-10 seconds with AI, instant with basic parsing.

**Q: Can I edit AI-extracted data?**
A: Yes, all extracted data is editable.

**Q: How often are candidates re-ranked?**
A: Rankings update in real-time when data changes.

**Q: Can I export candidate lists?**
A: Yes, use the Export button on any list view.

### Technical Questions

**Q: What browsers are supported?**
A: Chrome, Firefox, Safari, Edge (latest versions).

**Q: Is there a mobile app?**
A: Not yet, but the web interface is mobile-responsive.

**Q: Can I integrate with our ATS?**
A: API integration is available. Contact support.

**Q: How do I report bugs?**
A: Use the feedback button or contact support@recruitment-platform.com

---

## Getting Help

### Support Channels

1. **In-App Help**: Click the ? icon in top-right corner
2. **Email Support**: support@recruitment-platform.com
3. **Documentation**: docs.recruitment-platform.com
4. **Training Videos**: training.recruitment-platform.com

### Response Times

- **Critical Issues**: 2 hours
- **General Support**: 24 hours
- **Feature Requests**: 1 week

---

## Keyboard Shortcuts

- `Ctrl/Cmd + K`: Quick search
- `Ctrl/Cmd + N`: New candidate
- `Ctrl/Cmd + J`: New job
- `Ctrl/Cmd + I`: Schedule interview
- `Esc`: Close modal/dialog
- `?`: Show shortcuts help

---

## Privacy & Data

### What data is collected?
- Candidate information you enter
- Resume contents
- Interview notes and ratings
- Usage analytics (anonymized)

### What is sent to OpenAI?
- Resume text (for parsing)
- Job descriptions (for matching)
- Candidate summaries (for fit analysis)

### What is NOT sent?
- Personal identifiable information (emails, phones)
- User passwords
- System configuration
- Database backups

### Data retention
- Active candidates: Indefinitely
- Rejected candidates: 2 years (configurable)
- Interview data: 5 years
- Audit logs: 7 years

---

**Last Updated:** November 2025
**Version:** 1.0.0
**For more information:** support@recruitment-platform.com
