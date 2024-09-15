package com.example.lite_erp.entities.cidades;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadesRepository extends JpaRepository<Cidades, Long> {
    Page<Cidades> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Optional<Cidades> findByCodigoIbge(String codigoIbge);
}
