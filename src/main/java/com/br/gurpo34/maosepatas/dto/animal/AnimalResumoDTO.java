package com.br.gurpo34.maosepatas.dto.animal;

import com.br.gurpo34.maosepatas.enums.SexoAnimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalResumoDTO {

    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private SexoAnimal sexo;
    private String foto;
    private Long tutorId;
    private String tutorNome;
    private LocalDateTime dataCadastro;

}
