package com.mindflush.helpDesk.useCases.department;

import com.mindflush.helpDesk.dto.departament.request.DepartamentRequestDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Department;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateDepartmentUseCase {

    private final DepartmentRepository departmentRepository;

    public CreateDepartmentUseCase(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public Department execute(DepartamentRequestDTO dto, User authenticatedUser) {

        Company company = authenticatedUser.getCompany();

        if (departmentRepository.findByNameAndCompany(dto.getName(), company).isPresent()) {
            throw new IllegalArgumentException("Department with this name already exists in this company.");
        }

        Department newDepartment = new Department();
        newDepartment.setName(dto.getName());
        newDepartment.setCompany(company);

        return departmentRepository.save(newDepartment);
    }
}