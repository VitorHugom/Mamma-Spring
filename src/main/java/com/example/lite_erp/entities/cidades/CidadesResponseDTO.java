package com.example.lite_erp.entities.cidades;

public record CidadesResponseDTO(Long id, String nome, String estado, String codigoIbge) {
    public CidadesResponseDTO(Cidades cidades){
        this(cidades.getId(), cidades.getNome(), cidades.getEstado(), cidades.getCodigoIbge());
    }
}
