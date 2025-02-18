package com.br.authorizer.controller;

import com.br.authorizer.dto.CartaoDTO;
import com.br.authorizer.dto.CartaoResponseDTO;
import com.br.authorizer.handler.exception.CartaoDuplicadoException;
import com.br.authorizer.service.CartaoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private static final Logger logger = LogManager.getLogger(CartaoController.class);
    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PostMapping
    public ResponseEntity<CartaoResponseDTO> criarCartao(@RequestBody CartaoDTO cartaoDTO) {
        logger.info("Recebendo requisição para criar cartão: {}", cartaoDTO.getNumeroCartao());

        try {
            CartaoResponseDTO response = cartaoService.createCartao(cartaoDTO);
            logger.info("Cartão criado com sucesso: {}", cartaoDTO.getNumeroCartao());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (CartaoDuplicadoException e) {
            logger.error("Erro ao realizar transação. Número do Cartão: {}, Erro: Cartão já existe.",
                    cartaoDTO.getNumeroCartao());
            throw e;
        } catch (Exception ex) {
            logger.error("Erro ao criar cartão {}: {}", cartaoDTO.getNumeroCartao(), ex.getMessage());
            throw ex;
        }
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<CartaoResponseDTO> obterSaldo(@PathVariable String numeroCartao) {
        logger.info("Consultando saldo do cartão: {}", numeroCartao);

        try {
            CartaoResponseDTO response = cartaoService.getBalance(numeroCartao);
            logger.info("Saldo obtido com sucesso para o cartão: {}", numeroCartao);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Erro ao consultar saldo do cartão {}: {}", numeroCartao, e.getMessage());
            throw e;
        }
    }
}