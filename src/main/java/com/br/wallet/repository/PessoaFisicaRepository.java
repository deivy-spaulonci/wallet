package com.br.wallet.repository;

import com.br.wallet.dto.response.PessoaFisicaResponseDTO;
import com.br.wallet.entity.PessoaFisica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, BigInteger> {
    String QUERY = """
    SELECT new com.br.wallet.dto.response.PessoaFisicaResponseDTO(     
     pf.nome,
     pf.cpf,
     pf.codigoIbge)
    FROM PessoaFisica pf
    WHERE LOWER(pf.nome) LIKE LOWER(CONCAT('%', :busca, '%'))
    """;
    @Query(QUERY)
    Page<PessoaFisicaResponseDTO> findByNomeCpfPaged(@Param("busca") String busca, Pageable pageable);
//       OR pf.cpf  LIKE CONCAT('%', :busca, '%')

    @Query(QUERY)
    List<PessoaFisicaResponseDTO> findByNomeCpf(@Param("texto") String texto);

    PessoaFisica findPessoaFisicaByUuid(UUID uuid);
}
