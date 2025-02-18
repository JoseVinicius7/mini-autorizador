package com.br.authorizer.handler.exception;


public class CartaoInexistenteException extends RuntimeException {
    public CartaoInexistenteException() {
        super("Cartão não encontrado");
    }
}
