package com.br.gurpo34.maosepatas.repository;

import com.br.gurpo34.maosepatas.entity.Atendimento;
import com.br.gurpo34.maosepatas.enums.TipoAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento,Long> {
    List<Atendimento> findTop5ByOrderByDataCadastroDesc();

    List<Atendimento> findTop5ByRealizadoTrueOrderByDataAtendimentoDesc();

    List<Atendimento> findByAnimalIdOrderByDataAtendimentoDesc(Long animalId);

    List<Atendimento> findByTutorIdOrderByDataAtendimentoDesc(Long tutorId);

    long countByRealizado(Boolean realizado);

    long countByTipo(TipoAtendimento tipo);

    long countByTipoAndRealizado(TipoAtendimento tipo, Boolean realizado);

    @Query("""
            SELECT a FROM Atendimento a
            WHERE LOWER(a.descricao) LIKE LOWER(CONCAT('%', :termo, '%'))
               OR LOWER(a.animal.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
               OR LOWER(a.tutor.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
            ORDER BY a.dataAtendimento DESC
            """)
    List<Atendimento> pesquisarAtendimentos(@Param("termo") String termo);

    @Query("""
            SELECT a FROM Atendimento a
            WHERE (:tipo IS NULL OR a.tipo = :tipo)
              AND (:realizado IS NULL OR a.realizado = :realizado)
              AND (:inicio IS NULL OR a.dataAtendimento >= :inicio)
              AND (:fim IS NULL OR a.dataAtendimento <= :fim)
            ORDER BY a.dataAtendimento DESC
            """)
    List<Atendimento> filtrarAtendimentos(
            @Param("tipo") TipoAtendimento tipo,
            @Param("realizado") Boolean realizado,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim
    );
}
