package com.example.mamma_erp.entities.pedidos;

import com.example.mamma_erp.entities.clientes.Clientes;
import com.example.mamma_erp.entities.periodos_entrega.PeriodosEntrega;
import com.example.mamma_erp.entities.tipos_cobranca.TiposCobranca;
import com.example.mamma_erp.entities.vendedores.Vendedores;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes cliente;

    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private Vendedores vendedor;

    @Column(name = "data_emissao", nullable = false)
    private LocalDateTime dataEmissao;

    @Column(name = "data_entrega", nullable = false)
    private LocalDate dataEntrega;

    @ManyToOne
    @JoinColumn(name = "id_periodo_entrega", nullable = false)
    private PeriodosEntrega periodoEntrega;

    @Column(name = "valor_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_tipo_cobranca", nullable = false)
    private TiposCobranca tipoCobranca;

    @Column(name = "ultima_atualizacao", nullable = false)
    private LocalDateTime ultimaAtualizacao;
}
