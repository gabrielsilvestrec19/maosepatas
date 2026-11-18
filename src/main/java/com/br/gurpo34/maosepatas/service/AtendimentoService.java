package com.br.gurpo34.maosepatas.service;

import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoRequestDTO;
import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoResponseDTO;
import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoResumoDTO;
import com.br.gurpo34.maosepatas.entity.Animal;
import com.br.gurpo34.maosepatas.entity.Atendimento;
import com.br.gurpo34.maosepatas.entity.Tutor;
import com.br.gurpo34.maosepatas.enums.TipoAtendimento;
import com.br.gurpo34.maosepatas.exception.BusinessRuleException;
import com.br.gurpo34.maosepatas.exception.ResourceNotFoundException;
import com.br.gurpo34.maosepatas.mapper.AtendimentoMapper;
import com.br.gurpo34.maosepatas.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;
    private final AnimalService animalService;
    private final AtendimentoMapper atendimentoMapper;

    @Transactional
    public AtendimentoResponseDTO salvar(AtendimentoRequestDTO dto) {
        Animal animal = animalService.buscarEntidadePorId(dto.getAnimalId());
        Tutor tutor = resolverTutorDoAnimal(animal, dto.getTutorId());

        Atendimento atendimento = atendimentoMapper.toEntity(dto, animal, tutor);
        Atendimento atendimentoSalvo = atendimentoRepository.save(atendimento);
        return atendimentoMapper.toResponseDTO(atendimentoSalvo);
    }

    @Transactional(readOnly = true)
    public List<AtendimentoResumoDTO> listarTodos() {
        return atendimentoRepository.findAll(Sort.by(Sort.Direction.DESC, "dataAtendimento")).stream()
                .map(atendimentoMapper::toResumoDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtendimentoResumoDTO> listarPorAnimal(Long animalId) {
        animalService.buscarEntidadePorId(animalId);
        return atendimentoRepository.findByAnimalIdOrderByDataAtendimentoDesc(animalId).stream()
                .map(atendimentoMapper::toResumoDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtendimentoResumoDTO> pesquisar(String termo) {
        if (termo == null || termo.isBlank()) {
            return listarTodos();
        }
        return atendimentoRepository.pesquisarAtendimentos(termo.trim()).stream()
                .map(atendimentoMapper::toResumoDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtendimentoResumoDTO> filtrar(
            TipoAtendimento tipo,
            Boolean realizado,
            LocalDateTime inicio,
            LocalDateTime fim
    ) {
        return atendimentoRepository.filtrarAtendimentos(tipo, realizado, inicio, fim).stream()
                .map(atendimentoMapper::toResumoDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public AtendimentoResponseDTO buscarPorId(Long id) {
        Atendimento atendimento = buscarEntidadePorId(id);
        return atendimentoMapper.toResponseDTO(atendimento);
    }

    @Transactional
    public AtendimentoResponseDTO marcarComoRealizado(Long id) {
        Atendimento atendimento = buscarEntidadePorId(id);
        atendimento.setRealizado(true);
        Atendimento atendimentoAtualizado = atendimentoRepository.save(atendimento);
        return atendimentoMapper.toResponseDTO(atendimentoAtualizado);
    }

    @Transactional
    public AtendimentoResponseDTO atualizar(Long id, AtendimentoRequestDTO dto) {
        Atendimento atendimento = buscarEntidadePorId(id);
        Animal animal = animalService.buscarEntidadePorId(dto.getAnimalId());
        Tutor tutor = resolverTutorDoAnimal(animal, dto.getTutorId());

        atendimentoMapper.updateEntityFromDTO(dto, atendimento, animal, tutor);
        Atendimento atendimentoAtualizado = atendimentoRepository.save(atendimento);
        return atendimentoMapper.toResponseDTO(atendimentoAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Atendimento atendimento = buscarEntidadePorId(id);
        atendimentoRepository.delete(atendimento);
    }

    @Transactional(readOnly = true)
    public Map<String, Long> obterContadores() {
        Map<String, Long> contadores = new LinkedHashMap<>();
        contadores.put("total", atendimentoRepository.count());
        contadores.put("realizados", atendimentoRepository.countByRealizado(true));
        contadores.put("pendentes", atendimentoRepository.countByRealizado(false));
        contadores.put("consultas", atendimentoRepository.countByTipo(TipoAtendimento.CONSULTA));
        contadores.put("vacinas", atendimentoRepository.countByTipo(TipoAtendimento.VACINA));
        return contadores;
    }

    public Atendimento buscarEntidadePorId(Long id) {
        return atendimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atendimento não encontrado com o ID: " + id));
    }

    private Tutor resolverTutorDoAnimal(Animal animal, Long tutorIdInformado) {
        Tutor tutorDoAnimal = animal.getTutor();
        if (tutorIdInformado != null && !tutorDoAnimal.getId().equals(tutorIdInformado)) {
            throw new BusinessRuleException("O cliente informado não é o tutor vinculado a este animal");
        }
        return tutorDoAnimal;
    }
}
