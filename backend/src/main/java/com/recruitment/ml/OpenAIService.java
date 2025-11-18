package com.recruitment.ml;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.model:gpt-3.5-turbo}")
    private String model;

    private OpenAiService openAiService;

    @PostConstruct
    public void init() {
        if (apiKey != null && !apiKey.isEmpty() && !apiKey.equals("your-openai-api-key")) {
            this.openAiService = new OpenAiService(apiKey, Duration.ofSeconds(60));
        }
    }

    /**
     * Extract skills from resume using OpenAI GPT
     */
    public Set<String> extractSkillsWithAI(String resumeText) {
        if (openAiService == null || resumeText == null || resumeText.isEmpty()) {
            return new HashSet<>();
        }

        try {
            String prompt = """
                Extract all technical and professional skills from the following resume.
                Return only a comma-separated list of skills without any explanation or numbering.
                Focus on: programming languages, frameworks, databases, cloud technologies, tools, methodologies, and soft skills.

                Resume:
                %s
                """.formatted(resumeText);

            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(),
                "You are an expert technical recruiter skilled at extracting skills from resumes."));
            messages.add(new ChatMessage(ChatMessageRole.USER.value(), prompt));

            ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(messages)
                    .maxTokens(500)
                    .temperature(0.3)
                    .build();

            String response = openAiService.createChatCompletion(chatRequest)
                    .getChoices().get(0).getMessage().getContent();

            // Parse comma-separated skills
            return Arrays.stream(response.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());

        } catch (Exception e) {
            System.err.println("Error calling OpenAI API: " + e.getMessage());
            return new HashSet<>();
        }
    }

    /**
     * Enhanced sentiment analysis using OpenAI
     */
    public Map<String, Object> analyzeSentimentWithAI(String text) {
        if (openAiService == null || text == null || text.isEmpty()) {
            return Map.of("score", 0.5, "label", "neutral");
        }

        try {
            String prompt = """
                Analyze the sentiment and professionalism of the following resume/text.
                Return your response in this exact format:
                SCORE: [number between 0 and 1]
                LABEL: [positive/neutral/negative]
                REASONING: [brief explanation]

                Text:
                %s
                """.formatted(text);

            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(),
                "You are an expert at analyzing text sentiment and professionalism."));
            messages.add(new ChatMessage(ChatMessageRole.USER.value(), prompt));

            ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(messages)
                    .maxTokens(200)
                    .temperature(0.3)
                    .build();

            String response = openAiService.createChatCompletion(chatRequest)
                    .getChoices().get(0).getMessage().getContent();

            return parseSentimentResponse(response);

        } catch (Exception e) {
            System.err.println("Error calling OpenAI API: " + e.getMessage());
            return Map.of("score", 0.5, "label", "neutral");
        }
    }

    /**
     * Extract structured information from resume using OpenAI
     */
    public Map<String, Object> extractResumeData(String resumeText) {
        if (openAiService == null || resumeText == null || resumeText.isEmpty()) {
            return new HashMap<>();
        }

        try {
            String prompt = """
                Extract the following information from the resume and return it in the specified format:

                EMAIL: [email address]
                PHONE: [phone number]
                NAME: [full name]
                EDUCATION: [highest degree and field]
                EXPERIENCE_YEARS: [number of years]
                SKILLS: [comma-separated list]
                SUMMARY: [brief professional summary in 2-3 sentences]

                Resume:
                %s
                """.formatted(resumeText);

            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(),
                "You are an expert at parsing and extracting information from resumes."));
            messages.add(new ChatMessage(ChatMessageRole.USER.value(), prompt));

            ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(messages)
                    .maxTokens(800)
                    .temperature(0.2)
                    .build();

            String response = openAiService.createChatCompletion(chatRequest)
                    .getChoices().get(0).getMessage().getContent();

            return parseResumeDataResponse(response);

        } catch (Exception e) {
            System.err.println("Error calling OpenAI API: " + e.getMessage());
            return new HashMap<>();
        }
    }

    /**
     * Generate interview questions based on job requirements and candidate profile
     */
    public List<String> generateInterviewQuestions(String jobDescription, String candidateResume) {
        if (openAiService == null) {
            return new ArrayList<>();
        }

        try {
            String prompt = """
                Generate 10 relevant interview questions based on the job description and candidate's resume.
                Return only the questions, one per line, numbered 1-10.

                Job Description:
                %s

                Candidate Resume:
                %s
                """.formatted(jobDescription, candidateResume);

            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(),
                "You are an expert technical interviewer."));
            messages.add(new ChatMessage(ChatMessageRole.USER.value(), prompt));

            ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(messages)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            String response = openAiService.createChatCompletion(chatRequest)
                    .getChoices().get(0).getMessage().getContent();

            return parseQuestions(response);

        } catch (Exception e) {
            System.err.println("Error calling OpenAI API: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Calculate candidate-job fit score using AI
     */
    public Map<String, Object> calculateJobFitScore(String jobDescription, String candidateResume) {
        if (openAiService == null) {
            return Map.of("score", 0.0, "reasoning", "AI service not available");
        }

        try {
            String prompt = """
                Analyze how well this candidate fits the job position.
                Return your response in this exact format:
                SCORE: [number between 0 and 100]
                FIT_LEVEL: [poor/fair/good/excellent]
                STRENGTHS: [2-3 key strengths]
                GAPS: [2-3 skill gaps]
                RECOMMENDATION: [hire/interview/reject with brief reasoning]

                Job Description:
                %s

                Candidate Resume:
                %s
                """.formatted(jobDescription, candidateResume);

            List<ChatMessage> messages = new ArrayList<>();
            messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(),
                "You are an expert technical recruiter evaluating candidate-job fit."));
            messages.add(new ChatMessage(ChatMessageRole.USER.value(), prompt));

            ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
                    .model(model)
                    .messages(messages)
                    .maxTokens(500)
                    .temperature(0.4)
                    .build();

            String response = openAiService.createChatCompletion(chatRequest)
                    .getChoices().get(0).getMessage().getContent();

            return parseJobFitResponse(response);

        } catch (Exception e) {
            System.err.println("Error calling OpenAI API: " + e.getMessage());
            return Map.of("score", 0.0, "reasoning", "Error: " + e.getMessage());
        }
    }

    // Helper methods to parse responses

    private Map<String, Object> parseSentimentResponse(String response) {
        Map<String, Object> result = new HashMap<>();
        result.put("score", 0.5);
        result.put("label", "neutral");

        try {
            String[] lines = response.split("\n");
            for (String line : lines) {
                if (line.startsWith("SCORE:")) {
                    double score = Double.parseDouble(line.substring(6).trim());
                    result.put("score", score);
                } else if (line.startsWith("LABEL:")) {
                    result.put("label", line.substring(6).trim());
                } else if (line.startsWith("REASONING:")) {
                    result.put("reasoning", line.substring(10).trim());
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing sentiment response: " + e.getMessage());
        }

        return result;
    }

    private Map<String, Object> parseResumeDataResponse(String response) {
        Map<String, Object> data = new HashMap<>();

        try {
            String[] lines = response.split("\n");
            for (String line : lines) {
                if (line.startsWith("EMAIL:")) {
                    data.put("email", line.substring(6).trim());
                } else if (line.startsWith("PHONE:")) {
                    data.put("phoneNumber", line.substring(6).trim());
                } else if (line.startsWith("NAME:")) {
                    data.put("name", line.substring(5).trim());
                } else if (line.startsWith("EDUCATION:")) {
                    data.put("education", line.substring(10).trim());
                } else if (line.startsWith("EXPERIENCE_YEARS:")) {
                    String yearsStr = line.substring(17).trim().replaceAll("[^0-9]", "");
                    if (!yearsStr.isEmpty()) {
                        data.put("yearsOfExperience", Integer.parseInt(yearsStr));
                    }
                } else if (line.startsWith("SKILLS:")) {
                    String skillsStr = line.substring(7).trim();
                    Set<String> skills = Arrays.stream(skillsStr.split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toSet());
                    data.put("skills", skills);
                } else if (line.startsWith("SUMMARY:")) {
                    data.put("summary", line.substring(8).trim());
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing resume data response: " + e.getMessage());
        }

        return data;
    }

    private List<String> parseQuestions(String response) {
        List<String> questions = new ArrayList<>();
        String[] lines = response.split("\n");

        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty()) {
                // Remove numbering like "1.", "2)", etc.
                line = line.replaceFirst("^\\d+[.)\\s]+", "");
                if (!line.isEmpty()) {
                    questions.add(line);
                }
            }
        }

        return questions;
    }

    private Map<String, Object> parseJobFitResponse(String response) {
        Map<String, Object> result = new HashMap<>();
        result.put("score", 0.0);

        try {
            String[] lines = response.split("\n");
            for (String line : lines) {
                if (line.startsWith("SCORE:")) {
                    String scoreStr = line.substring(6).trim().replaceAll("[^0-9.]", "");
                    if (!scoreStr.isEmpty()) {
                        result.put("score", Double.parseDouble(scoreStr));
                    }
                } else if (line.startsWith("FIT_LEVEL:")) {
                    result.put("fitLevel", line.substring(10).trim());
                } else if (line.startsWith("STRENGTHS:")) {
                    result.put("strengths", line.substring(10).trim());
                } else if (line.startsWith("GAPS:")) {
                    result.put("gaps", line.substring(5).trim());
                } else if (line.startsWith("RECOMMENDATION:")) {
                    result.put("recommendation", line.substring(15).trim());
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing job fit response: " + e.getMessage());
        }

        return result;
    }

    public boolean isAvailable() {
        return openAiService != null;
    }
}
