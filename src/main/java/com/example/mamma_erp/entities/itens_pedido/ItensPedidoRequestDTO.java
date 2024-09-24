package com.example.mamma_erp.entities.itens_pedido;

import java.math.BigDecimal;

public record ItensPedidoRequestDTO(Long idPedido, Long idProduto, BigDecimal preco, Integer quantidade) {
}
