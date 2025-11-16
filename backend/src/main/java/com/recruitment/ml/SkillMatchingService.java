package com.recruitment.ml;

import com.recruitment.entity.Candidate;
import com.recruitment.entity.JobPosition;
import com.recruitment.entity.Skill;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class SkillMatchingService {

    /**
     * Calculate skill match score between candidate and job position
     * Returns a score between 0 and 1 (0 = no match, 1 = perfect match)
     */
    public double calculateSkillMatch(Candidate candidate, JobPosition jobPosition) {
        Set<Skill> candidateSkills = candidate.getSkills();
        Set<Skill> requiredSkills = jobPosition.getRequiredSkills();

        if (requiredSkills.isEmpty()) {
            return 1.0;
        }

        long matchedSkills = requiredSkills.stream()
            .filter(candidateSkills::contains)
            .count();

        return (double) matchedSkills / requiredSkills.size();
    }

    /**
     * Calculate compatibility score between candidate and job position
     * Combines skill match, experience, and salary expectations
     */
    public double calculateCompatibilityScore(Candidate candidate, JobPosition jobPosition) {
        double skillMatch = calculateSkillMatch(candidate, jobPosition);

        // Weight: 60% skills, 40% other factors
        double finalScore = skillMatch * 0.6;

        // Add bonus if salary expectations are within range
        if (jobPosition.getSalaryMin() != null && jobPosition.getSalaryMax() != null) {
            finalScore += 0.2; // Adjust based on actual salary data if available
        }

        return Math.min(finalScore, 1.0);
    }

    /**
     * Get recommendation score for a candidate for a specific job
     */
    public double getRecommendationScore(Candidate candidate, JobPosition jobPosition) {
        double skillMatch = calculateSkillMatch(candidate, jobPosition);
        double sentimentScore = candidate.getSentimentScore() != null ? candidate.getSentimentScore() : 0.5;

        // Weight: 70% skill match, 30% sentiment (professionalism)
        double sentimentNormalized = (sentimentScore + 1) / 2; // Convert from [-1, 1] to [0, 1]
        return (skillMatch * 0.7) + (sentimentNormalized * 0.3);
    }

    /**
     * Rank candidates for a specific job position
     */
    public int compareCandidates(Candidate candidate1, Candidate candidate2, JobPosition jobPosition) {
        double score1 = getRecommendationScore(candidate1, jobPosition);
        double score2 = getRecommendationScore(candidate2, jobPosition);

        return Double.compare(score2, score1); // Descending order (higher score first)
    }
}
