package com.example.mamma_erp.entities.clientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository <Clientes, Long> {

    Page<Clientes> findByRazaoSocialContainingIgnoreCase(String nome, Pageable pageable);
}
