package com.br.gurpo34.maosepatas.dto.atendimento;

import com.br.gurpo34.maosepatas.dto.animal.AnimalResumoDTO;
import com.br.gurpo34.maosepatas.dto.tutor.TutorResumoDTO;
import com.br.gurpo34.maosepatas.enums.TipoAtendimento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AtendimentoResponseDTO {

    private Long id;
    private TipoAtendimento tipo;
    private String descricao;
    private String observacoes;
    private LocalDateTime dataAtendimento;
    private Boolean realizado;
    private LocalDateTime dataCadastro;
    private AnimalResumoDTO animal;
    private TutorResumoDTO tutor;

}
