package com.br.authorizer.factory;

import com.br.authorizer.dto.CartaoDTO;
import com.br.authorizer.entity.CartaoEntity;

import java.math.BigDecimal;

public class CartaoFactory {

    private static final BigDecimal SALDO_INICIAL = BigDecimal.valueOf(500.00);

    private CartaoFactory() {
    }

    public static CartaoEntity criarCartao(CartaoDTO dto) {
        return new CartaoEntity(dto.getNumeroCartao(), dto.getSenha(), SALDO_INICIAL);
    }
}