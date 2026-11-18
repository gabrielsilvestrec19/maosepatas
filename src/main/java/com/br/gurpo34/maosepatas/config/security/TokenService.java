package com.br.gurpo34.maosepatas.config.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    private static final long EXPIRACAO_PADRAO_MS = 86_400_000L; // 24 horas
    private static final long EXPIRACAO_LEMBRAR_MS = 2_592_000_000L; // 30 dias

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(String email, boolean lembrarDeMim) {
        long expiracaoMs = calcularExpiracaoMs(lembrarDeMim);
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + expiracaoMs);

        return Jwts.builder()
                .subject(email)
                .issuedAt(agora)
                .expiration(dataExpiracao)
                .signWith(getSigningKey())
                .compact();
    }

    public long calcularExpiracaoMs(boolean lembrarDeMim) {
        return lembrarDeMim ? EXPIRACAO_LEMBRAR_MS : EXPIRACAO_PADRAO_MS;
    }

    public String extrairEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
