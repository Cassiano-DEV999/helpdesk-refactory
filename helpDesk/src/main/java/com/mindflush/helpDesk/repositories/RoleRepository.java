package com.mindflush.helpDesk.repositories;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    List<Role> findByCompany(Company company);
    Optional<Role> findByNameAndCompany(String name, Company company);
    List<Role> findByNameContainingIgnoreCaseAndCompany(String name, Company company);

}
