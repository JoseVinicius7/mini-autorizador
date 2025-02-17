package com.br.authorizer.controller;

import com.br.authorizer.dto.CartaoDTO;
import com.br.authorizer.dto.CartaoResponseDTO;
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
    public ResponseEntity<CartaoResponseDTO> criarCartao(@RequestBody CartaoDTO cartao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoService.createCartao(cartao));
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<CartaoResponseDTO> obterSaldo(@PathVariable String numeroCartao) {
        return ResponseEntity.ok(cartaoService.getBalance(numeroCartao));
    }
}

