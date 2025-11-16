package com.recruitment.service;

import com.recruitment.dto.JobPositionDTO;
import com.recruitment.entity.JobPosition;
import com.recruitment.repository.JobPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobPositionService {

    @Autowired
    private JobPositionRepository jobPositionRepository;

    public JobPositionDTO createJobPosition(JobPositionDTO jobPositionDTO) {
        JobPosition jobPosition = JobPosition.builder()
            .title(jobPositionDTO.getTitle())
            .description(jobPositionDTO.getDescription())
            .department(jobPositionDTO.getDepartment())
            .location(jobPositionDTO.getLocation())
            .salaryMin(jobPositionDTO.getSalaryMin())
            .salaryMax(jobPositionDTO.getSalaryMax())
            .build();

        JobPosition savedJobPosition = jobPositionRepository.save(jobPosition);
        return convertToDTO(savedJobPosition);
    }

    public JobPositionDTO getJobPositionById(Long id) {
        return jobPositionRepository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new RuntimeException("Job position not found"));
    }

    public List<JobPositionDTO> getAllJobPositions() {
        return jobPositionRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<JobPositionDTO> getOpenPositions() {
        return jobPositionRepository.findOpenPositions().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<JobPositionDTO> searchJobsByTitle(String title) {
        return jobPositionRepository.findByTitleContainingIgnoreCase(title).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<JobPositionDTO> searchJobsByDepartment(String department) {
        return jobPositionRepository.findByDepartment(department).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<JobPositionDTO> searchJobsByLocation(String location) {
        return jobPositionRepository.findByLocation(location).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public JobPositionDTO updateJobPosition(Long id, JobPositionDTO jobPositionDTO) {
        JobPosition jobPosition = jobPositionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Job position not found"));

        if (jobPositionDTO.getTitle() != null) {
            jobPosition.setTitle(jobPositionDTO.getTitle());
        }
        if (jobPositionDTO.getDescription() != null) {
            jobPosition.setDescription(jobPositionDTO.getDescription());
        }
        if (jobPositionDTO.getDepartment() != null) {
            jobPosition.setDepartment(jobPositionDTO.getDepartment());
        }
        if (jobPositionDTO.getLocation() != null) {
            jobPosition.setLocation(jobPositionDTO.getLocation());
        }
        if (jobPositionDTO.getSalaryMin() != null) {
            jobPosition.setSalaryMin(jobPositionDTO.getSalaryMin());
        }
        if (jobPositionDTO.getSalaryMax() != null) {
            jobPosition.setSalaryMax(jobPositionDTO.getSalaryMax());
        }

        JobPosition updatedJobPosition = jobPositionRepository.save(jobPosition);
        return convertToDTO(updatedJobPosition);
    }

    public void deleteJobPosition(Long id) {
        jobPositionRepository.deleteById(id);
    }

    private JobPositionDTO convertToDTO(JobPosition jobPosition) {
        return JobPositionDTO.builder()
            .id(jobPosition.getId())
            .title(jobPosition.getTitle())
            .description(jobPosition.getDescription())
            .department(jobPosition.getDepartment())
            .location(jobPosition.getLocation())
            .salaryMin(jobPosition.getSalaryMin())
            .salaryMax(jobPosition.getSalaryMax())
            .status(jobPosition.getStatus().toString())
            .applicationCount(jobPosition.getApplications().size())
            .createdAt(jobPosition.getCreatedAt())
            .updatedAt(jobPosition.getUpdatedAt())
            .build();
    }
}
