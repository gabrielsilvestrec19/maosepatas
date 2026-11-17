package com.br.gurpo34.maosepatas.dto.auth;

import com.br.gurpo34.maosepatas.dto.admin.AdministradorResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDTO {
    private String token;
    private String tipo;
    private Long expiracaoEmMs;
    private AdministradorResponseDTO administrador;
}
