package com.br.authorizer.service.impl;

import com.br.authorizer.dto.TransacaoDTO;
import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.handler.exception.CartaoInexistenteException;
import com.br.authorizer.handler.exception.SaldoInsuficienteException;
import com.br.authorizer.handler.exception.SenhaInvalidaException;
import com.br.authorizer.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransacaoServiceImplTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private TransacaoServiceImpl transacaoService;

    private TransacaoDTO transacaoDTO;
    private CartaoEntity cartaoEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        transacaoDTO = new TransacaoDTO("1234567890123456", "1234", new BigDecimal("50.00"));
        cartaoEntity = new CartaoEntity("1234567890123456", "1234", new BigDecimal("100.00"));
    }

    @Test
    void testProcessTransaction_Success() {
        when(cartaoRepository.findByNumeroCartaoComLock(transacaoDTO.getNumeroCartao())).thenReturn(Optional.of(cartaoEntity));

        ResponseEntity<String> response = transacaoService.processTransaction(transacaoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Transação realizada com sucesso!", response.getBody());

        verify(cartaoRepository, times(1)).save(any(CartaoEntity.class));
    }

    @Test
    void testProcessTransaction_CartaoInexistente() {
        when(cartaoRepository.findByNumeroCartaoComLock(transacaoDTO.getNumeroCartao())).thenReturn(Optional.empty());

        assertThrows(CartaoInexistenteException.class, () -> {
            transacaoService.processTransaction(transacaoDTO);
        });

        verify(cartaoRepository, times(0)).save(any(CartaoEntity.class));
    }

    @Test
    void testProcessTransaction_SenhaInvalida() {
        cartaoEntity.setSenha("wrongpassword");
        when(cartaoRepository.findByNumeroCartaoComLock(transacaoDTO.getNumeroCartao())).thenReturn(Optional.of(cartaoEntity));

        assertThrows(SenhaInvalidaException.class, () -> {
            transacaoService.processTransaction(transacaoDTO);
        });

        verify(cartaoRepository, times(0)).save(any(CartaoEntity.class));
    }

    @Test
    void testProcessTransaction_SaldoInsuficiente() {
        transacaoDTO.setValor(new BigDecimal("200.00"));
        when(cartaoRepository.findByNumeroCartaoComLock(transacaoDTO.getNumeroCartao())).thenReturn(Optional.of(cartaoEntity));

        assertThrows(SaldoInsuficienteException.class, () -> {
            transacaoService.processTransaction(transacaoDTO);
        });

        verify(cartaoRepository, times(0)).save(any(CartaoEntity.class));
    }
}