package com.example.mamma_erp.entities.periodos_entrega;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodosEntregaRepository extends JpaRepository<PeriodosEntrega, Long> {
    Page<PeriodosEntregaResponseDTO> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);
}
