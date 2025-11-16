package com.recruitment.repository;

import com.recruitment.entity.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {

    List<JobPosition> findByTitleContainingIgnoreCase(String title);

    List<JobPosition> findByDepartment(String department);

    List<JobPosition> findByLocation(String location);

    @Query("SELECT j FROM JobPosition j WHERE j.status = 'OPEN' ORDER BY j.createdAt DESC")
    List<JobPosition> findOpenPositions();

    @Query("SELECT j FROM JobPosition j JOIN j.requiredSkills s WHERE s.id = :skillId")
    List<JobPosition> findByRequiredSkill(@Param("skillId") Long skillId);
}
