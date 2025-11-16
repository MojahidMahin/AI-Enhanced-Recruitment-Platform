package com.recruitment.controller;

import com.recruitment.dto.CandidateDTO;
import com.recruitment.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/candidates")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

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
}
