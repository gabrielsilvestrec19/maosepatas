package com.br.gurpo34.maosepatas.service;

import com.br.gurpo34.maosepatas.dto.tutor.ContatoRequestDTO;
import com.br.gurpo34.maosepatas.dto.tutor.TutorResponseDTO;
import com.br.gurpo34.maosepatas.dto.tutor.TutorResquestDTO;
import com.br.gurpo34.maosepatas.entity.Contato;
import com.br.gurpo34.maosepatas.entity.Tutor;
import com.br.gurpo34.maosepatas.exception.BusinessRuleException;
import com.br.gurpo34.maosepatas.exception.ResourceNotFoundException;
import com.br.gurpo34.maosepatas.mapper.TutorMapper;
import com.br.gurpo34.maosepatas.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorService {
    private final TutorRepository tutorRepository;
    private final TutorMapper tutorMapper;

    @Transactional
    public TutorResponseDTO salvar(TutorResquestDTO dto) {
        if (tutorRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessRuleException("Já existe um cliente cadastrado com o CPF informado");
        }

        Tutor tutor = tutorMapper.toEntity(dto);
        Tutor tutorSalvo = tutorRepository.save(tutor);
        return tutorMapper.toResponseDTO(tutorSalvo);
    }

    @Transactional(readOnly = true)
    public List<TutorResponseDTO> listarTodos() {
        return tutorRepository.findAllByOrderByNomeAsc().stream()
                .map(tutorMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TutorResponseDTO> pesquisar(String termo) {
        if (termo == null || termo.isBlank()) {
            return listarTodos();
        }
        return tutorRepository.pesquisarPorNomeCodigoOuCpf(termo.trim()).stream()
                .map(tutorMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TutorResponseDTO> filtrarPorPossuirAnimais(Boolean comAnimais) {
        if (comAnimais == null) {
            return listarTodos();
        }
        List<Tutor> tutores = comAnimais
                ? tutorRepository.findByAnimaisIsNotEmptyOrderByNomeAsc()
                : tutorRepository.findByAnimaisIsEmptyOrderByNomeAsc();

        return tutores.stream()
                .map(tutorMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TutorResponseDTO buscarPorId(Long id) {
        Tutor tutor = buscarEntidadePorId(id);
        return tutorMapper.toResponseDTO(tutor);
    }

    @Transactional
    public TutorResponseDTO atualizar(Long id, TutorResquestDTO dto) {
        Tutor tutor = buscarEntidadePorId(id);

        tutorRepository.findByCpf(dto.getCpf()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new BusinessRuleException("O CPF informado já está vinculado a outro cliente");
            }
        });

        tutorMapper.updateEntityFromDTO(dto, tutor);
        Tutor tutorAtualizado = tutorRepository.save(tutor);
        return tutorMapper.toResponseDTO(tutorAtualizado);
    }

    @Transactional
    public TutorResponseDTO adicionarContato(Long tutorId, ContatoRequestDTO dto) {
        Tutor tutor = buscarEntidadePorId(tutorId);
        Contato novoContato = tutorMapper.toContatoEntity(dto, tutor);
        tutor.getContatos().add(novoContato);

        Tutor tutorAtualizado = tutorRepository.save(tutor);
        return tutorMapper.toResponseDTO(tutorAtualizado);
    }

    @Transactional
    public TutorResponseDTO removerContato(Long tutorId, Long contatoId) {
        Tutor tutor = buscarEntidadePorId(tutorId);

        if (tutor.getContatos().size() <= 1) {
            throw new BusinessRuleException("O cliente deve possuir ao menos um contato cadastrado");
        }

        boolean removido = tutor.getContatos().removeIf(c -> c.getId().equals(contatoId));
        if (!removido) {
            throw new ResourceNotFoundException("Contato não encontrado para este cliente");
        }

        Tutor tutorAtualizado = tutorRepository.save(tutor);
        return tutorMapper.toResponseDTO(tutorAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Tutor tutor = buscarEntidadePorId(id);

        if (tutorRepository.existsByIdAndAnimaisIsNotEmpty(id)) {
            throw new BusinessRuleException("Não é possível excluir um cliente que possui animais vinculados");
        }

        tutorRepository.delete(tutor);
    }

    public Tutor buscarEntidadePorId(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
    }
}
