package com.mindflush.helpDesk.useCases.departament;

import com.mindflush.helpDesk.entities.models.Department;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.DepartmentRepository;
import com.mindflush.helpDesk.repositories.TicketRepository;
import com.mindflush.helpDesk.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeleteDepartmentUseCase {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    public DeleteDepartmentUseCase(
            DepartmentRepository departmentRepository,
            UserRepository userRepository,
            TicketRepository ticketRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public void execute(UUID departmentId, User authenticatedUser) {

        // 1. Encontrar o departamento existente
        Department departmentToDelete = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + departmentId));

        // 2. Regra de Negócio: O administrador só pode desativar departamentos de sua própria empresa
        if (!departmentToDelete.getCompany().equals(authenticatedUser.getCompany())) {
            throw new IllegalArgumentException("You do not have permission to delete this department.");
        }

        // 3. Regra de Negócio: Não pode deletar se houver usuários ou tickets vinculados
        if (!userRepository.findByDepartment(departmentToDelete).isEmpty()) {
            throw new IllegalStateException("Cannot delete a department with active users.");
        }
        if (!ticketRepository.findAllByDepartment(departmentToDelete).isEmpty()) {
            throw new IllegalStateException("Cannot delete a department with existing tickets.");
        }

        // 4. Desativar o departamento (soft delete)
        departmentToDelete.setActive(false);
        departmentRepository.save(departmentToDelete);
    }
}