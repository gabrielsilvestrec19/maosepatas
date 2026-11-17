package com.br.gurpo34.maosepatas.dto.atendimento;

import com.br.gurpo34.maosepatas.enums.TipoAtendimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoRequestDTO {

    @NotNull(message = "O tipo do atendimento é obrigatório")
    private TipoAtendimento tipo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    private String observacoes;

    @NotNull(message = "A data e hora do atendimento são obrigatórias")
    private LocalDateTime dataAtendimento;

    private Boolean realizado = false;

    @NotNull(message = "O animal é obrigatório")
    private Long animalId;

    private Long tutorId;

}
