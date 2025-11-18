package com.recruitment.controller;

import com.recruitment.entity.Interview;
import com.recruitment.entity.InterviewStatus;
import com.recruitment.service.CandidateService;
import com.recruitment.service.InterviewService;
import com.recruitment.service.JobPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/interviews")
@CrossOrigin(origins = "*")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private JobPositionService jobPositionService;

    /**
     * Schedule a new interview with AI optimization
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> scheduleInterview(@RequestBody Map<String, Object> requestBody) {
        try {
            Long candidateId = Long.valueOf(requestBody.get("candidateId").toString());
            Long jobId = Long.valueOf(requestBody.get("jobId").toString());
            String interviewer = (String) requestBody.get("interviewer");

            LocalDateTime preferredTime = null;
            if (requestBody.containsKey("preferredTime") && requestBody.get("preferredTime") != null) {
                preferredTime = LocalDateTime.parse(requestBody.get("preferredTime").toString());
            }

            Interview interview = interviewService.scheduleInterview(candidateId, jobId, interviewer, preferredTime);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", interview);
            response.put("message", "Interview scheduled successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get all interviews
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllInterviews() {
        List<Interview> interviews = interviewService.getAllInterviews();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", interviews);
        response.put("count", interviews.size());

        return ResponseEntity.ok(response);
    }

    /**
     * Get interview by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getInterviewById(@PathVariable Long id) {
        try {
            Interview interview = interviewService.getInterviewById(id);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", interview);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Update interview (status, notes, rating)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateInterview(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updateData) {
        try {
            InterviewStatus status = updateData.containsKey("status")
                    ? InterviewStatus.valueOf(updateData.get("status").toString())
                    : null;

            String notes = updateData.containsKey("notes")
                    ? (String) updateData.get("notes")
                    : null;

            Double rating = updateData.containsKey("rating")
                    ? Double.valueOf(updateData.get("rating").toString())
                    : null;

            Interview updatedInterview = interviewService.updateInterview(id, status, notes, rating);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", updatedInterview);
            response.put("message", "Interview updated successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get interviews for a specific candidate
     */
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<Map<String, Object>> getInterviewsByCandidate(@PathVariable Long candidateId) {
        List<Interview> interviews = interviewService.getInterviewsByCandidate(candidateId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", interviews);
        response.put("count", interviews.size());

        return ResponseEntity.ok(response);
    }

    /**
     * Get interviews for a specific job position
     */
    @GetMapping("/job/{jobId}")
    public ResponseEntity<Map<String, Object>> getInterviewsByJob(@PathVariable Long jobId) {
        List<Interview> interviews = interviewService.getInterviewsByJob(jobId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", interviews);
        response.put("count", interviews.size());

        return ResponseEntity.ok(response);
    }

    /**
     * Get upcoming scheduled interviews
     */
    @GetMapping("/upcoming")
    public ResponseEntity<Map<String, Object>> getUpcomingInterviews() {
        List<Interview> interviews = interviewService.getUpcomingInterviews();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", interviews);
        response.put("count", interviews.size());

        return ResponseEntity.ok(response);
    }

    /**
     * Cancel an interview
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> cancelInterview(@PathVariable Long id) {
        try {
            interviewService.cancelInterview(id);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Interview cancelled successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Generate AI-powered interview questions
     */
    @GetMapping("/generate-questions")
    public ResponseEntity<Map<String, Object>> generateInterviewQuestions(
            @RequestParam Long candidateId,
            @RequestParam Long jobId) {
        try {
            List<String> questions = interviewService.generateInterviewQuestions(candidateId, jobId);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", questions);
            response.put("count", questions.size());
            response.put("message", "Interview questions generated successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Calculate AI-powered candidate-job fit score
     */
    @GetMapping("/job-fit-score")
    public ResponseEntity<Map<String, Object>> calculateJobFitScore(
            @RequestParam Long candidateId,
            @RequestParam Long jobId) {
        try {
            Map<String, Object> fitScore = interviewService.calculateJobFitScore(candidateId, jobId);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", fitScore);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get interview statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getInterviewStatistics() {
        Map<String, Object> stats = interviewService.getInterviewStatistics();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", stats);

        return ResponseEntity.ok(response);
    }

    /**
     * Suggest optimal interview time slots
     */
    @GetMapping("/suggest-slots")
    public ResponseEntity<Map<String, Object>> suggestInterviewSlots(
            @RequestParam(defaultValue = "10") int numberOfSlots) {
        List<LocalDateTime> slots = interviewService.suggestInterviewSlots(numberOfSlots);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", slots);
        response.put("count", slots.size());
        response.put("message", "Suggested interview slots for optimal scheduling");

        return ResponseEntity.ok(response);
    }

    /**
     * Optimize interview time for a candidate-job pair
     */
    @GetMapping("/optimize-time")
    public ResponseEntity<Map<String, Object>> optimizeInterviewTime(
            @RequestParam Long candidateId,
            @RequestParam Long jobId) {
        try {
            LocalDateTime optimizedTime = interviewService.optimizeInterviewTime(
                    candidateService.getCandidateById(candidateId),
                    jobPositionService.getJobById(jobId)
            );

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", optimizedTime);
            response.put("message", "Optimized interview time calculated using AI");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
