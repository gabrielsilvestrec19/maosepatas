package com.br.gurpo34.maosepatas.dto.animal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnimalLoteRequestDTO {

    @Valid
    @NotEmpty(message = "A lista de animais não pode estar vazia")
    private List<AnimalRequestDTO> animais = new ArrayList<>();

}
