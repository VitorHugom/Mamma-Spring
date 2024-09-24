package com.example.mamma_erp.entities.tipos_cobranca;

public record TiposCobrancaResponseDTO(Long id, String descricao) {
    public TiposCobrancaResponseDTO(TiposCobranca tiposCobranca) {
        this(tiposCobranca.getId(), tiposCobranca.getDescricao());
    }
}
