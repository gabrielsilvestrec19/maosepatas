package com.br.gurpo34.maosepatas.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorResponseDTO {

    private Long id;
    private String nome;
    private String email;

}
