package com.example.mamma_erp.services;

import com.example.mamma_erp.entities.clientes.ClientesRepository;
import com.example.mamma_erp.entities.pedidos.Pedidos;
import com.example.mamma_erp.entities.pedidos.PedidosRepository;
import com.example.mamma_erp.entities.pedidos.PedidosRequestDTO;
import com.example.mamma_erp.entities.clientes.Clientes;
import com.example.mamma_erp.entities.periodos_entrega.PeriodosEntregaRepository;
import com.example.mamma_erp.entities.tipos_cobranca.TiposCobrancaRepository;
import com.example.mamma_erp.entities.vendedores.Vendedores;
import com.example.mamma_erp.entities.periodos_entrega.PeriodosEntrega;
import com.example.mamma_erp.entities.tipos_cobranca.TiposCobranca;
import com.example.mamma_erp.entities.vendedores.VendedoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidosService {

    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private VendedoresRepository vendedoresRepository;

    @Autowired
    private PeriodosEntregaRepository periodosEntregaRepository;

    @Autowired
    private TiposCobrancaRepository tiposCobrancaRepository;

    // Método para listar todos os pedidos
    public List<Pedidos> listarTodos() {
        return pedidosRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    // Método para buscar um pedido pelo ID
    public Optional<Pedidos> buscarPorId(Long id) {
        return pedidosRepository.findById(id);
    }

    // Método para criar um novo pedido
    public Pedidos criarPedido(PedidosRequestDTO dto) {
        // Busca as entidades pelos seus IDs
        Clientes cliente = clientesRepository.findById(dto.idCliente())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Vendedores vendedor = vendedoresRepository.findById(dto.idVendedor())
                .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));

        PeriodosEntrega periodoEntrega = periodosEntregaRepository.findById(dto.idPeriodoEntrega())
                .orElseThrow(() -> new RuntimeException("Período de entrega não encontrado"));

        TiposCobranca tipoCobranca = tiposCobrancaRepository.findById(dto.idTipoCobranca())
                .orElseThrow(() -> new RuntimeException("Tipo de cobrança não encontrado"));

        Pedidos pedido = new Pedidos(
                null,
                cliente,
                vendedor,
                dto.dataEmissao(),
                dto.dataEntrega(),
                periodoEntrega,
                dto.valorTotal(),
                dto.status(),
                tipoCobranca,
                LocalDateTime.now()  // Define a data de última atualização como o horário atual
        );
        return pedidosRepository.save(pedido);
    }

    // Método para atualizar um pedido existente
    public Optional<Pedidos> atualizarPedido(Long id, PedidosRequestDTO dto) {
        return pedidosRepository.findById(id).map(pedido -> {
            Clientes cliente = clientesRepository.findById(dto.idCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            Vendedores vendedor = vendedoresRepository.findById(dto.idVendedor())
                    .orElseThrow(() -> new RuntimeException("Vendedor não encontrado"));

            PeriodosEntrega periodoEntrega = periodosEntregaRepository.findById(dto.idPeriodoEntrega())
                    .orElseThrow(() -> new RuntimeException("Período de entrega não encontrado"));

            TiposCobranca tipoCobranca = tiposCobrancaRepository.findById(dto.idTipoCobranca())
                    .orElseThrow(() -> new RuntimeException("Tipo de cobrança não encontrado"));

            pedido.setCliente(cliente);
            pedido.setVendedor(vendedor);
            pedido.setDataEmissao(dto.dataEmissao());
            pedido.setDataEntrega(dto.dataEntrega());
            pedido.setPeriodoEntrega(periodoEntrega);
            pedido.setValorTotal(dto.valorTotal());
            pedido.setStatus(dto.status());
            pedido.setTipoCobranca(tipoCobranca);
            pedido.setUltimaAtualizacao(LocalDateTime.now());

            return pedidosRepository.save(pedido);
        });
    }

    public List<Pedidos> getPedidosAguardando() {
        return pedidosRepository.findByStatus("aguardando");
    }

    // Busca pedidos por mês
    public Map<String, Map<String, List<Pedidos>>> getPedidosPorMes(int ano, int mes, int page, int size) {
        YearMonth yearMonth = YearMonth.of(ano, mes);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        Page<Pedidos> pedidosPage = pedidosRepository.findByStatusAndDataEntregaBetween(
                "aguardando", startDate, endDate, PageRequest.of(page, size)
        );

        // Organiza os pedidos por data de entrega e período
        return pedidosPage.getContent().stream()
                .collect(Collectors.groupingBy(
                        pedido -> pedido.getDataEntrega().toString(),
                        Collectors.groupingBy(
                                pedido -> pedido.getPeriodoEntrega().getDescricao()
                        )
                ));
    }

    // Método para deletar um pedido
    public boolean deletarPedido(Long id) {
        if (pedidosRepository.existsById(id)) {
            pedidosRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

