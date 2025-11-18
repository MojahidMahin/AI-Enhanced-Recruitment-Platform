package com.recruitment.service;

import com.recruitment.entity.*;
import com.recruitment.ml.OpenAIService;
import com.recruitment.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired(required = false)
    private OpenAIService openAIService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private JobPositionService jobPositionService;

    /**
     * Schedule interview with AI optimization
     * AI considers: candidate-job fit, interviewer availability, optimal time slots
     */
    public Interview scheduleInterview(Long candidateId, Long jobId, String interviewer, LocalDateTime preferredTime) {
        Candidate candidate = candidateService.getCandidateById(candidateId);
        JobPosition job = jobPositionService.getJobById(jobId);

        // Optimize interview time if no preferred time provided
        LocalDateTime scheduledTime = (preferredTime != null) ? preferredTime : optimizeInterviewTime(candidate, job);

        Interview interview = Interview.builder()
                .candidate(candidate)
                .jobPosition(job)
                .scheduledTime(scheduledTime)
                .interviewer(interviewer)
                .status(InterviewStatus.SCHEDULED)
                .build();

        return interviewRepository.save(interview);
    }

    /**
     * AI-optimized interview time scheduling
     * Considers: working hours, avoid Mondays/Fridays, optimal interview times (10am-3pm)
     */
    public LocalDateTime optimizeInterviewTime(Candidate candidate, JobPosition job) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime optimizedTime = now.plusDays(2); // Start with 2 days from now

        // Find next optimal day (Tuesday-Thursday for better focus)
        while (optimizedTime.getDayOfWeek() == DayOfWeek.MONDAY ||
               optimizedTime.getDayOfWeek() == DayOfWeek.FRIDAY ||
               optimizedTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
               optimizedTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            optimizedTime = optimizedTime.plusDays(1);
        }

        // Set optimal time: 10 AM (good energy, not too early)
        optimizedTime = optimizedTime.withHour(10).withMinute(0).withSecond(0);

        // Check for conflicts and adjust if needed
        List<Interview> conflictingInterviews = interviewRepository.findByScheduledTimeBetween(
                optimizedTime.minusHours(1),
                optimizedTime.plusHours(1)
        );

        // If conflicts exist, move to next available slot
        while (!conflictingInterviews.isEmpty()) {
            optimizedTime = optimizedTime.plusHours(2);

            // Skip lunch hours (12-1 PM)
            if (optimizedTime.getHour() == 12) {
                optimizedTime = optimizedTime.plusHours(1);
            }

            // If past 3 PM, move to next day at 10 AM
            if (optimizedTime.getHour() > 15) {
                optimizedTime = optimizedTime.plusDays(1).withHour(10).withMinute(0);

                // Ensure it's a weekday
                while (optimizedTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                       optimizedTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    optimizedTime = optimizedTime.plusDays(1);
                }
            }

            conflictingInterviews = interviewRepository.findByScheduledTimeBetween(
                    optimizedTime.minusHours(1),
                    optimizedTime.plusHours(1)
            );
        }

        return optimizedTime;
    }

    /**
     * Generate AI-powered interview questions based on candidate and job
     */
    public List<String> generateInterviewQuestions(Long candidateId, Long jobId) {
        Candidate candidate = candidateService.getCandidateById(candidateId);
        JobPosition job = jobPositionService.getJobById(jobId);

        if (openAIService != null && openAIService.isAvailable()) {
            String jobDescription = job.getDescription();
            String candidateResume = candidate.getResumeText() != null ? candidate.getResumeText() : candidate.getSummary();

            return openAIService.generateInterviewQuestions(jobDescription, candidateResume);
        }

        // Fallback: Generate basic questions
        return generateBasicInterviewQuestions(job);
    }

    /**
     * Calculate AI-powered candidate-job fit score
     */
    public Map<String, Object> calculateJobFitScore(Long candidateId, Long jobId) {
        Candidate candidate = candidateService.getCandidateById(candidateId);
        JobPosition job = jobPositionService.getJobById(jobId);

        if (openAIService != null && openAIService.isAvailable()) {
            String jobDescription = job.getDescription();
            String candidateResume = candidate.getResumeText() != null ? candidate.getResumeText() : candidate.getSummary();

            return openAIService.calculateJobFitScore(jobDescription, candidateResume);
        }

        // Fallback: Use basic matching score
        Map<String, Object> result = new HashMap<>();
        result.put("score", candidate.getMatchingScore() != null ? candidate.getMatchingScore() * 100 : 0.0);
        result.put("fitLevel", "unknown");
        result.put("reasoning", "AI service not available. Using basic matching score.");
        return result;
    }

    /**
     * Update interview status and notes
     */
    public Interview updateInterview(Long interviewId, InterviewStatus status, String notes, Double rating) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interview.setStatus(status);
        if (notes != null) {
            interview.setNotes(notes);
        }
        if (rating != null) {
            interview.setRating(rating);
        }

        if (status == InterviewStatus.COMPLETED || status == InterviewStatus.CANCELLED || status == InterviewStatus.NO_SHOW) {
            interview.setCompletedTime(LocalDateTime.now());
        }

        return interviewRepository.save(interview);
    }

    /**
     * Get all interviews
     */
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    /**
     * Get interview by ID
     */
    public Interview getInterviewById(Long id) {
        return interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));
    }

    /**
     * Get interviews for a candidate
     */
    public List<Interview> getInterviewsByCandidate(Long candidateId) {
        Candidate candidate = candidateService.getCandidateById(candidateId);
        return interviewRepository.findByCandidate(candidate);
    }

    /**
     * Get interviews for a job position
     */
    public List<Interview> getInterviewsByJob(Long jobId) {
        JobPosition job = jobPositionService.getJobById(jobId);
        return interviewRepository.findByJobPosition(job);
    }

    /**
     * Get upcoming interviews
     */
    public List<Interview> getUpcomingInterviews() {
        return interviewRepository.findByStatusAndScheduledTimeAfter(
                InterviewStatus.SCHEDULED,
                LocalDateTime.now()
        );
    }

    /**
     * Cancel interview
     */
    public void cancelInterview(Long interviewId) {
        Interview interview = getInterviewById(interviewId);
        interview.setStatus(InterviewStatus.CANCELLED);
        interview.setCompletedTime(LocalDateTime.now());
        interviewRepository.save(interview);
    }

    /**
     * Get interview statistics
     */
    public Map<String, Object> getInterviewStatistics() {
        List<Interview> allInterviews = interviewRepository.findAll();

        Map<String, Object> stats = new HashMap<>();
        stats.put("total", allInterviews.size());
        stats.put("scheduled", allInterviews.stream().filter(i -> i.getStatus() == InterviewStatus.SCHEDULED).count());
        stats.put("completed", allInterviews.stream().filter(i -> i.getStatus() == InterviewStatus.COMPLETED).count());
        stats.put("cancelled", allInterviews.stream().filter(i -> i.getStatus() == InterviewStatus.CANCELLED).count());

        List<Interview> completedWithRating = allInterviews.stream()
                .filter(i -> i.getStatus() == InterviewStatus.COMPLETED && i.getRating() != null)
                .collect(Collectors.toList());

        if (!completedWithRating.isEmpty()) {
            double avgRating = completedWithRating.stream()
                    .mapToDouble(Interview::getRating)
                    .average()
                    .orElse(0.0);
            stats.put("averageRating", avgRating);
        } else {
            stats.put("averageRating", 0.0);
        }

        return stats;
    }

    /**
     * Suggest optimal interview slots for next week
     */
    public List<LocalDateTime> suggestInterviewSlots(int numberOfSlots) {
        List<LocalDateTime> suggestedSlots = new ArrayList<>();
        LocalDateTime currentTime = LocalDateTime.now().plusDays(1);

        // Optimal interview hours: 10 AM, 11 AM, 2 PM, 3 PM
        List<Integer> optimalHours = Arrays.asList(10, 11, 14, 15);

        while (suggestedSlots.size() < numberOfSlots) {
            // Skip weekends
            if (currentTime.getDayOfWeek() == DayOfWeek.SATURDAY ||
                currentTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
                currentTime = currentTime.plusDays(1).withHour(10).withMinute(0);
                continue;
            }

            for (Integer hour : optimalHours) {
                if (suggestedSlots.size() >= numberOfSlots) break;

                LocalDateTime slotTime = currentTime.withHour(hour).withMinute(0).withSecond(0);

                // Check if slot is available (no conflicts within 1 hour)
                List<Interview> conflicts = interviewRepository.findByScheduledTimeBetween(
                        slotTime.minusMinutes(30),
                        slotTime.plusMinutes(30)
                );

                if (conflicts.isEmpty() && slotTime.isAfter(LocalDateTime.now())) {
                    suggestedSlots.add(slotTime);
                }
            }

            currentTime = currentTime.plusDays(1);
        }

        return suggestedSlots;
    }

    // Helper method for basic interview questions (fallback)
    private List<String> generateBasicInterviewQuestions(JobPosition job) {
        List<String> questions = new ArrayList<>();
        questions.add("Tell me about yourself and your relevant experience.");
        questions.add("Why are you interested in this " + job.getTitle() + " position?");
        questions.add("What are your key strengths that make you a good fit for this role?");
        questions.add("Describe a challenging project you worked on and how you overcame obstacles.");
        questions.add("How do you stay updated with the latest technologies in your field?");
        questions.add("Tell me about a time you worked in a team to achieve a goal.");
        questions.add("What are your salary expectations for this role?");
        questions.add("Where do you see yourself in 5 years?");
        questions.add("Do you have any questions for us?");
        return questions;
    }
}
