package com.example.mamma_erp.entities.pedidos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedidos, Long> {
    List<Pedidos> findByStatus(String status);

    // Método para buscar pedidos em status "aguardando" paginados por data de entrega
    Page<Pedidos> findByStatusAndDataEntregaBetween(String status, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
