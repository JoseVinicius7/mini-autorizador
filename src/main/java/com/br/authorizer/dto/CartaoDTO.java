package com.br.authorizer.dto;

public class CartaoDTO {
    private String numeroCartao;
    private String senha;

    public CartaoDTO(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }

    public CartaoDTO() {
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getSenha() {
        return senha;
    }
}
