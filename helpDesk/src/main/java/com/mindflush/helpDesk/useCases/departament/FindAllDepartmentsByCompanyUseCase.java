package com.mindflush.helpDesk.useCases.departament;

import com.mindflush.helpDesk.entities.models.Department;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindAllDepartmentsByCompanyUseCase {

    private final DepartmentRepository departmentRepository;

    public FindAllDepartmentsByCompanyUseCase(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<Department> execute(User authenticatedUser) {

        return departmentRepository.findByCompany(authenticatedUser.getCompany());
    }
}