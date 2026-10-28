package com.br.wallet.repository;

import com.br.wallet.dto.response.DespesaResponseGastoAnualDTO;
import com.br.wallet.dto.response.DespesaResponseGastoMensalDTO;
import com.br.wallet.dto.response.DespesaResponseGastoTipoDTO;
import com.br.wallet.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, BigInteger>, JpaSpecificationExecutor<Despesa>{

    @Query("""
        SELECT YEAR(d.dataPagamento) AS ano, SUM(d.valor) AS total
        FROM Despesa d
        GROUP BY YEAR(d.dataPagamento)
        ORDER BY YEAR(d.dataPagamento) ASC
    """)
    List<DespesaResponseGastoAnualDTO> gastoTotalAnual();

    @Query("""
        SELECT MONTH(d.dataPagamento) AS mes , SUM(d.valor) AS total
        FROM Despesa d
            WHERE YEAR(d.dataPagamento)= :ano
        GROUP BY MONTH(d.dataPagamento)
        ORDER BY MONTH(d.dataPagamento) ASC
    """)
    List<DespesaResponseGastoMensalDTO> gastoTotalMensal(@Param("ano") Integer ano);

    @Query("""
        SELECT d.tipoDespesa AS tipoDespesa, SUM(d.valor) AS total
        FROM Despesa d
        GROUP BY d.tipoDespesa
        ORDER BY d.tipoDespesa ASC
    """)
    List<DespesaResponseGastoTipoDTO> gastoTotalTipo();

    Despesa findByUuid(UUID uuid);
}
