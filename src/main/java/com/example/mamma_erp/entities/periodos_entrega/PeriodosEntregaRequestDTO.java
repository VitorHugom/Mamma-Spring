package com.example.mamma_erp.entities.periodos_entrega;

import java.time.LocalTime;

public record PeriodosEntregaRequestDTO(String descricao, LocalTime horarioInicio, LocalTime horarioFim) {
}
