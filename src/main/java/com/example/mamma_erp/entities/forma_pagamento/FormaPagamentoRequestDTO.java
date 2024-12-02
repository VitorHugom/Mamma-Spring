package com.example.mamma_erp.entities.forma_pagamento;

import java.util.List;

public record FormaPagamentoRequestDTO(String descricao, List<Integer> diasFormaPagamento) {
}
