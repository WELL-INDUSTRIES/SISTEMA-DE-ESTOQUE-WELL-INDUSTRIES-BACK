package com.avaliacao.repository;

import com.avaliacao.model.Mercadoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MercadoriaRepository extends JpaRepository<Mercadoria, Long> {

    List<Mercadoria> findByQuantidadeGreaterThan(Integer quantidade);

    List<Mercadoria> findByFornecedorId(Long fornecedorId);

    boolean existsByFornecedorId(Long fornecedorId);
}
