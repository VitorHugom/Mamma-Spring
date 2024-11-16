package com.example.mamma_erp.entities.movimento_estoque;

import java.math.BigDecimal;

public record MovimentoEstoqueResponseDTO(
        Long id,
        Long idPedido,
        Integer idRecebimentoMercadoria,
        Long idProduto,
        BigDecimal qtd
) {
    public MovimentoEstoqueResponseDTO(MovimentoEstoque movimentoEstoque) {
        this(
                movimentoEstoque.getId(),
                movimentoEstoque.getItemPedido() != null ? movimentoEstoque.getItemPedido().getPedido().getId() : null,
                movimentoEstoque.getItemRecebimentoMercadoria() != null ? movimentoEstoque.getItemRecebimentoMercadoria().getRecebimento().getIdRecebimento() : null,
                movimentoEstoque.getProduto().getId(),
                movimentoEstoque.getQtd()
        );
    }
}
