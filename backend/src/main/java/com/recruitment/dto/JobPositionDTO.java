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
public class JobPositionDTO {

    private Long id;
    private String title;
    private String description;
    private String department;
    private String location;
    private Double salaryMin;
    private Double salaryMax;
    private String status;
    private Set<SkillDTO> requiredSkills;
    private Integer applicationCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
