package com.br.authorizer.service.impl;

import com.br.authorizer.dto.TransacaoDTO;
import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.repository.CartaoRepository;
import com.br.authorizer.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private final CartaoRepository cartaoRepository;

    public TransacaoServiceImpl(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Transactional
    public String processTransaction(TransacaoDTO transacaoDTO) {
        CartaoEntity cartao = cartaoRepository.findById(transacaoDTO.getNumeroCartao())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        if (!cartao.getSenha().equals(transacaoDTO.getSenhaCartao())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha inválida");
        }

        if (cartao.getSaldo().compareTo(transacaoDTO.getValor()) < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Saldo insuficiente");
        }

        cartao.setSaldo(cartao.getSaldo().subtract(transacaoDTO.getValor()));
        cartaoRepository.save(cartao);

        return "OK";
    }
}
