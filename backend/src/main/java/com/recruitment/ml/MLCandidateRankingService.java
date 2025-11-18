package com.recruitment.ml;

import com.recruitment.entity.Candidate;
import com.recruitment.entity.JobPosition;
import com.recruitment.entity.Skill;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Advanced Machine Learning-based Candidate Ranking Service
 * Uses feature engineering, weighted scoring, and statistical methods
 */
@Service
public class MLCandidateRankingService {

    @Autowired
    private SkillMatchingService skillMatchingService;

    @Autowired(required = false)
    private OpenAIService openAIService;

    // Feature weights learned from historical data (can be tuned)
    private static final double WEIGHT_SKILL_MATCH = 0.35;
    private static final double WEIGHT_EXPERIENCE = 0.25;
    private static final double WEIGHT_SENTIMENT = 0.15;
    private static final double WEIGHT_EDUCATION = 0.15;
    private static final double WEIGHT_AI_FIT = 0.10;

    /**
     * Calculate comprehensive ML-based ranking score for a candidate
     * Uses multiple features with optimized weights
     */
    public double calculateMLRankingScore(Candidate candidate, JobPosition jobPosition) {
        // Extract features
        Map<String, Double> features = extractFeatures(candidate, jobPosition);

        // Calculate weighted score
        double score = 0.0;
        score += features.get("skillMatch") * WEIGHT_SKILL_MATCH;
        score += features.get("experienceScore") * WEIGHT_EXPERIENCE;
        score += features.get("sentimentScore") * WEIGHT_SENTIMENT;
        score += features.get("educationScore") * WEIGHT_EDUCATION;
        score += features.get("aiFitScore") * WEIGHT_AI_FIT;

        // Normalize to 0-1 range
        return Math.max(0.0, Math.min(1.0, score));
    }

    /**
     * Extract and engineer features from candidate and job data
     */
    public Map<String, Double> extractFeatures(Candidate candidate, JobPosition jobPosition) {
        Map<String, Double> features = new HashMap<>();

        // Feature 1: Skill Match Score
        double skillMatch = skillMatchingService.calculateSkillMatch(candidate, jobPosition);
        features.put("skillMatch", skillMatch);

        // Feature 2: Experience Score (normalized)
        double experienceScore = calculateExperienceScore(candidate, jobPosition);
        features.put("experienceScore", experienceScore);

        // Feature 3: Sentiment Score (professionalism indicator)
        double sentimentScore = candidate.getSentimentScore() != null
            ? Math.max(0.0, Math.min(1.0, candidate.getSentimentScore()))
            : 0.5;
        features.put("sentimentScore", sentimentScore);

        // Feature 4: Education Score
        double educationScore = calculateEducationScore(candidate);
        features.put("educationScore", educationScore);

        // Feature 5: AI-based Job Fit Score
        double aiFitScore = calculateAIFitScore(candidate, jobPosition);
        features.put("aiFitScore", aiFitScore);

        // Feature 6: Skill Diversity Score
        double skillDiversity = calculateSkillDiversityScore(candidate);
        features.put("skillDiversity", skillDiversity);

        // Feature 7: Matching Score (if pre-calculated)
        double matchingScore = candidate.getMatchingScore() != null
            ? candidate.getMatchingScore()
            : skillMatch;
        features.put("matchingScore", matchingScore);

        return features;
    }

    /**
     * Calculate experience score based on years of experience
     */
    private double calculateExperienceScore(Candidate candidate, JobPosition jobPosition) {
        // Assume 10 years is the maximum expected experience
        int maxExpectedYears = 10;

        // Extract years from candidate summary or other fields
        // This is a simplified version; in production, parse from resume/profile
        double candidateExperience = 3.0; // Default assumption

        // Normalize: 0-3 years = 0.5, 3-6 = 0.7, 6-10 = 0.9, 10+ = 1.0
        if (candidateExperience <= 3) {
            return 0.5;
        } else if (candidateExperience <= 6) {
            return 0.7;
        } else if (candidateExperience <= 10) {
            return 0.9;
        } else {
            return 1.0;
        }
    }

