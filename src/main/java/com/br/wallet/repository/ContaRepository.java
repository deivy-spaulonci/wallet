package com.br.wallet.repository;

import com.br.wallet.dto.response.ContaResponseGastoTipoDTO;
import com.br.wallet.dto.response.ContaResponseTotaisStatusDTO;
import com.br.wallet.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaRepository extends JpaRepository<Conta, BigInteger>, JpaSpecificationExecutor<Conta> {

    Optional<Conta> findByUuid(UUID uuid);

    @Query("""
        SELECT c.tipoConta.nome AS tipoConta, SUM(c.valor) AS total
        FROM Conta c
        GROUP BY c.tipoConta
        ORDER BY c.tipoConta ASC
    """)
    List<ContaResponseGastoTipoDTO> gastoTotalTipo();

    @Query("""
        SELECT CASE
            WHEN c.valorPago IS NULL AND c.vencimento = CURRENT_DATE THEN 'HOJE'
            WHEN c.valorPago IS NULL AND c.vencimento >= CURRENT_DATE THEN 'ABERTO'
            WHEN c.valorPago IS NULL AND c.vencimento <= CURRENT_DATE THEN 'ATRASADO'
        ELSE 'PAGO'
        END AS status,
        SUM(c.valor) AS total
        FROM Conta c
        GROUP BY
            CASE
                WHEN c.valorPago IS NULL AND c.vencimento = CURRENT_DATE THEN 'HOJE'
                WHEN c.valorPago IS NULL AND c.vencimento >= CURRENT_DATE THEN 'ABERTO'
                WHEN c.valorPago IS NULL AND c.vencimento <= CURRENT_DATE THEN 'ATRASADO'
                ELSE 'PAGO'
            END
    """)
    List<ContaResponseTotaisStatusDTO> totaisStatus();
}
