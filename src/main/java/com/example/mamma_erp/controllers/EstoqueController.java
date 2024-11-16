package com.example.mamma_erp.controllers;

import com.example.mamma_erp.entities.estoque.*;
import com.example.mamma_erp.services.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoque")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    // Endpoint para atualizar o estoque
    @PostMapping("/atualizar")
    public ResponseEntity<EstoqueResponseDTO> atualizarEstoque(@RequestBody EstoqueRequestDTO dto) {
        EstoqueResponseDTO response = estoqueService.atualizarEstoque(dto);
        return ResponseEntity.ok(response);
    }

    // Endpoint para obter o estoque de um produto
    @GetMapping("/produto/{idProduto}")
    public ResponseEntity<EstoqueResponseDTO> obterEstoquePorProduto(@PathVariable Long idProduto) {
        EstoqueResponseDTO response = estoqueService.obterEstoquePorProduto(idProduto);
        return ResponseEntity.ok(response);
    }
}
