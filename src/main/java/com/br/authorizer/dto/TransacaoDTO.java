package com.br.authorizer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransacaoDTO {
    private String numeroCartao;
    private String senhaCartao;
    private BigDecimal valor;
}
