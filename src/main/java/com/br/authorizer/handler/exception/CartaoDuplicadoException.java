package com.br.authorizer.handler.exception;


public class CartaoDuplicadoException extends RuntimeException {
    public CartaoDuplicadoException() {
        super("Cartão já existe");
    }
}
