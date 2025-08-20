package com.mindflush.helpDesk.useCases.company;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.repositories.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FindCompanyByIdUseCase {

    private final CompanyRepository companyRepository;

    public FindCompanyByIdUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional(readOnly = true)
    public Company execute(UUID companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Companhia não encontrada pelo ID: " + companyId));
    }
}