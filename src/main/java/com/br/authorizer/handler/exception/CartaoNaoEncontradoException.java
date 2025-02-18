package com.br.authorizer.handler.exception;

public class CartaoNaoEncontradoException extends RuntimeException {
    public CartaoNaoEncontradoException(String numeroCartao) {
        super("Cartão não encontrado: " + numeroCartao);
    }
}
