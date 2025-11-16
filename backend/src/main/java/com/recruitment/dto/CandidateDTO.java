package com.recruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String summary;
    private String resumeFilePath;
    private Double sentimentScore;
    private Double matchingScore;
    private String status;
    private Set<SkillDTO> skills;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
