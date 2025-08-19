package com.mindflush.helpDesk.mapper;

import com.mindflush.helpDesk.dto.user.request.UserRequestDTO;
import com.mindflush.helpDesk.dto.user.response.UserResponseDTO;
import com.mindflush.helpDesk.entities.models.Company;
import com.mindflush.helpDesk.entities.models.Role;
import com.mindflush.helpDesk.entities.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Converte um DTO de requisição para uma nova entidade User.
     * <p>
     * Este método é utilizado para a criação de um novo usuário.
     * Ele recebe o DTO do cliente e as entidades de relacionamento (Company e Role),
     * que são obtidas e validadas pelo UseCase.
     * <p>
     * O mapeamento ignora campos gerenciados pelo sistema como a ID, a senha (que será
     * criptografada no UseCase) e os timestamps.
     *
     * @param dto O DTO de entrada com os dados do usuário.
     * @param company A entidade Company a ser vinculada.
     * @param role A entidade Role a ser vinculada.
     * @return Uma nova entidade User, pronta para ser salva.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "company", source = "company")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    User toUser(UserRequestDTO dto, Company company, Role role);

    /**
     * Converte uma entidade User completa para um DTO de resposta.
     * <p>
     * Este método é usado para enviar os dados do usuário para o cliente da API.
     * Ele mapeia os campos da entidade para o DTO e "achata" os relacionamentos,
     * expondo apenas os IDs e nomes das entidades relacionadas (Company e Role)
     * para uma resposta mais leve e segura.
     *
     * @param user A entidade User a ser convertida.
     * @return Um DTO de resposta (UserResponseDTO) para a API.
     */
    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "companyName", source = "company.name")
    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "roleName", source = "role.name")
    UserResponseDTO toUserResponseDTO(User user);

    /**
     * Converte uma lista de entidades User para uma lista de DTOs de resposta.
     *
     * @param users A lista de entidades User.
     * @return Uma lista de DTOs de resposta.
     */

    List<UserResponseDTO> toUserResponseDTOList(List<User> users);

    /**
     * Atualiza uma entidade User existente com base em um DTO de requisição.
     * <p>
     * Este método é usado para operações de atualização. Ele copia os dados do DTO
     * para a entidade alvo, ignorando campos de relacionamento e a ID
     * para evitar a alteração acidental de vínculos ou identificadores.
     * <p>
     * A senha também é ignorada neste mapeamento, pois a lógica de atualização da senha
     * deve ser tratada separadamente e com criptografia no UseCase.
     *
     * @param dto O DTO com os dados a serem atualizados.
     * @param user A entidade User alvo da atualização.
     */

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUserFromDto(UserRequestDTO dto, @MappingTarget User user);
}