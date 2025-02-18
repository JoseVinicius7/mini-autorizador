package com.br.authorizer.service.impl;

import com.br.authorizer.dto.CartaoDTO;
import com.br.authorizer.dto.CartaoResponseDTO;
import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.factory.CartaoFactory;
import com.br.authorizer.handler.exception.CartaoDuplicadoException;
import com.br.authorizer.handler.exception.CartaoNaoEncontradoException;
import com.br.authorizer.repository.CartaoRepository;
import com.br.authorizer.service.CartaoService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CartaoServiceImpl implements CartaoService {

    private static final Logger logger = LogManager.getLogger(CartaoServiceImpl.class);
    private final CartaoRepository cartaoRepository;

    public CartaoServiceImpl(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Transactional
    public CartaoResponseDTO createCartao(CartaoDTO cartaoDTO) {
        logger.info("Iniciando criação do cartão: {}", cartaoDTO.getNumeroCartao());

        if (cartaoRepository.existsById(cartaoDTO.getNumeroCartao())) {
            logger.warn("Tentativa de criar cartão que já existe: {}", cartaoDTO.getNumeroCartao());
            throw new CartaoDuplicadoException();
        }

        CartaoEntity cartao = CartaoFactory.criarCartao(cartaoDTO);
        cartao = cartaoRepository.save(cartao);

        logger.info("Cartão criado com sucesso: {}", cartao.getNumeroCartao());

        return new CartaoResponseDTO(cartao.getNumeroCartao(), cartao.getSaldo());
    }

    public CartaoResponseDTO getBalance(String numeroCartao) {
        logger.info("Consultando saldo para o cartão: {}", numeroCartao);

        CartaoEntity cartao = cartaoRepository.findById(numeroCartao)
                .orElseThrow(() -> {
                    logger.error("Cartão não encontrado: {}", numeroCartao);
                    return new CartaoNaoEncontradoException(numeroCartao);
                });

        logger.info("Saldo consultado com sucesso para o cartão: {}", numeroCartao);

        return new CartaoResponseDTO(cartao.getNumeroCartao(), cartao.getSaldo());
    }
}
