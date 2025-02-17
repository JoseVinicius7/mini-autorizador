package com.br.authorizer.service;

import com.br.authorizer.entity.CartaoEntity;

import java.math.BigDecimal;

public interface CartaoService {

    public CartaoEntity createCartao(CartaoEntity cartao);

    public BigDecimal getBalance(String numeroCartao);

}
