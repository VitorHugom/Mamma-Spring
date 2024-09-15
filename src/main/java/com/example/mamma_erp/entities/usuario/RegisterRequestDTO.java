package com.example.mamma_erp.entities.usuario;

public record RegisterRequestDTO(String nomeUsuario, String email, String senha, Integer categoria_id, String telefone) {
}
