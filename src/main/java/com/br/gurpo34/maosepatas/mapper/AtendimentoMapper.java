package com.br.gurpo34.maosepatas.mapper;

import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoRequestDTO;
import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoResponseDTO;
import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoResumoDTO;
import com.br.gurpo34.maosepatas.entity.Animal;
import com.br.gurpo34.maosepatas.entity.Atendimento;
import com.br.gurpo34.maosepatas.entity.Tutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtendimentoMapper {
    private final AnimalMapper animalMapper;
    private final TutorMapper tutorMapper;

    public Atendimento toEntity(AtendimentoRequestDTO dto, Animal animal, Tutor tutor) {
        if (dto == null) {
            return null;
        }
        Atendimento atendimento = new Atendimento();
        atendimento.setTipo(dto.getTipo());
        atendimento.setDescricao(dto.getDescricao());
        atendimento.setObservacoes(dto.getObservacoes());
        atendimento.setDataAtendimento(dto.getDataAtendimento());
        atendimento.setRealizado(Boolean.TRUE.equals(dto.getRealizado()));
        atendimento.setAnimal(animal);
        atendimento.setTutor(tutor);
        return atendimento;
    }

    public void updateEntityFromDTO(AtendimentoRequestDTO dto, Atendimento entity, Animal animal, Tutor tutor) {
        entity.setTipo(dto.getTipo());
        entity.setDescricao(dto.getDescricao());
        entity.setObservacoes(dto.getObservacoes());
        entity.setDataAtendimento(dto.getDataAtendimento());
        if (dto.getRealizado() != null) {
            entity.setRealizado(dto.getRealizado());
        }
        entity.setAnimal(animal);
        entity.setTutor(tutor);
    }

    public AtendimentoResumoDTO toResumoDTO(Atendimento entity) {
        if (entity == null) {
            return null;
        }
        return new AtendimentoResumoDTO(
                entity.getId(),
                entity.getTipo(),
                entity.getDescricao(),
                entity.getDataAtendimento(),
                entity.getRealizado(),
                entity.getAnimal() != null ? entity.getAnimal().getId() : null,
                entity.getAnimal() != null ? entity.getAnimal().getNome() : null,
                entity.getTutor() != null ? entity.getTutor().getId() : null,
                entity.getTutor() != null ? entity.getTutor().getNome() : null
        );
    }

    public AtendimentoResponseDTO toResponseDTO(Atendimento entity) {
        if (entity == null) {
            return null;
        }
        return new AtendimentoResponseDTO(
                entity.getId(),
                entity.getTipo(),
                entity.getDescricao(),
                entity.getObservacoes(),
                entity.getDataAtendimento(),
                entity.getRealizado(),
                entity.getDataCadastro(),
                animalMapper.toResumoDTO(entity.getAnimal()),
                tutorMapper.toResumoDTO(entity.getTutor())
        );
    }
}
