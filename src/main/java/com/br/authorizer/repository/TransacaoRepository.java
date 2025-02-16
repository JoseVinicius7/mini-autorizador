package com.br.authorizer.repository;

import com.br.authorizer.dto.TransacaoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransacaoRepository extends MongoRepository<TransacaoDTO, String> {
}