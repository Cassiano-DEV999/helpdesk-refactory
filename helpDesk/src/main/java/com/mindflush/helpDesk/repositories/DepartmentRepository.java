package com.mindflush.helpDesk.repositories;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {


    Optional<Department> findByName(String name);

    List<Department> findByNameContainingIgnoreCase(String name);

    List<Department> findByCompany(Company company);

    Optional<Department> findByNameAndCompany(String name, Company company);
}