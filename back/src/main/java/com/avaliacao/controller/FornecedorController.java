package com.avaliacao.controller;

import com.avaliacao.dto.FornecedorRequestDTO;
import com.avaliacao.model.Fornecedor;
import com.avaliacao.model.Mercadoria;
import com.avaliacao.service.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public ResponseEntity<Fornecedor> criar(@Valid @RequestBody FornecedorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fornecedorService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<Page<Fornecedor>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(fornecedorService.listarTodos(pageable));
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<Fornecedor>> listarInativos(Pageable pageable) {
        return ResponseEntity.ok(fornecedorService.listarInativos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FornecedorRequestDTO dto
    ) {
        return ResponseEntity.ok(fornecedorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        fornecedorService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Fornecedor> reativar(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.reativar(id));
    }

    @GetMapping("/{id}/mercadorias")
    public ResponseEntity<List<Mercadoria>> listarMercadoriasDoFornecedor(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.listarMercadoriasDoFornecedor(id));
    }
}
