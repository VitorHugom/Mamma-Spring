package com.example.mamma_erp.controllers;

import com.example.mamma_erp.entities.movimento_estoque.*;
import com.example.mamma_erp.services.MovimentoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimento-estoque")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MovimentoEstoqueController {

    @Autowired
    private MovimentoEstoqueService movimentoEstoqueService;

    // Endpoint para criar um movimento de estoque avulso
    @PostMapping("/avulso")
    public ResponseEntity<MovimentoEstoqueResponseDTO> criarMovimentoAvulso(@RequestBody MovimentoEstoqueRequestDTO dto) {
        MovimentoEstoqueResponseDTO movimento = movimentoEstoqueService.criarMovimentoAvulso(dto);
        return ResponseEntity.ok(movimento);
    }
}
