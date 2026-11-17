package com.br.gurpo34.maosepatas.entity;

import com.br.gurpo34.maosepatas.enums.TipoAtendimento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "tb_atendimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atendimento", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "O tipo do atendimento é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoAtendimento tipo;

    @NotBlank(message = "A descrição da consulta ou vacina é obrigatória")
    @Column(name = "descricao", nullable = false, length = 200)
    private String descricao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @NotNull(message = "A data e hora do atendimento são obrigatórias")
    @Column(name = "data_atendimento", nullable = false)
    private LocalDateTime dataAtendimento;

    @NotNull(message = "O status de realização é obrigatório")
    @Column(name = "realizado", nullable = false)
    private Boolean realizado = false;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @ManyToOne
    @JoinColumn(name = "id_animal", nullable = false)
    @NotNull(message = "O animal do atendimento é obrigatório")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "id_tutor", nullable = false)
    @NotNull(message = "O tutor do atendimento é obrigatório")
    private Tutor tutor;

    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
    }
}
