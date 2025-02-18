package com.br.authorizer.controller;

import com.br.authorizer.dto.TransacaoDTO;
import com.br.authorizer.handler.exception.CartaoInexistenteException;
import com.br.authorizer.handler.exception.SaldoInsuficienteException;
import com.br.authorizer.handler.exception.SenhaInvalidaException;
import com.br.authorizer.service.TransacaoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private static final Logger logger = LogManager.getLogger(TransacaoController.class);
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<String> realizarTransacao(@RequestBody TransacaoDTO transacaoDTO) {
        logger.info("Recebendo requisição para realizar transação. Número do Cartão: {}, Valor: {}",
                transacaoDTO.getNumeroCartao(), transacaoDTO.getValor());

        try {
            ResponseEntity<String> result = transacaoService.processTransaction(transacaoDTO);
            logger.info("Transação realizada com sucesso. Número do Cartão: {}, Valor: {}",
                    transacaoDTO.getNumeroCartao(), transacaoDTO.getValor());
            return result;
        } catch (CartaoInexistenteException e) {
            logger.error("Erro ao realizar transação. Número do Cartão: {}, Valor: {}, Erro: Cartão não encontrado",
                    transacaoDTO.getNumeroCartao(), transacaoDTO.getValor());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado");
        } catch (SaldoInsuficienteException e) {
            logger.error("Erro ao realizar transação. Número do Cartão: {}, Valor: {}, Erro: Saldo insuficiente",
                    transacaoDTO.getNumeroCartao(), transacaoDTO.getValor());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Saldo insuficiente");
        } catch (SenhaInvalidaException e) {
            logger.error("Erro ao realizar transação. Número do Cartão: {}, Valor: {}, Erro: Senha inválida",
                    transacaoDTO.getNumeroCartao(), transacaoDTO.getValor());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha inválida");
        } catch (Exception e) {
            logger.error("Erro ao realizar transação. Número do Cartão: {}, Valor: {}, Erro: {}",
                    transacaoDTO.getNumeroCartao(), transacaoDTO.getValor(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a transação.");
        }
    }
}
