package com.recruitment.repository;

import com.recruitment.entity.Candidate;
import com.recruitment.entity.Interview;
import com.recruitment.entity.InterviewStatus;
import com.recruitment.entity.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByCandidateId(Long candidateId);

    List<Interview> findByJobPositionId(Long jobPositionId);

    List<Interview> findByCandidate(Candidate candidate);

    List<Interview> findByJobPosition(JobPosition jobPosition);

    List<Interview> findByStatusAndScheduledTimeAfter(InterviewStatus status, LocalDateTime time);

    @Query("SELECT i FROM Interview i WHERE i.scheduledTime BETWEEN :start AND :end")
    List<Interview> findInterviewsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    List<Interview> findByScheduledTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT i FROM Interview i WHERE i.status = 'SCHEDULED' ORDER BY i.scheduledTime ASC")
    List<Interview> findUpcomingInterviews();
}
