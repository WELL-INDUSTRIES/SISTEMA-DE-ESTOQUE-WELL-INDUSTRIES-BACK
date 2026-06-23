package com.avaliacao.repository;

import com.avaliacao.model.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    Page<Fornecedor> findByAtivoTrue(Pageable pageable);

    Page<Fornecedor> findByAtivoFalse(Pageable pageable);
}
