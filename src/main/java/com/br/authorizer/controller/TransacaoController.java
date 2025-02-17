package com.br.authorizer.controller;

import com.br.authorizer.dto.TransacaoDTO;
import com.br.authorizer.service.CartaoService;
import com.br.authorizer.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<String> realizarTransacao(@RequestBody TransacaoDTO transacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.processTransaction(transacao));
    }
}
