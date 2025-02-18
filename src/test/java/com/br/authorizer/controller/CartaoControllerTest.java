package com.br.authorizer.controller;

import com.br.authorizer.dto.CartaoDTO;
import com.br.authorizer.dto.CartaoResponseDTO;
import com.br.authorizer.service.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartaoControllerTest {

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private CartaoController cartaoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
    }

    @Test
    void shouldCreateCartaoSuccessfully() throws Exception {
        CartaoResponseDTO responseDTO = new CartaoResponseDTO("123456789", BigDecimal.valueOf(500.0));

        when(cartaoService.createCartao(any(CartaoDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/cartoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\":\"123456789\",\"valor\":500.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCartao").value("123456789"))
                .andExpect(jsonPath("$.saldo").value(500.0));
    }

    @Test
    void shouldReturnInternalServerErrorWhenCreateCartaoFails() throws Exception {

        when(cartaoService.createCartao(any(CartaoDTO.class))).thenThrow(new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Erro ao criar o cartão"));

        mockMvc.perform(post("/cartoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\":\"123456789\",\"valor\":500.0}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void shouldGetSaldoSuccessfully() throws Exception {
        String numeroCartao = "123456789";
        CartaoResponseDTO responseDTO = new CartaoResponseDTO(numeroCartao, BigDecimal.valueOf(500.0));

        when(cartaoService.getBalance(anyString())).thenReturn(responseDTO);

        mockMvc.perform(get("/cartoes/{numeroCartao}", numeroCartao))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCartao").value(numeroCartao))
                .andExpect(jsonPath("$.saldo").value(500.0));
    }


}