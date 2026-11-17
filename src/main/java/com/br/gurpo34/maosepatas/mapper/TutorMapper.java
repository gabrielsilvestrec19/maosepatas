package com.br.gurpo34.maosepatas.mapper;

import com.br.gurpo34.maosepatas.dto.animal.AnimalResumoDTO;
import com.br.gurpo34.maosepatas.dto.tutor.*;
import com.br.gurpo34.maosepatas.entity.Animal;
import com.br.gurpo34.maosepatas.entity.Contato;
import com.br.gurpo34.maosepatas.entity.Endereco;
import com.br.gurpo34.maosepatas.entity.Tutor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TutorMapper {

    public Tutor toEntity(TutorResquestDTO dto) {
        if (dto == null) {
            return null;
        }
        Tutor tutor = new Tutor();
        tutor.setNome(dto.getNome());
        tutor.setCpf(dto.getCpf());
        tutor.setEndereco(toEnderecoEntity(dto.getEndereco()));

        if (dto.getContatos() != null) {
            List<Contato> contatos = dto.getContatos().stream()
                    .map(contatoDTO -> toContatoEntity(contatoDTO, tutor))
                    .toList();
            tutor.getContatos().addAll(contatos);
        }

        return tutor;
    }

    public void updateEntityFromDTO(TutorResquestDTO dto, Tutor entity) {
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setEndereco(toEnderecoEntity(dto.getEndereco()));

        entity.getContatos().clear();
        if (dto.getContatos() != null) {
            List<Contato> novosContatos = dto.getContatos().stream()
                    .map(contatoDTO -> toContatoEntity(contatoDTO, entity))
                    .toList();
            entity.getContatos().addAll(novosContatos);
        }
    }

    public TutorResponseDTO toResponseDTO(Tutor entity) {
        if (entity == null) {
            return null;
        }
        List<ContatoResponseDTO> contatosDTO = entity.getContatos() == null
                ? Collections.emptyList()
                : entity.getContatos().stream()
                .map(this::toContatoResponseDTO)
                .toList();

        List<AnimalResumoDTO> animaisDTO = entity.getAnimais() == null
                ? Collections.emptyList()
                : entity.getAnimais().stream()
                .map(this::toAnimalResumoDTO)
                .toList();

        return new TutorResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                toEnderecoDTO(entity.getEndereco()),
                entity.getDataCadastro(),
                contatosDTO,
                animaisDTO.size(),
                animaisDTO
        );
    }

    public TutorResumoDTO toResumoDTO(Tutor entity) {
        if (entity == null) {
            return null;
        }
        List<ContatoResponseDTO> contatosDTO = entity.getContatos() == null
                ? Collections.emptyList()
                : entity.getContatos().stream()
                .map(this::toContatoResponseDTO)
                .toList();

        return new TutorResumoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getCpf(),
                contatosDTO
        );
    }

    public Endereco toEnderecoEntity(EnderecoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Endereco(
                dto.getCep(),
                dto.getLogradouro(),
                dto.getNumero(),
                dto.getComplemento(),
                dto.getBairro(),
                dto.getCidade(),
                dto.getEstado().toUpperCase()
        );
    }

    public EnderecoDTO toEnderecoDTO(Endereco entity) {
        if (entity == null) {
            return null;
        }
        return new EnderecoDTO(
                entity.getCep(),
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getComplemento(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getEstado()
        );
    }

    public Contato toContatoEntity(ContatoRequestDTO dto, Tutor tutor) {
        if (dto == null) {
            return null;
        }
        Contato contato = new Contato();
        contato.setTelefone(dto.getTelefone());
        contato.setWhatsapp(Boolean.TRUE.equals(dto.getWhatsapp()));
        contato.setTutor(tutor);
        return contato;
    }

    public ContatoResponseDTO toContatoResponseDTO(Contato entity) {
        if (entity == null) {
            return null;
        }
        return new ContatoResponseDTO(
                entity.getId(),
                entity.getTelefone(),
                entity.getWhatsapp(),
                gerarLinkWhatsapp(entity.getTelefone(), entity.getWhatsapp())
        );
    }

    private String gerarLinkWhatsapp(String telefone, Boolean whatsapp) {
        if (!Boolean.TRUE.equals(whatsapp) || telefone == null) {
            return null;
        }
        String apenasDigitos = telefone.replaceAll("\\D", "");
        if (apenasDigitos.isEmpty()) {
            return null;
        }
        if (!apenasDigitos.startsWith("55")) {
            apenasDigitos = "55" + apenasDigitos;
        }
        return "https://wa.me/" + apenasDigitos;
    }

    private AnimalResumoDTO toAnimalResumoDTO(Animal animal) {
        return new AnimalResumoDTO(
                animal.getId(),
                animal.getNome(),
                animal.getEspecie(),
                animal.getRaca(),
                animal.getSexo(),
                animal.getFoto(),
                animal.getTutor() != null ? animal.getTutor().getId() : null,
                animal.getTutor() != null ? animal.getTutor().getNome() : null,
                animal.getDataCadastro()
        );
    }

}