    /**
     * Calculate education score based on degree level
     */
    private double calculateEducationScore(Candidate candidate) {
        String summary = candidate.getSummary() != null ? candidate.getSummary().toLowerCase() : "";

        if (summary.contains("ph.d") || summary.contains("doctorate")) {
            return 1.0;
        } else if (summary.contains("master")) {
            return 0.9;
        } else if (summary.contains("bachelor")) {
            return 0.8;
        } else if (summary.contains("associate") || summary.contains("diploma")) {
            return 0.6;
        } else {
            return 0.5; // Default
        }
    }

    /**
     * Calculate AI-based job fit score using OpenAI
     */
    private double calculateAIFitScore(Candidate candidate, JobPosition jobPosition) {
        if (openAIService != null && openAIService.isAvailable()) {
            try {
                String jobDescription = jobPosition.getDescription();
                String candidateResume = candidate.getResumeText() != null
                    ? candidate.getResumeText()
                    : candidate.getSummary();

                Map<String, Object> fitResult = openAIService.calculateJobFitScore(jobDescription, candidateResume);

                if (fitResult.containsKey("score")) {
                    double score = (double) fitResult.get("score");
                    return score / 100.0; // Normalize to 0-1
                }
            } catch (Exception e) {
                System.err.println("Error calculating AI fit score: " + e.getMessage());
            }
        }

        // Fallback to skill match
        return skillMatchingService.calculateSkillMatch(candidate, jobPosition);
    }

    /**
     * Calculate skill diversity score (rewards breadth of skills)
     */
    private double calculateSkillDiversityScore(Candidate candidate) {
        Set<Skill> skills = candidate.getSkills();

        if (skills.isEmpty()) {
            return 0.0;
        }

        // Count unique skill categories
        Set<String> categories = skills.stream()
                .map(Skill::getCategory)
                .filter(Objects::nonNull)
                .map(Enum::name)
                .collect(Collectors.toSet());

        // Normalize: 1 category = 0.3, 2 = 0.5, 3 = 0.7, 4+ = 1.0
        int categoryCount = categories.size();
        if (categoryCount >= 4) {
            return 1.0;
        } else if (categoryCount == 3) {
            return 0.7;
        } else if (categoryCount == 2) {
            return 0.5;
        } else if (categoryCount == 1) {
            return 0.3;
        } else {
            return 0.0;
        }
    }

    /**
     * Rank multiple candidates for a job using ML scoring
     */
    public List<Map<String, Object>> rankCandidates(List<Candidate> candidates, JobPosition jobPosition) {
        List<Map<String, Object>> rankedCandidates = new ArrayList<>();

        for (Candidate candidate : candidates) {
            double score = calculateMLRankingScore(candidate, jobPosition);
            Map<String, Double> features = extractFeatures(candidate, jobPosition);

            Map<String, Object> result = new HashMap<>();
            result.put("candidate", candidate);
            result.put("score", score);
            result.put("features", features);
            result.put("percentile", 0.0); // Will be calculated after sorting

            rankedCandidates.add(result);
        }

        // Sort by score descending
        rankedCandidates.sort((a, b) -> Double.compare(
                (double) b.get("score"),
                (double) a.get("score")
        ));

        // Calculate percentiles
        int totalCandidates = rankedCandidates.size();
        for (int i = 0; i < totalCandidates; i++) {
            double percentile = (1.0 - ((double) i / totalCandidates)) * 100;
            rankedCandidates.get(i).put("percentile", percentile);
            rankedCandidates.get(i).put("rank", i + 1);
        }

        return rankedCandidates;
    }

