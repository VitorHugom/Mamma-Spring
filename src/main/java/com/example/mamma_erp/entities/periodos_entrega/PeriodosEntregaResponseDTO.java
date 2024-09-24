package com.example.mamma_erp.entities.periodos_entrega;

import java.time.LocalTime;

public record PeriodosEntregaResponseDTO(Long id, String descricao, LocalTime horarioInicio, LocalTime horarioFim) {
    public PeriodosEntregaResponseDTO(PeriodosEntrega periodoEntrega) {
        this(periodoEntrega.getId(), periodoEntrega.getDescricao(), periodoEntrega.getHorarioInicio(), periodoEntrega.getHorarioFim());
    }
}
