package com.mindflush.helpDesk.useCases.departament;

import com.mindflush.helpDesk.entities.models.Department;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FindDepartmentByIdUseCase {

    private final DepartmentRepository departmentRepository;

    public FindDepartmentByIdUseCase(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public Department execute(UUID departmentId, User authenticatedUser) {

        Department departmentFound = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        if (!departmentFound.getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to view this department.");
        }

        return departmentFound;
    }
}