package com.br.authorizer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cartoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoEntity {

    @Id
    @Column(length = 16, nullable = false, unique = true)
    private String numeroCartao;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private BigDecimal saldo = BigDecimal.valueOf(500.00);
}
