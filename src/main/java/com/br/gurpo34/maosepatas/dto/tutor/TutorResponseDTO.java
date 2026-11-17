package com.br.gurpo34.maosepatas.dto.tutor;

import com.br.gurpo34.maosepatas.dto.animal.AnimalResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TutorResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private EnderecoDTO endereco;
    private LocalDateTime dataCadastro;
    private List<ContatoResponseDTO> contatos;
    private Integer quantidadeAnimais;
    private List<AnimalResumoDTO> animais;

}
