package com.br.authorizer.controller;

import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.service.CartaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PostMapping
    public ResponseEntity<CartaoEntity> criarCartao(@RequestBody CartaoEntity cartao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoService.createCartao(cartao));
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> obterSaldo(@PathVariable String numeroCartao) {
        return ResponseEntity.ok(cartaoService.getBalance(numeroCartao));
    }
}

