package com.avaliacao.service;

import com.avaliacao.dto.FornecedorRequestDTO;
import com.avaliacao.exception.ConflitoException;
import com.avaliacao.exception.RecursoNaoEncontradoException;
import com.avaliacao.model.Fornecedor;
import com.avaliacao.model.Mercadoria;
import com.avaliacao.repository.FornecedorRepository;
import com.avaliacao.repository.MercadoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final MercadoriaRepository mercadoriaRepository;

    public FornecedorService(
            FornecedorRepository fornecedorRepository,
            MercadoriaRepository mercadoriaRepository
    ) {
        this.fornecedorRepository = fornecedorRepository;
        this.mercadoriaRepository = mercadoriaRepository;
    }

    @Transactional
    public Fornecedor criar(FornecedorRequestDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        preencherFornecedor(fornecedor, dto);
        return fornecedorRepository.save(fornecedor);
    }

    @Transactional(readOnly = true)
    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Fornecedor buscarPorId(Long id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fornecedor não encontrado"));
    }

    @Transactional
    public Fornecedor atualizar(Long id, FornecedorRequestDTO dto) {
        Fornecedor fornecedor = buscarPorId(id);
        preencherFornecedor(fornecedor, dto);
        return fornecedorRepository.save(fornecedor);
    }

    @Transactional
    public void deletar(Long id) {
        Fornecedor fornecedor = buscarPorId(id);

        if (mercadoriaRepository.existsByFornecedorId(id)) {
            throw new ConflitoException(
                    "Não é possível excluir fornecedor com mercadorias vinculadas"
            );
        }

        fornecedorRepository.delete(fornecedor);
    }

    /**
     * Retorna TODAS as mercadorias do fornecedor, independente do estoque.
     * Requisito 7 do trabalho: "tela que mostre todas as mercadorias de um determinado fornecedor".
     */
    @Transactional(readOnly = true)
    public List<Mercadoria> listarMercadoriasDoFornecedor(Long id) {
        buscarPorId(id);
        return mercadoriaRepository.findByFornecedorId(id);
    }

    private void preencherFornecedor(Fornecedor fornecedor, FornecedorRequestDTO dto) {
        fornecedor.setNome(dto.getNome().trim());
        fornecedor.setTelefone(dto.getTelefone().trim());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setEndereco(dto.getEndereco().trim());
        fornecedor.setCidade(dto.getCidade());
        fornecedor.setUf(dto.getUf());
        fornecedor.setBairro(dto.getBairro());
        fornecedor.setObservacoes(dto.getObservacoes());
        fornecedor.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
    }
}
