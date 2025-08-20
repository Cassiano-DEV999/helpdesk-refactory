package com.mindflush.helpDesk.repositories;

import com.mindflush.helpDesk.entities.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByCnpj(String cnpj);
    Optional<Company> findByName(String name);
    Optional<Company> findByEmail(String email);

    Page<Company> findAll(Pageable pageable);

    Page<Company> findAllByIsActive(boolean isActive, Pageable pageable);

    Page<Company> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Company> findByCnpjContaining(String cnpj, Pageable pageable);

    Page<Company> findByNameContainingIgnoreCaseAndIsActiveTrue(String name, Pageable pageable);

}