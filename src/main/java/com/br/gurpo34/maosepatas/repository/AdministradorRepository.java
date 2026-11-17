package com.br.gurpo34.maosepatas.repository;

import com.br.gurpo34.maosepatas.entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Administrador> findByCodigoRecuperacao(String codigoRecuperacao);
}
