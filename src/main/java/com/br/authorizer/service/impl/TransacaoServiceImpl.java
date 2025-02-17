package com.br.authorizer.service.impl;


import com.br.authorizer.dto.TransacaoDTO;
import com.br.authorizer.entity.CartaoEntity;
import com.br.authorizer.repository.CartaoRepository;
import com.br.authorizer.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

    private final CartaoRepository cartaoRepository;

    public String processTransaction(TransacaoDTO transacao) {
        // Buscar o cartão no banco
        CartaoEntity cartao = cartaoRepository.findById(transacao.getNumeroCartao())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "CARTAO_INEXISTENTE"));

        // Verificar se a senha está correta
        if (!cartao.getSenha().equals(transacao.getSenhaCartao())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "SENHA_INVALIDA");
        }

        // Verificar se o saldo é suficiente
        if (cartao.getSaldo().compareTo(transacao.getValor()) < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "SALDO_INSUFICIENTE");
        }

        // Realizar o débito e salvar no banco
        cartao.setSaldo(cartao.getSaldo().subtract(transacao.getValor()));
        cartaoRepository.save(cartao);

        return "OK";
    }
}
