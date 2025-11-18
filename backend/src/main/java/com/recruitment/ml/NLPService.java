package com.recruitment.ml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NLPService {

    @Autowired(required = false)
    private OpenAIService openAIService;

    // Common programming skills dictionary
    private static final Set<String> PROGRAMMING_SKILLS = Set.of(
        "java", "python", "javascript", "typescript", "go", "rust", "kotlin",
        "c++", "c#", "ruby", "php", "swift", "scala", "r", "matlab"
    );

    private static final Set<String> FRAMEWORK_SKILLS = Set.of(
        "spring boot", "spring", "react", "angular", "vue", "django", "flask",
        "express", "fastapi", "asp.net", "laravel", "rails", "hibernate"
    );

    private static final Set<String> DATABASE_SKILLS = Set.of(
        "postgresql", "mysql", "mongodb", "cassandra", "redis", "elasticsearch",
        "oracle", "sql server", "dynamodb", "firestore", "neo4j"
    );

    private static final Set<String> CLOUD_SKILLS = Set.of(
        "aws", "azure", "gcp", "kubernetes", "docker", "openshift", "heroku",
        "jenkins", "gitlab ci", "github actions", "travis ci"
    );

    /**
     * Extract skills from resume text using AI or pattern matching
     * Uses OpenAI if available, falls back to pattern matching
     */
    public Set<String> extractSkills(String resumeText) {
        // Try OpenAI first if available
        if (openAIService != null && openAIService.isAvailable()) {
            Set<String> aiSkills = openAIService.extractSkillsWithAI(resumeText);
            if (!aiSkills.isEmpty()) {
                return aiSkills;
            }
        }

        // Fallback to pattern matching
        return extractSkillsBasic(resumeText);
    }

    /**
     * Extract skills using basic pattern matching (fallback method)
     */
    private Set<String> extractSkillsBasic(String resumeText) {
        Set<String> extractedSkills = new HashSet<>();
        String lowerText = resumeText.toLowerCase();

        for (String skill : PROGRAMMING_SKILLS) {
            if (lowerText.contains(skill)) {
                extractedSkills.add(skill);
            }
        }

        for (String skill : FRAMEWORK_SKILLS) {
            if (lowerText.contains(skill)) {
                extractedSkills.add(skill);
            }
        }

        for (String skill : DATABASE_SKILLS) {
            if (lowerText.contains(skill)) {
                extractedSkills.add(skill);
            }
        }

        for (String skill : CLOUD_SKILLS) {
            if (lowerText.contains(skill)) {
                extractedSkills.add(skill);
            }
        }

        return extractedSkills;
    }

    /**
     * Extract email from text
     */
    public String extractEmail(String text) {
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Matcher matcher = emailPattern.matcher(text);
        return matcher.find() ? matcher.group() : null;
    }

    /**
     * Extract phone numbers from text
     */
    public String extractPhoneNumber(String text) {
        Pattern phonePattern = Pattern.compile("\\+?1?\\d{9,15}");
        Matcher matcher = phonePattern.matcher(text);
        return matcher.find() ? matcher.group() : null;
    }

    /**
     * Perform sentiment analysis on text using AI or basic pattern matching
     * Returns a score between 0 (negative) and 1 (positive)
     */
    public double analyzeSentiment(String text) {
        if (text == null || text.isEmpty()) {
            return 0.5;
        }

        // Try OpenAI first if available
        if (openAIService != null && openAIService.isAvailable()) {
            Map<String, Object> aiSentiment = openAIService.analyzeSentimentWithAI(text);
            if (aiSentiment.containsKey("score")) {
                return (double) aiSentiment.get("score");
            }
        }

        // Fallback to basic sentiment analysis
        return analyzeSentimentBasic(text);
    }

    /**
     * Perform basic sentiment analysis using keyword matching
     */
    private double analyzeSentimentBasic(String text) {
        String lowerText = text.toLowerCase();

        // Positive words
        String[] positiveWords = {
            "excellent", "great", "good", "outstanding", "amazing", "fantastic",
            "wonderful", "strong", "skilled", "experienced", "proficient",
            "expertise", "leadership", "achievements", "success", "accomplished"
        };

        // Negative words
        String[] negativeWords = {
            "poor", "weak", "bad", "terrible", "awful", "failure", "failed",
            "lacking", "incompetent", "inexperienced", "deficient", "problems",
            "issues", "concerns", "challenges"
        };

        int positiveCount = 0;
        for (String word : positiveWords) {
            if (lowerText.contains(word)) {
                positiveCount++;
            }
        }

        int negativeCount = 0;
        for (String word : negativeWords) {
            if (lowerText.contains(word)) {
                negativeCount++;
            }
        }

        int totalCount = positiveCount + negativeCount;
        if (totalCount == 0) {
            return 0.5; // neutral sentiment
        }

        // Normalize to 0-1 range
        double rawScore = (double) (positiveCount - negativeCount) / totalCount;
        return (rawScore + 1.0) / 2.0; // Convert from [-1, 1] to [0, 1]
    }

    /**
     * Extract education details from resume
     */
    public String extractEducation(String resumeText) {
        Pattern educationPattern = Pattern.compile(
            "(Bachelor|Master|Ph\\.?D|Associate|Diploma|Certificate).*(?:in|of)\\s+([^\\n]*)",
            Pattern.CASE_INSENSITIVE
        );
        Matcher matcher = educationPattern.matcher(resumeText);

        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    /**
     * Extract years of experience from resume
     */
    public Integer extractYearsOfExperience(String resumeText) {
        Pattern experiencePattern = Pattern.compile("(\\d+)\\+?\\s+(?:year|yr)s?\\s+(?:of\\s+)?(?:experience|exp)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = experiencePattern.matcher(resumeText);

        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
