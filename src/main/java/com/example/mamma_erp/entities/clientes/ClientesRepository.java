package com.example.mamma_erp.entities.clientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientesRepository extends JpaRepository <Clientes, Long> {

    Page<Clientes> findByRazaoSocialContainingIgnoreCase(String nome, Pageable pageable);

    @Query("SELECT new com.example.mamma_erp.entities.clientes.ClientesBuscaResponseDTO(" +
            "c.id, c.razaoSocial, v.nome) " +
            "FROM Clientes c " +
            "JOIN c.vendedor v " +
            "ORDER BY c.id DESC")
    Page<ClientesBuscaResponseDTO> findPedidosForBusca(Pageable pageable);

    @Query("SELECT new com.example.mamma_erp.entities.clientes.ClientesBuscaResponseDTO(" +
            "c.id, c.razaoSocial, v.nome) " +
            "FROM Clientes c " +
            "JOIN c.vendedor v " +
            "WHERE LOWER(c.razaoSocial) LIKE LOWER(:razaoSocial) " +
            "ORDER BY c.id DESC")
    Page<ClientesBuscaResponseDTO> findPedidosForBuscaByClienteRazaoSocial(@Param("razaoSocial") String razaoSocial, Pageable pageable);
}
