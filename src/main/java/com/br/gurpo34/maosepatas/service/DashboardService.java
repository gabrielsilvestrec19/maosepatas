package com.br.gurpo34.maosepatas.service;

import com.br.gurpo34.maosepatas.dto.animal.AnimalResumoDTO;
import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoResumoDTO;
import com.br.gurpo34.maosepatas.dto.dashboard.DashboardResponseDTO;
import com.br.gurpo34.maosepatas.enums.TipoAtendimento;
import com.br.gurpo34.maosepatas.mapper.AnimalMapper;
import com.br.gurpo34.maosepatas.mapper.AtendimentoMapper;
import com.br.gurpo34.maosepatas.repository.AnimalRepository;
import com.br.gurpo34.maosepatas.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AnimalRepository animalRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final AnimalMapper animalMapper;
    private final AtendimentoMapper atendimentoMapper;

    @Transactional(readOnly = true)
    public DashboardResponseDTO obterResumoPainel() {
        long totalAtendimentos = atendimentoRepository.count();
        long atendimentosRealizados = atendimentoRepository.countByRealizado(true);
        long atendimentosPendentes = atendimentoRepository.countByRealizado(false);
        long totalConsultas = atendimentoRepository.countByTipo(TipoAtendimento.CONSULTA);
        long totalVacinas = atendimentoRepository.countByTipo(TipoAtendimento.VACINA);

        List<AnimalResumoDTO> animaisRecentes = animalRepository.findTop5ByOrderByDataCadastroDesc().stream()
                .map(animalMapper::toResumoDTO)
                .toList();

        List<AtendimentoResumoDTO> atendimentosRecentes = atendimentoRepository.findTop5ByOrderByDataCadastroDesc().stream()
                .map(atendimentoMapper::toResumoDTO)
                .toList();

        return new DashboardResponseDTO(
                totalAtendimentos,
                atendimentosRealizados,
                atendimentosPendentes,
                totalConsultas,
                totalVacinas,
                animaisRecentes,
                atendimentosRecentes
        );
    }

}
