package com.br.gurpo34.maosepatas.dto.tutor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoResponseDTO {

    private Long id;
    private String telefone;
    private Boolean whatsapp;
    private String linkWhatsapp;

}
