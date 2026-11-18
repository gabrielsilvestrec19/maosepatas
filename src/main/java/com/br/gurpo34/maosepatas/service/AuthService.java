package com.br.gurpo34.maosepatas.service;

import com.br.gurpo34.maosepatas.config.security.TokenService;
import com.br.gurpo34.maosepatas.dto.auth.LoginRequestDTO;
import com.br.gurpo34.maosepatas.dto.auth.RecuperarSenhaRequestDTO;
import com.br.gurpo34.maosepatas.dto.auth.RedefinirSenhaRequestDTO;
import com.br.gurpo34.maosepatas.dto.auth.TokenResponseDTO;
import com.br.gurpo34.maosepatas.entity.Administrador;
import com.br.gurpo34.maosepatas.exception.BusinessRuleException;
import com.br.gurpo34.maosepatas.exception.ResourceNotFoundException;
import com.br.gurpo34.maosepatas.exception.UnauthorizedException;
import com.br.gurpo34.maosepatas.mapper.AdministradorMapper;
import com.br.gurpo34.maosepatas.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AdministradorRepository administradorRepository;
    private final AdministradorMapper administradorMapper;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public TokenResponseDTO login(LoginRequestDTO dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
            );
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("E-mail ou senha inválidos");
        }

        Administrador admin = administradorRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("E-mail ou senha inválidos"));

        boolean lembrar = Boolean.TRUE.equals(dto.getLembrarDeMim());
        String token = tokenService.gerarToken(admin.getEmail(), lembrar);
        long expiracaoMs = tokenService.calcularExpiracaoMs(lembrar);

        return new TokenResponseDTO(
                token,
                "Bearer",
                expiracaoMs,
                administradorMapper.toResponseDTO(admin)
        );
    }

    @Transactional
    public void solicitarRecuperacaoSenha(RecuperarSenhaRequestDTO dto) {
        Administrador admin = administradorRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum administrador encontrado com o e-mail informado"));

        String codigo = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        admin.setCodigoRecuperacao(codigo);
        admin.setCodigoExpiracao(LocalDateTime.now().plusMinutes(15));
        administradorRepository.save(admin);

        // Em ambiente de desenvolvimento, o código é exibido no console para teste imediato
        log.info("Código de recuperação de senha para {}: {}", admin.getEmail(), codigo);
    }

    @Transactional
    public void redefinirSenha(RedefinirSenhaRequestDTO dto) {
        Administrador admin = administradorRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Administrador não encontrado"));

        if (admin.getCodigoRecuperacao() == null
                || !admin.getCodigoRecuperacao().equals(dto.getCodigoVerificacao().trim())) {
            throw new BusinessRuleException("Código de verificação inválido");
        }

        if (admin.getCodigoExpiracao() == null
                || admin.getCodigoExpiracao().isBefore(LocalDateTime.now())) {
            throw new BusinessRuleException("O código de verificação expirou. Solicite um novo código");
        }

        admin.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        admin.setCodigoRecuperacao(null);
        admin.setCodigoExpiracao(null);
        administradorRepository.save(admin);
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
