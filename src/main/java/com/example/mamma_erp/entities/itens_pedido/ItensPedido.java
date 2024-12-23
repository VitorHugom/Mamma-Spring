package com.example.mamma_erp.entities.itens_pedido;

import com.example.mamma_erp.entities.pedidos.Pedidos;
import com.example.mamma_erp.entities.produtos.Produtos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "itens_pedido")
public class ItensPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedidos pedido;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produtos produto;

    private BigDecimal preco;

    private Integer quantidade;
}
