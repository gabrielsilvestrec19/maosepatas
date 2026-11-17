package com.br.gurpo34.maosepatas.repository;

import com.br.gurpo34.maosepatas.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAllByOrderByDataCadastroDesc();

    List<Animal> findTop5ByOrderByDataCadastroDesc();

    List<Animal> findByTutorIdOrderByDataCadastroDesc(Long tutorId);

    Optional<Animal> findByMicrochip(String microchip);

    boolean existsByMicrochip(String microchip);

    List<Animal> findByNomeContainingIgnoreCaseOrderByDataCadastroDesc(String nome);
}
