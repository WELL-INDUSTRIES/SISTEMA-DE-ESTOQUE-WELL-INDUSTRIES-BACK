package com.avaliacao.controller;

import com.avaliacao.dto.MercadoriaQuantidadeDTO;
import com.avaliacao.dto.MercadoriaRequestDTO;
import com.avaliacao.model.Mercadoria;
import com.avaliacao.service.MercadoriaService;
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

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaController {

    private final MercadoriaService mercadoriaService;

    public MercadoriaController(MercadoriaService mercadoriaService) {
        this.mercadoriaService = mercadoriaService;
    }

    @PostMapping
    public ResponseEntity<Mercadoria> criar(@Valid @RequestBody MercadoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mercadoriaService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<Page<Mercadoria>> listarTodosComEstoque(Pageable pageable) {
        return ResponseEntity.ok(mercadoriaService.listarTodosComEstoque(pageable));
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<Mercadoria>> listarInativos(Pageable pageable) {
        return ResponseEntity.ok(mercadoriaService.listarInativos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mercadoria> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mercadoriaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mercadoria> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MercadoriaRequestDTO dto
    ) {
        return ResponseEntity.ok(mercadoriaService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/quantidade")
    public ResponseEntity<Void> atualizarQuantidade(
            @PathVariable Long id,
            @Valid @RequestBody MercadoriaQuantidadeDTO dto
    ) {
        mercadoriaService.atualizarQuantidade(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        mercadoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Mercadoria> reativar(@PathVariable Long id) {
        return ResponseEntity.ok(mercadoriaService.reativar(id));
    }
}
