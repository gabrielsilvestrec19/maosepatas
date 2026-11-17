package com.br.gurpo34.maosepatas.mapper;

import com.br.gurpo34.maosepatas.dto.admin.AdministradorResponseDTO;
import com.br.gurpo34.maosepatas.dto.admin.AdminstradorUpdateDTO;
import com.br.gurpo34.maosepatas.entity.Administrador;
import org.springframework.stereotype.Component;

@Component
public class AdministradorMapper {
    public AdministradorResponseDTO toResponseDTO(Administrador entity) {
        if (entity == null) {
            return null;
        }
        return new AdministradorResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail()
        );
    }

    public void updateEntityFromDTO(AdminstradorUpdateDTO dto, Administrador entity) {
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
    }
}
