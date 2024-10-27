package com.example.mamma_erp.entities.pedidos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedidos, Long> {
    List<Pedidos> findByStatus(String status);

    // Método para buscar pedidos em status "aguardando" paginados por data de entrega
    Page<Pedidos> findByStatusAndDataEntregaBetween(String status, LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("SELECT new com.example.mamma_erp.entities.pedidos.PedidosBuscaResponseDTO(" +
            "p.id, p.cliente.razaoSocial, p.vendedor.nome, p.dataEmissao, p.dataEntrega, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.cliente c " +
            "JOIN p.vendedor v " +
            "ORDER BY p.dataEmissao DESC")
    Page<PedidosBuscaResponseDTO> findPedidosForBusca(Pageable pageable);

    @Query("SELECT new com.example.mamma_erp.entities.pedidos.PedidosBuscaResponseDTO(" +
            "p.id, c.razaoSocial, v.nome, p.dataEmissao, p.dataEntrega, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.cliente c " +
            "JOIN p.vendedor v " +
            "WHERE LOWER(c.razaoSocial) LIKE LOWER(:razaoSocial) " +
            "ORDER BY p.dataEmissao DESC")
    Page<PedidosBuscaResponseDTO> findPedidosForBuscaByClienteRazaoSocial(@Param("razaoSocial") String razaoSocial, Pageable pageable);
}
