package com.br.gurpo34.maosepatas.service;

import com.br.gurpo34.maosepatas.dto.admin.AdministradorResponseDTO;
import com.br.gurpo34.maosepatas.dto.admin.AdminstradorUpdateDTO;
import com.br.gurpo34.maosepatas.dto.admin.AlterarSenhaRequestDTO;
import com.br.gurpo34.maosepatas.entity.Administrador;
import com.br.gurpo34.maosepatas.exception.BusinessRuleException;
import com.br.gurpo34.maosepatas.exception.ResourceNotFoundException;
import com.br.gurpo34.maosepatas.mapper.AdministradorMapper;
import com.br.gurpo34.maosepatas.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final AdministradorMapper administradorMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public AdministradorResponseDTO buscarPerfilPorEmail(String email) {
        Administrador admin = buscarEntidadePorEmail(email);
        return administradorMapper.toResponseDTO(admin);
    }

    @Transactional(readOnly = true)
    public AdministradorResponseDTO buscarPerfilPorId(Long id) {
        Administrador admin = buscarEntidadePorId(id);
        return administradorMapper.toResponseDTO(admin);
    }

    @Transactional
    public AdministradorResponseDTO atualizarPerfil(String emailAutenticado, AdminstradorUpdateDTO dto) {
        Administrador admin = buscarEntidadePorEmail(emailAutenticado);

        administradorRepository.findByEmail(dto.getEmail()).ifPresent(existente -> {
            if (!existente.getId().equals(admin.getId())) {
                throw new BusinessRuleException("O e-mail informado já está em uso por outro administrador");
            }
        });

        administradorMapper.updateEntityFromDTO(dto, admin);
        Administrador adminAtualizado = administradorRepository.save(admin);
        return administradorMapper.toResponseDTO(adminAtualizado);
    }

    @Transactional
    public void alterarSenha(String emailAutenticado, AlterarSenhaRequestDTO dto) {
        Administrador admin = buscarEntidadePorEmail(emailAutenticado);

        if (!passwordEncoder.matches(dto.getSenhaAtual(), admin.getSenha())) {
            throw new BusinessRuleException("A senha atual informada está incorreta");
        }

        admin.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        administradorRepository.save(admin);
    }

    public Administrador buscarEntidadePorId(Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado"));
    }

    public Administrador buscarEntidadePorEmail(String email) {
        return administradorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado"));
    }

}
