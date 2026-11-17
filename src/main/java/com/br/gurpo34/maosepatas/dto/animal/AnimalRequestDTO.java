package com.br.gurpo34.maosepatas.dto.animal;

import com.br.gurpo34.maosepatas.enums.SexoAnimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnimalRequestDTO {

    @NotBlank(message = "O nome do animal é obrigatório")
    private String nome;

    private String foto;

    @NotNull(message = "O sexo do animal é obrigatório")
    private SexoAnimal sexo;

    @NotNull(message = "Informe se o animal é esterilizado")
    private Boolean esterilizado;

    @NotBlank(message = "A espécie é obrigatória")
    private String especie;

    private String raca;

    private String pelagem;

    private LocalDate dataNascimento;

    private String microchip;

    @NotNull(message = "O tutor do animal é obrigatório")
    private Long tutorId;
}

