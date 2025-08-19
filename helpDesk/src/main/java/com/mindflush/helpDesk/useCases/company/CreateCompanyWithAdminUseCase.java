package com.mindflush.helpDesk.useCases.company;

import com.mindflush.helpDesk.dto.company.request.CompanyRegistrationRequestDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Role;
import com.mindflush.helpDesk.entities.models.User;
import com.mindflush.helpDesk.repositories.CompanyRepository;
import com.mindflush.helpDesk.repositories.RoleRepository;
import com.mindflush.helpDesk.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCompanyWithAdminUseCase {

    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateCompanyWithAdminUseCase(
            CompanyRepository companyRepository,
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Company execute(CompanyRegistrationRequestDTO registrationDTO) {

        Company newCompany = new Company();
        newCompany.setName(registrationDTO.getCompanyData().getName());
        newCompany.setCnpj(registrationDTO.getCompanyData().getCnpj());
        newCompany.setEmail(registrationDTO.getCompanyData().getEmail());
        newCompany = companyRepository.save(newCompany);

        Role adminRole = new Role();
        adminRole.setName("Administrador");
        adminRole.setCompany(newCompany);
        adminRole = roleRepository.save(adminRole);

        User newAdminUser = new User();
        newAdminUser.setName(registrationDTO.getUserData().getName());
        newAdminUser.setEmail(registrationDTO.getUserData().getEmail());

        String encryptedPassword = passwordEncoder.encode(registrationDTO.getUserData().getPassword());
        newAdminUser.setPassword(encryptedPassword);

        newAdminUser.setCompany(newCompany);
        newAdminUser.setRole(adminRole);

        userRepository.save(newAdminUser);

        return newCompany;
    }
}