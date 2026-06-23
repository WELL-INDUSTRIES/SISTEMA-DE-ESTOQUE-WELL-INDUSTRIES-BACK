package com.avaliacao.repository;

import com.avaliacao.model.Mercadoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MercadoriaRepository extends JpaRepository<Mercadoria, Long> {

    List<Mercadoria> findByQuantidadeGreaterThan(Integer quantidade);

    Page<Mercadoria> findByAtivoTrue(Pageable pageable);

    Page<Mercadoria> findByAtivoFalse(Pageable pageable);

    Page<Mercadoria> findByAtivoTrueAndQuantidadeGreaterThan(Integer quantidade, Pageable pageable);

    List<Mercadoria> findByFornecedorId(Long fornecedorId);

    boolean existsByFornecedorId(Long fornecedorId);

    @Modifying
    @Query("update Mercadoria m set m.quantidade = :quantidade where m.id = :id and m.ativo = true")
    int atualizarQuantidade(@Param("id") Long id, @Param("quantidade") Integer quantidade);
}
