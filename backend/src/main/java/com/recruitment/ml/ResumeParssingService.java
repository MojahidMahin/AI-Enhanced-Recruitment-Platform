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

    /**
     * Parse PDF resume and extract key information
     */
    public Map<String, Object> parseResume(String filePath) throws IOException {
        Map<String, Object> resumeData = new HashMap<>();

        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String resumeText = stripper.getText(document);

            // Extract key information
            resumeData.put("fullText", resumeText);
            resumeData.put("email", nlpService.extractEmail(resumeText));
            resumeData.put("phoneNumber", nlpService.extractPhoneNumber(resumeText));
            resumeData.put("education", nlpService.extractEducation(resumeText));
            resumeData.put("yearsOfExperience", nlpService.extractYearsOfExperience(resumeText));
            resumeData.put("skills", nlpService.extractSkills(resumeText));
            resumeData.put("sentimentScore", nlpService.analyzeSentiment(resumeText));
        }

        return resumeData;
    }

    /**
     * Parse text resume and extract key information
     */
    public Map<String, Object> parseTextResume(String resumeText) {
        Map<String, Object> resumeData = new HashMap<>();

        resumeData.put("fullText", resumeText);
        resumeData.put("email", nlpService.extractEmail(resumeText));
        resumeData.put("phoneNumber", nlpService.extractPhoneNumber(resumeText));
        resumeData.put("education", nlpService.extractEducation(resumeText));
        resumeData.put("yearsOfExperience", nlpService.extractYearsOfExperience(resumeText));
        resumeData.put("skills", nlpService.extractSkills(resumeText));
        resumeData.put("sentimentScore", nlpService.analyzeSentiment(resumeText));

        return resumeData;
    }
}
