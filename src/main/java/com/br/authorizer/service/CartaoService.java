package com.br.authorizer.service;

import com.br.authorizer.dto.TransacaoDTO;
import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    public CartaoEntity criarCartao(CartaoEntity cartao) {
        return cartaoRepository.findById(cartao.getNumeroCartao()).orElseGet(() -> cartaoRepository.save(cartao));
    }

    public BigDecimal obterSaldo(String numeroCartao) {
        return cartaoRepository.findById(numeroCartao)
                .map(CartaoEntity::getSaldo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String processarTransacao(TransacaoDTO transacao) {
        CartaoEntity cartao = cartaoRepository.findById(transacao.getNumeroCartao())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "CARTAO_INEXISTENTE"));

        if (!cartao.getSenha().equals(transacao.getSenhaCartao())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "SENHA_INVALIDA");
        }

        if (cartao.getSaldo().compareTo(transacao.getValor()) < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "SALDO_INSUFICIENTE");
        }

        cartao.setSaldo(cartao.getSaldo().subtract(transacao.getValor()));
        cartaoRepository.save(cartao);

        return "OK";
    }
}
