package com.br.wallet.repository;

import com.br.wallet.entity.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.UUID;

public interface TipoContaRepository extends JpaRepository<TipoConta, BigInteger> {
    TipoConta findTipoContaByUuid(UUID uuid);
}
