package com.br.gurpo34.maosepatas.entity;

import com.br.gurpo34.maosepatas.enums.SexoAnimal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "foto", columnDefinition = "TEXT")
    private String foto;

    @NotNull(message = "O sexo do animal é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false, length = 10)
    private SexoAnimal sexo;

    @NotNull(message = "Informe se o animal é esterilizado")
    @Column(name = "esterilizado", nullable = false)
    private Boolean esterilizado;

    @NotBlank(message = "A espécie é obrigatória")
    @Column(name = "especie", nullable = false, length = 25)
    private String especie;

    @Column(name = "raca", length = 50)
    private String raca;

    @Column(name = "pelagem", length = 50)
    private String pelagem;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "microchip", unique = true, length = 50)
    private String microchip;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @ManyToOne
    @JoinColumn(name = "id_tutor", nullable = false)
    @NotNull(message = "O tutor do animal é obrigatório")
    private Tutor tutor;

    @JsonIgnore
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atendimento> atendimentos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
    }
}