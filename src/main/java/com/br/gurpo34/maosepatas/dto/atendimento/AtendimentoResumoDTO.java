package com.br.gurpo34.maosepatas.dto.atendimento;

import com.br.gurpo34.maosepatas.enums.TipoAtendimento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoResumoDTO {

    private Long id;
    private TipoAtendimento tipo;
    private String descricao;
    private LocalDateTime dataAtendimento;
    private Boolean realizado;
    private Long animalId;
    private String animalNome;
    private Long tutorId;
    private String tutorNome;

}
