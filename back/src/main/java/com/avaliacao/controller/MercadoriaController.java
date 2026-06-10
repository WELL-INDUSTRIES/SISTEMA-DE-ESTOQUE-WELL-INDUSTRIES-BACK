package com.avaliacao.controller;

import com.avaliacao.dto.MercadoriaRequestDTO;
import com.avaliacao.model.Mercadoria;
import com.avaliacao.service.MercadoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<Mercadoria>> listarTodosComEstoque() {
        return ResponseEntity.ok(mercadoriaService.listarTodosComEstoque());
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        mercadoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
