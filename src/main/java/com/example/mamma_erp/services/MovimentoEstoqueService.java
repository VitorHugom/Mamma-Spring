package com.example.mamma_erp.services;

import com.example.mamma_erp.entities.itens_pedido.ItensPedidoRepository;
import com.example.mamma_erp.entities.itens_recebimento_mercadorias.ItensRecebimentoMercadoriasRepository;
import com.example.mamma_erp.entities.movimento_estoque.*;
import com.example.mamma_erp.entities.itens_pedido.ItensPedido;
import com.example.mamma_erp.entities.itens_recebimento_mercadorias.ItensRecebimentoMercadorias;
import com.example.mamma_erp.entities.produtos.Produtos;
import com.example.mamma_erp.entities.produtos.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MovimentoEstoqueService {

    @Autowired
    private MovimentoEstoqueRepository movimentoEstoqueRepository;

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private ItensRecebimentoMercadoriasRepository itensRecebimentoMercadoriasRepository;

    @Autowired
    private ItensPedidoRepository itensPedidoRepository;

    // Método para criar um movimento avulso de estoque
    @Transactional
    public MovimentoEstoqueResponseDTO criarMovimentoAvulso(MovimentoEstoqueRequestDTO dto) {
        MovimentoEstoque movimento = new MovimentoEstoque();

        // Buscar o produto pelo ID
        Produtos produto = produtosRepository.findById(dto.idProduto())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        movimento.setProduto(produto);
        movimento.setQtd(dto.qtd());
        movimento.setDataMovimentacao(dto.dataMovimentacao());

        // Verificar se o idItemPedido foi informado e buscar o ItensPedido
        if (dto.idItemPedido() != null) {
            ItensPedido itemPedido = itensPedidoRepository.findById(dto.idItemPedido())
                    .orElseThrow(() -> new RuntimeException("Item do Pedido não encontrado"));
            movimento.setItemPedido(itemPedido);
        }

        // Verificar se o idItemRecebimentoMercadoria foi informado e buscar o ItensRecebimentoMercadorias
        if (dto.idItemRecebimentoMercadoria() != null) {
            ItensRecebimentoMercadorias itemRecebimento = itensRecebimentoMercadoriasRepository.findById(dto.idItemRecebimentoMercadoria())
                    .orElseThrow(() -> new RuntimeException("Item de Recebimento de Mercadoria não encontrado"));
            movimento.setItemRecebimentoMercadoria(itemRecebimento);
        }

        // Salvar o movimento de estoque
        movimentoEstoqueRepository.save(movimento);
        return new MovimentoEstoqueResponseDTO(movimento);
    }

    // Método para registrar movimento de estoque a partir de um item de pedido
    @Transactional
    public void registrarMovimentoPorPedido(ItensPedido itemPedido) {
        Produtos produto = buscarProduto(itemPedido.getProduto().getId());

        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setItemPedido(itemPedido);
        movimento.setProduto(produto);
        movimento.setQtd(new BigDecimal(itemPedido.getQuantidade()).negate()); // Subtrai do estoque
        movimento.setDataMovimentacao(LocalDateTime.now());

        movimentoEstoqueRepository.save(movimento);
    }

    // Método para registrar movimento de estoque a partir de um item de recebimento de mercadoria
    @Transactional
    public void registrarMovimentoPorRecebimento(ItensRecebimentoMercadorias itemRecebimento) {
        Produtos produto = buscarProduto(itemRecebimento.getProduto().getId());

        MovimentoEstoque movimento = new MovimentoEstoque();
        movimento.setItemRecebimentoMercadoria(itemRecebimento);
        movimento.setProduto(produto);
        movimento.setQtd(itemRecebimento.getQuantidade()); // Adiciona ao estoque
        movimento.setDataMovimentacao(LocalDateTime.now());

        movimentoEstoqueRepository.save(movimento);
    }

    private Produtos buscarProduto(Long idProduto) {
        return produtosRepository.findById(idProduto)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    }
}
