package com.br.gurpo34.maosepatas.dto.tutor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TutorResquestDTO {

    @NotBlank(message = "O nome do cliente é obrigatório")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    @Valid
    @NotNull(message = "O endereço é obrigatório")
    private EnderecoDTO endereco;

    @Valid
    @NotEmpty(message = "Informe ao menos um contato para o cliente")
    private List<ContatoRequestDTO> contatos = new ArrayList<>();
}
