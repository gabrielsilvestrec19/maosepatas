package com.br.gurpo34.maosepatas.service;

import com.br.gurpo34.maosepatas.dto.animal.AnimalLoteRequestDTO;
import com.br.gurpo34.maosepatas.dto.animal.AnimalRequestDTO;
import com.br.gurpo34.maosepatas.dto.animal.AnimalResponseDTO;
import com.br.gurpo34.maosepatas.dto.animal.AnimalResumoDTO;
import com.br.gurpo34.maosepatas.entity.Animal;
import com.br.gurpo34.maosepatas.entity.Tutor;
import com.br.gurpo34.maosepatas.exception.BusinessRuleException;
import com.br.gurpo34.maosepatas.exception.ResourceNotFoundException;
import com.br.gurpo34.maosepatas.mapper.AnimalMapper;
import com.br.gurpo34.maosepatas.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final TutorService tutorService;
    private final AnimalMapper animalMapper;

    @Transactional
    public AnimalResponseDTO salvar(AnimalRequestDTO dto) {
        validarMicrochipUnico(dto.getMicrochip(), null);
        Tutor tutor = tutorService.buscarEntidadePorId(dto.getTutorId());

        Animal animal = animalMapper.toEntity(dto, tutor);
        Animal animalSalvo = animalRepository.save(animal);
        return animalMapper.toResponseDTO(animalSalvo);
    }

    @Transactional
    public List<AnimalResponseDTO> salvarEmLote(AnimalLoteRequestDTO loteDto) {
        Set<String> microchipsNoLote = new HashSet<>();
        List<Animal> animaisParaSalvar = new ArrayList<>();

        for (AnimalRequestDTO dto : loteDto.getAnimais()) {
            if (dto.getMicrochip() != null && !dto.getMicrochip().isBlank()) {
                String chipNormalizado = dto.getMicrochip().trim();
                if (!microchipsNoLote.add(chipNormalizado)) {
                    throw new BusinessRuleException("Microchip duplicado na lista de cadastro: " + chipNormalizado);
                }
                validarMicrochipUnico(chipNormalizado, null);
            }

            Tutor tutor = tutorService.buscarEntidadePorId(dto.getTutorId());
            animaisParaSalvar.add(animalMapper.toEntity(dto, tutor));
        }

        return animalRepository.saveAll(animaisParaSalvar).stream()
                .map(animalMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AnimalResumoDTO> listarTodos() {
        return animalRepository.findAllByOrderByDataCadastroDesc().stream()
                .map(animalMapper::toResumoDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AnimalResumoDTO> listarPorTutor(Long tutorId) {
        tutorService.buscarEntidadePorId(tutorId);
        return animalRepository.findByTutorIdOrderByDataCadastroDesc(tutorId).stream()
                .map(animalMapper::toResumoDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AnimalResumoDTO> pesquisarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return listarTodos();
        }
        return animalRepository.findByNomeContainingIgnoreCaseOrderByDataCadastroDesc(nome.trim()).stream()
                .map(animalMapper::toResumoDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public AnimalResponseDTO buscarPorId(Long id) {
        Animal animal = buscarEntidadePorId(id);
        return animalMapper.toResponseDTO(animal);
    }

    @Transactional
    public AnimalResponseDTO atualizar(Long id, AnimalRequestDTO dto) {
        Animal animal = buscarEntidadePorId(id);
        validarMicrochipUnico(dto.getMicrochip(), id);
        Tutor tutor = tutorService.buscarEntidadePorId(dto.getTutorId());

        animalMapper.updateEntityFromDTO(dto, animal, tutor);
        Animal animalAtualizado = animalRepository.save(animal);
        return animalMapper.toResponseDTO(animalAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Animal animal = buscarEntidadePorId(id);
        animalRepository.delete(animal);
    }

    public Animal buscarEntidadePorId(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado com o ID: " + id));
    }

    private void validarMicrochipUnico(String microchip, Long animalIdIgnorado) {
        if (microchip == null || microchip.isBlank()) {
            return;
        }
        animalRepository.findByMicrochip(microchip.trim()).ifPresent(existente -> {
            if (animalIdIgnorado == null || !existente.getId().equals(animalIdIgnorado)) {
                throw new BusinessRuleException("Já existe um animal cadastrado com o microchip: " + microchip);
            }
        });
    }
}
