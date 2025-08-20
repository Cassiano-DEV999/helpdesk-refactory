package com.mindflush.helpDesk.useCases.company;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.CompanyRepository;
import com.mindflush.helpDesk.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.List;

@Service
public class DeleteCompanyUseCase {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public DeleteCompanyUseCase(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(UUID companyId) {
        Company companyToDelete = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("ID da companhia não encontrado: " + companyId));


        List<User> usersToDeactivate = userRepository.findAllByCompanyId(companyId);
        for (User user : usersToDeactivate) {
            user.setActive(false);
            userRepository.save(user);
        }
        companyToDelete.setActive(false);

        companyRepository.save(companyToDelete);
    }
}