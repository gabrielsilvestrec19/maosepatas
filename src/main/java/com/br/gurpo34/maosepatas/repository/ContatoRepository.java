package com.br.gurpo34.maosepatas.repository;

import com.br.gurpo34.maosepatas.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    List<Contato> findByTutorId(Long tutorId);
}
