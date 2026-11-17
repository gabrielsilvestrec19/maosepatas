package com.br.gurpo34.maosepatas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_contato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contato", unique = true, nullable = false)
    private Long id;

    @NotBlank(message = "O número de contato é obrigatório")
    @Column(name = "telefone", nullable = false, length = 20)
    private String telefone;

    @NotNull(message = "Informe se o contato possui WhatsApp")
    @Column(name = "whatsapp", nullable = false)
    private Boolean whatsapp = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_tutor", nullable = false)
    @NotNull(message = "O tutor do contato é obrigatório")
    private Tutor tutor;
}
