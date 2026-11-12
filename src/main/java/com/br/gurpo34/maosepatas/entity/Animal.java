package com.br.gurpo34.maosepatas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "tb_animal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal", unique = true, nullable = false)
    private Long id;

    @NotBlank(message = "O nome do animal é obrigatório")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A espécie é obrigatória")
    @Column(name = "especie", nullable = false, length = 25)
    private String especie;

    @Column(name = "raca",length = 25)
    private String raca;


    @ManyToOne
    @JoinColumn(name = "id_tutor")
    @NotNull(message = "O tutor do animal é obrigatório")
    private Tutor tutor;




}
