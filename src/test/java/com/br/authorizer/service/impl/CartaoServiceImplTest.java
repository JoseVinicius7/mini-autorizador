package com.br.authorizer.service.impl;

import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartaoServiceImplTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoServiceImpl cartaoService;

    private CartaoEntity cartao;

    @BeforeEach
    void setUp() {
        cartao = new CartaoEntity();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSenha("1234");
        cartao.setSaldo(new BigDecimal("500.00"));
    }

    @Test
    void deveCriarCartaoQuandoNaoExistir() {
        when(cartaoRepository.findById(cartao.getNumeroCartao())).thenReturn(Optional.empty());
        when(cartaoRepository.save(cartao)).thenReturn(cartao);

        CartaoEntity cartaoCriado = cartaoService.createCartao(cartao);

        assertNotNull(cartaoCriado);
        assertEquals(cartao.getNumeroCartao(), cartaoCriado.getNumeroCartao());
        verify(cartaoRepository, times(1)).findById(cartao.getNumeroCartao());
        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    void deveRetornarCartaoExistenteSemCriarNovo() {
        when(cartaoRepository.findById(cartao.getNumeroCartao())).thenReturn(Optional.of(cartao));

        CartaoEntity cartaoRetornado = cartaoService.createCartao(cartao);

        assertNotNull(cartaoRetornado);
        assertEquals(cartao.getNumeroCartao(), cartaoRetornado.getNumeroCartao());
        verify(cartaoRepository, times(1)).findById(cartao.getNumeroCartao());
        verify(cartaoRepository, never()).save(cartao);
    }

    @Test
    void deveRetornarSaldoDoCartao() {
        when(cartaoRepository.findById(cartao.getNumeroCartao())).thenReturn(Optional.of(cartao));

        BigDecimal saldo = cartaoService.getBalance(cartao.getNumeroCartao());

        assertNotNull(saldo);
        assertEquals(new BigDecimal("500.00"), saldo);
        verify(cartaoRepository, times(1)).findById(cartao.getNumeroCartao());
    }

    @Test
    void deveLancarExcecaoQuandoCartaoNaoForEncontrado() {
        when(cartaoRepository.findById("0000000000000000")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> cartaoService.getBalance("0000000000000000"));

        verify(cartaoRepository, times(1)).findById("0000000000000000");
    }
}