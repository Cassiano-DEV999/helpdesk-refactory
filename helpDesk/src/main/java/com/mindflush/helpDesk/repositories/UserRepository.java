package com.mindflush.helpDesk.repositories;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Department;
import com.mindflush.helpDesk.entities.models.Role;
import com.mindflush.helpDesk.entities.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmployeeId(String employeeId);

    List<User> findAllByIsActive(boolean isActive);

    List<User> findByNameContainingIgnoreCase(String name);

    List<User> findByCompany(Company company);

    List<User> findByRole(Role role);

    Page<User> findByCompanyAndIsActiveTrue(Company company, Pageable pageable);

    Page<User> findByRoleAndIsActiveTrue(Role role, Pageable pageable);
    List<User> findAllByCompanyId(UUID companyId);

    Collection<Object> findByDepartment(Department departmentToDelete);
}