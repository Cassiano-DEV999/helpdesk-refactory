package com.mindflush.helpDesk.useCases.company;

import com.mindflush.helpDesk.dto.company.request.CompanyRequestDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.mapper.CompanyMapper;
import com.mindflush.helpDesk.repositories.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateCompanyUseCase {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public UpdateCompanyUseCase(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    @Transactional
    public Company execute(UUID companyId, CompanyRequestDTO dto) {
        Company companyToUpdate = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("ID da companhia não encontrado: " + companyId));

        if (companyRepository.findByCnpj(dto.getCnpj()).isPresent() && !companyToUpdate.getCnpj().equals(dto.getCnpj())) {
            throw new IllegalArgumentException("CNPJ já vinculado a uma Companhia cadastrada!");
        }

        if (companyRepository.findByEmail(dto.getEmail()).isPresent() && !companyToUpdate.getEmail().equals(dto.getEmail())) {
            throw new IllegalArgumentException("Email já vinculado a uma Companhia cadastrada!");
        }

        companyMapper.updateCompanyFromDto(dto, companyToUpdate);

        return companyRepository.save(companyToUpdate);
    }
}