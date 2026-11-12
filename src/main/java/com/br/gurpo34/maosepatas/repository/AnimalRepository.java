package com.br.gurpo34.maosepatas.repository;

import com.br.gurpo34.maosepatas.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByTutorId (Long tutorId);

    List<Animal> findByTutorNomeContainingIgnoreCase (String nomeTutor);

    boolean existsByTutorId(Long tutorId);

    List<Animal> findByNomeContainingIgnoreCase (String nome);

    List<Animal> findByTutorCpf (String cpf);

    boolean existsByNomeIgnoreCaseAndTutorId(String nome, Long tutorId);

    List<Animal> findByEspecieIgnoreCase(String especie);
}
