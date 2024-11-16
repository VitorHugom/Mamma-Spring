package com.example.mamma_erp.entities.estoque;

import java.math.BigDecimal;

public record EstoqueRequestDTO(Long idProduto, BigDecimal qtdEstoque) {
}
