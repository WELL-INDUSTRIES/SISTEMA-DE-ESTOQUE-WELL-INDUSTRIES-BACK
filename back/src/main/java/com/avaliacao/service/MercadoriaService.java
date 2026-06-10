package com.avaliacao.service;

import com.avaliacao.dto.MercadoriaRequestDTO;
import com.avaliacao.exception.RecursoNaoEncontradoException;
import com.avaliacao.exception.RegraNegocioException;
import com.avaliacao.model.Fornecedor;
import com.avaliacao.model.Mercadoria;
import com.avaliacao.repository.FornecedorRepository;
import com.avaliacao.repository.MercadoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<Mercadoria> listarTodosComEstoque() {
        return mercadoriaRepository.findByQuantidadeGreaterThan(0);
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
    public void deletar(Long id) {
        Mercadoria mercadoria = buscarPorId(id);
        mercadoriaRepository.delete(mercadoria);
    }

    private void preencherMercadoria(Mercadoria mercadoria, MercadoriaRequestDTO dto, Fornecedor fornecedor) {
        mercadoria.setNome(dto.getNome().trim());
        mercadoria.setDescricao(dto.getDescricao());
        mercadoria.setCategoria(dto.getCategoria());
        mercadoria.setPreco(dto.getPreco());
        mercadoria.setQuantidade(dto.getQuantidade());
        mercadoria.setDataValidade(dto.getDataValidade());
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
