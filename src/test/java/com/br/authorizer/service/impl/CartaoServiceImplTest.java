package com.br.authorizer.service.impl;

import com.br.authorizer.dto.CartaoDTO;
import com.br.authorizer.dto.CartaoResponseDTO;
import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.handler.exception.CartaoNaoEncontradoException;
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

    private CartaoDTO cartaoDTO;
    private CartaoEntity cartaoEntity;

    @BeforeEach
    void setUp() {
        cartaoDTO = new CartaoDTO("1234567890123456", "1234");
        cartaoEntity = new CartaoEntity("1234567890123456", "1234", new BigDecimal("100.00"));
    }

    @Test
    void testCreateCartao_CartaoNaoExistente() {
        when(cartaoRepository.existsById(cartaoDTO.getNumeroCartao())).thenReturn(false);
        when(cartaoRepository.save(any(CartaoEntity.class))).thenReturn(cartaoEntity);

        CartaoResponseDTO response = cartaoService.createCartao(cartaoDTO);

        assertNotNull(response);
        assertEquals("1234567890123456", response.getNumeroCartao());
        assertEquals(new BigDecimal("100.00"), response.getSaldo());

        verify(cartaoRepository, times(1)).save(any(CartaoEntity.class));
    }

    @Test
    void testCreateCartao_CartaoJaExistente() {
        when(cartaoRepository.existsById(cartaoDTO.getNumeroCartao())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> cartaoService.createCartao(cartaoDTO));

        verify(cartaoRepository, never()).save(any(CartaoEntity.class));
    }

    @Test
    void testGetBalance_CartaoExistente() {
        when(cartaoRepository.findById(cartaoDTO.getNumeroCartao())).thenReturn(Optional.of(cartaoEntity));

        CartaoResponseDTO response = cartaoService.getBalance(cartaoDTO.getNumeroCartao());

        assertNotNull(response);
        assertEquals(new BigDecimal("100.00"), response.getSaldo());
    }

    @Test
    void testGetBalance_CartaoNaoExistente() {
        when(cartaoRepository.findById(cartaoDTO.getNumeroCartao())).thenReturn(Optional.empty());

        assertThrows(CartaoNaoEncontradoException.class, () -> cartaoService.getBalance(cartaoDTO.getNumeroCartao()));
    }
}
