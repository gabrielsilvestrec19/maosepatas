package com.br.gurpo34.maosepatas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "tb_administrador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_administrador", unique = true, nullable = false)
    private Long id;

    @NotBlank(message = "O nome do administrador é obrigatório")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @Column(name = "codigo_recuperacao", length = 10)
    private String codigoRecuperacao;

    @Column(name = "codigo_expiracao")
    private LocalDateTime codigoExpiracao;
}
