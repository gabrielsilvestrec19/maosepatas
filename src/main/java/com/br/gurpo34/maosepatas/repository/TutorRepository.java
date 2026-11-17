package com.br.gurpo34.maosepatas.repository;

import com.br.gurpo34.maosepatas.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor,Long> {

    List<Tutor> findAllByOrderByNomeAsc();

    Optional<Tutor> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    @Query("""
            SELECT t FROM Tutor t
            WHERE LOWER(t.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
               OR t.cpf LIKE CONCAT('%', :termo, '%')
               OR CAST(t.id AS string) = :termo
            ORDER BY t.nome ASC
            """)
    List<Tutor> pesquisarPorNomeCodigoOuCpf(@Param("termo") String termo);

    List<Tutor> findByAnimaisIsNotEmptyOrderByNomeAsc();

    List<Tutor> findByAnimaisIsEmptyOrderByNomeAsc();

    boolean existsByIdAndAnimaisIsNotEmpty(Long id);
}
