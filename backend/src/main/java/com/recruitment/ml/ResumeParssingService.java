package com.recruitment.ml;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ResumeParssingService {

    @Autowired
    private NLPService nlpService;

    @Autowired(required = false)
    private OpenAIService openAIService;

    /**
     * Parse PDF resume and extract key information using AI-enhanced extraction
     */
    public Map<String, Object> parseResume(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String resumeText = stripper.getText(document);
            return parseTextResume(resumeText);
        }
    }

    /**
     * Parse text resume and extract key information using AI-enhanced extraction
     */
    public Map<String, Object> parseTextResume(String resumeText) {
        Map<String, Object> resumeData = new HashMap<>();
        resumeData.put("fullText", resumeText);

        // Try OpenAI enhanced extraction first if available
        if (openAIService != null && openAIService.isAvailable()) {
            Map<String, Object> aiExtractedData = openAIService.extractResumeData(resumeText);

            // Use AI-extracted data if available, fallback to NLP service
            resumeData.put("email", aiExtractedData.getOrDefault("email", nlpService.extractEmail(resumeText)));
            resumeData.put("phoneNumber", aiExtractedData.getOrDefault("phoneNumber", nlpService.extractPhoneNumber(resumeText)));
            resumeData.put("name", aiExtractedData.get("name"));
            resumeData.put("education", aiExtractedData.getOrDefault("education", nlpService.extractEducation(resumeText)));
            resumeData.put("yearsOfExperience", aiExtractedData.getOrDefault("yearsOfExperience", nlpService.extractYearsOfExperience(resumeText)));
            resumeData.put("skills", aiExtractedData.getOrDefault("skills", nlpService.extractSkills(resumeText)));
            resumeData.put("summary", aiExtractedData.get("summary"));
            resumeData.put("sentimentScore", nlpService.analyzeSentiment(resumeText));
        } else {
            // Fallback to basic NLP extraction
            resumeData.put("email", nlpService.extractEmail(resumeText));
            resumeData.put("phoneNumber", nlpService.extractPhoneNumber(resumeText));
            resumeData.put("education", nlpService.extractEducation(resumeText));
            resumeData.put("yearsOfExperience", nlpService.extractYearsOfExperience(resumeText));
            resumeData.put("skills", nlpService.extractSkills(resumeText));
            resumeData.put("sentimentScore", nlpService.analyzeSentiment(resumeText));
        }

        return resumeData;
    }
}
