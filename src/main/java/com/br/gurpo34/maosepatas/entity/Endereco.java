package com.br.gurpo34.maosepatas.entity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @NotBlank(message = "O CEP é obrigatório")
    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    @NotBlank(message = "O logradouro é obrigatório")
    @Column(name = "logradouro", nullable = false, length = 150)
    private String logradouro;

    @NotBlank(message = "O número é obrigatório")
    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    @Column(name = "complemento", length = 100)
    private String complemento;

    @NotBlank(message = "O bairro é obrigatório")
    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;

    @NotBlank(message = "O estado (UF) é obrigatório")
    @Column(name = "estado", nullable = false, length = 2)
    private String estado;
}
