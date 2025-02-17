package com.br.authorizer.service.impl;

import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.repository.CartaoRepository;
import com.br.authorizer.service.CartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {

    private final CartaoRepository cartaoRepository;

    public CartaoEntity createCartao(CartaoEntity cartao) {
        return cartaoRepository.findById(cartao.getNumeroCartao()).orElseGet(() -> cartaoRepository.save(cartao));
    }

    public BigDecimal getBalance(String numeroCartao) {
        return cartaoRepository.findById(numeroCartao)
                .map(CartaoEntity::getSaldo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
