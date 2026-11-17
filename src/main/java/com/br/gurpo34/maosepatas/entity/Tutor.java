package com.br.gurpo34.maosepatas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_tutor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tutor", unique = true, nullable = false)
    private Long id;

    @NotBlank(message = "O nome do cliente é obrigatório")
    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Column(name = "cpf", unique = true, nullable = false, length = 14)
    private String cpf;

    @Valid
    @NotNull(message = "O endereço é obrigatório")
    @Embedded
    private Endereco endereco;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "tutor")
    private List<Animal> animais = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "tutor")
    private List<Atendimento> atendimentos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
    }
}
