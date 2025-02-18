package com.br.authorizer.controller;

import com.br.authorizer.dto.TransacaoDTO;
import com.br.authorizer.handler.exception.CartaoInexistenteException;
import com.br.authorizer.handler.exception.SaldoInsuficienteException;
import com.br.authorizer.handler.exception.SenhaInvalidaException;
import com.br.authorizer.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransacaoControllerTest {

    @Mock
    private TransacaoService transacaoService;

    private MockMvc mockMvc;

    @InjectMocks
    private TransacaoController transacaoController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
    }

    @Test
    void shouldProcessTransactionSuccessfully() throws Exception {
        when(transacaoService.processTransaction(any(TransacaoDTO.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body("Transação realizada com sucesso!"));

        mockMvc.perform(post("/transacoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\":\"123456789\",\"valor\":100.0}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Transação realizada com sucesso!"));

    }

    @Test
    void shouldReturnNotFoundWhenCardIsNotFound() throws Exception {
        when(transacaoService.processTransaction(any(TransacaoDTO.class)))
                .thenThrow(new CartaoInexistenteException());

        mockMvc.perform(post("/transacoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\":\"123456789\",\"valor\":100.0}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cartão não encontrado"));

    }

    @Test
    void shouldReturnUnprocessableEntityWhenBalanceIsInsufficient() throws Exception {
        when(transacaoService.processTransaction(any(TransacaoDTO.class)))
                .thenThrow(new SaldoInsuficienteException());

        mockMvc.perform(post("/transacoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\":\"123456789\",\"valor\":1000.0}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Saldo insuficiente"));

    }

    @Test
    void shouldReturnUnauthorizedWhenInvalidPassword() throws Exception {
        when(transacaoService.processTransaction(any(TransacaoDTO.class)))
                .thenThrow(new SenhaInvalidaException());

        mockMvc.perform(post("/transacoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\":\"123456789\",\"valor\":100.0}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Senha inválida"));

    }

    @Test
    void shouldReturnInternalServerErrorWhenUnexpectedErrorOccurs() throws Exception {
        when(transacaoService.processTransaction(any(TransacaoDTO.class)))
                .thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(post("/transacoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\":\"123456789\",\"valor\":100.0}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erro ao processar a transação."));

    }
}
