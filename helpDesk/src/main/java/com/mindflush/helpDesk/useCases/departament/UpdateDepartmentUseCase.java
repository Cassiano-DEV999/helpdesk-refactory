package com.mindflush.helpDesk.useCases.department;

import com.mindflush.helpDesk.dto.departament.request.DepartamentRequestDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Department;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.mapper.DepartamentMapper;
import com.mindflush.helpDesk.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateDepartmentUseCase {

    private final DepartmentRepository departmentRepository;
    private final DepartamentMapper departamentMapper;

    public UpdateDepartmentUseCase(DepartmentRepository departmentRepository, DepartamentMapper departamentMapper) {
        this.departmentRepository = departmentRepository;
        this.departamentMapper = departamentMapper;
    }

    @Transactional
    public Department execute(UUID departmentId, DepartamentRequestDTO dto, User authenticatedUser) {

        Department departmentToUpdate = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        Company company = authenticatedUser.getCompany();
        if (!departmentToUpdate.getCompany().equals(company)) {
            throw new IllegalArgumentException("You do not have permission to update this department.");
        }
        if (departmentRepository.findByNameAndCompany(dto.getName(), company).isPresent() &&
                !departmentToUpdate.getName().equals(dto.getName())) {
            throw new IllegalArgumentException("Department with this name already exists in your company.");
        }

        departamentMapper.updateDepartmentFromDto(dto, departmentToUpdate);

        return departmentRepository.save(departmentToUpdate);
    }
}