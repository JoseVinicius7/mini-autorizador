package com.br.authorizer.handler;

import com.br.authorizer.handler.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void shouldReturnNotFoundWhenCartaoInexistenteExceptionIsThrown() {
        CartaoInexistenteException exception = new CartaoInexistenteException();

        ResponseEntity<String> response = globalExceptionHandler.handleCartaoInexistente(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnUnauthorizedWhenSenhaInvalidaExceptionIsThrown() {
        SenhaInvalidaException exception = new SenhaInvalidaException();

        ResponseEntity<String> response = globalExceptionHandler.handleSenhaInvalida(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldReturnUnprocessableEntityWhenSaldoInsuficienteExceptionIsThrown() {
        SaldoInsuficienteException exception = new SaldoInsuficienteException();

        ResponseEntity<String> response = globalExceptionHandler.handleSaldoInsuficiente(exception);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    void shouldReturnUnprocessableEntityWhenCartaoDuplicadoExceptionIsThrown() {
        CartaoDuplicadoException exception = new CartaoDuplicadoException();

        ResponseEntity<String> response = globalExceptionHandler.handleCartaoDuplicado(exception);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenCartaoNaoEncontradoExceptionIsThrown() {
        CartaoNaoEncontradoException exception = new CartaoNaoEncontradoException("Cartão não encontrado");

        ResponseEntity<String> response = globalExceptionHandler.handleCartaoNaoEncontrado(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldReturnInternalServerErrorForGenericException() {
        Exception exception = new Exception("Erro inesperado");

        ResponseEntity<String> response = globalExceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro ao processar a transação.", response.getBody());
    }
}
