package com.riobranco.taskup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto criar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public java.util.Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public java.util.Optional<Produto> atualizar(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setPreco(produtoAtualizado.getPreco());
                    produto.setQuantidade(produtoAtualizado.getQuantidade());
                    return produtoRepository.save(produto);
                });
    }

    public boolean deletar(Long id) {
        if (!produtoRepository.existsById(id)) {
            return false;
        }
        produtoRepository.deleteById(id);
        return true;
    }
}