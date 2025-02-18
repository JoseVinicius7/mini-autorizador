package com.br.authorizer.service.impl;

import com.br.authorizer.dto.TransacaoDTO;
import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.handler.exception.CartaoInexistenteException;
import com.br.authorizer.handler.exception.SaldoInsuficienteException;
import com.br.authorizer.handler.exception.SenhaInvalidaException;
import com.br.authorizer.repository.CartaoRepository;
import com.br.authorizer.service.TransacaoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private static final Logger logger = LogManager.getLogger(TransacaoServiceImpl.class); // Logger para rastreamento

    private final CartaoRepository cartaoRepository;

    public TransacaoServiceImpl(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Transactional
    public ResponseEntity<String> processTransaction(TransacaoDTO transacaoDTO) {
        logger.info("Iniciando transação para o cartão: {}", transacaoDTO.getNumeroCartao());

        CartaoEntity cartao = cartaoRepository.findByNumeroCartaoComLock(transacaoDTO.getNumeroCartao())
                .orElseThrow(() -> {
                    logger.error("Cartão não encontrado: {}", transacaoDTO.getNumeroCartao());
                    return new CartaoInexistenteException();
                });

        if (!cartao.getSenha().equals(transacaoDTO.getSenhaCartao())) {
            logger.warn("Senha inválida para o cartão: {}", transacaoDTO.getNumeroCartao());
            throw new SenhaInvalidaException();
        }

        if (cartao.getSaldo().compareTo(transacaoDTO.getValor()) < 0) {
            logger.warn("Saldo insuficiente no cartão: {}", transacaoDTO.getNumeroCartao());
            throw new SaldoInsuficienteException();
        }

        cartao.debitarSaldo(transacaoDTO.getValor());
        cartaoRepository.save(cartao);

        logger.info("Transação concluída com sucesso para o cartão: {}", transacaoDTO.getNumeroCartao());
        return ResponseEntity.status(HttpStatus.CREATED).body("Transação realizada com sucesso!");
    }
}
