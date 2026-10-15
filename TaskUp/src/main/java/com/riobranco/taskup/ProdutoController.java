package com.riobranco.taskup;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    public record AjusteEstoque(Integer quantidade) {}

    @Autowired
    private ProdutoService produtoService;

    // CREATE
    @PostMapping
    public Produto criar(@RequestBody Produto produto) {
        return produtoService.criar(produto);
    }

    // READ - listar todos ou pesquisar por nome (GET /produtos?nome=agua)
    @GetMapping
    public List<Produto> listar(@RequestParam(required = false) String nome) {
        if (nome != null && !nome.isBlank()) {
            return produtoService.pesquisarPorNome(nome);
        }
        return produtoService.listarTodos();
    }

    // READ - estoque baixo (GET /produtos/estoque-baixo?limite=5)
    @GetMapping("/estoque-baixo")
    public List<Produto> estoqueBaixo(@RequestParam(defaultValue = "5") Integer limite) {
        return produtoService.estoqueBaixo(limite);
    }

    // READ - buscar um por id
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        return produtoService.atualizar(id, produtoAtualizado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // AJUSTE DE ESTOQUE (PATCH /produtos/{id}/estoque  body: {"quantidade": 10} ou {"quantidade": -3})
    @PatchMapping("/{id}/estoque")
    public ResponseEntity<?> ajustarEstoque(@PathVariable Long id, @RequestBody AjusteEstoque ajuste) {
        try {
            Optional<Produto> resultado = produtoService.ajustarEstoque(id, ajuste.quantidade());
            if (resultado.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(resultado.get());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // INATIVAR (soft delete)
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<Produto> inativar(@PathVariable Long id) {
        return produtoService.alterarAtivo(id, false)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // REATIVAR
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Produto> ativar(@PathVariable Long id) {
        return produtoService.alterarAtivo(id, true)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!produtoService.deletar(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}