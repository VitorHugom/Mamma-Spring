package com.example.mamma_erp.entities.pedidos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PedidosRequestDTO(Long idCliente, Long idVendedor, LocalDateTime dataEmissao, LocalDate dataEntrega, Long idPeriodoEntrega, BigDecimal valorTotal, String status, Long idTipoCobranca) {
}
