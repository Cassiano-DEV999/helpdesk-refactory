package com.mindflush.helpDesk.repositories;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.ProblemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProblemTypeRepository extends JpaRepository<ProblemType, UUID> {
    Optional<Object> findByNameAndCompany(String name, Company company);

    List<ProblemType> findByCompany(Company company);
}
