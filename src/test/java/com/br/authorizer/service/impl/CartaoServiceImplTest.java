package com.br.authorizer.service.impl;

import com.br.authorizer.dto.CartaoDTO;
import com.br.authorizer.dto.CartaoResponseDTO;
import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.repository.CartaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
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

    @Test
    void deveCriarCartaoQuandoNaoExistir() {
        CartaoDTO cartaoDTO = new CartaoDTO("1234567890123456", "1234");
        CartaoEntity cartaoEntity = new CartaoEntity("1234567890123456", "1234", BigDecimal.valueOf(500.00));

        // Simulando comportamento do repositório
        when(cartaoRepository.findById(cartaoDTO.getNumeroCartao())).thenReturn(Optional.empty());
        when(cartaoRepository.save(any(CartaoEntity.class))).thenReturn(cartaoEntity);

        // Testando o método
        CartaoResponseDTO response = cartaoService.createCartao(cartaoDTO);

        assertNotNull(response);
        assertEquals(cartaoDTO.getNumeroCartao(), response.getNumeroCartao());
        assertEquals(cartaoEntity.getSaldo(), response.getSaldo());

        // Verificando interações com o repositório
        verify(cartaoRepository, times(1)).findById(cartaoDTO.getNumeroCartao());
        verify(cartaoRepository, times(1)).save(any(CartaoEntity.class));
    }

    @Test
    void deveRetornarSaldoDoCartaoQuandoExistir() {
        String numeroCartao = "1234567890123456";
        CartaoEntity cartaoEntity = new CartaoEntity(numeroCartao, "1234", BigDecimal.valueOf(500.00));

        // Simulando comportamento do repositório
        when(cartaoRepository.findById(numeroCartao)).thenReturn(Optional.of(cartaoEntity));

        // Testando o método
        CartaoResponseDTO response = cartaoService.getBalance(numeroCartao);

        assertNotNull(response);
        assertEquals(numeroCartao, response.getNumeroCartao());
        assertEquals(cartaoEntity.getSaldo(), response.getSaldo());

        // Verificando interações com o repositório
        verify(cartaoRepository, times(1)).findById(numeroCartao);
    }

    @Test
    void deveLancarErroQuandoCartaoNaoExistir() {
        String numeroCartao = "1234567890123456";

        // Simulando comportamento do repositório
        when(cartaoRepository.findById(numeroCartao)).thenReturn(Optional.empty());

        // Verificando se a exceção é lançada
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cartaoService.getBalance(numeroCartao));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}