package com.br.gurpo34.maosepatas.controller;

import com.br.gurpo34.maosepatas.dto.admin.AdministradorResponseDTO;
import com.br.gurpo34.maosepatas.dto.admin.AdminstradorUpdateDTO;
import com.br.gurpo34.maosepatas.dto.admin.AlterarSenhaRequestDTO;
import com.br.gurpo34.maosepatas.service.AdministradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/administradores")
@RequiredArgsConstructor
public class AdministradorController {

    private final AdministradorService administradorService;

    @GetMapping("/perfil")
    public ResponseEntity<AdministradorResponseDTO> buscarPerfil(Authentication authentication) {
        return ResponseEntity.ok(administradorService.buscarPerfilPorEmail(authentication.getName()));
    }

    @PutMapping("/perfil")
    public ResponseEntity<AdministradorResponseDTO> atualizarPerfil(
            Authentication authentication,
            @Valid @RequestBody AdminstradorUpdateDTO dto
    ) {
        return ResponseEntity.ok(administradorService.atualizarPerfil(authentication.getName(), dto));
    }

    @PatchMapping("/senha")
    public ResponseEntity<Void> alterarSenha(
            Authentication authentication,
            @Valid @RequestBody AlterarSenhaRequestDTO dto
    ) {
        administradorService.alterarSenha(authentication.getName(), dto);
        return ResponseEntity.noContent().build();
    }

}
