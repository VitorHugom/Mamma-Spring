package com.example.mamma_erp.entities.clientes;

import com.example.mamma_erp.entities.vendedores.Vendedores;

public record ClienteSimplificadoResponseDTO(Long id, String razaoSocial, Long id_vendedor) {
}
