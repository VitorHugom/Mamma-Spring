package com.example.mamma_erp.services;

import com.example.mamma_erp.entities.estoque.*;
import com.example.mamma_erp.entities.produtos.Produtos;
import com.example.mamma_erp.entities.produtos.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutosRepository produtosRepository;

    @Transactional
    public EstoqueResponseDTO atualizarEstoque(EstoqueRequestDTO dto) {
        // Buscar o produto
        Produtos produto = produtosRepository.findById(dto.idProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Buscar o estoque existente ou criar um novo
        Estoque estoque = estoqueRepository.findByProdutoId(dto.idProduto())
                .orElseGet(() -> new Estoque(null, produto, BigDecimal.ZERO));

        // Atualizar a quantidade em estoque
        estoque.setQtdEstoque(estoque.getQtdEstoque().add(dto.qtdEstoque()));
        estoqueRepository.save(estoque);

        return new EstoqueResponseDTO(estoque);
    }

    public EstoqueResponseDTO obterEstoquePorProduto(Long idProduto) {
        Estoque estoque = estoqueRepository.findByProdutoId(idProduto)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto"));
        return new EstoqueResponseDTO(estoque);
    }
}
