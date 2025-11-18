package com.recruitment.controller;

import com.recruitment.dto.CandidateDTO;
import com.recruitment.entity.Candidate;
import com.recruitment.entity.JobPosition;
import com.recruitment.ml.MLCandidateRankingService;
import com.recruitment.service.CandidateService;
import com.recruitment.service.JobPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/candidates")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private MLCandidateRankingService mlRankingService;

    @Autowired
    private JobPositionService jobPositionService;

    @PostMapping
    public ResponseEntity<CandidateDTO> createCandidate(@RequestBody CandidateDTO candidateDTO) {
        CandidateDTO createdCandidate = candidateService.createCandidate(candidateDTO);
        return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getCandidateById(@PathVariable Long id) {
        CandidateDTO candidate = candidateService.getCandidateById(id);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CandidateDTO> getCandidateByEmail(@PathVariable String email) {
        CandidateDTO candidate = candidateService.getCandidateByEmail(email);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> getAllCandidates() {
        List<CandidateDTO> candidates = candidateService.getAllCandidates();
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CandidateDTO>> searchCandidates(@RequestParam String name) {
        List<CandidateDTO> candidates = candidateService.searchCandidatesByName(name);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/top-candidates")
    public ResponseEntity<List<CandidateDTO>> getTopCandidates(@RequestParam(defaultValue = "10") int limit) {
        List<CandidateDTO> topCandidates = candidateService.getTopCandidates(limit);
        return new ResponseEntity<>(topCandidates, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDTO> updateCandidate(
            @PathVariable Long id,
            @RequestBody CandidateDTO candidateDTO) {
        CandidateDTO updatedCandidate = candidateService.updateCandidate(id, candidateDTO);
        return new ResponseEntity<>(updatedCandidate, HttpStatus.OK);
    }

    @PostMapping("/{id}/parse-resume")
    public ResponseEntity<CandidateDTO> parseResume(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String resumeText = request.get("resumeText");
        CandidateDTO candidate = candidateService.parseAndProcessResume(id, resumeText);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * ML-based candidate ranking for a specific job
     */
    @GetMapping("/ml-rank")
    public ResponseEntity<Map<String, Object>> rankCandidatesForJob(@RequestParam Long jobId) {
        try {
            JobPosition job = jobPositionService.getJobById(jobId);
            List<Candidate> allCandidates = candidateService.getAllCandidates().stream()
                    .map(dto -> candidateService.getCandidateById(dto.getId()))
                    .collect(Collectors.toList());

            List<Map<String, Object>> rankedCandidates = mlRankingService.rankCandidates(allCandidates, job);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", rankedCandidates);
            response.put("jobId", jobId);
            response.put("totalCandidates", rankedCandidates.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get ML-based top candidates for a job
     */
    @GetMapping("/ml-top-candidates")
    public ResponseEntity<Map<String, Object>> getMLTopCandidates(
            @RequestParam Long jobId,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            JobPosition job = jobPositionService.getJobById(jobId);
            List<Candidate> allCandidates = candidateService.getAllCandidates().stream()
                    .map(dto -> candidateService.getCandidateById(dto.getId()))
                    .collect(Collectors.toList());

            List<Candidate> topCandidates = mlRankingService.getTopCandidates(allCandidates, job, limit);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", topCandidates);
            response.put("count", topCandidates.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get candidate features for ML analysis
     */
    @GetMapping("/{id}/features")
    public ResponseEntity<Map<String, Object>> getCandidateFeatures(
            @PathVariable Long id,
            @RequestParam Long jobId) {
        try {
            Candidate candidate = candidateService.getCandidateById(id);
            JobPosition job = jobPositionService.getJobById(jobId);

            Map<String, Double> features = mlRankingService.extractFeatures(candidate, job);
            double mlScore = mlRankingService.calculateMLRankingScore(candidate, job);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("candidateId", id);
            response.put("jobId", jobId);
            response.put("mlScore", mlScore);
            response.put("features", features);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get candidate pool statistics
     */
    @GetMapping("/pool-statistics")
    public ResponseEntity<Map<String, Object>> getCandidatePoolStatistics(@RequestParam Long jobId) {
        try {
            JobPosition job = jobPositionService.getJobById(jobId);
            List<Candidate> allCandidates = candidateService.getAllCandidates().stream()
                    .map(dto -> candidateService.getCandidateById(dto.getId()))
                    .collect(Collectors.toList());

            Map<String, Object> stats = mlRankingService.getCandidatePoolStatistics(allCandidates, job);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", stats);
            response.put("jobId", jobId);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Find similar candidates
     */
    @GetMapping("/{id}/similar")
    public ResponseEntity<Map<String, Object>> findSimilarCandidates(
            @PathVariable Long id,
            @RequestParam Long jobId,
            @RequestParam(defaultValue = "5") int limit) {
        try {
            Candidate referenceCandidate = candidateService.getCandidateById(id);
            JobPosition job = jobPositionService.getJobById(jobId);
            List<Candidate> allCandidates = candidateService.getAllCandidates().stream()
                    .map(dto -> candidateService.getCandidateById(dto.getId()))
                    .collect(Collectors.toList());

            List<Map<String, Object>> similarCandidates = mlRankingService.findSimilarCandidates(
                    referenceCandidate, allCandidates, job, limit);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", similarCandidates);
            response.put("referenceCandidateId", id);
            response.put("count", similarCandidates.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
