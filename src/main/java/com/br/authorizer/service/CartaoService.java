package com.br.authorizer.service;

import com.br.authorizer.dto.CartaoDTO;
import com.br.authorizer.dto.CartaoResponseDTO;

public interface CartaoService {

    CartaoResponseDTO createCartao(CartaoDTO cartao);

    CartaoResponseDTO getBalance(String numeroCartao);

}
