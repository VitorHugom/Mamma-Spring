package com.example.lite_erp.entities.grupo_produto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grupo_produtos")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GrupoProdutos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
}
