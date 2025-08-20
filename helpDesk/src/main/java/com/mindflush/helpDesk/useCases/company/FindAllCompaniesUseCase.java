package com.mindflush.helpDesk.useCases.company;

import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.repositories.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindAllCompaniesUseCase {

    private final CompanyRepository companyRepository;

    public FindAllCompaniesUseCase(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional(readOnly = true)
    public Page<Company> execute(Boolean isActive, Pageable pageable) {
        if (isActive == null) {
            return companyRepository.findAll(pageable);
        } else if (isActive) {
            return companyRepository.findAllByIsActive(true, pageable);
        } else {
            return companyRepository.findAllByIsActive(false, pageable);
        }
    }
}