package com.example.mamma_erp.controllers;

import com.example.mamma_erp.entities.itens_pedido.ItensPedido;
import com.example.mamma_erp.entities.itens_pedido.ItensPedidoRequestDTO;
import com.example.mamma_erp.entities.itens_pedido.ItensPedidoResponseDTO;
import com.example.mamma_erp.entities.pedidos.Pedidos;
import com.example.mamma_erp.entities.pedidos.PedidosRequestDTO;
import com.example.mamma_erp.entities.pedidos.PedidosResponseDTO;
import com.example.mamma_erp.services.PedidosService;
import com.example.mamma_erp.services.ItensPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PedidosController {

    @Autowired
    private PedidosService pedidosService;

    @Autowired
    private ItensPedidoService itensPedidoService;

    @GetMapping
    public List<PedidosResponseDTO> listarTodos() {
        List<Pedidos> pedidos = pedidosService.listarTodos();
        return pedidos.stream().map(PedidosResponseDTO::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidosResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<Pedidos> pedido = pedidosService.buscarPorId(id);
        return pedido.map(value -> ResponseEntity.ok(new PedidosResponseDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidosResponseDTO> criarPedido(@RequestBody PedidosRequestDTO dto) {
        Pedidos novoPedido = pedidosService.criarPedido(dto);
        return ResponseEntity.ok(new PedidosResponseDTO(novoPedido));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidosResponseDTO> atualizarPedido(@PathVariable Long id, @RequestBody PedidosRequestDTO dto) {
        Optional<Pedidos> pedidoAtualizado = pedidosService.atualizarPedido(id, dto);
        return pedidoAtualizado.map(value -> ResponseEntity.ok(new PedidosResponseDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        if (pedidosService.deletarPedido(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para buscar todos os pedidos em status "aguardando"
    @GetMapping("/aguardando")
    public ResponseEntity<List<Pedidos>> getPedidosAguardando() {
        List<Pedidos> pedidos = pedidosService.getPedidosAguardando();
        return ResponseEntity.ok(pedidos);
    }

    // Endpoint para buscar pedidos organizados por data de entrega e período
    // Endpoint para buscar pedidos por mês
    @GetMapping("/agenda/{ano}/{mes}")
    public ResponseEntity<Map<String, Map<String, List<Pedidos>>>> getPedidosPorMes(
            @PathVariable int ano,
            @PathVariable int mes,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Map<String, List<Pedidos>>> pedidosOrganizados = pedidosService.getPedidosPorMes(ano, mes, page, size);
        return ResponseEntity.ok(pedidosOrganizados);
    }

    // Métodos para itens de pedido

    @GetMapping("/{id}/itens")
    public List<ItensPedidoResponseDTO> listarItensPorPedido(@PathVariable Long id) {
        List<ItensPedido> itens = itensPedidoService.listarItensPorPedido(id);
        return itens.stream().map(ItensPedidoResponseDTO::new).toList();
    }

    @PostMapping("/{id}/itens")
    public ResponseEntity<ItensPedidoResponseDTO> adicionarItemAoPedido(@PathVariable Long id, @RequestBody ItensPedidoRequestDTO dto) {
        ItensPedido novoItem = itensPedidoService.criarItemPedido(dto);
        return ResponseEntity.ok(new ItensPedidoResponseDTO(novoItem));
    }

    @PutMapping("/itens/{idItem}")
    public ResponseEntity<ItensPedidoResponseDTO> atualizarItemPedido(@PathVariable Long idItem, @RequestBody ItensPedidoRequestDTO dto) {
        Optional<ItensPedido> itemAtualizado = itensPedidoService.atualizarItemPedido(idItem, dto);
        return itemAtualizado.map(value -> ResponseEntity.ok(new ItensPedidoResponseDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/itens/{idItensPedido}")
    public ResponseEntity<Void> deletarItemPedido(@PathVariable Long idItensPedido) {
        if (itensPedidoService.deletarItemPedido(idItensPedido)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
