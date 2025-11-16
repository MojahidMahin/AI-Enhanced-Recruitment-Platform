package com.recruitment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column
    private SkillCategory category;

    @Column
    private Integer proficiencyLevel; // 1-5 scale

    @ManyToMany(mappedBy = "skills")
    @Builder.Default
    private Set<Candidate> candidates = new HashSet<>();

    @ManyToMany(mappedBy = "requiredSkills")
    @Builder.Default
    private Set<JobPosition> jobPositions = new HashSet<>();
}

enum SkillCategory {
    PROGRAMMING, DATABASE, CLOUD, TOOLS, SOFT_SKILLS, FRAMEWORKS, METHODOLOGY
}
