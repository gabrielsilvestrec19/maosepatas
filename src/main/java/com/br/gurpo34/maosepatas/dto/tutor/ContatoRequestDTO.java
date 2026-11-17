package com.br.gurpo34.maosepatas.dto.tutor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContatoRequestDTO {

    private Long id;

    @NotBlank(message = "O número de telefone é obrigatório")
    private String telefone;

    @NotNull(message = "Informe se o contato possui WhatsApp")
    private Boolean whatsapp = false;

}
