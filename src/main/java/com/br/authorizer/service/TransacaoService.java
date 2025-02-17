package com.br.authorizer.service;


import com.br.authorizer.dto.TransacaoDTO;

public interface TransacaoService {
    String processTransaction(TransacaoDTO transacaoDTO);
}
