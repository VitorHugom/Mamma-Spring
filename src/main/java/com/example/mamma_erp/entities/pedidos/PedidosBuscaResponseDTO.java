package com.example.mamma_erp.entities.pedidos;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PedidosBuscaResponseDTO(
        Long id,
        String nomeCliente,
        String nomeVendedor,
        LocalDateTime dataEmissao,
        LocalDate dataEntrega,
        String status
) {
    public PedidosBuscaResponseDTO(Long id, String nomeCliente, String nomeVendedor,
                                   LocalDateTime dataEmissao, LocalDate dataEntrega, String status) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.nomeVendedor = nomeVendedor;
        this.dataEmissao = dataEmissao;
        this.dataEntrega = dataEntrega;
        this.status = status;
    }
}
