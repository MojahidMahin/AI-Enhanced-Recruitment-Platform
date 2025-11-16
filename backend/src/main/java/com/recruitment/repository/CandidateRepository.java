package com.recruitment.repository;

import com.recruitment.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByEmail(String email);

    List<Candidate> findByFirstNameContainingIgnoreCase(String firstName);

    List<Candidate> findByLastNameContainingIgnoreCase(String lastName);

    @Query("SELECT c FROM Candidate c WHERE c.matchingScore >= :minScore ORDER BY c.matchingScore DESC")
    List<Candidate> findByMinMatchingScore(@Param("minScore") Double minScore);

    @Query("SELECT c FROM Candidate c JOIN c.skills s WHERE s.id = :skillId")
    List<Candidate> findCandidatesBySkill(@Param("skillId") Long skillId);

    @Query("SELECT c FROM Candidate c WHERE c.sentimentScore >= :sentimentThreshold ORDER BY c.sentimentScore DESC")
    List<Candidate> findBySentimentScore(@Param("sentimentThreshold") Double sentimentThreshold);
}
