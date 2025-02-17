package com.br.authorizer.service;


import com.br.authorizer.dto.TransacaoDTO;

public interface TransacaoService {

    public String processTransaction(TransacaoDTO transacao);
}
