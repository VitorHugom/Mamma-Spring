package com.example.mamma_erp.entities.fornecedores;

import com.example.mamma_erp.entities.cidades.Cidades;
import com.example.mamma_erp.entities.vendedores.Vendedores;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FornecedoresRequestDTO(
        String tipoPessoa,
        String cpf,
        String cnpj,
        String nomeFantasia,
        String razaoSocial,
        String cep,
        String endereco,
        String complemento,
        String numero,
        String bairro,
        Cidades cidade,
        String celular,
        String telefone,
        String email,
        Boolean estadoInscricaoEstadual,
        String inscricaoEstadual,
        String observacao,
        Boolean status,
        LocalDate dataCadastro
) {

}
