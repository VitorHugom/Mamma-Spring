package com.example.mamma_erp.controllers;

import com.example.mamma_erp.entities.produtos.Produtos;
import com.example.mamma_erp.entities.produtos.ProdutosRequestDTO;
import com.example.mamma_erp.entities.produtos.ProdutosResponseDTO;
import com.example.mamma_erp.services.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    private ProdutosService produtosService;

    @GetMapping
    public List<ProdutosResponseDTO> listarTodos() {
        List<Produtos> produtos = produtosService.listarTodos();
        return produtos.stream().map(ProdutosResponseDTO::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutosResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<Produtos> produto = produtosService.buscarPorId(id);
        return produto.map(value -> ResponseEntity.ok(new ProdutosResponseDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProdutosResponseDTO> criarProduto(@RequestBody ProdutosRequestDTO dto) {
        Produtos novoProduto = produtosService.criarProduto(dto);
        return ResponseEntity.ok(new ProdutosResponseDTO(novoProduto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutosResponseDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutosRequestDTO dto) {
        Optional<Produtos> produtoAtualizado = produtosService.atualizarProduto(id, dto);
        return produtoAtualizado.map(value -> ResponseEntity.ok(new ProdutosResponseDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        if (produtosService.deletarProduto(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProdutosResponseDTO>> getProdutosByName(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("descricao").ascending());
        Page<Produtos> produtosPage;

        if (nome != null && !nome.isEmpty()) {
            produtosPage = produtosService.findByNomeContainingIgnoreCase(nome, pageable);
        } else {
            produtosPage = produtosService.listarTodosPaginado(pageable);
        }

        List<ProdutosResponseDTO> produtosDTO = produtosPage
                .getContent()
                .stream()
                .map(ProdutosResponseDTO::new)
                .toList();

        return ResponseEntity.ok(produtosDTO);
    }
}
