package com.br.gurpo34.maosepatas.controller;

import com.br.gurpo34.maosepatas.dto.auth.LoginRequestDTO;
import com.br.gurpo34.maosepatas.dto.auth.RecuperarSenhaRequestDTO;
import com.br.gurpo34.maosepatas.dto.auth.RedefinirSenhaRequestDTO;
import com.br.gurpo34.maosepatas.dto.auth.TokenResponseDTO;
import com.br.gurpo34.maosepatas.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<Map<String, String>> recuperarSenha(@Valid @RequestBody RecuperarSenhaRequestDTO dto) {
        authService.solicitarRecuperacaoSenha(dto);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Código de verificação gerado com sucesso. Verifique o console ou e-mail cadastrado."
        ));
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<Map<String, String>> redefinirSenha(@Valid @RequestBody RedefinirSenhaRequestDTO dto) {
        authService.redefinirSenha(dto);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Senha redefinida com sucesso."
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.noContent().build();
    }
}
