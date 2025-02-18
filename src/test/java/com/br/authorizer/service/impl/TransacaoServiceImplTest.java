package com.br.authorizer.service.impl;

import com.br.authorizer.dto.TransacaoDTO;
import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.repository.CartaoRepository;
import com.br.authorizer.service.impl.TransacaoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceImplTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private TransacaoServiceImpl transacaoService;

    @Test
    void deveRealizarTransacaoComSucesso() {
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSenha("1234");
        cartao.setSaldo(new BigDecimal("500.00"));

        TransacaoDTO transacao = new TransacaoDTO();
        transacao.setNumeroCartao("1234567890123456");
        transacao.setSenhaCartao("1234");
        transacao.setValor(new BigDecimal("100.00"));

        when(cartaoRepository.findById("1234567890123456")).thenReturn(Optional.of(cartao));
        when(cartaoRepository.save(any())).thenReturn(cartao);

        ResponseEntity<String> response = transacaoService.processTransaction(transacao);

        assertEquals("OK", response);

        verify(cartaoRepository, times(1)).findById("1234567890123456");
        verify(cartaoRepository, times(1)).save(any());
    }
}
