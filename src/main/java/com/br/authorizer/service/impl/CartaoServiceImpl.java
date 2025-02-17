package com.br.authorizer.service.impl;

import com.br.authorizer.dto.CartaoDTO;
import com.br.authorizer.dto.CartaoResponseDTO;
import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.factory.CartaoFactory;
import com.br.authorizer.repository.CartaoRepository;
import com.br.authorizer.service.CartaoService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CartaoServiceImpl implements CartaoService {

    private final CartaoRepository cartaoRepository;

    public CartaoServiceImpl(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Transactional
    public CartaoResponseDTO createCartao(CartaoDTO cartaoDTO) {
        CartaoEntity cartao = cartaoRepository.findById(cartaoDTO.getNumeroCartao())
                .orElseGet(() -> cartaoRepository.save(CartaoFactory.criarCartao(cartaoDTO)));

        return new CartaoResponseDTO(cartao.getNumeroCartao(), cartao.getSaldo());
    }

    public CartaoResponseDTO getBalance(String numeroCartao) {
        CartaoEntity cartao = cartaoRepository.findById(numeroCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new CartaoResponseDTO(cartao.getNumeroCartao(), cartao.getSaldo());
    }
}