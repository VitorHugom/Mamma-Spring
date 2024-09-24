package com.example.mamma_erp.entities.pedidos;

import com.example.mamma_erp.entities.clientes.Clientes;
import com.example.mamma_erp.entities.periodos_entrega.PeriodosEntrega;
import com.example.mamma_erp.entities.tipos_cobranca.TiposCobranca;
import com.example.mamma_erp.entities.vendedores.Vendedores;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PedidosResponseDTO(Long id, Clientes cliente, Vendedores vendedor, LocalDateTime dataEmissao, LocalDate dataEntrega, PeriodosEntrega periodoEntrega, BigDecimal valorTotal, String status, TiposCobranca tipoCobranca, LocalDateTime ultimaAtualizacao) {
    public PedidosResponseDTO(Pedidos pedidos) {
        this(pedidos.getId(), pedidos.getCliente(), pedidos.getVendedor(), pedidos.getDataEmissao(), pedidos.getDataEntrega(), pedidos.getPeriodoEntrega(), pedidos.getValorTotal(), pedidos.getStatus(), pedidos.getTipoCobranca(), pedidos.getUltimaAtualizacao());
    }
}
