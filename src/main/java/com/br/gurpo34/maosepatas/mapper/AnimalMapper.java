package com.br.gurpo34.maosepatas.mapper;

import com.br.gurpo34.maosepatas.dto.animal.AnimalRequestDTO;
import com.br.gurpo34.maosepatas.dto.animal.AnimalResponseDTO;
import com.br.gurpo34.maosepatas.dto.animal.AnimalResumoDTO;
import com.br.gurpo34.maosepatas.dto.atendimento.AtendimentoResumoDTO;
import com.br.gurpo34.maosepatas.entity.Animal;
import com.br.gurpo34.maosepatas.entity.Atendimento;
import com.br.gurpo34.maosepatas.entity.Tutor;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class AnimalMapper {
    private final TutorMapper tutorMapper;

    public Animal toEntity(AnimalRequestDTO dto, Tutor tutor) {
        if (dto == null) {
            return null;
        }
        Animal animal = new Animal();
        animal.setNome(dto.getNome());
        animal.setFoto(dto.getFoto());
        animal.setSexo(dto.getSexo());
        animal.setEsterilizado(dto.getEsterilizado());
        animal.setEspecie(dto.getEspecie());
        animal.setRaca(dto.getRaca());
        animal.setPelagem(dto.getPelagem());
        animal.setDataNascimento(dto.getDataNascimento());
        animal.setMicrochip(dto.getMicrochip());
        animal.setTutor(tutor);
        return animal;
    }

    public void updateEntityFromDTO(AnimalRequestDTO dto, Animal entity, Tutor tutor) {
        entity.setNome(dto.getNome());
        entity.setFoto(dto.getFoto());
        entity.setSexo(dto.getSexo());
        entity.setEsterilizado(dto.getEsterilizado());
        entity.setEspecie(dto.getEspecie());
        entity.setRaca(dto.getRaca());
        entity.setPelagem(dto.getPelagem());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setMicrochip(dto.getMicrochip());
        entity.setTutor(tutor);
    }

    public AnimalResumoDTO toResumoDTO(Animal entity) {
        if (entity == null) {
            return null;
        }
        return new AnimalResumoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEspecie(),
                entity.getRaca(),
                entity.getSexo(),
                entity.getFoto(),
                entity.getTutor() != null ? entity.getTutor().getId() : null,
                entity.getTutor() != null ? entity.getTutor().getNome() : null,
                entity.getDataCadastro()
        );
    }

    public AnimalResponseDTO toResponseDTO(Animal entity) {
        if (entity == null) {
            return null;
        }
        List<AtendimentoResumoDTO> historico = entity.getAtendimentos() == null
                ? Collections.emptyList()
                : entity.getAtendimentos().stream()
                .sorted(Comparator.comparing(Atendimento::getDataAtendimento).reversed())
                .map(this::toAtendimentoResumoDTO)
                .toList();

        return new AnimalResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getFoto(),
                entity.getSexo(),
                entity.getEsterilizado(),
                entity.getEspecie(),
                entity.getRaca(),
                entity.getPelagem(),
                entity.getDataNascimento(),
                entity.getMicrochip(),
                entity.getDataCadastro(),
                tutorMapper.toResumoDTO(entity.getTutor()),
                historico
        );
    }

    private AtendimentoResumoDTO toAtendimentoResumoDTO(Atendimento atendimento) {
        return new AtendimentoResumoDTO(
                atendimento.getId(),
                atendimento.getTipo(),
                atendimento.getDescricao(),
                atendimento.getDataAtendimento(),
                atendimento.getRealizado(),
                atendimento.getAnimal() != null ? atendimento.getAnimal().getId() : null,
                atendimento.getAnimal() != null ? atendimento.getAnimal().getNome() : null,
                atendimento.getTutor() != null ? atendimento.getTutor().getId() : null,
                atendimento.getTutor() != null ? atendimento.getTutor().getNome() : null
        );
    }
}
