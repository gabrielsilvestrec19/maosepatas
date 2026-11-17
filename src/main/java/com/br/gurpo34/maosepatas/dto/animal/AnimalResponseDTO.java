package com.br.gurpo34.maosepatas.dto.animal;

import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoResumoDTO;
import com.br.gurpo34.maosepatas.dto.tutor.TutorResumoDTO;
import com.br.gurpo34.maosepatas.enums.SexoAnimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalResponseDTO {

    private Long id;
    private String nome;
    private String foto;
    private SexoAnimal sexo;
    private Boolean esterilizado;
    private String especie;
    private String raca;
    private String pelagem;
    private LocalDate dataNascimento;
    private String microchip;
    private LocalDateTime dataCadastro;
    private TutorResumoDTO tutor;
    private List<AtendimentoResumoDTO> historicoAtendimentos;

}
