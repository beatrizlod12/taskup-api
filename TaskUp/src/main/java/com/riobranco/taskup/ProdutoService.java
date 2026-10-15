package com.riobranco.taskup;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public List<Produto> pesquisarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Produto criar(Produto produto) {
        produto.setId(null);
        produto.setAtivo(true);
        return repository.save(produto);
    }

    public Optional<Produto> atualizar(Long id, Produto dados) {
        return repository.findById(id).map(produto -> {
            produto.setNome(dados.getNome());
            produto.setPreco(dados.getPreco());
            produto.setQuantidade(dados.getQuantidade());
            return repository.save(produto);
        });
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    public Optional<Produto> ajustarEstoque(Long id, Integer quantidade) {
        if (quantidade == null) {
            throw new IllegalArgumentException("Informe a quantidade do ajuste.");
        }
        return repository.findById(id).map(produto -> {
            int atual = produto.getQuantidade() == null ? 0 : produto.getQuantidade();
            int novo = atual + quantidade;
            if (novo < 0) {
                throw new IllegalArgumentException("O estoque não pode ficar negativo.");
            }
            produto.setQuantidade(novo);
            return repository.save(produto);
        });
    }

    public Optional<Produto> alterarAtivo(Long id, boolean ativo) {
        return repository.findById(id).map(produto -> {
            produto.setAtivo(ativo);
            return repository.save(produto);
        });
    }

    public List<Produto> estoqueBaixo(Integer limite) {
        return repository.findByAtivoTrueAndQuantidadeLessThanEqual(limite);
    }
}