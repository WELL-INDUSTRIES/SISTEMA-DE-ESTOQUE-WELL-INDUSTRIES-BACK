package com.avaliacao.service;

import com.avaliacao.dto.MercadoriaQuantidadeDTO;
import com.avaliacao.dto.MercadoriaRequestDTO;
import com.avaliacao.exception.RecursoNaoEncontradoException;
import com.avaliacao.exception.RegraNegocioException;
import com.avaliacao.model.Fornecedor;
import com.avaliacao.model.Mercadoria;
import com.avaliacao.repository.FornecedorRepository;
import com.avaliacao.repository.MercadoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MercadoriaService {

    private final MercadoriaRepository mercadoriaRepository;
    private final FornecedorRepository fornecedorRepository;

    public MercadoriaService(
            MercadoriaRepository mercadoriaRepository,
            FornecedorRepository fornecedorRepository
    ) {
        this.mercadoriaRepository = mercadoriaRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    public Mercadoria criar(MercadoriaRequestDTO dto) {
        Fornecedor fornecedor = buscarFornecedorAtivo(dto.getFornecedorId());
        Mercadoria mercadoria = new Mercadoria();
        preencherMercadoria(mercadoria, dto, fornecedor);
        return mercadoriaRepository.save(mercadoria);
    }

    @Transactional(readOnly = true)
    public Page<Mercadoria> listarTodosComEstoque(Pageable pageable) {
        return mercadoriaRepository.findByAtivoTrueAndQuantidadeGreaterThan(0, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Mercadoria> listarInativos(Pageable pageable) {
        return mercadoriaRepository.findByAtivoFalse(pageable);
    }

    @Transactional(readOnly = true)
    public Mercadoria buscarPorId(Long id) {
        return mercadoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Mercadoria não encontrada"));
    }

    @Transactional
    public Mercadoria atualizar(Long id, MercadoriaRequestDTO dto) {
        Mercadoria mercadoria = buscarPorId(id);
        Fornecedor fornecedor = buscarFornecedorAtivo(dto.getFornecedorId());
        preencherMercadoria(mercadoria, dto, fornecedor);
        return mercadoriaRepository.save(mercadoria);
    }

    @Transactional
    public void atualizarQuantidade(Long id, MercadoriaQuantidadeDTO dto) {
        int atualizadas = mercadoriaRepository.atualizarQuantidade(id, dto.getQuantidade());

        if (atualizadas == 0) {
            throw new RecursoNaoEncontradoException("Mercadoria não encontrada");
        }
    }

    @Transactional
    public void deletar(Long id) {
        Mercadoria mercadoria = buscarPorId(id);
        mercadoria.setAtivo(false);
        mercadoriaRepository.save(mercadoria);
    }

    @Transactional
    public Mercadoria reativar(Long id) {
        Mercadoria mercadoria = buscarPorId(id);
        mercadoria.setAtivo(true);
        return mercadoriaRepository.save(mercadoria);
    }

    private void preencherMercadoria(Mercadoria mercadoria, MercadoriaRequestDTO dto, Fornecedor fornecedor) {
        mercadoria.setNome(dto.getNome().trim());
        mercadoria.setDescricao(dto.getDescricao());
        mercadoria.setCategoria(dto.getCategoria());
        mercadoria.setPreco(dto.getPreco());
        mercadoria.setQuantidade(dto.getQuantidade());
        mercadoria.setFornecedor(fornecedor);
    }

    private Fornecedor buscarFornecedorAtivo(Long fornecedorId) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new RegraNegocioException("Fornecedor não encontrado"));

        if (Boolean.FALSE.equals(fornecedor.getAtivo())) {
            throw new RegraNegocioException("Não é possível vincular mercadoria a um fornecedor inativo");
        }

        return fornecedor;
    }
}
