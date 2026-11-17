package com.br.gurpo34.maosepatas.dto.dashboard;

import com.br.gurpo34.maosepatas.dto.animal.AnimalResumoDTO;
import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardResponseDTO {

    private Long totalAtendimentos;
    private Long atendimentosRealizados;
    private Long atendimentosPendentes;
    private Long totalConsultas;
    private Long totalVacinas;
    private List<AnimalResumoDTO> animaisRecentes;
    private List<AtendimentoResumoDTO> atendimentosRecentes;

}
