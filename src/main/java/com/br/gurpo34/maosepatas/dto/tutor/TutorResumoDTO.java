package com.br.gurpo34.maosepatas.dto.tutor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TutorResumoDTO {

    private Long id;
    private String nome;
    private String cpf;
    private List<ContatoResponseDTO> contatos;

}
