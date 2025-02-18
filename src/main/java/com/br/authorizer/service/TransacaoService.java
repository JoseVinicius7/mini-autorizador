package com.br.authorizer.service;


import com.br.authorizer.dto.TransacaoDTO;
import org.springframework.http.ResponseEntity;

public interface TransacaoService {
    ResponseEntity<String> processTransaction(TransacaoDTO transacaoDTO);
}
