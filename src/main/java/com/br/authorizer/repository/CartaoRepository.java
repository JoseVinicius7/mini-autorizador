package com.br.authorizer.repository;

import com.br.authorizer.entity.CartaoEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<CartaoEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CartaoEntity c WHERE c.numeroCartao = :numeroCartao")
    Optional<CartaoEntity> findByNumeroCartaoComLock(@Param("numeroCartao") String numeroCartao);
}

