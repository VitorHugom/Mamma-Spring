package com.example.mamma_erp.services;

import com.example.mamma_erp.entities.itens_pedido.ItensPedido;
import com.example.mamma_erp.entities.itens_pedido.ItensPedidoRepository;
import com.example.mamma_erp.entities.itens_pedido.ItensPedidoRequestDTO;
import com.example.mamma_erp.entities.pedidos.PedidosRepository;
import com.example.mamma_erp.entities.produtos.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItensPedidoService {

    @Autowired
    private ItensPedidoRepository itensPedidoRepository;

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private ProdutosRepository produtosRepository;

    // Listar todos os itens de um pedido específico
    public List<ItensPedido> listarItensPorPedido(Long idPedido) {
        return itensPedidoRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .filter(item -> item.getPedido().getId().equals(idPedido))
                .toList();
    }

    // Buscar um item de pedido por ID
    public Optional<ItensPedido> buscarPorId(Long id) {
        return itensPedidoRepository.findById(id);
    }

    // Criar novo item de pedido
    public ItensPedido criarItemPedido(ItensPedidoRequestDTO dto) {
        ItensPedido itemPedido = new ItensPedido(
                null,
                pedidosRepository.findById(dto.idPedido()).orElseThrow(),
                produtosRepository.findById(dto.idProduto()).orElseThrow(),
                dto.preco(),
                dto.quantidade()
        );
        return itensPedidoRepository.save(itemPedido);
    }

    // Atualizar item de pedido existente
    public Optional<ItensPedido> atualizarItemPedido(Long id, ItensPedidoRequestDTO dto) {
        return itensPedidoRepository.findById(id).map(item -> {
            item.setProduto(produtosRepository.findById(dto.idProduto()).orElseThrow());
            item.setPreco(dto.preco());
            item.setQuantidade(dto.quantidade());
            return itensPedidoRepository.save(item);
        });
    }

    // Deletar item de pedido
    public boolean deletarItemPedido(Long id) {
        if (itensPedidoRepository.existsById(id)) {
            itensPedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
