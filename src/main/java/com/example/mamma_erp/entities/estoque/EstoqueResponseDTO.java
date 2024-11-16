package com.example.mamma_erp.entities.estoque;

import java.math.BigDecimal;

public record EstoqueResponseDTO(Long id, Long idProduto, String nomeProduto, BigDecimal qtdEstoque) {
    public EstoqueResponseDTO(Estoque estoque) {
        this(
                estoque.getId(),
                estoque.getProduto().getId(),
                estoque.getProduto().getDescricao(),
                estoque.getQtdEstoque()
        );
    }
}
