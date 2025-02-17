package com.br.authorizer.dto;

import java.math.BigDecimal;

public class CartaoResponseDTO {

    public CartaoResponseDTO(String numeroCartao, BigDecimal saldo) {
        this.numeroCartao = numeroCartao;
        this.saldo = saldo;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    private String numeroCartao;
    private BigDecimal saldo;
}