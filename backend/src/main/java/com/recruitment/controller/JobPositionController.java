package com.recruitment.controller;

import com.recruitment.dto.JobPositionDTO;
import com.recruitment.service.JobPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class JobPositionController {

    @Autowired
    private JobPositionService jobPositionService;

    @PostMapping
    public ResponseEntity<JobPositionDTO> createJobPosition(@RequestBody JobPositionDTO jobPositionDTO) {
        JobPositionDTO createdJob = jobPositionService.createJobPosition(jobPositionDTO);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPositionDTO> getJobPositionById(@PathVariable Long id) {
        JobPositionDTO job = jobPositionService.getJobPositionById(id);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<JobPositionDTO>> getAllJobPositions() {
        List<JobPositionDTO> jobs = jobPositionService.getAllJobPositions();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/open")
    public ResponseEntity<List<JobPositionDTO>> getOpenPositions() {
        List<JobPositionDTO> openJobs = jobPositionService.getOpenPositions();
        return new ResponseEntity<>(openJobs, HttpStatus.OK);
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<JobPositionDTO>> searchByTitle(@RequestParam String title) {
        List<JobPositionDTO> jobs = jobPositionService.searchJobsByTitle(title);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/search/department")
    public ResponseEntity<List<JobPositionDTO>> searchByDepartment(@RequestParam String department) {
        List<JobPositionDTO> jobs = jobPositionService.searchJobsByDepartment(department);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/search/location")
    public ResponseEntity<List<JobPositionDTO>> searchByLocation(@RequestParam String location) {
        List<JobPositionDTO> jobs = jobPositionService.searchJobsByLocation(location);
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobPositionDTO> updateJobPosition(
            @PathVariable Long id,
            @RequestBody JobPositionDTO jobPositionDTO) {
        JobPositionDTO updatedJob = jobPositionService.updateJobPosition(id, jobPositionDTO);
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPosition(@PathVariable Long id) {
        jobPositionService.deleteJobPosition(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
