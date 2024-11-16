package com.example.mamma_erp.entities.itens_recebimento_mercadorias;

import java.math.BigDecimal;

public record ItensRecebimentoMercadoriasRequestDTO(Integer idRecebimento, Long idProduto, BigDecimal quantidade, BigDecimal valorUnitario) {
}