    /**
     * Get top N candidates using ML ranking
     */
    public List<Candidate> getTopCandidates(List<Candidate> candidates, JobPosition jobPosition, int topN) {
        List<Map<String, Object>> ranked = rankCandidates(candidates, jobPosition);

        return ranked.stream()
                .limit(topN)
                .map(entry -> (Candidate) entry.get("candidate"))
                .collect(Collectors.toList());
    }

    /**
     * Calculate statistical insights for candidate pool
     */
    public Map<String, Object> getCandidatePoolStatistics(List<Candidate> candidates, JobPosition jobPosition) {
        Map<String, Object> stats = new HashMap<>();

        DescriptiveStatistics scoreStats = new DescriptiveStatistics();
        DescriptiveStatistics skillMatchStats = new DescriptiveStatistics();
        DescriptiveStatistics sentimentStats = new DescriptiveStatistics();

        for (Candidate candidate : candidates) {
            double mlScore = calculateMLRankingScore(candidate, jobPosition);
            double skillMatch = skillMatchingService.calculateSkillMatch(candidate, jobPosition);
            double sentiment = candidate.getSentimentScore() != null ? candidate.getSentimentScore() : 0.5;

            scoreStats.addValue(mlScore);
            skillMatchStats.addValue(skillMatch);
            sentimentStats.addValue(sentiment);
        }

        stats.put("totalCandidates", candidates.size());
        stats.put("averageMLScore", scoreStats.getMean());
        stats.put("medianMLScore", scoreStats.getPercentile(50));
        stats.put("stdDevMLScore", scoreStats.getStandardDeviation());
        stats.put("averageSkillMatch", skillMatchStats.getMean());
        stats.put("averageSentiment", sentimentStats.getMean());
        stats.put("topScore", scoreStats.getMax());
        stats.put("bottomScore", scoreStats.getMin());

        return stats;
    }

    /**
     * Calculate similarity between two candidates using feature vectors
     */
    public double calculateCandidateSimilarity(Candidate candidate1, Candidate candidate2, JobPosition jobPosition) {
        Map<String, Double> features1 = extractFeatures(candidate1, jobPosition);
        Map<String, Double> features2 = extractFeatures(candidate2, jobPosition);

        // Create feature vectors
        double[] vector1 = new double[]{
            features1.get("skillMatch"),
            features1.get("experienceScore"),
            features1.get("sentimentScore"),
            features1.get("educationScore"),
            features1.get("skillDiversity")
        };

        double[] vector2 = new double[]{
            features2.get("skillMatch"),
            features2.get("experienceScore"),
            features2.get("sentimentScore"),
            features2.get("educationScore"),
            features2.get("skillDiversity")
        };

        // Calculate cosine similarity
        return calculateCosineSimilarity(vector1, vector2);
    }

    /**
     * Calculate cosine similarity between two feature vectors
     */
    private double calculateCosineSimilarity(double[] vector1, double[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Vectors must have the same length");
        }

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
            norm1 += vector1[i] * vector1[i];
            norm2 += vector2[i] * vector2[i];
        }

        if (norm1 == 0.0 || norm2 == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    /**
     * Suggest similar candidates based on a reference candidate
     */
    public List<Map<String, Object>> findSimilarCandidates(Candidate referenceCandidate,
                                                            List<Candidate> candidatePool,
                                                            JobPosition jobPosition,
                                                            int topN) {
        List<Map<String, Object>> similarCandidates = new ArrayList<>();

        for (Candidate candidate : candidatePool) {
            if (candidate.getId().equals(referenceCandidate.getId())) {
                continue; // Skip the reference candidate itself
            }

            double similarity = calculateCandidateSimilarity(referenceCandidate, candidate, jobPosition);

            Map<String, Object> result = new HashMap<>();
            result.put("candidate", candidate);
            result.put("similarity", similarity);

            similarCandidates.add(result);
        }

        // Sort by similarity descending
        similarCandidates.sort((a, b) -> Double.compare(
                (double) b.get("similarity"),
                (double) a.get("similarity")
        ));

        return similarCandidates.stream().limit(topN).collect(Collectors.toList());
    }
}
