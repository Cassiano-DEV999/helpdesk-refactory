package com.mindflush.helpDesk.repositories;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Role;
import com.mindflush.helpDesk.entities.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmployeeId(String employeeId);

    List<User> findAllByIsActive(boolean isActive);

    List<User> findByNameContainingIgnoreCase(String name);

    List<User> findByCompany(Company company);

    List<User> findByRole(Role role);

    Page<User> findByCompanyAndIsActiveTrue(Company company, Pageable pageable);

    Page<User> findByRoleAndIsActiveTrue(Role role, Pageable pageable);


}