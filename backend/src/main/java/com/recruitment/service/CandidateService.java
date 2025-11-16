package com.recruitment.service;

import com.recruitment.dto.CandidateDTO;
import com.recruitment.entity.Candidate;
import com.recruitment.ml.NLPService;
import com.recruitment.ml.ResumeParssingService;
import com.recruitment.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ResumeParssingService resumeParssingService;

    @Autowired
    private NLPService nlpService;

    public CandidateDTO createCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = Candidate.builder()
            .firstName(candidateDTO.getFirstName())
            .lastName(candidateDTO.getLastName())
            .email(candidateDTO.getEmail())
            .phoneNumber(candidateDTO.getPhoneNumber())
            .summary(candidateDTO.getSummary())
            .build();

        Candidate savedCandidate = candidateRepository.save(candidate);
        return convertToDTO(savedCandidate);
    }

    public CandidateDTO getCandidateById(Long id) {
        return candidateRepository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new RuntimeException("Candidate not found"));
    }

    public CandidateDTO getCandidateByEmail(String email) {
        return candidateRepository.findByEmail(email)
            .map(this::convertToDTO)
            .orElseThrow(() -> new RuntimeException("Candidate not found"));
    }

    public List<CandidateDTO> getAllCandidates() {
        return candidateRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<CandidateDTO> searchCandidatesByName(String name) {
        List<Candidate> candidates = candidateRepository.findByFirstNameContainingIgnoreCase(name);
        return candidates.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<CandidateDTO> getTopCandidates(int limit) {
        return candidateRepository.findAll().stream()
            .sorted((c1, c2) -> Double.compare(
                c2.getMatchingScore() != null ? c2.getMatchingScore() : 0.0,
                c1.getMatchingScore() != null ? c1.getMatchingScore() : 0.0
            ))
            .limit(limit)
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public CandidateDTO updateCandidate(Long id, CandidateDTO candidateDTO) {
        Candidate candidate = candidateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Candidate not found"));

        if (candidateDTO.getFirstName() != null) {
            candidate.setFirstName(candidateDTO.getFirstName());
        }
        if (candidateDTO.getLastName() != null) {
            candidate.setLastName(candidateDTO.getLastName());
        }
        if (candidateDTO.getPhoneNumber() != null) {
            candidate.setPhoneNumber(candidateDTO.getPhoneNumber());
        }
        if (candidateDTO.getSummary() != null) {
            candidate.setSummary(candidateDTO.getSummary());
        }

        Candidate updatedCandidate = candidateRepository.save(candidate);
        return convertToDTO(updatedCandidate);
    }

    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

    public CandidateDTO parseAndProcessResume(Long candidateId, String resumeText) {
        Candidate candidate = candidateRepository.findById(candidateId)
            .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // Parse resume
        Map<String, Object> resumeData = resumeParssingService.parseTextResume(resumeText);

        // Update candidate with parsed data
        candidate.setResumeText(resumeText);

        if (resumeData.get("email") != null && candidate.getEmail() == null) {
            candidate.setEmail((String) resumeData.get("email"));
        }

        if (resumeData.get("sentimentScore") != null) {
            candidate.setSentimentScore((Double) resumeData.get("sentimentScore"));
        }

        Candidate savedCandidate = candidateRepository.save(candidate);
        return convertToDTO(savedCandidate);
    }

    private CandidateDTO convertToDTO(Candidate candidate) {
        return CandidateDTO.builder()
            .id(candidate.getId())
            .firstName(candidate.getFirstName())
            .lastName(candidate.getLastName())
            .email(candidate.getEmail())
            .phoneNumber(candidate.getPhoneNumber())
            .summary(candidate.getSummary())
            .resumeFilePath(candidate.getResumeFilePath())
            .sentimentScore(candidate.getSentimentScore())
            .matchingScore(candidate.getMatchingScore())
            .status(candidate.getStatus().toString())
            .createdAt(candidate.getCreatedAt())
            .updatedAt(candidate.getUpdatedAt())
            .build();
    }
}
