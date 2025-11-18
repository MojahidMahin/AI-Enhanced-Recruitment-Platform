package com.recruitment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url("http://localhost:8080" + contextPath).description("Development Server"));
        servers.add(new Server().url("https://api.recruitment-platform.com" + contextPath).description("Production Server"));

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag().name("Candidates").description("Candidate management and ML-powered ranking endpoints"));
        tags.add(new Tag().name("Job Positions").description("Job position management endpoints"));
        tags.add(new Tag().name("Interviews").description("AI-powered interview scheduling and management"));
        tags.add(new Tag().name("Assessments").description("Candidate assessment endpoints"));
        tags.add(new Tag().name("Applications").description("Job application tracking endpoints"));
        tags.add(new Tag().name("AI/ML").description("Artificial Intelligence and Machine Learning features"));

        return new OpenAPI()
                .info(new Info()
                        .title("AI-Enhanced Recruitment Platform API")
                        .description("""
                                # AI-Enhanced Recruitment Platform

                                A comprehensive recruitment management system powered by AI and Machine Learning.

                                ## Key Features:
                                - **Resume Parsing**: AI-powered resume parsing with NLP (OpenAI GPT integration)
                                - **Skill Matching**: Advanced algorithms to match candidates with job requirements
                                - **ML Ranking**: Machine learning-based candidate ranking with feature engineering
                                - **Interview Scheduling**: AI-optimized interview time slot suggestions
                                - **Sentiment Analysis**: Professionalism scoring for candidates
                                - **Job Fit Analysis**: AI-powered candidate-job compatibility scoring

                                ## Technologies:
                                - Spring Boot 3.1.5
                                - PostgreSQL Database
                                - OpenAI GPT API
                                - Apache Commons Math (ML)
                                - Stanford CoreNLP
                                - Apache PDFBox

                                ## Authentication:
                                Most endpoints require JWT authentication (coming soon).

                                For more information, visit the [GitHub Repository](https://github.com/your-org/ai-recruitment-platform)
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("AI Recruitment Platform Team")
                                .email("support@recruitment-platform.com")
                                .url("https://recruitment-platform.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(servers)
                .tags(tags);
    }
}
