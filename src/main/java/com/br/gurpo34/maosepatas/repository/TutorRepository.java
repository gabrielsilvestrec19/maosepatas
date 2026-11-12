package com.br.gurpo34.maosepatas.repository;

import com.br.gurpo34.maosepatas.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor,Long> {

    Optional<Tutor> findByEmail (String email);

    List<Tutor> findByNomeContainingIgnoreCase (String nome);

    Optional<Tutor> findByCpf(String cpf);

    List<Tutor> findByTelefoneContaining(String telefone);
}
